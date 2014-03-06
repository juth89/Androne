package de.dhbw.androne.control;

import java.io.IOException;
import java.net.UnknownHostException;

import android.util.Log;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.DroneStatusChangeListener;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;

import de.dhbw.androne.view.MainActivity;

public class DroneControl implements Runnable, DroneStatusChangeListener, NavDataListener {
	
	private static final String TAG = "DroneControl";
	
	private MainActivity mainActivity;
	
	private static final long CONNECT_TIMEOUT = 10000;
	private static final long READ_UPDATE_DELAY_MS = 5;
	
	private ARDrone drone;
	private DroneCommand droneCommand;
	private DirectDroneControl directDroneControl;
	
	
	public DroneControl(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		droneCommand = DroneCommand.DISCONNECTED;

		initDrone();
		
		directDroneControl = new DirectDroneControl(drone);
	}
	
	
	/**
	 * Drone initialization
	 */
	private void initDrone() {
		try {
			drone = new ARDrone();
		} catch (UnknownHostException e) {
			Log.e(TAG, "Unable to initalize", e);
		}
		drone.addNavDataListener(this);
		drone.addStatusChangeListener(this);
	}

	
	/**
	 * Drone commands
	 */
	public void trim() {
		droneCommand = DroneCommand.TRIM;
	}
	
	public void connect() {
		droneCommand = DroneCommand.CONNECT;
	}
	
	public void disconnect() {
		droneCommand = DroneCommand.DISCONNECT;
	}
	
	public void takeOff() {
		droneCommand = DroneCommand.TAKE_OFF;
	}
	
	public void land() {
		droneCommand = DroneCommand.LAND;
	}
	
	public void forward() {
		droneCommand = DroneCommand.FORWARD;
	}
	
	public void backward() {
		droneCommand = DroneCommand.BACKWARD;
	}

	public void left() {
		droneCommand = DroneCommand.LEFT;
	}
	
	public void right() {
		droneCommand = DroneCommand.RIGHT;
	}
	
	public void up() {
		droneCommand = DroneCommand.UP;
	}
	
	public void down() {
		droneCommand = DroneCommand.DOWN;
	}
	
	public void rotateLeft() {
		droneCommand = DroneCommand.ROTATE_LEFT;
	}
	
	public void rotateRight() {
		droneCommand = DroneCommand.ROTATE_RIGHT;
	}
	
	
	/**
	 * Update loop
	 */
	@Override
	public void run() {
		while(true) {
			switch(droneCommand) {
			
			case TRIM:
				trimImpl();
				break;
				
			case CONNECT:
				connectImpl();
				break;
			
			case CONNECTED:
				break;
				
			case DISCONNECT:
				disconnectImpl();
				break;
				
			case DISCONNECTED:
				break;
				
			case TAKE_OFF:
				takeOffImpl();
				break;
			
			case LAND:
				landImpl();
				break;
				
			case FORWARD:
				directDroneControl.forward();
				break;
				
			case BACKWARD:
				directDroneControl.backward();
				break;

			case LEFT:
				directDroneControl.left();
				break;
				
			case RIGHT:
				directDroneControl.right();
				break;
				
			case UP:
				directDroneControl.up();
				break;
				
			case DOWN:
				directDroneControl.down();
				break;
				
			case ROTATE_LEFT:
				directDroneControl.rotateLeft();
				break;
				
			case ROTATE_RIGHT:
				directDroneControl.rotateRight();
				break;
			}
	
			Log.e(TAG, droneCommand.name());
			
			try {
				Thread.sleep(READ_UPDATE_DELAY_MS);
			} catch (InterruptedException e) {
				Log.e(TAG, "Thread sleep exception", e);
			}
		}
	}


	/**
	 * Drone commands implementations
	 */
	private void trimImpl() {
		try {
			drone.trim();
		} catch (IOException e) {
			Log.e(TAG, "TRIM", e);
		}
	}
	
	
	private void connectImpl() {
		try {
			drone.addStatusChangeListener(new DroneStatusChangeListener() {
				
				@Override
				public void ready() {
					try {
						drone.disableVideo();
						drone.setCombinedYawMode(true);
						drone.trim();
					} catch (IOException e) {
						Log.e(TAG, "CONNECT LISTENER", e);
					}
				}
			});
			
			drone.connect();
			drone.waitForReady(CONNECT_TIMEOUT);
            drone.clearEmergencySignal();

            mainActivity.showToast("Successfully connected!");
            droneCommand = DroneCommand.CONNECTED;
		} catch(IOException e) {
			Log.e(TAG, "CONNECT", e);
			mainActivity.setConnectButtonTitle("Connect");
			mainActivity.setDroneStatus("Disconnected");
			mainActivity.showToast("Unable to connect to drone!");
			droneCommand = DroneCommand.DISCONNECTED;
		}
	}
	
	
	private void disconnectImpl() {
		try {
			drone.disconnect();
			
			mainActivity.showToast("Successfully disconnected!");
			droneCommand = DroneCommand.DISCONNECTED;
		} catch (IOException e) {
			Log.e(TAG, "DISCONNECT", e);
			mainActivity.setConnectButtonTitle("Disconnect");
			mainActivity.setDroneStatus("Connected");
			mainActivity.showToast("Unable to disconnect from drone");
		}
	}
	
	
	private void takeOffImpl() {
		try {
			drone.takeOff();
			Thread.sleep(3000);
			droneCommand = DroneCommand.TRIM;
		} catch (IOException e) {
			Log.e(TAG, "Unable to take off", e);
		} catch (InterruptedException e) {
			Log.e(TAG, "Unable to sleep", e);
		}
	}
	
	
	private void landImpl() {
		try {
			drone.land();
			Thread.sleep(3000);
			droneCommand = DroneCommand.CONNECTED;
		} catch (IOException e) {
			Log.e(TAG, "Unable to land", e);
		} catch (InterruptedException e) {
			Log.e(TAG, "Unable to sleep", e);
		}
	}
	
	/**
	 * Drone Listeners
	 */
	@Override
	public void navDataReceived(NavData nd) {
		
	}

	
	@Override
	public void ready() {
		mainActivity.setDroneStatus("Connected");
	}

}
