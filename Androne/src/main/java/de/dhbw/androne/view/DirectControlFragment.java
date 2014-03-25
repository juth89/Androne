package de.dhbw.androne.view;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DirectControlFragment extends ControlFragment implements OnTouchListener {

	
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_direct_control, container, false);
		
		((ImageButton)view.findViewById(R.id.direct_button_forward)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_backward)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_left)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_right)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_up)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_down)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_rotate_left)).setOnTouchListener(this);
		((ImageButton)view.findViewById(R.id.direct_button_rotate_right)).setOnTouchListener(this);
		
		return view;
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			droneControl.trim();
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			switch(v.getId()){
			case R.id.direct_button_forward:
				droneControl.forward();
				break;
			case R.id.direct_button_backward:
				droneControl.backward();
				break;
			case R.id.direct_button_left:
				droneControl.left();
				break;
			case R.id.direct_button_right:
				droneControl.right();
				break;
			case R.id.direct_button_up:
				droneControl.up();
				break;
			case R.id.direct_button_down:
				droneControl.down();
				break;
			case R.id.direct_button_rotate_left:
				droneControl.rotateLeft();
				break;
			case R.id.direct_button_rotate_right:
				droneControl.rotateRight();
				break;
			}
		}
		return false;
	}

}
