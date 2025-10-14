package com.ssomar.score.data;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.player.commands.absorption.AbsorptionObject;
import com.ssomar.score.utils.logging.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TemporaryAttributeQuery {

    private static final String TABLE_ID = "temp_attributes";
    // column names
    /**
     * The primary key for the temp_attributes table. Also used to give a unique key to an attribute
     */
    private static final String COL_ATTRIBUTE_KEY = "attribute_key";
    /**
     * The ADD_TEMPORARY_ATTRIBUTE works for both players and entities so this column will be named "entity_uuid"
     */
    private static final String COL_ENTITY_UUID = "entity_uuid";
    /**
     * Attributes come in various attribute types
     */
    private static final String COL_ATTRIBUTE_TYPE = "attribute_type";
    /**
     * Stored in unix time/long/bigint
     */
    private static final String COL_EXPIRY_TIME = "expiry_time";
    /**
     * Attributes only accept values in double
     */
    private static final String COL_AMOUNT = "amount";
    // create table query
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ID + " (" +
                    COL_ATTRIBUTE_KEY + " TEXT NOT NULL, " +
                    COL_ATTRIBUTE_TYPE + " TEXT NOT NULL, " +
                    COL_AMOUNT + " DOUBLE NOT NULL, " +
                    COL_ENTITY_UUID + " TEXT NOT NULL, " +
                    COL_EXPIRY_TIME + " BIGINT NOT NULL, " +
                    "PRIMARY KEY (" + COL_ATTRIBUTE_KEY + ")" +
                    ");";

    public static void createNewTable(Connection conn) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Creating table &6ATemporaryAttributesQuery &7if not exists...");
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

    public static void insertToRecords(Connection conn,
                                       String attribute_key,
                                       String attribute_type,
                                       double amount,
                                       String entity_uuid,
                                       long expiry_time) {
        final String insertQuery = "INSERT INTO "+TABLE_ID+" ("
                +COL_ATTRIBUTE_KEY+","
                +COL_ATTRIBUTE_TYPE+","
                +COL_AMOUNT+","
                +COL_ENTITY_UUID+","
                +COL_EXPIRY_TIME
                +") VALUES (?, ?, ?, ?, ?);";

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, attribute_key);
            stmt.setString(2, attribute_type);
            stmt.setDouble(3, amount);
            stmt.setString(4, entity_uuid);
            stmt.setLong(5, expiry_time);
            stmt.execute();
        } catch (Exception e) {
            SCore.plugin.getLogger().warning("There was complication with the insert query for TemporaryAttributeQuery.java: "+e.getMessage());
        } finally {
            if (stmt != null) try { stmt.close(); } catch (Exception ignored) {}
        }
    }

    public static ArrayList<TemporaryAttributeQuery> getTemporaryAttributesToRemove(Connection conn, String entityUUID) {
        ArrayList<TemporaryAttributeQuery> returnArray = new ArrayList<>();
    }

}
