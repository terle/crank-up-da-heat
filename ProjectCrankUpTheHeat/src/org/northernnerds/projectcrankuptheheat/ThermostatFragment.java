package org.northernnerds.projectcrankuptheheat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermostatFragment extends SherlockFragment {
	private TextView textViewTempGauge;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ThermostatView thermostat = new ThermostatView(getActivity());

		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.addView(thermostat);
		//TODO: Move this TExtview to the center of the thermostat, and change font.
		textViewTempGauge = new TextView(getActivity());
		textViewTempGauge.setText("Fuck Asier");
		
		textViewTempGauge.setGravity(Gravity.CENTER);

		layout.addView(textViewTempGauge);

		return layout;
	}
}
