package db02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestZipcodePstmt {
	
	// db 연결 문자열
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url    = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid  = "hr";
	private static String dbpwd  = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		//System.out.println(conn);
		
		String            sql   = "";
		sql +=   "SELECT   NVL(d.department_name, '부서없음')        부서명, ";
		sql +=            "e.first_name || ' ' || last_name          직원명, ";
		sql +=            "e.phone_number                            전화번호 ";
		sql +=   "FROM     departments d " ;
		sql +=   "RIGHT JOIN employees e ON d.department_id = e.department_id ";
		sql +=   "WHERE    d.department_id = ? ";
		sql +=   "ORDER BY employee_id ";
		//System.out.println(sql);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,80); // 첫번째 ?의 값을 설정하는 작업
		
		ResultSet         rs    = pstmt.executeQuery();
		
		String fmt = "%s, %s, %s";
		while ( rs.next() ) {
			String dname = rs.getString("부서명");
			String ename = rs.getString("직원명");
			String tel = rs.getString("전화번호");
			String msg = String.format(fmt, dname, ename, tel);
			System.out.println(msg);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		

	}

}
