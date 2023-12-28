package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오후 12:45:43
 * @subject dept - delete
 * @content
 */
public class Ex05_04 {

	public static void main(String[] args) {
		String sql = "{ call up_deldept(?) }";

		Connection conn = null;
		CallableStatement cstmt = null;
		DeptVO vo = null;

		int rowCount = 0;

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, 50);
			rowCount = cstmt.executeUpdate();

			if (rowCount == 1) {
				System.out.println("> 부서 삭제 완료!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end");
	}

}
