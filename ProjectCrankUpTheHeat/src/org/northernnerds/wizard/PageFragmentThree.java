package org.northernnerds.wizard;

import org.northernnerds.projectcrankuptheheat.MainActivity;
import org.northernnerds.projectcrankuptheheat.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class PageFragmentThree extends SherlockFragment implements
		OnClickListener {

	private WizardActivity parentActivity;

	public void setParentActivity(WizardActivity parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View page = (View) inflater.inflate(R.layout.fragment_three, null);
		Button button1 = (Button) page.findViewById(R.id.button1);
		button1.setOnClickListener(this);

		return page;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getSherlockActivity(), "Setup is complete - move on to app.", Toast.LENGTH_SHORT).show();
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
}
