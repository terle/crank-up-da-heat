package org.northernnerds.wizard;

import org.northernnerds.projectcrankuptheheat.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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

		ImageView thermostatIamge = (ImageView) page.findViewById(R.id.thermostatImage);
		thermostatIamge.setImageBitmap(ImageHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.termostat, 300, 400));
		
		return page;
	}

	@Override
	public void onClick(View v) {
		if (parentActivity == null) {
			parentActivity = (WizardActivity) getSherlockActivity();
		} 
		parentActivity.setCurrentFragment(3);
	}
}
