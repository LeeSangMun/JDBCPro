package days01;

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
 * @date 2023. 9. 19. - 오후 5:09:50
 * @subject
 * @content
 */
public class Test01 {

	public static void main(String[] args) {
		// EmpVO 클래스 선언
		// emp 테이블의 모든 사원 정보를 출력하는 코딩.
		// 조건) 부서번호를 입력받아서
		// 해당 부서원들만 출력하는 코딩.
		// 조건) printEmpList(ArrayList<EmpVO> list) 출력
		
		Connection conn = DBConn.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EmpVO> list = null;
		EmpVO empVO = null;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("> 부서 입력 ? ");
		int inputDeptno = sc.nextInt();
		
		String sql = "SELECT * ";
		sql += " FROM emp ";
		sql += " WHERE deptno = " + inputDeptno;
		
		int empno = 0;
		String ename = "";
		String job = "";
		int mgr = 0;
		String hiredate = null;
		int sal = 0;
		int comm = 0;
		int deptno = 0;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt(1);
					ename = rs.getString(2);
					job = rs.getString(3);
					mgr = rs.getInt(4);
					hiredate = rs.getString(5);
					sal = rs.getInt(6);
					comm = rs.getInt(7);
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
		if(list == null) {
			System.out.println("> 부서에 사원이 존재하지 않는다.");
			return;
		} 
		
		Iterator<EmpVO> ir = list.iterator();
		while(ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
	}

}
