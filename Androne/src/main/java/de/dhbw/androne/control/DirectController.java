package de.dhbw.androne.control;

import java.io.IOException;

import android.util.Log;

import com.codeminders.ardrone.ARDrone;

public class DirectController {

private static final String TAG = "DirectController";
	
	private static final float LEFT_RIGHT_TILT_SPEED = 1;
	private static final float FRONT_BACK_TILT_SPEED = 1;
	private static final float VERTICAL_SPEED = 1;
	private static final float ANGULAR_SPEED = 1;
	
	private ARDrone drone;

	
	public DirectController(ARDrone drone) {
		this.drone = drone;
	}

	
	public void forward() {
		try {
			drone.move(0, -FRONT_BACK_TILT_SPEED, 0, 0);
		} catch (IOException e) {
			Log.e(TAG, "Forward problem", e);
		}
	}
	
	
	public void backward() {
		try {
			drone.move(0, FRONT_BACK_TILT_SPEED, 0, 0);
		} catch (IOException e) {
			Log.e(TAG, "Backward problem", e);
		}
	}
	
	
	public void left() {
		try {
			drone.move(-LEFT_RIGHT_TILT_SPEED, 0, 0, 0);
		} catch (IOException e) {
			Log.e(TAG, "Left problem", e);
		}
		
	}
	
	
	public void right() {
		try {
			drone.move(LEFT_RIGHT_TILT_SPEED, 0, 0, 0);
		} catch (IOException e) {
			Log.e(TAG, "Right problem", e);
		}
	}
	
	
	public void up() {
		try {
			drone.move(0, 0, VERTICAL_SPEED, 0);
		} catch (IOException e) {
			Log.e(TAG, "Up problem", e);
		}
	}
	
	
	public void down() {
		try {
			drone.move(0, 0, -VERTICAL_SPEED, 0);
		} catch (IOException e) {
			Log.e(TAG, "Down problem", e);
		}
	}
	
	
	public void rotateLeft() {
		try {
			drone.move(0, 0, 0, -ANGULAR_SPEED);
		} catch (IOException e) {
			Log.e(TAG, "Rotate left problem", e);
		}
	}
	
	
	public void rotateRight() {
		try {
			drone.move(0, 0, 0, ANGULAR_SPEED);
		} catch (IOException e) {
			Log.e(TAG, "Rotate right problem", e);
		}
	}
	
	
	public void stay() {
		try {
			drone.move(0, 0, 0, 0);
			drone.hover();
		} catch (IOException e) {
			Log.e(TAG, "Stay problem", e);
		}
	}

}
