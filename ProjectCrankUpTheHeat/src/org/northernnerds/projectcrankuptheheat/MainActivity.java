package org.northernnerds.projectcrankuptheheat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setting main contentview.
		FrameLayout mainLayout = (FrameLayout) getLayoutInflater().inflate(
				R.layout.activity_main, null);
		ThermostatView thermostat = new ThermostatView(this);

		thermostat.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT));
//		thermostat.setBackgroundColor(Color.GRAY);
		// RelativeLayout.LayoutParams layoutParams =
		// (RelativeLayout.LayoutParams)thermostat.getLayoutParams();
		// layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);

		// mainLayout.setGravity(Gravity.CENTER);
		mainLayout.addView(thermostat);

		TextView tempTextView = new TextView(this);
		tempTextView.setText("TEMP!");
		tempTextView.setLayoutParams(new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT));
		tempTextView.setBackgroundColor(Color.GREEN);
		
		mainLayout.addView(tempTextView);

		mainLayout.setForegroundGravity(Gravity.CENTER);
		
		setContentView(mainLayout);

		// Setting the behind view
		setBehindContentView(R.layout.behind);

		// Setting up the Sliding Menu.
		final SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.defaultshadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);

		// Set the pager with an adapter
		// ViewPager pager = (ViewPager) findViewById(R.id.pager);
		// pager.setAdapter(new
		// TestFragmentAdapter(getSupportFragmentManager()));
		//
		// // Bind the title indicator to the adapter
		// TitlePageIndicator titlePageIndicator = (TitlePageIndicator)
		// findViewById(R.id.indicator);
		// titlePageIndicator.setViewPager(pager);

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
}
