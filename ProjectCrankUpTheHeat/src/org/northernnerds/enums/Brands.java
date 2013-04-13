package org.northernnerds.enums;

public enum Brands {
	Panasonic("Panasonic"),Haier("Haier"), Daikin("Daikin"), LG("LG"), Bosch("Bosch"), IVT("IVT"),Electrolux_old("Elux1"), Mitsubishi("Mitsubishi"),
	Toshiba("Toshiba"), Electrolux_new("Elux2"), Fujitsu("Fujitsu"), Zibro("");

	private String name;

	Brands(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
