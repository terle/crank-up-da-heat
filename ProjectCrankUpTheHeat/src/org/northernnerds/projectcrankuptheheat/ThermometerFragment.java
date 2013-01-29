package org.northernnerds.projectcrankuptheheat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.actionbarsherlock.app.SherlockFragment;

public class ThermometerFragment extends SherlockFragment implements OnSeekBarChangeListener {
	private SeekBar seekbar1, seekbar2;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View inflatedView = inflater.inflate(R.layout.thermometer, null);
		
		seekbar1 = (SeekBar) inflatedView.findViewById(R.id.seekBar1);
		seekbar1.setOnSeekBarChangeListener(this);
		
		seekbar2 = (SeekBar) inflatedView.findViewById(R.id.seekBar2);
		seekbar2.setOnSeekBarChangeListener(this);
		
		return inflatedView;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Toast.makeText(getSherlockActivity(), "Progress changed... " + progress, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Toast.makeText(getSherlockActivity(), "Started tracking...", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Toast.makeText(getSherlockActivity(), "Stopped tracking...", Toast.LENGTH_LONG).show();
	}
}
