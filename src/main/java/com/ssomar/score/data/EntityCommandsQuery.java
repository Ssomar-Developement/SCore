package com.ssomar.score.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssomar.score.SCore;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.ActionInfoSerializer;
import com.ssomar.score.commands.runnable.entity.EntityRunCommand;

public class EntityCommandsQuery {

	private final static String TABLE_COMMANDS_ENTITY = "commands_entity";
	private final static String TABLE_COMMANDS_ENTITY_NAME = "Commands Entity";

	private final static String COL_UUID_LAUNCHER = "uuid_launcher";
	private final static String COL_UUID_ENTITY = "uuid_entity";
	private final static String COL_BRUT_COMMAND = "brut_command";
	private final static String COL_RUN_TIME = "run_time";
	private final static String COL_ACTION_INFO = "action_info";

	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_COMMANDS_ENTITY+" ("+COL_UUID_LAUNCHER+" TEXT NOT NULL, "+COL_UUID_ENTITY+" TEXT NOT NULL, "+COL_BRUT_COMMAND+" TEXT NOT NULL,"+COL_RUN_TIME+" LONG NOT NULL, "+COL_ACTION_INFO+" TEXT NOT NULL);";


	public static void createNewTable(Connection conn) {
		try (Statement stmt = conn.createStatement()) {
			System.out.println(SCore.NAME_2+" Verification of the table "+TABLE_COMMANDS_ENTITY_NAME+"...");
			stmt.execute(CREATE_TABLE);
		} catch (SQLException e) {
			System.out.println(SCore.NAME_2+" "+e.getMessage());
		}
	}


	public static void insertCommand(Connection conn, List<EntityRunCommand> commands) {
		String sql = "INSERT INTO "+TABLE_COMMANDS_ENTITY+" ("+COL_UUID_LAUNCHER+","+COL_UUID_ENTITY+","+COL_BRUT_COMMAND+","+COL_RUN_TIME+","+COL_ACTION_INFO+") VALUES(?,?,?,?,?)";

		PreparedStatement pstmt = null;

		int i = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			for(EntityRunCommand command: commands) {
				pstmt.setString(1, command.getLauncherUUID().toString());
				pstmt.setString(2, command.getEntityUUID().toString());
				pstmt.setString(3, command.getBrutCommand());
				pstmt.setLong(4, command.getRunTime());
				pstmt.setString(5, ActionInfoSerializer.toString(command.getaInfo()));
				pstmt.addBatch();
				if (i % 1000 == 0 || i == commands.size()) {
					pstmt.executeBatch(); // Execute every 1000 items.
				}
			}
		} catch (SQLException | IOException e) {
			System.out.println(SCore.NAME_2+" "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			if(pstmt != null){
				try{
					pstmt.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}


	public static void deleteEntityCommands(Connection conn){

		String sql = "DELETE FROM "+TABLE_COMMANDS_ENTITY;

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(SCore.NAME_2+" "+e.getMessage());
		}
		finally {
			if(pstmt != null){
				try{
					pstmt.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	public static List<EntityRunCommand> selectEntityCommands(Connection conn){
		String sql = "SELECT "+COL_BRUT_COMMAND+","+COL_RUN_TIME+","+COL_ACTION_INFO+" FROM "+TABLE_COMMANDS_ENTITY+" ORDER BY "+COL_RUN_TIME;

		List<EntityRunCommand> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {	
			pstmt = conn.prepareStatement(sql);
			rs    = pstmt.executeQuery();

			while (rs.next()) {

				String brutCommand = rs.getString(COL_BRUT_COMMAND);
				long runTime = rs.getLong(COL_RUN_TIME);
				ActionInfo aInfo = (ActionInfo) ActionInfoSerializer.fromString(rs.getString(COL_ACTION_INFO));

				EntityRunCommand pCommand = new EntityRunCommand(brutCommand, runTime, aInfo);

				list.add(pCommand);
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			System.out.println(SCore.NAME_2+" "+e.getMessage());
		} finally {
			if(rs != null){
				try{
					rs.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try{
					pstmt.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
