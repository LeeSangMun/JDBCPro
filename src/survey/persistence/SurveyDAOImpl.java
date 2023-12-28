package survey.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import survey.domain.SurveyDTO;
import survey.domain.VoteDTO;

@AllArgsConstructor
@Getter
public class SurveyDAOImpl implements SurveyDAO {
	private Connection conn = null;
	
	@Override
	public ArrayList<SurveyDTO> select(int currentPage, int numberPerPage, int searchCondition, String searchWord)
			throws SQLException {
		ArrayList<SurveyDTO> list = null;
		int begin = (currentPage - 1) * numberPerPage + 1;
		int end = begin + numberPerPage - 1;

		String sql = "SELECT * " + "FROM( " + "    SELECT ROWNUM no, t.* " + "    FROM ( "
				+ "        SELECT survey_id, title, user_id, start_date, end_date, option_cnt, survey_cnt "
				+ "        FROM survey ";

		if (searchCondition == 1) { // 제목
			sql += " WHERE REGEXP_LIKE(title, ?, 'i') ";
		}

		sql += "        ORDER BY survey_id DESC " + "    ) t " + ") b " + "WHERE b.no BETWEEN ? AND ? ";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SurveyDTO dto = null;

		pstmt = conn.prepareStatement(sql);
		if(searchCondition == 1) { // 제목
			pstmt.setString(1, searchWord);
			pstmt.setInt(2, begin);
			pstmt.setInt(3, end);
		} else { // 검색조건이 없을 때
			pstmt.setInt(1, begin);
			pstmt.setInt(2, end);
		}
		
		rs = pstmt.executeQuery();

		int survey_id;
		String user_id;
		String title;
		int survey_cnt;
		int option_cnt;
		Date start_date;
		Date end_date;
		
		if (rs.next()) {
			list = new ArrayList<>();
			do {
				survey_id = rs.getInt("survey_id");
				user_id = rs.getString("user_id");
				title = rs.getString("title");
				survey_cnt = rs.getInt("survey_cnt");
				option_cnt = rs.getInt("option_cnt");
				start_date = rs.getDate("start_date");
				end_date = rs.getDate("end_date");

				dto = SurveyDTO.builder()
						.survey_id(survey_id)
						.user_id(user_id)
						.title(title)
						.survey_cnt(survey_cnt)
						.option_cnt(option_cnt)
						.start_date(start_date)
						.end_date(end_date)
						.build();
				list.add(dto);
			} while (rs.next());
		}

		rs.close();
		pstmt.close();

		return list;
	}

	@Override
	public int insert(SurveyDTO dto) throws SQLException {
		int rowCount = 0;
		
		String sql = "INSERT INTO survey(survey_id, user_id, title, option_cnt, start_date, end_date) "
				+ " VALUES(survey_seq.nextval, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getUser_id());
		pstmt.setString(2, dto.getTitle());
		pstmt.setInt(3, dto.getOption_cnt());
		pstmt.setDate(4, dto.getStart_date());
		pstmt.setDate(5, dto.getEnd_date());
		rowCount = pstmt.executeUpdate();
		
		
		pstmt.close();
		
		return rowCount;
	}

	// survey의 시퀀스 값 얻어오는 함수
	// 인터페이스에는 정의 X
	// 세션이 달라서 CURR_VAR는 계속 오류남
	// 시퀀스 만들 때 NOCACHE 해주고 last_number 얻어오기
	public int getSeqCurrVal() throws SQLException {
		int currVal = 0;
		
		String sql = "SELECT last_number "
				+ " FROM user_sequences t "
				+ " WHERE sequence_name = 'SURVEY_SEQ'";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			currVal = rs.getInt(1);
		}
		
		return currVal;
	}

	@Override
	public SurveyDTO view(int survey_seq) throws SQLException {
		SurveyDTO dto = null;
		
		String sql = "SELECT * "
				+ " FROM survey "
				+ " WHERE survey_id = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, survey_seq);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			dto = SurveyDTO.builder()
					.title(rs.getString("title"))
					.user_id(rs.getString("user_id"))
					.regdate(rs.getDate("regdate"))
					.start_date(rs.getDate("start_date"))
					.end_date(rs.getDate("end_date"))
					.option_cnt(rs.getInt("option_cnt"))
					.survey_cnt(rs.getInt("survey_cnt"))
					.build();	
		}
		
		pstmt.close();
		rs.close();
		
		return dto;
	}

	// 
	@Override
	public int update(SurveyDTO dto) throws SQLException {
		int rowCount = 0;
		
		String sql = "UPDATE survey "
				+ " SET title = ?, start_date = ?, end_date = ?, option_cnt = ?"
				+ " WHERE survey_id = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getTitle());
		pstmt.setDate(2, dto.getStart_date());
		pstmt.setDate(3, dto.getEnd_date());
		pstmt.setInt(4, dto.getOption_cnt());
		pstmt.setInt(5, dto.getSurvey_id());
		rowCount = pstmt.executeUpdate();
		
		pstmt.close();
		
		return rowCount;
	}

	@Override
	public int delete(int survey_seq) throws SQLException {
		int rowCount = 0;
		
		String sql = "DELETE FROM survey "
				+ " WHERE survey_id = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, survey_seq);
		rowCount = pstmt.executeUpdate();
		
		pstmt.close();
		
		return rowCount;
	}

	@Override
	public int insertVote(VoteDTO dto) throws SQLException {
		int rowCount = 0;
		
		String sql = "INSERT INTO vote(vote_seq, user_id, survey_id, option_id) "
				+ " VALUES(vote_seq.NEXTVAL, ?, ?, ?) ";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getUser_id());
		pstmt.setInt(2, dto.getSurvey_id());
		pstmt.setInt(3, dto.getOption_id());
		rowCount = pstmt.executeUpdate();
		
		pstmt.close();
		
		return rowCount;
	}

	@Override
	public int updateSurveyCnt(int survey_seq) throws SQLException {
		int rowCount = 0;
		
		String sql = "UPDATE survey "
				+ " SET survey_cnt = survey_cnt + 1"
				+ " WHERE survey_id = ?";
		
		PreparedStatement pstmt = null;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, survey_seq);
		rowCount = pstmt.executeUpdate();
		
		return rowCount;
	}
	
	
}
