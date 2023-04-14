package com.ssomar.score.data;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
import com.ssomar.score.features.custom.useperday.data.UsePerDayQuery;
import com.ssomar.score.utils.logging.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author sqlitetutorial.net
 */
public class Database {

    private static Database instance;

    private static Connection conn;

    private String fileName;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void load() {
        if (!GeneralConfig.getInstance().isUseMySQL()) createNewDatabase("data.db");
        SecurityOPQuery.createNewTable(connect());
        CommandsQuery.createNewTable(connect());
        CooldownsQuery.createNewTable(connect());
        PlayerCommandsQuery.createNewTable(connect());
        EntityCommandsQuery.createNewTable(connect());
        BlockCommandsQuery.createNewTable(connect());
        UsePerDayQuery.createNewTable(connect());
    }

    public void createNewDatabase(String fileName) {

        this.fileName = fileName;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:" + SCore.plugin.getDataFolder() + "/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7onnection to the db...");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        // SQLite connection string
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String urlLocal = "jdbc:sqlite:" + SCore.plugin.getDataFolder() + "/" + fileName;

        boolean needOpenConnection;
        try {
            needOpenConnection = conn == null || conn.isClosed();
        } catch (SQLException e) {
            needOpenConnection = true;
        }

        if (needOpenConnection) {
            try {
                if (GeneralConfig.getInstance().isUseMySQL()) {
                    try {
                        if (SCore.is1v18Plus()) conn = new Database1v18().get1v18Connection();
                        else conn = new DatabaseOld().getOldConnection();
                    } catch (SQLException e) {
                        SCore.plugin.getLogger().severe(SCore.NAME_2 + " Error when trying to connect to your mysql database (local db used instead)");
                        conn = DriverManager.getConnection(urlLocal);
                    }
                    catch (NoClassDefFoundError e) {
                        SCore.plugin.getLogger().severe(SCore.NAME_2 + " Error the library to connect to mysql is not present on your server (local db used instead)");
                        conn = DriverManager.getConnection(urlLocal);
                    }
                } else conn = DriverManager.getConnection(urlLocal);

                //System.out.println("[ExecutableItems] "+"Connexion OKAY");
            } catch (SQLException e) {
                //e.printStackTrace();
                SCore.plugin.getLogger().severe(SCore.NAME_2 + " " + e.getMessage());
            }
        }

        return conn;
    }


}