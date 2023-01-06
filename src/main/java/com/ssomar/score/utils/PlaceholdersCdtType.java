package com.ssomar.score.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public enum PlaceholdersCdtType {

    PLAYER_NUMBER,
    PLAYER_STRING,
    PLAYER_PLAYER,
    TARGET_NUMBER,
    TARGET_STRING,
    TARGET_TARGET,
    PLAYER_TARGET;


    public static List<PlaceholdersCdtType> getpCdtTypeWithNumber() {
        List<PlaceholdersCdtType> result = new ArrayList<>();
        result.add(PlaceholdersCdtType.PLAYER_NUMBER);
        result.add(PlaceholdersCdtType.TARGET_NUMBER);

        return result;
    }

    public static List<PlaceholdersCdtType> getpCdtTypeWithPlayer() {
        List<PlaceholdersCdtType> result = new ArrayList<>();
        result.add(PlaceholdersCdtType.PLAYER_NUMBER);
        result.add(PlaceholdersCdtType.PLAYER_STRING);
        result.add(PlaceholdersCdtType.PLAYER_PLAYER);
        result.add(PlaceholdersCdtType.PLAYER_TARGET);

        return result;
    }

    public static List<PlaceholdersCdtType> getpCdtTypeWithTarget() {
        List<PlaceholdersCdtType> result = new ArrayList<>();
        result.add(PlaceholdersCdtType.TARGET_NUMBER);
        result.add(PlaceholdersCdtType.TARGET_STRING);
        result.add(PlaceholdersCdtType.TARGET_TARGET);

        return result;
    }

    public static List<PlaceholdersCdtType> getpCdtTypeWithString() {
        List<PlaceholdersCdtType> result = new ArrayList<>();
        result.add(PlaceholdersCdtType.PLAYER_STRING);
        result.add(PlaceholdersCdtType.TARGET_STRING);

        return result;
    }

    public PlaceholdersCdtType nextPlaceholdersCdtType(PlaceholdersCdtType pct) {
        boolean next = false;
        for (PlaceholdersCdtType check : getPlaceholdersCdtType()) {
            if (check.equals(pct)) {
                next = true;
                continue;
            }
            if (next) return check;
        }
        return getPlaceholdersCdtType().get(0);
    }

    public PlaceholdersCdtType prevPlaceholdersCdtType(PlaceholdersCdtType pct) {
        int i = -1;
        int cpt = 0;
        for (PlaceholdersCdtType check : getPlaceholdersCdtType()) {
            if (check.equals(pct)) {
                i = cpt;
                break;
            }
            cpt++;
        }
        if (i == 0) return getPlaceholdersCdtType().get(getPlaceholdersCdtType().size() - 1);
        else return getPlaceholdersCdtType().get(cpt - 1);
    }

    public List<PlaceholdersCdtType> getPlaceholdersCdtType() {
        SortedMap<String, PlaceholdersCdtType> map = new TreeMap<String, PlaceholdersCdtType>();
        for (PlaceholdersCdtType l : PlaceholdersCdtType.values()) {
            map.put(l.name(), l);
        }
        return new ArrayList<>(map.values());
    }

}
