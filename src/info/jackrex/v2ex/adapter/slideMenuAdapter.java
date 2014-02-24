package info.jackrex.v2ex.adapter;

import info.jackrex.v2ex.R;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class slideMenuAdapter extends BaseAdapter {

	private Context context;
	private String[] tabs;
	private LayoutInflater inflater;
	private Typeface typeface;
	
	public slideMenuAdapter(Context context, String[] strs) {
		// TODO Auto-generated constructor stub
		this.context = context;
		tabs = strs;
		this.inflater = LayoutInflater.from(context);
		typeface         = Typeface.createFromAsset(context.getAssets(), "font/fangzhengthin.ttf");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tabs.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tabs[position];
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
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_dialog_item, null);
			holder = new ViewHolder();
			holder.txtItem = (TextView) convertView
					.findViewById(R.id.dialogItem);
			holder.bgMenu = (LinearLayout) convertView
					.findViewById(R.id.bg_menu);
			holder.bottom = (LinearLayout) convertView
					.findViewById(R.id.bottom);
			holder.bottom1 = (LinearLayout) convertView
					.findViewById(R.id.bottom1);
			holder.rela = (RelativeLayout) convertView.findViewById(R.id.rela);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		if (position == tabs.length - 1) {
			holder.bottom.setVisibility(View.INVISIBLE);
			holder.bottom1.setVisibility(View.INVISIBLE);
		} else {

			holder.bottom.setVisibility(View.VISIBLE);
			holder.bottom1.setVisibility(View.VISIBLE);
		}
		holder.txtItem.setTypeface(typeface);
		holder.txtItem.setText(tabs[position]);

		return convertView;
	}

	private class ViewHolder {
		TextView txtItem;
		LinearLayout bgMenu, bottom, bottom1;
		RelativeLayout rela;
	}

}
