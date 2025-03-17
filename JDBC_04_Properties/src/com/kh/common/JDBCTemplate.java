package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/*
	 * 매번 반복적으로 사용한 코드들을 메소드로 정의하기 위한 용도 (공통 템플릿)
	 * => 해당 객체를 매번 생성하지 않고 메소드를 호출할 수 있도록
	 * 	  모든 메소드를 static 메소드로 정의
	 */
	
	/**
	 * Connection 객체 생성 메소드
	 * @return	생성된 Connection 객체
	 */
	public static Connection getConnection() {
		Connection conn = null;
		// jdbc driver 등록
		// connection 객체 생성
		
		try {
			// 1) Properties 객체 생성
			Properties prop = new Properties();
			
			// 2) settings.properties 파일을 읽어오기
			prop.load(new FileInputStream("resources/settings.properties"));
			
			// 3) 키값에 해당하는 각 설정값을 적절한 위치에 사용
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											prop.getProperty("username"),
											prop.getProperty("password"));
			conn.setAutoCommit(false);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * Connection 객체 반납 (close 처리)
	 * @param conn		Connection 객체
	 */
	public static void close(Connection conn) {
		try {
			// connection 객체가 생성 되어있고 (null이 아니고)
			// 닫혀있지 않을 때 (isClosed() == false)
			if(conn != null && !conn.isClosed()) conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Statement 관련 객체들을 전달받아 close 처리
	 * @param stat		Statement 또는 그 자식 타입의 객체들
	 */
	public static void close(Statement stat) {
		try {
			if(stat != null && !stat.isClosed()) stat.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ResultSet 객체를 전달받아 close 처리
	 * @param rset		ResultSet 객체
	 */
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) rset.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * commit 처리
	 * @param conn	생성되어있는 Connection 객체
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * rollback 처리
	 * @param conn	
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
