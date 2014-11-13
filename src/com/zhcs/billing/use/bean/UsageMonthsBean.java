package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * �ʷ������м��Bean
 * @author yuqingchao
 *
 */
public class UsageMonthsBean {

	private String BILLSEQ_ID;		//����
	private String CUSTOMER_ID;		//�ͻ����
	private Integer FIXED_OR_MONTHLY;	//0������ۣ�1���³���
	private String SCANNING_WAY;	//ɨ�跽ʽ
	private String UNIT_RATE;		//�¿۽��
	private String ORDER_ID;		//�������
	private String PRODUCT_ID;		//��Ʒ���
	private String PRODUCT_TYPE;	//��Ʒ����
	private Integer PRODUCT_CATEGORY;	//��Ʒ���ࣨ1�������� 2��Ӧ���ࣩ
	private String COMPLETE_STATE;	//ɨ��״̬
	private String STATE;			//״̬
	private Integer SERVERS_NO_STATE;//���������״̬
	
	public String getUNIT_RATE() {
		return UNIT_RATE;
	}
	public void setUNIT_RATE(String uNITRATE) {
		UNIT_RATE = uNITRATE;
	}
	public Integer getFIXED_OR_MONTHLY() {
		return FIXED_OR_MONTHLY;
	}
	public void setFIXED_OR_MONTHLY(Integer fIXEDORMONTHLY) {
		FIXED_OR_MONTHLY = fIXEDORMONTHLY;
	}
	public String getCOMPLETE_STATE() {
		return COMPLETE_STATE;
	}
	public void setCOMPLETE_STATE(String cOMPLETESTATE) {
		COMPLETE_STATE = cOMPLETESTATE;
	}
	public String getBILLSEQ_ID() {
		return BILLSEQ_ID;
	}
	public void setBILLSEQ_ID(String bILLSEQID) {
		BILLSEQ_ID = bILLSEQID;
	}
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(String cUSTOMERID) {
		CUSTOMER_ID = cUSTOMERID;
	}
	public String getSCANNING_WAY() {
		return SCANNING_WAY;
	}
	public void setSCANNING_WAY(String sCANNINGWAY) {
		SCANNING_WAY = sCANNINGWAY;
	}
	public String getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
	}
	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(String pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}
	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}
	public void setPRODUCT_TYPE(String pRODUCTTYPE) {
		PRODUCT_TYPE = pRODUCTTYPE;
	}
	public Integer getPRODUCT_CATEGORY() {
		return PRODUCT_CATEGORY;
	}
	public void setPRODUCT_CATEGORY(Integer pRODUCTCATEGORY) {
		PRODUCT_CATEGORY = pRODUCTCATEGORY;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public Integer getSERVERS_NO_STATE() {
		return SERVERS_NO_STATE;
	}
	public void setSERVERS_NO_STATE(Integer sERVERSNOSTATE) {
		SERVERS_NO_STATE = sERVERSNOSTATE;
	}
	
	/**
	 * �����ؽ��ת��UsageMonthsBean�б����
	 * @param list
	 * @return
	 */
	public List<UsageMonthsBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<UsageMonthsBean> l = new ArrayList<UsageMonthsBean>();
		for(HashMap<String,Object> d:list){
			UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
			usageMonthsBean.setBILLSEQ_ID(d.get("BILLSEQ_ID")!=null?d.get("BILLSEQ_ID").toString():null);
			usageMonthsBean.setCUSTOMER_ID(d.get("CUSTOMER_ID")!=null?d.get("CUSTOMER_ID").toString():null);
			usageMonthsBean.setFIXED_OR_MONTHLY(d.get("FIXED_OR_MONTHLY")!=null?Integer.parseInt(d.get("FIXED_OR_MONTHLY").toString()):null);
			usageMonthsBean.setSCANNING_WAY(d.get("SCANNING_WAY")!=null?d.get("SCANNING_WAY").toString():null);
			usageMonthsBean.setUNIT_RATE(d.get("UNIT_RATE")!=null?d.get("UNIT_RATE").toString():null);
			usageMonthsBean.setORDER_ID(d.get("ORDER_ID")!=null?d.get("ORDER_ID").toString():null);
			usageMonthsBean.setPRODUCT_ID(d.get("PRODUCT_ID")!=null?d.get("PRODUCT_ID").toString():null);
			usageMonthsBean.setPRODUCT_TYPE(d.get("PRODUCT_TYPE")!=null?d.get("PRODUCT_TYPE").toString():null);
			usageMonthsBean.setCOMPLETE_STATE(d.get("COMPLETE_STATE")!=null?d.get("COMPLETE_STATE").toString():null);
			usageMonthsBean.setSTATE(d.get("STATE")!=null?d.get("STATE").toString():null);
			usageMonthsBean.setSERVERS_NO_STATE(d.get("SERVERS_NO_STATE")!=null?Integer.parseInt(d.get("SERVERS_NO_STATE").toString()):null);
			usageMonthsBean.setPRODUCT_CATEGORY(d.get("PRODUCT_CATEGORY")!=null?Integer.parseInt(d.get("PRODUCT_CATEGORY").toString()):null);
			l.add(usageMonthsBean);
		}
		return l;
	}
	
}
