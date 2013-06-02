package com.vektor.amapper.windows.ui;

import java.util.ArrayList;
import java.util.List;

import com.vektor.amapper.R;
import com.vektor.amapper.elements.KeyElement;
import com.vektor.amapper.elements.ProfileElement;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

@SuppressWarnings("deprecation")
public class ProfilesPanel implements OnTouchListener {
	private AbsoluteLayout abs;
	private LinearLayout panel;
	private int xDelta, yDelta, x, y;
	private ImageButton collapseList;
	private LinearLayout collapsablePart;
	private boolean flag = true;

	public ProfilesPanel(LinearLayout panel) {
		this.panel = panel;
		this.abs = (AbsoluteLayout) panel.findViewById(R.id.mainbg);
		this.collapseList = (ImageButton) panel
				.findViewById(R.id.profiles_list_collapse);
		this.collapsablePart = (LinearLayout) panel
				.findViewById(R.id.profiles_panel_collapsable);
		this.panel.setOnTouchListener(this);
		this.collapseList.setOnTouchListener(this);
		this.collapseList.setBackgroundResource(R.drawable.ic_menu_less);
		this.createProfList(panel);
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent mv) {
		switch (v.getId()) {
		case R.id.profiles_list_collapse:
			if (mv.getAction() == MotionEvent.ACTION_DOWN) {
				if (this.collapsablePart.getVisibility() == View.VISIBLE) {
					this.collapseList
							.setBackgroundResource(android.R.drawable.ic_menu_more);

					this.collapsablePart.setVisibility(View.GONE);
					

				} else {
					this.collapseList
							.setBackgroundResource(R.drawable.ic_menu_less);
					this.collapsablePart.setVisibility(View.VISIBLE);
				}
			}
			break;
		case R.id.profiles_list_panel:

			switch (mv.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = (int) mv.getX();
				y = (int) mv.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				xDelta = (int) mv.getRawX() - x;
				yDelta = (int) mv.getRawY() - y;
				AbsoluteLayout.LayoutParams abs = new AbsoluteLayout.LayoutParams(
						v.getWidth(), AbsoluteLayout.LayoutParams.WRAP_CONTENT, xDelta, yDelta);
				v.setLayoutParams(abs);
				break;
			}
		}
		return true;
	}
	public AbsoluteLayout getAbs() {
		// TODO Auto-generated method stub
		return this.abs;
	}
	
	public void createProfList(View v) {
		ListView list = (ListView) v.findViewById(R.id.list_profiles);
		list.setClickable(true);
		List<ProfileElement> listProf = new ArrayList<ProfileElement>();
		/*
		for(int i=0;i<5;i++){
			listProf.add(new ProfileElement("Profile "+i, "com.ex.pack"+i));
		}*/
		listProf.add(new ProfileElement("MapperProfile", "com.vektor.amapper"));
		listProf.add(new ProfileElement("EvInjProfile","net.pocketmagic.android.eventinjector"));
		listProf.add(new ProfileElement("Wrong App Profile","com.wrong.package"));
		ProfileElementAdapter adapter = new ProfileElementAdapter(v.getContext(), listProf);
		list.setAdapter(adapter);
	}
}
