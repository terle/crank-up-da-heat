package org.northernnerds.projectcrankuptheheat;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.behind);
		
		setContentView(R.layout.activity_main);

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
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
		
		// Bind the title indicator to the adapter
		TitlePageIndicator titlePageIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		titlePageIndicator.setViewPager(pager);
		
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
