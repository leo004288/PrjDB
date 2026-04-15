package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestZipcode01 {

	// 연결 문자열 : Connection String
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url    = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid  = "sky";
	private static String dbpwd  = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		Statement stmt = conn.createStatement();
		String    sql  = "select count(zipcode) cnt from zipcode";
		ResultSet rs   = stmt.executeQuery(sql);
		// int cnt = stmt.executeUpdate("update...")
		
		rs.next();
		
		System.out.println(rs.getInt("cnt"));
		
		rs.close();
		stmt.close();
		conn.close();
		
		
		
		
		
	}

}
