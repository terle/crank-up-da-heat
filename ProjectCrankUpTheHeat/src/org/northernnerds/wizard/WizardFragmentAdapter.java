package org.northernnerds.wizard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

class WizardFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    private WizardActivity parentActivity;

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
			PageFragmentOne p1 = new PageFragmentOne();
			p1.setParentActivity(parentActivity);
			return p1;
		case 1:
			PageFragmentTwo p2 = new PageFragmentTwo();
			p2.setParentActivity(parentActivity);
			return p2;
		case 2:
			PageFragmentThree p3 = new PageFragmentThree();
			p3.setParentActivity(parentActivity);
			return p3;
		default:
			return new PageFragmentOne();
		}
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	return "";    
    }

	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}