package survey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SurveyOptionDTO {
	int op_seq;
	int survey_id;
	String option_content;
}

/*
CREATE TABLE SURVEY_OPTION (
	op_seq NUMBER
	, SURVEY_ID NUMBER NOT NULL
	, OPTION_CONTENT VARCHAR2(20)
	, CONSTRAINT FK_OPTION_SURVEY_ID FOREIGN KEY(SURVEY_ID) REFERENCES SURVEY(SURVEY_ID) ON DELETE CASCADE
	, CONSTRAINT PK_survey_option_seq PRIMARY KEY(op_seq)
);
*/

/*
 * CREATE SEQUENCE op_seq;
 */