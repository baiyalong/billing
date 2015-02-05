package com.zhcs.billing.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import Util.BaseDao;

/**
 * JDBC 封装
 * 
 * @author LiuJie
 */
public class BaseDao {
	private static LoggerUtil log = LoggerUtil.getLogger(BaseDao.class);

	private Connection con;
	private ResultSet rs;
	private Statement stm;
	private PreparedStatement pstm;

	public Connection connection() {
		try {
			if (con == null || con.isClosed()) {
				con = DbUtil.getConnection();
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
	public List<HashMap<String, Object>> doSelect(String sql) {
		if (sql == null) {
			sql = "";
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			stm = getStm();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					temp.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
				}
				list.add(temp);
			}
		} catch (Exception e) {
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
			int metaDataCount = rs.getMetaData().getColumnCount();
			log.info("BaseDao doSelect metaDataCount====" + metaDataCount);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
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
	 * 查询订单
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<HashMap<String, Object>> doSelectOrder(String sql, List params) {
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
			int metaDataCount = rs.getMetaData().getColumnCount();
			log.info("BaseDao doSelect metaDataCount====" + metaDataCount);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
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
			num = 0;
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return num;
	}

	public int doSaveOrUpdateS(List<String> sql, List<List> params)
			throws Exception {
		int num = 0;
		if (sql != null && params != null && !sql.isEmpty()
				&& !params.isEmpty() && sql.size() == params.size()) {
			this.con = this.connection();
			try {
				con.setAutoCommit(false);
				for (int j = 0; j < sql.size(); j++) {
					String sq = sql.get(j);
					List param = params.get(j);

					pstm = getPstm(sq);
					if (param != null) {
						for (int i = 0; i < param.size(); i++) {
							pstm.setObject(i + 1, param.get(i));
						}
						if (questionCount(sq) != param.size()) {
							// 参数有误！
							throw new Exception("SQL 语句参数有误！");
						}
					}
					num = pstm.executeUpdate();
				}

				con.commit();
			} catch (SQLException e) {
				con.rollback();
				e.printStackTrace();
				num = 0;
			} finally {
				con.setAutoCommit(true);
				closed();
			}
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
				// num = rs.getInt(1);
				num = rs.getString(1);
			}
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			// num = 0;
			num = "0";
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
	 * 得到当前年、月
	 * */
	public static String getDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(dt);
	}

	/**
	 * 往T_Bill_Cycle_Seq表插入数据
	 * */
	public static void insertBillTBillCycleSeq() {
		try {
			Common billingID = new Common();
			BaseDao bdao = new BaseDao();
			String id = billingID.createBillingID();
			String BillCycle = getDate();
			List params = new ArrayList();
			params.add(id);
			params.add(BillCycle);
			params.add(1);
			int flag = bdao.doSaveOrUpdate(
					"insert into T_Bill_Cycle_Seq values(?,?,?)", params);
			if (flag == 0) {
				log.info("添加或更新失败 ！");
			} else {
				log.info("添加或更新成功 ！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}
	}

	/**
	 * 封装成json格式
	 * 
	 * @param params
	 * @param values
	 * @param array
	 * @return
	 */

	public static String changeJSON(List<String> paramsList,
			List<String> valuesList) {
		JSONArray array = new JSONArray();
		JSONObject jso = new JSONObject();
		for (int i = 0; i < paramsList.size(); i++) {
			jso.put(paramsList.get(i), valuesList.get(i));
		}
		array.add(jso);
		return array.toString();
	}

	/**
	 * 得到账期
	 * */
	public String GetBillCycle() {
		/*** 根据 T_Bill_Cycle_Seq 表 Bill_Cycle_Stat字段的状态，得到账期 ***/
		String dateStr = "";
		String sql = "select Bill_Cycle from T_Bill_Cycle_Seq where Bill_Cycle_Stat = ?";
		List<Comparable> params = new ArrayList<Comparable>();
		params.add("True");

		List<HashMap<String, Object>> list = new BillingBaseDao().doSelect(sql,
				params);
		if (list.size() > 0 && list != null) {
			if (list.get(0).get("Bill_Cycle") != null) {
				dateStr = list.get(0).get("Bill_Cycle").toString();
			} else {
				log.error("Common Class execute GetBillCycle方法： T_Bill_Cycle_Seq 表 Bill_Cycle 字段 无数据!");
			}
		} else {
			log.error("Common Class execute GetBillCycle方法： T_Bill_Cycle_Seq 表无数据需要处理!");
		}
		return dateStr;
	}
}
