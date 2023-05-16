package com.sh.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc 관련 static 메소드 제공 -> 공용으로 쓰기 좋다
 * 여기서 예외처리를 미리해서 클라이언트가 사용하기 쉽게 작성
 * 
 * - 드라비어클래스등록 (프로그램당 최초 1회) -> static 초기화블럭 사용
 * 
 * - Connection 생성
 * - commit/rollback
 * - close
 *
 */
public class JdbcTemplate {
	
	// 보안적으로 취약한 내용 -> 폴더로 관리 # datasource.properties
	private static String driverClass;
	private static String url;
	private static String user;
	private static String password;
	static {
		// resources/datasoures.properties 설정값 읽어오기
		Properties prop = new Properties();
		try {
			prop.load(new FileReader("resources/datasource.properties"));
			driverClass = prop.getProperty("driverClass");
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
		} catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			Class.forName(driverClass);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {
		Connection conn =null;
		
		try {
			conn =DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) // conn이 null이 아니면서, 닫혀있지 않다면
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) // conn이 null이 아니면서, 닫혀있지 않다면
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) // conn이 null이 아니면서, 닫혀있지 않다면
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null && !pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if (rset != null && !rset.isClosed())
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
