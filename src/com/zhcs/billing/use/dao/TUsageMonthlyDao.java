package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.TUsageEventsBean;
import com.zhcs.billing.use.bean.UsageMonthlyBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.Common;

/**
 * 查询包月月租费用信息类（按天扣）
 * 
 * @author 杨科
 *
 */
public class TUsageMonthlyDao {

	private static Logger log = LoggerFactory.getLogger(TUsageMonthlyDao.class);
	private BillingBaseDao billing;
	private BaseDao basedao;

	public BaseDao GetBaseDao() {
		if (basedao == null) {
			basedao = new BaseDao();
		}
		return basedao;
	}

	public BillingBaseDao GetBillingBaseDao() {
		if (billing == null) {
			billing = new BillingBaseDao();
		}
		return billing;
	}

	/**
	 * 往 资费月租表(T_USAGE_MONTHLY) 添加记录
	 * 字段名(主键id,外键,客户编号、按天扣或月初扣、月扣金额、订单编号、产品编号、产品类型、扫描状态,状态,服务器编号状态)
	 * 
	 * @param usageMonthlyBean
	 */
	@SuppressWarnings("unchecked")
	public void insertTUsageMonthly(UsageMonthlyBean usageMonthlyBean) {
		String insql = "INSERT INTO T_USAGE_MONTHLY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List param = new ArrayList();
		param.add(Common.createBillingID()); // 主键
		param.add(usageMonthlyBean.getBILLSEQ_ID()); // 外键 关联 T_Bill_Cycle_Seq
		param.add(usageMonthlyBean.getCUSTOMER_ID()); // 客户编号
		param.add(usageMonthlyBean.getFIXED_OR_MONTHLY()); // 按天扣或月初扣 0代表按天扣
		param.add(usageMonthlyBean.getSCANNING_WAY()); // 扫描方式
		param.add(usageMonthlyBean.getUNIT_RATE()); // 月扣金额 (月租)
		param.add(usageMonthlyBean.getORDER_ID()); // 订单编号
		param.add(usageMonthlyBean.getPRODUCT_ID()); // 产品编号
		param.add(usageMonthlyBean.getPRODUCT_TYPE()); // 产品类型
		param.add(usageMonthlyBean.getSCANNING_WAY()); // 扫描状态
		param.add(usageMonthlyBean.getSTATE()); // 状态
		param.add(usageMonthlyBean.getSERVERS_NO_STATE()); // 服务器编号状态
		param.add(usageMonthlyBean.getPRODUCT_CATEGORY()); // 产品大类（1：容器类 2：应用类）
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(insql, param);
			if (num != 0) {
				log.info("TUsageMonthlyDao Class insertTUsageMonthly Method： 入库T_USAGE_MONTHLY表成功,服务器所需要处理的数据状态为服务器编号:"
						+ usageMonthlyBean.getSERVERS_NO_STATE() + "成功！！");
			}
		} catch (Exception e) {
			log.error("TUsageMonthlyDao Class insertTUsageMonthly Method", e);
			e.printStackTrace();
		}
		if (num == 0) {
			log.info("TUsageMonthlyDao Class insertTUsageMonthly Method： 入库T_USAGE_MONTHLY表失败,服务器所需要处理的数据状态为服务器编号:"
					+ usageMonthlyBean.getSERVERS_NO_STATE() + "失败！！");
		}
		param = null;
	}

	/**
	 * 往 详单表（动态加年月）添加记录
	 * 
	 * @param usageEventsBean
	 */
	@SuppressWarnings("unchecked")
	public int insertTUsageEvents(TUsageEventsBean usageEventsBean) {
		String insql = "insert into T_USAGE_EVENTS_"
				+ Common.getDateStr().substring(0,
						Common.getDateStr().length() - 2)
				+ " values(?,?,?,?,?,?,?,?,?,?)";
		List orderParam = new ArrayList();
		orderParam.add(Common.getStrSSS()); // 主键id
		orderParam.add(usageEventsBean.getBILLCYCLE_ID()); // 外键 关联
															// T_Bill_Cycle_Seq
		orderParam.add(Common.getDateTimeSSS()); // 时间
		orderParam.add(usageEventsBean.getCUSTOMER_ID()); // 客户编号
		orderParam.add(usageEventsBean.getBILLING_ID()); // 接口之间的标识符
		orderParam.add(usageEventsBean.getPRUDUCT_ID()); // 产品编号
		orderParam.add(usageEventsBean.getREALOCKSTAT()); // 实扣结果(1实扣中2实扣完成3实扣失败)
		orderParam.add(usageEventsBean.getACCOUNT()); // 实扣金额
		orderParam.add(usageEventsBean.isFEE_TYPE()); // 费用类型
		orderParam.add(usageEventsBean.getSCHEDULEDSCANNINGSTATE());// 定时扫描状态
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(insql, orderParam);
			if (num != 0) {
				log.info("入库详单表T_USAGE_EVENTS_"
						+ Common.getDateStr().substring(0,
								Common.getDateStr().length() - 2) + "成功插入一条数据："
						+ num);
			}
		} catch (Exception e) {
			log.error("入库详单表T_USAGE_EVENTS_"
					+ Common.getDateStr().substring(0,
							Common.getDateStr().length() - 2) + "入库失败"
					+ e.getMessage());
			e.printStackTrace();
		}
		orderParam = null;
		return num;
	}

	/**
	 * 修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
	 * 
	 * @param usageMonthlyBean
	 * @param eachMachine
	 * @param theMachineStart
	 */
	@SuppressWarnings("unchecked")
	public void SetData(UsageMonthlyBean usageMonthlyBean, int eachMachine,
			int theMachineStart) {

		String sql = "UPDATE T_USAGE_MONTHLY SET SERVERS_NO_STATE = ? WHERE ID IN "
				+ "(SELECT T.ID FROM(SELECT ID FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? ORDER BY ID LIMIT "
				+ theMachineStart + "," + eachMachine + ")AS T)";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if (num != 0) {
				log.info("TUsageMonthlyDao Class SetData Method：修改服务器所需要处理的数据状态为服务器编号:"
						+ usageMonthlyBean.getSERVERS_NO_STATE() + "成功！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("TUsageMonthlyDao Class SetData Method", e);
		}
		if (num == 0) {
			log.info("TUsageMonthlyDao Class SetData Method：修改服务器所需要处理的数据状态为服务器编号:"
					+ usageMonthlyBean.getSERVERS_NO_STATE() + "失败！！");
			return;
		}
		params = null;
	}

	/**
	 * 修改STATE 为 threadName ，表示此线程正在扫描当中
	 * 
	 * @param usageMonthlyBean
	 * @param threadOneNum
	 * @return
	 */
	public int scanning(UsageMonthlyBean usageMonthlyBean, int threadOneNum) {
		String sql = "UPDATE T_USAGE_MONTHLY SET STATE = ?,COMPLETE_STATE = ? WHERE ID IN (SELECT T.ID FROM(SELECT ID FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? AND SERVERS_NO_STATE = ? LIMIT 0,"
				+ threadOneNum + ")AS T)";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getSTATE());
		params.add(usageMonthlyBean.getCOMPLETE_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if (stateNum != 0) {
				log.info("TUsageMonthlyDao Class scanning Method:修改T_USAGE_MONTHLY 表STATE 为 "
						+ usageMonthlyBean.getSTATE() + "成功！！");
			}
		} catch (Exception e) {
			log.error("TUsageMonthlyDao Class scanning Method ", e);
			e.printStackTrace();
		}
		if (stateNum == 0) {
			log.info("TUsageMonthlyDao Class scanning Method:修改T_USAGE_MONTHLY 表STATE 为 "
					+ usageMonthlyBean.getSTATE() + "失败！！");
		}
		params = null;
		return stateNum;
	}

	/**
	 * 修改STATE 为 完成，表示此线程已经扫描当完成
	 * 
	 * @param usageMonthlyBean
	 * @param ClassName
	 */
	public void updateUsageMonthlyState(UsageMonthlyBean usageMonthlyBean,
			String ClassName) {
		String sql = "UPDATE T_USAGE_MONTHLY SET COMPLETE_STATE = ? WHERE SCANNING_WAY = ? AND STATE = ? AND SERVERS_NO_STATE = ?";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getCOMPLETE_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		params.add(usageMonthlyBean.getSTATE());
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			log.info(" TUsageMonthlyDao Class run updateUsageMonthlyState Method 修改  T_USAGE_MONTHLY 表 STATE = 完成   成功.共修改了数据："
					+ stateNum);
		} catch (Exception e) {
			log.error("" + ClassName
					+ " Class run updateUsageMonthlyState Method ", e);
		}
		params = null;
		if (stateNum == 0) {
			log.info(" TUsageMonthlyDao Class run updateUsageMonthlyState Method 修改  T_USAGE_MONTHLY 表 STATE = 完成   失败！！");
			return; // 修改失败
		}

	}

	/**
	 * 判断数据是否都已经处理完成，清空T_USAGE_MONTHLY表为月租的数据
	 * 
	 * @param usageAccountBean
	 * @param tTNum
	 */
	public void deleteDate(UsageMonthlyBean usageMonthlyBean, int tTNum) {
		try {
			String sql = "SELECT COUNT(*) NUM FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
			List params = new ArrayList();
			params.add(usageMonthlyBean.getSCANNING_WAY());
			params.add(usageMonthlyBean.getSTATE());
			params.add(usageMonthlyBean.getCOMPLETE_STATE());
			List<HashMap<String, Object>> list = GetBillingBaseDao().doSelect(
					sql, params);
			int num = Integer.parseInt(list.get(0).get("NUM").toString()); // 所有线程共有处理了多少数据
			log.info("查询T_USAGE_ACCOUNT表，线程:" + usageMonthlyBean.getSTATE()
					+ " 共有处理了:" + num + " 条状态为:"
					+ usageMonthlyBean.getCOMPLETE_STATE() + "数据，应该处理的数为："
					+ tTNum);
			if (num == tTNum) {
				String sqlD = "DELETE FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
				List paramsD = new ArrayList();
				paramsD.add(usageMonthlyBean.getSCANNING_WAY());
				paramsD.add(usageMonthlyBean.getSTATE());
				paramsD.add(usageMonthlyBean.getCOMPLETE_STATE());
				GetBillingBaseDao().doDelete(sqlD, paramsD);
				params = null;
				log.info("成功删除线程:" + usageMonthlyBean.getSTATE()
						+ " 已经处理完成的数据 T_USAGE_MONTHLY表,删除个数为:" + num + "状态为:"
						+ usageMonthlyBean.getCOMPLETE_STATE());
			}
		} catch (Exception e) {
			log.error("TUsageMonthlyDao Class deleteDate Method ! ", e);
		}

	}

	/**
	 * 查询资费月租表
	 * 
	 * @param usageMonthlyBean
	 * @return
	 */
	public List<UsageMonthlyBean> listrate(UsageMonthlyBean usageMonthlyBean) {
		String sql = "SELECT * FROM T_USAGE_MONTHLY T WHERE T.SERVERS_NO_STATE = ? AND T.SCANNING_WAY = ? AND T.STATE = ?";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		params.add(usageMonthlyBean.getSTATE());
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<UsageMonthlyBean> l = new ArrayList<UsageMonthlyBean>();
		list = GetBillingBaseDao().doSelect(sql, params);
		l = changeToObject(list);
		params = null;
		list = null;
		return l;
	}

	/**
	 * 将返回结果转成UsageMonthlyBean列表对象
	 * 
	 * @param list
	 * @return
	 */
	private List<UsageMonthlyBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<UsageMonthlyBean> l = new ArrayList<UsageMonthlyBean>();
		for (HashMap<String, Object> d : list) {
			UsageMonthlyBean usageMonthlyBean = new UsageMonthlyBean();
			usageMonthlyBean.setBILLSEQ_ID(d.get("BILLSEQ_ID") != null ? d.get(
					"BILLSEQ_ID").toString() : null);
			usageMonthlyBean.setCUSTOMER_ID(d.get("CUSTOMER_ID") != null ? d
					.get("CUSTOMER_ID").toString() : null);
			usageMonthlyBean.setFIXED_OR_MONTHLY(Integer.parseInt(d
					.get("FIXED_OR_MONTHLY") != null ? d
					.get("FIXED_OR_MONTHLY").toString() : null));
			usageMonthlyBean.setSCANNING_WAY(d.get("SCANNING_WAY") != null ? d
					.get("SCANNING_WAY").toString() : null);
			usageMonthlyBean.setUNIT_RATE(d.get("UNIT_RATE") != null ? d.get(
					"UNIT_RATE").toString() : null);
			usageMonthlyBean.setORDER_ID(d.get("ORDER_ID") != null ? d.get(
					"ORDER_ID").toString() : null);
			usageMonthlyBean.setPRODUCT_ID(d.get("PRODUCT_ID") != null ? d.get(
					"PRODUCT_ID").toString() : null);
			usageMonthlyBean.setPRODUCT_TYPE(d.get("PRODUCT_TYPE") != null ? d
					.get("PRODUCT_TYPE").toString() : null);
			usageMonthlyBean
					.setCOMPLETE_STATE(d.get("COMPLETE_STATE") != null ? d.get(
							"COMPLETE_STATE").toString() : null);
			usageMonthlyBean.setSTATE(d.get("STATE") != null ? d.get("STATE")
					.toString() : null);
			usageMonthlyBean.setSERVERS_NO_STATE(Integer.parseInt(d
					.get("SERVERS_NO_STATE") != null ? d
					.get("SERVERS_NO_STATE").toString() : null));
			usageMonthlyBean.setPRODUCT_CATEGORY(Integer.parseInt(d
					.get("PRODUCT_CATEGORY") != null ? d
					.get("PRODUCT_CATEGORY").toString() : null));
			l.add(usageMonthlyBean);
		}
		return l;
	}

}
