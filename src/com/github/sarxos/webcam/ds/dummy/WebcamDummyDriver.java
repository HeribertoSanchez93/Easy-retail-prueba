package com.github.sarxos.webcam.ds.dummy;

import java.util.List;

import com.github.sarxos.webcam.WebcamDevice;
import com.github.sarxos.webcam.WebcamDiscoverySupport;
import com.github.sarxos.webcam.WebcamDriver;


public class WebcamDummyDriver implements WebcamDriver, WebcamDiscoverySupport {

	@Override
	public long getScanInterval() {
		return 3000;
	}

	@Override
	public boolean isScanPossible() {
		return true;
	}

	@Override
	public List<WebcamDevice> getDevices() {
		return null;
	}

	@Override
	public boolean isThreadSafe() {
		return false;
	}
}
