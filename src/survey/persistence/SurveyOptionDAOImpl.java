package survey.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import survey.domain.SurveyOptionDTO;

@AllArgsConstructor
public class SurveyOptionDAOImpl implements SurveyOptionDAO {
	Connection conn = null;

	@Override
	public int insert(SurveyOptionDTO dto) throws SQLException {
		int rowCount = 0;
		
		String sql = "INSERT INTO survey_option "
				+ " VALUES(op_seq.NEXTVAL, ?, ?) ";
		
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, dto.getSurvey_id());
		pstmt.setString(2, dto.getOption_content());
		rowCount = pstmt.executeUpdate();
		
		pstmt.close();
		
		return rowCount;
	}

	// 해당 설문에 항목 가져오기
	@Override
	public ArrayList<SurveyOptionDTO> view(int survey_seq) throws SQLException {
		ArrayList<SurveyOptionDTO> list = null;
		
		String sql = "SELECT op_seq, option_content "
				+ " FROM survey_option "
				+ " WHERE survey_id = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SurveyOptionDTO dto = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, survey_seq);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			list = new ArrayList<>();
			do {
				dto = SurveyOptionDTO.builder()
						.op_seq(rs.getInt("op_seq"))
						.option_content(rs.getString("option_content"))
						.build();
				
				list.add(dto);
			} while (rs.next());
		}
		
		return list;
	}

	@Override
	public int delete(int survey_seq) throws SQLException {
		int rowCount = 0;
		
		String sql = "DELETE FROM survey_option "
				+ " WHERE survey_id = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, survey_seq);
		rowCount = pstmt.executeUpdate();
		
		pstmt.close();
		
		return rowCount;
	}
	
}
