package de.dhbw.androne.view.shape;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import de.dhbw.androne.view.R;
import de.dhbw.androne.view.ShapeControlFragment;

public class NumPickerDialog extends DialogFragment {

	private int width = 5;
	private int height = 5;
	private int tmpWidth = width, tmpHeight = height;
	
	private static final int MIN_VALUE = 1;
	private ShapeControlFragment shapeControlFragment;
	
	
	public NumPickerDialog() {
		super();
	}
	
	
	public void setShapeControlFragment(ShapeControlFragment shapeControlFragment) {
		this.shapeControlFragment = shapeControlFragment;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.fragment_shape_width_height_picker, null);
		
		builder.setView(view);
		
		builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				width = tmpWidth;
				height = tmpHeight;
				shapeControlFragment.setRectangle(width, height);
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tmpWidth = width;
				tmpHeight = height;
			}
		});

		NumberPicker widthPicker = (NumberPicker)view.findViewById(R.id.fragment_shape_width_picker);
		widthPicker.setMinValue(MIN_VALUE);
		widthPicker.setMaxValue(shapeControlFragment.getVerticalLineCount() - 2);
		widthPicker.setWrapSelectorWheel(false);
		widthPicker.setLongClickable(false);
		widthPicker.setValue(width);
		widthPicker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				tmpWidth = arg2;
			}
		});

		NumberPicker heightPicker = (NumberPicker)view.findViewById(R.id.fragment_shape_height_picker);
		heightPicker.setMinValue(MIN_VALUE);
		heightPicker.setMaxValue(shapeControlFragment.getHorizontalLineCount() - 2);
		heightPicker.setWrapSelectorWheel(false);
		heightPicker.setLongClickable(false);
		heightPicker.setValue(height);
		heightPicker.setOnValueChangedListener(new OnValueChangeListener() {
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				tmpHeight = arg2;
			}
		});
		
		return builder.create();
	}
	
	
	public int getWidth() {
		return width;
	}
	
	
	public int getHeight() {
		return height;
	}
}
