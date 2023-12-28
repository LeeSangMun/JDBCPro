package survey.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import survey.domain.SurveyDTO;
import survey.domain.VoteDTO;

public interface SurveyDAO {
	// 1. 설문 게시글 조회 - 페이징 + 서치
	ArrayList<SurveyDTO> select(int currentPage, int numberPerPage, int searchCondition, String searchWord) throws SQLException;
	
	// 2. 설문 작성
	int insert(SurveyDTO dto) throws SQLException;
	
	// 3. 설문 보기
	SurveyDTO view(int survey_seq) throws SQLException;
	
	// 4. 설문 수정
	int update(SurveyDTO dto) throws SQLException;
	
	// 5. 설문 삭제
	int delete(int survey_seq) throws SQLException;
	
	// 6. 설문 투표
	int insertVote(VoteDTO dto) throws SQLException;
	
	// 7. 설문 참여자 수 수정
	int updateSurveyCnt(int survey_seq) throws SQLException;
}
