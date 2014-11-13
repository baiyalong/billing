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
	 * �� ����ȷ�ϱ�(T_USAGE_AFFIRM_TOTAL)�� ��������(����id��������ˮ�š���Ʒ��š�Ӧ�ñ�š��ͻ���š�ά��Ԫ�顢�������ڡ���������)
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
		log.info("�� ����ȷ�ϱ������������ "+i);
		para=null;
	}
	/**
	 * ���������۽ӿ���ϸ��(����id,�ӿ�֮��ı�ʶ��,�ͻ����,��Ʒ���,Ӧ�ñ��,ά��Ԫ��,��չ����,��������,������ˮ��,ʱ���,���۽��,���۽��,�Դ���/�쳣����ϸ������Ϣ,��������) ��������
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
    	para.add(id);							//����id
    	para.add(BillingID);					//�ӿ�֮��ı�ʶ��
    	para.add(CustomerID);					//�ͻ����
    	para.add(ProductID);					//��Ʒ���
    	para.add(AppID);						//Ӧ�ñ��
    	para.add(Dimension);					//ά��Ԫ��
    	para.add(Properties);					//��չ����
    	para.add(OperationType);				//��������
    	para.add(SerialNo);						//������ˮ��
    	para.add(TimeStamp);					//ʱ���
    	para.add(total);						//���۽��
    	para.add(flag);							//���۽��
    	para.add(Description);					//�Դ���/�쳣����ϸ������Ϣ
    	para.add(createDate);		//��������
    	int i = GetBillingBaseDao().doSaveOrUpdate(insertSQL,para);
    	log.info("���������۽ӿ���ϸ������������� "+i);
    	para=null;
	}
	/**
	 * �� ����ʹ������(T_USAGE_BAND_TOTAL)�� ��������(������ˮ�š���Ʒ��š�Ӧ�ñ�š��ͻ���š�ά��Ԫ�顢�������ڡ���������)
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
		log.info("�� ����ʹ�������������� ��"+i);
		params=null;
	}
	
}
