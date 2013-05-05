package org.northernnerds.enums;

public enum SettingsNames {
	PREFERENCES_NAME("CrankUpDaHeatPREFERENCES"), IS_OLD_CONTROLLER("isOldController"), 
	DEVICE_PHONENO("phoneNumber"), DEVICE_PASSWORD("password"), WARNING_TEMP_LOW("minTemp"), 
	WARNING_TEMP_HIGH("maxTemp"), DEVICE_NAME("deviceName"), HEAT_TEMP("HeatTemp"), 
	COOL_TEMP("CoolTemp"), CURRENT_PROGRAM("CurrentProgram"), GSM_BATTERY("GSMBat"), 
	GSM_SIGNAL("GSMSignal"), CURRENT_TEMP("AktuelTemp"), ALARM_NUMBER_01("AlarmNumber01"), 
	ALARM_NUMBER_02("AlarmNumber02"), ALARM_NUMBER_03("AlarmNumber03"), ALARM_NUMBER_04("AlarmNumber04"), 
	BRAND_NAME("Brand Name"), SHOULD_SHOW_WIZARD("ShowWizard");

	private String name;

	SettingsNames(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
