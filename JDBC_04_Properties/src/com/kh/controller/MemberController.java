package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

/*
 * 	Controller : View로부터 사용자가 요쳥한 기능에 대해 처리하는 역할
 * 				 전달받은 데이터를 가공처리한 후 DAO로 전달하여 호출
 * 				 DAO로부터 반환 받은 결과에 따라 성공인지, 실패인지 판단 후 응답
 */
public class MemberController {
	private MemberService mService = new MemberService();
	
	/**
	 * 회원 추가 요청을 처리할 메소드
	 * @param id		신규회원 아이디
	 * @param pwd		회원 비밀번호
	 * @param gender	회원 성별
	 * @param age		회원 나이
	 */
	public void insertMember(String id, String pwd, char gender, int age) {
		// View로부터 전달 받은 값들을 DAO에게 바로 전달하지 않고
		// Member 객체에 담아서 전달
		
		// * Member 객체 생성
		// - 기본생성자로 생성 후 setter를 이용해 저장
		// - 매개변수가 있는 생성자를 사용하여 저장
		Member m = new Member(id, pwd, gender+"", age);
		
		// DAO에게 신규회원정보(Member)를 전달하여 추가 요청
		// int result = mDao.insertMember(m);
		int result = mService.insertMember(m);
		
		if(result > 0) {
			// 회원 추가 성공 출력 (=> View를 이용하여)
			new MemberMenu().displaySuccess("* 회원 추가 성공 *");
		} else {
			// 회원 추가 실패 출력(=> View를 이용하여)
			new MemberMenu().displayFailed("* 회원 추가 실패 *");
		}
	}
	
	/**
	 * 전체 회원 정보 조회 메소드
	 */
	public void selectAll() {
		ArrayList<Member> list = mService.selectAllList();
		
		// 조회된 결과에 따라 사용자에게 표시(출력)
		// if (list.size() == 0)
		if(list.isEmpty()) {
			// 조회 결과가 없을 경우 (=> 리스트가 비어있다면)
			new MemberMenu().displayNoData();
		}else {
			// 조회 결과가 있을 경우
			new MemberMenu().displayAllMembers(list);
		}
	}

	/**
	 * 전달된 아이디가 포함된 회원 정보 조회
	 * @param searchId 회원 아이디
	 */
	public void searchById(String id) {
		ArrayList<Member> list = mService.searchById(id);
		// 기능 변경 : 아이디에 해당하는 회원이 아닌, 전달된 아이디가 포함된 회원 정보들을 조회 -> 반환타입이 Member에서 ArrayList<Member>로 변경됨
		if(list.size() == 0) {
			// 조회된 결과가 없는 경우
			new MemberMenu().displayNoData();
		} else {
			// 조회된 결과가 있는 경우
//			new MemberMenu().displayMember(m);
			new MemberMenu().displayAllMembers(list);
		}
	}

	/**
	 * 회원 아이디 기준으로 회원 정보 변경
	 * @param id		회원 아이디
	 * @param pwd		변경할 비밀번호
	 * @param gender	변경할 성별
	 * @param addr		변경할 주소
	 * @param hobby		변경할 취미
	 */
	public void updateMember(String id, String pwd, String gender, String addr, String hobby) {

		Member m = new Member(id, pwd, gender, addr, hobby);
		int result = mService.updateMember(m);
		if(result > 0) {
			// 회원 정보 수정 성공 시
			new MemberMenu().displaySuccess(" 회원 정보 수정 성공 ");
		} else {
			// 회원 정보 수정 실패 시
			new MemberMenu().displayFailed(" 회원 정보 수정 실패 ");
		}
	}

	/**
	 * 전달된 회원 아이디에 해당하는 회원 정보 삭제
	 * @param id 	탈퇴할 회원 아이디
	 */
	public void deleteMember(String id) {
		
		int result = mService.deleteMember(id);
		if(result > 0) {
			new MemberMenu().displaySuccess(" 회원 탈퇴 성공 ");
		} else {
			new MemberMenu().displayFailed(" 회원 탈퇴 실패 ");
		}
		
	}
}
