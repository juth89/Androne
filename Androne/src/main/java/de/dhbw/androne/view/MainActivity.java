package de.dhbw.androne.view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import de.dhbw.androne.view.adapter.TabsPagerAdapter;
import de.dhbw.androne.view.viewpager.NonSwipeableViewPager;

public class MainActivity extends FragmentActivity implements TabListener {

	private NonSwipeableViewPager viewPager;
	private TabsPagerAdapter tabsPagerAdapter;
	private ActionBar actionBar;
	
	private String[] tabNames = { "Direct", "Shape", "Polygon" };
	
	
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
		
		for(String tabName : tabNames) {
			actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
		}
//		MenuItem item = (MenuItem) findViewById(R.id.action_connect);
//		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				if(item.getTitle().equals("Connect")) {
//					item.setTitle("Disconnect");
//				} else {
//					item.setTitle("Connect");
//				}
//				return false;
//			}
//		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_connect:

			if(item.getTitle().equals("Connect")) {
				item.setTitle(R.string.action_disconnect);
			} else {
				item.setTitle(R.string.action_connect);
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
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}

}
