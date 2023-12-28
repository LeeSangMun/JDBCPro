package survey.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyDTO {
	int survey_id;
	String user_id;
	String title;
	int survey_cnt;
	int option_cnt;
	Date start_date;
	Date end_date;
	Date regdate; 
}

/*
CREATE TABLE SURVEY (
	SURVEY_ID NUMBER PRIMARY KEY NOT NULL 
	, USER_ID NVARCHAR2(20)
	, TITLE VARCHAR2(100) NOT NULL
	, SURVEY_CNT NUMBER(10) DEFAULT 0 
	, OPTION_cnt NUMBER(3) DEFAULT 0
	, START_DATE DATE DEFAULT SYSDATE NOT NULL
	, END_DATE DATE NOT NULL
	--, STATE VARCHAR(20) DEFAULT '일반'
	--, CONSTRAINT FK_SURVEY_USER_ID FOREIGN KEY(USER_ID) REFERENCES USER_INFO(user_id) ON DELETE SET NULL
	, REGDATE DATE DEFAULT SYSDATE
);
*/


/*
 * CREATE SEQUENCE survey_seq;
 */