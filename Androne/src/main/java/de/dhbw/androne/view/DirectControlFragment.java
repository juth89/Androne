package de.dhbw.androne.view;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import de.dhbw.androne.control.values.DroneCommand;

public class DirectControlFragment extends ControlFragment implements OnTouchListener {

	private List<ImageButton> buttons = new ArrayList<ImageButton>();
	
	
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_direct_control, container, false);
		
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_forward));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_backward));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_left));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_right));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_up));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_down));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_rotate_left));
		buttons.add((ImageButton)view.findViewById(R.id.direct_button_rotate_right));
		
		for(ImageButton button : buttons) {
			button.setOnTouchListener(this);
		}
		
		return view;
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			droneController.setCommand(DroneCommand.TRIM);
		}
		
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			switch(v.getId()){
			case R.id.direct_button_forward:
				droneController.setCommand(DroneCommand.FORWARD);
				break;
			case R.id.direct_button_backward:
				droneController.setCommand(DroneCommand.BACKWARD);
				break;
			case R.id.direct_button_left:
				droneController.setCommand(DroneCommand.LEFT);
				break;
			case R.id.direct_button_right:
				droneController.setCommand(DroneCommand.RIGHT);
				break;
			case R.id.direct_button_up:
				droneController.setCommand(DroneCommand.UP);
				break;
			case R.id.direct_button_down:
				droneController.setCommand(DroneCommand.DOWN);
				break;
			case R.id.direct_button_rotate_left:
				droneController.setCommand(DroneCommand.ROTATE_LEFT);
				break;
			case R.id.direct_button_rotate_right:
				droneController.setCommand(DroneCommand.ROTATE_RIGHT);
				break;
			}
		}
		return false;
	}

	
	public void enableAll() {
		for(ImageButton button : buttons) {
			button.setEnabled(true);
		}
	}
	
	
	public void disableAll() {
		for(ImageButton button : buttons) {
			button.setEnabled(false);
		}
		
	}
}
