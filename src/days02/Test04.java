package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오전 11:44:21
 * @subject	emp 테이블에서
 * @content	사원의 정보를 수정
 * 					수정할 사원번호. 기타 사원정보를 입력 수정.
 */
public class Test04 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int rowCount = 0;
		
		String empno;
		String ename;
		String job;
		String mgr;
		String hiredate;
		String sal;
		String comm;
		String deptno;
		
		System.out.print("> 수정할 사원번호를 입력 ? ");
		empno = br.readLine();
		System.out.print("> 수정할 사원이름을 입력 ? ");
		ename = br.readLine();
		System.out.print("> 수정할 직업을 입력 ? ");
		job = br.readLine();
		System.out.print("> 수정할 상사번호를 입력 ? ");
		mgr = br.readLine();
		System.out.print("> 수정할 입사날짜를 입력 ? ");
		hiredate = br.readLine();
		System.out.print("> 수정할 sal를 입력 ? ");
		sal = br.readLine();
		System.out.print("> 수정할 comm를 입력 ? ");
		comm = br.readLine();
		System.out.print("> 수정할 부서번호를 입력 ? ");
		deptno = br.readLine();
		
		String sql = String.format("SELECT * FROM emp WHERE empno = %s", empno);
		
		String originEname = null
				, originJob = null
				, originMgr = null
				, originHiredate = null
				, originSal = null
				, originComm = null
				, originDeptno = null;
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				originEname = rs.getString("ename");
				originJob = rs.getString("job");
				originMgr = rs.getString("mgr");
				originHiredate = rs.getString("hiredate");
				originSal = rs.getString("sal");
				originComm = rs.getString("comm");
				originDeptno = rs.getString("deptno");
				System.out.println(originEname);
			} else {
				System.out.println("사원 존재 X");
			}
			
			if(ename.isEmpty()) ename = originEname;
			if(job.isEmpty()) job = originJob;
			if(mgr.isEmpty()) mgr = originMgr;
			if(hiredate.isEmpty()) hiredate = originHiredate;
			if(sal.isEmpty()) sal = originSal;
			if(comm.isEmpty()) comm = originComm;
			if(deptno.isEmpty()) deptno = originDeptno;
			
			sql = String.format("UPDATE emp "
					+ " SET ename = '%s', job = '%s', mgr = %s"
					+ ", hiredate = '%s', sal = %s, comm = %s, deptno = %s"
					+ " WHERE empno = %s", ename, job, mgr, hiredate, sal, comm, deptno, empno);
			
			System.out.println(sql);
			
			rowCount = stmt.executeUpdate(sql);
			if(rowCount == 1) {
				System.out.println("사원 수정 성공!!!");
			}
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

}
