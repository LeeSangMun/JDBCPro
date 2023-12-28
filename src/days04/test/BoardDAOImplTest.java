package days04.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAOImpl;

class BoardDAOImplTest {

	@Test
	void selectTest() {
		Connection conn = DBConn.getConnection();
		
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			ArrayList<BoardDTO> list = dao.select();
			
			if(list == null) {
				System.out.println("> 게시글은 존재 X");
				return;
			}
			
			Iterator<BoardDTO> ir = list.iterator();
			
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.println(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			DBConn.close();
		}
		
	}

	@Test
	void insertTest() {
		Connection conn = DBConn.getConnection();
		
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			BoardDTO dto = BoardDTO.builder()
										.writer("서영학")
										.pwd("1234")
										.email("se@naver.com")
										.title("두 번째 게시글")
										.tag(0)
										.content("두 번째 게시글 내용")
										.build();
			
			int rowCount = dao.insert(dto);
			
			if(rowCount == 1) {
				System.out.println("> 게시글은 작성 완료!!!");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			DBConn.close();
		}
		
	}
	
	@Test
	void totalRecordsTest() {
		Connection conn = DBConn.getConnection();
		
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			int totalRecords = dao.getTotalRecords();
			
			System.out.println("총 레코드수 : " + totalRecords);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			DBConn.close();
		}
		
	}
	
	@Test
	void totalPagesTest() {
		Connection conn = DBConn.getConnection();
		
		BoardDAOImpl dao = new BoardDAOImpl(conn);
		
		try {
			int totalPages = dao.getTotalPages(10);
			
			System.out.println("총 페이지 수 : " + totalPages);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			DBConn.close();
		}
		
	}
}
