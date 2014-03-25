package de.dhbw.androne.view;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codeminders.ardrone.ARDrone.State;
import com.codeminders.ardrone.data.navdata.FlyingState;

import de.dhbw.androne.control.DroneControl;
import de.dhbw.androne.view.adapter.TabsPagerAdapter;

public class ControlUpdater {

	private MenuItem connectItem, takeOffItem;
	private DroneControl droneControl;
	private TabsPagerAdapter tabsPagerAdapter;


	public ControlUpdater(TabsPagerAdapter tabsPagerAdapter, DroneControl droneControl) {
		this.tabsPagerAdapter = tabsPagerAdapter;
		this.droneControl = droneControl;
	}
	
	
	public void setMenu(Menu menu) {
		connectItem = menu.getItem(0);
		takeOffItem = menu.getItem(1);
	}
	
	
	public void update() {
		updateMenu();
		updateDrone();
	}
	
	
	public void updateMenu() {
		if(null == connectItem || null == takeOffItem) {
			return;
		}
		State state = droneControl.getState();
		FlyingState flyingState = droneControl.getFlyingState();

		
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
	
	
	public void updateDrone() {
		Fragment currentFragment = tabsPagerAdapter.getCurrentFragment();
		ControlFragment controlFragment = null;

		if(currentFragment instanceof ControlFragment) {
			controlFragment = (ControlFragment) currentFragment;
		}
		
		if(null == controlFragment || !currentFragment.isVisible()) {
			return;
		}
		
		controlFragment.setAltitude(droneControl.getAltitude());
		controlFragment.setState(droneControl.getState().toString());
		controlFragment.setBattery(droneControl.getBattery());
	}
}
