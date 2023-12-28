package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author sangmun
 * @date 2023. 9. 19. - 오후 2:26:43
 * @subject
 * @content
 */
public class Ex02 {

	public static void main(String[] args) {
		// 1. jdbc driver 로딩
		// 2. DriverManager.getConnection() -> Connection
		// 3. Statement -> CRUD 작업
		// 4. Connection Close()
	
		String className = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		
		Connection conn = null;
		
		try {
			Class.forName(className);
			// ORA-01017: invalid username/password; logon denied
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn.isClosed());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
