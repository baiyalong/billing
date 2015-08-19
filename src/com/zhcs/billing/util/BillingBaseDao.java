package com.zhcs.billing.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.use.bean.JobTaskBean;

//import Util.BaseDao;

/**
 * JDBC 封装
 * 
 * @author LiuJie
 */
public class BillingBaseDao {

	private static LoggerUtil log = LoggerUtil.getLogger(BillingBaseDao.class);

	private Connection con;
	private ResultSet rs;
	private Statement stm;
	private PreparedStatement pstm;
	private PreparedStatement pstm2;

	public Connection connection() {
		try {
			if (con == null || con.isClosed()) {
				con = DbUtil.getCalculateConnection();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * @author LiuJie 获取Statement
	 */
	public Statement getStm() {
		try {
			con = connection();
			stm = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return stm;
	}

	/**
	 * @author LiuJie 获取 PreparedStatement
	 */
	public PreparedStatement getPstm(String sql) {
		try {
			con = connection();
			pstm = con.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return pstm;
	}

	/**
	 * @author LiuJie 获取 PreparedStatement
	 */
	public PreparedStatement getPstmt(String sql) {
		try {
			con = connection();
			pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return pstm;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @return ResultSet 查询数据库，返回结果 其他类调用过程如下： BaseDao bd=new BaseDao();
	 *         ResultSet rs=bd.getrs(sql); while(rs.next()) { int s1 =
	 *         rs.getInt(1); }
	 */
	public ResultSet getrs(String sql) {
		if (sql == null) {
			sql = "";
		}
		try {
			stm = getStm();
			rs = stm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closed();
		}
		return rs;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @return ResultSet 查询数据库，返回结果 其他类调用过程如下： BaseDao bd=new BaseDao();
	 *         ResultSet rs=bd.getRs(sql); while(rs.next()) { int s1 =
	 *         rs.getInt(1); int s2 = rs.getInt(2); int s3 = rs.getInt(3); }
	 * @throws SQLException
	 */
	public ResultSet getRs(String sql) throws SQLException {
		if (sql == null) {
			sql = "";
		}
		try {
			stm = getStm();
			rs = stm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closed();
		}
		return rs;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @return List 无条件查询 其他类调用过程如下： BaseDao bd=new BaseDao(); List list =
	 *         bd.doSelect(sql);
	 */
	public List<HashMap<String, Object>> doSelect(String sql) throws Exception {
		if (sql == null) {
			sql = "";
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		stm = getStm();
		rs = stm.executeQuery(sql);
		while (rs.next()) {
			HashMap<String, Object> temp = new HashMap<String, Object>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				temp.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
			}
			list.add(temp);
			temp = null;
		}
		closed();
		return list;
	}

	/*
	 * public List<HashMap<String, Object>> doSelect(String sql){ if (sql ==
	 * null) { sql = ""; } List<HashMap<String, Object>> list = new
	 * ArrayList<HashMap<String, Object>>(); try { stm = getStm(); rs =
	 * stm.executeQuery(sql); while (rs.next()) { HashMap<String, Object> temp =
	 * new HashMap<String, Object>(); for (int i = 1; i <=
	 * rs.getMetaData().getColumnCount(); i++) {
	 * temp.put(rs.getMetaData().getColumnName(i), rs.getObject(i)); }
	 * list.add(temp); } } catch (Exception e) { e.printStackTrace(); } finally
	 * { closed(); } return list; }
	 */
	public List<JobTaskBean> doExecuteQuery(String sql) {
		List<JobTaskBean> jtkList = new ArrayList<JobTaskBean>();
		if (sql == null) {
			sql = "";
		}
		try {
			pstm = getPstm(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				JobTaskBean jtkBean = new JobTaskBean();
				jtkBean.setTaskId(rs.getLong("TASK_ID"));
				jtkBean.setTaskCode(rs.getString("TASK_CODE"));
				jtkBean.setTaskType(rs.getString("TASK_TYPE"));
				jtkBean.setTaskImplClass(rs.getString("TASK_IMPL_CLASS"));
				jtkBean.setTaskExpress(rs.getString("TASK_EXPRESS"));
				jtkBean.setStateDate(rs.getTimestamp("STATE_DATE"));
				jtkBean.setState(rs.getString("STATE"));
				jtkBean.setParms(rs.getString("PARMS"));
				jtkBean.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				jtkList.add(jtkBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closed();
		}
		return jtkList;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @param params
	 * @return List 带条件查询 其他类调用过程如下： BaseDao bd=new BaseDao(); List list =
	 *         bd.doSelect(sql,params);
	 */
	public List<HashMap<String, Object>> doSelect(String sql, List params) {
		if (sql == null) {
			sql = "";
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			pstm = getPstm(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					// 参数有误！
					throw new Exception("SQL 语句参数有误！");
				}
			}
			rs = pstm.executeQuery();
			Integer metaDataCount = rs.getMetaData().getColumnCount();
			// log.info("BaseDao doSelect metaDataCount===="+metaDataCount);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= metaDataCount; i++) {
					// log.info("BaseDao doSelect getColumnName===="+rs.getMetaData().getColumnName(i)
					// +"getObject ==="+rs.getObject(i));
					temp.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
				}
				list.add(temp);
			}
		} catch (Exception e) {
			list = null;
			e.printStackTrace();
		} finally {
			closed();
		}
		return list;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @param params
	 * @return List 带条件查询 其他类调用过程如下： BaseDao bd=new BaseDao(); List list =
	 *         bd.doSelect(sql,params);
	 */
	public List<HashMap<String, Object>> doSelectOrder(String sql, List params) {
		if (sql == null) {
			sql = "doSelectOrder";
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			pstm = getPstm(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					// 参数有误！
					throw new Exception("SQL 语句参数有误！");
				}
			}
			rs = pstm.executeQuery();
			int metaDataCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= metaDataCount; i++) {
					// log.info("BillingBaseDao doSelect getColumnName===="
					// + rs.getMetaData().getColumnName(i)
					// + "getObject ===" + rs.getObject(i));
					temp.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
				}
				list.add(temp);
			}
		} catch (Exception e) {
			list = null;
			e.printStackTrace();
		} finally {
			// closed();
		}
		return list;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @return int 无条件对数据库进行添加或更新操作。适合SQL的insert语句和update语句
	 *         返回一个int值，表示添加或更新的记录数。若返回为0,表示添加或更新失败 ！ 其他类调用过程如下： BaseDao bd=new
	 *         BaseDao(); int i=bd.saveOrUpdate(sql); if(i==0){ return xx; }
	 *         return xx;
	 * @throws SQLException
	 */
	public int doSaveOrUpdate(String sql) throws SQLException {
		int num = 0;
		if (sql == null) {
			sql = "";
		}
		try {
			stm = getStm();
			con.setAutoCommit(false);
			num = stm.executeUpdate(sql);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			log.error(e.getMessage());
			num = 0;
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return num;
	}

	public boolean createTab(String sql) {
		boolean b = false;
		try {
			con = connection();
			PreparedStatement ps = con.prepareStatement(sql);
			b = ps.execute();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			closed();
		}
		return b;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @param params
	 * @return int 带条件对数据库进行添加或更新操作。适合SQL的insert语句和update语句
	 *         返回一个int值，表示添加或更新的记录数。若返回为0,表示添加或更新失败 ！ 其他类调用过程如下： BaseDao bd=new
	 *         BaseDao(); int i=bd.saveOrUpdate(sql,params); if(i==0){ return
	 *         xx; } return xx;
	 * @throws SQLException
	 */
	public int doSaveOrUpdate(String sql, List params) throws Exception {
		int num = 0;
		if (sql == null) {
			sql = "";
		}
		try {
			pstm = getPstm(sql);
			// log.info("BillingBaseDao doSaveOrUpdate pstm====" + pstm);
			// log.info("BillingBaseDao doSaveOrUpdate con====" + con
			// + "con.isColose:===" + con.isClosed());
			// if (con.isClosed()) {
			// con.notify();
			// }
			con.setAutoCommit(false);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					// 参数有误！
					throw new Exception("SQL 语句参数有误！");
				}
			}
			num = pstm.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			// pstm.close();
			closed();
		}
		return num;
	}

	/**
	 * 事物,实扣并同时更新累积量表
	 * 
	 * @param sql
	 * @param sql2
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int doSaveOrUpdate(String sql, String sql2, List params, List params2)
			throws Exception {
		int num = 0;
		if (sql == null) {
			sql = "";
		}
		if (sql2 == null) {
			sql2 = "";
		}
		try {
			pstm = getPstm(sql);
			pstm2 = getPstm(sql2);
			con.setAutoCommit(false);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					throw new Exception("SQL 语句参数有误！");
				}
			}
			if (params2 != null) {
				for (int i = 0; i < params2.size(); i++) {
					pstm2.setObject(i + 1, params2.get(i));
				}
				if (questionCount(sql2) != params2.size()) {
					throw new Exception("SQL 语句参数有误！");
				}
			}
			num = pstm2.executeUpdate();
			num = pstm.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			num = 0;
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return num;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @param params
	 * @return int 带条件对数据库进行添加或更新操作。适合SQL的insert语句和update语句 返回一个int值，表示添加或更新的ID
	 *         其他类调用过程如下： BaseDao bd=new BaseDao(); int
	 *         i=bd.saveOrUpdate(sql,params);
	 * @throws SQLException
	 */
	public String doSaveOrUpdateID(String sql, List params) throws Exception {
		// int num = 0;
		String num = "0";
		if (sql == null) {
			sql = "";
		}
		try {
			pstm = getPstmt(sql);
			con.setAutoCommit(false);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					// 参数有误！
					throw new Exception("SQL 语句参数有误！");
				}
			}

			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				num = String.valueOf(rs.getInt(1));
				// num = rs.getString("Id");
			}
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			e.getErrorCode();
			int code = e.getErrorCode();
			num = String.valueOf(code);
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return num;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @return boolean 无条件删除数据库的中数据 其他类调用过程如下： BaseDao bd=new BaseDao();
	 *         bd.delete(sql);
	 * @throws SQLException
	 */
	public boolean doDelete(String sql) throws SQLException {
		boolean ok;
		if (sql == null) {
			sql = "";
		}
		try {
			stm = getStm();
			con.setAutoCommit(false);
			ok = stm.execute(sql);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return true;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @param params
	 * @return boolean 带条件删除数据库的中数据 其他类调用过程如下： BaseDao bd=new BaseDao();
	 *         bd.delete(sql,params);
	 * @throws SQLException
	 */
	public boolean doDelete(String sql, List params) throws Exception {
		boolean ok;
		if (sql == null) {
			sql = "";
		}
		try {
			pstm = getPstm(sql);
			con.setAutoCommit(false);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					// 参数有误！
					throw new Exception("SQL 语句参数有误！");
				}
			}
			ok = pstm.execute();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return true;
	}

	/**
	 * @author LiuJie
	 * @param sql
	 * @return questions 获得SQL语句中有多个参数
	 */
	private int questionCount(String sql) {
		int questions = 0;
		for (int i = 0; i < sql.length(); i++) {
			if ("?".equals(sql.charAt(i) + "")) {
				questions++;
			}
		}
		return questions;
	}

	/**
	 * @author LiuJie 断开数据库连接 其他类调用过程如下： BaseDao bd=new BaseDao(); bd.closed();
	 */
	public void closed() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建表 （表名+当前时间）
	 */
	public static boolean createTable() {
		String dateStr = Common.getDateStr();
		String date = dateStr.substring(0, dateStr.length() - 2);
		boolean b = false;
		try {
			b = new BillingBaseDao()
					.createTab("create table T_USAGE_EVENTS_"
							+ date
							+ "(ID varchar(200) primary key,BILLCYCLE_ID varchar(200),REQ_TIME datetime,CUSTOMER_ID varchar(200),BILLING_ID varchar(200),PRUDUCT_ID varchar(200),REALOCKSTAT int,ACCOUNT decimal,FEE_TYPE boolean,SCHEDULEDSCANNINGSTATE varchar(200))");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return b;
	}
}
