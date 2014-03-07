package de.dhbw.androne.control;

import java.io.IOException;
import java.net.UnknownHostException;

import android.util.Log;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.DroneStatusChangeListener;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;
import com.codeminders.ardrone.data.navdata.FlyingState;

import de.dhbw.androne.view.MainActivity;

public class DroneControl implements Runnable, DroneStatusChangeListener, NavDataListener {
	
	private static final String TAG = "DroneControl";
	
	private MainActivity mainActivity;
	
	private static final long CONNECT_TIMEOUT = 10000;
	private static final long READ_UPDATE_DELAY_MS = 5;
	
	private boolean running = true;
	private ARDrone drone;
	private FlyingState flyingState;
	private int battery;
	private float altitude;
	
	private DroneCommand droneCommand = DroneCommand.DISCONNECTED;
	private DirectDroneControl directDroneControl;
	
	
	public DroneControl(MainActivity mainActivity) {
		this.mainActivity = mainActivity;

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
		while(running) {
			mainActivity.setDroneStatus(drone.getState().name());
			if(drone.getState() == ARDrone.State.DISCONNECTED) {
				if(droneCommand == DroneCommand.CONNECT) {
					connectImpl();
				}
			}
			if(drone.getState() == ARDrone.State.CONNECTING) {
				// do nothing
			}
			if(drone.getState() == ARDrone.State.TAKING_OFF) {
				// do nothing
			}
			if(drone.getState() == ARDrone.State.LANDING) {
				// do nothing
			}
			if(drone.getState() == ARDrone.State.DEMO) {
				if(flyingState == FlyingState.FLYING) {
					if(droneCommand == DroneCommand.TRIM) {
						trimImpl();
					}
					if(droneCommand == DroneCommand.FORWARD) {
						directDroneControl.forward();
					}
					if(droneCommand == DroneCommand.BACKWARD) {
						directDroneControl.backward();
					}
					if(droneCommand == DroneCommand.LEFT) {
						directDroneControl.left();
					}
					if(droneCommand == DroneCommand.RIGHT) {
						directDroneControl.right();
					}
					if(droneCommand == DroneCommand.UP) {
						directDroneControl.up();
					}
					if(droneCommand == DroneCommand.DOWN) {
						directDroneControl.down();
					}
					if(droneCommand == DroneCommand.ROTATE_LEFT) {
						directDroneControl.rotateLeft();
					}
					if(droneCommand == DroneCommand.ROTATE_RIGHT) {
						directDroneControl.rotateRight();
					}
					
					if(droneCommand == DroneCommand.DISCONNECT) {
						landImpl();
						disconnectImpl();
					}
				}
				if(flyingState == FlyingState.LANDED) {
					if(droneCommand == DroneCommand.DISCONNECT) {
						disconnectImpl();
					}
					if(droneCommand == DroneCommand.TAKE_OFF) {
						takeOffImpl();
					}
				}
			}
			if(drone.getState() == ARDrone.State.ERROR) {
			}
			
			Log.e(TAG, droneCommand.name());
			
			try {
				Thread.sleep(READ_UPDATE_DELAY_MS);
			} catch (InterruptedException e) {
				Log.e(TAG, "Thread sleep exception", e);
			}
		}
	}
	
	
	public void stopThread() {
		running = false;
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
		} catch(IOException e) {
			Log.e(TAG, "CONNECT", e);
			droneCommand = DroneCommand.DISCONNECTED;
			initDrone();
			mainActivity.setConnectButtonTitle("Connect");
			mainActivity.showToast("Unable to connect to drone!");
		}
	}
	
	
	private void disconnectImpl() {
		try {
			drone.disconnect();
			
			mainActivity.showToast("Successfully disconnected!");
		} catch (IOException e) {
			Log.e(TAG, "DISCONNECT", e);
			mainActivity.setConnectButtonTitle("Disconnect");
			mainActivity.showToast("Unable to disconnect from drone");
		}
	}
	
	
	private void takeOffImpl() {
		try {
			drone.takeOff();
			droneCommand = DroneCommand.TRIM;
		} catch (IOException e) {
			Log.e(TAG, "Unable to take off", e);
		}
	}
	
	
	private void landImpl() {
		try {
			drone.land();
		} catch (IOException e) {
			Log.e(TAG, "Unable to land", e);
		}
	}
	
	/**
	 * Drone Listeners
	 */
	@Override
	public void navDataReceived(NavData nd) {
		flyingState = nd.getFlyingState();
		battery = nd.getBattery();
		altitude = nd.getAltitude();
		
		mainActivity.setBattery(battery);
		mainActivity.setAltitude(altitude);
	}

	
	@Override
	public void ready() {
		mainActivity.setDroneStatus("Connected");
	}

}
