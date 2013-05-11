package org.northernnerds.wizard;

import org.northernnerds.enums.SettingsNames;
import org.northernnerds.projectcrankuptheheat.MainActivity;
import org.northernnerds.projectcrankuptheheat.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class WizardActivity extends SherlockFragmentActivity  {
	private WizardFragmentAdapter mAdapter;
	private NonSwipableViewPager mPager;
	private PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences sharedPreferences = getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
	    boolean shouldShowWizard = sharedPreferences.getBoolean(SettingsNames.SHOULD_SHOW_WIZARD.getName(), true);
	    if(!shouldShowWizard) {
	    	finish();
	    	Intent appIntent = new Intent(this, MainActivity.class);
	    	startActivity(appIntent);
	    }
		
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.wizard_layout);

		mAdapter = new WizardFragmentAdapter(getSupportFragmentManager());
		mAdapter.setParentActivity(this);

		mPager = (NonSwipableViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		LinePageIndicator indicator = (LinePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		mIndicator = indicator;
	}
	
	public void setCurrentFragment(int position) {
		mIndicator.setCurrentItem(position);
	}
}
