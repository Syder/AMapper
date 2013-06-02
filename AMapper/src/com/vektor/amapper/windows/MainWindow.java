package com.vektor.amapper.windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vektor.amapper.R;
import com.vektor.amapper.elements.KeyElement;
import com.vektor.amapper.elements.ProfileElement;
import com.vektor.amapper.util.InputDeviceManager;
import com.vektor.amapper.util.MapperManager;
import com.vektor.amapper.windows.ui.ProfileElementAdapter;
import com.vektor.amapper.windows.ui.ProfilesPanel;

import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressWarnings("deprecation")
public class MainWindow extends StandOutWindow implements OnTouchListener {
	private final String SHOWHIDE = "SH";
	private ProfilesPanel pnls;
	private Map<Integer, KeyElement> btns;
	private FrameLayout frame;
	private MapperManager mm;
	private KeyElement ke;

	@Override
	public String getAppName() {
		return "AMapper";
	}

	@Override
	public int getAppIcon() {
		return R.drawable.game_pad;
	}

	@Override
	public void createAndAttachView(int id, FrameLayout frame) {
		// create a new layout from body.xml
		this.btns = new HashMap<Integer,KeyElement>();
		this.frame=frame;
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.profiles_list, frame, true);
		LinearLayout profilesList = (LinearLayout) frame
				.findViewById(R.id.profiles_list_panel);
		this.pnls = new ProfilesPanel(profilesList);
		frame.setOnTouchListener(this);
		mm = new MapperManager(frame);
	      	
		ke = new KeyElement(KeyEvent.KEYCODE_BUTTON_R2, frame);
		//InputDeviceManager manager = new InputDeviceManager();
		/*
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(Color.RED);
		gd.setCornerRadius(10);
		gd.setShape(GradientDrawable.OVAL);
		gd.setStroke(2, Color.WHITE);
		
		Button b = (Button) frame.findViewById(R.id.button1);
		b.setBackground(gd);
		b.setWidth(50);
		b.setHeight(50);
		*/
	}

	// the window will be centered
	@Override
	public StandOutLayoutParams getParams(int id, Window window) {
		return new StandOutLayoutParams(id, StandOutLayoutParams.MATCH_PARENT,
				StandOutLayoutParams.MATCH_PARENT, StandOutLayoutParams.LEFT,
				StandOutLayoutParams.TOP);
	}

	// move the window by dragging the view
	@Override
	public int getFlags(int id) {
		return super.getFlags(id)
				| StandOutFlags.FLAG_WINDOW_EDGE_LIMITS_ENABLE
				| StandOutFlags.FLAG_WINDOW_HIDE_ENABLE
				| StandOutFlags.FLAG_WINDOW_BRING_TO_FRONT_ON_TAP
				| StandOutFlags.FLAG_ADD_FUNCTIONALITY_DROP_DOWN_DISABLE;
	}

	@Override
	public String getPersistentNotificationMessage(int id) {
		return "Click to Disable visibility";
	}

	@Override
	public Intent getPersistentNotificationIntent(int id) {
		// return StandOutWindow.getHideIntent(this, MainWindow.class, id);
		return new Intent(this, this.getClass()).putExtra("id", id).setAction(
				SHOWHIDE);
	}

	@Override
	public Notification getHiddenNotification(int id) {
		return null;
	}

	@Override
	public boolean onKeyEvent(int id, Window window, KeyEvent event) {
		/*
		// TODO Auto-generated method stub
		if(event.getAction() == event.ACTION_DOWN) {
			int kc = event.getKeyCode();
			Log.d("KeyPressed",KeyEvent.keyCodeToString(kc)+"="+kc);
			ke.press();
		}
		else if(event.getAction() == event.ACTION_UP){
			ke.press();
		}*/
		
		/*
		if(this.btns.get(event.getKeyCode())==null){
			this.btns.put(event.getKeyCode(), new KeyElement(event.getKeyCode(), this.frame));
		}
		*/
		return true;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int result = super.onStartCommand(intent, flags, startId);

		if (intent != null) {
			String action = intent.getAction();
			int id = intent.getIntExtra("id", DEFAULT_ID);

			if (SHOWHIDE.equals(action)) {
				Window window = getWindow(id);
				if (window != null) {
					if (window.visibility != Window.VISIBILITY_VISIBLE) {
						show(id);
					} else {
						hide(id);
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		Log.i("TOUCH",
				"Touch Registered X=" + arg1.getRawX() + " Y=" + arg1.getRawY());
		return false;
	}


}
