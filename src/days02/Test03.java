package days02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.EmpVO;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오전 11:44:18
 * @subject	emp 테이블에서 
 * @content	사원명, 부서번호, 잡(job) 검색
 * 					LIKE			 LIKE
 * 					검색된 사원정보를 조회
 */
public class Test03 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int searchCondition;
		String searchKeyword;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EmpVO> list = null;
		EmpVO vo = null;
		
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;
		
		System.out.print("> 검색 조건을 입력하세요 ? ");
		searchCondition = scanner.nextInt();
		
		System.out.print("> 검색 키워드를 입력하세요 ? ");
		searchKeyword = scanner.next();
		
		String sql = "SELECT * FROM emp"
						+ " WHERE ";
		
		if (searchCondition == 1) { // 사원명
			sql += String.format(" REGEXP_LIKE(ename, '%s', 'i')", searchKeyword);
		} else if(searchCondition == 2) { // 부서명
			sql += String.format(" deptno = %s", searchKeyword);
		} else if(searchCondition == 3) { // 잡
			sql += String.format(" REGEXP_LIKE(job, '%s', 'i')", searchKeyword);
		}
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getString("hiredate");
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
					list.add(vo);
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
		
		System.out.println("end");
	}

	private static void printEmpList(ArrayList<EmpVO> list) {
		if(list == null) {
			System.out.println("> 검색된 사원이 없음");
			return;
		}
		
		int count = list.size();
		
		System.out.printf("> 사원 수 : %d\n", count);
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
		
	}

}
