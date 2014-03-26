package de.dhbw.androne.view.shape.valuepicker;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import de.dhbw.androne.view.R;
import de.dhbw.androne.view.shape.TriangleShapeView;

public class TriangleValuePicker extends ValuePicker {

	private int width, height, tmpWidth, tmpHeight;
	
	private static final int MIN_WIDTH = 2;
	private static final int MIN_HEIGHT = 2;

	private TriangleShapeView triangleShapeView;
	
	
	public void setShapeView(TriangleShapeView triangleShapeView) {
		this.triangleShapeView = triangleShapeView;
		width = triangleShapeView.getTriangleWidth();
		height = triangleShapeView.getTriangleHeight();
	}

	
	@Override
	protected View createDialog() {
		View view = inflater.inflate(R.layout.rectangle_value_picker_dialog, null);

		tmpWidth = width;
		tmpHeight = height;
		
		NumberPicker widthPicker = (NumberPicker)view.findViewById(R.id.shape_rect_tri_angle_width_picker);
		widthPicker.setMinValue(MIN_WIDTH);
		widthPicker.setMaxValue(shapeControlFragment.getVerticalLineCount() - 2);
		widthPicker.setWrapSelectorWheel(false);
		widthPicker.setLongClickable(false);
		widthPicker.setValue(triangleShapeView.getTriangleWidth());
		widthPicker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				width = arg2;
			}
		});

		NumberPicker heightPicker = (NumberPicker)view.findViewById(R.id.shape_rect_tri_angle_height_picker);
		heightPicker.setMinValue(MIN_HEIGHT);
		heightPicker.setMaxValue(shapeControlFragment.getHorizontalLineCount() - 2);
		heightPicker.setWrapSelectorWheel(false);
		heightPicker.setLongClickable(false);
		heightPicker.setValue(triangleShapeView.getTriangleHeight());
		heightPicker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				height = arg2;
			}
		});
		
		return view;
	}

	@Override
	protected void positiveOnClick() {
		triangleShapeView.setTriangle(width, height);
	}

	@Override
	protected void negativeOnClick() {
		width = tmpWidth;
		height = tmpHeight;
	}

}
