package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

/*
 * DAO (Database Access Object)
 *  : DB에 직접 접근하여 사용자의 요청에 맞는 SQL문 실행 후 결과 반환
 *    --> JDBC 사용
 */
public class MemberDAO {
	// * 기존 상수 필드 정보는 JDBCTemplate 클래스에서 사용되어 제거 *

	// 공통적으로 사용되는 Properties 객체 필드부에 선언
	private Properties prop = new Properties();
	
	// MemberDAO 생성자 생성될 때마다 파일을 읽어올 수 있게
	public MemberDAO() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 회원 정보 추가 메소드
	 * 
	 * @param conn 생성되어 있는 Connection 객체
	 * @param m    회원 정보가 저장된 Member 객체
	 * @return 처리된 결과(처리된 행 수)
	 */
	public int insertMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstat = null;
		// 1) jdbc driver 등록 -- Service 객체가 완료
		// 2) Connection 객체 생성 -- Service 객체가 완료
		// ----------------------------------------------------
		// 3) Statement 객체 생성
		try {
//			String sql = "INSERT INTO MEMBER VALUES(SEQ_MNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
			String sql = prop.getProperty("insertMember");
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, m.getMemberId());
			pstat.setString(2, m.getMemberPwd());
			pstat.setString(3, m.getGender());
			pstat.setInt(4, m.getAge());
			pstat.setString(5, m.getEmail());
			pstat.setString(6, m.getAddress());
			pstat.setString(7, m.getPhone());
			pstat.setString(8, m.getHobby());

			// 4) SQL문 실행 5) 결과 받기
			result = pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 6) 결과에 대한 처리 : 트랜잭션처리 --> Service 객체에서 담당
			// 7) 자원 반납
			JDBCTemplate.close(pstat);
		}
		return result;
	}

	/**
	 * 전체 회원에 대한 정보를 DB에서 조회
	 * 
	 * @param conn Connection 객체
	 * @return 조회된 결과(회원목록)
	 */
	public ArrayList<Member> selectAll(Connection conn) {
		ArrayList<Member> list = new ArrayList<>();
		Statement stat = null;
		ResultSet rset = null;

//		String sql = "SELECT * FROM MEMBER ORDER BY MEMBERNO";
		// try~with~resource문 사용 시 자원 관리 자동으로
		// try(Statement stat = conn.createStatement(); ResultSet rset =
		// stat.executeQuery(sql);){}
		try {
			String sql = prop.getProperty("selectAll");
			stat = conn.createStatement();
			rset = stat.executeQuery(sql);
			while (rset.next()) {
				Member m = new Member(rset.getInt("MEMBERNO"), rset.getString("MEMBERID"), rset.getString("memberpw"),
						rset.getString("gender"), rset.getInt("AGE"), rset.getString("email"),
						rset.getString("ADDRESS"), rset.getString("PHONE"), rset.getString("hobby"),
						rset.getDate("enrollDate"));

				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(stat);
		}
		return list;
	}

	/**
	 * 전달받은 아이디로 DB에 포함하고 있는 데이터가 있는 지 조회
	 * 
	 * @param conn 생성된 Connection 객체
	 * @param id   검색할 회원 아이디
	 * @return 검색 결과
	 */
	public ArrayList<Member> searchById(Connection conn, String id) {
		ArrayList<Member> list = new ArrayList<>();
		PreparedStatement pstat = null;
		ResultSet rset = null;
//		String sql = "SELECT * FROM MEMBER WHERE MEMBERID LIKE '%' || ? || '%'";
		// PreparedStatement 로 실행한다면 '%' || ? || '%' ---> ? 는 작은따옴표를 포함하고 있기 때문에
		try {
			String sql = prop.getProperty("searchById");
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);
			// -> setString의 값을 '%' + id + '%' 의 형태로 전달해줘도 상관 없음

			rset = pstat.executeQuery();

			while (rset.next()) {
				Member m = new Member(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4),
						rset.getInt(5), rset.getString(6), rset.getString(7), rset.getString(8), rset.getString(9),
						rset.getDate(10));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstat);
		}
		return list;
	}

	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstat = null;
//		String sql = "UPDATE MEMBER SET MEMBERPW = ?, GENDER = ?, ADDRESS = ?, HOBBY = ? WHERE MEMBERID = ?";

		try {
			String sql = prop.getProperty("updateMember");
			
			pstat = conn.prepareStatement(sql);

			pstat.setString(1, m.getMemberPwd());
			pstat.setString(2, m.getGender());
			pstat.setString(3, m.getAddress());
			pstat.setString(4, m.getHobby());
			pstat.setString(5, m.getMemberId());

			result = pstat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstat);
		}
		return result;
	}

	public int deleteMember(Connection conn, String id) {
		int result = 0;
		PreparedStatement pstat = null;
//		String sql = "DELETE FROM MEMBER WHERE MEMBERID = ?";

		try {
			String sql = prop.getProperty("deleteMember");
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, id);

			result = pstat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstat);
		}
		return result;
	}

}