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

	// TODO - Remove this method. Enum has a method called valueOf(String) that returns the Enum for a String,
	// so this code is redundant.
	public Brands getBrand(String name) {
		Brands result = null;

		if (name.equals(Brands.BOSCH.getName()))
			return Brands.BOSCH;
		else if (name.equals(Brands.DAIKIN.getName()))
			return Brands.DAIKIN;

		else if (name.equals(Brands.ELECTROLUX_NEW.getName()))
			return Brands.ELECTROLUX_NEW;

		else if (name.equals(Brands.ELECTROLUX_OLD.getName()))
			return Brands.ELECTROLUX_OLD;

		else if (name.equals(Brands.FUJITSU.getName()))
			return Brands.FUJITSU;

		else if (name.equals(Brands.HAIER.getName()))
			return Brands.HAIER;

		else if (name.equals(Brands.IVT.getName()))
			return Brands.IVT;

		else if (name.equals(Brands.LG.getName()))
			return Brands.LG;

		else if (name.equals(Brands.MITSUBISHI.getName()))
			return Brands.MITSUBISHI;

		else if (name.equals(Brands.PANASONIC.getName()))
			return Brands.PANASONIC;

		else if (name.equals(Brands.TOSHIBA.getName()))
			return Brands.TOSHIBA;

		else if (name.equals(Brands.ZIBRA.getName()))
			return Brands.ZIBRA;

		return result;
	}

}
