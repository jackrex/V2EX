package info.jackrex.v2ex.adapter;

import info.jackrex.v2ex.R;
import info.jackrex.v2ex.entity.DetailEntity;
import info.jackrex.v2ex.utils.ImageUtils;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class DetailAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<DetailEntity> entities;
	
	
	
	public DetailAdapter(Context context, List<DetailEntity> entities) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
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
		
		DetailEntity entity = entities.get(position);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.detail_list_item, null);
			holder = new ViewHolder();
			holder.tv_head = (TextView) convertView
					.findViewById(R.id.tv_head);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.iv_head);
			
			holder.comment_floor = (TextView) convertView
					.findViewById(R.id.comment_floor);
		
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		

		
		if (!TextUtils.isEmpty(entity.getAvater())) {

			ImageLoader.getInstance().displayImage(entity.getAvater(), holder.iv_head, new SimpleImageLoadingListener(){
				
				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					// TODO Auto-generated method stub
					if(loadedImage!=null){
					Bitmap circleBitmap=ImageUtils.toRoundBitmap(loadedImage);
					((ImageView) view).setImageBitmap(circleBitmap);
					}
				}
				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					// TODO Auto-generated method stub
					((ImageView)view).setImageResource(R.drawable.default_headportrait_402x);
				}
			});
		}else{
			holder.iv_head.setImageResource(R.drawable.default_headportrait_402x);
		}
		
		
		holder.comment_floor.setText(""+(position+1));
		holder.tv_content.setText(entity.getReply_count());
		holder.tv_head.setText(entity.getTitle());
		
		

		return convertView;
	}

	private class ViewHolder {
		TextView  tv_head, tv_time,tv_content,comment_floor;
		ImageView iv_head;
	}
}
