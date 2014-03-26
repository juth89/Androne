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
import de.dhbw.androne.control.DroneController;
import de.dhbw.androne.control.values.DroneCommand;
import de.dhbw.androne.view.adapter.TabsPagerAdapter;
import de.dhbw.androne.view.updater.ControlUpdater;
import de.dhbw.androne.view.viewpager.NonSwipeableViewPager;

public class MainActivity extends FragmentActivity implements TabListener {

	private NonSwipeableViewPager viewPager;
	private TabsPagerAdapter tabsPagerAdapter;
	private ActionBar actionBar;
	
	private String[] tabNames = { "Direct", "Shape", "Polygon" };
	
	private DroneController droneController;
	
	private ControlUpdater controlUpdater;
	
	
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
		
		droneController = new DroneController(this);
		Thread droneControllerThread = new Thread(droneController);
		droneControllerThread.start();
		
		controlUpdater = new ControlUpdater(tabsPagerAdapter, droneController, this);
		Thread controlUpdaterThread = new Thread(controlUpdater);
		controlUpdaterThread.start();
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		controlUpdater.setMenu(menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_connect:
			if(item.getTitle().equals(getResources().getString(R.string.action_connect))) {
				droneController.setCommand(DroneCommand.CONNECT);
			} else {
				droneController.setCommand(DroneCommand.DISCONNECT);
			}
			return false;
			
		case R.id.action_take_off:
			if(item.getTitle().equals(getResources().getString(R.string.action_take_off))) {
				droneController.setCommand(DroneCommand.TAKE_OFF);
			} else {
				droneController.setCommand(DroneCommand.LAND);
			}
			return false;
			
		case R.id.action_settings:
			
			return false;
		default:
			super.onOptionsItemSelected(item);
		}
		
        return super.onOptionsItemSelected(item);
	}

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		controlUpdater.updateMenu();
		return true;
	}
	
	
	@Override
	public void onDestroy() {
		droneController.stopThread();
		super.onDestroy();
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
		tabsPagerAdapter.setCurrentIndex(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}
	
	/**
	 * Getter and setter
	 */
	
	public DroneController getDroneControl() {
		return droneController;
	}

	
	public void updateDroneUi(Runnable runnable) {
		runOnUiThread(runnable);
	}
}
