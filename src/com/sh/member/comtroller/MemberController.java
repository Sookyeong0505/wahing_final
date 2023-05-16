package com.sh.member.comtroller;

import java.util.ArrayList;

import java.util.List;

import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberLog;
import com.sh.member.model.dto.WashingMachine;
import com.sh.member.model.service.MemberService;

public class MemberController {

	// 의존 객체 생성
	private MemberService memberService = new MemberService();
	

	/**
	 * id, password를 입력받아 로그인
	 */
	public Member login(String id, String password) {
		Member member = memberService.login(id, password);
		return member;
	}
	
	public List<MemberLog> findAllMemberLog(String table){
		List<MemberLog> memberLogList = new ArrayList<>();
		memberLogList = memberService.findAllMemberLog(table);
		return memberLogList;
	}
	
	public List<Member> findAll(String table) {
		List<Member> memberList = new ArrayList<>();
		memberList = memberService.findAll(table);
		return memberList;
	}
	
	/**
	 * DB에 해당 아이디를 가지고 있는 멤버가 있는지 확인
	 */
	public Member findById(String id) {
		Member member = memberService.findById(id);
		return member;
	}

	public int insertMember(Member member) {
		int result =0;
		result = memberService.insertMember(member);
		return result;
	}

	public int chargePoint(Member member, int point) {
		int result =0;
		result = memberService.chargePoint(member, point);
		return result;
	}

	public int serviceStart(Member member, WashingMachine machine, String serviceName) {
		int result =0;
		result = memberService.serviceStart(member, machine, serviceName);
		return result;
	}


	public List<WashingMachine> showAllMachine() {
		List<WashingMachine> machineList = memberService.showAllMachine();
		return machineList;
	}


	/**
	 * 작성자: 신희진
	 * @return
	 */
	public List<WashingMachine> findByMachine() {
        List<WashingMachine> machineList = null;
        try {
            machineList = memberService.findByMachine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return machineList; 
    }

    public int deleteMember(String id) {
        int result = memberService.deleteMember(id);
        return result;
    }
    
    public int updateMember(String id, String colName, String newValue) {
        return memberService.updateMember(id, colName, newValue);
    }

	public WashingMachine findMachineByNo(String machineNo) {
        WashingMachine machine = null;
        try {
            machine = memberService.findMachineByNo(machineNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return machine; 
	}

	public void updateMachine(Member member, WashingMachine machine, String state, String serviceName) {
		memberService.updateMachine(member, machine, state, serviceName);
	}


	
}
