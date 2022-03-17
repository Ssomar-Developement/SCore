package com.ssomar.score.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

import com.ssomar.score.SCore;

public class SecurityOPQuery {

	private final static String TABLE_SECURITYOP = "securityop";
	private final static String TABLE_SECURITYOP_NAME = "SecurityOP";

	private final static String COL_PLAYER = "player";

	public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_SECURITYOP+" ("+COL_PLAYER+" TEXT NOT NULL);";


	public static void createNewTable(Connection conn) {
		try (Statement stmt = conn.createStatement()) {
			SCore.plugin.getLogger().info(SCore.NAME_2+" Verification of the table "+TABLE_SECURITYOP_NAME+"...");
			stmt.execute(CREATE_TABLE);
		} catch (SQLException e) {
			System.out.println(SCore.NAME_2+" "+e.getMessage());
		}
	}

	public static boolean insertPlayerOP(Connection conn, Player p) {
		String sql = "INSERT INTO "+TABLE_SECURITYOP+" ("+COL_PLAYER+") VALUES(?)";

		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, p.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(SCore.NAME_2+" "+e.getMessage());
			return false;
		} finally {
			if(pstmt != null){
	            try{
	                pstmt.close();
	            } catch(Exception e){
	                e.printStackTrace();
	            }
	        }
		}
		return true;
	}


	public static void deletePlayerOP(Connection conn, Player p){
		String sql = "DELETE FROM "+TABLE_SECURITYOP+" where "+COL_PLAYER+"=?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
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


	public static boolean selectIfSecurityOPcontains(Connection conn, Player p){
		String sql = "SELECT "+COL_PLAYER+" FROM "+TABLE_SECURITYOP+" where "+COL_PLAYER+"=?";

		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			rs    = pstmt.executeQuery();

			if (rs.next()) return true;
		} catch (SQLException e) {
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
		return false;
	}

}
