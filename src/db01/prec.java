package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class prec {
	
		private static String driver = "oracle.jdbc.OracleDriver";
		private static String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		private static String dbuid  = "hr";
		private static String dbpwd  = "1234";
		
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		Statement stmt = conn.createStatement();
		String    sql  = "SELECT d.department_name, e.FIRST_NAME || ' ' || e.LAST_NAME, e.PHONE_NUMBER "
						+ "FROM EMPLOYEES e LEFT JOIN departments d ON e.department_id = d.department_id ";
//		System.out.println(sql);
		ResultSet rs   = stmt.executeQuery(sql); 
		
		while(rs.next() != false ) {
			System.out.print(rs.getString(1) + ",");
			System.out.print(rs.getString(2) + ",");
			System.out.print(rs.getString(3) + ",");
			System.out.println();
		}
		
		rs.close();
		stmt.close();
		conn.close();
		

	}

}
