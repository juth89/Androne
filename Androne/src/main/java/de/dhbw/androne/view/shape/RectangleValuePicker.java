package de.dhbw.androne.view.shape;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import de.dhbw.androne.view.R;

public class RectangleValuePicker extends ValuePicker {

	private int width, height, tmpWidth, tmpHeight;

	private static final int MIN_WIDTH = 2;
	private static final int MIN_HEIGHT = 2;

	private RectangleShapeView rectangleShapeView;
	
	
	public void setShapeView(RectangleShapeView rectangleShapeView) {
		this.rectangleShapeView = rectangleShapeView;
		width = rectangleShapeView.getRectangleWidth();
		height = rectangleShapeView.getRectangleHeight();
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
		widthPicker.setValue(rectangleShapeView.getRectangleWidth());
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
		heightPicker.setValue(rectangleShapeView.getRectangleHeight());
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
		rectangleShapeView.setRectangle(width, height);
	}

	@Override
	protected void negativeOnClick() {
		width = tmpWidth;
		height = tmpHeight;
	}

}
