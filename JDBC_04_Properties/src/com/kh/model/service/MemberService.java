package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.model.dao.MemberDAO;
import com.kh.model.vo.Member;

public class MemberService {

	/*
	 * [1] Connection 객체 생성
	 * 		- jdbc driver 등록
	 * 		- Connection 객체 생성
	 * [2] DML문 실행했을 경우 트랜잭션 처리
	 * 		- commit
	 * 		- rollback
	 * [3] Connection 객체 반납
	 * 		- close
	 */
	
	/**
	 * 회원 추가 기능에 대한 메소드
	 * @param m 	추가할 회원 정보
	 * @return 		처리 결과
	 */
	public int insertMember(Member m) {
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO 객체에게 전달받은 데이터(m)와 생성한 Connection 객체를 전달하여 결과 받기
		int result = new MemberDAO().insertMember(conn, m);
		
		// 3) 실행한 SQL문 확인(DML, INSERT)
		//			- DML이므로 트랜잭션 처리
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}

	/**
	 * 전체 회원 목록을 조회하는 메소드
	 * @return	전체 회원 목록 정보
	 */
	public ArrayList<Member> selectAllList() {
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) DAO에게 전체회원 목록 요청 --> 생성된 Connection 객체 전달
		ArrayList<Member> list = new MemberDAO().selectAll(conn);
		
		// 3) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 4) 조회 결과 반환
		return list;
	}

	/**
	 * 전달된 아이디가 포함된 회원 목록을 조회
	 * @param id 검색할 회원 아이디
	 * @return	조회결과
	 */
	public ArrayList<Member> searchById(String id) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Member> list = new MemberDAO().searchById(conn, id);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public int updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDAO().updateMember(conn, m);
		
		JDBCTemplate.close(conn);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}

	public int deleteMember(String id) {
		// Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// DAO에게 삭제 요청 (=> 실제 DB에서 해당 회원 정보 삭제)
		int result = new MemberDAO().deleteMember(conn, id);
		
		// 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		// Connection 객체 반납
		JDBCTemplate.close(conn);
		return result;
	}
}
