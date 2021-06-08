package com.ssomar.score.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ssomar.score.SCore;

/**
 *
 * @author sqlitetutorial.net
 */
public class Database {

	private static Database instance;

	private String fileName;
	
	public void load() {
		createNewDatabase("data.db");
		SecurityOPQuery.createNewTable(connect());
		CommandsQuery.createNewTable(connect());
	}
	
	public void createNewDatabase(String fileName) {

		this.fileName=fileName;

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
		String url = "jdbc:sqlite:"+SCore.getPlugin().getDataFolder() + "/"+fileName;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			//System.out.println("[ExecutableItems] "+"Connexion OKAY");
		} catch (SQLException e) {
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