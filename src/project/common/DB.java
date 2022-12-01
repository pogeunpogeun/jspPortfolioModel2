package project.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
	
	public static Connection dbConn() {
		String dbDrv = "oracle.jdbc.driver.OracleDriver";
		String dbUrl = "jdbc:oracle:thin:@localhost:1521/xe";
		String dbUsr = "jspPortFolioModel2";
		String dbPwd = "1234";
		
		Connection conn = null;
		try {
			Class.forName(dbDrv);
		} catch (Exception e) {
			System.out.println("-- Error01 --");
		}
		
		try {
			conn = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
		} catch (Exception e) {
			System.out.println("-- Error2 --");
		}
		return conn;
	}
	
	public static void dbConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if(rs != null) { rs.close(); }
		} catch(Exception e) {
			System.out.println("-- Error3 --");
		}
		
		try {
			if(pstmt != null) { rs.close(); }
		} catch(Exception e) {
			System.out.println("-- Error4 --");
		}
		
		try {
			if(conn != null) { rs.close(); }
		} catch(Exception e) {
			System.out.println("-- Error5 --");
		}
	}
}
