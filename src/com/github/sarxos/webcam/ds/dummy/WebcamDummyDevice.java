package com.github.sarxos.webcam.ds.dummy;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.sarxos.webcam.WebcamDevice;
import com.github.sarxos.webcam.WebcamException;


public class WebcamDummyDevice implements WebcamDevice {

	private AtomicBoolean open = new AtomicBoolean(false);

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension[] getResolutions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getResolution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResolution(Dimension size) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getImage() {

		if(!isOpen()) {
			throw new WebcamException("Webcam is not open");
		}

		return null;
	}

	@Override
	public void open() {
		if(open.compareAndSet(false, true)) {
			// dodne
		}
	}

	@Override
	public void close() {
		if(open.compareAndSet(true, false)) {
			// dood
		}
	}

	@Override
	public void dispose() {
		close();
	}

	@Override
	public boolean isOpen() {
		return open.get();
	}
}
