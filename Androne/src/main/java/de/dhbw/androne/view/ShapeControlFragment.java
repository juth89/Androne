package de.dhbw.androne.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import de.dhbw.androne.view.shape.CircleShapeView;
import de.dhbw.androne.view.shape.CircleValuePicker;
import de.dhbw.androne.view.shape.RectangleShapeView;
import de.dhbw.androne.view.shape.RectangleValuePicker;
import de.dhbw.androne.view.shape.ShapeView;
import de.dhbw.androne.view.shape.TriangleShapeView;
import de.dhbw.androne.view.shape.TriangleValuePicker;
import de.dhbw.androne.view.shape.ValuePicker;

public class ShapeControlFragment extends ControlFragment implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener {

	private ShapeView currentShapeView;
	private RectangleShapeView rectangleShapeView;
	private TriangleShapeView triangleShapeView;
	private CircleShapeView circleShapeView;
	
	private ValuePicker currentValuePicker;
	private RectangleValuePicker rectangleValuePicker;
	private TriangleValuePicker triangleValuePicker;
	private CircleValuePicker circleValuePicker;

	

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_shape_control, container, false);

		rectangleShapeView = (RectangleShapeView)view.findViewById(R.id.rectangle_shape_view);
		triangleShapeView = (TriangleShapeView)view.findViewById(R.id.triangle_shape_view);
		circleShapeView = (CircleShapeView)view.findViewById(R.id.circle_shape_view);
		
		rectangleValuePicker = new RectangleValuePicker();
		triangleValuePicker = new TriangleValuePicker();
		circleValuePicker = new CircleValuePicker();

		rectangleValuePicker.setShapeControlFragment(this);
		rectangleValuePicker.setShapeView(rectangleShapeView);
		triangleValuePicker.setShapeControlFragment(this);
		triangleValuePicker.setShapeView(triangleShapeView);
		circleValuePicker.setShapeControlFragment(this);
		circleValuePicker.setShapeView(circleShapeView);
		
		Spinner shapeChooser = (Spinner)view.findViewById(R.id.shape_chooser);
		shapeChooser.setOnItemSelectedListener(this);
		
		CheckBox checkChangeDirection = (CheckBox)view.findViewById(R.id.shape_check_change_direction);
		checkChangeDirection.setOnCheckedChangeListener(this);

		Button showValuePicker = (Button)view.findViewById(R.id.shape_button_show_value_picker);
		showValuePicker.setOnClickListener(this);
		
		return view;
	}

	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.shape_button_show_value_picker) {
			currentValuePicker.show(getFragmentManager(), null);
		}
	}
	
	
	public int getVerticalLineCount() {
		return currentShapeView.getVerticalLineCount();
	}
	
	
	public int getHorizontalLineCount() {
		return currentShapeView.getHorizontalLineCount();
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		rectangleShapeView.setVisibility(View.INVISIBLE);
		triangleShapeView.setVisibility(View.INVISIBLE);
		circleShapeView.setVisibility(View.INVISIBLE);
		
		Object item = parent.getItemAtPosition(pos);
		
		if(item.equals("Rectangle")) {
			currentShapeView = rectangleShapeView;
			currentValuePicker = rectangleValuePicker;
		} else if(item.equals("Circle")) {
			currentShapeView = circleShapeView;
			currentValuePicker = circleValuePicker;
		} else if(item.equals("Triangle")) {
			currentShapeView = triangleShapeView;
			currentValuePicker = triangleValuePicker;
		}
		
		currentShapeView.setVisibility(View.VISIBLE);
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}


	@Override
	public void onCheckedChanged(CompoundButton cp, boolean value) {
		rectangleShapeView.setDirection(value);
		triangleShapeView.setDirection(value);
		circleShapeView.setDirection(value);
	}


}
