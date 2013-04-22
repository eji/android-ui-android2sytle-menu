package com.github.eji.android.ui.android2style.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class Menu {

	static final int MENU_COL_NUM = 2;
	
	private ArrayList<MenuItem> mMenuItems;
	private Activity mActivity = null;
	private PopupWindow mPopupWindow = null;
	private boolean mIsShowing = false;
	private int mMenuSpaceResourceId;
	private int mBackgroundColorId;
	
	public Menu(Activity activity, int menu_space_resource_id) {
		mMenuItems = new ArrayList<MenuItem>();
		mActivity = activity;
		mMenuSpaceResourceId = menu_space_resource_id;
	}
	
	public boolean isShowing() { return mIsShowing; }
	
	public void menuButtonPushed() {
		if (isShowing()) {
			hide();
		} else {
			show(mActivity.findViewById(mMenuSpaceResourceId));
		}
	}
	
	public Activity getActivity() {
	    return mActivity;
	}
	
	public synchronized void show(View v) {
		mIsShowing = true;
		if (mMenuItems.isEmpty() || mPopupWindow != null) return;
		
		LinearLayout menu = new LinearLayout(mActivity);
	    menu.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
	    menu.setOrientation(LinearLayout.VERTICAL);
		menu.setBackgroundColor(mBackgroundColorId);
		menu.setPadding(0, 0, 0, 0);
		menu.setFocusable(true);
		menu.setFocusableInTouchMode(true);
		menu.setClickable(true);
		
		mPopupWindow = new PopupWindow(menu, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		Display display = ((WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        mPopupWindow.setWidth(display.getWidth());
        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        
        menu.removeAllViews();
        Iterator <List<MenuItem>> it = Lists.reverse(Lists.partition(mMenuItems, Menu.MENU_COL_NUM)).iterator();
        while (it.hasNext()) {
            LinearLayout menu_row = new LinearLayout(mActivity);
            menu_row.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
            menu_row.setOrientation(LinearLayout.HORIZONTAL);
            menu_row.setPadding(0, 0, 0, 0);
            menu_row.setFocusable(true);
            menu_row.setFocusableInTouchMode(true);
            menu_row.setClickable(true);
		
            Iterator<MenuItem> row_it = it.next().iterator();
            while (row_it.hasNext()) {
    			menu_row.addView(row_it.next().getLayout());
            }
            menu.addView(menu_row);
        }
	}
	
	public synchronized void hide() {
		mIsShowing = false;
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
			mPopupWindow = null;
		}
		return;
	}
	
    public Menu addMenu(int icon_resource_id, int caption_resource_id, int background_resource_id, MenuHandler handler) {
		MenuItem cmi = new MenuItem(this, icon_resource_id, caption_resource_id, background_resource_id, handler);
		mMenuItems.add(cmi);
        return this;
    }
    
    public Menu setBackgroundColor(int color_resource_id) {
        mBackgroundColorId = color_resource_id;
        return this;
    }
    
}
