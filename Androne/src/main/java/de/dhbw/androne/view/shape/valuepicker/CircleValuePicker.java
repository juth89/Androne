package de.dhbw.androne.view.shape.valuepicker;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import de.dhbw.androne.view.R;
import de.dhbw.androne.view.shape.CircleShapeView;

public class CircleValuePicker extends ValuePicker {

	private int radius, tmpRadius;;
	
	private CircleShapeView circleShapeView;
	private static final int MIN_RADIUS = 2;
	
	public void setShapeView(CircleShapeView circleShapeView) {
		this.circleShapeView = circleShapeView;
		radius = circleShapeView.getCircleRadius();
	}
	
	
	@Override
	protected View createDialog() {
		View view = inflater.inflate(R.layout.circle_value_picker_dialog, null);
		
		tmpRadius = radius;
		
		NumberPicker radiusPicker = (NumberPicker)view.findViewById(R.id.shape_circle_radius_picker); 
		radiusPicker.setMinValue(MIN_RADIUS);
		radiusPicker.setMaxValue((shapeControlFragment.getHorizontalLineCount() - 2) / 2);
		radiusPicker.setWrapSelectorWheel(false);
		radiusPicker.setLongClickable(false);
		radiusPicker.setValue(circleShapeView.getCircleRadius());
		radiusPicker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				radius = arg2;
			}
		});
		
		return view;
	}

	@Override
	protected void positiveOnClick() {
		circleShapeView.setCircleRadius(radius);
	}

	@Override
	protected void negativeOnClick() {
		radius = tmpRadius;
	}

}
