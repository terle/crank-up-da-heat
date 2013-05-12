package org.northernnerds.wizard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class WizardFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
	private WizardActivity parentActivity;
	private PageFragmentOne page1;
	private PageFragmentTwo page2;
	private PageFragmentThree page3;
	private PageFragmentFour page4;

	public WizardFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public void setParentActivity(WizardActivity parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			page1 = new PageFragmentOne();
			page1.setParentActivity(parentActivity);
			return page1;
		case 1:
			page2 = new PageFragmentTwo();
			page2.setParentActivity(parentActivity);
			return page2;
		case 2:
			page3 = new PageFragmentThree();
			page3.setParentActivity(parentActivity);
			return page3;
		case 3:
			page4 = new PageFragmentFour();
			page4.setParentActivity(parentActivity);
			return page4;
		default:
			page2 = new PageFragmentTwo();
			page2.setParentActivity(parentActivity);
			return page2;
		}
	}

	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}

	@Override
	public int getIconResId(int index) {
		return 0;
	}
}