package org.northernnerds.projectcrankuptheheat;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	//Final strings to hold preferences names, not values
	public static final String PREFSNAME = "CrankUpDaHeatPREFERENCES";
	public static final String PREFS_isOldController = "isOldController";
	public static final String PREFS_phoneNum = "phoneNum";
	public static final String PREFS_password = "password";
	public static final String PREFS_minTemp = "minTemp";
	public static final String PREFS_maxTemp = "maxTemp";
	public static final String PREFS_deviceName = "deviceName";
	public static final String PREFS_HeatingTemp = "HeatTemp";
	public static final String PREFS_CoolingTemp = "CoolTemp";
	public static final String PREFS_GSMBat = "GSMBat";
	public static final String PREFS_GSMSignal = "GSMSignal";

	//Values for SharedPrefs
	public static String name = "Sommerfjong i bingbong";
	public static boolean isOldController = false;
	public static String devicePhoneNum = "004528921237";
	public static String passwd = "1234";
	public static int minTemp = 8;
	public static int maxTemp = 8;
	public static int heatingTemp = 24;
	public static int coolingTemp = 30;
	public static String GSMbat = "100%";
	public static String GSMSignal ="(1-5):3";
	private ThermostatView thermostat;
	private TextView tempTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setting main contentview.
		setContentView(R.layout.activity_main);

		// Setting the behind view
		setBehindContentView(R.layout.menu_layout);

		tempTextView = (TextView) findViewById(R.id.temperatureTextView);
		Typeface roboto = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		tempTextView.setTypeface(roboto);
		tempTextView.setTextSize(98);
		
		// Setting up the Sliding Menu.
		final SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.defaultshadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		
		// Overriding the behindContentView with a fragment.
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SettingsFragment())
		.commit();
		
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_settings:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		SharedPreferences settings = getSharedPreferences(PREFSNAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREFS_deviceName, name);
		editor.putBoolean(PREFS_isOldController, isOldController);
		editor.putString(PREFS_phoneNum, devicePhoneNum);
		editor.putString(PREFS_password, passwd); 
		editor.putInt(PREFS_minTemp, minTemp);
		editor.putInt(PREFS_maxTemp, maxTemp);
		editor.putInt(PREFS_HeatingTemp, heatingTemp); 
		editor.putInt(PREFS_CoolingTemp, coolingTemp); 
		editor.putString(PREFS_GSMBat, GSMbat);
		editor.putString(PREFS_GSMSignal, GSMSignal);
		editor.commit();
		super.onPause();
	}
	@Override
	protected void onResume() {


		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFSNAME, 0);
		name = settings.getString(PREFS_deviceName, name);
		isOldController = settings.getBoolean(PREFS_isOldController, false);
		devicePhoneNum = settings.getString(PREFS_phoneNum, "");
		passwd = settings.getString(PREFS_password, "1234");
		minTemp = settings.getInt(PREFS_minTemp, 9);
		maxTemp = settings.getInt(PREFS_maxTemp, 9);
		heatingTemp = settings.getInt(PREFS_HeatingTemp, 24);
		coolingTemp = settings.getInt(PREFS_CoolingTemp, 20);
		GSMbat = settings.getString(PREFS_GSMBat, "100%");
		GSMSignal = settings.getString(PREFS_GSMSignal, "bla");

		super.onResume();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//TODO: Her skal services startes der tjekker for sms'er
	}
}
