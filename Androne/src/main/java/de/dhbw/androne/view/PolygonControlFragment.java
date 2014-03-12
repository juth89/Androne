package de.dhbw.androne.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PolygonControlFragment extends Fragment implements DroneUpdater {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		View rootView = inflater.inflate(R.layout.fragment_polygon_control, container, false);
		
		return rootView;
	}

	@Override
	public void setAltitude(float altitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setState(String state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBattery(int battery) {
		// TODO Auto-generated method stub
		
	}

}
