package org.northernnerds.projectcrankuptheheat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

public class SettingsFragment extends SherlockFragment implements OnClickListener {
	private EditText unitNameEditText, phonenoEditText;
	private Button thermometerButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View inflatedView = inflater.inflate(R.layout.behind, null);
		unitNameEditText = (EditText) inflatedView.findViewById(R.id.unitNameEditText);
		phonenoEditText = (EditText) inflatedView.findViewById(R.id.phonenoEditText);
		
		thermometerButton = (Button) inflatedView.findViewById(R.id.alarmButton);
		thermometerButton.setOnClickListener(this);
		
		return inflatedView;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getSherlockActivity(), ThermometerActivity.class);
		startActivity(intent);
	}
}
