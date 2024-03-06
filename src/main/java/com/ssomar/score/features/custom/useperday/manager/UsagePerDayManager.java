package com.ssomar.score.features.custom.useperday.manager;

import com.ssomar.score.data.Database;
import com.ssomar.score.data.UsePerDayQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsagePerDayManager {

    private static UsagePerDayManager instance;
    /* date, player, id -> use */
    private final Map<String, Map<String, Map<String, Integer>>> usagePerDayMap;

    public UsagePerDayManager() {
        usagePerDayMap = UsePerDayQuery.loadUsePerDay(Database.getInstance().connect());
    }

    public static UsagePerDayManager getInstance() {
        if (instance == null) {
            instance = new UsagePerDayManager();
        }
        return instance;
    }

    public void save() {
        UsePerDayQuery.purgeUserPerDay(Database.getInstance().connect());
        UsePerDayQuery.insertPlayerUserPerDay(Database.getInstance().connect(), usagePerDayMap);
    }

    public void insertUsage(String player, String itemid) {
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy");
        String date = formater.format(today);
        if (usagePerDayMap.containsKey(date)) {
            Map<String, Map<String, Integer>> dateMap = usagePerDayMap.get(date);
            if (dateMap.containsKey(player)) {
                Map<String, Integer> playerMap = dateMap.get(player);
                if (playerMap.containsKey(itemid)) {
                    playerMap.put(itemid, playerMap.get(itemid) + 1);
                } else playerMap.put(itemid, 1);
            } else {
                Map<String, Integer> playerMap = new HashMap<>();
                playerMap.put(itemid, 1);
                dateMap.put(player, playerMap);
            }
        } else {
            Map<String, Map<String, Integer>> dateMap = new HashMap<>();
            Map<String, Integer> playerMap = new HashMap<>();
            playerMap.put(itemid, 1);
            dateMap.put(player, playerMap);
        }
    }

    public int getCount(String player, String itemid) {
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy");
        String date = formater.format(today);
        int nb = 0;
        if (usagePerDayMap.containsKey(date)) {
            if (usagePerDayMap.get(date).containsKey(player)) {
                if (usagePerDayMap.get(date).get(player).containsKey(itemid)) {
                    nb = usagePerDayMap.get(date).get(player).get(itemid);
                }
            }
        }
        return nb;
    }
}
