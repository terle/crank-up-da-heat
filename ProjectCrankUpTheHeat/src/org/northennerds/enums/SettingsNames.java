package org.northennerds.enums;

public enum SettingsNames {
	prefsName("CrankUpDaHeatPREFERENCES"), isOldController("isOldController"), DevicePhoneNum("phoneNumber"), password(
			"password"), warningTempLOW("minTemp"), warningTempHIGH("maxTemp"), deviceName("deviceName"), HeatTemp(
			"HeatTemp"), CoolTemp("CoolTemp"), GSMBat("GSMBat"), GSMSignal("GSMSignal"), AktuelTemp("AktuelTemp"), AlarmNum01(
			"AlarmNumber01"), AlarmNum02("AlarmNumber02"), AlarmNum03("AlarmNumber03"), AlarmNum04("AlarmNumber04");

	private String name;

	SettingsNames(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
