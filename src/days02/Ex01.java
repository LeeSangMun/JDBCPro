package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.EmpVO;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오전 9:00:48
 * @subject
 * @content
 */
public class Ex01 {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EmpVO> list = null;
		EmpVO empVO = null;

		Scanner sc = new Scanner(System.in);
		System.out.print("> 부서 입력 ? ");
		int inputDeptno = sc.nextInt();

		String sql = String.format("SELECT * "
						+ " FROM emp "
						+ " WHERE deptno = %d", inputDeptno);

		int empno = 0;
		String ename = "";
		String job = "";
		int mgr = 0;
		String hiredate = null;
		double sal = 0;
		double comm = 0;
		int deptno = 0;

		try {
//			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt(1);
					ename = rs.getString(2);
					job = rs.getString(3);
					mgr = rs.getInt(4);
					hiredate = rs.getString(5);
					sal = rs.getDouble(6);
					comm = rs.getDouble(7);
					deptno = rs.getInt(8);
					empVO = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
					list.add(empVO);
				} while (rs.next());
			}

			printEmpList(list);
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

	private static void printEmpList(ArrayList<EmpVO> list) {
		if (list == null) {
			System.out.println("> 부서에 사원이 존재하지 않는다.");
			return;
		}

		int count = list.size();
		int deptno = list.get(0).getDeptno();
		System.out.printf("> %d번 부서의 사원수 : %d 명\n", deptno, count);
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
	}
}
