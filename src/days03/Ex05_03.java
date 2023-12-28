package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오후 12:45:43
 * @subject dept - update
 * @content
 */
public class Ex05_03 {

	public static void main(String[] args) {
		String sql = "{ call up_upddept(?, ?, ?) }";

		Connection conn = null;
		CallableStatement cstmt = null;
		DeptVO vo = null;

		int deptno;
		String dname = "EQC", loc = "ESEOUL";
		int rowCount = 0;

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, 50);
			cstmt.setString(2, dname);
			cstmt.setString(3, loc);
			rowCount = cstmt.executeUpdate();

			if (rowCount == 1) {
				System.out.println("> 부서 수정 완료!!!");
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
