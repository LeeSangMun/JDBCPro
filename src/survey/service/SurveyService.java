package survey.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import lombok.AllArgsConstructor;
import survey.domain.SurveyDTO;
import survey.domain.SurveyOptionDTO;
import survey.domain.VoteDTO;
import survey.persistence.SurveyDAO;
import survey.persistence.SurveyDAOImpl;
import survey.persistence.SurveyOptionDAO;

@AllArgsConstructor
public class SurveyService {
	private SurveyDAO surveyDao;
	private SurveyOptionDAO surveyOptionDao;
	
	public ArrayList<SurveyDTO> selectService(int currentPage, int numberPerPage, int searchCondition, String searchWord) {
		ArrayList<SurveyDTO> list = null;
		
//		System.out.println("> 설문 게시글 조회 로그...");
		try {
			list = surveyDao.select(currentPage, numberPerPage, searchCondition, searchWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 설문 레코드와 항목들을 한 트랜잭션에 담음
	public int insertService(SurveyDTO dto, ArrayList<SurveyOptionDTO> list) {
		int rowCount = 0;
		SurveyOptionDTO surveyOptionDTO = null;
		
		try {
			((SurveyDAOImpl)surveyDao).getConn().setAutoCommit(false);
			
			rowCount = surveyDao.insert(dto);
			System.out.println("> surveyDao.insert(dto) rowCount -> " + rowCount);
			
			Iterator<SurveyOptionDTO> ir = list.iterator();
			while (ir.hasNext()) {
				surveyOptionDTO = ir.next();
				rowCount = surveyOptionDao.insert(surveyOptionDTO);
				System.out.println("> surveyOptionDao.insert(surveyOptionDTO) rowCount -> " + rowCount);
			}
			
			((SurveyDAOImpl)surveyDao).getConn().commit();
			System.out.println("> 설문 게시글 등록 로그...");
		} catch (SQLException e) {
			try {
				((SurveyDAOImpl)surveyDao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return rowCount;
	}
	
	
	public Map viewService(int survey_seq) {
		Map map = new HashMap();
		
		try {
			SurveyDTO surveyDto = surveyDao.view(survey_seq);
			ArrayList<SurveyOptionDTO> list = surveyOptionDao.view(survey_seq);
			
			map.put("surveyDto", surveyDto);
			map.put("list", list);
			
			System.out.println("> 게시글 상세 보기 로그...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	// 해당 게시글 수정 -> 해당 게시글의 항목 삭제 -> 다시 수정된 항목 추가
	public int updateService(SurveyDTO dto, ArrayList<SurveyOptionDTO> list) {
		int rowCount = 0;
		SurveyOptionDTO surveyOptionDTO = null;
		
		try {
			((SurveyDAOImpl)surveyDao).getConn().setAutoCommit(false);
			
			rowCount = surveyDao.update(dto);
			System.out.println("> surveyDao.update(dto) rowCount -> " + rowCount);
			rowCount = surveyOptionDao.delete(dto.getSurvey_id());
			System.out.println("> surveyOptionDao.delete(dto.getSurvey_id()) rowCount -> " + rowCount);
			
			Iterator<SurveyOptionDTO> ir = list.iterator();
			while (ir.hasNext()) {
				surveyOptionDTO = ir.next();
				rowCount = surveyOptionDao.insert(surveyOptionDTO);
				System.out.println("> surveyOptionDao.insert(surveyOptionDTO) rowCount -> " + rowCount);
			}
			
			((SurveyDAOImpl)surveyDao).getConn().commit();
		} catch (SQLException e) {
			try {
				((SurveyDAOImpl)surveyDao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("> 게시글 수정 로그...");
		
		return rowCount;
	}
	
	// ON DELETE SET NULL 이므로 삭제도 해줘야 할 거 같다.
	// 항목 삭제 후 -> 설문 삭제
	public int deleteService(int survey_seq) {
		int rowCount = 0;
		
		try {
			((SurveyDAOImpl)surveyDao).getConn().setAutoCommit(false);
			
			rowCount = surveyOptionDao.delete(survey_seq);
			System.out.println("> surveyOptionDao.delete(survey_seq) rowCount -> " + rowCount);
			
			rowCount = surveyDao.delete(survey_seq);
			System.out.println("> surveyDao.delete(survey_seq) rowCount -> " + rowCount);
			
			((SurveyDAOImpl)surveyDao).getConn().commit();
		} catch (SQLException e) {
			try {
				((SurveyDAOImpl)surveyDao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("> 게시글 삭제 로그...");
		
		return rowCount;
	}
	
	// 투표 후 -> survey테이블 투표자수 업데이트
	public int insertVoteService(VoteDTO dto) {
		int rowCount = 0;
		
		try {
			((SurveyDAOImpl)surveyDao).getConn().setAutoCommit(false);
			
			rowCount = surveyDao.insertVote(dto);
			System.out.println("> surveyDao.insertVote(dto) rowCount -> " + rowCount);
			
			rowCount = surveyDao.updateSurveyCnt(dto.getSurvey_id());
			System.out.println("> surveyDao.updateSurveyCnt(dto.getSurvey_id()) rowCount -> " + rowCount);
			
			((SurveyDAOImpl)surveyDao).getConn().commit();
		} catch (SQLException e) {
			try {
				((SurveyDAOImpl)surveyDao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("> 투표 로그...");
		
		return rowCount;
	}
}
