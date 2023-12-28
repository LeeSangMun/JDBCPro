package days02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.util.DBConn;

import domain.EmpVO;
import domain.SalgradeVO;

public class Ex06 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Map<SalgradeVO, ArrayList<EmpVO>> map = null;
		ArrayList<EmpVO> list = null;
		EmpVO empVO = null;
		SalgradeVO salgradeVO = null;

		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		int grade;
		int losal;
		int hisal;

		String sql = "SELECT * " + " FROM emp e JOIN salgrade s ON sal BETWEEN losal AND hisal "
				+ " ORDER BY grade, deptno, sal + NVL(comm, 0) DESC";

		try {
			conn = DBConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				map = new LinkedHashMap<>();
				do {
					grade = rs.getInt("grade");
					losal = rs.getInt("losal");
					hisal = rs.getInt("hisal");
					salgradeVO = new SalgradeVO(grade, losal, hisal);

					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getString("hiredate");
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					empVO = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);

					if (!map.containsKey(salgradeVO)) {
						list = new ArrayList<>();
						map.put(salgradeVO, list);
					}
					list.add(empVO);

//               if(map.containsKey(salgradeVO)) {
//                  list = map.get(salgradeVO);
//                  list.add(empVO);
//               } else {
//                  map.put(salgradeVO, new ArrayList<>());
//                  list = map.get(salgradeVO);
//                  list.add(empVO);
//               }
				} while (rs.next());
			}
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

		printEmpMap(map);
	}

	private static void printEmpMap(Map<SalgradeVO, ArrayList<EmpVO>> map) {
		SalgradeVO salgradeVO = null;
		ArrayList<EmpVO> list = null;
		EmpVO empVO = null;

		Set<Entry<SalgradeVO, ArrayList<EmpVO>>> set = map.entrySet();
		Iterator<Entry<SalgradeVO, ArrayList<EmpVO>>> ir = set.iterator();
		while (ir.hasNext()) {
			Entry<SalgradeVO, ArrayList<EmpVO>> entry = ir.next();
			salgradeVO = entry.getKey();
			list = entry.getValue();
			System.out.printf("%d등급 (%d~%d) - %d명\n", salgradeVO.getGrade(), salgradeVO.getLosal(),
					salgradeVO.getHisal(), list.size());
			Iterator<EmpVO> ir2 = list.iterator();
			while (ir2.hasNext()) {
				empVO = ir2.next();
				System.out.printf("  %d  %s  %d  %s  %.0f\n", empVO.getDeptno(), empVO.getJob(), empVO.getEmpno(),
						empVO.getEname(), empVO.getSal() + empVO.getComm());
			}
		}
	}

}