package org.northernnerds.enums;

public enum Brands {
	PANASONIC("Panasonic"), HAIER("Haier"), DAIKIN("Daikin"), LG("LG"), 
	BOSCH("Bosch"), IVT("IVT"), ELECTROLUX_OLD("Elux1"), MITSUBISHI("Mitsubishi"), 
	TOSHIBA("Toshiba"), ELECTROLUX_NEW("Elux2"), FUJITSU("Fujitsu"), ZIBRA("Zibro");

	private String name;

	Brands(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
