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
	
	public Temperatures getTemperature(int i){
		switch(i){
		case 0: return T00;
		case 8: return T08;
		case 10: return T10;
		case 16: return T16;
		case 17: return T17;
		case 18: return T18;
		case 19: return T19;
		case 20: return T20;
		case 21: return T21;
		case 22: return T22;
		case 23: return T23;
		case 24: return T24;
		default: return T18;
		}
	}
}
