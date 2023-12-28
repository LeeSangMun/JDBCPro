package survey.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import survey.domain.SurveyOptionDTO;

public interface SurveyOptionDAO {
	// 1. 항목 추가
	int insert(SurveyOptionDTO dto) throws SQLException;
	
	// 2. 항목 보기
	ArrayList<SurveyOptionDTO> view(int survey_seq) throws SQLException;
	
	// 3. 항목 삭제
	// 항목아이디가 아닌 설문아이디를 통해 전체 삭제
	int delete(int survey_seq) throws SQLException;
}
