package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.List;

import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;

public class TGrantPriceForlockDao {
	
	private static LoggerUtil log=LoggerUtil.getLogger(TGrantPriceForlockDao.class);
	private BaseDao basedao;
	private BillingBaseDao billing;
	
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
	 * 向 锁定确认表(T_USAGE_AFFIRM_TOTAL)表 插入数据(主键id、交易流水号、产品编号、应用编号、客户编号、维度元组、创建日期、更新日期)
	 * @param id
	 * @param SerialNo
	 * @param ProductID
	 * @param AppID
	 * @param CustomerID
	 * @param dimensionJSON
	 * @param createDate
	 * @param updateDate
	 * @throws Exception
	 */
	public void insertTUsageAffirmTotal(String id,String SerialNo,String ProductID,String AppID,String CustomerID,String dimensionJSON,String createDate,String updateDate) throws Exception{
		String insql="insert into T_USAGE_AFFIRM_TOTAL values(?,?,?,?,?,?,?,?)";
		List<String> para=new ArrayList<String>();
		para.add(id);
		para.add(SerialNo);
		para.add(ProductID);
		para.add(AppID);
		para.add(CustomerID);
		para.add(dimensionJSON);
		para.add(createDate);
		para.add(updateDate);
		int i = GetBillingBaseDao().doSaveOrUpdate(insql,para);
		log.info("向 锁定确认表插入数据条数 "+i);
		para=null;
	}
	/**
	 * 往锁定批价接口详细表(主键id,接口之间的标识符,客户编号,产品编号,应用编号,维度元组,扩展属性,操作类型,交易流水号,时间戳,批价金额,批价结果,对错误/异常的详细描述信息,创建日期) 插入数据
	 * @param id
	 * @param BillingID
	 * @param CustomerID
	 * @param ProductID
	 * @param AppID
	 * @param Dimension
	 * @param Properties
	 * @param OperationType
	 * @param SerialNo
	 * @param TimeStamp
	 * @param total
	 * @param flag
	 * @param Description
	 * @param createDate
	 * @throws Exception
	 */
	public void insertTGrantPriceForlock(String id,String BillingID,String CustomerID,String ProductID,String AppID,String Dimension,String Properties,String OperationType,String SerialNo,String TimeStamp,String total,String flag,String Description,String createDate) throws Exception{
		String insertSQL="insert into T_GRANT_PRICE_FORLOCK values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	List<String> para=new ArrayList<String>();
    	para.add(id);							//主键id
    	para.add(BillingID);					//接口之间的标识符
    	para.add(CustomerID);					//客户编号
    	para.add(ProductID);					//产品编号
    	para.add(AppID);						//应用编号
    	para.add(Dimension);					//维度元组
    	para.add(Properties);					//扩展属性
    	para.add(OperationType);				//操作类型
    	para.add(SerialNo);						//交易流水号
    	para.add(TimeStamp);					//时间戳
    	para.add(total);						//批价金额
    	para.add(flag);							//批价结果
    	para.add(Description);					//对错误/异常的详细描述信息
    	para.add(createDate);		//创建日期
    	int i = GetBillingBaseDao().doSaveOrUpdate(insertSQL,para);
    	log.info("往锁定批价接口详细表插入数据条数 "+i);
    	para=null;
	}
	/**
	 * 向 锁定使用量表(T_USAGE_BAND_TOTAL)表 插入数据(交易流水号、产品编号、应用编号、客户编号、维度元组、创建日期、更新日期)
	 * @param id
	 * @param SerialNo
	 * @param ProductID
	 * @param AppID
	 * @param CustomerID
	 * @param dimensionJSON
	 * @param createDate
	 * @param updateDate
	 * @throws Exception
	 */
	public void insertTUsageBandTotal(String id,String SerialNo,String ProductID,String AppID,String CustomerID,String dimensionJSON,String createDate,String updateDate) throws Exception{
		String insql="insert into T_USAGE_BAND_TOTAL values(?,?,?,?,?,?,?,?)";
		List<String> params=new ArrayList<String>();
		params.add(id);
		params.add(SerialNo);
		params.add(ProductID);
		params.add(AppID);
		params.add(CustomerID);
		params.add(dimensionJSON);
		params.add(createDate);
		params.add(updateDate);
		int i = GetBillingBaseDao().doSaveOrUpdate(insql,params);
		log.info("向 锁定使用量表数据条数 ："+i);
		params=null;
	}
	
}
