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
 * 查询订单下的所有产品（供订单树使用的所有查询方法）
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
	 * 根据订单编号拿到订单详细信息
	 * 
	 * @param orderId
	 */
	public static OrderInfoBean getProjectOrderInfo(OrderInfoBean orderInfoBean) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProjectOrderInfo(orderInfoBean.getORDER_ID(), "");
		OrderDetailBean bean = new OrderDetailBean();
		List<OrderDetailBean> beans = bean.changeToObject(list);// 将object对象转换成OrderDetailBean
		for (OrderDetailBean orderDetailBean : beans) {
			List<ProductInfoBean> productInfoBeans = getProductInfo(orderDetailBean
					.getPRODUCT_ID());// 根据产品编号查询产品信息
			orderDetailBean.setProductInfoBean(productInfoBeans.get(0));// 将产品放入订单详细
		}

		orderInfoBean.setOrderDetailBeans(beans);// 将订单详细放入订单
		list = null;
		return orderInfoBean;
	}

	/**
	 * 根据产品编号查到产品信息
	 * 
	 * @param productNo
	 */
	public static List<ProductInfoBean> getProductInfo(String productNo) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProductType(productNo);
		ProductInfoBean bean = new ProductInfoBean();
		List<ProductInfoBean> beans = bean.changeToObject(list);// 将object对象转换成ProductInfoBean
		for (ProductInfoBean productInfoBean : beans) {
			List<ProductResourceBean> productResourceBeans = getProductResource(productInfoBean
					.getPRODUCT_ID());// 根据产品编号查询产品资源信息
			productInfoBean.setProductResourceBeans(productResourceBeans);// 将资源信息放入产品
		}
		list = null;
		return beans;
	}

	/**
	 * 根据产品编号查询产品资源信息
	 * 
	 * @param productNo
	 */
	public static List<ProductResourceBean> getProductResource(String productNo) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProductDetail(productNo);
		ProductResourceBean bean = new ProductResourceBean();
		List<ProductResourceBean> beans = bean.changeToObject(list);// 将object对象转换成ProductResourceBean
		for (ProductResourceBean productResourceBean : beans) {
			if (productResourceBean.getNODE_TYPE() == 8) {// 资源下是纬度
				List<ProductItemBean> productItemBeans = getProductItem(productResourceBean
						.getRESOURCE_ID());
				productResourceBean.setProductItemBeans(productItemBeans);// 将纬度信息放入资源
			}
		}
		list = null;
		return beans;
	}

	/**
	 * 根据资源编号查询产品纬度信息
	 * 
	 * @param productNo
	 * @param RESOURCE_ID
	 * @return
	 */
	public static List<ProductItemBean> getProductItem(String resourceNo) {
		List<HashMap<String, Object>> list = new GetAllRecords()
				.getProductWDByResourceNo(resourceNo);
		ProductItemBean bean = new ProductItemBean();
		return bean.changeToObject(list);// 将object对象转换成ProductResourceBean
	}

	/**
	 * 根据订单编号查询 订单表
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
	 * 根据订单编号查询订单明细表
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
	 * 根据产品编号 查询 产品资源信息表 得到 当前产品下的应用个数
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
	 * 根据产品编号 查询 资费模型表
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
	 * 根据 产品编号、应用编号 在 产品资源信息表 中得到 资源编号
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
	 * 根据 资源编号、维度编码 在 产品维度表中 得到 维度编号
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
	 * 根据 维度编号 在申购明细表 得到 申购量
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
	 * 根据维度编号 和 资源编号 去产品维度表（PRODUCT_ITEM）里面去获取相关维度单价（PRICE）信息
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
	 * 根据交易流水号查询 维度使用量
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
	 * 修改锁定使用量表 数据状态为已解除 的维度元组
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
				log.info("修改锁定使用量表中 数据状态为已解除 的维度元组 成功 共 " + i + "条");
			} else {
				log.info("修改锁定使用量表中 数据状态为已解除 的维度元组 无");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改锁定使用量表中 数据状态为已解除 的维度元组出现异常 原因：" + e.getMessage());
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

	// 此方法没用上
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
