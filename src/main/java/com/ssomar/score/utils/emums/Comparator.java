package com.ssomar.score.utils.emums;

import java.util.List;

public enum Comparator {

    DIFFERENT("!="),
    INFERIOR_OR_EQUALS("<="),
    SUPERIOR_OR_EQUALS(">="),
    INFERIOR("<"),
    SUPERIOR(">"),
    EQUALS("="),
    IS_CONTAINED_IN("(("),
    IS_NOT_CONTAINED_IN("(/");

    private String symbol;

    Comparator(String symbol) {
        this.symbol = symbol;
    }

    public <T,U> boolean verify(T a, U b) {
        //SsomarDev.testMsg(a+" / "+b+" > "+ a.equals(b));
        switch (this) {
            case EQUALS:
                return a.equals(b);
            case DIFFERENT:
                return (!a.equals(b));
            case INFERIOR:
                if (a instanceof Double && b instanceof Double) {
                    Double pA = (Double) a;
                    Double pB = (Double) b;
                    return pA < pB;
                }
                break;
            case SUPERIOR:
                if (a instanceof Double && b instanceof Double) {
                    Double pA = (Double) a;
                    Double pB = (Double) b;
                    return pA > pB;
                }
                break;
            case INFERIOR_OR_EQUALS:
                if (a instanceof Double && b instanceof Double) {
                    Double pA = (Double) a;
                    Double pB = (Double) b;
                    return pA <= pB;
                }
                break;
            case SUPERIOR_OR_EQUALS:
                if (a instanceof Double && b instanceof Double) {
                    Double pA = (Double) a;
                    Double pB = (Double) b;
                    return pA >= pB;
                }
                break;
            case IS_CONTAINED_IN:
                    if (a instanceof String && b instanceof String) {
                        String pA = (String) a;
                        String pB = (String) b;
                        return pB.contains(pA);
                    }
                    else if (a instanceof String && b instanceof List) {
                        String pA = (String) a;
                        List pB = (List) b;
                        return pB.contains(pA);
                    }
                    break;
            case IS_NOT_CONTAINED_IN:
                if (a instanceof String && b instanceof String) {
                    String pA = (String) a;
                    String pB = (String) b;
                    return (!pB.contains(pA));
                }
                else if (a instanceof String && b instanceof List) {
                    String pA = (String) a;
                    List pB = (List) b;
                    return (!pB.contains(pA));
                }
                break;
            default:
                break;
        }


        return false;
    }

    public Comparator getNext() {
        switch (this) {
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
                return IS_CONTAINED_IN;
            case IS_CONTAINED_IN:
                return IS_NOT_CONTAINED_IN;
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
