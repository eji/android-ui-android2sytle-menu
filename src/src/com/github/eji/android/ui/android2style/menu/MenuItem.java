package com.github.eji.android.ui.android2style.menu;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuItem {
	
	private int mCaptionResourceId = -1;
	private int mImageResourceId = -1;
	private MenuHandler mHandler;
	private Menu mMenu;
	private int mBackgroundResourceId;
	
	public MenuItem(Menu menu, int image_resource_id, int caption_resource_id, int background_resource_id, MenuHandler handler) {
	    mMenu = menu;
	    mCaptionResourceId = caption_resource_id;
	    mImageResourceId = image_resource_id;
	    mBackgroundResourceId = background_resource_id;
	    mHandler = handler;
	}
	
	public View getLayout() {
	    LinearLayout itemLayout = new LinearLayout(mMenu.getActivity());
	    itemLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
        LinearLayout.LayoutParams item_params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        item_params.gravity = Gravity.CENTER;
        itemLayout.setClickable(true);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setPadding(4, 4, 4, 4);
        itemLayout.setBackgroundResource(mBackgroundResourceId);
	    
	    ImageView icon = new ImageView(mMenu.getActivity());
	    icon.setImageResource(mImageResourceId);
        LinearLayout.LayoutParams icon_params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
        icon_params.gravity = Gravity.CENTER;
        icon.setLayoutParams(icon_params);
        icon.setPadding(0, 2, 0, 2); // top, bottom: 2dp
            
	    TextView caption = new TextView(mMenu.getActivity());
	    caption.setText(mCaptionResourceId);
        LinearLayout.LayoutParams caption_params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f);
        caption_params.gravity = Gravity.CENTER;
        caption.setLayoutParams(caption_params);
        caption.setTextSize(12);
	    
	    itemLayout.addView(icon);
	    itemLayout.addView(caption);
	    
	    itemLayout.setOnClickListener( new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            mHandler.handle();
	            mMenu.hide();
	        }
	    });
	    return itemLayout;
	}

}
