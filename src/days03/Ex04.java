package days03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBConn;

import oracle.jdbc.internal.OracleTypes;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오후 12:10:13
 * @subject
 * @content
 */
public class Ex04 {

	public static void main(String[] args) {
		/*
	       * 1.  로그인 처리
	       *      아이디    : [    kenik    ]
	       *      비밀번호 : [    1234     ]
	       *      
	       *      [로그인]   [회원가입]
	       * 2.  up_logon
	       *     회원테이블 = 아이디(PK), 비밀번호 X
	       *     emp          = empno(PK), ename    
	       * 3.      로그인 성공 : 0
	       *          로그인 실패
	       *                 ㄴ 아이디 존재하지 않으면  : 1
	       *                 ㄴ 비밀번호 틀리면               : -1
	       * */
		
		Scanner scanner = new Scanner(System.in);

		System.out.print("> 로그인 할 ID(empno), PWD(ename)를 입력 ? ");
		int pID = scanner.nextInt();
		String pPWD = scanner.next();

		String sql = "{ call UP_LOGON(?, ?, ?) }";

		Connection conn = null;
		CallableStatement cstmt = null;
		int logonCheck = 0;

		try {
			conn = DBConn.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pID);
			cstmt.setString(2, pPWD);
			cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			cstmt.executeUpdate();
			logonCheck = cstmt.getInt(3);

			if (logonCheck == 0) {
				System.out.println("로그인 성공.");
			} else if(logonCheck == 1) {
				System.out.println("로그인 실패 - ID가 존재하지 않습니다.");
			} else {
				System.out.println("로그인 실패 - ID가 존재하지만 PWD가 잘못되었습니다.");
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

}
