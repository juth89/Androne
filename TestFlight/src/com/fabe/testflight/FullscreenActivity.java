package com.fabe.testflight;

import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fabe.testflight.util.SystemUiHider;

/* @see SystemUiHider */
public class FullscreenActivity extends Activity 
{
	private SystemUiHider mSystemUiHider;
    private static final long CONNECT_TIMEOUT = 3000;
    
	private static final long IN_AIR_TIME = 10000;
	private static final long DISCONNECT_TIMEOUT = 2000;
    
    private DroneControl droneControl;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		setContentView(R.layout.activity_fullscreen);

		//final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() 
		{public void onClick(View view) {}});
			
			// Upon interacting with UI controls, delay any scheduled hide()
			// operations to prevent the jarring behavior of controls going away
			// while interacting with the UI.
			findViewById(R.id.BTstart_land).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTemergency).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTconnect).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTup).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTdown).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTforward).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTbackward).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTleft).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTright).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTrotate_left).setOnTouchListener(mDelayHideTouchListener);
			findViewById(R.id.BTrotate_right).setOnTouchListener(mDelayHideTouchListener);

			try {
				droneControl = new DroneControl();
				droneControl.init();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) 
	{
		super.onPostCreate(savedInstanceState);
	}

	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() 
	{
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) 
		{
			return false;
		}
	};

	public void onButtonClick (View view) //throws Exception
    {
    	Log.v("Testflight", "onButtonClick");
    	Toast toast = Toast.makeText(FullscreenActivity.this, "EMERCENCY", Toast.LENGTH_LONG);
    	
    	// Button Auswahl
    	switch(view.getId())
    	{
	    	case R.id.BTemergency: 	
	    		toast = Toast.makeText(FullscreenActivity.this, "EMERCENCY", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
    	
	    	case R.id.BTconnect:
	    		toast = Toast.makeText(FullscreenActivity.this, "CONNECT", Toast.LENGTH_LONG);
		    	toast.show();
		    	try {
		    		droneControl.connect();
		    		droneControl.takeOff();
		    		Thread.sleep(IN_AIR_TIME);
		    		droneControl.land();
		    		Thread.sleep(DISCONNECT_TIMEOUT);
		    		droneControl.disconnect();
		    	} catch(Exception e) {
		    		
		    	}
	    		break;
	    		
	    	case R.id.BTstart_land:
	    		toast = Toast.makeText(FullscreenActivity.this, "START_LAND", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTup:
	    		toast = Toast.makeText(FullscreenActivity.this, "UP", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTdown:
	    		toast = Toast.makeText(FullscreenActivity.this, "DOWN", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTforward:
	    		toast = Toast.makeText(FullscreenActivity.this, "FORWARD", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTbackward:
	    		toast = Toast.makeText(FullscreenActivity.this, "BACKWARD", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTleft: 
	    		toast = Toast.makeText(FullscreenActivity.this, "LEFT", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTright:
	    		toast = Toast.makeText(FullscreenActivity.this, "RIGHT", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTrotate_left:
	    		toast = Toast.makeText(FullscreenActivity.this, "ROTATE_LEFT", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	case R.id.BTrotate_right: 		
	    		toast = Toast.makeText(FullscreenActivity.this, "ROTATE_RIGHT", Toast.LENGTH_LONG);
		    	toast.show();
	    		break;
	    		
	    	default:
	    		break;
    	}
    }
}
