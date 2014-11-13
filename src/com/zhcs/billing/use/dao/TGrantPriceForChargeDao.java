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
	 * ���۷����۽ӿ���ϸ��(����ID,�ӿ�֮��ı�ʶ��,�������,��������,ʱ���,���۽��壩,���۽��,�Դ���/�쳣����ϸ������Ϣ,����ʱ��) ��������
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
    	para.add(id);					//����ID
    	para.add(BillingID);			//�ӿ�֮��ı�ʶ��
    	para.add(OrderID);				//�������
    	para.add(OperationType);		//��������
    	para.add(TimeStamp);			//ʱ���
    	para.add(money);				//���۽��壩
    	para.add(flag);					//���۽��
    	para.add(Description);			//�Դ���/�쳣����ϸ������Ϣ
    	para.add(createDate);//����ʱ��
    	int i=GetBillingBaseDao().doSaveOrUpdate(insertSQL,para);
    	log.info("���۷����۽ӿ���ϸ������� "+i+" ������ ");
	}
}
