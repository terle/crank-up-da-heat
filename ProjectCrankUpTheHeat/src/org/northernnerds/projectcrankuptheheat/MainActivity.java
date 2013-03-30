package org.northernnerds.projectcrankuptheheat;

import org.northennerds.enums.SettingsNames;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements OnTouchListener, OnClickListener {
	private LinearLayout buttonLayout;
	private Button sendButton, cancelButton;
		
//	//Final strings to hold preferences names, not values
//	public static final String PREFSNAME = "CrankUpDaHeatPREFERENCES";
//	public static final String PREFS_isOldController = "isOldController";
//	public static final String PREFS_phoneNum = "phoneNum";
//	public static final String PREFS_password = "password";
//	public static final String PREFS_minTemp = "minTemp";
//	public static final String PREFS_maxTemp = "maxTemp";
//	public static final String PREFS_deviceName = "deviceName";
//	public static final String PREFS_HeatingTemp = "HeatTemp";
//	public static final String PREFS_CoolingTemp = "CoolTemp";
//	public static final String PREFS_GSMBat = "GSMBat";
//	public static final String PREFS_GSMSignal = "GSMSignal";
	
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
		
		thermostat = (ThermostatView) findViewById(R.id.thermostatView1);
		thermostat.setTempTextView(tempTextView);
		thermostat.setOnTouchListener(this);
		
		buttonLayout = (LinearLayout) findViewById(R.id.sendCancelButtonLayout);
		
		sendButton = (Button) findViewById(R.id.sendButton);
		sendButton.setOnClickListener(this);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(this);
		
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
	public void onBackPressed() {
		super.onBackPressed();
		//TODO: Her skal services startes der tjekker for sms'er
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("X: " + event.getX() + ", Y: " + event.getY());
		
		if(event.getAction() == MotionEvent.ACTION_UP) {
			if(buttonLayout.getVisibility() == View.INVISIBLE) {
		        Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_in_anim);
				buttonLayout.startAnimation(anim);
				buttonLayout.setVisibility(View.VISIBLE);
				System.out.println("Is this called??!");
			}
		}
		return false;
	}

	private void slideOutButtons() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_out_anim);
		buttonLayout.startAnimation(anim);
		buttonLayout.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendButton:
			Toast.makeText(this, "Sending something!", Toast.LENGTH_SHORT).show();
			slideOutButtons();
			break;
		case R.id.cancelButton:
			slideOutButtons();
			break;
		default:
			break;
		}
	}
}
