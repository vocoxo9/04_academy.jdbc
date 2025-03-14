package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRun {
	
	/*
	 *  * JDBC용 객체 *
	 *  
	 *  - Connection	: DB의 연결정보를 담고 있는 객체
	 *  - [Prepared]Statement : 연결된 DB에 SQL문을 전달하여 실행하고 그 결과를 받아주는 객체
	 *  - Result Set : DQL(SELECT)문 실행 후 조회 결과를 담고 있는 객체
	 *  
	 *  * JDBC 과정 ( 순서 중요 *) *
	 *  1) JDBC Driver 등록		: 해당 DBMS(오라클)가 제공하는 클래스를 등록하는 작업
	 *  2) Connection 객체 생성 	: 연결하고자 하는 DB정보를 전달하여 해당 DB와 연결하면서 생성
	 * 							-> DB정보 : 접속 주소(url), 사용자명(username), 비밀번호(password)
	 *  3) Statement 객체 생성	: Connection 객체를 이용하여 생성하고, SQL문을 실행하고 결과를 받을 것
	 *  4) SQL문을 DB에 전달하여 실행 : Statement 객체 사용
	 *  5) 실행 결과를 받기(저장하기)
	 *  	- 1) DQL(SELECT)문 실행 : ResultSet 객체 (=> 조회된 데이터들이 담겨져 있음)
	 *  	- 2) DML문 실행	       : int (=> 처리된 행 수)
	 *  6) 결과에 대해 처리
	 *  	- 1) ResultSet 객체에 담겨져 있는 데이터들을 하나하나 꺼내서 VO(Value Object)객체에 옮겨 담기
	 *  	- 2) 트랜잭션 처리 (실행에 성공했을 경우 commit, 실패했을 경우 rollback)
	 *  7) 사용 후 JDBC용 객체들을 반납 (close)
	 *  	--> !!! 생성 역순으로 close 해줘야 함
	 */
	public static void main(String[] args) {
		// TEST 테이블에 데이터 추가
//		insertTest();
		
		
		// TEST 테이블에서 데이터 조회
		selectTest();
	}
	
	public static void selectTest() {
		// DQL(SELECT) ---> ResultSet 객체 ---> 데이터를 하나하나 추출하여 저장
		
		// * 필요한 객체에 대한 변수 선언
		Connection conn = null;
		Statement stat = null;
		ResultSet rset = null;
		
		
		// 1) jdbc driver 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		
			// 2) Connection 객체 생성
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String username = "C##JDBC";
			String password = "JDBC";
			
			conn = DriverManager.getConnection(url, username, password);
				
			// 3) Statement 객체 생성
			stat = conn.createStatement();
				
			// 4, 5) SQL 실행 후 결과 받기
			String sql = "SELECT * FROM TEST";
			rset = stat.executeQuery(sql);
			
			// 6) 조회된 결과에 대한 데이터를 하나하나 추출하기
			//	* 데이터가 있는 지 확인 : rset.next() : boolean (데이터가 있다면 true, 없다면 false)
			while(rset.next()) {
				// -> 조회된 데이터가 있는 동안 반복문 수행
				
				// 데이터를 추출할 때, "컬럼명" 또는 "컬럼순번"을 사용하여 추출
				// | TNO | TNAME | TDATE |
				
				// - TNO 컬럼의 값 추출 : 해당컬럼의 데이터타입 -> NUMBER
				//	 * 데이터들이 딱 떨어지는 수이므로 정수형변수(int) 사용
				int tno = rset.getInt("TNO");	// 컬럼명으로 추출
				
				// - TNAME 컬럼의 값 추출 : 데이터타입 -> VARCHAR2
				//	 * 데이터들이 문자타입이지만 길이가 다양할 수 있으므로 문자열변수(String) 사용
				String tname = rset.getString(2);	// 컬럼 순번으로 추출
													// => 조회했을 때 기준으로 두번째 위치
				// - TDATE 컬럼의 값 추출 : 데이터타입 -> DATE
				//	 * 날짜 타입 데이터는 java.sql.Date 타입의 변수 사용
				Date tdate = rset.getDate("TDATE");
				
				System.out.println(tno + ", " + tname + ", " + tdate);
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7) 자원반납 (close) --> 생성 역순으로 반남
			try {
				rset.close();
				stat.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void insertTest() {
		// finally 부분에서 close 처리를 위해 변수 선언 위치 변경
		Connection conn = null;
		Statement stat = null;
		// DML(INSERT) ---> int (처리된 행 수) ---> 트랜잭션 처리(commit, rollback)
		
		// 1) jdbc driver 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("* 오라클 드라이버 등록 완료 *");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 2) Connection 객체 생성
		// 연결할 DB 정보 (url, username, password)
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe"
															, "C##JDBC"
															, "JDBC");
			System.out.println("* Connection 객체 생성 완료 *");
			conn.setAutoCommit(false);	// => 자동커밋(auto commit) 설정 OFF 
										// (jdbc6 버전 이후 auto commit 설정되어 있음)
			// --> 오라클 db 접속 성공
			
			// 3) Statement 객체 생성
			stat = conn.createStatement();
			
			// 4,5) SQL문을 실행하고 결과 받기 (=> int)
			String sql = "INSERT INTO TEST VALUES (2, '정혜영', SYSDATE)";
			int result = stat.executeUpdate(sql);
			// => 완성된 형태의 sql문 작성 (=> sql developer에서 실행해도 정상 동작되는 형태)
			// => !! sql문 끝에 세미콜론이 없어야 함
			
			// 6) 트랜잭션 처리 (DML 실행 시)
			//		sql문 실행 성공 시 commit
			if(result > 0) {
				conn.commit();
				System.out.println("* 데이터 추가 성공 *");
			} else {
			// 		실행 실패 시 rollback
				conn.rollback();
				System.out.println("* 데이터 추가 실패 *");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 7) 자원 반납
			//		=> 생성 역순으로 close!
			try {
			stat.close();
			conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
