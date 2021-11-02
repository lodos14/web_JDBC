package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		String writerId = "qqq";
		String content = "hahahaha";
		String title = "TEST2";
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; 
		String sql = "INSERT INTO NOTICE (" + 
				"    writer_id," + 
				"    content," + 
				"    title" + 
				") VALUES (?,?,?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "ohji","tkwl1414");
		//Statement st = con.createStatement(); // 실행하면서 문장을 전달
		PreparedStatement st = con.prepareStatement(sql); // 실행하기 전에 문장을 준비
		st.setString(1, writerId); // index 1부터 시작
		st.setString(2, content);
		st.setString(3, title);
		
		int result = st.executeUpdate(); // insert 성공시 반환 값은 1
		
		System.out.println(result); // 1
		
		st.close();
		con.close();

	}

}
