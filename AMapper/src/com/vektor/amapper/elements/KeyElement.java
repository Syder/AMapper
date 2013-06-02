package com.vektor.amapper.elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.vektor.amapper.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;

@SuppressWarnings("deprecation")
public class KeyElement implements OnTouchListener {
	private int keyCode;
	private String btnname;
	private GradientDrawable idle, press;
	private boolean pressed;
	private View v;
	private Button btn;
	private AbsoluteLayout abs;

	public KeyElement(int keyCode, View v) {
		this.keyCode = keyCode;
		this.v = v;
		this.abs = (AbsoluteLayout) v.findViewById(R.id.mainbg);
		setButton();
		btn.setOnTouchListener(this);

	}

	public void press() {
		if (pressed) {
			btn.setBackground(idle);
			pressed = false;
		} else {
			btn.setBackground(press);
			pressed = true;
		}
	}

	private void setButton() {
		setButtonName();
		createButton();
		// posButton();
	}

	private void posButton() {
		AbsoluteLayout abs = (AbsoluteLayout) v.findViewById(R.id.mainbg);
		AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(
				btn.getWidth(), btn.getHeight(), abs.getWidth() / 2,
				abs.getHeight() / 2);
		btn.setLayoutParams(param);
		(abs).addView(btn);
	}

	public void setPressed(boolean press) {
		pressed = press;
	}

	public boolean isPressed() {
		return pressed;
	}

	private void setButtonName() {
		String[] btn = KeyEvent.keyCodeToString(keyCode).split("\\_");
		this.btnname = btn[btn.length - 1];
	}

	private void createButton() {
		int thr = 50;
		int red = (int) (Math.random() * 255) + 1;
		int green = (int) (Math.random() * 255) + 1;
		int blue = (int) (Math.random() * 255) + 1;

		int redp, greenp, bluep;
		if (red - thr < 0)
			redp = 0;
		else
			redp = red - thr;
		if (green - thr < 0)
			greenp = 0;
		else
			greenp = green - thr;
		if (blue - thr < 0)
			bluep = 0;
		else
			bluep = blue - thr;

		idle = new GradientDrawable();
		new Color();
		idle.setColor(Color.rgb(red, green, blue));
		idle.setCornerRadius(10);
		idle.setShape(GradientDrawable.OVAL);
		idle.setStroke(2, Color.rgb(redp, greenp, bluep));

		press = new GradientDrawable();
		press.setColor(Color.rgb(redp, greenp, bluep));
		press.setCornerRadius(10);
		press.setShape(GradientDrawable.OVAL);
		press.setStroke(2, Color.rgb(red, green, blue));

		btn = new Button(abs.getContext());
		btn.setText(this.btnname);
		btn.setWidth(50);
		btn.setHeight(65);
		btn.setTextColor(Color.WHITE);
		btn.setTypeface(null, Typeface.BOLD);
		btn.setBackground(idle);
		Context ctx = v.getContext();
		WindowManager wm = (WindowManager) ctx
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		btn.setX(width / 2 - btn.getWidth() / 2);
		btn.setY(height / 2 - btn.getHeight() / 2);
		abs.addView(btn);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) press();
		else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// moveView(v, event.getRawX(), event.getRawY());
			v.setX(event.getRawX() - v.getWidth() / 2);
			v.setY(event.getRawY() - v.getHeight() / 2);
		} else if(event.getAction() == MotionEvent.ACTION_UP) press();
		return true;
	}



}
