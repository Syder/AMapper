package com.vektor.amapper.util;

import java.util.HashMap;
import java.util.Map;

import android.view.View;

public class MapperManager {
	private View v;
	private Map<String, KeyElementManager> map;
	private InputDeviceManager im;
	
	public MapperManager(View v){
		map = new HashMap<String, KeyElementManager>();
		im = new InputDeviceManager();
		this.v=v;
	}
	
	public boolean addMappedDevice(String name){
		if(im.findDevice(name)==-1) return false;
		if(map.get(name)!=null) return false;
		map.put(name, new KeyElementManager(v));
		return true;
	}
	
}
