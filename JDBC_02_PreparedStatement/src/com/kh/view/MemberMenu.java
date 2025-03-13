package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

/*
 * 	View
 * 	: 사용자가 보게 될 시각적인 요소 (화면),
 * 	  출력 또는 입력
 */
public class MemberMenu {
	// 스캐너 객체 생성
	Scanner sc = new Scanner(System.in);

	private MemberController mController = new MemberController();
	
	/**
	 * 메인메뉴 : 사용자가 보게 될 첫 화면
	 */ 
	public void mainMenu() {
		while (true) {
			System.out.println(" ========== 회원 관리 프로그램 ==========");
			System.out.println(" 1. 회원 추가 "); // c (create)
			System.out.println(" 2. 전체 회원 조회"); /// r (read)
			System.out.println(" 3. 회원 아이디로 검색"); // r (read)
			System.out.println(" 4. 회원 정보 수정 "); // u (update)
			System.out.println(" 5. 회원 탈퇴"); // d (delete)
			System.out.println(" 9. 프로그램 종료 ");

			System.out.print(">> 메뉴 번호 : ");
			int menu = sc.nextInt();
			sc.nextLine();

			switch (menu) {
			case 1:
				addMemberMenu();
				break;
			case 2:
				// Controller 객체에게 전체 회원 조회 요청
				mController.selectAll();
				break;
			case 3:
				searchByIdMenu();
				break;
			case 4:
				updateMemberMenu();
				break;
			case 5:
				deleteMemberMenu();
				break;
			case 9:
				System.out.println("프로그램이 종료됩니다...");
				return;
			}
		}
	}

	/**
	 * 회원 아이디를 기준으로 회원 정보 삭제
	 */
	private void deleteMemberMenu() {
		System.out.print("- 탈퇴할 회원 아이디 : ");
		String id = sc.nextLine();
		
		mController.deleteMember(id);
	}

	/**
	 * 회원 아이디를 기준으로 회원 정보를 수정
	 */
	private void updateMemberMenu() {
		System.out.print("- 회원 아이디 : ");
		String id = sc.nextLine();
		
		System.out.print("- 변경할 비밀번호 : ");
		String pwd = sc.nextLine();
		
		System.out.print("- 변경할 성별(M/F) : ");
		char gender = sc.next().toUpperCase().charAt(0);
		sc.nextLine();
		
		System.out.print("- 변경할 주소 : ");
		String addr = sc.nextLine();
		
		System.out.print(" - 변경할 취미 : ");
		String hobby = sc.nextLine();
		
		mController.updateMember(id, pwd, gender, addr, hobby);
		
	}

	/**
	 * 회원 아이디로 정보 조회
	 */
	private void searchByIdMenu() {
		System.out.print("조회할 아이디 입력 : ");
		String searchId = sc.nextLine();
		
		mController.searchById(searchId);
		
	}

	/**
	 * 전체 회원 정보 출력
	 * @param list 회원 목록
	 */
	public void displayAllMembers(ArrayList<Member> list) {
		System.out.println(" ----- 조회된 회원 목록 -----");
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		/* for-each문
		for(Member m : list) {
			System.out.println(m);
		}
		*/
	}

	// 조회 결과가 없을 경우 출력
	public void displayNoData() {
		System.out.println(" ----- 조회된 회원정보가 없습니다. -----");
	}
	
	// 회원 추가 메뉴 : 아이디, 비밀번호, 성별, 나이를 입력 받아 회원 등록
	private void addMemberMenu() {
		System.out.print("아이디 : ");
		String id = sc.nextLine();
		System.out.print("비밀번호 : ");
		String pwd = sc.nextLine();
		System.out.print("성별(M/F) : ");
		char gender = sc.next().toUpperCase().charAt(0);
		System.out.print("나이 : ");
		int age = sc.nextInt();
		sc.nextLine();
		// m 또는 f 입력받은 경우에도 정상적으로 추가하고자 한다면 toUpperCase()
		// sc.next() 입력받은 문자열을 대문자로 변환한 다음 첫번째 문자만 추출
		
		// 회원 추가 요청 --> Controller 객체에게 id, pwd, gender, age 변수의 값을 전달
		mController.insertMember(id, pwd, gender, age);
	}
	
	// -----------------------------------------------------------------------------
	/**
	 * 요청에 대한 성공 시 결과 출력
	 * @param message 결과 메시지
	 */
	public void displaySuccess(String message) {
		System.out.println("* 서비스 요청 성공 : " + message);
	}
	
	/**
	 * 요청에 대한 실패 시 결과 출력
	 * @param message 결과 메시지
	 */
	public void displayFailed(String message) {
		System.out.println("* 서비스 요청 실패 : " + message);
	}
	
	/**
	 * 회원에 대한 정보를 출력
	 * @param m 회원 정보
	 */
	public void displayMember(Member m) {
		System.out.println(" ---- 조회된 회원 정보 ----");
		System.out.println(m);
	}
}
