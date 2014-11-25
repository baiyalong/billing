package com.zhcs.billing.use.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.feerate.PriceModelAndDiscountCulDao;
import com.zhcs.billing.use.bean.OrderDetailBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.ProductInfoBean;
import com.zhcs.billing.use.bean.ProductItemBean;
import com.zhcs.billing.use.bean.ProductResourceBean;
import com.zhcs.billing.use.bean.SubscriptionItemBean;
import com.zhcs.billing.use.bean.TCulOrderDetailBean;
import com.zhcs.billing.use.bean.TScanningAddTotalBean;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 计费方法
 * 
 * @author hefa
 * 
 */
public class BillingMethod {
	public static void main(String[] args) {
		BillingMethod method = new BillingMethod();
		OrderInfoBean orderInfoBean = new OrderInfoBean();
		orderInfoBean.setORDER_ID("1660140528000235");
		String s = method.compute(orderInfoBean, "1", "2014-05-28 12:00:00");
		System.out.println("扣费" + s);
	}

	private static Logger log = LoggerFactory.getLogger(BillingMethod.class);
	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(BillingMethod.class);

	// 订单号,扫描方式,时间
	public static synchronized String compute(OrderInfoBean orderInfoBean,
			String SCANNING_WAY, String timeInterval) {
		if (orderInfoBean.getORDER_ID() == null
				|| "".equals(orderInfoBean.getORDER_ID())
				|| timeInterval == null || "".equals(timeInterval)) {
			log.error("传入的参数有误");
			logUtil.error("传入的参数有误");
			return "0";
		}

		// 根据一张订单获取产品编号
		OrderDetailBean detailBean = BillingQuery
				.getProductNumber(orderInfoBean.getORDER_ID());
		try {
			if (detailBean == null || detailBean.getPRODUCT_ID() == null
					|| "".equals(detailBean.getPRODUCT_ID())) {
				log.error("订单：" + orderInfoBean.getORDER_ID() + "下没有产品");
				logUtil.error("订单：" + orderInfoBean.getORDER_ID() + "下没有产品");
				return "0";
			}
		} catch (Exception e) {
			log.error("订单：" + orderInfoBean.getORDER_ID() + "获取产品出现异常"
					+ e.getMessage());
			logUtil.error("订单：" + orderInfoBean.getORDER_ID() + "获取产品出现异常"
					+ e.getMessage());
			return "0";
		}
		// 将订单下面所有的产品获取产品下所有的资源信息
		List<ProductResourceBean> resourceBeans = BillingQuery
				.getResourceInfo(detailBean.getPRODUCT_ID());
		// 根据产品获取所有纬度信息
		List<ProductItemBean> itemBeans = BillingQuery
				.getProductItem(detailBean.getPRODUCT_ID());
		// 根据产品编号、申购编号查询申购明细
		List<SubscriptionItemBean> subscriptionItemBeans = BillingQuery
				.getSubscriptionItems(detailBean.getSUBSCRIBER_ID(),
						detailBean.getPRODUCT_ID());
		for (ProductItemBean ItemBean : itemBeans) {
			for (SubscriptionItemBean subscription : subscriptionItemBeans) {
				if (ItemBean.getITEM_ID().equals(subscription.getITEM_ID())
						&& ItemBean.getPRODUCT_ID().equals(
								subscription.getPRODUCT_ID())) {
					ItemBean.setSI_ID(subscription.getSI_ID());// 申购明细编号
					ItemBean.setPD_ID(subscription.getPD_ID());// 套餐详细编号
					// ItemBean.setUSAGE_AMOUNT(subscription.getSUBSCRIBE_AMOUNT());//申购量
					ItemBean.setITEM_AMOUNT(subscription.getREMAINING_AMOUNT());// 包内剩余量
				}
			}
		}
		subscriptionItemBeans = null;
		// 获取累积量
		BillingCumulative.getJson(orderInfoBean.getORDER_ID(), SCANNING_WAY,
				timeInterval);// 订单,扫描方式,时间

		// 根据订单号到计费子系统获取产品纬度累积量 订单、时间、扫描方式）
		List<TScanningAddTotalBean> addTotalBeans = BillingQuery.getCumulants(
				orderInfoBean, timeInterval, SCANNING_WAY);
		// 费率模型和优惠策略的公共算法
		PriceModelAndDiscountCulDao model = new PriceModelAndDiscountCulDao();
		// 把累积量赋值给纬度
		for (ProductItemBean itemBean : itemBeans) {
			Map<String, Integer> map = new HashMap<String, Integer>();// 累计量map
			Map<String, Integer> map2 = new HashMap<String, Integer>();// 当次使用量map
			for (TScanningAddTotalBean bean : addTotalBeans) {
				if (addTotalBeans == null || addTotalBeans.size() == 0) {
					log.info("订单：" + orderInfoBean.getORDER_ID()
							+ "没有查询到产品维度累积量");
					logUtil.info("订单：" + orderInfoBean.getORDER_ID()
							+ "没有查询到产品维度累积量");
				} else {
					try {
						if ("1".equals(bean.getSCANNING_WAY())) {
							// 相同纬度累取最后一次同一纬度累积量 产品纬度编码
							if (itemBean.getITEM_CODE().equals(bean.getWD_NO())) {
								map.put(bean.getRESOURCE_ID(),
										bean.getWD_ADD_TOTAL());
								map2.put(bean.getRESOURCE_ID(),
										bean.getCURRENT_ADD_TOTAL());
							}
						} else if ("5".equals(bean.getSCANNING_WAY())) {// 按5分钟扫描
							// 相同纬度累加为同一纬度累积量 产品纬度编码
							if (itemBean.getITEM_CODE().equals(bean.getWD_NO())) {
								itemBean.setUSAGE_AMOUNT(itemBean
										.getUSAGE_AMOUNT()
										+ bean.getWD_ADD_TOTAL());
								itemBean.setCURRENT_ADD_TOTAL(itemBean
										.getCURRENT_ADD_TOTAL()
										+ bean.getCURRENT_ADD_TOTAL());// 同纬度的当次量
							}
						}
					} catch (Exception e) {
						log.error("产品维度：" + itemBean.getITEM_NAME()
								+ "设置累积量出现异常" + e.getMessage());
						logUtil.error("产品维度：" + itemBean.getITEM_NAME()
								+ "设置累积量出现异常" + e.getMessage());
					}
				}
			}
			if ("1".equals(SCANNING_WAY)) {
				Iterator iterator = map.values().iterator();
				Iterator iterator2 = map2.values().iterator();
				int object = 0;
				int object2 = 0;
				while (iterator.hasNext()) {
					Integer it = (Integer) iterator.next();
					object = object + it;
				}
				itemBean.setUSAGE_AMOUNT(object);
				while (iterator2.hasNext()) {
					Integer it = (Integer) iterator2.next();
					object2 = object2 + it;
				}
				itemBean.setUSAGE_AMOUNT(object);
				itemBean.setCURRENT_ADD_TOTAL(object2);
			}

			// 如果该产品维度有套餐
			if (itemBean.getPD_ID() != null) {
				if (itemBean.getCURRENT_ADD_TOTAL() > 0) {
					// 使用量-套餐剩余量
					int ua = itemBean.getCURRENT_ADD_TOTAL()
							- itemBean.getITEM_AMOUNT();
					// 套餐剩余量-使用量
					int cat = itemBean.getITEM_AMOUNT()
							- itemBean.getCURRENT_ADD_TOTAL();
					itemBean.setITEM_AMOUNT(cat);// 当使用量大于套餐剩余量时，取0
					itemBean.setMONEY(ua > 0 ? ua * itemBean.getPRICE() : 0);// 当使用量超过套餐剩余量时，超出部分按标准批价收费
					itemBean.setTwoMONEY(itemBean.getMONEY());// 二次批价
					// 更新数据库 申购明细编号，套餐剩余量
					BillingQuery.updateSubscriptionItem(itemBean.getSI_ID(),
							itemBean.getITEM_AMOUNT());
					log.info(itemBean.getITEM_NAME()
							+ "累积量："
							+ itemBean.getUSAGE_AMOUNT()
							+ ",套餐包剩余数量："
							+ (itemBean.getITEM_AMOUNT() > 0 ? itemBean
									.getITEM_AMOUNT() : 0) + ",超出量:"
							+ (ua > 0 ? ua : 0) + ",超出部分费用："
							+ itemBean.getMONEY() + "厘");
					logUtil.info(itemBean.getITEM_NAME()
							+ "累积量："
							+ itemBean.getUSAGE_AMOUNT()
							+ ",套餐包内剩余数量："
							+ (itemBean.getITEM_AMOUNT() > 0 ? itemBean
									.getITEM_AMOUNT() : 0) + ",超出部分费用："
							+ itemBean.getMONEY() + "厘");
				}
			} else {// 该产品没有套餐
				if (itemBean.getUSAGE_AMOUNT() > 0) {
					// 计算产品纬度标准批价
					itemBean.setMONEY(itemBean.getUSAGE_AMOUNT()
							* itemBean.getPRICE());// 标准批价
					log.info(itemBean.getITEM_NAME() + "累积量："
							+ itemBean.getUSAGE_AMOUNT() + ",单价："
							+ itemBean.getPRICE() + "产生标准批价费用:"
							+ itemBean.getMONEY() + "厘");
					logUtil.info(itemBean.getITEM_NAME() + "累积量："
							+ itemBean.getUSAGE_AMOUNT() + ",单价："
							+ itemBean.getPRICE() + "产生标准批价费用:"
							+ itemBean.getMONEY() + "厘");
					// 计算产品维度二次批价
					double money = model.getAmountCount(
							itemBean.getPRODUCT_ID(),
							itemBean.getRESOURCE_ID(), itemBean.getITEM_CODE(),
							itemBean.getUSAGE_AMOUNT() + "",
							itemBean.getPRICE());
					itemBean.setTwoMONEY(money);// 二次批价
					log.info(itemBean.getITEM_NAME() + "累积量："
							+ itemBean.getUSAGE_AMOUNT() + ",产生二次批价：" + money
							+ "厘");
					logUtil.info(itemBean.getITEM_NAME() + "累积量："
							+ itemBean.getUSAGE_AMOUNT() + ",产生二次批价：" + money
							+ "厘");
				}
			}
		}

		// 把纬度计费的两次批价金额添加到资源
		for (ProductResourceBean resource : resourceBeans) {
			for (ProductItemBean item : itemBeans) {
				try {
					if (resource.getRESOURCE_ID().equals(item.getRESOURCE_ID())) {
						resource.setMONEY(add(resource.getMONEY(),
								item.getMONEY()));// 产品纬度标准批价
						resource.setTwoMONEY(add(resource.getTwoMONEY(),
								item.getTwoMONEY()));// 产品纬度二次批价
						log.info("产品资源" + resource.getRESOURCE_NAME()
								+ "的标准批价为：" + resource.getMONEY() + "厘,二次批价为："
								+ resource.getTwoMONEY() + "厘++");
						logUtil.info("产品资源" + resource.getRESOURCE_NAME()
								+ "的标准批价为：" + resource.getMONEY() + "厘,二次批价为："
								+ resource.getTwoMONEY() + "厘++");
					}
				} catch (Exception e) {
					log.error("把产品维度：" + item.getITEM_NAME() + "的费用添加到产品资源："
							+ resource + "出现异常：" + e.getMessage());
					logUtil.error("把产品维度：" + item.getITEM_NAME()
							+ "的费用添加到产品资源：" + resource + "出现异常："
							+ e.getMessage());
				}

			}
		}
		// 获取资源根节点编号
		String root = BillingQuery.getRootResourceInfo(detailBean
				.getPRODUCT_ID());
		// 根节点资源
		List<ProductResourceBean> rootResources = new ArrayList<ProductResourceBean>();
		for (ProductResourceBean productResourceBean : resourceBeans) {
			if (productResourceBean.getPARENT_ID().equals(root)) {// 父资源
				rootResources.add(productResourceBean);
				computingResources(productResourceBean);
			}
		}
		ProductInfoBean infoBean = new ProductInfoBean();
		infoBean.setPRODUCT_ID(detailBean.getPRODUCT_ID());
		for (ProductResourceBean rootResource : rootResources) {
			// 根资源的二次批价金额计算为产品二次批价金额
			if (infoBean.getPRODUCT_ID().equals(rootResource.getPRODUCT_ID())) {
				infoBean.setMONEY(infoBean.getMONEY() + rootResource.getMONEY());// 一次批价
				infoBean.setTwoMONEY(infoBean.getTwoMONEY()
						+ rootResource.getTwoMONEY());// 二次批价
				TCulOrderDetailBean bean = new TCulOrderDetailBean();
				bean.setID(Common.createBillingID());// 主键ID
				bean.setORDER_ID(orderInfoBean.getORDER_ID());// 订单号
				bean.setCODE(rootResource.getRESOURCE_ID());// 资源编号
				bean.setCTYPE("1");// 0代表产品，1代表资源
				bean.setSCANNING_TIME(timeInterval);// 计费子系统扫描订单计量时间
				bean.setSCANNING_WAY(SCANNING_WAY);// 扫描方式
				bean.setBEFORE_AMOUNT(String.valueOf(rootResource.getMONEY()));// 资源资费模型前金额
				bean.setAFTER_AMOUNT(String.valueOf(rootResource.getTwoMONEY())); // 资源资费模型后金额
				BillingInsert.AddTCulOrderDetail(bean);// 往计费系统数据库容器类产品资源分类三次批价记录表添加记录
				log.info("资源：" + rootResource.getRESOURCE_NAME() + "标准批价费用金额："
						+ rootResource.getMONEY() + "厘,二次批价费用金额："
						+ rootResource.getTwoMONEY() + "厘。");
				logUtil.info("资源：" + rootResource.getRESOURCE_NAME()
						+ "标准批价费用金额：" + rootResource.getMONEY() + "厘,二次批价费用金额："
						+ rootResource.getTwoMONEY() + "厘。");
			}
		}
		// 计算订单、产品金额
		TCulOrderDetailBean bean = new TCulOrderDetailBean();
		bean.setID(Common.createBillingID());// 主键ID
		bean.setORDER_ID(orderInfoBean.getORDER_ID());// 订单号
		bean.setCODE(infoBean.getPRODUCT_ID());// 产品编号
		bean.setCTYPE("0");// 0代表产品，1代表资源
		bean.setSCANNING_TIME(timeInterval);// 计费子系统扫描订单计量时间
		bean.setSCANNING_WAY(SCANNING_WAY);// 扫描方式
		// 计算产品优惠后金额
		double SUMARY = model.getDiscountAmount(infoBean.getTwoMONEY(),
				infoBean.getPRODUCT_ID());
		infoBean.setSUMARY(SUMARY);

		bean.setBEFORE_AMOUNT(String.valueOf(infoBean.getTwoMONEY()));// 优惠前金额
		bean.setAFTER_AMOUNT(String.valueOf(infoBean.getSUMARY())); // 优惠后金额
		bean.setCOUNT_COST(bean.getAFTER_AMOUNT());// 累计计算费用
		log.info("产品：" + infoBean.getPRODUCT_ID() + "优惠前的金额为"
				+ infoBean.getTwoMONEY() + "厘，优惠后的金额为：" + infoBean.getSUMARY()
				+ "厘。");
		logUtil.info("产品：" + infoBean.getPRODUCT_ID() + "优惠前的金额为"
				+ infoBean.getTwoMONEY() + "厘，优惠后的金额为：" + infoBean.getSUMARY()
				+ "厘。");
		// 根据订单\时间最新的一条 取出已经扣费
		// 查询订单上次扣费情况
		List<TCulOrderDetailBean> culOrderDetailBeans = BillingQuery
				.getCulOrderDetailBeans(orderInfoBean.getORDER_ID(),
						timeInterval, SCANNING_WAY);
		// 已经扣除费用DEDUCT_COST
		for (TCulOrderDetailBean tCulOrderDetailBean : culOrderDetailBeans) {
			try {
				// 属于该产品的上次记录
				if ("0".equals(tCulOrderDetailBean.getCTYPE())) {
					bean.setDEDUCT_COST(add(
							new Double(tCulOrderDetailBean.getDEDUCT_COST()),
							new Double(tCulOrderDetailBean.getREALITY()))
							.toString());
					break;
				}
			} catch (Exception e) {
				log.error("查找订单:" + bean.getORDER_ID()
						+ "已经扣除费用DEDUCT_COST出现异常：" + e.getMessage());
				logUtil.error("查找订单:" + bean.getORDER_ID()
						+ "已经扣除费用DEDUCT_COST出现异常：" + e.getMessage());
			}
		}
		// 这是这个月的第一条，没有前一条记录
		if ("".equals(bean.getDEDUCT_COST()) || null == bean.getDEDUCT_COST()) {
			bean.setDEDUCT_COST("0");
		}
		// 本次应该扣的费用
		// 本次扣费REALITY=优惠后金额-已扣金额
		Double reality = sub(infoBean.getSUMARY(),
				new Double(bean.getDEDUCT_COST()));
		Long moneyl = Math.round(reality);// double转long 四舍五入
		bean.setREALITY(moneyl > 0 ? moneyl.toString() : "0");
		BillingInsert.AddTCulOrderDetail(bean);// 往计费系统数据库容器类产品三次批价记录表添加记录
		log.info("订单：" + bean.getORDER_ID() + "本次扣费：" + moneyl + "厘(优惠后金额："
				+ infoBean.getSUMARY() + "厘,此前已经扣费金额：" + bean.getDEDUCT_COST()
				+ "厘)");
		logUtil.info("订单：" + bean.getORDER_ID() + "本次扣费：" + moneyl + "厘(优惠后金额："
				+ infoBean.getSUMARY() + "厘,此前已经扣费金额：" + bean.getDEDUCT_COST()
				+ "厘)");
		return bean.getREALITY();// 订单此次扫描实际扣费 单位厘
	}

	/**
	 * 计算资源的二次批价金额
	 * 
	 * @param bean
	 */
	public static void computingResources(ProductResourceBean bean) {
		List<ProductResourceBean> list = bean.getProductResourceBeans();
		for (ProductResourceBean resourceBean : list) {
			computingResources(resourceBean);
			// 一次批价的金额
			bean.setMONEY(add(bean.getMONEY(), resourceBean.getMONEY()));
			// 资费模型的金额
			bean.setTwoMONEY(add(bean.getTwoMONEY(), resourceBean.getTwoMONEY()));
			if (resourceBean.getNODE_TYPE() != 8) {
				log.info("产品资源" + resourceBean.getRESOURCE_NAME() + "的标准批价为："
						+ bean.getMONEY() + "厘,二次批价为：" + bean.getTwoMONEY()
						+ "厘--");
				logUtil.info("产品资源" + resourceBean.getRESOURCE_NAME()
						+ "的标准批价为：" + bean.getMONEY() + "厘,二次批价为："
						+ bean.getTwoMONEY() + "厘--");
			}
		}
	}

	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 两个double数相加
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.add(b2).doubleValue());
	}

	/**
	 * 两个Double数相减
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.subtract(b2).doubleValue());
	}

	/**
	 * 两个Double数相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1.multiply(b2).doubleValue());
	}

	/**
	 * 两个Double数相除
	 * 
	 * @param v1
	 * @param v2
	 * @return Double
	 */
	public static Double div(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return new Double(b1
				.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}
}
