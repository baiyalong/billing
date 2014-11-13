package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 产品资源信息表
 * 
 * @author hefa
 * 
 */
public class ProductResourceBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1849570817721077668L;

	public ProductResourceBean() {

	}

	public ProductResourceBean(List<ProductResourceBean> productResourceBeans,
			List<ProductItemBean> productItemBeans, String rESOURCE_ID,
			String pRODUCT_ID, String aPP_ID, String rESOURCE_NAME,
			String nODE_CODE, Integer nODE_TYPE, String c_RESOURCE_ID,
			String pARENT_ID, Integer rESOURCE_STATUS, String dEFAULT_VALUE,
			String mAX_VALUE, String iS_AUTO_SIZE) {
		super();
		this.productResourceBeans = productResourceBeans;
		this.productItemBeans = productItemBeans;
		RESOURCE_ID = rESOURCE_ID;
		PRODUCT_ID = pRODUCT_ID;
		APP_ID = aPP_ID;
		RESOURCE_NAME = rESOURCE_NAME;
		NODE_CODE = nODE_CODE;
		NODE_TYPE = nODE_TYPE;
		C_RESOURCE_ID = c_RESOURCE_ID;
		PARENT_ID = pARENT_ID;
		RESOURCE_STATUS = rESOURCE_STATUS;
		DEFAULT_VALUE = dEFAULT_VALUE;
		MAX_VALUE = mAX_VALUE;
		IS_AUTO_SIZE = iS_AUTO_SIZE;
	}



	private List<ProductResourceBean> productResourceBeans = new ArrayList<ProductResourceBean>();// 子资源

	private List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();// 纬度

	private Integer dict;//节点类型
	
	public Integer getDict() {
		return dict;
	}

	public void setDict(Integer dict) {
		this.dict = dict;
	}

	public List<ProductResourceBean> getProductResourceBeans() {
		return productResourceBeans;
	}

	public void setProductResourceBeans(
			List<ProductResourceBean> productResourceBeans) {
		this.productResourceBeans = productResourceBeans;
	}

	public List<ProductItemBean> getProductItemBeans() {
		return productItemBeans;
	}

	public void setProductItemBeans(List<ProductItemBean> productItemBeans) {
		this.productItemBeans = productItemBeans;
	}

	private String RESOURCE_ID;// 资源编号
	private String PRODUCT_ID;// 产品编号
	private String APP_ID;// 应用编号
	private String RESOURCE_NAME;//资源名称
	private String NODE_CODE;//节点编码
	private Integer NODE_TYPE;// 节点类型
	private String C_RESOURCE_ID;// 模板资源编号
	private String PARENT_ID;// 父资源编号
	private Integer RESOURCE_STATUS;// 状态
	private String DEFAULT_VALUE;//默认值
	private String MAX_VALUE;//最大值
	private String IS_AUTO_SIZE;//是否自行调整
	
	private String container_id;//计量中相对应的容器资源编号
	private Integer rank;// 级别
	private String MODEL_ID;//优惠模型
	
	private double MONEY;//一次批价
	private double twoMONEY;//二次批价
	private double threeMONEY;//三次批价

	
	public String getMODEL_ID() {
		return MODEL_ID;
	}

	public void setMODEL_ID(String mODEL_ID) {
		MODEL_ID = mODEL_ID;
	}


	public String getRESOURCE_ID() {
		return RESOURCE_ID;
	}

	public void setRESOURCE_ID(String rESOURCEID) {
		RESOURCE_ID = rESOURCEID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}

	public String getAPP_ID() {
		return APP_ID;
	}

	public void setAPP_ID(String aPPID) {
		APP_ID = aPPID;
	}

	public String getRESOURCE_NAME() {
		return RESOURCE_NAME;
	}

	public void setRESOURCE_NAME(String rESOURCENAME) {
		RESOURCE_NAME = rESOURCENAME;
	}

	public String getC_RESOURCE_ID() {
		return C_RESOURCE_ID;
	}

	public void setC_RESOURCE_ID(String cRESOURCEID) {
		C_RESOURCE_ID = cRESOURCEID;
	}

	public Integer getNODE_TYPE() {
		return NODE_TYPE;
	}

	public void setNODE_TYPE(Integer nODETYPE) {
		NODE_TYPE = nODETYPE;
	}


	public String getPARENT_ID() {
		return PARENT_ID;
	}

	public void setPARENT_ID(String pARENTID) {
		PARENT_ID = pARENTID;
	}

	public Integer getRESOURCE_STATUS() {
		return RESOURCE_STATUS;
	}

	public void setRESOURCE_STATUS(Integer rESOURCESTATUS) {
		RESOURCE_STATUS = rESOURCESTATUS;
	}
	
	public String getContainer_id() {
		return container_id;
	}

	public void setContainer_id(String container_id) {
		this.container_id = container_id;
	}
	

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getNODE_CODE() {
		return NODE_CODE;
	}

	public void setNODE_CODE(String nODE_CODE) {
		NODE_CODE = nODE_CODE;
	}

	public String getDEFAULT_VALUE() {
		return DEFAULT_VALUE;
	}

	public void setDEFAULT_VALUE(String dEFAULT_VALUE) {
		DEFAULT_VALUE = dEFAULT_VALUE;
	}

	public String getMAX_VALUE() {
		return MAX_VALUE;
	}

	public void setMAX_VALUE(String mAX_VALUE) {
		MAX_VALUE = mAX_VALUE;
	}

	public String getIS_AUTO_SIZE() {
		return IS_AUTO_SIZE;
	}

	public void setIS_AUTO_SIZE(String iS_AUTO_SIZE) {
		IS_AUTO_SIZE = iS_AUTO_SIZE;
	}

	public double getMONEY() {
		return MONEY;
	}

	public void setMONEY(double mONEY) {
		MONEY = mONEY;
	}

	public double getTwoMONEY() {
		return twoMONEY;
	}

	public void setTwoMONEY(double twoMONEY) {
		this.twoMONEY = twoMONEY;
	}

	public double getThreeMONEY() {
		return threeMONEY;
	}

	public void setThreeMONEY(double threeMONEY) {
		this.threeMONEY = threeMONEY;
	}

	public List<ProductResourceBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<ProductResourceBean> beans = new ArrayList<ProductResourceBean>();
		for (HashMap<String, Object> hashMap : list) {
			ProductResourceBean bean = new ProductResourceBean();
			bean.setRESOURCE_ID(hashMap.get("RESOURCE_ID") != null ? hashMap.get("RESOURCE_ID").toString() : null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get("PRODUCT_ID").toString() : null);
			bean.setAPP_ID(hashMap.get("APP_ID") != null ? hashMap.get("APP_ID").toString() : null);
			bean.setRESOURCE_NAME(hashMap.get("RESOURCE_NAME") != null ? hashMap.get("RESOURCE_NAME").toString(): null);
			bean.setNODE_CODE(hashMap.get("NODE_CODE") != null ? hashMap.get("NODE_CODE").toString(): null);
			bean.setNODE_TYPE(hashMap.get("NODE_TYPE") != null ? Integer.parseInt(hashMap.get("NODE_TYPE").toString()) : null);
			bean.setC_RESOURCE_ID(hashMap.get("C_RESOURCE_ID") != null ? hashMap.get("C_RESOURCE_ID").toString(): null);
			bean.setPARENT_ID(hashMap.get("PARENT_ID") != null ? hashMap.get("PARENT_ID").toString() : null);
			bean.setRESOURCE_STATUS(hashMap.get("RESOURCE_STATUS") != null ? Integer.parseInt(hashMap.get("RESOURCE_STATUS").toString()): null);
			bean.setDEFAULT_VALUE(hashMap.get("DEFAULT_VALUE") != null ? hashMap.get("DEFAULT_VALUE").toString(): null);
			bean.setMAX_VALUE(hashMap.get("MAX_VALUE") != null ? hashMap.get("MAX_VALUE").toString(): null);
			bean.setIS_AUTO_SIZE(hashMap.get("IS_AUTO_SIZE") != null ? hashMap.get("IS_AUTO_SIZE").toString(): null);
			beans.add(bean);
		}
		return beans;

	}
}
