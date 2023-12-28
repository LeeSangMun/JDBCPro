package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

import domain.EmpVO;

public class EmpDAOImpl implements EmpDAO {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public EmpDAOImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	// 조회
	public ArrayList<EmpVO> getSelect() {
		ArrayList<EmpVO> list = null;

		EmpVO empVO = null;

		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;

		String sql = "SELECT * FROM emp";

		try {
//			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getString("hiredate");
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					empVO = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
					list.add(empVO);
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 검색
	public ArrayList<EmpVO> getSelect(int searchCondition, String searchKeyword) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<EmpVO> list = null;
		EmpVO vo = null;
		
		int empno;
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;
		
		
		String sql = "SELECT * FROM emp"
						+ " WHERE ";
		
		if (searchCondition == 1) { // 사원명
			sql += String.format(" REGEXP_LIKE(ename, '%s', 'i')", searchKeyword);
		} else if(searchCondition == 2) { // 부서명
			sql += String.format(" deptno IN (%s)", searchKeyword);
		} else if(searchCondition == 3) { // 잡
			sql += String.format(" REGEXP_LIKE(job, '%s', 'i')", searchKeyword);
		}
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					empno = rs.getInt("empno");
					ename = rs.getString("ename");
					job = rs.getString("job");
					mgr = rs.getInt("mgr");
					hiredate = rs.getString("hiredate");
					sal = rs.getDouble("sal");
					comm = rs.getDouble("comm");
					deptno = rs.getInt("deptno");
					vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
					list.add(vo);
				} while (rs.next());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("end");
		
		return list;
	}
	
	// 추가
	public int add(EmpVO vo) {
		int rowCount = 0;
		
		String sql = 
				String.format("INSERT INTO emp ( empno, ename, job, mgr, hiredate, sal, comm, deptno ) "
		                  + " VALUES ( %d, '%s', '%s', %d, '%s', %f, %f, %d)"
		                  , vo.getEmpno(), vo.getEname(), vo.getJob(), vo.getMgr()
		                  , vo.getHiredate(), vo.getSal(), vo.getComm(), vo.getDeptno() );

	      try {
	         conn =  DBConn.getConnection();
	         stmt = conn.createStatement(); 
	         rowCount = stmt.executeUpdate(sql);
	      } catch (SQLException e) { 
	         e.printStackTrace();
	      } finally {
	         try {
	            stmt.close();
	         } catch (SQLException e) { 
	            e.printStackTrace();
	         }
	      }

		return rowCount;
	}

	public EmpVO get(int empno) {
		EmpVO vo = null;
		
		String ename;
		String job;
		int mgr;
		String hiredate;
		double sal;
		double comm;
		int deptno;
		
		String sql = "SELECT * FROM emp"
						+ " WHERE empno = " + empno;
		
		System.out.println(sql);
		
		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				mgr = rs.getInt("mgr");
				hiredate = rs.getString("hiredate");
				sal = rs.getDouble("sal");
				comm = rs.getDouble("comm");
				deptno = rs.getInt("deptno");
				vo = new EmpVO(empno, ename, job, mgr, hiredate, sal, comm, deptno);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("end");
		
		return vo;
	}

	// 수정
	public int update(EmpVO vo) {
		int rowCount = 0;

		String sql = String.format(
				"UPDATE emp "
				+ " SET ename = '%s', job = '%s', mgr = %d"
				+ ", sal = %f, comm = %f, deptno = %d"
				+ " WHERE empno = %d"
						, vo.getEname(), vo.getJob(), vo.getMgr()
						, vo.getSal(), vo.getComm(), vo.getDeptno(), vo.getEmpno());

		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowCount;
	}

	// 삭제
	public int delete(int empno) {
		int rowCount = 0;

		String sql = String.format("DELETE FROM emp"
										+ " WHERE empno = %d", empno);

		try {
			conn = DBConn.getConnection();
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rowCount;
	}
}
