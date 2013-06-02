package com.vektor.amapper.elements;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class ProfileElement {
	//private Drawable icon;
	private String profName;
	private String packageName;
	
	public ProfileElement(String profName, String packageName){
		this.profName = profName;
		this.packageName = packageName;
		//this.icon = this.fetchIcon();
	}
	

	
	public String getProfileName(){
		return this.profName;
	}
	public String getPackageName(){
		return this.packageName;
	}
	/*public Drawable getIcon(){
		return this.icon;
	}*/
}
