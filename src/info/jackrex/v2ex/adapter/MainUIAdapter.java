package info.jackrex.v2ex.adapter;

import info.jackrex.v2ex.R;
import info.jackrex.v2ex.entity.HomeEntity;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainUIAdapter extends BaseAdapter {

	
	
	private Context context;
	private LayoutInflater inflater;
	private List<HomeEntity> entities;
	
	public MainUIAdapter(Context context, List<HomeEntity> entities) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
	}
	
	
	
	
	public List<HomeEntity> getEntities() {
		return entities;
	}




	public void setEntities(List<HomeEntity> entities) {
		this.entities = entities;
	}




	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		
		HomeEntity entity = entities.get(position);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_home, null);
			holder = new ViewHolder();
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.tv_detail = (TextView) convertView
					.findViewById(R.id.tv_detail);
			holder.tv_comment = (TextView) convertView
					.findViewById(R.id.tv_comment);
			holder.iv_img = (ImageView) convertView
					.findViewById(R.id.iv_img);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.iv_head);
		
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_content.setText(entity.getTitle());
		holder.tv_comment.setText(entity.getCount());
		holder.tv_detail.setText(entity.getSmallfade());
		
		ImageLoader.getInstance().displayImage(entity.getAvater(), holder.iv_img);
		ImageLoader.getInstance().displayImage(entity.getAvater(), holder.iv_head);
		
		

		return convertView;
	}

	private class ViewHolder {
		TextView tv_content, tv_detail, tv_comment;
		ImageView iv_img, iv_head;
	}
}
