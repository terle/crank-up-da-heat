package org.northernnerds.enums;

public enum Brands {
	Panasonic("Panasonic"), Haier("Haier"), Daikin("Daikin"), LG("LG"), Bosch(
			"Bosch"), IVT("IVT"), Electrolux_old("Elux1"), Mitsubishi(
			"Mitsubishi"), Toshiba("Toshiba"), Electrolux_new("Elux2"), Fujitsu(
			"Fujitsu"), Zibro("Zibro");

	private String name;

	Brands(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Brands getBrand(String name) {
		Brands result = null;

		if (name.equals(Brands.Bosch.getName()))
			return Brands.Bosch;
		else if (name.equals(Brands.Daikin.getName()))
			return Brands.Daikin;

		else if (name.equals(Brands.Electrolux_new.getName()))
			return Brands.Electrolux_new;

		else if (name.equals(Brands.Electrolux_old.getName()))
			return Brands.Electrolux_old;

		else if (name.equals(Brands.Fujitsu.getName()))
			return Brands.Fujitsu;

		else if (name.equals(Brands.Haier.getName()))
			return Brands.Haier;

		else if (name.equals(Brands.IVT.getName()))
			return Brands.IVT;

		else if (name.equals(Brands.LG.getName()))
			return Brands.LG;

		else if (name.equals(Brands.Mitsubishi.getName()))
			return Brands.Mitsubishi;

		else if (name.equals(Brands.Panasonic.getName()))
			return Brands.Panasonic;

		else if (name.equals(Brands.Toshiba.getName()))
			return Brands.Toshiba;

		else if (name.equals(Brands.Zibro.getName()))
			return Brands.Zibro;

		return result;
	}

}
