package com.zhcs.billing.util;

/**
 * sql语句管理类
 * 
 * @author yuqingchao
 * 
 */
public class VariableConfigManager {

	// *********************************yuqingchao
	// start****************************************
	// com.zhcs.billing.quartz.service.JobEngine
	public static String execute_select = "SELECT * FROM T_JOB_TASK WHERE STATE='U' AND STATE_DATE>now()";
	// com.zhcs.billing.quartz.service.JobEngine
	public static String execute_update = "UPDATE T_JOB_TASK SET STATE_DATE=now() WHERE TASK_ID=?";

	// com.zhcs.billing.quartz.service.Mouse
	public static String run_select = "SELECT * FROM T_JOB_TASK WHERE STATE='U' AND TASK_ID = ";

	// com.zhcs.billing.quartz.service.Task
	public static String Task_execute_insert = "INSERT INTO T_TASK_LOG(TASK_ID,REMARKS,START_DATE,STATE) VALUES(? , '执行中' , NOW() , 'R' )";
	// com.zhcs.billing.quartz.service.Task
	public static String Task_execute_update = "UPDATE T_TASK_LOG SET STATE='O',REMARKS='执行完成',FINISH_DATE=NOW() WHERE TASK_LOG_ID=?";
	// com.zhcs.billing.quartz.service.Task
	public static String Task_execute_update_error = "UPDATE T_TASK_LOG SET STATE='E',FINISH_DATE=NOW(),REMARKS=? WHERE TASK_LOG_ID=? ";

	// com.zhcs.billing.use.dao.QueryDao
	// public static String getOrderNumSql =
	// "SELECT ORDERID,SPERIOD,STARTTIME,ENDTIME,VALUE FROM ("
	// + "SELECT ORDERID,SPERIOD,STARTTIME,ENDTIME,VALUE FROM CSERVER"
	// + " UNION "
	// + "SELECT ORDERID,SPERIOD,STARTTIME,ENDTIME,VALUE FROM CNETWORK"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM CSECURITY"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM  CSTORE"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM CDB"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM CMIDSOFT"
	// + " UNION "
	// + "SELECT ORDERID ,SPERIOD,STARTTIME,ENDTIME,VALUE FROM CENGINE) T "
	// +
	// "WHERE STARTTIME < ? <= ENDTIME AND SPERIOD = ? AND VALUE > 0 GROUP BY ORDERID ORDER BY ORDERID";

	public static String getOrderNumSql = "SELECT packid,colltime,speriod FROM ("
			+ "SELECT packid,colltime,speriod FROM CSERVER"
			+ " UNION "
			+ "SELECT packid,colltime,speriod FROM CNETWORK"
			+ " UNION "
			+ "SELECT packid,colltime,speriod FROM CSECURITY"
			+ " UNION "
			+ "SELECT packid,colltime,speriod FROM CSTORE"
			+ " UNION "
			+ "SELECT packid,colltime,speriod FROM CDB"
			+ " UNION "
			+ "SELECT packid,colltime,speriod FROM CMIDSOFT) T "
			+ "WHERE colltime = ? AND SPERIOD = ?";

	// com.zhcs.billing.use.dao.QueryDao
	public static String getOrderNumSql_Months = "SELECT OI.ORDER_ID,OI.CUSTOMER_ID,OI.ORDER_STATUS,OI.PRODUCT_CATEGORY FROM ORDER_INFO OI WHERE OI.PRODUCT_CATEGORY = ? AND OI.ORDER_STATUS = ? GROUP BY OI.ORDER_ID ORDER BY OI.ORDER_ID";

	// com.zhcs.billing.use.dao.QueryDao
	public static String getCustomerId = "SELECT OI.ACCOUNT_CODE FROM ORDER_INFO OI WHERE OI.ORDER_ID = ?";

	// com.zhcs.billing.use.dao.QueryDao
	public static String insertAccountPaySql = "INSERT INTO T_ACCOUNTPAY (ID,TIME_STAMP,BILLING_ID,CUSTOMER_ID,ORDER_ID,AMOUNT,CDR_TYPE,RESULT,DESCRIPTION) VALUES(?,?,?,?,?,?,?,?,?)";

	// com.zhcs.billing.quartz.dao.UsageAccountDao
	public static String Account_SetDataSql = "INSERT INTO T_USAGE_ACCOUNT (ID,ORDER_ID,SCANNING_TIME_POINT,SCANNING_WAY,SERVERS_NO_STATE) VALUES(?,?,?,?,?)";
	// com.zhcs.billing.quartz.dao.UsageAccountDao
	public static String Account_scanningSql = "UPDATE T_USAGE_ACCOUNT SET STATE = ?,COMPLETE_STATE = ? WHERE ID IN (SELECT T.ID FROM(SELECT ID FROM T_USAGE_ACCOUNT WHERE SCANNING_WAY = ? AND SERVERS_NO_STATE = ? AND STATE IS NULL LIMIT 0,?)AS T)";
	// com.zhcs.billing.quartz.dao.UsageAccountDao
	public static String Account_listUsageAccountSql = "SELECT T.CUSTOMER_ID,T.ORDER_ID,T.SCANNING_TIME_POINT,T.SERVERS_NO_STATE FROM T_USAGE_ACCOUNT T WHERE T.SCANNING_WAY = ? AND T.SERVERS_NO_STATE = ? AND T.STATE = ?";
	// com.zhcs.billing.quartz.dao.UsageAccountDao
	public static String Account_updateUsageAccountState = "UPDATE T_USAGE_ACCOUNT SET COMPLETE_STATE = ? WHERE SCANNING_WAY = ? AND STATE = ? AND SERVERS_NO_STATE = ?";
	// com.zhcs.billing.quartz.dao.UsageAccountDao
	public static String Account_deleteDate_delete = "DELETE FROM T_USAGE_ACCOUNT WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
	// //com.zhcs.billing.quartz.dao.UsageAccountDao
	public static String Account_deleteDate_select = "SELECT COUNT(*) NUM FROM T_USAGE_ACCOUNT WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";

	// com.zhcs.billing.quartz.dao.UsageMonthsDao
	public static String Months_SetDataSql = "INSERT INTO T_USAGE_MONTHS (CUSTOMER_ID,ORDER_ID,FIXED_OR_MONTHLY,SCANNING_WAY,SERVERS_NO_STATE,PRODUCT_CATEGORY) VALUES(?,?,?,?,?,?)";
	// com.zhcs.billing.quartz.dao.UsageMonthsDao
	public static String Months_scanningSql = "UPDATE T_USAGE_MONTHS SET STATE = ?,COMPLETE_STATE = ? WHERE ID IN (SELECT T.ID FROM(SELECT ID FROM T_USAGE_MONTHS WHERE SCANNING_WAY = ? AND FIXED_OR_MONTHLY = ? AND SERVERS_NO_STATE = ? AND STATE IS NULL LIMIT 0,?)AS T)";
	// com.zhcs.billing.quartz.dao.UsageMonthsDao
	public static String Months_listUsageMonthsSql = "SELECT T.CUSTOMER_ID,T.ORDER_ID,T.SERVERS_NO_STATE FROM T_USAGE_MONTHS T WHERE T.SCANNING_WAY = ? AND FIXED_OR_MONTHLY = ? AND T.SERVERS_NO_STATE = ? AND T.STATE = ?";
	// com.zhcs.billing.quartz.dao.UsageMonthsDao
	public static String Months_updateUsageMonthsState = "UPDATE T_USAGE_MONTHS SET COMPLETE_STATE = ? WHERE SCANNING_WAY = ? AND FIXED_OR_MONTHLY = ? AND STATE = ? AND SERVERS_NO_STATE = ?";
	// com.zhcs.billing.quartz.dao.UsageMonthsDao
	public static String Months_deleteDate_select = "SELECT COUNT(*) NUM FROM T_USAGE_MONTHS WHERE SCANNING_WAY = ? AND FIXED_OR_MONTHLY = ? AND STATE = ? AND COMPLETE_STATE = ?";
	// com.zhcs.billing.quartz.dao.UsageMonthsDao
	public static String Months_deleteDate_delete = "DELETE FROM T_USAGE_MONTHS WHERE SCANNING_WAY = ? AND FIXED_OR_MONTHLY = ? AND STATE = ? AND COMPLETE_STATE = ?";

	// com.zhcs.billing.quartz.service.UsageAccountSTreatment
	public static int Delayed_STreatment = 1000 * 60 * 60 * 3;
	// com.zhcs.billing.quartz.service.UsageAmountVMTreatment
	public static int Delayed_VMTreatment = 1000 * 60 * 60 * 3 + 1;
	// *****************************************yuqingchao
	// end***********************************

	// *****************************************yangke
	// start*************************************
	// com.zhcs.billing.use.dao
	public static String getCustomerIdByOrderId = "SELECT CUSTOMER_ID FROM ORDER_INFO WHERE ORDER_ID = ?";
	// com.zhcs.billing.use.dao
	public static String getProjectOrderInfo = "SELECT O.PRODUCT_ID FROM PRICE_MODEL P,ORDER_DETAIL O WHERE P.PRODUCT_ID=O.PRODUCT_ID AND O.ORDER_ID=?";
	// com.zhcs.billing.use.dao
	public static String getAppCountsByProductId = "SELECT count(*) COUNTS FROM PRODUCT_RESOURCE WHERE PRODUCT_ID=?";
	// com.zhcs.billing.use.dao
	public static String getItemCodeByProductId = "SELECT ITEM_CODE,RESOURCE_ID FROM PRODUCT_ITEM WHERE PRODUCT_ID=?";
	// com.zhcs.billing.use.dao
	public static String getResourceIdByProductIdAndAppId = "SELECT RESOURCE_ID FROM PRODUCT_RESOURCE WHERE PRODUCT_ID=? AND APP_ID=?";
	// com.zhcs.billing.use.dao
	public static String getItemByResourceIdAndItmeCode = "SELECT ITEM_ID FROM PRODUCT_ITEM WHERE ITEM_CODE=? AND RESOURCE_ID=?";
	// com.zhcs.billing.use.dao
	public static String getSubscribeAmountByItemId = "SELECT SUBSCRIBE_AMOUNT FROM SUBSCRIPTION_ITEM where ITEM_ID=?";
	// com.zhcs.billing.use.dao
	public static String getPriceByItemId = "SELECT PRICE FROM PRODUCT_ITEM where 1=1";
	// com.zhcs.billing.use.dao
	public static String getDimensionsBySerialNo = "SELECT ID,DIMENSIONS,CREATE_DATE FROM T_USAGE_BAND_TOTAL where STATE=? SALE_ID=?";
	// com.zhcs.billing.use.dao
	public static String updateUsageBandTotal = "UPDATE T_USAGE_BAND_TOTAL SET DIMENSIONS = ? AND UPDATE_DATE=? WHERE ID=? ";
	// com.zhcs.billing.use.dao
	public static String getProductType = "select PRODUCT_CATEGORY from PRODUCT_INFO where PRODUCT_ID=?";
	// com.zhcs.billing.use.dao
	// 没用上
	public static String getProductWDByProductNo = "select * from PRODUCT_ITEM where PRODUCT_ID=?";
	// com.zhcs.billing.use.dao
	public static String getProductDetail = "SELECT * FROM PRODUCT_RESOURCE where PRODUCT_ID=?"; // ---
	// com.zhcs.billing.use.dao
	public static String getItemNoByItemCode = "SELECT ITEM_ID FROM PRODUCT_ITEM where ITEM_CODE=?";
	// com.zhcs.billing.use.dao
	public static String getPriceModelByThreeParams = "SELECT MODEL_ID FROM PRICE_MODEL where PRODUCT_ID=? AND RESOURCE_ID=? AND ITEM_ID=?";
	// com.zhcs.billing.use.dao
	public static String getItemCodeByItemNo = "SELECT ITEM_CODE FROM PRODUCT_ITEM where ITEM_ID=?";
	// com.zhcs.billing.use.dao
	public static String getProductWDByResourceNo = "select * from PRODUCT_ITEM where RESOURCE_ID=?"; // ---
	// com.zhcs.billing.use.dao
	public static String getPriceModel = " select MODEL_ID from PRICE_MODEL p where p.PRODUCT_ID=? and p.RESOURCE_ID =? and p.ITEM_ID =?";
	// com.zhcs.billing.quartz.dao
	public static String getUsageMonthly = "SELECT ID FROM T_USAGE_MONTHLY WHERE ORDER_ID=? AND BILLSEQ_ID=?";
	// *****************************************yangke
	// end***************************************
}
