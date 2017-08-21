package com.artdiscovery.samples.widget.lifthand;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

import com.artdiscovery.samples.widget.CommonLayout;

/**
 * ===============================
 * 描    述：Listview需要抬手，才能可切换选项卡
 * 作    者：wzf
 * 创建日期：2017/8/15 17:53
 * ===============================
 */
public class VerContainerView2 extends CommonLayout {

    public VerContainerView2(@NonNull Context context) {
        super(context);
        init();
    }

    public VerContainerView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerContainerView2(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();


    private int lastY;
    int childHeight;
    int viewHeight;

    private int curentIndex;
    private MyListView2 mListView;

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mListView = (MyListView2) getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (curentIndex == 0) return true;
        else {
            int y = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.lastY = y;
                    return false;
                case MotionEvent.ACTION_MOVE:
                    return mListView.isTop() && (y - lastY) > 0;
                default:
                    return false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int index = event.getActionIndex();
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                childHeight = viewHeight = getMeasuredHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerId(index) == 0) { // 解决多点触控位置跳动的情况
                    smoothScrollBy(lastY - y);
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(100);
                int speedY = (int) mVelocityTracker.getYVelocity();
                if (speedY < -500) {
                    smoothScrollBy(childHeight - mScroller.getFinalY());
                    curentIndex = 1;
                } else if (speedY > 500) {
                    smoothScrollBy(-mScroller.getFinalY());
                    curentIndex = 0;
                } else {
                    scrollerToPager();
                }
                break;
        }
        this.lastY = y;
        return true;
    }

    private void scrollerToPager() {
        int scrollY = mScroller.getFinalY();
        if (scrollY >= childHeight / 2) {
            smoothScrollBy(childHeight - scrollY);
            curentIndex = 1;
        } else {
            smoothScrollBy(-scrollY);
            curentIndex = 0;
        }
    }

    private void smoothScrollBy(int dy) {
        mScroller.startScroll(0, mScroller.getFinalY(), 0, dy);
        invalidate();
    }

    private void smoothScrollTo(int fy) {
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dy);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
