package survey.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import com.util.DBConn;

import days04.board.domain.BoardDTO;
import survey.domain.SurveyDTO;
import survey.domain.SurveyOptionDTO;
import survey.service.SurveyService;

public class SurveyController {
	private Scanner scanner;
	private int selectedNumber;
	private SurveyService service;
	
	public SurveyController() {
		this.scanner = new Scanner(System.in);
	}
	
	public SurveyController(SurveyService service) {
		this();
		this.service = service;
	}
	
	public void boardStart() {
		목록보기();
		일시정지();
		상세보기();
//		while (true) {
//			메뉴출력();
//			메뉴선택();
//			메뉴처리();
//		}
	}
	
	private void 메뉴처리() {
		switch (this.selectedNumber) {
		case 1: // 새글
//			새글쓰기();
			break;
		case 2: // 목록
			목록보기();
			break;
		case 3: // 보기
			상세보기();
			break;
		case 4: // 수정
//			수정하기();
			break;
		case 5: // 삭제
//			삭제하기();
			break;
		case 6: // 검색
//			검색하기();
			break;
		case 7: // 종료
			exit();
			break;
		}
	}
	
	private void 목록보기() {
		System.out.println("[설문목록]");
		System.out.println("─".repeat(55));
		System.out.printf("%s\t%-25s\t%s\t%20s\t%20s\t\t%s\t%s\t%s\n", "설문번호", "질문", "작성자", "시작일", "종료일", "항목수", "투표수", "상태");
		System.out.println("─".repeat(55));
		ArrayList<SurveyDTO> list = service.selectService(1, 10, 0, "1");
		
		if(list == null) {
			System.out.println("> 설문 게시글은 존재 X");
			return;
		}
		
		Iterator<SurveyDTO> ir = list.iterator();
		
		while (ir.hasNext()) {
			SurveyDTO dto = ir.next();
			String state = "진행 전";
			Date today = new Date(System.currentTimeMillis());
			if(today.before(dto.getEnd_date()) && today.after(dto.getStart_date())) {
				state = "진행중";
			} else if(today.after(dto.getEnd_date())) {
				state = "종료";
			}
			System.out.printf("%d\t%-25s\t%s\t%20s\t%20s\t%s\t%s\t%s\n",	dto.getSurvey_id(), dto.getTitle(), dto.getUser_id()
					, dto.getStart_date(), dto.getEnd_date(), dto.getOption_cnt(), dto.getSurvey_cnt(), state);
		}
		System.out.println("─".repeat(55));
	}

	private void 상세보기() {
		System.out.print("> 보고자 하는 설문 번호 입력 ? ");
		int survey_id = this.scanner.nextInt();
		
		SurveyOptionDTO surveyOptionDto = null;
		
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
	}
	
	private void 메뉴선택() {
		System.out.print("> 메뉴 선택하세요 ? ");
		this.selectedNumber = this.scanner.nextInt();
	}
	
	private void 메뉴출력() {
		String[] menus = { "새글", "목록", "보기", "수정", "삭제", "검색", "종료" };
		System.out.println("[ 메뉴 ]");
		for (int i = 0; i < menus.length; i++) {
			System.out.printf("%d. %s\t", i + 1, menus[i]);
		} // for
		System.out.println();
	}
	
	private void 일시정지() {
		System.out.println(" \t\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available()); // 13, 10
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exit() {
		DBConn.close();
		System.out.println("\t\t\t  프로그램 종료!!!");
		System.exit(-1);
	}
}
