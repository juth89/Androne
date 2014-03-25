package de.dhbw.androne.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.dhbw.androne.control.DroneControl;

public abstract class ControlFragment extends Fragment {

	protected DroneControl droneControl;
	private TextView textViewAltitude, textViewState, textViewBattery;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		View view = createView(inflater, container);
		
		droneControl = ((MainActivity) getActivity()).getDroneControl();
		
		textViewAltitude = (TextView) view.findViewById(R.id.text_view_altitude);
		textViewState = (TextView) view.findViewById(R.id.text_view_state);
		textViewBattery = (TextView) view.findViewById(R.id.text_view_battery);
		
		return view;
	}
	
	
	protected abstract View createView(LayoutInflater inflater, ViewGroup container);
	
	
	public void setAltitude(float altitude) {
		if(!isVisible()) {
			return;
		}
		String text = getResources().getString(R.string.text_view_altitude);
		textViewAltitude.setText(text + " " + altitude + " m");
	}

	public void setState(String state) {
		if(!isVisible()) {
			return;
		}
		String text = getResources().getString(R.string.text_view_state);
		textViewState.setText(text + " " +state);
	}
	
	public void setBattery(int battery) {
		if(!isVisible()) {
			return;
		}
		String text = getResources().getString(R.string.text_view_battery);
		textViewBattery.setText(text + " " + battery + " %");
	}
}
