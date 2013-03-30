package org.northennerds.enums;

public enum SettingsNames {
	isOldController("isOldController"),phoneNum("phoneNumber"), password("password"), minTemp("minTemp"), maxTemp("maxTemp"), deviceName("deviceName")
	, HeatTemp("HeatTemp"), CoolTemp("CoolTemp"),GSMBat("GSMBat"), GSMSignal("GSMSignal");

	public String name;

	SettingsNames(String name) {
		this.name = name;
	}
	
	
	
//	public static final String PREFSNAME = "CrankUpDaHeatPREFERENCES";
//	public static final String PREFS_isOldController = "isOldController";
//	public static final String PREFS_phoneNum = "phoneNum";
//	public static final String PREFS_password = "password";
//	public static final String PREFS_minTemp = "minTemp";
//	public static final String PREFS_maxTemp = "maxTemp";
//	public static final String PREFS_deviceName = "deviceName";
//	public static final String PREFS_HeatingTemp = "HeatTemp";
//	public static final String PREFS_CoolingTemp = "CoolTemp";
//	public static final String PREFS_GSMBat = "GSMBat";
//	public static final String PREFS_GSMSignal = "GSMSignal";
}
