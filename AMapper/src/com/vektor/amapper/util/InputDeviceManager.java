package com.vektor.amapper.util;

import java.io.File;
import java.util.ArrayList;
import android.util.Log;

public class InputDeviceManager {
	private ArrayList<MapperDevice> devices;
	private boolean uInputLoaded;
	static {
		System.loadLibrary("EventInjector");
		System.loadLibrary("Vektor");
	}

	public InputDeviceManager() {
		/*
		if (new File("/dev/uinput").exists()){
			Log.i("Uinput Exists","Yes");
			ShInterface.execute("chmod 777 /dev/uinput");
			this.uInputLoaded = true;
		}
		else {
			this.uInputLoaded = loadUinput();
			ShInterface.execute("chmod 777 /dev/uinput");
			DestroyVirtualDevice();
		}
		int a = CreateVirtualDevice("NCCE_Virtual_Dev");
		Log.i("libVektor", "Result:" + a);
			this.devices = new ArrayList<MapperDevice>();
			int devs = this.Init();
			Log.i("AMapper","I can see "+devs+" devices:");
			boolean b = ShInterface.canSu();
			for(int i=0;i<devs;i++){
				MapperDevice current = devices.get(i);
				if(current.Open(b)){
					Log.i("AMapper",i+1+")"+current.getName()+","+current.getPath());
				}
				
			}*/
	}
	
	public int findDevice(String name){
		Release();
		Init();
		boolean b = ShInterface.canSu();
		for(int i=0;i<ScanFiles();i++){
			MapperDevice current = devices.get(i);
			if(current.Open(b)){
				if (current.getName().equals(name)) return current.getId();
			}
		}
		return -1;
	}
	
	private boolean loadUinput() {
		boolean a = ShInterface.execute("insmod /system/lib/modules/uinput.ko");
		boolean b = ShInterface.execute("modprobe uinput");
		boolean c = ShInterface.execute("chmod 777 /dev/uinput");
		Log.i("Results","Insmod= "+a+" Probe="+b+" Chmod="+c);
		return (a&&b&&c);
	}

	public int Init() {
		this.devices.clear();
		int n = ScanFiles();
		for (int i = 0; i < n; i++) {
			devices.add(new MapperDevice(i, getDevPath(i)));
		}
		return n;
	}

	public void Release() {
		for (MapperDevice dev : devices) {
			dev.Close();
		}
	}

	public native static void intEnableDebug(int enable);

	private native static int ScanFiles();

	private native static int OpenDev(int devid);

	private native static int RemoveDev(int devid);

	private native static String getDevPath(int devid);

	private native static String getDevName(int devid);

	private native static int PollDev(int devid);

	private native static int getType();

	private native static int getCode();

	private native static int getValue();

	private native static int intSendEvent(int devid, int type, int code,
			int value);

	private native static int CreateVirtualDevice(String name);
	private native static int DestroyVirtualDevice();

	public class MapperDevice {

		final int EV_KEY = 0x01, EV_REL = 0x02, EV_ABS = 0x03, REL_X = 0x00,
				REL_Y = 0x01, REL_Z = 0x02, BTN_TOUCH = 0x14a;

		private int m_nId;
		private String m_szPath, m_szName;
		private boolean m_bOpen;

		public MapperDevice(int id, String path) {
			m_nId = id;
			m_szPath = path;
		}

		public int InjectEvent() {
			return 0;
		}

		public int getPollingEvent() {
			return PollDev(m_nId);
		}

		public int getSuccesfulPollingType() {
			return getType();
		}

		public int getSuccesfulPollingCode() {
			return getCode();
		}

		public int getSuccesfulPollingValue() {
			return getValue();
		}

		public boolean getOpen() {
			return this.m_bOpen;
		}

		public int getId() {
			return this.m_nId;
		}

		public String getPath() {
			return this.m_szPath;
		}

		public String getName() {
			return this.m_szName;
		}

		public void Close() {
			this.m_bOpen = false;
			RemoveDev(this.m_nId);
		}

		public int SendKey(int key, boolean state) {
			if (state)
				return intSendEvent(m_nId, EV_KEY, key, 1);
			else
				return intSendEvent(m_nId, EV_KEY, key, 0);
		}

		public int SendTouchButton(boolean state) {
			if (state) {
				intSendEvent(m_nId, EV_ABS, 24, 100);
				intSendEvent(m_nId, EV_ABS, 28, 1);
				intSendEvent(m_nId, 1, 330, 1);
			} else {
				intSendEvent(m_nId, EV_ABS, 24, 0);
				intSendEvent(m_nId, EV_ABS, 28, 0);
				intSendEvent(m_nId, 1, 330, 0);
			}
			return 1;
		}

		public int SendTouchAbsCoord(int x, int y) {
			intSendEvent(m_nId, EV_ABS, REL_X, x); // set x coord
			intSendEvent(m_nId, EV_ABS, REL_Y, y); // set y coord
			intSendEvent(m_nId, EV_ABS, 53, x);
			intSendEvent(m_nId, EV_ABS, 54, y);
			intSendEvent(m_nId, EV_ABS, 48, 100);
			intSendEvent(m_nId, EV_ABS, 50, 0);
			intSendEvent(m_nId, 0, 2, 0);
			intSendEvent(m_nId, 0, 2, 0);
			intSendEvent(m_nId, 0, 0, 0);
			return 0;
		}

		public int SendTouchDownAbs(int x, int y) {
			intSendEvent(m_nId, EV_ABS, REL_X, x); // set x coord
			intSendEvent(m_nId, EV_ABS, REL_Y, y); // set y coord
			intSendEvent(m_nId, EV_ABS, 24, 100);
			intSendEvent(m_nId, EV_ABS, 28, 1);
			intSendEvent(m_nId, 1, 330, 1); // touch down
			intSendEvent(m_nId, EV_ABS, 53, x);
			intSendEvent(m_nId, EV_ABS, 54, y);
			intSendEvent(m_nId, EV_ABS, 48, 100);
			intSendEvent(m_nId, EV_ABS, 50, 0);
			intSendEvent(m_nId, 0, 2, 0);
			intSendEvent(m_nId, 0, 2, 0);
			intSendEvent(m_nId, 0, 0, 0);
			intSendEvent(m_nId, EV_ABS, 24, 0);
			intSendEvent(m_nId, EV_ABS, 28, 0);
			intSendEvent(m_nId, 1, 330, 0); // touch up
			intSendEvent(m_nId, EV_ABS, 53, 0);
			intSendEvent(m_nId, EV_ABS, 54, 0);
			intSendEvent(m_nId, EV_ABS, 48, 0);
			intSendEvent(m_nId, EV_ABS, 50, 0);
			intSendEvent(m_nId, 0, 2, 0);
			intSendEvent(m_nId, 0, 2, 0);
			intSendEvent(m_nId, 0, 0, 0);
			return 1;
		}

		public int SendTouchDown(int x, int y, int screenW, int screenH) {
			int absx = (screenW * x) / screenW;
			int absy = (screenH * y) / screenH;
			return SendTouchDownAbs(absx, absy);
		}

		/**
		 * function Open : opens an input event node
		 * 
		 * @param forceOpen
		 *            will try to set permissions and then reopen if first open
		 *            attempt fails
		 * @return true if input event node has been opened
		 */
		public boolean Open(boolean forceOpen) {
			int res = OpenDev(m_nId);
			// if opening fails, we might not have the correct permissions, try
			// changing 660 to 666
			if (res != 0) {
				//boolean su = ShInterface.canSu();
				// possible only if we have root
				if (forceOpen) {
					// set new permissions
					ShInterface.execute("chmod 666 " + m_szPath);
					// reopen
					res = OpenDev(m_nId);
				}
			}
			m_szName = getDevName(m_nId);
			m_bOpen = (res == 0);
			// debug
			//Log.d("KeyMapper", "Open:" + m_szPath + " Name:" + m_szName
			//		+ " Result:" + m_bOpen);
			// done, return
			return m_bOpen;
		}
	}

}
