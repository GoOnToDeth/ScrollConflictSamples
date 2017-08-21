package com.artdiscovery.samples.widget.lifthand;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * ===============================
 * 描    述：不需要抬手，可切换选项卡
 * 作    者：wzf
 * 创建日期：2017/8/16 16:47
 * ===============================
 */
public class MyListView2 extends ListView implements AbsListView.OnScrollListener {
    public MyListView2(Context context) {
        super(context);
    }

    public MyListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setOnScrollListener(this);
    }

    private boolean isTop;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0) {
            View firstVisibleItemView = getChildAt(0);
            if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                isTop = true;
                return;
            }
        }
        isTop = false;
    }

    public boolean isTop() {
        return isTop;
    }
}
