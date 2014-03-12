package de.dhbw.androne.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import de.dhbw.androne.control.DroneControl;

public class DirectControlFragment extends Fragment implements OnTouchListener, DroneUpdater {

	public static DirectControlFragment INSTANCE;
	
	private DroneControl droneControl;
	private TextView textViewAltitude, textViewState, textViewBattery;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		View rootView = inflater.inflate(R.layout.fragment_direct_control, container, false);
		
		INSTANCE = this;
		
		droneControl = ((MainActivity)getActivity()).getDroneControl();

		((ImageButton)rootView.findViewById(R.id.button_direct_forward)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_backward)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_left)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_right)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_up)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_down)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_rotate_left)).setOnTouchListener(this);
		((ImageButton)rootView.findViewById(R.id.button_direct_rotate_right)).setOnTouchListener(this);
		
		textViewAltitude = (TextView)rootView.findViewById(R.id.text_view_altitude);
		textViewState = (TextView)rootView.findViewById(R.id.text_view_state);
		textViewBattery = (TextView)rootView.findViewById(R.id.text_view_battery);
		
		setAltitude(0);
		setState("Disconnected");
		setBattery(0);
		
		return rootView;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			droneControl.trim();
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			switch(v.getId()){
			case R.id.button_direct_forward:
				droneControl.forward();
				break;
			case R.id.button_direct_backward:
				droneControl.backward();
				break;
			case R.id.button_direct_left:
				droneControl.left();
				break;
			case R.id.button_direct_right:
				droneControl.right();
				break;
			case R.id.button_direct_up:
				droneControl.up();
				break;
			case R.id.button_direct_down:
				droneControl.down();
				break;
			case R.id.button_direct_rotate_left:
				droneControl.rotateLeft();
				break;
			case R.id.button_direct_rotate_right:
				droneControl.rotateRight();
				break;
			}
		}
		return false;
	}
	
	@Override
	public void setAltitude(float altitude) {
		String text = getResources().getString(R.string.text_view_altitude);
		textViewAltitude.setText(text + " " + altitude + " m");
	}

	@Override
	public void setState(String state) {
		String text = getResources().getString(R.string.text_view_state);
		textViewState.setText(text + " " +state);
	}
	
	@Override
	public void setBattery(int battery) {
		String text = getResources().getString(R.string.text_view_battery);
		textViewBattery.setText(text + " " + battery + " %");
	}
}
