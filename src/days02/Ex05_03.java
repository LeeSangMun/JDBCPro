package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;


/**
 * @author sangmun
 * @date 2023. 9. 20. - 오후 4:42:41
 * @subject
 * @content
 */
public class Ex05_03 {

	public static void main(String[] args) throws IOException {
		// days02.Ex02_02.java -> pstmt 수정
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int deptno;
		String dname, loc;
		
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
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
		sql = "SELECT * FROM dept "
				+ " WHERE deptno = ?";
		
		// originalDname, originalLoc 변수
		String originalDname = null, originalLoc = null;
		conn = DBConn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			rs = pstmt.executeQuery();
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
			
			sql = "UPDATE dept "
					+ " SET dname = ?, loc = ? "
					+ " WHERE deptno = ?";
			pstmt2 = conn.prepareStatement(sql);
			
			// System.out.println(sql);
			pstmt2.setString(1, dname);
			pstmt2.setString(2, loc);
			pstmt2.setInt(3, deptno);
			
			rowCount = pstmt2.executeUpdate();
			
			if(rowCount == 1) System.out.println("> 부서 수정 완료!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				pstmt2.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		System.out.println("end");
	}

}
