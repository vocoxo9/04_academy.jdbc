package com.kh.model.vo;

import java.sql.Date;

/*
 * 	VO(Value Objct)
 * 	: 저장용 객체
 * 
 * --> 한 명의 회원에 대한 데이터를 저장하기 위한 객체
 * ==> DB테이블에서 한 행의 데이터
 */
public class Member {
	// 필드부	--> Member 테이블의 컬럼 정보와 매칭시켜 정의
	private int memberNo;	//	MEMBERNO	NUMBER
	private String memberId;	//	MEMBERID	VARCHAR2(20 BYTE)
	private String memberPwd;	//MEMBERPW	VARCHAR2(20 BYTE)
	private String gender;//	GENDER	CHAR(1 BYTE)
	private int age;//	AGE	NUMBER	
	private String email;//	EMAIL	VARCHAR2(30 BYTE)
	private String address;//	ADDRESS	VARCHAR2(100 BYTE)
	private String phone;//	PHONE	VARCHAR2(13 BYTE)
	private String hobby;//	HOBBY	VARCHAR2(50 BYTE)
	private Date enrolldate;//	ENROLLDATE	DATE
	
	// 생성자부
	public Member() {
		super();
	}
	
	public Member(int memberNo, String memberId, String memberPwd, String gender, int age, String email, String address,
			String phone, String hobby, Date enrolldate) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.hobby = hobby;
		this.enrolldate = enrolldate;
	}

	// 회원 추가 시 사용된 생성자
	public Member(String memberId, String memberPwd, String gender, int age) {
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.gender = gender;
		this.age = age;
	}

	// 회원 정보 수정 시 사용된 생성자
	public Member(String memberId, String memberPwd, String gender, String address, String hobby) {
		super();
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.gender = gender;
		this.address = address;
		this.hobby = hobby;
	}

	// 회원 정보 삭제 시 사용된 생성자
	public Member(String memberId) {
		super();
		this.memberId = memberId;
	}

	// 메소드부
	// getter, setter
	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrolldate() {
		return enrolldate;
	}

	public void setEnrolldate(Date enrolldate) {
		this.enrolldate = enrolldate;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd + ", gender="
				+ gender + ", age=" + age + ", email=" + email + ", address=" + address + ", phone=" + phone
				+ ", hobby=" + hobby + ", enrolldate=" + enrolldate + "]";
	}
	
}
