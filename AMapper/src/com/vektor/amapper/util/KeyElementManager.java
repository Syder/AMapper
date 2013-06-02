package com.vektor.amapper.util;

import java.util.HashMap;
import java.util.Map;

import android.view.View;

import com.vektor.amapper.elements.KeyElement;

public class KeyElementManager {
	private Map<Integer, KeyElement> keys;
	private View v;

	// private SourceDevice
	// private DestDevice

	public KeyElementManager(View v) {
		this.v = v;
		this.keys = new HashMap<Integer, KeyElement>();
	}

	public void add(int keyCode) {
		keys.put(keyCode, new KeyElement(keyCode, v));
	}

	public KeyElement get(int keyCode) {
		return keys.get(keyCode);
	}

	public void remove(int keyCode) {
		if (null != keys.get(keyCode))
			keys.remove(keyCode);
	}

	public void update(int keyCode) {
		if (null != keys.get(keyCode)) {
			remove(keyCode);
			add(keyCode);
		}
	}

}
