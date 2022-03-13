package com.ssomar.score.data;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ActionInfoSerializer;
import com.ssomar.score.commands.runnable.player.PlayerRunCommand;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerCommandsQuery {

    private final static String TABLE_COMMANDS_PLAYER = "commands_player";
    private final static String TABLE_COMMANDS_PLAYER_NAME = "Commands Player";

    private final static String COL_UUID_LAUNCHER = "uuid_launcher";
    private final static String COL_UUID_RECEIVER = "uuid_receiver";
    private final static String COL_BRUT_COMMAND = "brut_command";
    private final static String COL_RUN_TIME = "run_time";
    private final static String COL_ACTION_INFO = "action_info";

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMANDS_PLAYER + " (" + COL_UUID_LAUNCHER + " TEXT NOT NULL, " + COL_UUID_RECEIVER + " TEXT NOT NULL, " + COL_BRUT_COMMAND + " TEXT NOT NULL," + COL_RUN_TIME + " LONG NOT NULL, " + COL_ACTION_INFO + " TEXT NOT NULL);";


    public static void createNewTable(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            SCore.getPlugin().getLogger().info(SCore.NAME_2 + " Verification of the table " + TABLE_COMMANDS_PLAYER_NAME + "...");
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
        }
    }


    public static void insertCommand(Connection conn, List<PlayerRunCommand> commands, boolean async) {

        if(async) {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    insertCommand(conn, commands);
                }
            };
            runnable.runTaskAsynchronously(SCore.plugin);
        }
        else insertCommand(conn, commands);
    }


    public static void insertCommand(Connection conn, List<PlayerRunCommand> commands) {

        String sql = "INSERT INTO " + TABLE_COMMANDS_PLAYER + " (" + COL_UUID_LAUNCHER + "," + COL_UUID_RECEIVER + "," + COL_BRUT_COMMAND + "," + COL_RUN_TIME + "," + COL_ACTION_INFO + ") VALUES(?,?,?,?,?)";

        PreparedStatement pstmt = null;

        int i = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            for (PlayerRunCommand command : commands) {
                pstmt.setString(1, command.getLauncherUUID().toString());
                pstmt.setString(2, command.getReceiverUUID().toString());
                pstmt.setString(3, command.getBrutCommand());
                pstmt.setLong(4, command.getRunTime());
                pstmt.setString(5, ActionInfoSerializer.toString(command.getaInfo()));
                pstmt.addBatch();
                i++;
                if (i % 1000 == 0 || i == commands.size()) {
                    pstmt.executeBatch(); // Execute every 1000 items.
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(SCore.NAME_2 + " " + e.getMessage());
            e.printStackTrace();
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


    public static void deleteCommandsForPlayer(Connection conn, UUID uuid) {

        String sql = "DELETE FROM " + TABLE_COMMANDS_PLAYER + " where " + COL_UUID_RECEIVER + "=?";

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

    public static List<PlayerRunCommand> selectCommandsForPlayer(Connection conn, UUID uuid) {
        String sql = "SELECT " + COL_BRUT_COMMAND + "," + COL_RUN_TIME + "," + COL_ACTION_INFO + " FROM " + TABLE_COMMANDS_PLAYER + " where " + COL_UUID_RECEIVER + "=? ORDER BY " + COL_RUN_TIME;

        List<PlayerRunCommand> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uuid.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                String brutCommand = rs.getString(COL_BRUT_COMMAND);
                long runTime = rs.getLong(COL_RUN_TIME);
                ActionInfo aInfo = (ActionInfo) ActionInfoSerializer.fromString(rs.getString(COL_ACTION_INFO));

                PlayerRunCommand pCommand = new PlayerRunCommand(brutCommand, runTime, aInfo);

                list.add(pCommand);
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
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
