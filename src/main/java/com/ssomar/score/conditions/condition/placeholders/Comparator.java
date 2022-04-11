package com.ssomar.score.conditions.condition.placeholders;

public enum Comparator {

	EQUALS("="),
	DIFFERENT("!="),
	INFERIOR("<"),
	SUPERIOR(">"),
	INFERIOR_OR_EQUALS("<="),
	SUPERIOR_OR_EQUALS(">=");

	private String symbol;

	Comparator(String symbol) {
		this.symbol = symbol;
	}

	public <T> boolean verify(T a, T b) {
		//SsomarDev.testMsg(a+" / "+b+" > "+ a.equals(b));
		switch(this) {
		case EQUALS:
			return a.equals(b);
		case DIFFERENT:
			return (!a.equals(b));
		case INFERIOR:
			if(a instanceof Double) {
				Double pA = (Double)a;
				Double pB = (Double)b;
				return pA<pB;
			}
			break;
		case SUPERIOR:
			if(a instanceof Double) {
				Double pA = (Double)a;
				Double pB = (Double)b;
				return pA>pB;
			}
			break;
		case INFERIOR_OR_EQUALS:
			if(a instanceof Double) {
				Double pA = (Double)a;
				Double pB = (Double)b;
				return pA<=pB;
			}
			break;
		case SUPERIOR_OR_EQUALS:
			if(a instanceof Double) {
				Double pA = (Double)a;
				Double pB = (Double)b;
				return pA>=pB;
			}
			break;
		default:
			break;
		}


		return false;
	}

	public Comparator getNext() {
		switch(this) {
		case EQUALS:
			return DIFFERENT;
		case DIFFERENT:
			return INFERIOR;
		case INFERIOR:
			return SUPERIOR;
		case SUPERIOR:
			return INFERIOR_OR_EQUALS;
		case INFERIOR_OR_EQUALS:
			return SUPERIOR_OR_EQUALS;
		case SUPERIOR_OR_EQUALS:
			return EQUALS;
		default:
			return EQUALS;
		}
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


}
