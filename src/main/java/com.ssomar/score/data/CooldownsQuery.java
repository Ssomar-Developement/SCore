package com.ssomar.score.data;

import com.ssomar.score.SCore;
import com.ssomar.scoretestrecode.features.custom.cooldowns.Cooldown;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CooldownsQuery {

    private final static String TABLE_COOLDOWNS = "cooldows";
    private final static String TABLE_COOLDOWNS_NAME = "Cooldowns";

    private final static String COL_ID = "id";
    private final static String COL_UUID = "UUID";
    private final static String COL_COOLDOWN = "cooldown";
    private final static String COL_IS_IN_TICK = "isInTick";
    private final static String COL_IS_GLOBAL = "isGlobal";
    private final static String COL_TIME = "time";
    private final static String COL_LOADED = "loaded";

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COOLDOWNS + " (" + COL_ID + " TEXT NOT NULL, "
            + COL_UUID + " TEXT NOT NULL, "
            + COL_COOLDOWN + " INTEGER NOT NULL, "
            + COL_IS_IN_TICK + " BOOLEAN NOT NULL, "
            + COL_IS_GLOBAL + " BOOLEAN NOT NULL, "
            + COL_TIME + " LONG NOT NULL, "
            + COL_LOADED + " BOOLEAN NOT NULL );";


    public static void createNewTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            SCore.plugin.getLogger().info(SCore.NAME_2 + " Verification of the table " + TABLE_COOLDOWNS_NAME + "...");
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
        }
    }


    public static void insertCooldowns(Connection conn, List<Cooldown> cooldowns) {

        String sql = "INSERT INTO " + TABLE_COOLDOWNS + " (" + COL_ID + "," + COL_UUID + "," + COL_COOLDOWN + "," + COL_IS_IN_TICK + "," + COL_IS_GLOBAL + "," + COL_TIME + "," + COL_LOADED + ") VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstmt = null;
        int i = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            for (Cooldown cd : cooldowns) {
                i++;
                if (cd != null) {
                    pstmt.setString(1, cd.getId());
                    pstmt.setString(2, cd.getEntityUUID() + "");
                    pstmt.setInt(3, cd.getCooldown());
                    pstmt.setBoolean(4, cd.isInTick());
                    pstmt.setBoolean(5, cd.isGlobal());
                    pstmt.setLong(6, cd.getTime());
                    pstmt.setBoolean(7, false);
                    pstmt.addBatch();
                }

                if (i % 1000 == 0 || i == cooldowns.size()) {
                    pstmt.executeBatch(); // Execute every 1000 items.
                }
            }
        } catch (SQLException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
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

    public static List<Cooldown> getCooldownsOf(Connection conn, UUID uuid) {
        String sql = "SELECT " + COL_ID + "," + COL_UUID + "," + COL_COOLDOWN + "," + COL_IS_IN_TICK + "," + COL_IS_GLOBAL + "," + COL_TIME + " FROM " + TABLE_COOLDOWNS + " where " + COL_UUID + "=? AND " + COL_LOADED + "=0";

        List<Cooldown> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                String id = rs.getString(COL_ID);
                String uuidStr = rs.getString(COL_UUID);
                int cd = rs.getInt(COL_COOLDOWN);
                boolean isInTick = rs.getBoolean(COL_IS_IN_TICK);
                boolean isGlobal = rs.getBoolean(COL_IS_GLOBAL);
                long time = rs.getLong(COL_TIME);

                Cooldown cooldown = new Cooldown(id, UUID.fromString(uuidStr), cd, isInTick, time, isGlobal);

                list.add(cooldown);
            }
        } catch (SQLException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
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
        return list;
    }

    public static void deleteCooldownsOf(Connection conn, UUID uuid) {

        String sql = "DELETE FROM " + TABLE_COOLDOWNS + " where " + COL_UUID + "=?";

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
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

}
