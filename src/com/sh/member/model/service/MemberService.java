package com.sh.member.model.service;

// static 자원(메소드)을 임포트하기 => 클래스명을 생략할 수 있게 된다.
import static com.sh.common.JdbcTemplate.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.sh.common.JdbcTemplate;
import com.sh.member.model.dao.MemberDao;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberLog;
import com.sh.member.model.dto.WashingMachine;

public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	public Member findById(String id) {
		Connection conn = getConnection();
		Member member = memberDao.findById(conn, id);
		close(conn);
		return member;
	}
	
	public List<Member> findAll(String table) {
		Connection conn = getConnection();
		List<Member> memberList = memberDao.findAll(conn, table);
		close(conn);
		return memberList;
	}

	public List<MemberLog> findAllMemberLog(String table){
		Connection conn = getConnection();
		List<MemberLog> memberLogList = memberDao.findAllMemberLog(conn, table);
		close(conn);
		return memberLogList;
	}
	
	/** 로그인 메소드
	 *  id, password를 입력받아서 로그인
	 */
	public Member login(String id, String password) {
		Connection conn = getConnection();
		Member member=null;
		try {
			member = memberDao.login(conn, id, password);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return member;
	}
	
	
	public int insertMember(Member member) {
		int result=0;
		Connection conn = getConnection();
		try {
			result = memberDao.insertMember(conn, member);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			e.printStackTrace(); // controller 에게 예외 사실을 알림
		} finally {
			close(conn);
		}
		return result;
	}


	public int chargePoint(Member member, int point) {
		int result=0;
		Connection conn = JdbcTemplate.getConnection();
		try {
			result = memberDao.chargePoint(conn, member, point);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			e.printStackTrace(); 
		} finally {
			close(conn);
		}
		return result;
	}


	public List<WashingMachine> showAllMachine() {
		Connection conn = getConnection();
		List<WashingMachine> machineList = memberDao.showAllMachine(conn);
		close(conn);
		return machineList;
	}


	public List<WashingMachine> findByMachine() {
        Connection conn = getConnection();
        List<WashingMachine> machineList = memberDao.findByMachine(conn);
        close(conn);
        return machineList;
    }

	public int serviceStart(Member member, WashingMachine machine, String serviceName) {
		int result=0;
		Connection conn = JdbcTemplate.getConnection();
		try {
			result = memberDao.serviceStart(conn, member, machine, serviceName);
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			e.printStackTrace(); 
		} finally {
			close(conn);
		}
		return result;
	}

    public int deleteMember(String id) {
        int result = 0;
        Connection conn = getConnection();
        try {
            result = memberDao.deleteMember(conn, id);
            commit(conn);
        } catch (Exception e) {
            rollback(conn);
            e.printStackTrace(); 
        } finally {
            close(conn);
        }
        return result;
    }
    
    
    public int updateMember(String id, String colName, String newValue) {
        Connection conn = getConnection();
        int result = 0;
        try {
            result = memberDao.updateMember(conn, id, colName, newValue);
            commit(conn);
        } catch (Exception e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            close(conn);            
        }
        return result;
    }


	public WashingMachine findMachineByNo(String machineNo) {
        Connection conn = getConnection();
        WashingMachine machine = memberDao.findMachineByNo(conn, machineNo);
        close(conn);
        return machine;
	}


	public void updateMachine(Member member, WashingMachine machine, String state, String serviceName) {
        Connection conn = getConnection();
        int result = 0;
        try {
            result = memberDao.updateMachine(conn, member, machine, state, serviceName);
            commit(conn);
        } catch (Exception e) {
            rollback(conn);
            e.printStackTrace();
        } finally {
            close(conn);            
        }
	}
	
}
