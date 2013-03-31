package org.northernnerds.projectcrankuptheheat;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements OnTouchListener, OnClickListener, OnSeekBarChangeListener {
	private LinearLayout buttonLayout;
	private Button sendButton, cancelButton;
	private ThermostatView thermostat;
	private TextView tempTextView;
	private SeekBar hotColdSeekBar;
	private RelativeLayout mainLayout;
	private ImageView hotImageView, coldImageView;
	private Drawable hotUnselected, hotSelected, coldUnselected, coldSelected, hotBackground, coldBackground;

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
		tempTextView.setTextSize(70);
		
		thermostat = (ThermostatView) findViewById(R.id.thermostatView1);
		thermostat.setTempTextView(tempTextView);
		thermostat.setOnTouchListener(this);
		
		buttonLayout = (LinearLayout) findViewById(R.id.sendCancelButtonLayout);
		
		sendButton = (Button) findViewById(R.id.sendButton);
		sendButton.setOnClickListener(this);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(this);
		
		hotColdSeekBar = (SeekBar) findViewById(R.id.hotColdSeekBar);
		hotColdSeekBar.setOnSeekBarChangeListener(this);
		
		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		
		hotImageView = (ImageView) findViewById(R.id.hotImageView); 
		coldImageView = (ImageView) findViewById(R.id.coldImageView);
		
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
			// Should call SMSHandler here...
			slideOutButtons();
			break;
		case R.id.cancelButton:
			slideOutButtons();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Preloading all background and heat/cold images into drawables - if not doing this, it seems like the first time
		// we need to change background images and such, it takes forever, if we load them from files. Makes sense, since
		// I/O is always performance heavy.
		hotUnselected = getResources().getDrawable(R.drawable.varme_ikon);
		hotSelected = getResources().getDrawable(R.drawable.varme_ikon_selected);
		coldUnselected = getResources().getDrawable(R.drawable.kulde_ikon);
		coldSelected = getResources().getDrawable(R.drawable.kulde_ikon_selected);
		
		hotBackground = getResources().getDrawable(R.drawable.bg_varm);
		coldBackground = getResources().getDrawable(R.drawable.bg_kold);
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if(progress == 0) {
			// TODO: This is deprecated, but since the app is working from API = 8, then we need it.
			// From API = 16, we should use setBackground(drawable) - make a check for the OS version.
			mainLayout.setBackgroundDrawable(hotBackground);
			hotImageView.setImageDrawable(hotSelected);
			coldImageView.setImageDrawable(coldUnselected);
		} else {
			// TODO: This is deprecated, but since the app is working from API = 8, then we need it.
			// From API = 16, we should use setBackground(drawable) - make a check for the OS version.
			mainLayout.setBackgroundDrawable(coldBackground);
			coldImageView.setImageDrawable(coldSelected);
			hotImageView.setImageDrawable(hotUnselected);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}
}
