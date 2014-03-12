package de.dhbw.androne.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.dhbw.androne.view.DirectControlFragment;
import de.dhbw.androne.view.PolygonControlFragment;
import de.dhbw.androne.view.ShapeControlFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private Fragment dcFragment, scFragment, pcFragment;
	private int currentIndex;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			dcFragment = new DirectControlFragment();
			return dcFragment;
		case 1:
			scFragment = new ShapeControlFragment();
			return scFragment;
		case 2:
			pcFragment = new PolygonControlFragment();
			return pcFragment;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	
	public void setCurrentIndex(int index) {
		currentIndex = index;
	}
	
	
	public Fragment getCurrentFragment() {
		switch(currentIndex) {
		case 0:
			return dcFragment;
		case 1:
			return scFragment;
		case 2:
			return pcFragment;
		default:
			return null;
		}
	}
}
