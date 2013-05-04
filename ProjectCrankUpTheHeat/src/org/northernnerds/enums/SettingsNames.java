package org.northernnerds.enums;

public enum SettingsNames {
	prefsName("CrankUpDaHeatPREFERENCES"), isOldController("isOldController"), 
	devicePhoneNum("phoneNumber"), 
	devicePassword("password"), 
	warningTempLOW("minTemp"), 
	warningTempHIGH("maxTemp"), 
	deviceName("deviceName"), 
	HeatTemp("HeatTemp"), 
	CoolTemp("CoolTemp"),
	currentProgram("CurrentProgram"), 
	GSMBat("GSMBat"), 
	GSMSignal("GSMSignal"), 
	currentTemperatur("AktuelTemp"), 
	AlarmNum01("AlarmNumber01"), 
	AlarmNum02("AlarmNumber02"), 
	AlarmNum03("AlarmNumber03"), 
	AlarmNum04("AlarmNumber04"), 
	BrandName("Brand Name"),
	ShouldShowWizzard("ShowWizzard");

	private String name;

	SettingsNames(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
