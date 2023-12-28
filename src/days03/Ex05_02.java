package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBConn;

import domain.DeptVO;
import oracle.jdbc.internal.OracleTypes;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오후 12:45:43
 * @subject dept - insert
 * @content
 */
public class Ex05_02 {

	public static void main(String[] args) {
		String sql = "{ call UP_INSDEPT(?, ?) }";

		Connection conn = null;
		CallableStatement cstmt = null;
		DeptVO vo = null;

		int deptno;
		String dname = "QC", loc = "SEOUL";
		int rowCount = 0;

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, dname);
			cstmt.setString(2, loc);
			rowCount = cstmt.executeUpdate();
			
			if(rowCount == 1) {
				System.out.println("> 부서 추가 완료!!!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end");
	}

	/*
	SELECT last_number - increment_by
	FROM user_sequences
	WHERE sequence_name LIKE 'SEQ_DEPT';
	*/
}
