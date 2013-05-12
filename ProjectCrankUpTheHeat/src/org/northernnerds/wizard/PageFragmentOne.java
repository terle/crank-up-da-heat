package org.northernnerds.wizard;

import org.northernnerds.enums.Brands;
import org.northernnerds.enums.SettingsNames;
import org.northernnerds.projectcrankuptheheat.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class PageFragmentOne extends SherlockFragment implements OnClickListener {
	private WizardActivity parentActivity;
	private int contactSelected = -1;
	private final int contactNumber1 = 1, contactNumber2 = 2, contactNumber3 = 3, contactNumber4 = 4;

	private EditText alarm1Text, alarm2Text, alarm3Text, alarm4Text, unitNameEditText, unitPhoneNumEditText, unitPasswordEditText;
	private ImageButton contact1, contact2, contact3, contact4;

	private Spinner brandSpinner;

	private Brands[] brandList = { Brands.BOSCH, Brands.DAIKIN, Brands.ELECTROLUX_NEW, Brands.ELECTROLUX_OLD, Brands.FUJITSU, Brands.HAIER,
			Brands.IVT, Brands.LG, Brands.MITSUBISHI, Brands.PANASONIC, Brands.TOSHIBA, Brands.ZIBRA };
	private Brands brand;
	public void setParentActivity(WizardActivity parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View page = (View) inflater.inflate(R.layout.fragment_one, null);
		Button nextButton = (Button) page.findViewById(R.id.nextButton);
		nextButton.setOnClickListener(this);

		unitNameEditText = (EditText) page.findViewById(R.id.unitNameEditText);
		unitPasswordEditText = (EditText) page.findViewById(R.id.unitPasswordEditText);
		unitPhoneNumEditText = (EditText) page.findViewById(R.id.unitPhoneNoEditText);

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

		brandSpinner = (Spinner) page.findViewById(R.id.modelSpinner);
		brandSpinner.setAdapter(new CustomAdapter(getActivity(), R.layout.row, brandList));
		
		return page;
	}

	@Override
	public void onClick(View v) {
//		Toast.makeText(getSherlockActivity(), "OnClick: " + v.getId(), Toast.LENGTH_SHORT).show();

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
			setSharedPreferencesFromFields();
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
					c = getSherlockActivity().getContentResolver().query(uri,
							new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE }, null, null,
							null);
					if (c != null && c.moveToFirst()) {
						String number = c.getString(0);
						int type = c.getInt(1);
						Toast.makeText(getSherlockActivity(), type + ": " + number, Toast.LENGTH_LONG).show();
						switch (requestCode) {
						case contactNumber1:
							((EditText) getView().findViewById(R.id.alarm1EditText)).setText(number);
							break;
						case contactNumber2:
							((EditText) getView().findViewById(R.id.alarm2EditText)).setText(number);
							break;
						case contactNumber3:
							((EditText) getView().findViewById(R.id.alarm3EditText)).setText(number);
							break;
						case contactNumber4:
							((EditText) getView().findViewById(R.id.alarm4EditText)).setText(number);
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

	private void setSharedPreferencesFromFields(){
		SharedPreferences settings = getActivity().getSharedPreferences(SettingsNames.PREFERENCES_NAME.getName(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString(SettingsNames.DEVICE_NAME.getName(), unitNameEditText.getText().toString());
		editor.putString(SettingsNames.DEVICE_PASSWORD.getName(), unitPasswordEditText.getText().toString());
		editor.putString(SettingsNames.DEVICE_PHONENO.getName(), unitPhoneNumEditText.getText().toString());
	
		editor.putString(SettingsNames.BRAND_NAME.getName(), brand.getName());
		
		editor.putString(SettingsNames.ALARM_NUMBER_01.getName(), alarm1Text.getText().toString());
		editor.putString(SettingsNames.ALARM_NUMBER_02.getName(), alarm2Text.getText().toString());
		editor.putString(SettingsNames.ALARM_NUMBER_03.getName(), alarm3Text.getText().toString());
		editor.putString(SettingsNames.ALARM_NUMBER_04.getName(), alarm4Text.getText().toString());
		
		editor.commit();
	}
	
	//FIXME: The brand is never set. Always the last, Zibro, is chosen
	//This is a horrible way of doing it. This class should be moved out, since it's used twice. I DRY myself, I know.
	public class CustomAdapter extends ArrayAdapter<Brands> {

		public CustomAdapter(Context context, int textViewResourceId, Brands[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
		
		public View getCustomView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater(getArguments());
			View row = inflater.inflate(R.layout.row, parent, false);
			ImageView icon = (ImageView) row.findViewById(R.id.icon);

			if (brandList[position] == Brands.BOSCH) {
				icon.setImageResource(R.drawable.bosch);
				brand = Brands.BOSCH;
			} else if (brandList[position] == Brands.DAIKIN) {
				icon.setImageResource(R.drawable.daikin);
				brand = Brands.DAIKIN;
			} else if (brandList[position] == Brands.ELECTROLUX_NEW) {
				icon.setImageResource(R.drawable.electrolux);
				brand = Brands.ELECTROLUX_NEW;
			} else if (brandList[position] == Brands.ELECTROLUX_OLD) {
				icon.setImageResource(R.drawable.electrolux);
				brand = Brands.ELECTROLUX_OLD;
			} else if (brandList[position] == Brands.FUJITSU) {
				icon.setImageResource(R.drawable.fujitsu);
				brand = Brands.FUJITSU;
			} else if (brandList[position] == Brands.HAIER) {
				icon.setImageResource(R.drawable.haier);
				brand = Brands.HAIER;
			} else if (brandList[position] == Brands.IVT) {
				icon.setImageResource(R.drawable.ivt);
				brand = Brands.IVT;
			} else if (brandList[position] == Brands.LG) {
				icon.setImageResource(R.drawable.lg);
				brand = Brands.LG;
			} else if (brandList[position] == Brands.MITSUBISHI) {
				icon.setImageResource(R.drawable.mitsubishi);
				brand = Brands.MITSUBISHI;
			} else if (brandList[position] == Brands.PANASONIC) {
				icon.setImageResource(R.drawable.panasonic);
				brand = Brands.PANASONIC;
			} else if (brandList[position] == Brands.TOSHIBA) {
				icon.setImageResource(R.drawable.toshiba);
				brand = Brands.TOSHIBA;
			} else if (brandList[position] == Brands.ZIBRA) {
				icon.setImageResource(R.drawable.zibro);
				brand = Brands.ZIBRA;
			} else {
				icon.setImageResource(R.drawable.refresh);
			}
			return row;
		}
	}

}
