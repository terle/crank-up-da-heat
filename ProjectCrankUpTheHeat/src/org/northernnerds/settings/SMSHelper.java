package org.northernnerds.settings;

import org.northernnerds.enums.ResponseTypes;

import android.content.Context;
import android.widget.Toast;

public class SMSHelper {
	private Context context;

	public SMSHelper(Context context) {
		this.context = context;
	}

	public ResponseTypes getResponseType(String str) {
		if (str.contains(ResponseTypes.ALARM_NUMBERS.getText()))
			return ResponseTypes.ALARM_NUMBERS;
		if (str.contains(ResponseTypes.BRANDS.getText()))
			return ResponseTypes.BRANDS;
		if (str.contains(ResponseTypes.NORMALIZED_NOTIFICATION.getText()))
			return ResponseTypes.NORMALIZED_NOTIFICATION;
		if (str.contains(ResponseTypes.PASSWORD_UPDATE.getText()))
			return ResponseTypes.PASSWORD_UPDATE;
		if (str.contains(ResponseTypes.POWER_FAILURE.getText()))
			return ResponseTypes.POWER_FAILURE;
		if (str.contains(ResponseTypes.POWER_RESTORED.getText()))
			return ResponseTypes.POWER_RESTORED;
		if (str.contains(ResponseTypes.SET_UPDATE.getText()))
			return ResponseTypes.SET_UPDATE;
		if (str.contains(ResponseTypes.STATUS.getText()))
			return ResponseTypes.STATUS;
		if (str.contains(ResponseTypes.TEMP_HIGH_UPDATE.getText()))
			return ResponseTypes.TEMP_HIGH_UPDATE;
		if (str.contains(ResponseTypes.TEMP_LOW_UPDATE.getText()))
			return ResponseTypes.TEMP_LOW_UPDATE;
		if (str.contains(ResponseTypes.WARNING_HIGH_TEMP.getText()))
			return ResponseTypes.WARNING_HIGH_TEMP;
		if (str.contains(ResponseTypes.WARNING_LOW_TEMP.getText()))
			return ResponseTypes.WARNING_LOW_TEMP;
		if (str.contains(ResponseTypes.WARNING_UNDER_5_DEGREES.getText()))
			return ResponseTypes.WARNING_UNDER_5_DEGREES;

		return ResponseTypes.UNKOWN;
	}

	public boolean isSetTempCool(String str) {
		// Aktuel:27 - Set:16_Cool
		if (str.contains("Aktuel")) {
			if (str.contains("Cool")) {
				return true;
			}
		}
		return false;
	}

	public boolean isSetTempHeat(String str) {
		// Aktuel:26 - Set:8_Heat
		if (str.contains("Aktuel")) {
			if (str.contains("Heat")) {
				return true;
			}
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
					Toast.makeText(context, "Error in parsing the Set Temperature Trying to parse: " + s, Toast.LENGTH_LONG).show();
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
				Toast.makeText(context, "Error in parsing the Current Temperature Trying to parse: " + s, Toast.LENGTH_LONG).show();
				return result;
			}
		}
		return result;
	}

	public int getGsmSignal(String str) {
		// GSM signal(1-5):2
		int result = -1;

		if (str.contains("(1-5")) {
			String s = str.substring(str.indexOf(":") + 1);
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the GSM Signal Trying to parse: " + s, Toast.LENGTH_LONG).show();
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
				Toast.makeText(context, "Error in parsing the Battery Status. Trying to parse: " + s, Toast.LENGTH_LONG).show();
				return result;
			}
		}
		return result;
	}

	public int getWarningHighTemp(String str) {
		// Advarsel Lav:30 - H¿j:45
		int result = -1;
		if (str.contains("Advarsel")) {
			String s = str.substring(str.indexOf("H¿j") + 4);
			try {
				result = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the High WarningTemp. Trying to parse: " + s, Toast.LENGTH_LONG).show();
				return result;
			}
		}
		return result;
	}

	public int getWarningLowTemp(String str) {
		// Advarsel Lav:30 - H¿j:45
		int result = -1;
		if (str.contains("Advarsel")) {
			int indexLav = str.indexOf("Lav") + 4;
			String s = str.substring(indexLav, indexLav + 2);
			try {
				result = Integer.parseInt(s.trim());
			} catch (NumberFormatException e) {
				Toast.makeText(context, "Error in parsing the Low WarningTemp. Trying to parse: " + s, Toast.LENGTH_LONG).show();
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

	public String getPassword(String str) {
		// Din nye kode er:8110

		String result = "";
		if (str.contains("Din nye kode er:")) {

			String s = str.substring(str.indexOf(":") + 1);
			result = s.trim();
		}
		return result;
	}
	
	public String getBrand(String str){
		// Aktivt Brand: Panasonic
		
		String result = "";
		if (str.contains("Aktivt Brand:")) {

			String s = str.substring(str.indexOf(":") + 1);
			result = s.trim();
		}
		return result;
	}
}
