package de.dhbw.androne.view.updater;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codeminders.ardrone.ARDrone.State;
import com.codeminders.ardrone.data.navdata.FlyingState;

import de.dhbw.androne.control.DroneController;
import de.dhbw.androne.view.ControlFragment;
import de.dhbw.androne.view.DirectControlFragment;
import de.dhbw.androne.view.MainActivity;
import de.dhbw.androne.view.PolygonControlFragment;
import de.dhbw.androne.view.R;
import de.dhbw.androne.view.ShapeControlFragment;
import de.dhbw.androne.view.adapter.TabsPagerAdapter;

public class ControlUpdater implements Runnable {

	private static final String TAG = "ControlUpdater";
	
	private static final long UPDATE_INTERVAL = 100;
	
	private boolean running = true;
	
	private MenuItem connectItem, takeOffItem;
	private DroneController droneController;
	private TabsPagerAdapter tabsPagerAdapter;

	private MainActivity mainActivity;


	public ControlUpdater(TabsPagerAdapter tabsPagerAdapter, DroneController droneController, MainActivity mainActivity) {
		this.tabsPagerAdapter = tabsPagerAdapter;
		this.droneController = droneController;
		this.mainActivity = mainActivity;
	}
	
	
	public void setMenu(Menu menu) {
		connectItem = menu.getItem(0);
		takeOffItem = menu.getItem(1);
	}
	
	
	public void updateMenu() {
		if(null == connectItem || null == takeOffItem) {
			return;
		}
		State state = droneController.getState();
		FlyingState flyingState = droneController.getFlyingState();

		
		if(State.CONNECTING == state) {
			connectItem.setEnabled(false);
			connectItem.setTitle(R.string.action_connect);
			
			takeOffItem.setEnabled(false);
			takeOffItem.setTitle(R.string.action_take_off);
			
		} else if(State.DEMO == state) {
			connectItem.setEnabled(true);
			connectItem.setTitle(R.string.action_disconnect);
			
			if(null != flyingState) {
				if(FlyingState.FLYING == flyingState) {
					takeOffItem.setEnabled(true);
					takeOffItem.setTitle(R.string.action_land);
				} else if(FlyingState.LANDED == flyingState) {
					takeOffItem.setEnabled(true);
					takeOffItem.setTitle(R.string.action_take_off);
				} else if(FlyingState.LANDING == flyingState) {
					connectItem.setEnabled(false);
					connectItem.setTitle(R.string.action_disconnect);
					takeOffItem.setEnabled(false);
					takeOffItem.setTitle(R.string.action_land);
				} else if(FlyingState.TAKING_OFF == flyingState) {
					connectItem.setEnabled(false);
					connectItem.setTitle(R.string.action_disconnect);
					takeOffItem.setEnabled(false);
					takeOffItem.setTitle(R.string.action_take_off);
				}
			}
			
		} else if(State.DISCONNECTED == state) {
			connectItem.setEnabled(true);
			connectItem.setTitle(R.string.action_connect);
			
			takeOffItem.setEnabled(false);
			takeOffItem.setTitle(R.string.action_take_off);
		}
	}
	
	
	


	@Override
	public void run() {
		while(running) {
			mainActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					updateDrone();
				}
			});
			
			try {
				Thread.sleep(UPDATE_INTERVAL);
			} catch (InterruptedException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}
	
	
	private void updateDrone() {
		Fragment currentFragment = tabsPagerAdapter.getCurrentFragment();
		ControlFragment controlFragment = null;

		if(currentFragment instanceof ControlFragment) {
			controlFragment = (ControlFragment) currentFragment;
		}

		updateControlFragment(controlFragment);
		
		State state = droneController.getState();
		FlyingState flyingState = droneController.getFlyingState();
			
		if(controlFragment instanceof DirectControlFragment) {
			updateDirectControlFragment((DirectControlFragment)controlFragment, state, flyingState);
		} else if(controlFragment instanceof ShapeControlFragment) {
			updateShapeControlFragment((ShapeControlFragment)controlFragment, state, flyingState);
		} else if(controlFragment instanceof PolygonControlFragment) {
			updatePolygonControlFragment((PolygonControlFragment)controlFragment, state, flyingState);
		}
	}
	
	
	private void updateControlFragment(final ControlFragment controlFragment) {
		if(null == controlFragment || !controlFragment.isVisible()) {
			return;
		}
		controlFragment.setAltitude(droneController.getAltitude());
		controlFragment.setState(droneController.getState().toString());
		controlFragment.setBattery(droneController.getBattery());	
	}
	
	
	private void updateDirectControlFragment(DirectControlFragment fragment, State state, FlyingState flyingState) {
		if(state != State.DEMO || droneController.isFlyingShape()) {
			fragment.disableAll();
		} else {
			fragment.enableAll();
		}
	}

	private void updateShapeControlFragment(final ShapeControlFragment fragment, final State state, FlyingState flyingState) {
		if(state != State.DEMO) {
			fragment.disableStartButton();
		} else {
			if(droneController.isFlyingShape()) {
				fragment.disableAll();
			}
			fragment.enableAll();
		}
	}
	
	private void updatePolygonControlFragment(PolygonControlFragment fragment, State state, FlyingState flyingState) {
		
	}
}
