package persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import domain.BoardDTO;

public interface BoardDAO {
	// 1. 게시글 목록 조회
	ArrayList<BoardDTO> select() throws SQLException;
	
	// 2. 게시글 쓰기
	int insert(BoardDTO dto) throws SQLException;
	
	
}
