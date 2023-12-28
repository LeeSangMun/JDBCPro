package survey;

import java.sql.Connection;

import com.util.DBConn;

import survey.controller.SurveyController;
import survey.persistence.SurveyDAO;
import survey.persistence.SurveyDAOImpl;
import survey.persistence.SurveyOptionDAO;
import survey.persistence.SurveyOptionDAOImpl;
import survey.service.SurveyService;

public class EX {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		SurveyDAO surveyDao = new SurveyDAOImpl(conn);
		SurveyOptionDAO surveyOptionDAO = new SurveyOptionDAOImpl(conn);
		SurveyService service = new SurveyService(surveyDao, surveyOptionDAO);
		SurveyController controller = new SurveyController(service);
		
		controller.boardStart();
	}

}
