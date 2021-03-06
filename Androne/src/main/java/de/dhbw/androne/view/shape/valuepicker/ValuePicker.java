package de.dhbw.androne.view.shape.valuepicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import de.dhbw.androne.view.ShapeControlFragment;

public abstract class ValuePicker extends DialogFragment {
	
	private static final String POSITIVE_BUTTON_TEXT = "Set";
	private static final String NEGATIVE_BUTTON_TEXT = "Cancel";
	
	protected ShapeControlFragment shapeControlFragment;
	protected AlertDialog.Builder builder;
	protected LayoutInflater inflater;
	
	
	public void setShapeControlFragment(ShapeControlFragment shapeControlFragment) {
		this.shapeControlFragment = shapeControlFragment;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		builder = new AlertDialog.Builder(getActivity());
		
		builder.setPositiveButton(POSITIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				positiveOnClick();
			}
		});
		
		builder.setNegativeButton(NEGATIVE_BUTTON_TEXT, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				negativeOnClick();
			}
		});
		
		inflater = getActivity().getLayoutInflater();
		
		View view = createDialog();
		builder.setView(view);
		
		return builder.create();
	}
	
	
	protected abstract View createDialog();
	
	protected abstract void positiveOnClick();

	protected abstract void negativeOnClick();
	
}
