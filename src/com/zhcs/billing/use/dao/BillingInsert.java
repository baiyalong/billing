package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.AccountCheckAllBean;
import com.zhcs.billing.use.bean.AccountCheckBean;
import com.zhcs.billing.use.bean.AccountInfoBean;
import com.zhcs.billing.use.bean.PartnerSettlementRuleBean;
import com.zhcs.billing.use.bean.RDetailRecordAbilityBean;
import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.bean.TCulOrderDetailBean;
import com.zhcs.billing.use.bean.TScanningAddTotalBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author hefa
 *
 */
public class BillingInsert {
	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(BillingInsert.class);
	private static Logger log = LoggerFactory.getLogger(BillingInsert.class);

	// 锁
	private static Lock lock_RPackage = new ReentrantLock();
	private static Lock lock_RBalance = new ReentrantLock();

	/**
	 * 实时计费 异常话单记录
	 * 
	 * @param bean
	 * @return
	 */
	public static boolean RErrorRecord(RErrorRecordBean bean) {
		boolean res = false;

		String sql = "INSERT INTO R_ERROR_RECORD(MSG_TYPE,MSG,DESCRIPTION,TIMESTAMP) "
				+ "VALUES (?,?,?,now());";

		List params = new ArrayList();
		params.add(bean.getMSG_TYPE());
		params.add(bean.getMSG());
		params.add(bean.getDESCRIPTION());

		try {
			int rs = new BillingBaseDao().doSaveOrUpdate(sql, params);
			if (rs == 0) {
				throw new Exception();
			}
			res = true;
			log.info("实时计费 异常话单记录：" + bean.getMSG());
			logUtil.info("实时计费 异常话单记录：" + bean.getMSG());
		} catch (Exception e) {
			log.error("实时计费 异常话单记录失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
			logUtil.error("实时计费 异常话单记录失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
		}
		return res;
	}

	/**
	 * 存入三次批价
	 * 
	 * @param bean
	 * @return
	 */
	public static String AddTCulOrderDetail(TCulOrderDetailBean bean) {

		String result = "";

		String sql = "INSERT INTO T_CUL_ORDER_DETAIL(ID,ORDER_ID,CODE,BEFORE_AMOUNT,AFTER_AMOUNT,COUNT_COST,DEDUCT_COST,REALITY,CTYPE,SCANNING_TIME,SCANNING_WAY,CREATE_TIME,UPDATE_TIME) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,now(),?);";

		List params = new ArrayList();
		params.add(bean.getID());
		params.add(bean.getORDER_ID());
		params.add(bean.getCODE());
		params.add(bean.getBEFORE_AMOUNT());
		params.add(bean.getAFTER_AMOUNT());
		params.add(bean.getCOUNT_COST());
		params.add(bean.getDEDUCT_COST());
		params.add(bean.getREALITY());
		params.add(bean.getCTYPE());
		params.add(bean.getSCANNING_TIME());
		params.add(bean.getSCANNING_WAY());
		params.add(bean.getUPDATE_TIME());
		try {
			new BillingBaseDao().doSaveOrUpdate(sql, params);
			result = "成功";
			log.info("执行三次批价添加：" + sql);
			logUtil.info("执行三次批价添加：" + sql);
		} catch (Exception e) {
			result = "失败 " + e.getMessage();
			log.error("执行三次批价添加失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
			logUtil.error("执行三次批价添加失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
		}
		return result;
	}

	/**
	 * 添加累积量
	 * 
	 * @param bean
	 * @return
	 */
	public static String AddTScanningAddTotal(TScanningAddTotalBean bean) {

		String result = "";
		String sql = " INSERT INTO T_SCANNING_ADD_TOTAL(ID,ORDER_ID,PRODUCT_ID,RESOURCE_ID, "
				+ " WD_NAME,WD_ID,CURRENT_ADD_TOTAL,SCANNING_WAY,START_TIME,END_TIME,CREATE_TIME, UPDATE_TIME) "
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?)";

		List params = new ArrayList();
		params.add(bean.getID());
		params.add(bean.getORDER_ID());
		params.add(bean.getPRODUCT_ID());
		params.add(bean.getRESOURCE_ID());
		params.add(bean.getWD_NAME());
		params.add(bean.getWD_NO());
		params.add(bean.getCURRENT_ADD_TOTAL());
		params.add(bean.getSCANNING_WAY());
		params.add(bean.getSTART_TIME());
		params.add(bean.getEND_TIME());
		params.add(bean.getCREATE_TIME());
		params.add(bean.getUPDATE_TIME());

		try {
			new BillingBaseDao().doSaveOrUpdate(sql, params);
			result = "成功";
			log.info("执行添加累积量：" + sql);
			logUtil.info("执行添加累积量：" + sql);
		} catch (Exception e) {
			result = "失败 " + e.getMessage();
			log.error("执行添加累积量失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
			logUtil.error("执行添加累积量失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
		}
		return result;
	}

	public static boolean RDetailRecordAbility(RDetailRecordAbilityBean bean) {
		boolean res = false;

		String sql = "INSERT INTO R_DETAIL_RECORD_ABILITY(MSG_TYPE,CUSTOMER_ID,CONTAINER_ID,DEDUCTION_TYPE,DEDUCTION_AMOUNT,OPERATION_RESULT,PAYMENT_TYPE,ORIGINAL_RECORD_TIME,DETAIL_RECORD_TIME,ORIGINAL_RECORD) "
				+ "VALUES (?,?,?,?,?,?,?,?,now(),?);";

		List params = new ArrayList();
		params.add(bean.getMSG_TYPE());
		params.add(bean.getCUSTOMER_ID());
		params.add(bean.getCOUNTAINER_ID());
		params.add(bean.getDEDUCTION_TYPE());
		params.add(bean.getDEDUCTION_AMOUNT());
		params.add(bean.getOPERATION_RESULT());
		params.add(bean.getPAYMENT_TYPE());
		params.add(bean.getORIGINAL_RECORD_TIME());
		params.add(bean.getORIGINAL_RECORD());

		try {
			new BillingBaseDao().doSaveOrUpdate(sql, params);
			res = true;
			log.info("实时计费 详单记录：" + sql + params.toString());
			logUtil.info("实时计费 详单记录：" + sql);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("实时计费 详单记录失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
			logUtil.error("实时计费 详单记录失败SQL:" + sql + "失败原因" + e.getMessage()
					+ "SQL语句:" + sql);
		}
		return res;
	}

	// 扣套餐
	// true 成功，完成扣费操作
	// false 失败，没有订购套餐或套餐已用完
	public static boolean RPackage(RDetailRecordAbilityBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		boolean res = false;

		BaseDao dao = new BaseDao();
		List params = new ArrayList();
		List<HashMap<String, Object>> li;
		String sql = "";
		// 是否订购套餐
		if (bean.getDEDUCTION_TYPE() == 0) {
			res = false;
		} else {
			// 查套餐余额并扣1，需要同步
			lock_RPackage.lock();
			try {
				// 查询余额
				sql = "select REMAINING_AMOUNT from SUBSCRIPTION_ITEM where SI_ID=?;";
				params.add(bean.getSI_ID());
				li = dao.doSelect(sql, params);
				if (li != null && !li.isEmpty()) {
					bean.setREMAINING_AMOUNT((Integer) li.get(0).get(
							"REMAINING_AMOUNT"));
					if (bean.getREMAINING_AMOUNT() > 0) {
						// 扣套餐
						sql = "update SUBSCRIPTION_ITEM set REMAINING_AMOUNT = REMAINING_AMOUNT -1 where SI_ID=?; ";
						int rs = dao.doSaveOrUpdate(sql, params);
						if (rs == 0) {
							throw new Exception();
						}
						res = true;
						bean.setDEDUCTION_TYPE(2);// 套餐未用完，扣套餐
					} else {
						bean.setDEDUCTION_TYPE(1);// 套餐已用完，扣余额
					}
				} else {
					log.error("没有找到套餐余额信息！");
					logUtil.error("没有找到套餐余额信息！");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock_RPackage.unlock();
			}
		}

		return res;
	}

	// 扣余额
	// true 成功，完成扣费操作
	// false 失败，账户余额不足
	public static boolean RBalance(RDetailRecordAbilityBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		boolean res = false;

		BaseDao dao = new BaseDao();
		List<String> sql = new ArrayList();
		List params = new ArrayList();
		List<HashMap<String, Object>> li;
		int rest = 0; // 余额
		// 查余额并扣费，需要同步
		lock_RBalance.lock();
		try {
			// 查余额
			String sq = "select BALANCE from  ACCOUNT_BOOK where BOOK_ID = ?;";
			List param = new ArrayList();
			param.add(bean.getBOOK_ID());
			li = dao.doSelect(sq, param);
			if (li != null && !li.isEmpty()) {
				rest = Integer.parseInt((String) li.get(0).get("BALANCE"));
				bean.setOPERATION_RESULT(rest > bean.getPRICE() ? 0 : 1);// 0 正常
																			// 1
																			// 欠费
				// 扣费
				// 1。生成流水
				String serialNo = UUID.randomUUID().toString();
				String sq1 = "insert into TRADE_SERIAL (SERIAL_NO,CREATE_TIME) values (?,now());";
				List param1 = new ArrayList();
				param1.add(serialNo);
				sql.add(sq1);
				params.add(param1);

				// 2。添加账本收支记录
				String sq2 = "insert into ACCOUNT_TRANSACTION "
						+ "(TRANSACTION_ID,BOOK_ID,PROVINCE_CODE,AREA_CODE,CUSTOMER_ID,ACCOUNT_ID,SERIAL_NO,TRANSACTION_TYPE,INOUT_FLAG,AMOUNT,BALANCE,LOCK_AMOUNT,DEAL_TIME,DESCRIPTION,TRAN_STATUS)"
						+ "values "
						+ "(?,?,?,?,?,?,?,2,4,?,?,0,now(),'RAbility',0);";
				List param2 = new ArrayList();
				param2.add(UUID.randomUUID().toString());
				param2.add(bean.getBOOK_ID());
				param2.add(bean.getPROVINCE_CODE());
				param2.add(bean.getAREA_CODE());
				param2.add(bean.getCUSTOMER_ID());
				param2.add(bean.getACCOUNT_ID());
				param2.add(serialNo);
				param2.add(bean.getPRICE());
				param2.add(rest);
				sql.add(sq2);
				params.add(param2);

				// 3.账本扣费
				String sq3 = "update ACCOUNT_BOOK set BALANCE = BALANCE - ?,CREDIT_AMOUNT = CREDIT_AMOUNT + ? where BOOK_ID = ?;";
				List param3 = new ArrayList();
				param3.add(bean.getPRICE());
				param3.add(bean.getBOOK_ID());
				sql.add(sq3);
				params.add(param3);

				int rs = dao.doSaveOrUpdateS(sql, params);
				if (rs == 0) {
					throw new Exception("能力实时计费-扣费失败");
				}
				res = true;
				bean.setDEDUCTION_AMOUNT(bean.getPRICE());

			}
		} catch (Exception e) {
		} finally {
			lock_RBalance.unlock();
		}

		return res;
	}

	public static boolean PartnerSettlementRecords(
			PartnerSettlementRuleBean bean, int amount) {
		// TODO Auto-generated method stub
		boolean res = false;
		List params = new ArrayList();
		BillingBaseDao dao = new BillingBaseDao();

		String sql = "insert into PARTNER_SETTLEMENT_RECORDS (PARTNER_ID,SETTLEMENT_RULE_ID,SETTLEMENT_AMOUNT,SETTLEMENT_DATE) values (?,?,?,now());";
		params.add(bean.getPARTNER_ID());
		params.add(bean.getID());
		params.add(amount);
		try {
			int rs = dao.doSaveOrUpdate(sql, params);
			res = rs == 0 ? false : true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public static void AccountCheckAll(AccountCheckAllBean bean)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "insert into ACCOUNT_CHECK_ALL (INCOME,OUTCOME,BALANCE,CHECK_DATE) values (?,?,?,now());";
		List params = new ArrayList();
		params.add(bean.getINCOME());
		params.add(bean.getOUTCOME());
		params.add(bean.getBALANCE());

		int rs = new BillingBaseDao().doSaveOrUpdate(sql, params);
		if (rs == 0) {
			throw new Exception();
		}

	}

	public static void AccountCheck(AccountCheckBean b) {
		// TODO Auto-generated method stub

	}

	public static void RDetailRecordApp(String msg) throws Exception {
		// TODO Auto-generated method stub

		String sql = "insert into R_DETAIL_RECORD_APP (APP_ID,ORIGINAL_RECORD_TIME,DETAIL_RECORD_TIME,ORIGINAL_RECORD) values (0,now(),now(),?);";
		List params = new ArrayList();
		params.add(msg);

		int rs = new BillingBaseDao().doSaveOrUpdate(sql, params);
		if (rs == 0) {
			throw new Exception();
		}
	}
}
