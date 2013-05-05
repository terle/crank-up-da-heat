package org.northernnerds.enums;

public enum ResponseTypes {

	SET_UPDATE("**Set opdateret**"), TEMP_HIGH_UPDATE("**H¿j temp opdateret**"), 
	TEMP_LOW_UPDATE("**Lav temp opdateret**"), BRANDS("**Brand status**"), 
	STATUS("**Status**"), ALARM_NUMBERS("**Alarmnumre**"), WARNING_HIGH_TEMP("**ADVARSEL H¿j temp**"), 
	WARNING_LOW_TEMP("**ADVARSEL Lav temp**"), NORMALIZED_NOTIFICATION("**Temp normal igen**"), 
	POWER_FAILURE("**Netforsyning afbrudt**"), POWER_RESTORED("**Netforsyning tilbage**"), 
	WARNING_UNDER_5_DEGREES("**ADVARSEL under 5gC**"), PASSWORD_UPDATE("**Din kode er ¾ndret**"), 
	UNKOWN("Unknown Repose from device");

	private String text;

	ResponseTypes(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
