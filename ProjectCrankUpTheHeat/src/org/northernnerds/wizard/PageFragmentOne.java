package org.northernnerds.wizard;

import org.northernnerds.projectcrankuptheheat.R;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class PageFragmentOne extends SherlockFragment implements
		OnClickListener {
	private WizardActivity parentActivity;
	private int contactSelected = -1;
	private final int contactNumber1 = 1, contactNumber2 = 2, contactNumber3 = 3, contactNumber4 = 4;  

	private EditText alarm1Text, alarm2Text, alarm3Text, alarm4Text;
	private ImageButton contact1, contact2, contact3, contact4;

	public void setParentActivity(WizardActivity parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View page = (View) inflater.inflate(R.layout.fragment_one, null);
		Button nextButton = (Button) page.findViewById(R.id.nextButton);
		nextButton.setOnClickListener(this);

		alarm1Text = (EditText) page.findViewById(R.id.alarm1EditText);
		contact1 = (ImageButton) page.findViewById(R.id.contactButton1);
		contact1.setOnClickListener(this);
		
		alarm2Text = (EditText) page.findViewById(R.id.alarm2EditText);
		contact2 = (ImageButton) page.findViewById(R.id.contactButton2);
		contact2.setOnClickListener(this);
		
		alarm3Text = (EditText) page.findViewById(R.id.alarm3EditText);
		contact3 = (ImageButton) page.findViewById(R.id.contactButton3);
		contact3.setOnClickListener(this);
		
		alarm4Text = (EditText) page.findViewById(R.id.alarm4EditText);
		contact4 = (ImageButton) page.findViewById(R.id.contactButton4);
		contact4.setOnClickListener(this);
		
		return page;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getSherlockActivity(), "OnClick: " + v.getId(), Toast.LENGTH_SHORT).show();

		switch (v.getId()) {
		case R.id.contactButton1:
			selectContact(contactNumber1);
			break;
		case R.id.contactButton2:
			selectContact(contactNumber2);
			break;
		case R.id.contactButton3:
			selectContact(contactNumber3);
			break;
		case R.id.contactButton4:
			selectContact(contactNumber4);
			break;
		case R.id.nextButton:
			if (parentActivity == null) {
				parentActivity = (WizardActivity) getSherlockActivity();
			}
			parentActivity.setCurrentFragment(1);
			break;
		default:
			break;
		}
	}

	private void selectContact(int contactNumber) {
		contactSelected = contactNumber;
		Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
		startActivityForResult(intent, contactSelected);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Uri uri = data.getData();
			if (uri != null) {
				Cursor c = null;
				try {
					c = getSherlockActivity().getContentResolver().query(uri, new String[] {
											ContactsContract.CommonDataKinds.Phone.NUMBER,
											ContactsContract.CommonDataKinds.Phone.TYPE },
											null, null, null);
					if (c != null && c.moveToFirst()) {
						String number = c.getString(0);
						int type = c.getInt(1);
						Toast.makeText(getSherlockActivity(), type + ": " + number, Toast.LENGTH_LONG).show();
						switch (requestCode) {
						case contactNumber1:
							((EditText)getView().findViewById(R.id.alarm1EditText)).setText(number);
							break;
						case contactNumber2:
							((EditText)getView().findViewById(R.id.alarm2EditText)).setText(number);
							break;
						case contactNumber3:
							((EditText)getView().findViewById(R.id.alarm3EditText)).setText(number);
							break;
						case contactNumber4:
							((EditText)getView().findViewById(R.id.alarm4EditText)).setText(number);
							break;
						default:
							break;
						}
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
	}
}
