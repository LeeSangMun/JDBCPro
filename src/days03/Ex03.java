package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.internal.OracleTypes;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오전 11:29:36
 * @subject	CallableStatement - 저장프로시저, 저장함수
 * @content	회원가입
 * 					아이디 : [kenik] [ID 중복체크 버튼]
 * 					예) 회원테이블	(emp)
 * 						회원ID		(empno)
 */
public class Ex03 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("> 중복체크할 ID(empno)를 입력 ? ");
		int pID = scanner.nextInt();
		
//		String sql = "{ call UP_IDCHECK(pid => ?, pcheck => ?) }";
		String sql = "{ call UP_IDCHECK(?, ?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int idCheck = 0;
		
		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pID);
//			cstmt.registerOutParameter(2, Types.INTEGER);
//			cstmt.registerOutParameter(2, Types.NUMERIC);
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			cstmt.executeUpdate();
			idCheck = cstmt.getInt(2);
			
			if(idCheck == 0) {
				System.out.println("사용 가능한 ID(empno)입니다.");
			} else {
				System.out.println("이미 사용 중인 ID(empno)입니다.");
			}
			
			System.out.println(idCheck);
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

}
