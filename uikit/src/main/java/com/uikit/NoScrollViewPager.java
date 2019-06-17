package com.uikit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义一个不能左右滑动的ViewPager
 * @author Beyond
 *
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}

	//所有在此控件上的触摸事件都不做处理
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
	
	/**
	 * 所有传到此view的事件，都不进行拦截，不拦截事件
	 * 1.解决问题是当ViewPage处于最后一页，继续往左滑，右边不出现空白
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

	/**
	 *依旧执行父类的事件分发机制
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
}
