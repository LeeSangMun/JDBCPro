package survey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VoteDTO {
	int vote_seq;
	String user_id;
	int survey_id;
	int option_id;
}

/*
CREATE TABLE vote(
	vote_seq NUMBER
	, user_id NVARCHAR2(20)
	, SURVEY_ID NUMBER(4) NOT NULL 
	, OPTION_ID NUMBER NOT NULL     
	, FOREIGN KEY (OPTION_ID) REFERENCES SURVEY_OPTION(op_seq)
	, FOREIGN KEY (SURVEY_ID) REFERENCES SURVEY(SURVEY_ID)
	, CONSTRAINT vote_pk PRIMARY KEY(vote_seq)
); 
*/

/*
 * CREATE SEQUENCE vote_seq;
 */