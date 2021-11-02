package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
		String sql = "SELECT * FROM NOTICE WHERE HIT = 0";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "ohji","tkwl1414");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while (rs.next()) { 
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerid = rs.getString("writer_id");
			Date regDate = rs.getDate("regdate");
			String content = rs.getString("content");
			int hit = rs.getInt("hit");
			
			
			System.out.printf("id : %d, title : %s, writerid : %s, regDate : %s, content : %s, hit : %d \n",
					id, title, writerid, regDate, content, hit);
			
		}
		rs.close();
		st.close();
		con.close();

	}

}
