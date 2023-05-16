package com.sh.member.model.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberLog;
import com.sh.member.model.dto.WashingMachine;
import com.sh.member.model.exception.MemberException;

public class MemberDao {

	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.load(new FileReader("resources/member-query.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public List<Member> findAll(Connection conn, String table){
		List<Member> memberList = new ArrayList<>();
		String sql = "select * from # ";
		sql = sql.replace("#", table);
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			try (ResultSet rset = pstmt.executeQuery()){

				while(rset.next()) {
					Member member = handleMemberResusltSet(rset);
					memberList.add(member);
				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberList;
	}
	
	
	public List<MemberLog> findAllMemberLog(Connection conn, String table){
		List<MemberLog> memberLogList = new ArrayList<>();
		String sql = "select * from # ";
		sql = sql.replace("#", table);
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			try (ResultSet rset = pstmt.executeQuery()){

				while(rset.next()) {
					MemberLog memberLog = new MemberLog();
					memberLog.setId(rset.getString("id"));
					memberLog.setDate(rset.getTimestamp("log_date"));
					memberLog.setServiceName(rset.getString("servicename"));
					memberLog.setNo(rset.getString("no"));
					memberLog.setChargedPoint(rset.getInt("charged_point"));
					memberLog.setSpentPoint(rset.getInt("spent_point"));
					memberLog.setPoint(rset.getInt("point"));
					memberLogList.add(memberLog);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberLogList;
	}
	
	
	/** 로그인 
	 *  id, password가 일치하는 멤버가 있는지 조회
	 */
    public Member login(Connection conn,String id, String password) {
        Member member = new Member();
        String sql = "select * from member where id = ? and password = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            
            try (ResultSet rset = pstmt.executeQuery()){
                if(rset.next())
                member = handleMemberResusltSet(rset);
            }
            
        } catch (SQLException e) {
            throw new MemberException("로그인오류", e);
        }
        return member;
    }

	/**
	 * 회원가입 메소드
	 * DB에 입력받은 정보로 만든 회윈정보를 insert 한다
	 */
	public int insertMember(Connection conn, Member member){
		String sql = "insert into member values (?, ?, ?, ?, default, default, null, default)";
		int result=0;
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("회원가입오류", e);
		}
		return result;
	}
	


	/** 포인트 충전
	 * DB 정보 수정
	 */
	public int chargePoint(Connection conn, Member member, int point) {

		int result=0;
		String sql = "update member set point = point + ? where id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, point);
			pstmt.setString(2, member.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new MemberException("포인트충전 오류", e);
		}
		return result;
	}
	

	/**
	 * DB에 해당 id를 가지고 있는 멤버 객체가 있는지 리스트로 반환
	 * @return
	 */
	public Member findById(Connection conn, String id) {
        Member member = null;
        String sql = "select * from member where id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            
            try(ResultSet rset = pstmt.executeQuery();){
                if(rset.next())
                    member = handleMemberResusltSet(rset);
            }
            
        } catch (SQLException e) {
            throw new MemberException("회원 조회 오류", e);
        } 
        return member;
    }

	private Member handleMemberResusltSet(ResultSet rset) throws SQLException {
		Member member = new Member();
		member.setId(rset.getString("id"));
		member.setName(rset.getString("name"));
		member.setPassword(rset.getString("password"));
		member.setPhone(rset.getString("phone"));
		member.setPoint(rset.getInt("point"));
		member.setRegDate(rset.getTimestamp("reg_date"));
		return member;
	}


	public int serviceStart(Connection conn, Member member, WashingMachine machine, String serviceName) {
		int result=0;
		String sql = null;
		int spentPoint=0;
		// 회원로그 관리자용에 insert
		// -> machine 테이블에서 기게 상태 update
		// -> member_log 테이블에서 사용 기록 insert
		switch(serviceName){
		case "standard" : 
			sql = "insert member_log values(?, default, 'standard', ?, ?, 0, ?)"; 
			spentPoint=4500;
			break;
		case "warm" : 
			sql = "insert member_log values(?, default, 'warm', ?, ?, 0, ?)"; 
			spentPoint=5000;
			break;
		case "power" : 
			sql = "insert member_log values(?, default, 'power', ?, ?, 0, ?)"; 
			spentPoint=5000;
			break;
		case "brain" : 
			sql = "insert member_log values(?, default, 'brain', ?, ?, 0, ?)"; 
			spentPoint=10000;
			break;
		default : 
		}
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, member.getId());
			pstmt.setString(2, machine.getNo());
			pstmt.setInt(3, member.getPoint());
			pstmt.setInt(4, spentPoint);  // 서비스 비용
			
		} catch (SQLException e) {
			throw new MemberException("세탁서비스 오류", e);
		}
		
		return result;
	}


	public List<WashingMachine> showAllMachine(Connection conn) {
		List<WashingMachine> machineList = new ArrayList<>();
		String sql = "select * from machine";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			try (ResultSet rset = pstmt.executeQuery()){
				
				while(rset.next()) {
					WashingMachine machine = new WashingMachine();
					machine.setNo(rset.getString("no"));
					machine.setState(rset.getString("state"));
					machine.setServiceName(rset.getString("service_name"));
					machineList.add(machine);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return machineList;
	}


	public List<WashingMachine> findByMachine(Connection conn) {
        List<WashingMachine> machineList = new ArrayList<>();
        
        String sql = "select * from machine";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            try(ResultSet rset = pstmt.executeQuery();){
                while(rset.next()) {
                    WashingMachine machine = new WashingMachine();
                    machine.setNo(rset.getString("no"));
                    machine.setState(rset.getString("state"));
                    machine.setServiceName(rset.getString("service_name"));
                    
                    machineList.add(machine);
                }
            }
        } catch (SQLException e) {
            throw new MemberException("회원 1명 조회 오류", e);
        } 
        return machineList;
    }

	
    public int deleteMember(Connection conn, String id) throws SQLException{
        String sql = "delete from member where id = ? ";
        int result = 0;
        
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            result = pstmt.executeUpdate();
        } catch(SQLException e) {
            throw e;
        }
        return result;
    }
	
    public int updateMember(Connection conn, String id, String colName, String newValue) {
        String sql = "update member set # = ? where id = ?";
        sql = sql.replace("#", colName);
        int result = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setObject(1, newValue);
            pstmt.setString(2, id);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new MemberException("회원정보 수정오류", e);
        }
        return result;
    }


	public WashingMachine findMachineByNo(Connection conn, String machineNo) {
        WashingMachine machine = null;
        String sql = "select * from machine where no = ?";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, machineNo);
            
            try(ResultSet rset = pstmt.executeQuery();){
                while(rset.next()) {
                    machine = new WashingMachine();
                    machine.setNo(rset.getString("no"));
                    machine.setState(rset.getString("state"));
                    machine.setServiceName(rset.getString("service_name"));
                }
            }
        } catch (SQLException e) {
            throw new MemberException("세탁기 조회 오류", e);
        } 
        return machine;
	}


	public int updateMachine(Connection conn, Member member, WashingMachine machine, String state, String serviceName) {
        String no = machine.getNo();
        String memberId = member.getId();
		String sql = "update machine set state = ?, service_name = ?, member_id = ? where no = ?";
        int result = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, state);
            pstmt.setString(2, serviceName);
            pstmt.setString(3, memberId);
            pstmt.setString(4, no);
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new MemberException("세탁기상태 수정오류", e);
        }
        return result;
	}
    
}
