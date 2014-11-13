package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.List;

import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;
/**
 * 
 * @author hugo
 *
 */
public class TGrantPriceForChargeDao {
	private static LoggerUtil log=LoggerUtil.getLogger(TGrantPriceForChargeDao.class);
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
	 * 往扣费批价接口详细表(主键ID,接口之间的标识符,订单编号,操作类型,时间戳,批价金额（厘）,批价结果,对错误/异常的详细描述信息,创建时间) 插入数据
	 * @param id
	 * @param BillingID
	 * @param OrderID
	 * @param OperationType
	 * @param TimeStamp
	 * @param money
	 * @param flag
	 * @param Description
	 * @param createDate
	 * @throws Exception
	 */
	public void insertTGrantPriceForcharge(String id,String BillingID,String OrderID,String OperationType,String TimeStamp,String money,String flag,String Description,String createDate) throws Exception{
		String insertSQL="insert into T_GRANT_PRICE_FORCHARGE values(?,?,?,?,?,?,?,?,?)";
    	List<String> para=new ArrayList<String>();
    	para.add(id);					//主键ID
    	para.add(BillingID);			//接口之间的标识符
    	para.add(OrderID);				//订单编号
    	para.add(OperationType);		//操作类型
    	para.add(TimeStamp);			//时间戳
    	para.add(money);				//批价金额（厘）
    	para.add(flag);					//批价结果
    	para.add(Description);			//对错误/异常的详细描述信息
    	para.add(createDate);//创建时间
    	int i=GetBillingBaseDao().doSaveOrUpdate(insertSQL,para);
    	log.info("往扣费批价接口详细表插入了 "+i+" 条数据 ");
	}
}
