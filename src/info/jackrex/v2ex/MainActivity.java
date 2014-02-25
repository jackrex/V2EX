package info.jackrex.v2ex;

import info.jackrex.v2ex.adapter.MainUIAdapter;
import info.jackrex.v2ex.entity.HomeEntity;
import info.jackrex.v2ex.uibase.XListView;
import info.jackrex.v2ex.uibase.XListView.IXListViewListener;
import info.jackrex.v2ex.utils.NetWorkUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity implements IXListViewListener {

	private SlidingMenu sm;
	private ImageButton toggle;
	private XListView xlistView;
	private List<HomeEntity> homeEntities;
	private List<HomeEntity> entities;//暂时存储
	private ProgressDialog dialog;
	private MainUIAdapter adapter;

	private static final int REFRESH = 0;
	private static final int FINISH = 1;
	protected static final int ERROR = 2;
	
	protected boolean isFresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toggle = (ImageButton) findViewById(R.id.toggle);
		initSlidingMenu();
		UmengUpdateAgent.update(this);
		FeedbackAgent agent = new FeedbackAgent(this);
		agent.sync();
		xlistView = (XListView) findViewById(R.id.xlistview);
		dialog = new ProgressDialog(this);
		dialog.setMessage("加载中请稍后....");
		homeEntities = new ArrayList<HomeEntity>();
		entities =  new ArrayList<HomeEntity>();
		dialog.show();
		adapter = new MainUIAdapter(this, homeEntities);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				initJsoupData("tech");

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						xlistView.setAdapter(adapter);
						xlistView.setXListViewListener(MainActivity.this);
						xlistView.setPullLoadEnable(false);

						initListener();
					}
				});
			}
		}).start();

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case REFRESH:

				initJsoupData(VApplication.tab);

				break;

			case FINISH:
				
				
				adapter.setEntities(homeEntities);
				adapter.notifyDataSetChanged();
				dialog.dismiss();
				xlistView.stopRefresh();
				break;

			case ERROR:
				Toast.makeText(MainActivity.this, "网络请求错误，请稍后再试",
						Toast.LENGTH_LONG).show();
				xlistView.stopRefresh();
				break;

			default:
				break;
			}

		};

	};

	private void initListener() {
		// TODO Auto-generated method stub
		xlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putSerializable("homedata", homeEntities.get(arg2 - 1));

				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(MainActivity.this, DetailActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.abc_fade_in,
						R.anim.abc_fade_out);

			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initJsoupData(final String tab) {
		// TODO Auto-generated method stub

		if (NetWorkUtils.detect(getApplicationContext())) {
			dialog.show();
			

			try {

				@SuppressWarnings("resource")
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(VApplication.fileDir
								+ "/"+tab));
				homeEntities = (List<HomeEntity>) in.readObject();
				
				adapter.setEntities(homeEntities);
				adapter.notifyDataSetChanged();
				
				dialog.dismiss();

			} catch (Exception e) {
				// TODO: handle exception
				syncNetRequest(tab);
			}

			
		} else {
			Toast.makeText(MainActivity.this, "请检查您的网络连接诶...",
					Toast.LENGTH_LONG).show();

		}

	}

	private void syncNetRequest(final String tab) {
		
		if (homeEntities.size() != 0&&!isFresh) {
			homeEntities.clear();
		}
		
		if(isFresh){
			
			if(entities.size()!=0){
				entities.clear();
			}
		}
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					Document doc = Jsoup.connect("http://v2ex.com/?tab=" + tab)
							.get();
					// Elements content = doc.getElementsByClass("cell item");
					Elements content = doc.getElementsByTag("tbody");
					for (Element link : content) {

						HomeEntity entity = new HomeEntity();

						Elements avatar = link.getElementsByTag("img");
						String avaterLink = avatar.attr("src");
						if (avaterLink.startsWith("//cdn.")) {
							entity.setAvater("http:" + avaterLink);
						}
						
						if(avaterLink.startsWith("http://")){
							entity.setAvater(avaterLink);
						}
						
						Elements title = link.getElementsByTag("a");

						for (Element titlelink : title)

							if (titlelink.attr("href").startsWith("/t/")) {
								entity.setTitle(titlelink.text());

								entity.setJumpUrl(titlelink.attr("href"));
								break;
							}

						Elements smallfade = link.getAllElements();
						entity.setSmallfade(smallfade.text());

						Elements count = link.getElementsByClass("count_livid");
						entity.setCount(count.text());
						if(isFresh){
							
							if (!TextUtils.isEmpty(entity.getCount())) {
								
								entities.add(entity);
							}
						}else{
							if (!TextUtils.isEmpty(entity.getCount())) {
								
								homeEntities.add(entity);
							}
						}
						
					}
					
					if(isFresh){
						homeEntities.clear();
						homeEntities.addAll(entities);
					}
					
					VApplication.writeToFile(tab, homeEntities);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					handler.sendEmptyMessage(ERROR);
				}

				handler.sendEmptyMessage(FINISH);

			}
		}).start();
	}

	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		WindowManager manage = getWindowManager();
		Display display = manage.getDefaultDisplay();
		sm = new SlidingMenu(this);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setFadeDegree(0.35f);
		sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		sm.setShadowWidth(15);
		// sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffset(display.getWidth() * 2 / 3);
		sm.setMenu(R.layout.menu_frame);

		MenuFragment menuFragment = new MenuFragment(sm, handler);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(R.id.menu, menuFragment).commit();
		toggle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sm.toggle();
			}
		});
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		isFresh = true;
		syncNetRequest(VApplication.tab);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

}
