package org.northernnerds.projectcrankuptheheat;

import org.northernnerds.enums.SettingsNames;
import org.northernnerds.wizard.NonSwipableViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends SlidingFragmentActivity  {
    private ViewPager mPager;
    private PageIndicator mIndicator;
    private SettingsFragment settingsFragment;
    private HeatFragmentAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_PROGRESS);
		
		// Setting main contentview.
		setContentView(R.layout.fragmentpager_layout);
  
		// Setting the behind view
		setBehindContentView(R.layout.menu_layout);
		
		mAdapter = new HeatFragmentAdapter(getSupportFragmentManager());
		
        mPager = (NonSwipableViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
		
		// Setting up the Sliding Menu.
		final SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.defaultshadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		
		// Overriding the behindContentView with a fragment.
		settingsFragment = new SettingsFragment();
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.menu_frame, settingsFragment)
			.commit();
		
		getSupportActionBar().setHomeButtonEnabled(true);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(SettingsNames.SMS_HANDLED_ACTION.getName());
		registerReceiver(smsReceivedReceiver, filter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(smsReceivedReceiver);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		toggle();
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//TODO: Her skal services startes der tjekker for sms'er
	}
	
	private BroadcastReceiver smsReceivedReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(SettingsNames.SMS_HANDLED_ACTION.getName())) {
				mAdapter.updateFragments();
				settingsFragment.update();
			}
		}
	};
}
