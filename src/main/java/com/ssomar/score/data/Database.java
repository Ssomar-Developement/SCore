package com.ssomar.score.data;

import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;
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

    public static boolean useMySQL = false;

    public static boolean DEBUG = false;

    private String fileName;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void load() {
        fileName = "data.db";
        if (!GeneralConfig.getInstance().isUseMySQL()) createNewDatabase();
        SecurityOPQuery.createNewTable(connect());
        CommandsQuery.createNewTable(connect());
        CooldownsQuery.createNewTable(connect());
        PlayerCommandsQuery.createNewTable(connect());
        EntityCommandsQuery.createNewTable(connect());
        BlockCommandsQuery.createNewTable(connect());
        UsePerDayQuery.createNewTable(connect());
        AbsorptionQuery.createNewTable(connect());
        TemporaryAttributeQuery.createNewTable(connect());
        /* Variables in DB only in MYSQL to share infos between multiple servers */
        if(GeneralConfig.getInstance().isUseMySQL()) VariablesQuery.createNewTable(connect());
    }

    public void createNewDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:sqlite:" + SCore.dataFolder + "/" + fileName;

        try (Connection conn = connect()) {
            if (conn != null) {
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7Connection to the db...");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect() {
        return connect(false);
    }

    public Connection connect(boolean forceReopen) {
        // SQLite connection string
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String urlLocal = "jdbc:sqlite:" + SCore.dataFolder + "/" + fileName;

        boolean needOpenConnection = true;
        try {
            if (conn != null && !conn.isClosed()) {
                // java.lang.AbstractMethodError: Receiver class org.sqlite.Conn does not define or inherit an implementation of the resolved method 'abstract boolean isValid(int)' of interface java.sql.Connection.
                try{
                    needOpenConnection = !conn.isValid(2);
                }
                catch (AbstractMethodError e){
                    needOpenConnection = false;
                }
            }
        } catch (SQLException e) {
            needOpenConnection = true;
        }

        if(conn != null && forceReopen) {
            try {
                conn.close();
                if(DEBUG) Utils.sendConsoleMsg("Connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            needOpenConnection = true;
        }

        if (needOpenConnection) {
            useMySQL = false;
            try {
                String where = GeneralConfig.getInstance().isUseMySQL() ? "MySQL" : "In Local";
                if(!forceReopen) Utils.sendConsoleMsg(SCore.NAME_COLOR + " &7will connect to the database hosted: &6" + where);
                if (GeneralConfig.getInstance().isUseMySQL()) {
                    try {
                        if (SCore.is1v17Plus()) conn = new Database1v18().get1v18Connection();
                        else conn = new DatabaseOld().getOldConnection();
                        useMySQL = true;
                    } catch (SQLException e) {
                        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cError when trying to connect to the database "+e.getMessage());
                        conn = DriverManager.getConnection(urlLocal);
                    }
                    catch (NoClassDefFoundError e) {
                        Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cError the library to connect to mysql is not present on your server (local db used instead) "+e.getMessage());
                        conn = DriverManager.getConnection(urlLocal);
                    }
                } else conn = DriverManager.getConnection(urlLocal);

                //System.out.println("[ExecutableItems] "+"Connexion OKAY");
            } catch (SQLException e) {
                //e.printStackTrace();
                Utils.sendConsoleMsg(SCore.NAME_COLOR + " &cError when trying to connect to the database "+e.getMessage());
            }
        }

        return conn;
    }


}
