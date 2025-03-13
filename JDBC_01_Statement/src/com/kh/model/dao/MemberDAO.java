package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

/*
 * DAO (Database Access Object)
 *  : DB에 직접 접근하여 사용자의 요청에 맞는 SQL문 실행 후 결과 반환
 *    --> JDBC 사용
 */
public class MemberDAO {
	// * DB 정보 : 서버주소, 사용자명, 비밀번호 (=> 바뀌지 않기 때문에 상수로 선언해도 됨)
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER_NAME = "C##JDBC";
	private final String PASSWORD = "JDBC";
	
	
	public Member searchById(String searchId) {
		Member m = null; // => controller에서 결과처리를 null로 비교하도록 작성되어 있음
		
		// jdbc 객체 선언
		Connection conn = null;
		Statement stat = null;
		ResultSet rset = null;
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER WHERE MEMBERID = '" + searchId + "'";
		// String sql = String.format("SELECT * FROM MEMBER WHERE MEMBERID = '%s'", searchId);
		try {
			// jdbc driver 연결
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// connection 생성
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			
			// statement 생성
			stat = conn.createStatement();
			
			// 결과 처리
			rset = stat.executeQuery(sql);
			
			if(rset.next()) {
				// => 조회된 결과가 있다면 한 행만 있을 것 ---> 아이디에 unique제약조건이 있기 때문
				m = new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("memberpw"),
						rset.getString("gender") == null ? ' ' : rset.getString("gender").charAt(0),
						rset.getInt("AGE"),
						rset.getString("email"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("hobby"),
						rset.getDate("enrollDate")
						);
			}
			// 조건문이 끝난 시점에
			// - 조회된 데이터가 없다면 m = null
			// - 조회된 데이터가 있다면 m 변수는 새로 생성된 객체 정보를 담고 있을 것
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stat.close();
				conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		
		return m;
	}
	/**
	 * 요청된 회원 정보를 DB에 추가하는 메소드
	 * @param m 사용자가 입력한 정보들이 담겨있는 Member 객체
	 * @return	추가(INSERT)후 처리된 행 수
	 */
	public int insertMember(Member m) {
		int result = 0;
		// DML(INSERT) ---> int(처리된 행 수)이기 때문 ---> 트랜잭션 처리
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_MNO.NEXTVAL, "
				+ "'" + m.getMemberId() + "', "
				+ "'"+ m.getMemberPwd() +"', "
				+ "'"+ m.getGender() +"', "
				+ "'"+ m.getAge() +"', "
				+ "'"+ m.getEmail() +"', "
				+ "'"+ m.getAddress() +"', "
				+ "'"+ m.getPhone() +"', "
				+ "'"+ m.getHobby() +"', "
				+ "SYSDATE)";
		
		System.out.println("----------------------------------------------------");
		System.out.println(sql);
		System.out.println("----------------------------------------------------");
		
		// *JDBC용 객체 선언
		Connection conn = null;
		Statement stat = null;
		
		// 1) JDBC Driver 등록
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성 (DB 정보로 연결)
			conn = DriverManager.getConnection(URL,USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			
			// 3) Statement 객체 생성 (Connection 객체로 생성)
			stat = conn.createStatement();
			
			// 4,5) SQL 실행 후 결과 받기
			result = stat.executeUpdate(sql);
			
			// 6) 트랜잭션 처리
			if (result > 0 ) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
				conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		
		return result;
	}
	
	/**
	 * 회원 전체 목록을 조회하여 반환하는 메소드
	 * @return 전체 회원 목록
	 */
	public ArrayList<Member> selectAllList(){
		ArrayList<Member> list = new ArrayList<>();	// []
		
		// DQL(SELECT)문 실행 (여러 행 조회) ---> ResultSet 객체 ---> ArrayList에 담기
		
		// JDBC용 객체 선언
		Connection conn = null;
		Statement stat = null;
		ResultSet rset = null;
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER ORDER BY MEMBERNO ASC";
		// i) 쿼리문 자체를 변경하기
		// SELECT MEMBERNO, MEMBERID, MEMBERPW, NVL(GENDER, ' ') GENDER, AGE, EMAIL, ADDRESS, PHONE, HOBBY, ENROLLDATE FROM MEMBER ORDER BY MEMBERNO;
		try {
			// 1) JDBC용 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			
			// 3) Statement 객체 생성
			stat = conn.createStatement();
			
			// 4) SQL 실행, 5) 결과 받기
			rset = stat.executeQuery(sql);
			
			// 6) ResultSet에 담겨져 있는 데이터를 하나하나 추출
			while(rset.next()) {
				// [1] 데이터를 추출하여 Member 객체에 담기 (생성)
				Member m = new Member(
						rset.getInt("MEMBERNO"),
						rset.getString("MEMBERID"),
						rset.getString("memberpw"),
//						rset.getString("gender").charAt(0),	
						// developer에서 insert 했을 때 오류 발생 : NullPointerException ---> String으로 변경...?
						// DB에 gender 컬럼에 null값이 존재 => 따라서 오류 발생
						// ii) 삼항연산자 사용
						rset.getString("gender") == null ? ' ' : rset.getString("gender").charAt(0),
						rset.getInt("AGE"),
						rset.getString("email"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("hobby"),
						rset.getDate("enrollDate")
						);
				// [2] Member 객체를 리스트에 추가
				list.add(m);
			}
			// * 반복문이 끝난 시점
			//	--> 조회된 데이터가 있다면? 리스트에는 최소 한 개 이상의 데이터가 담겨져 있을 것
			//	--> 조회된 데이터가 없다면? 리스트는 비어있을 것임
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stat.close();
				conn.close();
			}catch(SQLException e) { e.printStackTrace(); }
		}
		
		return list;
	}
	
	/**
	 * 요청된 회원 정보로 수정
	 * @param m 회원아이디, 변경할 데이터(비밀번호, 성별, 주소, 취미)
	 * @return 처리 결과(처리된 행 수)
	 */
	public int updateMember(Member m) {
		int result = 0;
		Connection conn = null;
		Statement stat = null;
		
		String sql = "UPDATE MEMBER "
				+ "SET MEMBERPW = '" + m.getMemberPwd() 
				+ "', GENDER = '" + m.getGender() 
				+ "', ADDRESS = '" + m.getAddress() 
				+ "', HOBBY = '" + m.getHobby() + "' "
				+ "WHERE MEMBERID = '" + m.getMemberId() + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			
			stat = conn.createStatement();
			
			result = stat.executeUpdate(sql);
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
				conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		
		return result;
	}
	
	/**
	 * 전달 받은 아이디에 해당하는 회원 정보 삭제
	 * @param m		삭제할 회원 아이디
	 * @return		처리 결과
	 */
	public int deleteMember(String id) {
		int result = 0;
		Connection conn = null;
		Statement stat = null;
		
		String sql = "DELETE FROM MEMBER WHERE MEMBERID = '" + id + "'";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			
			stat = conn.createStatement();
			
			result = stat.executeUpdate(sql);
			
			if(result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stat.close();
				conn.close();
			} catch(SQLException e) { e.printStackTrace(); }
		}
		return result;
	}
}
