package info.jackrex.v2ex;

import info.jackrex.v2ex.adapter.slideMenuAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MenuFragment extends Fragment{
	private MainActivity activity;
	private View contentView;
	private ListView slideListView;
	private Typeface typeface;
	private Typeface typeface2;

	private Button about;
	SlidingMenu sm;
	private Handler handler;
	public MenuFragment(SlidingMenu sm , Handler handler) {
		this.sm = sm;
		this.handler = handler;
		// TODO Auto-generated constructor stub
	}
	
	
	public MenuFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activity = (MainActivity)getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){

		contentView       = inflater.inflate(R.layout.menu_layout, null);
		slideListView = (ListView) contentView.findViewById(R.id.slideListview);
		about             = (Button)contentView.findViewById(R.id.about);
	
		typeface2         = Typeface.createFromAsset(activity.getAssets(), "font/fangzhengthin.ttf");
	
		slideListView.setAdapter(new slideMenuAdapter(activity, activity.getResources().getStringArray(R.array.slideTab)));
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity,AboutActivity.class);
				startActivity(intent);
				activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		});
		return contentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		slideListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				sm.toggle();
				Log.e("xx", arg2+"");
				VApplication.tab = VApplication.tabs[arg2];
				
				Message msg = new Message();
				msg.what =0;
				handler.sendMessage(msg);
			}
		});
	}
	
}
