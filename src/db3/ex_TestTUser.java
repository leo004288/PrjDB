package db3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ex_TestTUser {
	static Scanner in = new Scanner(System.in);
	
	// 연결 문자열
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url    = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid  = "sky";
	private static String dbpwd  = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// CRUD (create(insert), read(select), update, delete)
		do {
			System.out.println("=====================================================");
			System.out.println("                      회원정보                       ");
			System.out.println("=====================================================");
			System.out.println("1. 회원 목록");
			System.out.println("2. 회원 조회");
			System.out.println("3. 회원 추가 ");
			System.out.println("4. 회원 수정");
			System.out.println("5. 회원 삭제");
			System.out.println("q. 종료");
			System.out.println("선택:");
			
			TUserDTO tuser = null;
			String  choice = in.nextLine();
			switch (choice) {
			case "1" :         // 1. 회원 목록  
				List<TUserDTO> userlist = getTUerList();
				display1(userlist);
				; break;
			case "2" :         // 2. 회원 조회
				System.out.println("아이디 입력");
				String uid    = in.nextLine();
				       tuser  = getTUSER(uid);
				display2(tuser);
				; break;
			case "3" :         // 3. 회원 추가
				       tuser  = inputData();
				int    aftcnt = addTUser(tuser);
				System.out.println(aftcnt + "건 저장되었습니다");
				; break;
			case "4" :         // 4. 회원 수정
				       tuser  = alterData();
		        int    altcnt = altUser(tuser);
				System.out.println(altcnt + "건 변경되었습니다");
				; break;
			case "5" :         // 5. 회원 삭제
		               tuser  = deleteData();
		        int    delcnt = delUser(tuser);
				System.out.println(delcnt + "건 삭제되었습니다");
				; break;
			case "q" :         // q. 종료
				
				; break;
			}
			
		} while(true);

		
	} //
	
	// 1. 전체 조회
	private static List<TUserDTO> getTUerList() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String            sql   = "SELECT * From tuser ";
						  sql  += "ORDER BY userid ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet         rs    = pstmt.executeQuery();
		
		List<TUserDTO> userlist = new ArrayList<>();
		
		while(rs.next()) {
			String   userid   = rs.getString("userid");
			String   username = rs.getString("username");
			String   email    = rs.getString("email");
			TUserDTO ul       = new TUserDTO(userid, username, email);
			userlist.add(ul);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return userlist;
	}
	
	// 2. 입력 받은 id 로 db 에서 한 줄 조회
	private static TUserDTO getTUSER(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String            sql   = "SELECT * FROM TUSER "
				                + "WHERE  userid = ? "; 
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, uid);
        
        ResultSet rs = pstmt.executeQuery();
        
        TUserDTO tuser = null;
        if(rs.next()) {
        	String userid   = rs.getString("userid");
        	String username = rs.getString("username");
        	String email    = rs.getString("email");
        	       tuser    = new TUserDTO(userid, username, email); 
        } else {
        	
        }
        
        pstmt.close();
		conn.close();
        
		return tuser;
	}
	
	// 3_1. 새 데이터 받음 
	private static TUserDTO inputData() {
	    System.out.println("아이디");
	    String userid   = in.nextLine();
	    System.out.println("이름");
	    String username = in.nextLine();
	    System.out.println("이메일");
	    String email    = in.nextLine();
	    
	    TUserDTO tuser = new TUserDTO(userid, username, email);
		return tuser;
	}
		
	// 3_2. 회원 추가
	private static int addTUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql  = "INSERT INTO TUSER VALUES (?, ?, ?) ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tuser.getUserid());
		pstmt.setString(2, tuser.getUsername());
		pstmt.setString(3, tuser.getEmail());
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}
	
	// 4-1. 변경 데이터 받음
	private static TUserDTO alterData() {
		System.out.println("기존 아이디:");
		String buserid   = in.nextLine();
		
//		System.out.println("새 아이디");
//	    String userid   = in.nextLine();
	    System.out.println("새 이름");
	    String username = in.nextLine();
	    System.out.println("새 이메일");
	    String email    = in.nextLine();
	    
	    TUserDTO tuser = new TUserDTO(buserid, username, email);
		return tuser;
	}
		
	// 4-2. 정보 변경
	private static int altUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql  = "UPDATE tuser "
				    
				    + "SET    username = ?, "
				    + "       email    = ? "
				    + "WHERE  userid   = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tuser.getUsername());
		pstmt.setString(2, tuser.getEmail());
		pstmt.setString(3, tuser.getUserid());
		
		int altcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return altcnt;
	}
	
	// 5-1. 삭제 데이터 받음
    private static TUserDTO deleteData() {
    	System.out.println("삭제 아이디");
	    String userid   = in.nextLine();
	    
	    TUserDTO tuser = new TUserDTO(userid, null, null);
		return tuser;
	}

 // 5-2. 정보 삭제
 	private static int delUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
 		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String     sql  = "DELETE "
				        + "FROM tuser "
				        + "WHERE userid = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tuser.getUserid());
		
		int delcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return delcnt;
 	}
    
	// 한줄 출력
	private static void display2(TUserDTO tuser) {
		
		if (tuser == null) {
			System.out.println("조회자료가 없습니다");
		} else {
			String msg = String.format("%s %s %s",
					tuser.getUserid(), tuser.getUsername(), tuser.getEmail());
			System.out.println(msg);
		}
		
	}
		
	// 전체 출력
	private static void display1(List<TUserDTO> userlist) {
		
		if ( userlist.size() == 0 ) {
			System.out.println("조회결과가 없습니다");
			return;
		} else {
			for (TUserDTO ul : userlist) {
				String userid   = ul.getUserid();
				String username = ul.getUsername();
				String email    = ul.getEmail();
				String msg      = """
						          %s %s %s
						          """.formatted(userid, username, email);	
				System.out.print(msg);
			}
			System.out.println("Press ent");
			in.nextLine();
		}
		
	}

	
} //
