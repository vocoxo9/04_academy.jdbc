package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestRun {

	/*
	 * Properties 특징 
	 * - Map 계열의 컬렉션 중 하나 => key:value 형태로 데이터 저장 
	 * - 문자열(String) 형태로 데이터 저장
	 * 
	 * * 사용하는 메소드 
	 * - 값을 저장할 때 : setProperty(key값, value값) 
	 * - 값을 꺼내올 때 : getProperty(key값)
	 * 
	 * * 파일로 저장 시 종류(확장자) 
	 * - properties 
	 * - xml
	 */
	public static void main(String[] args) {
		// Properties 객체에 담긴 데이터를 파일로 저장
		// saveProp();
		
		// JDBC 관련 설정 파일 만들기(저장)
		// saveJdbcSettings();

		// JDBC 설정 파일 읽어오기
		// readJdbcSettings();
		
		// 쿼리문 저장 파일에서 읽어오기
		readQueryFile();
	}

	
	/**
	 * 쿼리문을 저장한 파일 내용 읽어오기
	 * 
	 * - xml 형식의 파일 내용 읽어오기 : loadFromXML(InputStream)
	 */
	public static void readQueryFile() {
		// * query.xml 파일 내용을 읽어와서
		//	 저장되어 있는 쿼리문들을 콘솔창에 출력
		Properties prop = new Properties();
		
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
				
			String insert = prop.getProperty("insertMember");
			System.out.println(insert);
			System.out.println(prop.getProperty("selectAll"));
			System.out.println(prop.getProperty("updateMember"));
			System.out.println(prop.getProperty("deleteMember"));
								
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	/**
	 * JDBC 설정 파일 내용 읽어오기
	 * 
	 * - properties 형식의 파일 내용 읽기 : load(InputStream)
	 */
	public static void readJdbcSettings() {
		// * Properties 객체 생성
		Properties prop = new Properties();
		
		try {
			// * 파일로부터 데이터를 읽어와서 Properties 객체에 담기(저장)
			prop.load(new FileInputStream("resources/settings.properties"));
			
			// * Properties 객체에서 키값으로 데이터 추출(조회)
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			
			// * 키값들이 기억이 안 날 경우 참고
			for(Object key : prop.keySet()) {
				System.out.println(key);
			}
			
			System.out.println("driver ---> " + driver);
			System.out.println("url ---> " + url);
			System.out.println("username ---> " + prop.getProperty("username"));
			System.out.println("password ---> " + prop.getProperty("password"));
			
			
//			for(Object key : prop.keySet()) {
//				System.out.println(key + prop.getProperty(key));
//			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("설정 파일 읽는데 문제 발생");
		}
	}
	
	/**
	 * JDBC 관련된 설정 정보를 파일에 저장
	 * 
	 * - driver		:	oracle.jdbc.driver.OracleDriver
	 * - url		:	jdbc:oracle:thin:@localhost:1521:xe (jdbc:oracle:thin:@{IP주소}:{포트번호}:{sid})
	 * - username	:	사용자명
	 * - password	:	비밀번호
	 */
	public static void saveJdbcSettings() {
		// * JDBC용 계정 정보로 resources/settings.properties 파일에 저장

		Properties prop = new Properties();
		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
		prop.setProperty("username", "C##JDBC");
		prop.setProperty("password", "JDBC");
		
		try {
			
			prop.store(new FileOutputStream("resources/settings.properties"), "");
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Properties 객체를 이용하여 데이터를 파일에 저장
	 * 
	 * 1) Properties 객체 생성
	 * 2) 객체에 데이터 저장
	 * 3) 객체에 저장된 데이터를 파일에 저장(출력) => 파일용 기반스트림 사용
	 */
	public static void saveProp() {
		// 1) Properties 객체 생성
		Properties prop = new Properties();

		// 2) 객체에 데이터 저장
		prop.setProperty("C", "INSERT"); // Create : 데이터 추가 / 게시글 작성 / 회원 등록
		prop.setProperty("R", "SELECT"); // Read : 데이터 조회 / 게시글 목록 조회, 검색, 상세페이지
		prop.setProperty("U", "UPDATE"); // Update : 데이터 수정 / 게시글 수정 / 회원 정보 수정
		prop.setProperty("D", "DELETE"); // Delete : 데이터 삭제 / 게시글 삭제 / 회원 탈퇴

		// 3) 객체에 저장된 데이터를 파일에 출력
		// * properties 형식 : store(OuputStream) --> 설정들을 저장하기 위한 용도
		// * xml 형식 : storeToXML(OuputStream) --> SLQ문들을 저장하기 위한 용도
		try {
			// properties 형식
			prop.store(new FileOutputStream("test.properties"), "Properties Test");
			// 키값=밸류값 형태로 파일에 저장됨

			// xml 형식
			prop.storeToXML(new FileOutputStream("test.xml"), "Properties Test");
			// <entry key="키값">밸류값</entry> 형태로 파일에 저장됨
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
