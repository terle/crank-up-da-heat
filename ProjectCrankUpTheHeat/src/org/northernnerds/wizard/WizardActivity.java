package org.northernnerds.wizard;

import org.northernnerds.projectcrankuptheheat.R;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class WizardActivity extends SherlockFragmentActivity  {
	private WizardFragmentAdapter mAdapter;
	private NonSwipableViewPager mPager;
	private PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
