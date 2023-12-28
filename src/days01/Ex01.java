package days01;

public class Ex01 {

	public static void main(String[] args) {
		// Java <- JDBC 드라이버 -> Oracle
		// JDBC(Java DataBase Connectivity)
		// 자바 표준 인터페이스
		// 자바의 DB에 연결 및 CRUD 작업을 하기 위한 표준 인터페이스
		// 오라클사 + JDBC => 구현한 클래스 == JDBC 드라이버
		// MS사 + JDBC => 구현한 클래스 == JDBC 드라이버
		// 11g XE       ojdbc6.jar + JDK 1.6 이상
		// JDBC 드라이버 종류
		// 1) Type1 : ODBC 
		// 2) Type2 : C, C++ 언어로 만든 라이브러리를 사용해서 DB연동
		// 3) Type3 : 미들웨어 서버
		// 4) Type4 : (Thin 드라이버) 순수 자바, 가장 많이 사용
		
		// [DBMS와 연결 방법]
		// 1. JDBC 드라이버 로딩 - Class.forName()
		// 2. Connection 얻어오는 작업 - DriverManager  getConnection()
		// 3. 작업자(Statement) CRUD 작업
		//		ㄴ Statement 
		//		ㄴ PreParedStatement : 성능 빠르다
		//		ㄴ CallableStatement : 저장 프로시저, 저장 함수
		// 4. DB연결종료 Connection 객체 Close
		
	}

}
