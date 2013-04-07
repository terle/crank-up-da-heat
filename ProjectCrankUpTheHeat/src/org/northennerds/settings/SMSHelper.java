package org.northennerds.settings;

import org.northennerds.enums.ResponseTypes;

import android.content.Context;
import android.widget.Toast;

public class SMSHelper {
	private Context context;

	public SMSHelper(Context context) {
		this.context = context;
	}

	public ResponseTypes getResponseType(String str) {
		if (str.contains(ResponseTypes.AlarmNumre.getText()))
			return ResponseTypes.AlarmNumre;
		if (str.contains(ResponseTypes.Brands.getText()))
			return ResponseTypes.Brands;
		if (str.contains(ResponseTypes.NORMALIZEDNotification.getText()))
			return ResponseTypes.NORMALIZEDNotification;
		if (str.contains(ResponseTypes.PASSWORDUpdate.getText()))
			return ResponseTypes.PASSWORDUpdate;
		if (str.contains(ResponseTypes.PowerFailure.getText()))
			return ResponseTypes.PowerFailure;
		if (str.contains(ResponseTypes.PowerRestored.getText()))
			return ResponseTypes.PowerRestored;
		if (str.contains(ResponseTypes.SETUpdate.getText()))
			return ResponseTypes.SETUpdate;
		if (str.contains(ResponseTypes.Status.getText()))
			return ResponseTypes.Status;
		if (str.contains(ResponseTypes.TempHIGHUpdate.getText()))
			return ResponseTypes.TempHIGHUpdate;
		if (str.contains(ResponseTypes.TempLOWUpdate.getText()))
			return ResponseTypes.TempLOWUpdate;
		if (str.contains(ResponseTypes.WarningHIGHTemp.getText()))
			return ResponseTypes.WarningHIGHTemp;
		if (str.contains(ResponseTypes.WarningLOWTemp.getText()))
			return ResponseTypes.WarningLOWTemp;
		if (str.contains(ResponseTypes.WarningUNDER5Degrees.getText()))
			return ResponseTypes.WarningUNDER5Degrees;

		return ResponseTypes.UNKOWN;

	}

	public boolean isSetTempCOOL(String str) {
		// Aktuel:27 - Set:16_Cool
		if (str.contains("Aktuel")) {
			if (str.contains("Cool"))
				return true;
		}
		return false;
	}

	public boolean isSetTempHEAT(String str) {
		// Aktuel:26 - Set:8_Heat
		if (str.contains("Aktuel")) {
			if (str.contains("Heat"))
				return true;
		}
		return false;
	}

	public int getSetTemp(String str) {
		// Aktuel:27 - Set:16_Cool
		int result = -1;

		if (str.contains("Aktuel")) {
			str = str.substring(str.indexOf("-"));
			if (str.contains("Set")) {
				int i = str.indexOf(":");
				String s = str.substring(i + 1, i + 3);
				try {
					result = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					Toast.makeText(context, "Error in parsing the Set Temperature Trying to parse: " + s,
							Toast.LENGTH_LONG).show();
					return result;
				}
			}
		}

		return result;
	}

	public int getCurrentTemp(String str) {
		// Aktuel:27 - Set:16_Cool
		int result = -1;

		if (str.contains("Aktuel")) {
			String s = str.substring(str.indexOf(":") + 1, str.indexOf("-") - 1);
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the Current Temperature Trying to parse: " + s,
						Toast.LENGTH_LONG).show();
				return result;
			}
		}

		return result;
	}

	public int getGSMSignal(String str) {
		// GSM signal(1-5):2
		int result = -1;

		if (str.contains("(1-5")) {
			String s = str.substring(str.indexOf(":") + 1);
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the GSM Signal Trying to parse: " + s, Toast.LENGTH_LONG)
						.show();
				return result;
			}
		}

		return result;
	}

	public int getBatteryStatus(String str) {
		// Batteri:47%
		int result = -1;
		if (str.contains("Batteri")) {
			String s = str.substring(str.indexOf(":") + 1, str.indexOf("%"));
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the Battery Status. Trying to parse: " + s, Toast.LENGTH_LONG)
						.show();
				return result;
			}
		}
		return result;
	}

	public int getWarningHIGHTemp(String str) {
		// Advarsel Lav:30 - H¿j:45
		int result = -1;
		if (str.contains("Advarsel")) {
			String s = str.substring(str.indexOf("H¿j") + 4);
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the High WarningTemp. Trying to parse: " + s,
						Toast.LENGTH_LONG).show();
				return result;
			}
		}
		return result;
	}

	public int getWarningLOWTemp(String str) {
		// Advarsel Lav:30 - H¿j:45
		int result = -1;
		if (str.contains("Advarsel")) {
			int indexLav = str.indexOf("Lav") + 4;
			String s = str.substring(indexLav, indexLav + 2);
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the Low WarningTemp. Trying to parse: " + s,
						Toast.LENGTH_LONG).show();
				return result;
			}
		}
		return result;
	}

	public String getPhoneNum(String str) {
		// Ph1:+4561669199

		String result = "";

		if (str.contains("Ph")) {
			result = str.substring(3);
		}

		return result;
	}

	
}
