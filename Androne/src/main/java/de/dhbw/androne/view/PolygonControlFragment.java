package de.dhbw.androne.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PolygonControlFragment extends ControlFragment {


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_polygon_control, container, false);
		
		return view;
	}

}
