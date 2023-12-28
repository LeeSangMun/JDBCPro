package days04;

import java.sql.Connection;

import com.util.DBConn;

import days04.board.controller.BoardController;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import days04.board.service.BoardService;

/**
 * @author sangmun
 * @date 2023. 9. 21. - 오후 3:39:18
 * @subject 게시판
 * @content
 * 		모델 1방식
 * 			글쓰기, 목록, 수정, 삭제 -> 로직 처리
 * 		모델 2방식(MVC패턴)
 * 			글쓰기 컨트롤러 -> 글쓰기 서비스 -> DAO -> DB처리
 * 							 <-				 <-
 * 			주문(A) + 요리(B) + 서빙(C)
 * 			Model 로직처리 객체
 * 			View 출력 객체
 * 			Controller 모든 요청/처리 담당 객체
 */
public class Ex01 {

	public static void main(String[] args) {
		/*
		CREATE TABLE tbl_cstVSBoard (
				  seq NUMBER NOT NULL PRIMARY KEY,
				  writer VARCHAR2 (20) NOT NULL ,
				  pwd VARCHAR2 (20) NOT NULL ,
				  email VARCHAR2 (100),
				  title VARCHAR2 (200) NOT NULL ,
				  writedate DATE DEFAULT SYSDATE,
				  readed NUMBER DEFAULT (0),
				  tag NUMBER(1) DEFAULT(0) ,
				  content CLOB
				);
		*/
		
		// 1. 테이블, 시퀀스 생성
		
		// 2. domain.BoardDTO.java
		
		// 3. 	persistence.BoardDAO.java
		//		persistence.BoardDAOImpl.java
		
		// 4. 단위 테스트 select(), insert()
		//		days04.test.BoardDAOImplTest.java
		
		// 5. 서비스 - 트랜잭션 처리
		
		// 6. 단위테스트 BoardServiceTest.java
		
		// 7. BoardController.java
		
		
		// 8. days04.Ex01.java 테스트
		Connection conn = DBConn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);
		
		controller.boardStart();
		
		// 9. 목록, 새글쓰기, 상세보기
		
		// 10. 삭제
		
		// 11. 수정
		
		// 12. 검색
		
		// 13. 페이징처리
		// 	한페이지에 몇 개의 게시글 출력 ? numberPerPage = 10;
		//	현재 페이지 번호 ?  int currentPage = 1;
		// 	페이징블럭 수 ? int numberOfPageBlock = 10;
		
		//		1) 현재 페이지 번호 빌드			currentPage
		//		2) 페이지 당 출력 게시글 수 필드	numberPerPage
		//		3) 페이지 블럭 수 필드			
		//		4) 총 레코드 수	BoardDAOImpl.java getTotalRecords()
		//		5) 총 페이지 수	BoardDAOImpl.java getTotalPages()
		
		// 		6) 쿼리 확인
		/*
		SELECT *
		FROM(
		    SELECT ROWNUM no, t.*
		    FROM (
		        SELECT seq, writer, email, title, readed, writedate
		        FROM tbl_cstvsboard
		        ORDER BY seq DESC
		    ) t
		) b
		WHERE b.no BETWEEN ? AND ?
		*/
		
		// 6-2)
		/*
		int begin = (currentPage-1)*numberPerPage + 1;
		int end = begin + numberPerPage - 1;
		*/
		
		// 7) BoardController
		//			service.selectService(currentPage, numberPerPage)
		//			출력
		
		// 8) service.selectService()
		//		ArrayList<BoardDTO> list = dao.select(currentPage, numberPerPage)
		
		
		/*
		 * 1) sequence 생성 - seq_테이블명
		 * 2) tbl_cstvsboard 테이블 생성
		 * 3) BoardDTO
		 * 4) BoardDAO, BoardDAOImpl -> 단위 테스트
		 * 		insert(), select() 등등
		 * 5) BoardService -> 단위테스트
		 * 		insertService(), selectSerice() 등등
		 * 6) BoardController
		 * 				while(true) {
		 * 					메뉴출력();
		 * 					메뉴선택();
		 * 					메뉴처리();
		 * 				}
		 * 
		 * 7) Ex01.main() 
		 * 				controller.boardStart(); 
		 * 
		 * 8) search()
		 * 		페이징 처리
		 * 			1) 현재 페이지번호
		 * 			2) 한 페이지당 출력할 게시글 수
		 * 			3) 페이징 블럭 수
		 * 			4) 총 페이지수, 총 레코드수
		 * 
		 * 9) 검색하기() + 페이징 처리
		 */
	}

}
