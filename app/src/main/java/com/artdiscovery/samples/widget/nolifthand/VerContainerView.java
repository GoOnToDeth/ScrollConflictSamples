package com.artdiscovery.samples.widget.nolifthand;

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
 * 描    述：不需要抬手，可切换选项卡
 * 作    者：wzf
 * 创建日期：2017/8/15 17:53
 * ===============================
 */
public class VerContainerView extends CommonLayout {

    public VerContainerView(@NonNull Context context) {
        super(context);
        init();
    }

    public VerContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerContainerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    private int lastY;
    int childHeight;
    int viewHeight;

    private int curentIndex;

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (curentIndex == 0)
            return true;
        else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    return false;
                default:
                    return true;
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
}
