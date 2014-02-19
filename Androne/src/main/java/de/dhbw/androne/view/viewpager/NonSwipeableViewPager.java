package de.dhbw.androne.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeableViewPager extends ViewPager {

	public NonSwipeableViewPager(Context context) {
		super(context);
	}

	public NonSwipeableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// Override for non swipe
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    
    // Override for non swipe
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
