package com.ssomar.score.data;

import com.ssomar.score.SCore;
import com.ssomar.score.utils.Utils;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandsQuery {

    private final static String TABLE_COMMANDS = "commands";
    private final static String TABLE_COMMANDS_NAME = "Commands";

    private final static String COL_PLAYER = "player";
    private final static String COL_COMMAND = "command";

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMANDS + " (" + COL_PLAYER + " TEXT , " + COL_COMMAND + " TEXT NOT NULL);";


    public static void createNewTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Creating table &6" + TABLE_COMMANDS_NAME + " &7if not exists...");
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
        }
    }


    public static void insertCommand(Connection conn, Player p, String command) {

        String sql = "INSERT INTO " + TABLE_COMMANDS + " (" + COL_PLAYER + "," + COL_COMMAND + ") VALUES(?,?)";

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getName());
            pstmt.setString(2, command);
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


    public static void deleteCommandsForPlayer(Connection conn, Player player) {

        String sql = "DELETE FROM " + TABLE_COMMANDS + " where " + COL_PLAYER + "=?";

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getName());
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

    public static List<String> selectCommandsForPlayer(Connection conn, Player player) {
        String sql = "SELECT " + COL_COMMAND + " FROM " + TABLE_COMMANDS + " where " + COL_PLAYER + "=?";

        List<String> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, player.getName());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString(COL_COMMAND));
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

}
