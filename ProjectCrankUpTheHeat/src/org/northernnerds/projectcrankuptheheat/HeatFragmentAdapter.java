package org.northernnerds.projectcrankuptheheat;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.actionbarsherlock.app.SherlockFragment;
import com.viewpagerindicator.IconPagerAdapter;

public class HeatFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {
	protected static final String[] CONTENT = new String[] { "Info",
			"Programvalg", "Advarsler" };

	private int mCount = CONTENT.length;
	private List<SherlockFragment> fragmentList;

	public HeatFragmentAdapter(FragmentManager fm) {
		super(fm);
		fragmentList = new ArrayList<SherlockFragment>();
    }

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			InfoFragment infoFragment = new InfoFragment();
			fragmentList.add(infoFragment);
			return infoFragment;
		case 1:
			ThermostatFragment thermostatFragment = new ThermostatFragment();
			fragmentList.add(thermostatFragment);
			return thermostatFragment;
		default:
			ThermometerFragment thermometerFragment = new ThermometerFragment();
			fragmentList.add(thermometerFragment);
			return thermometerFragment;
		}
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return HeatFragmentAdapter.CONTENT[position % CONTENT.length];
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
	
	@Override
	public int getIconResId(int index) {
		return 0;
	}
	
	public void updateFragments() {
		for(SherlockFragment f : fragmentList) {
			if(f instanceof InfoFragment) {
				((InfoFragment) f).update();
			} else if(f instanceof ThermostatFragment) {
				((ThermostatFragment) f).update();
			} else if(f instanceof ThermometerFragment) {
				((ThermometerFragment) f).update();
			}
		}
	}
}