package days02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBConn;

import domain.EmpVO;
import persistence.EmpDAO;
import persistence.EmpDAOImpl2;

/**
 * @author sangmun
 * @date 2023. 9. 20. - 오후 2:05:18
 * @subject
 * @content
 */
public class Ex04 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// persistence.EmpDAO.java
		
		ArrayList<EmpVO> list = null;
		Connection conn = DBConn.getConnection();
		
//		EmpDAO dao = new EmpDAOImpl(conn);
		EmpDAO dao = new EmpDAOImpl2(conn);
		
		/*
		list = dao.getSelect();
		printEmpList(list);
		*/
		
		Scanner scanner = new Scanner(System.in);
		
		// 검색
		/*
		System.out.print("> 검색 조건을 입력 ? ");
		int searchCondition = scanner.nextInt();
		
		System.out.print("> 검색어를 입력 ? ");
		String searchWord = scanner.next();
		list = dao.getSelect(searchCondition, searchWord);
		printEmpList(list);
		*/
		
		//  추가
		/*
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("1. empno 입력 ? ");
		empno = Integer.parseInt(br.readLine());
		System.out.print("2. ename 입력 ? ");
		ename = br.readLine();
		System.out.print("3. job 입력 ? ");
		job = br.readLine();
		System.out.print("4. mgr 입력 ? ");
		mgr = Integer.parseInt(br.readLine());
		System.out.print("5. hiredate 입력 ? ");
		hiredate = br.readLine();
		System.out.print("6. sal 입력 ? ");
		sal = Double.parseDouble(br.readLine());
		System.out.print("7. comm 입력 ? ");
		comm = Double.parseDouble(br.readLine());
		System.out.print("8. deptno 입력 ? ");
		deptno = Integer.parseInt(br.readLine());

		EmpVO vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
		
		int rowCount = dao.add(vo);
		if(rowCount == 1) System.out.println("사원 추가 완료"); 
		*/
		
		// 수정
		/*
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("1. 수정할 empno 입력 ? ");
		empno = Integer.parseInt(br.readLine());
		EmpVO vo = dao.get(empno);
		
		System.out.println(vo);
		
		System.out.print("2. ename 입력 ? ");
		ename = br.readLine();
		if(ename.isEmpty()) ename = vo.getEname();
		System.out.print("3. job 입력 ? ");
		job = br.readLine();
		if(job.isEmpty()) job = vo.getJob();
		System.out.print("4. mgr 입력 ? ");
		try {
			mgr = Integer.parseInt(br.readLine());
		} catch(Exception e) {
			mgr = vo.getMgr();
		}
		System.out.print("6. sal 입력 ? ");
		try {
			sal = Double.parseDouble(br.readLine());
		} catch(Exception e) {
			sal = vo.getSal();
		}
		System.out.print("7. comm 입력 ? ");
		try {
			comm = Double.parseDouble(br.readLine());
		} catch(Exception e) {
			comm = vo.getComm();
		}
		System.out.print("8. deptno 입력 ? ");
		try {
			deptno = Integer.parseInt(br.readLine());
		} catch(Exception e) {
			deptno = vo.getDeptno();
		}
		
		// 입력 X - > 예전의 컬럼값..
		
		vo = new EmpVO(empno, ename, job, mgr, vo.getHiredate(), sal, comm, deptno);
		
		int rowCount = dao.update(vo);
		
		if(rowCount == 1) System.out.println("사원 수정 완료!!!");
		*/
		
		// 삭제
		/*
		int empno;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("1. 삭제할 empno 입력 ? ");
		empno = Integer.parseInt(br.readLine());
		int rowCount = dao.delete(empno);
		if(rowCount == 1) System.out.println("사원 삭제 완료!!!");
		*/
		
		DBConn.close();
		System.out.println("exit");
		
	}

	private static void printEmpList(ArrayList<EmpVO> list) {
		if(list == null) {
			System.out.println("> 검색된 사원이 없음");
			return;
		}
		
		System.out.printf("검색된 사원 수 : %d\n", list.size());
		Iterator<EmpVO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpVO vo = ir.next();
			System.out.println(vo);
		}
		
	}
}
