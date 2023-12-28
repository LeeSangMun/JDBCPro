package days04.board.service;

import java.sql.SQLException;
import java.util.ArrayList;

import days04.board.domain.BoardDTO;
import days04.board.persistence.BoardDAO;
import days04.board.persistence.BoardDAOImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// 사용자 -> BoardController -> BoardService -> BoardDaoImpl -> DB
@Data
@AllArgsConstructor
@Builder
public class BoardService {
	private BoardDAO dao = null;
	
	// 1. 게시글 목록 조회 서비스
	public ArrayList<BoardDTO> selectService() {
		ArrayList<BoardDTO> list = null;
		
		// 1) 로그기록
		System.out.println("> 게시글 목록 조회 - 로그 기록 작업...");
		// 2) 
		try {
			list = this.dao.select();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 2. 게시글 쓰기 서비스
	public int insertService(BoardDTO dto) {
		int rowCount = 0;
		
		// 1) 로그기록
		System.out.println("> 게시글 쓰기 - 로그 기록 작업...");
		// 2)
		try {
			rowCount = this.dao.insert(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}

	// 3. 게시글 상세 보기 서비스
	// 		ㄱ. 해당 게시글의 조회수를 1증가
	//		ㄴ. 해당 게시글 정보를 조회gdgdd
	//		ㄱ + ㄴ 트랜잭션 처리.
	public BoardDTO viewService(int seq) {
		BoardDTO dto = null;
		
		try {
			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			
			this.dao.increaseReaded(seq);
			dto = this.dao.view(seq);
			
			// ㄷ. 로그기록
			System.out.println("> 게시글 상세 보기 -> 로그 기록 작업...");
			
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch(Exception e) {
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}
	
	// 4. 게시글 삭제 서비스
	public int deleteService(int seq) {
		int rowCount = 0;
		
		try {
			System.out.println("> 게시글 삭제 -> 로그 기록 작업...");
			rowCount = this.dao.delete(seq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}

	public int updateService(BoardDTO dto) {
		int rowCount = 0;
		
		try {
			System.out.println("> 게시글 수정 -> 로그 기록 작업...");
			rowCount = this.dao.update(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}

	public ArrayList<BoardDTO> searchService(int searchCondition, String searchWord) {
		ArrayList<BoardDTO> list = null;
		
		// 1) 로그기록
		System.out.println("> 게시글 목록 조회 - 로그 기록 작업...");
		// 2) 
		try {
			list = this.dao.search(searchCondition, searchWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<BoardDTO> searchService(int currentPage, int numberPerPage, int searchCondition,
			String searchWord) {
ArrayList<BoardDTO> list = null;
		
		// 1) 로그기록
		System.out.println("> 게시글 목록 조회 - 로그 기록 작업...");
		// 2) 
		try {
//			list = this.dao.search(searchCondition, searchWord);
			list = this.dao.search(currentPage, numberPerPage, searchCondition, searchWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<BoardDTO> selectService(int currentPage, int numberPerPage) {
		ArrayList<BoardDTO> list = null;
		
		// 1) 로그기록
		System.out.println("> 게시글 목록 조회 - 로그 기록 작업...");
		// 2) 
		try {
			list = this.dao.select(currentPage, numberPerPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public String pageService(int currentPage, int numberPerPage, int numberOfPageBlock) {
		String pagingBlock = "\t\t\t";
		
		int totalPages;
		
		try {
			totalPages = this.dao.getTotalPages(numberPerPage);
			int start = (currentPage - 1) / numberOfPageBlock * numberOfPageBlock + 1;
			int end = start + numberOfPageBlock - 1;
			end = end > totalPages ? totalPages : end;

			if (start != 1)
				pagingBlock += " < ";
			for (int j = start; j <= end; j++) {
				pagingBlock += String.format(currentPage == j ? "[%d] " : "%d ", j);
			}
			if (end != totalPages)
				pagingBlock += " > ";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pagingBlock;
	}

	public String pageService(int currentPage, int numberPerPage, int numberOfPageBlock, int searchCondition,
			String searchWord) {
String pagingBlock = "\t\t\t";
		
		int totalPages;
		
		try {
//			totalPages = this.dao.getTotalPages(numberPerPage);
			totalPages = this.dao.getTotalPages(numberPerPage, searchCondition, searchWord);
			
			int start = (currentPage - 1) / numberOfPageBlock * numberOfPageBlock + 1;
			int end = start + numberOfPageBlock - 1;
			end = end > totalPages ? totalPages : end;

			if (start != 1)
				pagingBlock += " < ";
			for (int j = start; j <= end; j++) {
				pagingBlock += String.format(currentPage == j ? "[%d] " : "%d ", j);
			}
			if (end != totalPages)
				pagingBlock += " > ";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pagingBlock;
	}

	
}
