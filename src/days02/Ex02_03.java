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

import domain.DeptVO;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오전 10:37:40
 * @subject
 * @content
 */
public class Ex02_03 {

	public static void main(String[] args) throws IOException {
		// 부서정보 수정
		// 1. 수정하기 전의 원래 값
		// 2. 부서번호 X, 부서명 + 지역명
		
		Scanner scanner = new Scanner(System.in);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int deptno;
		String dname, loc;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DeptVO vo = null;
		String sql = null;
		int rowCount = 0;
		
		System.out.print("> 수정할 부서번호를 입력 ? ");
		deptno = Integer.parseInt(br.readLine());
		System.out.print("> 수정할 부서명을 입력 ? ");
		dname = br.readLine();
		System.out.print("> 수정할 지역명을 입력 ? ");
		loc = br.readLine();
		
		// 1. 해당 부서번호의 수정 전 부서명, 지역명 저장
		sql = String.format("SELECT * FROM dept "
								+ " WHERE deptno = %d", deptno);
		
		// originalDname, originalLoc 변수
		String originalDname = null, originalLoc = null;
		conn = DBConn.getConnection();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				originalDname = rs.getString("dname");
				originalLoc = rs.getString("loc");
				System.out.println(originalDname);
			} else {
				System.out.println("부서가 존재 X.");
				return;
			}
			
			// ================UPDATE===============
			if(dname.isEmpty()) dname = originalDname;
			if(loc.isEmpty()) loc = originalLoc;
			
			sql = String.format("UPDATE dept "
					+ " SET dname = '%s', loc = '%s' "
					+ " WHERE deptno = %d", dname, loc, deptno);
			
			// System.out.println(sql);
			
			rowCount = stmt.executeUpdate(sql);
			
			if(rowCount == 1) System.out.println("> 부서 수정 완료!!!");
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
