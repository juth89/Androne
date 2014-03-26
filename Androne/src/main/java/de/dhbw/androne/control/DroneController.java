package de.dhbw.androne.control;

import java.io.IOException;
import java.net.UnknownHostException;

import android.util.Log;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.ARDrone.State;
import com.codeminders.ardrone.DroneStatusChangeListener;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;
import com.codeminders.ardrone.data.navdata.FlyingState;

import de.dhbw.androne.control.values.DroneCommand;
import de.dhbw.androne.model.Shape;
import de.dhbw.androne.view.MainActivity;

public class DroneController implements Runnable, NavDataListener, DroneStatusChangeListener {

	private static final String TAG = "DroneController";
	
	private boolean running = true;

	private static final long SLEEP_TIME = 5;
	private static final long CONNECT_TIMEOUT = 10000;
	
	private MainActivity mainActivity;
	
	/**
	 * Sub Controllers
	 */
	private DirectController directController;
	private ShapeController shapeController;
	private PolygonController polygonController;
	
	/**
	 * Drone Data
	 */
	private ARDrone drone;
	private boolean ready;
	private FlyingState flyingState;
	private float altitude;
	private int battery;
	
	private DroneCommand droneCommand;
	
	/**
	 * Shape and polygon holders
	 */
	private Shape shape;
	
	
	public DroneController(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		
		this.initDrone();
		
		directController = new DirectController(drone);
		shapeController = new ShapeController(drone);
		polygonController = new PolygonController(drone);
	}
	
	
	private void initDrone() {
		try {
			drone = new ARDrone();
		} catch (UnknownHostException e) {
			Log.e(TAG, e.getMessage());
		}
		drone.addNavDataListener(this);
		drone.addStatusChangeListener(this);
	}

	
	public void stopThread() {
		running = false;
	}
	
	
	@Override
	public void run() {
		while(running) {
			
			updateLoop();
			
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}
	
	
	private void updateLoop() {
		if(drone.getState() == State.DISCONNECTED) {
			if(droneCommand == DroneCommand.CONNECT) {
				connect();
			}
		} else if(drone.getState() == State.DEMO) {
			if(flyingState == FlyingState.LANDED) {
				if(droneCommand == DroneCommand.DISCONNECT) {
					disconnect();
				} else if(droneCommand == DroneCommand.TAKE_OFF) {
					takeOff();
				}
			} else if(flyingState == FlyingState.FLYING) {
				if(droneCommand == DroneCommand.DISCONNECT) {
					disconnect();
					land();
				} else if(droneCommand == DroneCommand.LAND) {
					land();
				} else if(droneCommand == DroneCommand.FORWARD) {
					directController.forward();
				} else if(droneCommand == DroneCommand.BACKWARD) {
					directController.backward();
				} else if(droneCommand == DroneCommand.LEFT) {
					directController.left();
				} else if(droneCommand == DroneCommand.RIGHT) {
					directController.right();
				} else if(droneCommand == DroneCommand.UP) {
					directController.up();
				} else if(droneCommand == DroneCommand.DOWN) {
					directController.down();
				} else if(droneCommand == DroneCommand.ROTATE_LEFT) {
					directController.rotateLeft();
				} else if(droneCommand == DroneCommand.ROTATE_RIGHT) {
					directController.rotateRight();
				} else if(droneCommand == DroneCommand.FLY_CURRENT_SHAPE) {
					shapeController.flyShape(shape);
				} else {
					try {
						drone.hover();
					} catch (IOException e) {
						Log.e(TAG, e.getMessage());
					}
				}
			}
		} else if(drone.getState() == State.ERROR) {
			
		}
	}
	
	
	private void connect() {
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

		} catch(IOException e) {
			Log.e(TAG, "CONNECT", e);
			droneCommand = DroneCommand.DISCONNECTED;
			initDrone();
		}
	}
	
	
	private void disconnect() {
		try {
			drone.disconnect();
			
		} catch (IOException e) {
			Log.e(TAG, "DISCONNECT", e);
		}
	}
	
	
	private void takeOff() {
		try {
			drone.takeOff();
			droneCommand = DroneCommand.TRIM;
		} catch (IOException e) {
			Log.e(TAG, "Unable to take off", e);
		}
	}
	
	
	private void land() {
		try {
			drone.land();
			Thread.sleep(5000);
		} catch (IOException e) {
			Log.e(TAG, "Unable to land", e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Call this method to interact asynchron with the drone
	 */
	public void setCommand(DroneCommand droneCommand) {
		this.droneCommand = droneCommand;
	}
	
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	
	/**
	 * DroneStatusChangeListener
	 */
	@Override
	public void ready() {
		this.ready = true;
	}

	
	/**
	 * NavDataListener
	 */
	@Override
	public void navDataReceived(NavData nd) {
		flyingState = nd.getFlyingState();
		altitude = nd.getAltitude();
		battery = nd.getBattery();
	}

	
	/**
	 * Getter and Setter
	 */
	public State getState() {
		return drone.getState();
	}
	
	
	public FlyingState getFlyingState() {
		return flyingState;
	}
	
	
	public float getAltitude() {
		return altitude;
	}
	
	
	public int getBattery() {
		return battery;
	}
	
	
	public boolean isFlyingShape() {
		return shapeController.isFlyingShape();
	}
}
