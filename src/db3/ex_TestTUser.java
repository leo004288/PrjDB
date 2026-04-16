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
				String uid = in.nextLine();
				     tuser = getTUSER(uid);
				; break;
			case "3" :         // 3. 회원 추가
				System.out.println();
				; break;
			case "4" :         // 4. 회원 수정
				
				; break;
			case "5" :         // 5. 회원 삭제
				
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
		
		String            sql   = "SELECT * From tuser";	 
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
	
	// 2. 
		private static TUserDTO getTUSER(String uid) {
			// TODO Auto-generated method stub
			return null;
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
