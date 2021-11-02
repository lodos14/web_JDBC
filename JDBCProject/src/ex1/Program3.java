package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program3 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		int id = 10;
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
		String sql = "DELETE NOTICE WHERE ID = ?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "ohji","tkwl1414");
		
		PreparedStatement st = con.prepareStatement(sql); 
		st.setInt(1, id); // index 1부터 시작
		
		int result = st.executeUpdate(); 
		
		System.out.println(result); // 1
		
		st.close();
		con.close();

	}

}
