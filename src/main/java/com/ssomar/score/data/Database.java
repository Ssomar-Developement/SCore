package com.ssomar.score.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.ssomar.score.SCore;
import com.ssomar.score.config.GeneralConfig;

/**
 *
 * @author sqlitetutorial.net
 */
public class Database {

	private static Database instance;

	private String fileName;
	
	public void load() {
		if(!GeneralConfig.getInstance().isUseMySQL()) createNewDatabase("data.db");
		SecurityOPQuery.createNewTable(connect());
		CommandsQuery.createNewTable(connect());
		CooldownsQuery.createNewTable(connect());
		PlayerCommandsQuery.createNewTable(connect());
		EntityCommandsQuery.createNewTable(connect());
		BlockCommandsQuery.createNewTable(connect());
	}
	
	public void createNewDatabase(String fileName) {

		this.fileName = fileName;

		String url = "jdbc:sqlite:"+SCore.getPlugin().getDataFolder() +"/"+fileName;

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				System.out.println(SCore.NAME_2+" Connexion to the db...");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Connection connect() {
		// SQLite connection string
		
		String urlLocal = "jdbc:sqlite:"+SCore.getPlugin().getDataFolder() + "/"+fileName;
		
		Connection conn = null;
		
		try {
			if(GeneralConfig.getInstance().isUseMySQL()){
				MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
				dataSource.setServerName(GeneralConfig.getInstance().getDbIP());
				dataSource.setPortNumber(GeneralConfig.getInstance().getDbPort());
				dataSource.setDatabaseName(GeneralConfig.getInstance().getDbName());
				dataSource.setUser(GeneralConfig.getInstance().getDbUser());
				dataSource.setPassword(GeneralConfig.getInstance().getDbPassword());
				dataSource.setServerTimezone("UTC");
				conn = dataSource.getConnection();
			}
			else conn = DriverManager.getConnection(urlLocal);
			
			//System.out.println("[ExecutableItems] "+"Connexion OKAY");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(SCore.NAME_2+" "+e.getMessage());
		}
		return conn;
	}

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database(); 
		}
		return instance;
	}


}