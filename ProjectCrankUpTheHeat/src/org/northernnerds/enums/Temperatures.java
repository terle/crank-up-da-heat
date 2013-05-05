package org.northernnerds.enums;

public enum Temperatures {
	T00(47), T08(-1), T10(-24), T16(-69), T17(-86), T18(-103), T19(-120), 
	T20(-137), T21(-154), T22(-171), T23(-188), T24(-204);

	private int angle;

	Temperatures(int angle) {
		this.angle = angle;
	}

	public int getAngle() {
		return this.angle;
	}
}
