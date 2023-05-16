package com.sh.member.model.dto;

import java.sql.Timestamp;

public class WashingMachine {
	
	private String no; // 세탁기 번호
	private String state; // 사용가능여부 (사용가능, 사용불가)
	private String serviceName; // 진행중인 서비스
	private String memberId;
	public WashingMachine() {
		super();
	}
	public WashingMachine(String no, String state, String serviceName, String memberId) {
		super();
		this.no = no;
		this.state = state;
		this.serviceName = serviceName;
		this.memberId = memberId;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	

}
