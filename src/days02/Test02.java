package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오전 11:44:15
 * @subject	emp테이블에서 한 사원의 정보를 입력받아서 삽입
 * @content
 */
public class Test02 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		Connection conn = null;
		Statement stmt = null;
		int rowCount = 0;
		
		String empno;
		String ename;
		String job;
		String mgr;
		String hiredate;
		String sal;
		String comm;
		String deptno;
		
		System.out.print("> 사원번호를 입력 ? ");
		empno = br.readLine();
		System.out.print("> 사원이름을 입력 ? ");
		ename = br.readLine();
		System.out.print("> 직업을 입력 ? ");
		job = br.readLine();
		System.out.print("> 상사번호를 입력 ? ");
		mgr = br.readLine();
		System.out.print("> 입사날짜를 입력 ? ");
		hiredate = br.readLine();
		System.out.print("> sal를 입력 ? ");
		sal = br.readLine();
		System.out.print("> comm를 입력 ? ");
		comm = br.readLine();
		System.out.print("> 부서번호를 입력 ? ");
		deptno = br.readLine();
		
		String sql = String.format("INSERT INTO emp "
				+ " VALUES(%s, '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
							empno, ename, job, mgr, hiredate, sal, comm, deptno);
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);
			
			if(rowCount == 1) {
				System.out.println("사원 정보가 삽입 되었습니다!!!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end");
	}

}
