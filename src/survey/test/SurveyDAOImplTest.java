package survey.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import survey.domain.SurveyDTO;
import survey.persistence.SurveyDAO;
import survey.persistence.SurveyDAOImpl;

class SurveyDAOImplTest {

	@Test
	void select() {
		Connection conn = null;
		conn = DBConn.getConnection();
		SurveyDAO dao = new SurveyDAOImpl(conn);
		ArrayList<SurveyDTO> list = null;
		try {
//			list = dao.select(1, 10, 1, "t");
			list = dao.select(1, 10, 0, "t");
			
			if(list == null) {
				System.out.println("> 설문조사 존재 X");
				return;
			}
			
			Iterator<SurveyDTO> ir = list.iterator();
			while (ir.hasNext()) {
				SurveyDTO dto = ir.next();
				System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close();
		}
	}
	
}
