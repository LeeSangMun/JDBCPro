package survey.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import survey.domain.SurveyDTO;
import survey.domain.SurveyOptionDTO;
import survey.domain.VoteDTO;
import survey.persistence.SurveyDAO;
import survey.persistence.SurveyDAOImpl;
import survey.persistence.SurveyOptionDAO;
import survey.persistence.SurveyOptionDAOImpl;
import survey.service.SurveyService;

class SurveyServiceTest {

	@Test
	void selectServiceTest() {
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);
		
		ArrayList<SurveyDTO> list = service.selectService(1, 10, 0, "1");
		
		if(list == null) {
			System.out.println("> 설문 게시글은 존재 X");
			return;
		}
		
		Iterator<SurveyDTO> ir = list.iterator();
		
		while (ir.hasNext()) {
			SurveyDTO dto = ir.next();
			System.out.println(dto);
		}
		
	}
	
	@Test
	void insertServiceTest() {
		ArrayList<SurveyOptionDTO> list = new ArrayList<>();
		
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);

		SurveyDTO surveyDto = SurveyDTO.builder()
							.user_id("관리자")
							.title("좋아하는 과목?")
							.option_cnt(4)
							.start_date(Date.valueOf("2023-8-21"))
							.end_date(Date.valueOf("2023-8-30"))
							.build();
		
		int currVal = 0; // 현재 시퀀스 번호를 저장 할 변수
		try {
			currVal = ((SurveyDAOImpl)surveyDao).getSeqCurrVal();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SurveyOptionDTO surveyOptionDto = SurveyOptionDTO.builder()
				.survey_id(currVal)
				.option_content("C")
				.build();
		list.add(surveyOptionDto);
		
		surveyOptionDto = SurveyOptionDTO.builder()
				.survey_id(currVal)
				.option_content("C++")
				.build();
		list.add(surveyOptionDto);
		
		surveyOptionDto = SurveyOptionDTO.builder()
				.survey_id(currVal)
				.option_content("자바")
				.build();
		list.add(surveyOptionDto);
		
		surveyOptionDto = SurveyOptionDTO.builder()
				.survey_id(currVal)
				.option_content("파이썬")
				.build();
		list.add(surveyOptionDto);
		
		service.insertService(surveyDto, list);
		
		System.out.println("설문조사 작성 완료!!!");
	}
	
	// 나중에 시작일이 작성일 후에 있어야 할 거 같다.
	@Test
	void viewServiceTest() {
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);
		
		SurveyOptionDTO surveyOptionDto = null;
		int survey_id = 1;
		
		Map map = service.viewService(survey_id);
		SurveyDTO surveyDto = (SurveyDTO)map.get("surveyDto");
		ArrayList<SurveyOptionDTO> list = (ArrayList<SurveyOptionDTO>)map.get("list");
		
		if(surveyDto == null || list == null) {
			System.out.printf("> %d번 설문 없음\n", survey_id);
			return;
		}
		
		// 상태 설정
		String state = "진행 전";
		Date today = new Date(System.currentTimeMillis());
		if(today.before(surveyDto.getEnd_date()) && today.after(surveyDto.getStart_date())) {
			state = "진행중";
		} else if(today.after(surveyDto.getEnd_date())) {
			state = "종료";
		}
		
		System.out.println();
		System.out.println("[상세보기]");
		System.out.printf("|질문|\t%s\n", surveyDto.getTitle());
		// sql.Date는 시간이 안나오는거 같다.
		System.out.printf("|작성자|\t%s\t\t\t|작성일|\t%s\n", surveyDto.getUser_id(), surveyDto.getRegdate().toLocaleString());
		System.out.printf("|시작일|\t%s\t\t|종료일|\t%s\n", surveyDto.getStart_date(), surveyDto.getEnd_date());
		System.out.printf("|상태|\t%s\t\t\t|항목수|\t%d\n", state, surveyDto.getOption_cnt());
		
		System.out.print("|항목|");
		Iterator<SurveyOptionDTO> ir = list.iterator();
		while (ir.hasNext()) {
			surveyOptionDto = ir.next();
			System.out.printf("\t🔘 %s\n", surveyOptionDto.getOption_content());
		}
		
		System.out.printf("총 참여자 수 : %d명\n", surveyDto.getSurvey_cnt());
	}

	@Test
	void updateServiceTest() {
		ArrayList<SurveyOptionDTO> list = new ArrayList<>();
		
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);
		
		int survey_seq = 22;
		
		SurveyDTO surveyDto = SurveyDTO.builder()
				.survey_id(survey_seq)
				.title("가장 좋아하는 연예인은??")
				.option_cnt(2)
				.start_date(Date.valueOf("2023-09-21"))
				.end_date(Date.valueOf("2023-09-27"))
				.build();
		
		SurveyOptionDTO surveyOptionDto = SurveyOptionDTO.builder()
				.survey_id(survey_seq)
				.option_content("한효주")
				.build();
		list.add(surveyOptionDto);
		
		surveyOptionDto = SurveyOptionDTO.builder()
				.survey_id(survey_seq)
				.option_content("김선아")
				.build();
		list.add(surveyOptionDto);
		
		
		service.updateService(surveyDto, list);
	}
	
	@Test
	void deleteServiceTest() {
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);
		
		int survey_id = 22;
		
		service.deleteService(survey_id);
	}
	
	@Test
	void insertVoteServiceTest() {
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);
		
		VoteDTO dto = null;
		String user_id = "유저1";
		int survey_id = 3;
		int option_id = 8;
		
		dto = VoteDTO.builder()
				.user_id(user_id)
				.survey_id(survey_id)
				.option_id(option_id)
				.build();
		
		int rowCount = service.insertVoteService(dto);
		
		if(rowCount == 1) {
			System.out.println("> 투표 완료");
		}
	}
}
