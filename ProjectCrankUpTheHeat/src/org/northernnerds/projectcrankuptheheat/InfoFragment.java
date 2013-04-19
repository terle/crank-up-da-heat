package org.northernnerds.projectcrankuptheheat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class InfoFragment extends SherlockFragment {	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View inflatedView = inflater.inflate(R.layout.infoscreen_layout, null);
		
		return inflatedView;
	}
}
