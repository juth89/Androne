package de.dhbw.androne.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import de.dhbw.androne.control.DroneControl;
import de.dhbw.androne.view.adapter.TabsPagerAdapter;
import de.dhbw.androne.view.viewpager.NonSwipeableViewPager;

public class MainActivity extends FragmentActivity implements TabListener {

	private NonSwipeableViewPager viewPager;
	private TabsPagerAdapter tabsPagerAdapter;
	private ActionBar actionBar;
	private Menu menu;
	
	private String[] tabNames = { "Direct", "Shape", "Polygon" };
	
	private DroneControl droneControl;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewPager = (NonSwipeableViewPager) findViewById(R.id.pager);
		tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		actionBar = getActionBar();
		
		viewPager.setAdapter(tabsPagerAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setSubtitle("Status: Disconnected");

		for(String tabName : tabNames) {
			actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
		}
		
		droneControl = new DroneControl(this);
		Thread droneControlThread = new Thread(droneControl);
		droneControlThread.start();
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		this.menu = menu;
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_connect:

			if(item.getTitle().equals("Connect")) {
				item.setTitle(R.string.action_disconnect);
				setDroneStatus("Connecting...");
				droneControl.connect();
				
			} else {
				item.setTitle(R.string.action_connect);
				setDroneStatus("Disconnecting...");
				droneControl.disconnect();
			}
			
			return false;
			
		case R.id.action_take_off:
			
			if(item.getTitle().equals("Take Off")) {
				item.setTitle(R.string.action_land);
				droneControl.takeOff();
			} else {
				item.setTitle(R.string.action_take_off);
				droneControl.land();
			}
					

			return false;
			
		case R.id.action_settings:
			
			return false;
		default:
			super.onOptionsItemSelected(item);
		}
		
        return super.onOptionsItemSelected(item);
	}

	
	public void setDroneStatus(final String status) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				actionBar.setSubtitle("Status: " + status);
			}
		});
	}
	
	
	public void setConnectButtonTitle(final String title) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MenuItem menuItem = menu.getItem(0);
				menuItem.setTitle(title);
			}
		});
	}
	
	
	public void showToast(final String toastMessage) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG);
				toast.show();
			}
		});
	}
	
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}

	
	/**
	 * Getter and setter
	 */
	
	public DroneControl getDroneControl() {
		return droneControl;
	}
	
	
	public void setBattery(final int battery) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((DirectControlFragment)tabsPagerAdapter.getItem(0)).setBattery(battery);
			}
		});
	}
	
	
	public void setAltitude(final float altitude) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((DirectControlFragment)tabsPagerAdapter.getItem(0)).setAltitude(altitude);
			}
		});
	}
}
