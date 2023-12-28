package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.util.DBConn;

import domain.DeptEmpSalgradeVO;
import domain.SalgradeVO2;

public class Ex01_02 {
	public static void main(String[] args) {
		String sql = "SELECT grade, losal, hisal, count(*) cnt "
				+ " FROM salgrade s JOIN emp e ON e.sal BETWEEN losal AND hisal " + " GROUP BY grade, losal, hisal "
				+ " ORDER BY grade ";

		String empSql = "SELECT d.deptno, dname, empno, ename, sal, grade "
				+ " FROM dept d RIGHT JOIN emp e ON d.deptno = e. deptno "
				+ " JOIN salgrade s ON e.sal BETWEEN losal AND hisal " + " WHERE grade = ?";

		Connection conn = null;
		ResultSet rs = null, empRs = null;
		PreparedStatement pstmt = null, empPstmt = null;
		LinkedHashMap<SalgradeVO2, ArrayList<DeptEmpSalgradeVO>> map = new LinkedHashMap<>();
		SalgradeVO2 vo = null;
		ArrayList<DeptEmpSalgradeVO> empList = null;
		DeptEmpSalgradeVO empVo = null;
		
		int deptno;
		String dname;
		int empno;
		String ename;
		double sal;
		int grade;

		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt가 실행할 쿼리에 (? 바인딩변수)인 파라미터 확인설정
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				do {
					grade = rs.getInt(1);
					vo = new SalgradeVO2(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));

					
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, grade);
					empRs = empPstmt.executeQuery();
					if(empRs.next()) {
						empList = new ArrayList<>();
						do {
							deptno = empRs.getInt(1);
							dname = empRs.getString(2);
							empno = empRs.getInt(3);
							ename = empRs.getString(4);
							sal = empRs.getDouble(5);
							empVo = new DeptEmpSalgradeVO(deptno, dname, empno, ename, sal, grade);
							empList.add(empVo);
						} while (empRs.next());
					}
					map.put(vo, empList);
					
					empRs.close();
					empPstmt.close();
				} while (rs.next());

				printSalgrade(map);
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // rs를 먼저 닫아야한다.
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try_catch
		} // finally

		DBConn.close();
		System.out.println("end");

	} // main

	private static void printSalgrade(LinkedHashMap<SalgradeVO2, ArrayList<DeptEmpSalgradeVO>> map) {
		Set<Entry<SalgradeVO2, ArrayList<DeptEmpSalgradeVO>>> set = map.entrySet();
		Iterator<Entry<SalgradeVO2, ArrayList<DeptEmpSalgradeVO>>> ir = set.iterator();
		while (ir.hasNext()) {
			Entry<SalgradeVO2, ArrayList<DeptEmpSalgradeVO>> entry = ir.next();
			SalgradeVO2 vo = entry.getKey();
			ArrayList<DeptEmpSalgradeVO> list = entry.getValue();
			System.out.printf("%d 등급   (%d~%d)-%d명\n", vo.getGrade(), vo.getLosal(), vo.getHisal(), vo.getCnt());
			Iterator<DeptEmpSalgradeVO> empIr = list.iterator();
			while (empIr.hasNext()) {
				DeptEmpSalgradeVO empVo = empIr.next();
				System.out.printf("\t%d\t%s\t%d\t%s\t%.2f\n"
						, empVo.getDeptno(), empVo.getDname(), empVo.getEmpno()
						, empVo.getEname()
						, empVo.getSal());
			}
		}
	}

	private static void printSalgrade(ArrayList<SalgradeVO2> list) {

		Iterator<SalgradeVO2> it = list.iterator();
		while (it.hasNext()) {
			SalgradeVO2 vo = it.next();
			System.out.printf("%d 등급   (%d~%d)-%d명\n", vo.getGrade(), vo.getLosal(), vo.getHisal(), vo.getCnt());
		} // while
	} // printSalgrade
}
//SalgradeVO2 vo = SalgradeVO2.builder().grade(1).losal(0).hisal(0).cnt(2).build();