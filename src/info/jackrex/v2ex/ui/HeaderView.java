/*
 * Copyright (c) 2014.
 * Jackrex
 */

package info.jackrex.v2ex.ui;

import info.jackrex.v2ex.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class HeaderView extends RelativeLayout{
	View headerView;
	public TextView tvHeaderTitle;
	public ImageButton btnLeft,btnRight;

	
	public HeaderView(Context context) {
        super(context);
        initView();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
		headerView = LayoutInflater.from(getContext()).inflate(R.layout.item_header,this);
        btnLeft = (ImageButton)headerView.findViewById(R.id.btnLeft);
        btnRight = (ImageButton)headerView.findViewById(R.id.btnRight);
		tvHeaderTitle = (TextView)headerView.findViewById(R.id.tvHeaderTitle);

	}
    

    
    public void setTitle(String Title){
    	tvHeaderTitle.setText(Title);
    }
    
}
