package com.zhcs.billing.use.dao;

import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.feerate.PriceModelAndDiscountCulDao;
import com.zhcs.billing.quartz.dao.TusageEventsDao;
import com.zhcs.billing.use.bean.OrderDetailBean;
import com.zhcs.billing.use.bean.OrderInfoRecords;
import com.zhcs.billing.use.bean.TUsageEventsBean;
import com.zhcs.billing.use.bean.UsageMonthlyBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 
 * @author hugo
 * 
 */
public class AppCalculateCharage {
	private static LoggerUtil log = LoggerUtil
			.getLogger(AppCalculateCharage.class);
	private BaseDao basedao;
	private BillingBaseDao billing;
	private PriceModelAndDiscountCulDao freecount = new PriceModelAndDiscountCulDao();
	private GetAllRecords allRecords = new GetAllRecords();
	private TUsageMonthlyDao usageMonthlyDao = new TUsageMonthlyDao();
	private TusageEventsDao usageEventsDao = new TusageEventsDao();
	private TGrantPriceForChargeDao grantPriceForChargeDao = new TGrantPriceForChargeDao();
	private static Logger logs = LoggerFactory.getLogger(BillingQueryDao.class);

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
	 * 
	 * @param OrderID
	 * @param OperationType
	 * @param TimeStamp
	 * @param BillingID
	 * @param BillCycleId
	 * @param productType
	 * @return
	 */
	public String CommonFee(String OrderID, String OperationType,
			String TimeStamp, String BillingID, String BillCycleId,
			String productType) {
		String productId = ""; // 产品编号
		String productCategory = ""; // 产品大类
		String itemCode = ""; // 维度编码
		String customerId = ""; // 客户编号
		int total = 0; // 所有产品的总额
		String Description = ""; // 描述
		String flag = "true"; // 批价结果
		String resourceId = "";
		// List oneProductTotal=new ArrayList(); //存放 一次性收费类型下的 每个产品总额
		// List monthProductTotal=new ArrayList(); //存放 包月收费下类型 每个产品总额
		// 根据订单编号查询 订单表 得到客户编号
		List<HashMap<String, Object>> custList = allRecords
				.getCustomerIdByOrderId(OrderID);
		if (custList != null && custList.size() > 0) {
			log.info("查询订单信息表 共:" + custList.size() + "条");
			// 一个订单对应一个客户
			customerId = custList.get(0).get("CUSTOMER_ID") + "";
		}
		// 根据订单编号 和 操作类型(规则编号OperationType) 查询订单明细表
		List<HashMap<String, Object>> list = allRecords.getProjectOrderInfo(
				OrderID, OperationType);
		if (list != null && list.size() > 0) {
			log.info("根据订单编号和规则编号 查询订单明细表数据只是包月或者一次性收费中的一种产品  共:" + list.size()
					+ "条");
			List<OrderInfoRecords> RecordList = new ArrayList<OrderInfoRecords>();
			for (HashMap<String, Object> map : list) {
				// 得到产品编号
				productId = map.get("PRODUCT_ID") + "";
				// 根据产品编号 查询 产品资源信息表 得到 当前产品下的应用个数
				String appCounts = ""; // 当前产品下的应用个数 appCounts
				List<HashMap<String, Object>> appList = allRecords
						.getAppCountsByProductId(productId);
				if (appList != null && appList.size() > 0) {
					log.info("根据产品编号 查询 产品资源信息表 得到当前产品下的应用个数 共:"
							+ appList.size() + "个");
					appCounts = appList.get(0).get("COUNTS") + "";
					Description += appCounts;
				} else {
					Description += " 根据产品编号查询无数据";
				}
				// 根据产品编号获取产品大类
				List<HashMap<String, Object>> productTypeList = allRecords
						.getProductType(productId);
				if (productTypeList != null && productTypeList.size() > 0) {
					log.info("根据产品编号获取产品大类 共:" + productTypeList.size() + "条");
					// 一个产品对应一个产品大类
					productCategory = productTypeList.get(0).get(
							"PRODUCT_CATEGORY")
							+ "";
				} else {
					Description += " 根据产品编号查询无数据";
				}
				// (2)代表 应用类产品 (1)代表 容器类产品
				if (productType.equals(productCategory)) {
					// 根据产品编号 查询 产品维度表
					List<HashMap<String, Object>> Itemlist = allRecords
							.getItemCodeByProductId(productId);
					if (Itemlist != null && Itemlist.size() > 0) {
						log.info("根据产品编号 查询 资费模型表 共:" + Itemlist.size() + "条");
						for (HashMap<String, Object> m : Itemlist) {
							itemCode = m.get("ITEM_CODE") + ""; // 得到维度编码
							resourceId = m.get("RESOURCE_ID") + ""; // 得到资源编号
							// 规则编号是1 代表 一次性收费
							if ("1".equals(OperationType)) {
								double oneProduct = freecount
										.getAmountCount(productId, resourceId,
												itemCode, "0", 0); // 之前传递的是规则编号、应用个数现在改为维度编号、0
								log.info("根据 产品的资费模型和产品的应用个数计算费用为:"
										+ oneProduct + "元");
								total += Integer
										.parseInt(new DecimalFormat("0")
												.format(oneProduct));
							}
							// 规则编号是2 代表 包月收费
							else if ("2".equals(OperationType)) {

								String dayStr = Common.getDateStr();
								List<HashMap<String, Object>> eventsList = null;
								try {
									eventsList = GetBillingBaseDao()
											.doSelect(
													"select ID from T_USAGE_EVENTS_"
															+ dayStr
																	.substring(
																			0,
																			dayStr
																					.length() - 2));
								} catch (Exception e) {
									log.error(e.getMessage());
									log.info("不存在 详单表 要新建");
								}
								if (eventsList == null) {
									boolean b = BillingBaseDao.createTable(); // 创建表(主键id,时间,帐期编号,客户编号,接口之间的标识符,产品编号,实扣结果(1实扣中2实扣完成3实扣失败),实扣金额,费用类型,定时扫描状态)
									log.info("创建表状态 ：" + b);
								}

								// 月租
								double monthProduct = freecount
										.getAmountCount(productId, resourceId,
												itemCode, "0", 0);
								// 把单个的产品总额存放在list集合里 （包月收费）
								// monthProductTotal.add(monthProduct);
								// 订单记录对象
								OrderInfoRecords record = new OrderInfoRecords();
								record.setOrderId(OrderID);
								record.setProductNo(productId);
								record.setMonthCost(monthProduct);
								RecordList.add(record);
								// 从 List<订单记录bean>中 计算
								if (RecordList != null && RecordList.size() > 0) {
									int days = Common.getThisMonthDays(); // 得到当前月的天数
									double totalAvgPrice = 0; // 所有产品的 日均费用和
									double totalMonthPrice = 0; // 所有产品月租费用和
									for (OrderInfoRecords ors : RecordList) {
										// 求日均 = 单个产品的总费用(月租) / 当月天数
										double avgPrice = ors.getMonthCost()
												/ days;
										// 得到所有产品的 日均费用和
										totalAvgPrice += avgPrice;
										// 得到所有产品月租费用和
										totalMonthPrice += ors.getMonthCost();
									}
									int totalAvgPriceFormat = Integer
											.parseInt(new DecimalFormat("0")
													.format(totalAvgPrice));
									// 如果当前时间是本月最后一天，就返回差额，如果不是就返回日均费用
									if (days == Integer.parseInt(Common
											.getNowDay())) {
										// 用所有产品的 日均费用和 *（当前月的天数-1）
										double totalPrices = totalAvgPriceFormat
												* (days - 1);
										// 差额 =
										// (当前订单下所有产品的月租的总和)-(当前订单下所有产品的日均费用的总和*(当前月的天数-1))
										double balancePrice = totalMonthPrice
												- totalPrices;
										total = Integer
												.parseInt(new DecimalFormat("0")
														.format(balancePrice));
									} else {
										total = totalAvgPriceFormat;
									}
								}

								//
								if ("2".equals(productType)) {
									List<HashMap<String, Object>> usageMonthdlyList = usageEventsDao
											.getUsageMonthly(OrderID,
													BillCycleId);
									if (usageMonthdlyList.size() < 1) {
										log.info("查询需要处理的月租数据T_Usage_Monthly："
												+ usageMonthdlyList.size());
										try {
											// 往 资费月租表(T_USAGE_MONTHLY) 添加记录
											UsageMonthlyBean usageMonthlyBean = new UsageMonthlyBean();
											usageMonthlyBean
													.setBILLSEQ_ID(BillCycleId);
											usageMonthlyBean
													.setCUSTOMER_ID(customerId);
											usageMonthlyBean
													.setFIXED_OR_MONTHLY(0);
											usageMonthlyBean
													.setORDER_ID(OrderID);
											usageMonthlyBean
													.setPRODUCT_CATEGORY(Integer
															.parseInt(productCategory));
											usageMonthlyBean
													.setPRODUCT_ID(productId);
											usageMonthlyBean
													.setSCANNING_WAY("");
											usageMonthlyBean
													.setPRODUCT_TYPE("2");
											usageMonthlyBean.setSTATE("");
											usageMonthlyBean
													.setUNIT_RATE(monthProduct
															+ "");// 时扣金额
											// 字段名(主键id,外键,客户编号、按天扣或月初扣、月扣金额、订单编号、产品编号、产品类型、扫描状态,状态,服务器编号状态)
											usageMonthlyDao
													.insertTUsageMonthly(usageMonthlyBean);
										} catch (Exception e) {
											flag = "false";
											// Description +=
											// " 往 资费月租表添加记录失败 原因："
											// + e.getMessage();
											log.error(e.getMessage());
										}
									}
									usageMonthdlyList = null;
								}

								appList = null;
								productTypeList = null;
								Itemlist = null;
								eventsList = null;
							}
						}
					}
				}
			}

			// 如果是包月收费 就往 详单表（动态加年月）添加记录 (注:一个月只能创建一张表)
			try {
				TUsageEventsBean usageEventsBean = new TUsageEventsBean();
				usageEventsBean.setBILLCYCLE_ID(BillCycleId); // 账期外键
				usageEventsBean.setCUSTOMER_ID(customerId); // 客户id
				usageEventsBean.setPRUDUCT_ID(productId); // 产品id
				usageEventsBean.setORDER_ID(OrderID); // 订单ID
				usageEventsBean.setACCOUNT(Integer.parseInt(total + "")); // 时扣金额
				usageEventsBean.setFEE_TYPE(true); // 是否收费
				usageEventsBean.setREALOCKSTAT(1); // 扣费状态
				usageMonthlyDao.insertTUsageEvents(usageEventsBean);
			} catch (Exception e) {
				flag = "false";
				// Description += " 往  详单表（动态加年月）添加记录失败 原因："
				// + e.getMessage();
				log.error(e.getMessage());
			}

			RecordList = null;
		} else {
			flag = "false";
			Description = "根据此订单编号查询无数据";
		}
		if ("2".equals(productType)) {
			try {
				// 往扣费批价接口详细表(主键ID,接口之间的标识符,订单编号,操作类型,时间戳,批价金额（厘）,批价结果,对错误/异常的详细描述信息,创建时间)
				// 插入数据
				grantPriceForChargeDao.insertTGrantPriceForcharge(Common
						.getStrSSS(), BillingID, OrderID, OperationType,
						TimeStamp, total + "", flag, Description, Common
								.getDateTimeSSS());
			} catch (Exception e) {
				flag = "false";
				// Description += " 往扣费批价接口详细表插入数据失败";
				log.error(e.getMessage());
			}
		}
		String result = "";
		// "2"代表 应用类产品 "1"代表 容器类产品
		if ("2".equals(productType)) {
			// 拼 维度元组 (时间戳、接口之间的标识符、批价金额（厘）、批价结果、对错误/异常的详细描述信息)
			List<String> params = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			params.add("TimeStamp");
			params.add("BillingID");
			params.add("Amount");
			params.add("Result");
			params.add("Description");

			values.add(TimeStamp);
			values.add(BillingID);
			values.add(total + "");
			values.add(flag);
			values.add(Description);
			String reJson = Common.changeJSON(params, values);
			log.info("返回的JSON:" + reJson);
			result = reJson.substring(1, reJson.length() - 1);
			reJson = null;
			params = null;
			values = null;
		} else {
			result = total + "";
		}
		custList = null;
		list = null;
		productId = null; // 产品编号
		productCategory = null; // 产品大类
		itemCode = null; // 维度编码
		customerId = null; // 客户编号
		total = 0; // 所有产品的总额
		Description = null; // 描述
		flag = null; // 批价结果
		resourceId = null;

		return result;
	}

	/**
	 * 
	 * @param OrderID
	 * @param OperationType
	 * @param TimeStamp
	 * @param BillingID
	 * @param BillCycleId
	 * @param productType
	 * @return
	 */
	public String CommonFeeN(String OrderID, String BillCycleId) {
		String customerId = ""; // 客户编号
		int total = 0; // 所有产品的总额
		// 当前日期
		String time = new SimpleDateFormat("yyyyMM")
				.format(new Date());
		// 根据订单编号查询 订单表 得到客户编号
		List<HashMap<String, Object>> custList = allRecords
				.getCustomerIdByOrderId(OrderID);
		if (custList != null && custList.size() > 0) {
			log.info("查询订单信息表 共:" + custList.size() + "条");
			// 一个订单对应一个客户
			customerId = custList.get(0).get("CUSTOMER_ID") + "";
		}
		List<HashMap<String, Object>> list = new BillingQueryDao().getYZFR(
				OrderID, time);
		double money = 0;
		OrderDetailBean orderDetailBean = BillingQuerys
				.getProductNumber(OrderID);
		log.info("订单为：" + OrderID + ",产品为" + orderDetailBean.getPRODUCT_ID()
				+ "配置月租费的维度如下：");
		logs.info("订单为：" + OrderID + ",产品为" + orderDetailBean.getPRODUCT_ID()
				+ "配置月租费的维度如下：");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String itemName=list.get(i).get("ITEM_NAME")+"";
				String itemCode=list.get(i).get("ITEM_CODE")+"";
				String fixedValue=list.get(i).get("FIXED_VALUE")+"";
				money += Double
						.parseDouble(list.get(i).get("FIXED_VALUE") + "");
				log.info("维度名称:" + itemName + ",维度编码："
						+  itemCode+ ",维度的月租费："
						+ fixedValue);
				logs.info("维度名称:" + itemName + ",维度编码："
						+  itemCode+ ",维度的月租费："
						+ fixedValue);
			}
		}
		log.info("订单为：" + OrderID + "的总金额为" + money);
		logs.info("订单为：" + OrderID + "的总金额为" + money);
		String dayStr = Common.getDateStr();

		// 查询月租详单表是否存在，如果不存在就创建，便于插入月租每月扣费信息。
		List<HashMap<String, Object>> eventsList = null;
		try {
			eventsList = GetBillingBaseDao().doSelect(
					"select ID from T_USAGE_EVENTS_"
							+ dayStr.substring(0, dayStr.length() - 2));
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("不存在 详单表 要新建");
		}
		if (eventsList == null) {
			boolean b = BillingBaseDao.createTable(); // 创建表(主键id,时间,帐期编号,客户编号,接口之间的标识符,产品编号,实扣结果(1实扣中2实扣完成3实扣失败),实扣金额,费用类型,定时扫描状态)
			log.info("创建表状态 ：" + b);
		}

		// monthProductTotal.add(monthProduct);
		int totalAvgPriceFormat=0;
		int days = Common.getThisMonthDays(); // 得到当前月的天数
//		if (days != Integer.parseInt(Common.getNowDay())){
			// 求日均 = 订单的总费用(月租) / 当月天数
			double avgPrice = money / days;
			// 费用转换
			totalAvgPriceFormat = Integer.parseInt(new DecimalFormat("0").format(avgPrice));
			logs.info("日均金额："+totalAvgPriceFormat);
			log.info("日均金额："+totalAvgPriceFormat);
//		}
		// 如果当前时间是本月最后一天，就返回差额，如果不是就返回日均费用
		if (days == Integer.parseInt(Common.getNowDay())) {
			// 用所有订单的 日均费用和 *（当前月的天数-1）
			double totalPrices = totalAvgPriceFormat * (days - 1);
			logs.info("已扣费金额："+totalPrices);
			log.info("已扣费金额："+totalPrices);
			// 差额 = (当前订单下所有产品的月租的总和)-(当前订单下所有产品的日均费用的总和*(当前月的天数-1))
			double balancePrice = money - totalPrices;
			total = Integer.parseInt(new DecimalFormat("0")
					.format(balancePrice));
			logs.info("本月最后一天所扣的费用为："+total+"(补齐的差额)");
			log.info("本月最后一天所扣的费用为： "+total+"(补齐的差额)");
		} else {
			total = totalAvgPriceFormat;
			logs.info("订单总费用除以当月的天数得到每天所扣的日均值为："+total);
			log.info("订单总费用除以当月的天数得到每天所扣的日均值为："+total);
		}

		List<HashMap<String, Object>> usageMonthdlyList = usageEventsDao
				.getUsageMonthly(OrderID, BillCycleId);
		if (usageMonthdlyList != null && usageMonthdlyList.size() < 1) {
			log.info("查询需要处理的月租数据T_Usage_Monthly：" + usageMonthdlyList.size());
			try {
				// 往 资费月租表(T_USAGE_MONTHLY) 添加记录
				UsageMonthlyBean usageMonthlyBean = new UsageMonthlyBean();
				usageMonthlyBean.setBILLSEQ_ID(BillCycleId);
				usageMonthlyBean.setCUSTOMER_ID(customerId);
				usageMonthlyBean.setFIXED_OR_MONTHLY(0);
				usageMonthlyBean.setORDER_ID(OrderID);
				usageMonthlyBean.setPRODUCT_CATEGORY(Integer.parseInt("1"));
				usageMonthlyBean.setPRODUCT_ID("");
				usageMonthlyBean.setSCANNING_WAY("");
				usageMonthlyBean.setPRODUCT_TYPE("2");
				usageMonthlyBean.setSTATE("");
				usageMonthlyBean.setUNIT_RATE(money + "");// 时扣金额
				// 字段名(主键id,外键,客户编号、按天扣或月初扣、月扣金额、订单编号、产品编号、产品类型、扫描状态,状态,服务器编号状态)
				usageMonthlyDao.insertTUsageMonthly(usageMonthlyBean);
			} catch (Exception e) {
				// Description +=
				// " 往 资费月租表添加记录失败 原因："
				// + e.getMessage();
				log.error(e.getMessage());
			}
		}

		// 如果是包月收费 就往 详单表（动态加年月）添加记录 (注:一个月只能创建一张表)
		try {
			TUsageEventsBean usageEventsBean = new TUsageEventsBean();
			usageEventsBean.setBILLCYCLE_ID(BillCycleId); // 账期外键
			usageEventsBean.setCUSTOMER_ID(customerId); // 客户id
			usageEventsBean.setPRUDUCT_ID(""); // 产品id
			usageEventsBean.setORDER_ID(OrderID); // 订单ID
			usageEventsBean.setACCOUNT(Integer.parseInt(total + "")); // 时扣金额
			usageEventsBean.setFEE_TYPE(true); // 是否收费
			usageEventsBean.setREALOCKSTAT(1); // 扣费状态
			usageMonthlyDao.insertTUsageEvents(usageEventsBean);
		} catch (Exception e) {
			// Description += " 往  详单表（动态加年月）添加记录失败 原因："
			// + e.getMessage();
			log.error(e.getMessage());
		}
		String result = total + "";
		/*
		 * // 拼 维度元组 (时间戳、接口之间的标识符、批价金额（厘）、批价结果、对错误/异常的详细描述信息) List<String>
		 * params = new ArrayList<String>(); List<String> values = new
		 * ArrayList<String>(); params.add("TimeStamp");
		 * params.add("BillingID"); params.add("Amount"); params.add("Result");
		 * params.add("Description");
		 * 
		 * values.add(TimeStamp); values.add(BillingID); values.add(total + "");
		 * values.add(flag); values.add(Description); String reJson =
		 * Common.changeJSON(params, values); log.info("返回的JSON:" + reJson);
		 * result = reJson.substring(1, reJson.length() - 1);
		 */

		// 清理内存占用
		custList = null;
		customerId = null; // 客户编号
		total = 0; // 所有产品的总额

		// 返回结果
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new AppCalculateCharage().CommonFeeN(
				"1673140530000243", "2014-05-30 12:00:00"));
	}
}
