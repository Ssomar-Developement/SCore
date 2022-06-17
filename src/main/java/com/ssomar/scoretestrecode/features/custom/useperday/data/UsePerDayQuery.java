package com.ssomar.scoretestrecode.features.custom.useperday.data;

import com.iridium.iridiumskyblock.dependencies.ormlite.stmt.query.In;
import com.ssomar.score.SCore;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class UsePerDayQuery {

    private final static String TABLE_USE_PER_DAY = "useperday";
    private final static String TABLE_USE_PER_DAY_NAME = "UsePerDay";

    private final static String COL_DATE = "date";
    private final static String COL_PLAYER = "player";
    private final static String COL_ID = "id";
    private final static String COL_NB = "nb";

    public static Boolean DEBUG = false;

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USE_PER_DAY + " (" + COL_DATE + " TEXT NOT NULL, " + COL_PLAYER + " TEXT NOT NULL, " + COL_ID + " TEXT NOT NULL, " + COL_NB + " INTEGER NOT NULL);";


    public static void createNewTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            SCore.plugin.getLogger().info("Verification of the table " + TABLE_USE_PER_DAY_NAME + "...");
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            SCore.plugin.getLogger().severe(e.getMessage());
        }
    }

    public static boolean insertPlayerUserPerDay(Connection conn, Map<String, Map<String, Map<String, Integer>>> map) {
        purgeUserPerDay(conn);

        String sql = "INSERT INTO " + TABLE_USE_PER_DAY + " (" + COL_DATE + "," + COL_PLAYER + ", " + COL_ID + ", " + COL_NB + ") VALUES(?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy");
        String date = formater.format(today);
        int i = 0;
        if (!map.containsKey(date)) return true;

        try {
            pstmt = conn.prepareStatement(sql);
            for (String player : map.get(date).keySet()) {
                i++;
                for (String item : map.get(date).get(player).keySet()) {
                    int nb = map.get(date).get(player).get(item);
                    pstmt.setString(1, date);
                    pstmt.setString(2, player);
                    pstmt.setString(3, item);
                    pstmt.setInt(4, nb);
                    if (DEBUG)
                        System.out.println("[ExecutableItems] SAVE USPD >" + date + "<" + player + ">" + item + "<" + nb + ">");
                    pstmt.addBatch();
                }

                if (i % 15 == 0 || i == map.size()) {
                    pstmt.executeBatch(); // Execute every 1000 items.
                }
            }
        } catch (SQLException e) {
            SCore.plugin.getLogger().severe(e.getMessage());
            return false;
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static void purgeUserPerDay(Connection conn) {
        String sql = "DELETE FROM " + TABLE_USE_PER_DAY;

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            SCore.plugin.getLogger().severe(e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static Map<String, Map<String, Map<String, Integer>>> loadUsePerDay(Connection conn) {
        String sql = "SELECT * FROM " + TABLE_USE_PER_DAY + " where " + COL_DATE + "=?";

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Map<String, Map<String, Map<String, Integer>>> map = new HashMap<>();
        Map<String, Map<String, Integer>> datas = new HashMap<>();

        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy");
        String date = formater.format(today);

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            rs = pstmt.executeQuery();

            if (DEBUG) System.out.println("[ExecutableItems] TRY LOAD USPD >" + date);
            while (rs.next()) {
                String player = rs.getString(COL_PLAYER);
                String item = rs.getString(COL_ID);
                int nb = rs.getInt(COL_NB);
                if (datas.containsKey(player)) {
                    Map<String, Integer> map2 = datas.get(player);
                    if (map2.containsKey(item)) {
						map2.put(item, map2.get(item) + nb);
                        if (DEBUG)
                            System.out.println("[ExecutableItems] LOAD USPD >" + date + "<" + player + ">" + item +" >" + nb);
                    } else {
                        map2.put(item, nb);
                        if (DEBUG)
                            System.out.println("[ExecutableItems] LOAD USPD >" + date + "<" + player + ">" + item + ">" + nb);
                    }
                } else {
                    if (DEBUG)
                        System.out.println("[ExecutableItems] LOAD USPD >" + date + "<" + player + ">" + item + ">" + nb);
                    Map<String, Integer> map2 = new HashMap<>();
                    map2.put(item, nb);
                    datas.put(player, map2);
                }
            }
            map.put(date, datas);
        } catch (SQLException e) {
            SCore.plugin.getLogger().severe(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

}
