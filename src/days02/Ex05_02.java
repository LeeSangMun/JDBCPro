package days02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.DeptVO;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오후 4:16:44
 * @subject	days02.Ex02_02.java -> PreparedStatement 수정
 * @content
 */
public class Ex05_02 {

	public static void main(String[] args) {
		// 부서 검색한 후
		// 부서 정보 수정 + 삭제
		// 1: deptno, 2: dname, 3: loc
		Scanner scanner = new Scanner(System.in);
		int searchCondition;
		String searchWord;
		Connection conn = null;
		// Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int deptno;
		String dname;
		String loc;
		
		DeptVO vo = null;
		ArrayList<DeptVO> list = null;
		
		System.out.print("> 검색조건을 입력 ? ");
		searchCondition = scanner.nextInt();
		System.out.print("> 검색어를 입력 ? ");
		searchWord = scanner.next();
		
		String sql = "SELECT * "
				+ " FROM dept "
				+ " WHERE ";
		
		if (searchCondition == 1) { // 부서번호
			sql += " deptno = ? ";
		} else if(searchCondition == 2) { // 부서명
//			sql += " REGEXP_LIKE (dname, ?, 'i')";
//			sql += " dname LIKE '%검색어%' ";
			sql += " dname LIKE ? ";
		} else if(searchCondition == 3) { // 지역명
			sql += " REGEXP_LIKE (loc, ?, 'i')";
		}
		
		System.out.println(sql);

		try {
			conn = DBConn.getConnection();
//			stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			
			if (searchCondition == 1) {
				pstmt.setInt(1, Integer.parseInt(searchWord));
			} else if(searchCondition == 2) {
				pstmt.setString(1, "%"+searchWord.toUpperCase()+"%");
				// pstmt.setNString(1, searchWord); // NVARCHAR2
			} else if(searchCondition == 2) {
				pstmt.setString(1, searchWord);
				// pstmt.setNString(1, searchWord); // NVARCHAR2
			}
			
//			pstmt.setInt(1, Integer.parseInt(searchWord));
			
//			rs = stmt.executeQuery(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					deptno = rs.getInt("deptno");
					dname = rs.getString("dname");
					if(searchCondition == 2) {
						searchWord = searchWord.toUpperCase();
						dname = dname.replaceAll(searchWord, "[" + searchWord + "]");
					}
					
					loc = rs.getString("loc");
					
					vo = new DeptVO(deptno, dname, loc);
					list.add(vo);
				} while(rs.next());
			}
			
			printDeptList(list);
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
	}

	private static void printDeptList(ArrayList<DeptVO> list) {
		if(list == null) {
			System.out.println("> 부서 존재하지 않는다.");
			return;
		} 
		
		System.out.printf("> 검색 결과 : %d개\n", list.size());
		Iterator<DeptVO> ir = list.iterator();
		while(ir.hasNext()) {
			DeptVO vo = ir.next();
			System.out.println(vo);
		}
	}

}