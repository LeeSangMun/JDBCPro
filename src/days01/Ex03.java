package days01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author sangmun
 * @date 2023. 9. 19. - 오후 3:37:40
 * @subject
 * @content
 */
public class Ex03 {

	public static void main(String[] args) {
		// Scott.Dept 테이블 - CRUD
		// [dept 테이블 조회]
		String className = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.10.179:1521:xe";
		String user = "scott";
		String password = "tiger";
		
		Connection conn = DBConn.getConnection(url, user, password);
		
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM dept";
		
		int deptno = 0;
		String dname = "";
		String loc = "";
		DeptVO vo = null;
		ArrayList<DeptVO> list = null;
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			// 1) rs로부터 읽어올 다음 레코드 (행)가 있니?
			// 2) 다음 읽어올 레코드로 이동시키는 기능
			// boolean rs.next()
			
//			System.out.println(rs.next());
			
//			rs.next();
//			int deptno = rs.getInt("deptno");
//			String dname = rs.getString("dname");
//			String loc = rs.getString("loc");
//			
//			DeptVO vo = new DeptVO(deptno, dname, loc);
//			System.out.println(vo);
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					loc = rs.getString("loc");
					
					vo = new DeptVO(deptno, dname, loc);
					list.add(vo);
				} while(rs.next());
			}
			
			printDeptList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void printDeptList(ArrayList<DeptVO> list) {
		if(list == null) {
			System.out.println("> 부서 존재하지 않는다.");
			return;
		} 
		
		Iterator<DeptVO> ir = list.iterator();
		while(ir.hasNext()) {
			DeptVO vo = ir.next();
			System.out.println(vo);
		}
	}

}
