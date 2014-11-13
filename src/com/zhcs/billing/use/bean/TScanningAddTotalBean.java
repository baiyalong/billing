package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.util.Common;

/**
 * �������Ʒɨ���ۻ�����
 * @author hefa
 *
 */
public class TScanningAddTotalBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4525621482454703992L;
	private String ID;// ����id
	private String ORDER_ID;// �������
	private String PRODUCT_ID;// ��Ʒ���
	private String RESOURCE_ID;// ��Դ���
	private String WD_NAME;// ά������
	private String WD_NO;// ά�ȱ���
	private int WD_ADD_TOTAL;//ά���ۻ���
	private int CURRENT_ADD_TOTAL;// ά�ȴ˴�ʹ����
	private String TIME_SPERIOD;//ʱ����
	private Date START_TIME;// ��ʼʱ��
	private Date END_TIME;// ����ʱ��
	private Date CREATE_TIME;// ��������
	private Date UPDATE_TIME;// ��������
	private String SCANNING_WAY;// ɨ�跽ʽ
	public TScanningAddTotalBean() {
		// TODO Auto-generated constructor stub
	}
	
	public TScanningAddTotalBean(String iD, String oRDER_ID, String pRODUCT_ID,
			String rESOURCE_ID, String wD_NAME, String wD_NO, int wD_ADD_TOTAL,
			int cURRENT_ADD_TOTAL, String tIME_SPERIOD, Date sTART_TIME,
			Date eND_TIME, Date cREATE_TIME, Date uPDATE_TIME,
			String sCANNING_WAY) {
		super();
		ID = iD;
		ORDER_ID = oRDER_ID;
		PRODUCT_ID = pRODUCT_ID;
		RESOURCE_ID = rESOURCE_ID;
		WD_NAME = wD_NAME;
		WD_NO = wD_NO;
		WD_ADD_TOTAL = wD_ADD_TOTAL;
		CURRENT_ADD_TOTAL = cURRENT_ADD_TOTAL;
		TIME_SPERIOD = tIME_SPERIOD;
		START_TIME = sTART_TIME;
		END_TIME = eND_TIME;
		CREATE_TIME = cREATE_TIME;
		UPDATE_TIME = uPDATE_TIME;
		SCANNING_WAY = sCANNING_WAY;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getRESOURCE_ID() {
		return RESOURCE_ID;
	}

	public void setRESOURCE_ID(String rESOURCE_ID) {
		RESOURCE_ID = rESOURCE_ID;
	}

	public String getWD_NAME() {
		return WD_NAME;
	}

	public void setWD_NAME(String wD_NAME) {
		WD_NAME = wD_NAME;
	}

	public String getWD_NO() {
		return WD_NO;
	}

	public void setWD_NO(String wD_NO) {
		WD_NO = wD_NO;
	}

	public int getWD_ADD_TOTAL() {
		return WD_ADD_TOTAL;
	}

	public void setWD_ADD_TOTAL(int wD_ADD_TOTAL) {
		WD_ADD_TOTAL = wD_ADD_TOTAL;
	}

	public int getCURRENT_ADD_TOTAL() {
		return CURRENT_ADD_TOTAL;
	}

	public void setCURRENT_ADD_TOTAL(int cURRENT_ADD_TOTAL) {
		CURRENT_ADD_TOTAL = cURRENT_ADD_TOTAL;
	}

	public String getTIME_SPERIOD() {
		return TIME_SPERIOD;
	}

	public void setTIME_SPERIOD(String tIME_SPERIOD) {
		TIME_SPERIOD = tIME_SPERIOD;
	}

	public Date getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(Date sTART_TIME) {
		START_TIME = sTART_TIME;
	}

	public Date getEND_TIME() {
		return END_TIME;
	}

	public void setEND_TIME(Date eND_TIME) {
		END_TIME = eND_TIME;
	}

	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

	public String getSCANNING_WAY() {
		return SCANNING_WAY;
	}

	public void setSCANNING_WAY(String sCANNING_WAY) {
		SCANNING_WAY = sCANNING_WAY;
	}

	public List<TScanningAddTotalBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<TScanningAddTotalBean> beans = new ArrayList<TScanningAddTotalBean>();
		for (HashMap<String, Object> hashMap : list) {
			TScanningAddTotalBean bean = new TScanningAddTotalBean();
			bean.setID(hashMap.get("ID") != null ? hashMap.get("ID").toString() : null);
			bean.setORDER_ID(hashMap.get("ORDER_ID") != null ? hashMap.get("ORDER_ID").toString() : null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get("PRODUCT_ID").toString() : null);
			bean.setRESOURCE_ID(hashMap.get("RESOURCE_ID") != null ? hashMap.get("RESOURCE_ID").toString() : null);
			bean.setWD_NAME(hashMap.get("WD_NAME") != null ? hashMap.get("WD_NAME").toString() : null);
			bean.setWD_NO(hashMap.get("WD_NO") != null ? hashMap.get("WD_NO").toString() : null);
			bean.setWD_ADD_TOTAL(hashMap.get("WD_ADD_TOTAL") != null ? Integer.parseInt(hashMap.get("WD_ADD_TOTAL").toString()) : null);
			bean.setCURRENT_ADD_TOTAL(hashMap.get("CURRENT_ADD_TOTAL") != null ? Integer.parseInt(hashMap.get("CURRENT_ADD_TOTAL").toString()) : null);
			bean.setTIME_SPERIOD(hashMap.get("TIME_SPERIOD") != null ? hashMap.get("TIME_SPERIOD").toString() : null);
			bean.setSCANNING_WAY(hashMap.get("SCANNING_WAY") != null ? hashMap.get("SCANNING_WAY").toString() : null);
			bean.setSTART_TIME(hashMap.get("START_TIME") != null ? Common.StrToDate(hashMap.get("START_TIME").toString()) : null);
			bean.setEND_TIME(hashMap.get("END_TIME") != null ? Common.StrToDate(hashMap.get("END_TIME").toString()) : null);
			bean.setCREATE_TIME(hashMap.get("CREATE_TIME") != null ? Common.StrToDate(hashMap.get("CREATE_TIME").toString()) : null);
			bean.setUPDATE_TIME(hashMap.get("UPDATE_TIME") != null ? Common.StrToDate(hashMap.get("UPDATE_TIME").toString()) : null);
			beans.add(bean);
		}
		
		return beans;
	}

}
