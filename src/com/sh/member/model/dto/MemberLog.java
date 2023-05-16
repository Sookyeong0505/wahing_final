package com.sh.member.model.dto;

import java.sql.Timestamp;

public class MemberLog {
	private String id;
	private Timestamp date;
	private String serviceName;
	private String no;
	private int point;
	private int chargedPoint;
	private int spentPoint;
	
	public MemberLog() {
		super();
	}

	public MemberLog(String id, Timestamp date, String serviceName, String no, int point, int chargedPoint,
			int spentPoint) {
		super();
		this.id = id;
		this.date = date;
		this.serviceName = serviceName;
		this.no = no;
		this.point = point;
		this.chargedPoint = chargedPoint;
		this.spentPoint = spentPoint;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getChargedPoint() {
		return chargedPoint;
	}

	public void setChargedPoint(int chargedPoint) {
		this.chargedPoint = chargedPoint;
	}

	public int getSpentPoint() {
		return spentPoint;
	}

	public void setSpentPoint(int spentPoint) {
		this.spentPoint = spentPoint;
	}

	@Override
	public String toString() {
		return "Member_log [id=" + id + ", date=" + date + ", serviceName=" + serviceName + ", no=" + no + ", point="
				+ point + ", chargedPoint=" + chargedPoint + ", spentPoint=" + spentPoint + "]";
	}

	
	
	
}
