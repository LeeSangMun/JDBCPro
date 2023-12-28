package persistence;

import java.util.ArrayList;

import domain.EmpVO;

public interface EmpDAO {
	// 조회
	ArrayList<EmpVO> getSelect();

	// 검색
	ArrayList<EmpVO> getSelect(int searchCondition, String searchKeyword);
	
	// 추가
	int add(EmpVO vo);

	EmpVO get(int empno);

	// 수정
	int update(EmpVO vo);

	// 삭제
	int delete(int empno);
}
