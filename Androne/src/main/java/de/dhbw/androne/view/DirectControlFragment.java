package de.dhbw.androne.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import de.dhbw.androne.control.DroneControl;

public class DirectControlFragment extends Fragment implements OnTouchListener {

	public static DirectControlFragment INSTANCE;
	
	private DroneControl droneControl;
	
	
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
	
	
	public void setBattery(int battery) {
		TextView batteryTextView = (TextView)getView().findViewById(R.id.textViewBattery);
		batteryTextView.setText("Battery: " + battery + " %");
	}
	
	
	public void setAltitude(float altitude) {
		TextView altitudeTextView = (TextView)getView().findViewById(R.id.textViewAltitude);
		altitudeTextView.setText("Altitude: " + altitude + " m");
	}
}
