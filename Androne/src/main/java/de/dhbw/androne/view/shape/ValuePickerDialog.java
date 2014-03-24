package de.dhbw.androne.view.shape;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import de.dhbw.androne.view.ShapeControlFragment;

public abstract class ValuePickerDialog extends DialogFragment {
	
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
		
		createDialog();
		
		return builder.create();
	}
	
	
	protected abstract void createDialog();
	
	protected abstract void positiveOnClick();

	protected abstract void negativeOnClick();
	
}
