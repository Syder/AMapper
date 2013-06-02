package com.vektor.amapper.windows.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vektor.amapper.elements.ProfileElement;
import com.vektor.amapper.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileElementAdapter extends BaseAdapter {
	private Map<String, Drawable> icnsMap;
	private Context context;
	private List<ProfileElement> listProf;

	public ProfileElementAdapter(Context context, List<ProfileElement> listProf) {
		this.context = context;
		this.listProf = listProf;
		this.icnsMap = new HashMap<String, Drawable>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listProf.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.listProf.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int id, View v, ViewGroup vg) {
		// TODO Auto-generated method stub
		ProfileElement entry = listProf.get(id);
		if (null == v) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.profiles_list_row, null);
		}
		TextView profileName = (TextView) v.findViewById(R.id.profile_name);
		TextView packageName = (TextView) v.findViewById(R.id.package_name);
		ImageView packageIcon = (ImageView) v.findViewById(R.id.app_icon);
		profileName.setText(entry.getProfileName());
		packageName.setText(entry.getPackageName());
		Drawable d = fetchIcon(v, entry);
		if (d != null)
			packageIcon.setImageDrawable(d);
		return v;
	}

	private Drawable fetchIcon(View v, ProfileElement entry){
		String packageName = entry.getPackageName();
		Drawable d = this.icnsMap.get(packageName);
		if(d!=null) return d;
		PackageManager pm = v.getContext().getPackageManager();
		try {
			Intent i = pm.getLaunchIntentForPackage(packageName);
			if(i==null) return null;
			d= pm.getActivityIcon(i);
			if (d!=null) this.icnsMap.put(packageName, d);
			return this.icnsMap.get(packageName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
