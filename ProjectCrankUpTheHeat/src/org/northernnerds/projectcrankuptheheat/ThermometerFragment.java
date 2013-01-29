package org.northernnerds.projectcrankuptheheat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermometerFragment extends SherlockFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LinearLayout layout = new LinearLayout(getActivity());
//		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
//				LayoutParams.FILL_PARENT));
//		layout.setGravity(Gravity.CENTER);
//		
		

		return layout;
	}
}
