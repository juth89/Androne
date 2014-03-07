package de.dhbw.androne.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import de.dhbw.androne.view.DirectControlFragment;
import de.dhbw.androne.view.PolygonControlFragment;
import de.dhbw.androne.view.ShapeControlFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new DirectControlFragment();
		case 1:
			return new ShapeControlFragment();
		case 2:
			return new PolygonControlFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

}
