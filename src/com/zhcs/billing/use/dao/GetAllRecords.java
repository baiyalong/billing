package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.use.bean.OrderDetailBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.ProductInfoBean;
import com.zhcs.billing.use.bean.ProductItemBean;
import com.zhcs.billing.use.bean.ProductResourceBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;

/**
 * ��ѯ�����µ����в�Ʒ����������ʹ�õ����в�ѯ������
 * 
 * @author hefa
 * 
 */
public class GetAllRecords {
	private static LoggerUtil log = LoggerUtil.getLogger(GetAllRecords.class);
	private BillingBaseDao billing;
	private BaseDao basedao;

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
	 * ���ݶ�������õ�������ϸ��Ϣ
	 * 
	 * @param orderId
	 */
	public static OrderInfoBean getProjectOrderInfo(OrderInfoBean orderInfoBean) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProjectOrderInfo(orderInfoBean.getORDER_ID(), "");
		OrderDetailBean bean = new OrderDetailBean();
		List<OrderDetailBean> beans = bean.changeToObject(list);// ��object����ת����OrderDetailBean
		for (OrderDetailBean orderDetailBean : beans) {
			List<ProductInfoBean> productInfoBeans = getProductInfo(orderDetailBean
					.getPRODUCT_ID());// ���ݲ�Ʒ��Ų�ѯ��Ʒ��Ϣ
			orderDetailBean.setProductInfoBean(productInfoBeans.get(0));// ����Ʒ���붩����ϸ
		}

		orderInfoBean.setOrderDetailBeans(beans);// ��������ϸ���붩��
		list = null;
		return orderInfoBean;
	}

	/**
	 * ���ݲ�Ʒ��Ų鵽��Ʒ��Ϣ
	 * 
	 * @param productNo
	 */
	public static List<ProductInfoBean> getProductInfo(String productNo) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProductType(productNo);
		ProductInfoBean bean = new ProductInfoBean();
		List<ProductInfoBean> beans = bean.changeToObject(list);// ��object����ת����ProductInfoBean
		for (ProductInfoBean productInfoBean : beans) {
			List<ProductResourceBean> productResourceBeans = getProductResource(productInfoBean
					.getPRODUCT_ID());// ���ݲ�Ʒ��Ų�ѯ��Ʒ��Դ��Ϣ
			productInfoBean.setProductResourceBeans(productResourceBeans);// ����Դ��Ϣ�����Ʒ
		}
		list = null;
		return beans;
	}

	/**
	 * ���ݲ�Ʒ��Ų�ѯ��Ʒ��Դ��Ϣ
	 * 
	 * @param productNo
	 */
	public static List<ProductResourceBean> getProductResource(String productNo) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProductDetail(productNo);
		ProductResourceBean bean = new ProductResourceBean();
		List<ProductResourceBean> beans = bean.changeToObject(list);// ��object����ת����ProductResourceBean
		for (ProductResourceBean productResourceBean : beans) {
			if (productResourceBean.getNODE_TYPE() == 8) {// ��Դ����γ��
				List<ProductItemBean> productItemBeans = getProductItem(productResourceBean
						.getRESOURCE_ID());
				productResourceBean.setProductItemBeans(productItemBeans);// ��γ����Ϣ������Դ
			}
		}
		list = null;
		return beans;
	}

	/**
	 * ������Դ��Ų�ѯ��Ʒγ����Ϣ
	 * 
	 * @param productNo
	 * @param RESOURCE_ID
	 * @return
	 */
	public static List<ProductItemBean> getProductItem(String resourceNo) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProductWDByResourceNo(resourceNo);
		ProductItemBean bean = new ProductItemBean();
		return bean.changeToObject(list);// ��object����ת����ProductResourceBean
	}

	/**
	 * ���ݶ�����Ų�ѯ ������
	 * 
	 * @param OrderID
	 * @return
	 */
	public List<HashMap<String, Object>> getCustomerIdByOrderId(String OrderID) {
		// String custSQL = "SELECT * FROM ORDER_INFO WHERE ORDER_ID = ?";
		String custSQL = VariableConfigManager.getCustomerIdByOrderId;
		List<String> cust = new ArrayList<String>();
		cust.add(OrderID);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(custSQL,
				cust);
		return list;
	}

	/**
	 * ���ݶ�����Ų�ѯ������ϸ��
	 * 
	 * @param orderId
	 * @return
	 */
	public List<HashMap<String, Object>> getProjectOrderInfo(String orderId,
			String OperationType) {
		// TODO Auto-generated method stub
		// String sql =
		// "SELECT O.PRODUCT_ID FROM PRICE_MODEL P,ORDER_DETAIL O WHERE P.PRODUCT_ID=O.PRODUCT_ID AND O.ORDER_ID=?";
		String sql = VariableConfigManager.getProjectOrderInfo;
		List<String> params = new ArrayList<String>();
		params.add(orderId);
		if (!"".equals(OperationType)) {
			sql += " and RULE_ID=?";
			params.add(OperationType);
		}
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, params);

		return list;
	}

	/**
	 * ���ݲ�Ʒ��� ��ѯ ��Ʒ��Դ��Ϣ�� �õ� ��ǰ��Ʒ�µ�Ӧ�ø���
	 * 
	 * @param productNo
	 * @return
	 */
	public List<HashMap<String, Object>> getAppCountsByProductId(
			String productId) {
		String appSQL = VariableConfigManager.getAppCountsByProductId;
		List<String> appparam = new ArrayList<String>();
		appparam.add(productId);
		return GetBaseDao().doSelect(appSQL, appparam);
	}

	/**
	 * ���ݲ�Ʒ��� ��ѯ �ʷ�ģ�ͱ�
	 * 
	 * @param productId
	 * @return
	 */
	public List<HashMap<String, Object>> getItemCodeByProductId(String productId) {
		String sql = VariableConfigManager.getItemCodeByProductId;
		List<String> params = new ArrayList<String>();
		params.add(productId);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, params);
		params = null;
		return list;
	}

	/**
	 * ���� ��Ʒ��š�Ӧ�ñ�� �� ��Ʒ��Դ��Ϣ�� �еõ� ��Դ���
	 * 
	 * @param ProductId
	 * @param AppId
	 * @return
	 */
	public List<HashMap<String, Object>> getResourceIdByProductIdAndAppId(
			String ProductId, String AppId) {
		String resourceSQL = VariableConfigManager.getResourceIdByProductIdAndAppId;
		List<String> resourceParam = new ArrayList<String>();
		resourceParam.add(ProductId);
		resourceParam.add(AppId);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(resourceSQL,
				resourceParam);
		resourceParam = null;
		return list;
	}

	/**
	 * ���� ��Դ��š�ά�ȱ��� �� ��Ʒά�ȱ��� �õ� ά�ȱ��
	 * 
	 * @param ResourceId
	 * @param ItmeCode
	 * @return
	 */
	public List<HashMap<String, Object>> getItemByResourceIdAndItmeCode(
			String resourceId, String itmeCode) {
		String itemSQL = VariableConfigManager.getItemByResourceIdAndItmeCode;
		List<String> itemParam = new ArrayList<String>();
		itemParam.add(itmeCode);
		itemParam.add(resourceId);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(itemSQL,
				itemParam);
		itemParam = null;
		return list;
	}

	/**
	 * ���� ά�ȱ�� ���깺��ϸ�� �õ� �깺��
	 * 
	 * @param itemId
	 * @return
	 */
	public List<HashMap<String, Object>> getSubscribeAmountByItemId(
			String itemId) {
		String subscribe = VariableConfigManager.getSubscribeAmountByItemId;
		List<String> par = new ArrayList<String>();
		par.add(itemId);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(subscribe,
				par);
		par = null;
		return list;
	}

	/**
	 * ����ά�ȱ�� �� ��Դ��� ȥ��Ʒά�ȱ�PRODUCT_ITEM������ȥ��ȡ���ά�ȵ��ۣ�PRICE����Ϣ
	 * 
	 * @param itemId
	 * @return
	 */
	public List<HashMap<String, Object>> getPriceByItemId(String itemId,
			String resourceId) {
		String sql = VariableConfigManager.getPriceByItemId;
		List<String> param = new ArrayList<String>();
		if (!"".equals(itemId)) {
			sql += " AND ITEM_ID=? ";
			param.add(itemId);
		}
		if (!"".equals(resourceId)) {
			sql += " AND RESOURCE_ID=?";
			param.add(resourceId);
		}
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, param);
		param = null;
		return list;
	}

	/*
	 * public List<HashMap<String, Object>> getStateBySerialNo(String SerialNo){
	 * String sql = ""; }
	 */

	/**
	 * ���ݽ�����ˮ�Ų�ѯ ά��ʹ����
	 */
	public List<HashMap<String, Object>> getDimensionsBySerialNo(
			String SerialNo, String state) {
		// String
		// sql="SELECT * FROM T_USAGE_BAND_TOTAL where STATE=? SALE_ID=?";
		String sql = VariableConfigManager.getDimensionsBySerialNo;
		List<String> params = new ArrayList<String>();
		params.add(state);
		params.add(SerialNo);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, params);
		params = null;
		return list;
	}

	/**
	 * �޸�����ʹ������ ����״̬Ϊ�ѽ�� ��ά��Ԫ��
	 * 
	 * @param id
	 * @param dimension
	 * @throws Exception
	 */
	public void updateUsageBandTotal(String id, String dimension,
			String updateDate) {
		// String sql =
		// "UPDATE T_USAGE_BAND_TOTAL SET DIMENSIONS = ? AND UPDATE_DATE=? WHERE ID=? ";
		String sql = VariableConfigManager.updateUsageBandTotal;
		List<String> param = new ArrayList<String>();
		param.add(dimension);
		param.add(updateDate);
		param.add(id);
		int i;
		try {
			i = GetBillingBaseDao().doSaveOrUpdate(sql, param);
			if (i > 0) {
				log.info("�޸�����ʹ�������� ����״̬Ϊ�ѽ�� ��ά��Ԫ�� �ɹ� �� " + i + "��");
			} else {
				log.info("�޸�����ʹ�������� ����״̬Ϊ�ѽ�� ��ά��Ԫ�� ��");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("�޸�����ʹ�������� ����״̬Ϊ�ѽ�� ��ά��Ԫ������쳣 ԭ��" + e.getMessage());
		}
		param = null;
	}

	public List<HashMap<String, Object>> getProductType(String productNo) {
		// TODO Auto-generated method stub
		// String sql="select * from PRODUCT_INFO where PRODUCT_ID=?";
		String sql = VariableConfigManager.getProductType;
		List<String> params = new ArrayList<String>();
		params.add(productNo);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, params);
		params = null;
		return list;
	}

	// �˷���û����
	public List<HashMap<String, Object>> getProductWDByProductNo(
			String productNo) {
		// TODO Auto-generated method stub
		// String sql="select * from PRODUCT_ITEM where PRODUCT_ID=?";
		String sql = VariableConfigManager.getProductWDByProductNo;
		List<String> params = new ArrayList<String>();
		params.add(productNo);
		return GetBaseDao().doSelect(sql, params);
	}

	public List<HashMap<String, Object>> getProductDetail(String productNo) {
		// TODO Auto-generated method stub
		// String sql="SELECT * FROM PRODUCT_RESOURCE where PRODUCT_ID=?";
		String sql = VariableConfigManager.getProductDetail;
		List<String> params = new ArrayList<String>();
		params.add(productNo);
		return GetBaseDao().doSelect(sql, params);
	}

	public List<HashMap<String, Object>> getItemNoByItemCode(String itemCode) {
		// TODO Auto-generated method stub
		// String sql="SELECT * FROM PRODUCT_ITEM where ITEM_CODE=?";
		String sql = VariableConfigManager.getItemNoByItemCode;
		List<String> params = new ArrayList<String>();
		params.add(itemCode);
		return GetBaseDao().doSelect(sql, params);
	}

	public List<HashMap<String, Object>> getPriceModelByThreeParams(
			String productId, String resourceId, String itemId) {
		// String sql =
		// "SELECT * FROM PRICE_MODEL where PRODUCT_ID=? AND RESOURCE_ID=? AND ITEM_ID=?";
		String sql = VariableConfigManager.getPriceModelByThreeParams;
		List<String> params = new ArrayList<String>();
		params.add(productId);
		params.add(resourceId);
		params.add(itemId);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, params);
		params = null;
		return list;
	}

	public List<HashMap<String, Object>> getItemCodeByItemNo(String itemNo) {
		// TODO Auto-generated method stub
		// String sql="SELECT * FROM PRICE_MODEL where ITEM_ID=?";
		String sql = VariableConfigManager.getItemCodeByItemNo;
		List<String> params = new ArrayList<String>();
		params.add(itemNo);
		List<HashMap<String, Object>> list = GetBaseDao().doSelect(sql, params);

		return list;
	}

	public List<HashMap<String, Object>> getProductWDByResourceNo(
			String resourceNo) {
		// String sql="select * from PRODUCT_ITEM where RESOURCE_ID=?";
		String sql = VariableConfigManager.getProductWDByResourceNo;
		List<String> params = new ArrayList<String>();
		params.add(resourceNo);
		return GetBaseDao().doSelect(sql, params);
	}

	public String getPriceModel(ProductItemBean bean) {
		String MODEL_ID = null;
		// String sql =
		// " select * from PRICE_MODEL p where p.PRODUCT_ID=? and p.RESOURCE_ID =? and p.ITEM_ID =?";
		String sql = VariableConfigManager.getPriceModel;
		List<String> params = new ArrayList<String>();
		params.add(bean.getPRODUCT_ID());
		params.add(bean.getRESOURCE_ID());
		params.add(bean.getITEM_ID());
		List<HashMap<String, Object>> LockDimenList = GetBaseDao().doSelect(
				sql, params);
		for (HashMap<String, Object> map : LockDimenList) {
			MODEL_ID = (String) map.get("MODEL_ID");
		}
		LockDimenList = null;
		params = null;
		return MODEL_ID;
	}

}
