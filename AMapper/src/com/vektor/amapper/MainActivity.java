package com.vektor.amapper;

import com.vektor.amapper.windows.MainWindow;

import wei.mark.standout.StandOutWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StandOutWindow.closeAll(this, MainWindow.class);
		StandOutWindow.show(this, MainWindow.class, StandOutWindow.DEFAULT_ID);
		finish();
	}
}
