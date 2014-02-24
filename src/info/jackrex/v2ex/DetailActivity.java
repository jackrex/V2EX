package info.jackrex.v2ex;

import info.jackrex.v2ex.adapter.DetailAdapter;
import info.jackrex.v2ex.entity.DetailEntity;
import info.jackrex.v2ex.entity.HomeEntity;
import info.jackrex.v2ex.uibase.XListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class DetailActivity extends BaseActivity {

	private Intent intent;
	private XListView xListView;
	private LayoutInflater inflater;
	private View headerView;
	
	private ImageView iv_head;
	private TextView tv_author, tv_title,tv_content,tv_comment,tv_time;
	
	private String url, title;
	private ProgressDialog dialog;
	
	private List<DetailEntity> entities;
	private HomeEntity homeEntity;
	private DetailAdapter detailAdapter;
	@Override
	public void setLayoutAndInitView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activitydetail);
		intent = getIntent();
		
		dialog = new ProgressDialog(DetailActivity.this);
		dialog.setMessage("请稍等...");
		homeEntity = (HomeEntity) intent.getExtras().getSerializable("homedata");
		xListView = (XListView) findViewById(R.id.comments_lists);
		inflater = LayoutInflater.from(this);
		initHeader();
		entities = new ArrayList<DetailEntity>();
		
		dialog.show();
		xListView.setPullLoadEnable(false);
		xListView.setPullRefreshEnable(false);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getDatafromJsoup( "http://www.v2ex.com"+homeEntity.getJumpUrl());
				
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						tv_content.setText(title);
						detailAdapter = new DetailAdapter(getApplicationContext(), entities);
						xListView.addHeaderView(headerView);
						xListView.setAdapter(detailAdapter);
						
						dialog.dismiss();
						
					}
				});
				
				
			}
		}).start();
		
		
		
	
		
	}
	
	private void getDatafromJsoup(String url) {
		// TODO Auto-generated method stub
		try {
			Document doc = Jsoup.connect(url).get();
			//Elements content = doc.getElementsByClass("cell item");
			
			Elements header = doc.getElementsByClass("topic_content");
			
			Log.e("topic_content", header.text());
			title = header.text();
			
			
			
			Elements content = doc.getElementsByTag("tbody");
			for(Element link : content){
				
				DetailEntity entity = new DetailEntity();
				
				Elements avatar =link.getElementsByTag("img");{
					String avaterLink = avatar.attr("src");
					if(avaterLink.startsWith("//cdn.")){
						entity.setAvater("http:"+avaterLink);
					}
				}
				
				Elements reply_content =link.getElementsByClass("reply_content");
			
				Log.e("reply_content", reply_content.text());
				
				entity.setReply_count(reply_content.text());
				
				Elements title = link.getElementsByTag("a");
				if(title.attr("href").startsWith("/member/")){
					
					Log.e("title",title.text() );
					entity.setTitle(title.text());
				}
				
				Log.e("other",link.getElementsByClass("fade small").text() + link.getElementsByClass("small fade").text());
				if(!TextUtils.isEmpty(reply_content.text()))
				entities.add(entity);

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void initHeader() {
		// TODO Auto-generated method stub
		headerView = inflater.inflate(R.layout.detail_header, null);
		iv_head = (ImageView) headerView.findViewById(R.id.iv_head);
		tv_title = (TextView) headerView.findViewById(R.id.tv_title);
		tv_content = (TextView) headerView.findViewById(R.id.tv_content);
		tv_comment = (TextView) headerView.findViewById(R.id.tv_comment);
		tv_time = (TextView) headerView.findViewById(R.id.tv_time);
		
		
		ImageLoader.getInstance().displayImage(homeEntity.getAvater(), iv_head ,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View view, FailReason arg2) {
				// TODO Auto-generated method stub
				((ImageView)view).setImageResource(R.drawable.default_headportrait_402x);
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				if(arg2!=null){
					Bitmap circleBitmap=ImageUtils.toRoundBitmap(arg2);
					((ImageView) arg1).setImageBitmap(circleBitmap);
					}
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		tv_title.setText(homeEntity.getTitle());
		headTitle = homeEntity.getTitle();
		tv_comment.setText(homeEntity.getCount());
		
		
	}

	
	
}
