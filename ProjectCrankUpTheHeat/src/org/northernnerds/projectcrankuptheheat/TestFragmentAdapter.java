//package org.northernnerds.projectcrankuptheheat;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.viewpagerindicator.IconPagerAdapter;
//
//// TODO: Skal omd¿bes og renses for un¿dig kode...
//class TestFragmentAdapter extends FragmentPagerAdapter implements
//		IconPagerAdapter {
//	protected static final String[] CONTENT = new String[] { "This", "Is", "A",
//			"Test", };
//
//	private int mCount = CONTENT.length;
//
//	public TestFragmentAdapter(FragmentManager fm) {
//		super(fm);
//	}
//
//	@Override
//	public Fragment getItem(int position) {
//		switch (position) {
//		case 0:
//			return new ThermostatFragment();
//		case 1:
//			return new ThermometerFragment();
//		default:
//			return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
//		}
//	}
//
//	@Override
//	public int getCount() {
//		return mCount;
//	}
//
//	@Override
//	public CharSequence getPageTitle(int position) {
//		return TestFragmentAdapter.CONTENT[position % CONTENT.length];
//	}
//
//	public void setCount(int count) {
//		if (count > 0 && count <= 10) {
//			mCount = count;
//			notifyDataSetChanged();
//		}
//	}
//
//	@Override
//	public int getIconResId(int index) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//}