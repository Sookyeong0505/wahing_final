package com.sh.member.model.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Member {

	private String id;
	private String name;
	private String password;
	private String phone;
	private int point;
	private Timestamp regDate;
	private Timestamp delDate;
	private char stateYn; 

	public Member() {
		super();
	}

	public Member(String id, String name, String password, String phone, int point, Timestamp regDate,
			Timestamp delDate, char stateYn) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.point = point;
		this.regDate = regDate;
		this.delDate = delDate;
		this.stateYn = stateYn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public Timestamp getDelDate() {
		return delDate;
	}

	public void setDelDate(Timestamp delDate) {
		this.delDate = delDate;
	}

	public char getStateYn() {
		return stateYn;
	}

	public void setStateYn(char stateYn) {
		this.stateYn = stateYn;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", password=" + password + ", phone=" + phone + ", point="
				+ point + ", regDate=" + regDate + ", delDate=" + delDate + ", stateYn=" + stateYn + "]";
	}
	
	

}
