package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.TCulOrderDetailBean;
import com.zhcs.billing.use.bean.TScanningAddTotalBean;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 
 * @author hefa
 *
 */
public class InsertClass {
	private static LoggerUtil logUtil = LoggerUtil.getLogger(InsertClass.class);
	private static Logger log = LoggerFactory.getLogger(InsertClass.class);
	/**
	 * 存入三次批价
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
			log.info("执行三次批价添加："+sql);
			logUtil.info("执行三次批价添加："+sql);
		} catch (Exception e) {
			result = "失败 " + e.getMessage();
			log.error("执行三次批价添加失败SQL:"+sql+"失败原因"+e.getMessage()+"SQL语句:"+sql);
			logUtil.error("执行三次批价添加失败SQL:"+sql+"失败原因"+e.getMessage()+"SQL语句:"+sql);
		}
		return result;
	}

	/**
	 * 添加累积量
	 * @param bean
	 * @return
	 */
	public static String AddTScanningAddTotal(TScanningAddTotalBean bean) {

		String result = "";
		String sql = " IINSERT INTO T_SCANNING_ADD_TOTAL(ID,ORDER_ID,PRODUCT_ID,RESOURCE_ID, "
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
			log.info("执行添加累积量："+sql);
			logUtil.info("执行添加累积量："+sql);
		} catch (Exception e) {
			result = "失败 " + e.getMessage();
			log.error("执行添加累积量失败SQL:"+sql+"失败原因"+e.getMessage()+"SQL语句:"+sql);
			logUtil.error("执行添加累积量失败SQL:"+sql+"失败原因"+e.getMessage()+"SQL语句:"+sql);
		}
		return result;
	}

}
