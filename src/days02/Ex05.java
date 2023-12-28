package days02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오후 3:46:08
 * @subject
 * @content
 */
public class Ex05 {

	public static void main(String[] args) {
		// PreparedStatement
		// Scott.Dept 테이블 - CRUD
		// [dept 테이블 조회]
		String className = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.10.179:1521:xe";
		String user = "scott";
		String password = "tiger";

		Connection conn = DBConn.getConnection(url, user, password);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM dept";

		int deptno = 0;
		String dname = "";
		String loc = "";
		DeptVO vo = null;
		ArrayList<DeptVO> list = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				list = new ArrayList<>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");

					vo = new DeptVO(deptno, dname, loc);
					list.add(vo);
				} while (rs.next());
			}

			printDeptList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void printDeptList(ArrayList<DeptVO> list) {
		if (list == null) {
			System.out.println("> 부서 존재하지 않는다.");
			return;
		}

		Iterator<DeptVO> ir = list.iterator();
		while (ir.hasNext()) {
			DeptVO vo = ir.next();
			System.out.println(vo);
		}
	}

}