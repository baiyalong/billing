package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.util.Common;

/**
 * 产品信息表
 * 
 * @author hefa
 * 
 */
public class ProductInfoBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1405468839059118489L;

	public ProductInfoBean() {
		// TODO Auto-generated constructor stub
	}

	public ProductInfoBean(List<ProductResourceBean> productResourceBeans,
			List<ProductItemBean> productItemBeans, String pRODUCT_ID,
			String eXTEND_ID, String gROUP_ID, String pRODUCT_NAME,
			Integer pRODUCT_CATEGORY, String pRODUCT_TYPE,
			String pRODUCT_CODE, Integer pRODUCT_STATUS, String pROVINCE_CODE,
			Date eFFECTIVE_DATE, Date eXPIRE_DATE, Date cREAT_TIME,
			Date mODIFY_TIME, String nEED_CONSTRUCTION_FLAG,
			String mULTIPLE_SUBSCRIBE_FLAG, String iMAGE_URL,
			String dESCRIPTION, String iNDUSTRY_TYPE, String c_TEMPLATE_ID,
			String pROCESS_ID, String rEFERENCE_KEY, String nEED_SYNC,
			String sYNC_FLAG) {
		super();
		this.productResourceBeans = productResourceBeans;
		this.productItemBeans = productItemBeans;
		PRODUCT_ID = pRODUCT_ID;
		EXTEND_ID = eXTEND_ID;
		GROUP_ID = gROUP_ID;
		PRODUCT_NAME = pRODUCT_NAME;
		PRODUCT_CATEGORY = pRODUCT_CATEGORY;
		PRODUCT_TYPE = pRODUCT_TYPE;
		PRODUCT_CODE = pRODUCT_CODE;
		PRODUCT_STATUS = pRODUCT_STATUS;
		PROVINCE_CODE = pROVINCE_CODE;
		EFFECTIVE_DATE = eFFECTIVE_DATE;
		EXPIRE_DATE = eXPIRE_DATE;
		CREAT_TIME = cREAT_TIME;
		MODIFY_TIME = mODIFY_TIME;
		NEED_CONSTRUCTION_FLAG = nEED_CONSTRUCTION_FLAG;
		MULTIPLE_SUBSCRIBE_FLAG = mULTIPLE_SUBSCRIBE_FLAG;
		IMAGE_URL = iMAGE_URL;
		DESCRIPTION = dESCRIPTION;
		INDUSTRY_TYPE = iNDUSTRY_TYPE;
		C_TEMPLATE_ID = c_TEMPLATE_ID;
		PROCESS_ID = pROCESS_ID;
		REFERENCE_KEY = rEFERENCE_KEY;
		NEED_SYNC = nEED_SYNC;
		SYNC_FLAG = sYNC_FLAG;
	}

	private static final Integer dict = 2;// 节点类型

	public static Integer getDict() {
		return dict;
	}

	private List<ProductResourceBean> productResourceBeans = new ArrayList<ProductResourceBean>();// 产品资源
	private List<ProductItemBean> productItemBeans = new ArrayList<ProductItemBean>();// 产品纬度

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

	private String PRODUCT_ID;// 产品编号
	private String PRODUCT_NAME;// 产品名称
	private Integer PRODUCT_CATEGORY;// 产品大类
	private String PRODUCT_TYPE;// 产品类型
	private String PRODUCT_CODE;// 产品编码
	private Integer PRODUCT_STATUS;// 状态
	private String PROVINCE_CODE;// 省份编码
	private Date EFFECTIVE_DATE;// 生效时间
	private Date EXPIRE_DATE;// 失效时间
	private Date CREAT_TIME;// 创建时间
	private Date MODIFY_TIME;// 更新时间
	private String NEED_CONSTRUCTION_FLAG;// 是否需要外部施工
	private String MULTIPLE_SUBSCRIBE_FLAG;// 是否允许重复订购
	private String IMAGE_URL;// 图片链接
	private String DESCRIPTION;// 描述
	private String INDUSTRY_TYPE;// 所属行业
	private String C_TEMPLATE_ID;// 容器模板编号
	private String REFERENCE_KEY;// 备用字段
	private String EXTEND_ID;// 扩展编号
	private String GROUP_ID;// 资源组标识
	private String PROCESS_ID;// 流程编号
	
	private String NEED_SYNC;// 是否需要同步
	private String SYNC_FLAG;// 同步标识

//	private String DISCOUNT_ID;// 优惠策略规则编号
	private double MONEY;// 一次批价
	private double twoMONEY;// 二次批价
	private double SUMARY;// 产品金额(三次批价金额)
	
	public double getSUMARY() {
		return SUMARY;
	}

	public void setSUMARY(double sUMARY) {
		SUMARY = sUMARY;
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

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}

	public String getEXTEND_ID() {
		return EXTEND_ID;
	}

	public void setEXTEND_ID(String eXTENDID) {
		EXTEND_ID = eXTENDID;
	}

	public String getGROUP_ID() {
		return GROUP_ID;
	}

	public void setGROUP_ID(String gROUPID) {
		GROUP_ID = gROUPID;
	}

	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}

	public void setPRODUCT_NAME(String pRODUCTNAME) {
		PRODUCT_NAME = pRODUCTNAME;
	}

	public Integer getPRODUCT_CATEGORY() {
		return PRODUCT_CATEGORY;
	}

	public void setPRODUCT_CATEGORY(Integer pRODUCTCATEGORY) {
		PRODUCT_CATEGORY = pRODUCTCATEGORY;
	}

	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}

	public void setPRODUCT_TYPE(String pRODUCTTYPE) {
		PRODUCT_TYPE = pRODUCTTYPE;
	}

	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}

	public void setPRODUCT_CODE(String pRODUCTCODE) {
		PRODUCT_CODE = pRODUCTCODE;
	}

	public Integer getPRODUCT_STATUS() {
		return PRODUCT_STATUS;
	}

	public void setPRODUCT_STATUS(Integer pRODUCTSTATUS) {
		PRODUCT_STATUS = pRODUCTSTATUS;
	}

	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}

	public void setPROVINCE_CODE(String pROVINCECODE) {
		PROVINCE_CODE = pROVINCECODE;
	}

	public Date getEFFECTIVE_DATE() {
		return EFFECTIVE_DATE;
	}

	public void setEFFECTIVE_DATE(Date eFFECTIVEDATE) {
		EFFECTIVE_DATE = eFFECTIVEDATE;
	}

	public Date getEXPIRE_DATE() {
		return EXPIRE_DATE;
	}

	public void setEXPIRE_DATE(Date eXPIREDATE) {
		EXPIRE_DATE = eXPIREDATE;
	}

	public Date getCREAT_TIME() {
		return CREAT_TIME;
	}

	public void setCREAT_TIME(Date cREATTIME) {
		CREAT_TIME = cREATTIME;
	}

	public Date getMODIFY_TIME() {
		return MODIFY_TIME;
	}

	public void setMODIFY_TIME(Date mODIFYTIME) {
		MODIFY_TIME = mODIFYTIME;
	}

	public String getNEED_CONSTRUCTION_FLAG() {
		return NEED_CONSTRUCTION_FLAG;
	}

	public void setNEED_CONSTRUCTION_FLAG(String nEEDCONSTRUCTIONFLAG) {
		NEED_CONSTRUCTION_FLAG = nEEDCONSTRUCTIONFLAG;
	}

	public String getMULTIPLE_SUBSCRIBE_FLAG() {
		return MULTIPLE_SUBSCRIBE_FLAG;
	}

	public void setMULTIPLE_SUBSCRIBE_FLAG(String mULTIPLESUBSCRIBEFLAG) {
		MULTIPLE_SUBSCRIBE_FLAG = mULTIPLESUBSCRIBEFLAG;
	}

	public String getIMAGE_URL() {
		return IMAGE_URL;
	}

	public void setIMAGE_URL(String iMAGEURL) {
		IMAGE_URL = iMAGEURL;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getINDUSTRY_TYPE() {
		return INDUSTRY_TYPE;
	}

	public void setINDUSTRY_TYPE(String iNDUSTRYTYPE) {
		INDUSTRY_TYPE = iNDUSTRYTYPE;
	}

	public String getC_TEMPLATE_ID() {
		return C_TEMPLATE_ID;
	}

	public void setC_TEMPLATE_ID(String cTEMPLATEID) {
		C_TEMPLATE_ID = cTEMPLATEID;
	}

	public String getPROCESS_ID() {
		return PROCESS_ID;
	}

	public void setPROCESS_ID(String pROCESS_ID) {
		PROCESS_ID = pROCESS_ID;
	}

	public String getREFERENCE_KEY() {
		return REFERENCE_KEY;
	}

	public void setREFERENCE_KEY(String rEFERENCEKEY) {
		REFERENCE_KEY = rEFERENCEKEY;
	}

	public String getNEED_SYNC() {
		return NEED_SYNC;
	}

	public void setNEED_SYNC(String nEEDSYNC) {
		NEED_SYNC = nEEDSYNC;
	}

	public String getSYNC_FLAG() {
		return SYNC_FLAG;
	}

	public void setSYNC_FLAG(String sYNCFLAG) {
		SYNC_FLAG = sYNCFLAG;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<ProductInfoBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<ProductInfoBean> beans = new ArrayList<ProductInfoBean>();
		for (HashMap<String, Object> hashMap : list) {
			ProductInfoBean bean = new ProductInfoBean();
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get("PRODUCT_ID").toString() : null);
			bean.setPRODUCT_NAME(hashMap.get("PRODUCT_NAME") != null ? hashMap.get("PRODUCT_NAME").toString() : null);
			bean.setPRODUCT_CATEGORY(hashMap.get("PRODUCT_CATEGORY") != null ? Integer.parseInt(hashMap.get("PRODUCT_CATEGORY").toString()): null);
			bean.setPRODUCT_TYPE(hashMap.get("PRODUCT_TYPE") != null ? hashMap.get("PRODUCT_TYPE").toString(): null);
			bean.setPRODUCT_CODE(hashMap.get("PRODUCT_CODE") != null ? hashMap.get("PRODUCT_CODE").toString() : null);
			bean.setPRODUCT_STATUS(hashMap.get("PRODUCT_STATUS") != null ? Integer.parseInt(hashMap.get("PRODUCT_STATUS").toString()): null);
			bean.setPROVINCE_CODE(hashMap.get("PROVINCE_CODE") != null ? hashMap.get("PROVINCE_CODE").toString(): null);
			bean.setEFFECTIVE_DATE(hashMap.get("EFFECTIVE_DATE") != null ? Common.StrToDate(hashMap.get("EFFECTIVE_DATE").toString()): null);
			bean.setEXPIRE_DATE(hashMap.get("EXPIRE_DATE") != null ? Common.StrToDate(hashMap.get("EXPIRE_DATE").toString()): null);
			bean.setCREAT_TIME(hashMap.get("CREAT_TIME") != null ? Common.StrToDate(hashMap.get("CREAT_TIME").toString()) : null);
			bean.setMODIFY_TIME(hashMap.get("MODIFY_TIME") != null ? Common.StrToDate(hashMap.get("MODIFY_TIME").toString()): null);
			bean.setNEED_CONSTRUCTION_FLAG(hashMap.get("NEED_CONSTRUCTION_FLAG") != null ? hashMap.get("NEED_CONSTRUCTION_FLAG").toString() : null);
			bean.setMULTIPLE_SUBSCRIBE_FLAG(hashMap.get("MULTIPLE_SUBSCRIBE_FLAG") != null ? hashMap.get("MULTIPLE_SUBSCRIBE_FLAG").toString() : null);
			bean.setIMAGE_URL(hashMap.get("IMAGE_URL") != null ? hashMap.get("IMAGE_URL").toString() : null);
			bean.setDESCRIPTION(hashMap.get("DESCRIPTION") != null ? hashMap.get("DESCRIPTION").toString() : null);
			bean.setINDUSTRY_TYPE(hashMap.get("INDUSTRY_TYPE") != null ? hashMap.get("INDUSTRY_TYPE").toString(): null);
			bean.setC_TEMPLATE_ID(hashMap.get("C_TEMPLATE_ID") != null ? hashMap.get("C_TEMPLATE_ID").toString(): null);
			bean.setREFERENCE_KEY(hashMap.get("REFERENCE_KEY") != null ? hashMap.get("REFERENCE_KEY").toString(): null);
			bean.setEXTEND_ID(hashMap.get("EXTEND_ID") != null ? hashMap.get("EXTEND_ID").toString() : null);
			bean.setGROUP_ID(hashMap.get("GROUP_ID") != null ? hashMap.get("GROUP_ID").toString() : null);
			bean.setPROCESS_ID(hashMap.get("PROCESS_ID") != null ? hashMap.get("PROCESS_ID").toString() : null);
			beans.add(bean);
		}
		return beans;
	}
}
