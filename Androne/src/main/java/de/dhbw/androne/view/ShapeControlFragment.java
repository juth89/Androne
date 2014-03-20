package de.dhbw.androne.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import de.dhbw.androne.view.shape.NumPickerDialog;
import de.dhbw.androne.view.shape.ShapeView;

public class ShapeControlFragment extends Fragment implements DroneUpdater, OnClickListener {

	private NumPickerDialog numPickerDialog;
	private TextView textViewAltitude, textViewState, textViewBattery;
	private ShapeView shapeView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
		View rootView = inflater.inflate(R.layout.fragment_shape_control, container, false);
		
		numPickerDialog = new NumPickerDialog();
		numPickerDialog.setShapeControlFragment(this);
		
		textViewAltitude = (TextView)rootView.findViewById(R.id.text_view_altitude);
		textViewState = (TextView)rootView.findViewById(R.id.text_view_state);
		textViewBattery = (TextView)rootView.findViewById(R.id.text_view_battery);
		
		shapeView = (ShapeView)rootView.findViewById(R.id.surface_view);

		((Button)rootView.findViewById(R.id.shape_button_set_width_height)).setOnClickListener(this);
		
		return rootView;
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


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.shape_button_set_width_height) {
			numPickerDialog.show(getFragmentManager(), "width_height_dialog");
		}
	}
	
	
	public void setRectangle(int width, int height) {
		shapeView.setRectangle(width, height);
	}
	
	
	public int getVerticalLineCount() {
		return shapeView.getVerticalLineCount();
	}
	
	
	public int getHorizontalLineCount() {
		return shapeView.getHorizontalLineCount();
	}

}
