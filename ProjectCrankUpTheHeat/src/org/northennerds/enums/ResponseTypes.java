package org.northennerds.enums;

public enum ResponseTypes {

	SETUpdate("**Set opdateret**"), TempHIGHUpdate("**H¿j temp opdateret**"), TempLOWUpdate("**Lav temp opdateret**"), Brands(
			"**Brand status**"), Status("**Status**"), AlarmNumre("**Alarmnumre**"), WarningHIGHTemp(
			"**ADVARSEL H¿j temp**"), WarningLOWTemp("**ADVARSEL Lav temp**"), NORMALIZEDNotification(
			"**Temp normal igen**"), PowerFailure("**Netforsyning afbrudt**"), PowerRestored("**Netforsyning tilbage**"), WarningUNDER5Degrees(
			"**ADVARSEL under 5gC**"), PASSWORDUpdate("TODO"), UNKOWN("Unknown Repose from device");

	private String text;

	ResponseTypes(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
