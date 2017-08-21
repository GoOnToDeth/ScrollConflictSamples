package com.artdiscovery.samples.widget.nolifthand;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
public class MyListView extends ListView implements AbsListView.OnScrollListener {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private VerContainerView parent;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.setOnScrollListener(this);
        parent = (VerContainerView) getParent();
    }

    private int lastY;
    private boolean isTop;
    private boolean isDisallowIntercept;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDisallowIntercept = true;
                parent.requestDisallowInterceptTouchEvent(isDisallowIntercept);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTop && (y - lastY) > 0 && isDisallowIntercept) {
                    isDisallowIntercept = false;
                    parent.requestDisallowInterceptTouchEvent(isDisallowIntercept);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastY = y;
        parent.setLastY(y);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isDisallowIntercept && ev.getAction() == MotionEvent.ACTION_MOVE)
            return false;
        return super.onTouchEvent(ev);
    }

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
}
