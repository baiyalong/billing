package com.zhcs.billing.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MeteringBaseDao {
	private static	LoggerUtil log=LoggerUtil.getLogger(MeteringBaseDao.class);
	private Connection con;
	private ResultSet rs;
	private Statement stm;
	private PreparedStatement pstm;
	
	public Connection connection() {
		try {
			//if (con == null || con.isClosed()) {
				con = DbUtil.getEstimateConnection();
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	/**
	 * @author LiuJie
	 * ��ȡStatement
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
	 * @author LiuJie
	 * ��ȡ PreparedStatement
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
	 * @author LiuJie
	 * ��ȡ PreparedStatement
	 */
	public PreparedStatement getPstmt(String sql) {
		try {
			 con = connection();
	         pstm = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return pstm;
	}

	/**
	 * @author LiuJie
	 * @param  sql
	 * @return ResultSet
	 * ��ѯ���ݿ⣬���ؽ�� 
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao(); 
	 * ResultSet rs=bd.getrs(sql); 
	 * while(rs.next())
	 * { 
	 * 		int s1 = rs.getInt(1); 
	 * }
	 */
	public ResultSet getrs(String sql) {
		if (sql == null){
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
	 * @param  sql
	 * @return ResultSet
	 * ��ѯ���ݿ⣬���ؽ��
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * ResultSet rs=bd.getRs(sql); 
	 * while(rs.next())
	 * { 
	 * 		int s1 = rs.getInt(1); 
	 * 		int s2 = rs.getInt(2); 
	 * 		int s3 = rs.getInt(3); 
	 * }
	 * @throws SQLException 
	 */
	public ResultSet getRs(String sql) throws SQLException {
		if (sql == null){
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
	 * @param  sql    
	 * @return List
	 * ��������ѯ
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * List list = bd.doSelect(sql);
	 */
	public List<HashMap<String, Object>> doSelect(String sql) {
		if (sql == null){
			sql = "";
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			stm = getStm();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					temp.put(rs.getMetaData().getColumnName(i),rs.getObject(i));
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
	 * @param  sql
	 * @param  params 
	 * @return List
	 * ��������ѯ
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * List list = bd.doSelect(sql,params);
	 */
	public List<HashMap<String, Object>> doSelect(String sql,List params) {
		if (sql == null){
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
					// ��������
					throw new Exception("SQL ����������");
				}
			}
			rs = pstm.executeQuery();
			int metaDataCount = rs.getMetaData().getColumnCount();
			log.info("BaseDao doSelect metaDataCount===="+metaDataCount);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//					log.info("BaseDao doSelect getColumnName===="+rs.getMetaData().getColumnName(i)
//							+"getObject ==="+rs.getObject(i));
					temp.put(rs.getMetaData().getColumnName(i),rs.getObject(i));
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
	public List<HashMap<String, Object>> doSelectOrders(String sql,List params) {
		if (sql == null){
			sql = "";
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			 con = DbUtil.getEstimateConnection();
	         pstm = con.prepareStatement(sql);
			/*pstm = getPstm(sql);*/
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					pstm.setObject(i + 1, params.get(i));
				}
				if (questionCount(sql) != params.size()) {
					// ��������
					throw new Exception("SQL ����������");
				}
			}
			rs = pstm.executeQuery();
			int metaDataCount = rs.getMetaData().getColumnCount();
			log.info("BaseDao doSelect metaDataCount===="+metaDataCount);
			while (rs.next()) {
				HashMap<String, Object> temp = new HashMap<String, Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//					log.info("BaseDao doSelect getColumnName===="+rs.getMetaData().getColumnName(i)
//							+"getObject ==="+rs.getObject(i));
					temp.put(rs.getMetaData().getColumnName(i),rs.getObject(i));
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
	 * @param  sql
	 * @return int
	 * �����������ݿ������ӻ���²������ʺ�SQL��insert����update��� 
	 * ����һ��intֵ����ʾ��ӻ���µļ�¼����������Ϊ0,��ʾ��ӻ����ʧ�� ��
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * int i=bd.saveOrUpdate(sql); 
	 * if(i==0){ 
	 * 		return xx;
	 * } 
	 *      return xx;
	 * @throws SQLException 
	 */
	public int doSaveOrUpdate(String sql) throws SQLException {
		int num = 0;
		if (sql == null){
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
	 * @param  sql
	 * @param  params
	 * @return int
	 * �����������ݿ������ӻ���²������ʺ�SQL��insert����update��� 
	 * ����һ��intֵ����ʾ��ӻ���µļ�¼����������Ϊ0,��ʾ��ӻ����ʧ�� ��
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * int i=bd.saveOrUpdate(sql,params); 
	 * if(i==0){ 
	 * 		return xx;
	 * } 
	 *      return xx;
	 * @throws SQLException 
	 */
	public int doSaveOrUpdate(String sql,List params) throws Exception {
		int num = 0;
		if (sql == null){
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
					// ��������
					throw new Exception("SQL ����������");
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
	/**
	 * @author LiuJie
	 * @param  sql
	 * @param  params
	 * @return int
	 * �����������ݿ������ӻ���²������ʺ�SQL��insert����update��� 
	 * ����һ��intֵ����ʾ��ӻ���µ�ID 
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * int i=bd.saveOrUpdate(sql,params); 
	 * @throws SQLException 
	 */
	public String doSaveOrUpdateID(String sql,List params) throws Exception {
		//int num = 0;
		String num = "0";
		if (sql == null){
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
					// ��������
					throw new Exception("SQL ����������");
				}
			}
			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();
            if(rs.next())
            {
                //num = rs.getInt(1);
            	num = rs.getString(1);
            }
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			e.printStackTrace();
			//num = 0;
			num = "0";
		} finally {
			con.setAutoCommit(true);
			closed();
		}
		return num;
	}

	/**
	 * @author LiuJie
	 * @param  sql
	 * @return boolean
	 * ������ɾ�����ݿ�������� 
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * bd.delete(sql);
	 * @throws SQLException 
	 */
	public boolean doDelete(String sql) throws SQLException {
		boolean ok;
		if (sql == null){
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
	 * @param  sql
	 * @param  params
	 * @return boolean
	 * ������ɾ�����ݿ�������� 
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * bd.delete(sql,params);
	 * @throws SQLException 
	 */
	public boolean doDelete(String sql,List params) throws Exception {
		boolean ok;
		if (sql == null){
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
					// ��������
					throw new Exception("SQL ����������");
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
	 * @param  sql
	 * @return questions
	 * ���SQL������ж������
	 */
	private int questionCount(String sql) {
		int questions = 0;
		for (int i = 0; i < sql.length(); i++) {
			if ("?".equals(sql.charAt(i)+"")){
				questions++;
			}
		}
		return questions;
	}
	
	/**
	 * @author LiuJie
	 * �Ͽ����ݿ����� 
	 * ��������ù������£� 
	 * BaseDao bd=new BaseDao();
	 * bd.closed();
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
	
}
