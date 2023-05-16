package com.sh.member.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import com.sh.member.comtroller.MemberController;
import com.sh.member.model.dto.Member;
import com.sh.member.model.dto.MemberLog;
import com.sh.member.model.dto.WashingMachine;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private List<WashingMachine> machineList = new ArrayList<>();
	
	private MemberController memberController = new MemberController();
	
	  public void startMenu() {
	        String menu = "===== 시작 프로그램 =====\n"
	                + "1. 로그인\n"
	                + "2. 회원가입\n"
	                + "3. 아이디조회\n"
	                + "4. 회원정보변경\n"
	                + "5. 회원탈퇴\n"
	                + "=========================\n"
	                + "선택 : ";
	        
	        Member member = null;
	        
	        while(true) {
	            System.out.print(menu);
	            String choice = sc.next();
	            int result =0;
	            String id = null;
	            
	            switch(choice) {
	            case "1" : // 로그인
	                member = login();
	                userMenu(member);
	                break;
	            case "2" : // 회원 가입
	                member = inputMember(); // 회원가입으로 멤버 생성
	                result = memberController.insertMember(member); // 테이블에 멤버 삽입
	                displayResult("회원가입", result);
	                break;
	            case "3" : // 아이디조회
	                id = inputId();
	                member = memberController.findById(id);
	                memberInfo(member);
	                break;
	            case "4" : // 회원정보변경
	                updateMenu();
	                break;
	                
	            case "5" : // 회원탈퇴
	                id = inputId();
	                result = memberController.deleteMember(id);
	                displayResult("회원탈퇴", result);
	                break;
	                
	            case "666" : // 관리자 메뉴
	                managerMenu(member);
	                break;
	            case "0" : return; // 프로그램 종료
	            default : System.out.println(">> 잘못 입력하셨습니다.");
	            }
	        }
	    }
	

    /** 관리자용 메뉴
     */
    private void managerMenu(Member member) {
        
        // 1. 매니저 아이디, 비밀번호 확인
        System.out.println("관리자 계정으로 접속완료");
        String menu = "===== 관리자 계정 접속 =====\n"
                    + "1. 전체 회원 조회\n"
                    + "2. 포인트내역 조회\n"
                    + "0. 프로그램 종료\n"
                    + "=========================\n"
                    + "선택 : ";        
        while(true) {
            System.out.print(menu);
            String choice = sc.next();
            
            List<Member> memberList = null;
            List<MemberLog> memberLogList = null;
            switch(choice) {
            case "1" : // 전체 회원 조회
            	memberList = findAll("member");
                displayMemberList(memberList);
                break;
                 
            case "2" : // 회원사용기록조회
            	memberLogList = findAllMemberLog("member_log");
                displayMemberLogList(memberLogList);
                 break;
            case "0" : // 프로그램 종료
                System.out.println("프로그램을 종료합니다. ");
                return; 
            default : System.out.println("> 잘못 입력하셨습니다. 다시 입력해주세요");
            }
        }
    }
    
	public List<Member> findAll(String table) {
		List<Member> memberList = new ArrayList<>();
		memberList = memberController.findAll(table);
		return memberList;
	}
    
	public List<MemberLog> findAllMemberLog(String table) {
		List<MemberLog> memberLogList = new ArrayList<>();
		memberLogList = memberController.findAllMemberLog(table);
		return memberLogList;
	}
    
    /**
     * 회원정보 수정 메뉴
     */
    private void updateMenu() {
        String menu = "+++++++++ 회원 정보수정 +++++++++\n"
                + "1. 이름 변경\n"
                + "2. 비밀번호 변경\n"
                + "3. 전화번호 변경\n"
                + "0. 메인메뉴로 돌아가기\n"
                + "++++++++++++++++++++++++++++++\n"
                + "선택 : ";
        String id = inputId();
        Member member=null;
        
        while(true) {
            member = memberController.findById(id);
            if(member == null) {
                System.out.println(">> 조회된 회원이 없습니다. 메인메뉴로 돌아갑니다");
                return;
            } else {
                memberInfo(member);
            }
            
            System.out.print(menu);
            String choice = sc.next();
            String colName = null;
            String newValue = null;
            
            switch(choice) {
            case "1" : // 이름 변경
                System.out.print("> 변경할 이름을 입력해주세요. :");
                colName = "name";
                newValue = sc.next();
                break;
                
            case "2" : // 비밀번호 변경
                System.out.print("> 변경할 비밀번호를 입력해주세요. :");
                colName = "password";
                newValue = sc.next();
                break;
                
            case "3" : // 전화번호 변경
                System.out.print("> 변경할 전화번호를 입력해주세요. :");
                colName = "phone";
                newValue = sc.next();
                break;
                
            case "0" : return;
            default :
                System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
                continue;
            }
            
            int result = memberController.updateMember(id, colName, newValue);
            System.out.println(result > 0 ? "> 회원정보 변경이 완료되었습니다.😉" : "> 회원정보 변경에 실패하였습니다.😢");
        }
    }

    /**
     * 회원정보 전체 조회결과 출력 메소드
     */
    
    private void displayMemberList(List<Member> memberList) {
        if(memberList == null || memberList.isEmpty()) {
            System.out.println("> 조회된 회원이 없습니다.");
        }
        else {
            System.out.println("--------------------------------------------------------");
            System.out.printf("%-10s %-10s %-10s %-10s \n", 
                      "id", "name", "password", "point");
            System.out.println("--------------------------------------------------------");
            for(Member member : memberList) {
                System.out.printf("%-10s %-10s %-10s %-10s \n", 
                        member.getId(), member.getName(), member.getPassword(), member.getPoint());
            System.out.println("--------------------------------------------------------");
            }
            System.out.println("--------------------------------------------------------");
            }
    }

    private void displayMemberLogList(List<MemberLog> memberLogList) {
        if(memberLogList == null || memberLogList.isEmpty()) {
            System.out.println("> 조회된 기록이 없습니다.");
        }
        else {
            System.out.println("--------------------------------------------------------");
            System.out.printf("%-10s %-20s %-15s %-10s \n", 
                      "회원아이디", "사용시간", "서비스명", "포인트");
            System.out.println("--------------------------------------------------------");
            for(MemberLog member : memberLogList) {
                System.out.printf("%-10s %-20s %-15s %-10s \n", 
                        member.getId(), member.getDate(), member.getServiceName(), member.getPoint());
            System.out.println("--------------------------------------------------------");
            }
            System.out.println("--------------------------------------------------------");
            }
    }
    
	/** 로그인
	 *  회원의 아이디, 비밀번호를 입력받고
	 *  DB에서 일치하는 member 객체를 가져옴
	 */
	private Member login() {
		String id = inputId();
		String password = inputPassword();
		Member member = memberController.login(id, password);
		return member;
	}


	/**
	 * 아이디를 입력받는 메소드
	 */
	private String inputId() {
		System.out.print(">> 아이디 입력 : ");
		return sc.next();
	}
	
	/**
	 * 비밀번호를 입력받는 메소드
	 */
	private String inputPassword() {
		System.out.print(">> 비밀번호 입력 : ");
		return sc.next();
	}

	
	/**
	 * 사용자 메뉴
	 */
	public void userMenu(Member member) {
		
		String menu = "===== 사용자 프로그램 =====\n"
                + "1. 세탁하기\n"
                + "2. 포인트 충전\n"
                + "3. 회원정보 조회\n"
                + "0. 로그아웃\n"
                + "=========================\n"
                + "선택 : ";
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			switch(choice) {
			case "1" : // 세탁하기
				serviceMenu(member); 
				break;
			case "2" : // 포인트 충전 메뉴로
				chargeMenu(member);
				break;
			case "3" : // 회원정보조회
				memberInfo(member);
				break;
			case "0" : 
				return;
			default : System.out.println(">> 잘못 입력하셨습니다.");
			}
		}
	}




	/** 
	 * 세탁 서비스 메뉴
	 */
	private void serviceMenu(Member member) {
		
		String menu = "========= 세탁기 사용 ========\n"
                + "1. 일반 세탁 (Standard Wash)\n"
                + "2. 따뜻한 물 세탁 (Warm Wash)\n"
                + "3. 초강력 세탁 (Power Wash)\n"
                + "4. 뇌 세탁 (Brain Wash)\n"
                + "0. 사용자 메뉴로\n"
                + "=========================\n"
                + "선택 : ";
		
		WashingMachine machine = selectMachine(); // 세탁기 선택
		
		while(true) {
			System.out.println(menu);
			String choice = sc.next();
			int result = 0;
			String serviceName=null;
			boolean flag=false;
			
			switch(choice) {
			case "1" : // 일반세탁
				serviceName= "standard";
				showServiceInfo(member, machine, serviceName);
				break;
			case "2" : // 따뜻한 세탁
				serviceName= "warm";
				showServiceInfo(member, machine, serviceName);
				break;
			case "3" : // 초강력 세탁
				serviceName= "power";
				showServiceInfo(member, machine, serviceName);
				break;
			case "4" : // 뇌 세탁
				serviceName= "brain";
				showServiceInfo(member, machine, serviceName);
				break;
			case "0" : return;
			default : System.out.println(">> 잘못 입력하셨습니다.");
			}
			
			// 선택한 서비스 정보 보여줌
			// showServiceInfo(member, machine, serviceName);
			
			// 실행 여부 결정
			flag = conformService(member,machine, serviceName);
			
			
			if(flag) {
				if(serviceName.equals("brain")) brainWash();
				
				selectService(member, machine, serviceName);
				result = memberController.serviceStart(member, machine, serviceName);
				break;
			} 
		}
		
	}
		

	private void brainWash() {
		 System.out.print("뇌 세탁을 시작합니다.");
	     try {
	    	 for(int i =0 ; i<10; i++) {
	    		 System.out.println(i);
	    		 Thread.sleep(1000);
	    	 }
	         
	     }catch(InterruptedException e) {
	         e.printStackTrace();
	     }
	     System.out.println("뇌 세탁이 완료되었습니다.");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
	     System.out.println("행복해~~");
		
	}


	/**
	 * 사용할 서비스를 확정한 후, DB에 세탁기 사용 내역을 업데이트한다.
	 */
	private void selectService(Member member, WashingMachine machine, String serviceName) {
		memberController.updateMachine(member, machine, "사용중", serviceName);
		System.out.println(serviceName + " 서비스를 선택했습니다.");
	}

	
	
	private boolean conformService(Member member, WashingMachine machine, String serviceName) {
		System.out.print(">> 이대로 진행하시겠습니까?(Y/N) : ");
		String choice = sc.next();
		if(choice.equals("N")) {
			System.out.println();
			System.out.println("세탁을 실행을 취소합니다.");
			return false;
		}else {
		//세탁실행시 세탁 사용내용 출력
			System.out.println(" 세탁기 사용 정보 ");
			System.out.print(" 사용 세탁기 번호 : " + machine.getNo()+ "\n");
			System.out.print(" 사용 서비스 : " + serviceName + "\n");
			System.out.println();
			return true;
		}
	}
	
	/**
	 * 선택한 세탁 서비스에 대한 정보를 보여준다.
	 * 
	 */
	public void showServiceInfo(Member member, WashingMachine machine, String serviceName) {
		
		String desc = "";
		int minute=0;
		int price=0;
		int point= 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		
		switch(serviceName) {
		case "standard": 
			desc += "일반세탁 : 찬물- 세탁1회 + 헹굼2회\n";
			minute =60;
			price=4500;
			serviceExplain(member,cal, format, desc, minute, price);
			break;
		case "warm": 
			desc += "따뜻한물 세탁 : 세탁1회 + 헹굼2회 \n";
			minute=50;
			price=5000;
			serviceExplain(member,cal, format, desc, minute, price);
			break;
		case "power": 
			desc += "찌든때세탁 : 따뜻한물 - 항균세탁1회 + 헹굼3회 \n";
			minute=60;
			price=5000;
			serviceExplain(member,cal, format, desc, minute, price);
			break;
		case "brain": 
			desc += "뇌세탁 - 뇌깨끗 \n";
			minute = 10;
			price = 10000;
			cal.add(Calendar.SECOND, minute);
			desc += "걸리는 시간 : 10초 => 완료 예상 시각 : " + format.format(cal.getTime()) + " \n" ;
			point = member.getPoint() - 10000;
			if(point > 0) {
				desc += "차감 후 보유 포인트 : " + member.getPoint() + "\n";
			} else {
				System.out.println("포인트가 부족합니다. 포인트를 충전해주세요.");
				return;
			}
			break;
		default: System.out.println(">> 잘못 입력했습니다.");
			}
	}

	private void serviceExplain(Member member, Calendar cal, SimpleDateFormat format, String desc, int minute, int price) {
		System.out.print(desc);
		cal.add(Calendar.MINUTE, minute);
		desc = "걸리는 시간 : "+ minute +"분 => 완료 예상 시각 : " + format.format(cal.getTime()) + " \n" ;
		System.out.print(desc);
		desc = "보유 포인트 : " + member.getPoint() + "\n";
	}
	
	
	
	/** 세탁기 선택
	 * @return
	 */
	private WashingMachine selectMachine() {
		WashingMachine machine=null;
		String machineNo = null;
		
		while(true) {
			machineList = searchMachine(); 
			
			if(machineList.isEmpty()) {
				System.out.println(">> 사용할 수 있는 기계가 없습니다. 메뉴로 돌아갑니다.");
				return null;
			} else {
				System.out.print(">> 세탁기 선택 : ");
				machineNo = sc.next();
				machine = memberController.findMachineByNo(machineNo);
				System.out.println(">> " + machine.getNo() + " 세탁기를 선택했습니다.");
				break;
			}
			
		}
		return machine;
	}

	/**
     * 희진
     * */
    private List<WashingMachine> searchMachine() {
        List<WashingMachine> machineList = memberController.findByMachine();
        
        if(machineList.size() > 0) {
            System.out.println("――――――사용가능한 세탁기――――――\n"
            			+ "―――――――――――――――――――――――\n"
            			+ "｜ 1 ｜ 2 ｜ 3 ｜ 4 ｜ 5  ｜\n"
            			+ "―――――――――――――――――――――――\n");
            for (WashingMachine machine : machineList)
            	System.out.println("번호 : " + machine.getNo() + "\t상태 : "+ machine.getState());
            System.out.println("------------------------------");
            
        }else {
            System.out.println("사용가능한 세탁기가 없습니다.");
        }
        return machineList;
        
    }


	/** 포인트 충전 메뉴
	 * @param member
	 */
	private void chargeMenu(Member member) {
		String menu = "========= 충전 ========\n"
                + "1. 10,000원 충전\n"
                + "2. 30,000원 충전 ( + 1,000 포인트 추가 ) \n"
                + "3. 50,000원 충전 ( + 5,000 포인트 추가 ) \n"
                + "0. 사용자 메뉴로\n"
                + "=========================\n"
                + "선택 : ";
		int result=0;
		
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			String id = member.getId();
			switch(choice) {
			case "1" : // 10,000원 충전
				result = memberController.chargePoint(member, 10000);
				displayResult("포인트 충전", result);
				member = memberController.findById(id);
				System.out.println("현재 보유 포인트 : " + member.getPoint());
				return;
			case "2" : // 30,000원 충전 (+1,000 포인트 추가)
				result = memberController.chargePoint(member, 31000);
				member = memberController.findById(id);
				displayResult("포인트 충전", result);
				System.out.println("현재 보유 포인트 : " + member.getPoint());
				return;
			case "3" : // 50,000원 충전 (+5,000 포인트 추가)
				result = memberController.chargePoint(member, 55000);
				member = memberController.findById(id);
				displayResult("포인트 충전", result);
				System.out.println("현재 보유 포인트 : " + member.getPoint());
				return;
			case "0" : return;
			default : System.out.println(">> 잘못 입력하셨습니다.");
			}
		}
		
	}
	

	/** 회원정보를 조회
	 *  이름
	 *  전화번호
	 *  가입일
	 *  보유 포인트
	 * @param member
	 */
	private void memberInfo(Member member) {
		
		if(member == null) {
			System.out.println(">> 존재하지 않는 회원정보 입니다.");
			return;
		}
		String id = member.getId();
		member = memberController.findById(id);
		System.out.println("------------------------------");
		System.out.println("이름 :" + member.getName());
		System.out.println("전화번호 :" + member.getPhone());
		System.out.println("가입일 :" + member.getRegDate());
		System.out.println("보유 포인트 :" + member.getPoint());
		System.out.println("------------------------------");
	}


    /**
     * 회원가입
     * 1. 아이디 입력 -> 중복 체크
     * 2. 비밀번호 입력
     * 3. 전화번호 입력
     * @return
     */
    private Member inputMember() {
        Member member = new Member();
        String id =null;
        
        while(true) {
            id = CheckId();
            member.setId(id); // 아이디 중복검사
            
            System.out.print(">> 이름 : ");
            member.setName(sc.next());
            
            System.out.print(">> 비밀번호 : ");
            member.setPassword(sc.next());
            
            System.out.print(">> 전화번호(01033336666) : ");
            member.setPhone(sc.next());
            
            System.out.println(">> 회원 가입 완료! 로그인 해주세요!");
            return member;
    
        }
    }
		

    /**
     * 중복 아이디 검사
     */
    private String CheckId() {
        String id = null;
        do {
            if(id != null)
                System.out.printf("> [%s]는 이미 사용중인 아이디입니다. 다른 아이디를 입력해주세요.%n", id);
            
            System.out.println(">> 아이디 : ");
            id = sc.next();
        } while (memberController.findById(id) != null);
        
        return id;
    }

    /*
     * 에러메세지
     */
    
    public static void displayError(String message) {
        System.err.println("> 관리자에게 문의바랍니다. [" + message + "]");
    }
    
    /**
     * DML처리결과 - 회원탈퇴
     */
    private void displayResult(String name, int result) {
        if(result > 0)
            System.out.println("> " + name + "성공!");
        else
            System.out.println("> " + name + "실패!");
    }
	
}
