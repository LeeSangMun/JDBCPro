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
 * @date 2023. 9. 20. - 오전 9:33:48
 * @subject
 * @content
 */
public class Ex02 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// 1. dept 부서정보 조회
		// 2. dept 부서추가
		int deptno;
		String dname;
		String loc;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("1. 부서번호 입력 ? ");
		deptno = Integer.parseInt(br.readLine());

		System.out.print("2. 부서명 입력 ? ");
		dname = br.readLine();
		
		System.out.print("3. 지역명 입력 ? ");
		loc = br.readLine();
		
		String sql = String.format("INSERT INTO dept(deptno, dname, loc) "
						+ " VALUES(%d, '%s', '%s')", deptno, dname, loc);
		
		Connection conn = null;
		Statement stmt = null;
		int rowCount = 0;
		
		try {
			conn = DBConn.getConnection();
//			conn.setAutoCommit(true); 기본값이 true라 자동커밋된다.
			stmt = conn.createStatement();
			
			rowCount = stmt.executeUpdate(sql);
			if(rowCount == 1) {
				System.out.println("> 부서 추가 성공!!!");
			}
			System.out.println(rowCount);
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
