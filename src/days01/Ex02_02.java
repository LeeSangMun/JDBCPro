package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author sangmun
 * @date 2023. 9. 19. - 오후 2:51:35
 * @subject
 * @content
 */
public class Ex02_02 {

	public static void main(String[] args) {
		Connection conn = null;
		String className = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:scott/tiger@localhost:1521:xe";
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url);
			System.out.println(conn.isClosed());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				System.out.println(conn.isClosed());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
