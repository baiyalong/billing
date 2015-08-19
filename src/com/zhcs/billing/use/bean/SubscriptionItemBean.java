package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * �깺��ϸ��
 * 
 * @author hefa
 *
 */
public class SubscriptionItemBean {
	public SubscriptionItemBean() {
		// TODO Auto-generated constructor stub
	}

	public SubscriptionItemBean(String sI_ID, String sUBSCRIBER_ID,
			String sR_ID, String iTEM_ID, String pD_ID, String pROVINCE_CODE,
			String aREA_CODEl, String cUSTOMER_ID, String pRODUCT_ID,
			String pRODUCT_TYPE, String aP_ID, String sERVICE_ID,
			String vALID_FROM, String vALID_TILL, Integer sUBSCRIBE_AMOUNT,
			Integer rEMAINING_AMOUNT, String sYNC_FLAG, String dEAL_TIME,
			String dESCRIPTION) {
		super();
		SI_ID = sI_ID;
		SUBSCRIBER_ID = sUBSCRIBER_ID;
		SR_ID = sR_ID;
		ITEM_ID = iTEM_ID;
		PD_ID = pD_ID;
		PROVINCE_CODE = pROVINCE_CODE;
		AREA_CODEl = aREA_CODEl;
		CUSTOMER_ID = cUSTOMER_ID;
		PRODUCT_ID = pRODUCT_ID;
		PRODUCT_TYPE = pRODUCT_TYPE;
		AP_ID = aP_ID;
		SERVICE_ID = sERVICE_ID;
		VALID_FROM = vALID_FROM;
		VALID_TILL = vALID_TILL;
		SUBSCRIBE_AMOUNT = sUBSCRIBE_AMOUNT;
		REMAINING_AMOUNT = rEMAINING_AMOUNT;
		SYNC_FLAG = sYNC_FLAG;
		DEAL_TIME = dEAL_TIME;
		DESCRIPTION = dESCRIPTION;
	}

	private String SI_ID;// �깺��ϸ���
	private String SUBSCRIBER_ID;// �깺���
	private String SR_ID;// �깺��Դ���
	private String ITEM_ID;// ά�ȱ��
	private String PD_ID;// �ײ���ϸ���
	private String PROVINCE_CODE;// ʡ�ݱ���
	private String AREA_CODEl;// ���б���
	private String CUSTOMER_ID;// �ͻ����
	private String PRODUCT_ID;// ��Ʒ���
	private String PRODUCT_TYPE;// ��Ʒ����
	private String AP_ID;// AP���
	private String SERVICE_ID;// ������
	private String VALID_FROM;// ��ʼʱ��
	private String VALID_TILL;// ����ʱ��
	private Integer SUBSCRIBE_AMOUNT;// �깺��
	private Integer REMAINING_AMOUNT;// ����ʣ����
	private String SYNC_FLAG;// ͬ����־
	private String DEAL_TIME;// ����ʱ��
	private String DESCRIPTION;// ����

	public String getSI_ID() {
		return SI_ID;
	}

	public void setSI_ID(String sI_ID) {
		SI_ID = sI_ID;
	}

	public String getSUBSCRIBER_ID() {
		return SUBSCRIBER_ID;
	}

	public void setSUBSCRIBER_ID(String sUBSCRIBER_ID) {
		SUBSCRIBER_ID = sUBSCRIBER_ID;
	}

	public String getSR_ID() {
		return SR_ID;
	}

	public void setSR_ID(String sR_ID) {
		SR_ID = sR_ID;
	}

	public String getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(String iTEM_ID) {
		ITEM_ID = iTEM_ID;
	}

	public String getPD_ID() {
		return PD_ID;
	}

	public void setPD_ID(String pD_ID) {
		PD_ID = pD_ID;
	}

	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}

	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
	}

	public String getAREA_CODEl() {
		return AREA_CODEl;
	}

	public void setAREA_CODEl(String aREA_CODEl) {
		AREA_CODEl = aREA_CODEl;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}

	public void setPRODUCT_TYPE(String pRODUCT_TYPE) {
		PRODUCT_TYPE = pRODUCT_TYPE;
	}

	public String getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(String aP_ID) {
		AP_ID = aP_ID;
	}

	public String getSERVICE_ID() {
		return SERVICE_ID;
	}

	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
	}

	public String getVALID_FROM() {
		return VALID_FROM;
	}

	public void setVALID_FROM(String vALID_FROM) {
		VALID_FROM = vALID_FROM;
	}

	public String getVALID_TILL() {
		return VALID_TILL;
	}

	public void setVALID_TILL(String vALID_TILL) {
		VALID_TILL = vALID_TILL;
	}

	public Integer getSUBSCRIBE_AMOUNT() {
		return SUBSCRIBE_AMOUNT;
	}

	public void setSUBSCRIBE_AMOUNT(Integer sUBSCRIBE_AMOUNT) {
		SUBSCRIBE_AMOUNT = sUBSCRIBE_AMOUNT;
	}

	public Integer getREMAINING_AMOUNT() {
		return REMAINING_AMOUNT;
	}

	public void setREMAINING_AMOUNT(Integer rEMAINING_AMOUNT) {
		REMAINING_AMOUNT = rEMAINING_AMOUNT;
	}

	public String getSYNC_FLAG() {
		return SYNC_FLAG;
	}

	public void setSYNC_FLAG(String sYNC_FLAG) {
		SYNC_FLAG = sYNC_FLAG;
	}

	public String getDEAL_TIME() {
		return DEAL_TIME;
	}

	public void setDEAL_TIME(String dEAL_TIME) {
		DEAL_TIME = dEAL_TIME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public List<SubscriptionItemBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<SubscriptionItemBean> beans = new ArrayList<SubscriptionItemBean>();
		for (HashMap<String, Object> hashMap : list) {
			SubscriptionItemBean bean = new SubscriptionItemBean();
			bean.setITEM_ID(hashMap.get("ITEM_ID") != null ? hashMap.get(
					"ITEM_ID").toString() : null);
			bean.setSI_ID(hashMap.get("SI_ID") != null ? hashMap.get("SI_ID")
					.toString() : null);// �깺��ϸ���
			bean.setSUBSCRIBER_ID(hashMap.get("SUBSCRIBER_ID") != null ? hashMap
					.get("SUBSCRIBER_ID").toString() : null);// �깺���
			bean.setSR_ID(hashMap.get("SR_ID") != null ? hashMap.get("SR_ID")
					.toString() : null);// �깺��Դ���
			bean.setITEM_ID(hashMap.get("ITEM_ID") != null ? hashMap.get(
					"ITEM_ID").toString() : null);// ά�ȱ��
			bean.setPD_ID(hashMap.get("PD_ID") != null ? hashMap.get("PD_ID")
					.toString() : null);// �ײ���ϸ���
			bean.setPROVINCE_CODE(hashMap.get("PROVINCE_CODE") != null ? hashMap
					.get("PROVINCE_CODE").toString() : null);// ʡ�ݱ���
			bean.setAREA_CODEl(hashMap.get("AREA_CODEl") != null ? hashMap.get(
					"AREA_CODEl").toString() : null);// ���б���
			bean.setCUSTOMER_ID(hashMap.get("CUSTOMER_ID") != null ? hashMap
					.get("CUSTOMER_ID").toString() : null);// �ͻ����
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get(
					"PRODUCT_ID").toString() : null);// ��Ʒ���
			bean.setPRODUCT_TYPE(hashMap.get("PRODUCT_TYPE") != null ? hashMap
					.get("PRODUCT_TYPE").toString() : null);// ��Ʒ����
			bean.setAP_ID(hashMap.get("AP_ID") != null ? hashMap.get("AP_ID")
					.toString() : null);// AP���
			bean.setSERVICE_ID(hashMap.get("SERVICE_ID") != null ? hashMap.get(
					"SERVICE_ID").toString() : null);// ������
			bean.setVALID_FROM(hashMap.get("VALID_FROM") != null ? hashMap.get(
					"VALID_FROM").toString() : null);// ��ʼʱ��
			bean.setVALID_TILL(hashMap.get("VALID_TILL") != null ? hashMap.get(
					"VALID_TILL").toString() : null);// ����ʱ��
			bean.setSUBSCRIBE_AMOUNT(hashMap.get("SUBSCRIBE_AMOUNT") != null ? Integer
					.parseInt(hashMap.get("SUBSCRIBE_AMOUNT").toString()) : 0);// �깺��
			bean.setREMAINING_AMOUNT(hashMap.get("REMAINING_AMOUNT") != null ? Integer
					.parseInt(hashMap.get("REMAINING_AMOUNT").toString()) : 0);// ����ʣ����
			bean.setSYNC_FLAG(hashMap.get("SYNC_FLAG") != null ? hashMap.get(
					"SYNC_FLAG").toString() : null);// ͬ����־
			bean.setDEAL_TIME(hashMap.get("DEAL_TIME") != null ? hashMap.get(
					"DEAL_TIME").toString() : null);// ����ʱ��
			bean.setDESCRIPTION(hashMap.get("DESCRIPTION") != null ? hashMap
					.get("DESCRIPTION").toString() : null);// ����

			beans.add(bean);
		}
		return beans;
	}
}
