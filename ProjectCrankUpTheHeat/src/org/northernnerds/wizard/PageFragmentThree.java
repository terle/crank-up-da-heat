package org.northernnerds.wizard;

import org.northernnerds.enums.SettingsNames;
import org.northernnerds.projectcrankuptheheat.MainActivity;
import org.northernnerds.projectcrankuptheheat.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.SherlockFragment;

public class PageFragmentThree extends SherlockFragment implements OnClickListener, OnCheckedChangeListener {
	private CheckBox showWizardCheckbox;
	private WizardActivity parentActivity;

	public void setParentActivity(WizardActivity parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View page = (View) inflater.inflate(R.layout.fragment_three, null);
		Button button1 = (Button) page.findViewById(R.id.button1);
		button1.setOnClickListener(this);
		showWizardCheckbox = (CheckBox) page.findViewById(R.id.showWizardCheckbox);
		showWizardCheckbox.setOnCheckedChangeListener(this);
		
		return page;
	}

	@Override
	public void onClick(View v) {
		if(parentActivity != null) {
			Intent appIntent = new Intent(getSherlockActivity(), MainActivity.class);
			startActivity(appIntent);
			parentActivity.finish();
		} else {
			Intent appIntent = new Intent(getSherlockActivity(), MainActivity.class);
			startActivity(appIntent);
			parentActivity = (WizardActivity) getSherlockActivity();
			parentActivity.setCurrentFragment(0);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		SharedPreferences sharedPreferences = getSherlockActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putBoolean("ShowWizard", isChecked);
	    editor.commit();
	}
}
