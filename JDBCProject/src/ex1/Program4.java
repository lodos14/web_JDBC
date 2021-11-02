package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program4 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		String writerId = "qqq";
		String content = "hahahaha33";
		String title = "TEST3";
		int id = 10;
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
		String sql = "UPDATE NOTICE " + 
				"SET" + 
				"    TITLE = ?," + 
				"    CONTENT = ?" + 
				"WHERE ID = ?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "ohji","tkwl1414");
		
		PreparedStatement st = con.prepareStatement(sql); 
		st.setString(1, title); // index 1부터 시작
		st.setString(2, content); 
		st.setInt(3, id);
		
		int result = st.executeUpdate(); 
		
		System.out.println(result); // 1
		
		st.close();
		con.close();

	}

}
