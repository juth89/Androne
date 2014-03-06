package com.fabe.testflight;

import java.io.IOException;
import java.net.UnknownHostException;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.DroneStatusChangeListener;
import com.codeminders.ardrone.NavDataListener;
import com.codeminders.ardrone.ARDrone.VideoChannel;
import com.codeminders.ardrone.NavData;


public class DroneControl implements DroneStatusChangeListener, NavDataListener{

	private static final long CONNECT_TIMEOUT = 2000;
	private ARDrone drone;
	
	public DroneControl() {
		super();
	}
	
	public void init() throws UnknownHostException {
		drone = new ARDrone();
		drone.addStatusChangeListener(this);
		drone.addNavDataListener(this);
	}
	
	
	public void connect() throws IOException {
		drone.connect();
		drone.waitForReady(CONNECT_TIMEOUT);
		drone.clearEmergencySignal();
	}
	
	
	public void takeOff() throws IOException {
		drone.takeOff();
	}
	
	
	public void land() throws IOException {
		drone.land();
	}
	
	
	public void disconnect() throws IOException {
		drone.disconnect();
		drone = null;
	}
	
	
	@Override
	public void ready() {
		try {
			drone.selectVideoChannel(VideoChannel.HORIZONTAL_ONLY);
			drone.setCombinedYawMode(true);
			drone.trim();
		} catch (IOException e) {
			drone.changeToErrorState(e);
			e.printStackTrace();
		}
	}

	
	@Override
	public void navDataReceived(NavData navData) {
		System.out.println("Battery status: " + navData.getBattery());
		System.out.println("Drone is flying: " + navData.isFlying());
	}
	
	
	
}
