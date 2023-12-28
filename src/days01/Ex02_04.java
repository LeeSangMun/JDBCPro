package days01;

import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

/**
 * @author sangmun
 * @date 2023. 9. 19. - 오후 3:29:10
 * @subject
 * @content
 */
public class Ex02_04 {

	public static void main(String[] args) throws SQLException {
		String ipAddress = "";
		String sid = "";
		
		Connection conn = DBConn.getConnection();
		System.out.println(conn.isClosed());
		DBConn.close();
		
		System.out.println("끝났다.");
	}

}
