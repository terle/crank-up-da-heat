//package org.northernnerds.projectcrankuptheheat;
//
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//
//import com.actionbarsherlock.app.SherlockFragment;
//
//public class ThermostatFragment extends SherlockFragment implements OnTouchListener {
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
////		ThermostatView thermostat = new ThermostatView(getActivity());
////		thermostat.setOnTouchListener(this);
//		
//		LinearLayout layout = new LinearLayout(getActivity());
////		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
////				LayoutParams.MATCH_PARENT));
////		layout.setGravity(Gravity.CENTER);
////		layout.addView(thermostat);
//
//		return layout;
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		LinearLayout buttonLayout = (LinearLayout) getActivity().findViewById(R.id.sendCancelButtonLayout);
//        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_anim);
//		buttonLayout.startAnimation(anim);		
//		return false;
//	}
//}
