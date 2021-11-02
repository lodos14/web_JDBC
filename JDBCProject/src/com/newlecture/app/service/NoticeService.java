package com.newlecture.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.app.entity.Notice;

public class NoticeService {
	
	private String url = "jdbc:oracle:thin:@localhost/xepdb1"; 
	private String uid = "ohji";
	private String pwd = "tkwl1414";
	private String driver = "oracle.jdbc.driver.OracleDriver";

	public List<Notice> getList(int page, String field, String query ) throws ClassNotFoundException, SQLException {
		
		int start = 1 + (page-1)*10; // 1, 11, 21 ,31
		int end = 10 * page; // 10, 20, 30, 40
		// prepareStatement 방식은 컬럼의 값을 넣을 때 사용
		String sql = "select * from (select ROW_NUMBER() OVER (ORDER BY regdate desc) as num," + 
				"notice.* from notice "
				+ "where "+field+ " like ? ) " + 
				"where num between ? and ?";
		
		Class.forName(driver); //
		Connection con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+query+"%");
		st.setInt(2, start);
		st.setInt(3, end);
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>(); // 게시물 객체를 담을 리스트 생성
	
		while (rs.next()) { 
			
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerId = rs.getString("writer_id");
			Date regDate = rs.getDate("regdate");
			String content = rs.getString("content");
			int hit = rs.getInt("hit");
			
			
			Notice notice = new Notice( // 게시물 객체 생성
						id,
						title,
						writerId,
						regDate,
						content,
						hit
					);
			
			list.add(notice); // 리스트에 게시물 객체 추가
			
		}
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	
	}
	
	public int getCount() throws ClassNotFoundException, SQLException {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM NOTICE";
		
		Class.forName(driver); 
		Connection con = DriverManager.getConnection(url, uid, pwd);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		if(rs.next()) {
			count = rs.getInt("COUNT");				
		}
		
		rs.close();
		st.close();
		con.close();
		
		return count;
	}
	
	public int insert(Notice notice) throws SQLException, ClassNotFoundException {
		
		String writerId = notice.getWriterid();
		String content = notice.getContent();
		String title = notice.getTitle();
		
		String sql = "INSERT INTO NOTICE (" + 
				"    writer_id," + 
				"    content," + 
				"    title" + 
				") VALUES (?,?,?)";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url,uid, pwd);
		PreparedStatement st = con.prepareStatement(sql); 
		st.setString(1, writerId); 
		st.setString(2, content);
		st.setString(3, title);
		
		int result = st.executeUpdate(); 
		
		st.close();
		con.close();
		
		return result;
	}
	
	public int update(Notice notice) throws SQLException, ClassNotFoundException {
		
		String content = notice.getContent();
		String title = notice.getTitle();
		int id = notice.getId();
		
		String sql = "UPDATE NOTICE " + 
				"SET" + 
				"    TITLE = ?," + 
				"    CONTENT = ?" + 
				"WHERE ID = ?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		
		PreparedStatement st = con.prepareStatement(sql); 
		st.setString(1, title); 
		st.setString(2, content); 
		st.setInt(3, id);
		
		int result = st.executeUpdate(); 
		
		st.close();
		con.close();
		
		return result;
	}
	
	public int delete(int id) throws SQLException, ClassNotFoundException {
		
		String sql = "DELETE NOTICE WHERE ID = ?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		
		PreparedStatement st = con.prepareStatement(sql); 
		st.setInt(1, id); 
		
		int result = st.executeUpdate(); 
		
		st.close();
		con.close();
		
		return result;
	}

	
}
