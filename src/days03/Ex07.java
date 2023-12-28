package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오후 2:40:39
 * @subject 리플렉션(reflection)
 * @content ㄴ JDBC 리플렉션 ? 결과물(rs)에 대한 정보를 추출해서 사용할 수 있는 기술
 */
public class Ex07 {

	public static void main(String[] args) {
		String sql = "SELECT table_name "
						+ " FROM tabs";
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ArrayList<String> tnList = null;
		String tableName = null;

		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt가 실행할 쿼리에 (? 바인딩변수)인 파라미터 확인설정
			rs = pstmt.executeQuery();
			if(rs.next()) {
				tnList = new ArrayList<>();
				do {
					tableName = rs.getString(1);
					tnList.add(tableName);
				} while (rs.next());
				printTableName(tnList);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // rs를 먼저 닫아야한다.
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try_catch
		} // finally

		// [2]
		Scanner scanner = new Scanner(System.in);
		System.out.print("> 테이블명을 입력 ? ");
		tableName = scanner.next();
		
		sql = String.format("SELECT * FROM %s", tableName);
		
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt가 실행할 쿼리에 (? 바인딩변수)인 파라미터 확인설정
			rs = pstmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
//			System.out.println(rsmd.getColumnCount());
			
			int columnCount = rsmd.getColumnCount();
			/*
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				int ColumnType = rsmd.getColumnType(i);
				String columnTypeName = rsmd.getColumnTypeName(i);
				int p = rsmd.getPrecision(i);
				int s = rsmd.getScale(i);
				System.out.println(columnName + "/" + ColumnType + "/" + columnTypeName
						+ "(" + p + "," + s + ")");
			} // for
			*/
			
			System.out.println("-".repeat(7*columnCount));
			
			for (int i = 1; i <= columnCount; i++) {
				System.out.printf("%s\t", rsmd.getColumnName(i));
			} // for
			System.out.println();
			System.out.println("-".repeat(7*columnCount));
			if(rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						int columnType = rsmd.getColumnType(i);
						int p = rsmd.getPrecision(i);
						int s = rsmd.getScale(i);
						if(columnType == 2 && s == 0) {
							System.out.printf("%d / ", rs.getInt(i));
						} else if(columnType == 2) {
							System.out.printf("%.2f / ", rs.getDouble(i));
						} else if(columnType == 12 ) {
							System.out.printf("%s / ", rs.getString(i));
						} else if(columnType == 93) {
							System.out.printf("%tF / ", rs.getDate(i));
						}
					} // for
					System.out.println();
				} while (rs.next());
			} else {
				System.out.println("레코드가 존재 X");
			} //Types
			System.out.println();
			System.out.println("-".repeat(7*columnCount));
			
			/*
			if(rs.next()) {
				do {
					
				} while (rs.next());
				printTableName(tnList);
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // rs를 먼저 닫아야한다.
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try_catch
		} // finally
		
		System.out.println("end");
	}

	private static void printTableName(ArrayList<String> tnList) {
		System.out.println("[Scott의 테이블 목록]");
		Iterator<String> ir = tnList.iterator();
		int count = 1;
		while (ir.hasNext()) {
			String tableName = ir.next();
			System.out.printf("%d. %s\t", count, tableName);
			if(count++%5==0) System.out.println();
		}
		System.out.println();
	}

}
