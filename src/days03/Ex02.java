package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오전 10:44:15
 * @subject
 * @content
 */
public class Ex02 {

	public static void main(String[] args) {
		// [자바 트랜잭션 처리]
		// 하나의 논리적인 작업 단위
		// 전부 완료 또는 전부 롤백
		// 예) 계좌 이체
		//		A 통장 돈을 인출 UPDATE : dept 50 부서 추가
		//		B 통장 돈을 입금 UPDATE : dept 50 부서 추가
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		String sql = "INSERT INTO dept VALUES(?, ?, ?)";
		
		
		try {
			conn = DBConn.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC1");
			pstmt.setString(3, "SEOUL");
			rowCount =  pstmt.executeUpdate();
			
			if(rowCount == 1) System.out.println("1번 부서 추가 성공");
			
			pstmt.setInt(1, 50);
			pstmt.setString(2, "QC2");
			pstmt.setString(3, "SEOUL");
			rowCount =  pstmt.executeUpdate();
			if(rowCount == 2) System.out.println("2번 부서 추가 성공");
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
