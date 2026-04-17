package db3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestTUser {
	static Scanner in = new Scanner(System.in);
	
	// 연결문자열
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url    = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid  = "sky";
	private static String dbpwd  = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		// CRUD 예제 create, read, update, delete 
		do {
			// 화면출력
			System.out.println("========================================");
			System.out.println("               회원 정보                ");
			System.out.println("========================================");
			System.out.println("1. 회원 목록");
			System.out.println("2. 회원 조회");
			System.out.println("3. 회원 추가");
			System.out.println("4. 회원 수정");
			System.out.println("5. 회원 삭제");
			System.out.println("q. 종료");
			
			System.out.println("선택:");
			String    choice = in.nextLine();
			
			TUserDTO tuser   = null;
			int      aftcnt  = 0;
			switch ( choice ) {
			case "1" : // 회원목록 HOOteahoon7
				ArrayList<TUserDTO> userlist = getTUserList();
				displayList(userlist);
				; break;
			case "2" : // 회원조회 (아이디로 조회)
				System.out.println("조회할 아이디를 입력하세요");
				String   uid   = in.nextLine();
				         tuser = getTUser(uid);
				//System.out.println(tuser.toString());
				display(tuser);
				; break;
			case "3" : // 회원추가
				        tuser  = inputData();
				        aftcnt = addTUser(tuser);
				System.out.println(aftcnt + "건 저장되었습니다");
				; break;
			case "4" : // 회원수정
				System.out.println("수정 아이디");
				String orgUserid = in.nextLine();    // 검색
				System.out.println("수정 내용");
				        tuser  = inputupdateData();  // 수정 
		                aftcnt = updateTUser(orgUserid, tuser);
				System.out.println(aftcnt + "건 수정되었습니다");
				System.out.println("Press enter key...");
				in.nextLine();
				; break;
			case "5" : // 회원삭제
				System.out.println("삭제 아이디");
				String orgUserid2 = in.nextLine();
				           aftcnt = deleteTUser(orgUserid2);
	           System.out.println(aftcnt + "건 삭제되었습니다");
	           System.out.println("Press enter key...");
			   in.nextLine();
				; break;
			case "q" : // 종료
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
				; break;
			}
			
		} while(true); //무한반복
		
		
	} // 
	

	// 1. 전체 목록 조회 
	private static ArrayList<TUserDTO> getTUserList() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String            sql   = "SELECT * FROM TUSER ";
						  sql  += "ORDER BY userid ";	 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet         rs    = pstmt.executeQuery();
		
		ArrayList<TUserDTO> userlist = new ArrayList<>(); 
		
		while( rs.next() ) {
			String   userid   = rs.getString("userid");
			String   username = rs.getString("username");
			String   email    = rs.getString("email");
			TUserDTO tuser    = new TUserDTO(userid, username, email); 
			userlist.add(tuser);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return userlist;
	}

	// 2. 입력받은 아이디로 db 에서 한줄을 조회한다
	private static TUserDTO getTUser(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String            sql   = "SELECT * FROM TUSER "
								+ "WHERE  userid = ? "; 
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, uid);	
		
		ResultSet rs = pstmt.executeQuery();
				
		TUserDTO tuser = null;
		if( rs.next() ) {               // 자료가 있을때   PRIMARY KEY
			String userid   = rs.getString("userid");
			String username = rs.getString("username");
			String email    = rs.getString("email");
			
			tuser           = new TUserDTO(userid, username, email);
			
		} else {                        //        없을때   PRIMARY KEY
			
		}
		
		pstmt.close();
		conn.close();
		
		return tuser;
	}

	// 3. 회원 추가
	private static int addTUser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn   = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String     sql    = "";
	               sql   += "INSERT INTO TUSER VALUES (?, ?, ?) ";
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, tuser.getUserid());
	    pstmt.setString(2, tuser.getUsername());
		pstmt.setString(3, tuser.getEmail());
		   
		int        aftcnt = pstmt.executeUpdate();
		   
		   pstmt.close();
		   conn.close();
		   
		   return aftcnt;
	}

	// 4. 회원 수정 
    private static int updateTUser(String orgUserid, TUserDTO tuser) throws ClassNotFoundException, SQLException {
    	Class.forName(driver);
		Connection        conn   = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql  = "";
	           sql += "UPDATE tuser SET username = ?, email = ?, WHERE userid = ? ";
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        pstmt.setString(1, tuser.getUsername());
	    pstmt.setString(2, tuser.getEmail());
	    pstmt.setString(3, orgUserid);
	   
	    int               aftcnt = pstmt.executeUpdate();
	   
	    pstmt.close();
	    conn.close();
	   
	    return aftcnt;
	}
	
    // 5. 회원 삭제
 	private static int deleteTUser(String orgUserid2) throws ClassNotFoundException, SQLException {
 		Class.forName(driver);
		Connection        conn   = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql  = "";
	           sql += "DELETE FROM tuser WHERE userid = ? ";
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        pstmt.setString(1, orgUserid2);
	    
	    int               aftcnt = pstmt.executeUpdate();
	   
	    pstmt.close();
	    conn.close();
	   
	    return aftcnt;
 	}
    
	// 추가할 데이터를 키보드로 입력받음
	private static TUserDTO inputData() {
		System.out.println("아이디:");
		String userid   = in.nextLine();
		System.out.println("이름:");		
		String username = in.nextLine();
		System.out.println("이메일:");		
		String email    = in.nextLine();
		
		TUserDTO tuser = new TUserDTO(userid, username, email);
		return   tuser;
	}
	
	// 수정할 데이터를 입력받는다
	private static TUserDTO inputupdateData() {
		System.out.println("이름:");		
		String username = in.nextLine();
		System.out.println("이메일:");		
		String email    = in.nextLine();
		
		TUserDTO tuser = new TUserDTO(username, email);
		return tuser;
	}
	
    // TUser 한줄을 출력한다
	private static void display(TUserDTO tuser) {
		
		if ( tuser == null ) {
			System.out.println("조회한 자료가 없습니다");
		} else {
			String msg = String.format("%s %s %s",
					tuser.getUserid(), tuser.getUsername(), tuser.getEmail());
			System.out.println(msg);
		}
		
	}
	
	// 	전체 목록 출력
	private static void displayList(ArrayList<TUserDTO> userlist) {
		
		if(userlist.size() == 0 ) {
			System.out.println("조회한 자료가 없습니다.");
			return;
		}
		
		String fmt = "";
		String msg = "";
		for (TUserDTO tuser : userlist) {
			String userid   = tuser.getUserid();
			String username = tuser.getUsername();
			String email    = tuser.getEmail();
			msg = """
				  %s %s %s	
				  """.formatted(userid, username, email);  // java template 문자열
			System.out.print(msg);
		}
		
		System.out.println("Press enter key...");
		in.nextLine();
	}

} //
