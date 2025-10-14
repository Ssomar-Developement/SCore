package com.ssomar.score.data;


import com.ssomar.score.SCore;
import com.ssomar.score.SsomarDev;
import com.ssomar.score.commands.runnable.player.commands.absorption.AbsorptionObject;
import com.ssomar.score.utils.logging.Utils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AbsorptionQuery {
    // table details
    private static final String TABLE_ID = "temp_absorptions";
    // column names
    private static final String COL_ABSORPTION_UUID = "absorption_uuid";
    private static final String COL_PLAYER_UUID = "player_uuid";
    private static final String COL_ABSORPTION_AMOUNT = "absorption";
    private static final String COL_EXPIRY_TIME = "expiry_time";
    // create table query
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ID + " (" +
                    COL_ABSORPTION_UUID + " TEXT NOT NULL, " +
                    COL_PLAYER_UUID + " TEXT NOT NULL, " +
                    COL_ABSORPTION_AMOUNT + " DOUBLE NOT NULL, " +
                    COL_EXPIRY_TIME + " BIGINT NOT NULL, " +
                    "PRIMARY KEY (" + COL_ABSORPTION_UUID + ")" +
                    ");";

    public static void createNewTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Creating table &6AbsorptionQuery &7if not exists...");
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            SCore.plugin.getLogger().severe("ERROR WHILE CREATING TABLE "+TABLE_ID+" IN DATABASE: "+e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param conn
     * @param absorptionUUID to assign a unique key value. Mainly to handle a specific absorption in their own method calls. See AbsorptionManager.java's logic for more details
     * @param playerUUID to be able to indicate who owns that absorption
     * @param absorption_amount the amount of absorption this temp absorption provided to the player
     * @param expiry_time the unix time of when it will expire
     */
    public static void insertToRecords(Connection conn, UUID absorptionUUID, UUID playerUUID, double absorption_amount, long expiry_time) {
        // this testmsg call exists because as of this writing, I'm checking why are there no inserts
        SsomarDev.testMsg(ChatColor.GOLD+"[#s0017] AbsorptionQuery.insertToRecords() is triggered", true);
        final String insertQuery = "INSERT INTO "+TABLE_ID+" ("
                +COL_ABSORPTION_UUID+","
                +COL_PLAYER_UUID+","
                +COL_ABSORPTION_AMOUNT+","
                +COL_EXPIRY_TIME
                +") VALUES (?, ?, ?, ?);";

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, absorptionUUID.toString());
            stmt.setString(2, playerUUID.toString());
            stmt.setDouble(3, absorption_amount);
            stmt.setLong(4, expiry_time);
            stmt.execute();
        } catch (Exception e) {
            // temp
            SCore.plugin.getLogger().warning("There was complication with the insert query for AbsorptionQuery.java");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method mainly queries the mysql database and look for expired absorptions tied to the target player's
     * uuid and then remove it.
     * @param conn
     * @param playerUUID to get the player's expired absorptions
     * @param absorptionUUID (optional) to remove a specific expired absorption
     * @return ArrayList of AbsorptionObject
     */
    public static ArrayList<AbsorptionObject> getAbsorptionsToRemove(Connection conn, String playerUUID, String absorptionUUID) {
        // the reason this testmsg is here is for as of this writing, I am checking why are records not getting deleted after a while in the db browser assuming the player is online during the deletion
        SsomarDev.testMsg(ChatColor.GOLD+"[#s0018] &a &6 AbsorptionQuery.getAbsorptionsToRemove() is triggered", true);
        ArrayList<AbsorptionObject> returnArray = new ArrayList<>();

        // the reason this variable is here is to know the exact point in time this method got executed
        // because if the .currentTimeMillis() method is passed in the query as a temp value, it's going to create huge
        // complications if we also want to remove these expired absorptions.
        long currentTimeOfExecution = System.currentTimeMillis();

        // prepare what queries to use
        PreparedStatement stmt = null;
        ResultSet rset = null;
        final String selectQuery;
        final String deleteQuery;
        if (absorptionUUID != null) {
            selectQuery = "SELECT * FROM " + TABLE_ID + " WHERE " + COL_PLAYER_UUID + "=? AND " + COL_EXPIRY_TIME + "<? AND "+COL_ABSORPTION_UUID+"=?;";
        } else selectQuery = "SELECT * FROM " + TABLE_ID + " WHERE " + COL_PLAYER_UUID + "=? AND " + COL_EXPIRY_TIME + " < ?;";
        if (absorptionUUID != null) {
            deleteQuery = "DELETE FROM "+TABLE_ID+" WHERE "+COL_PLAYER_UUID+"=? AND "+COL_EXPIRY_TIME+ "<? AND "+COL_ABSORPTION_UUID+"=?;";
        } else deleteQuery = "DELETE FROM "+TABLE_ID+" WHERE "+COL_PLAYER_UUID+"=? AND "+COL_EXPIRY_TIME+" < ?;";

        // start querying
        try {
            stmt = conn.prepareStatement(selectQuery);
            stmt.setString(1, playerUUID);
            stmt.setLong(2, currentTimeOfExecution);
            if (absorptionUUID != null) stmt.setString(3, absorptionUUID);

            rset = stmt.executeQuery();

            while (rset.next()) {
                returnArray.add(new AbsorptionObject(
                        UUID.fromString(rset.getString(1)),
                        UUID.fromString(rset.getString(2)),
                        rset.getDouble(3),
                        rset.getLong(4)
                ));
            }

            // time to delete these expired absorptions
            stmt = conn.prepareStatement(deleteQuery);
            stmt.setString(1, playerUUID);
            stmt.setLong(2, currentTimeOfExecution);
            if (absorptionUUID != null) stmt.setString(3, absorptionUUID);
            stmt.executeUpdate();

        } catch (Exception e) {
            return returnArray;
        } finally {
            if (rset != null) try { stmt.close(); } catch (Exception ignored) {}
            if (stmt != null) try { stmt.close(); } catch (Exception ignored) {}
        }

        return returnArray;
    }


}
