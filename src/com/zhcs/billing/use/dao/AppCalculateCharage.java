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
		String productId = ""; // ��Ʒ���
		String productCategory = ""; // ��Ʒ����
		String itemCode = ""; // ά�ȱ���
		String customerId = ""; // �ͻ����
		int total = 0; // ���в�Ʒ���ܶ�
		String Description = ""; // ����
		String flag = "true"; // ���۽��
		String resourceId = "";
		// List oneProductTotal=new ArrayList(); //��� һ�����շ������µ� ÿ����Ʒ�ܶ�
		// List monthProductTotal=new ArrayList(); //��� �����շ������� ÿ����Ʒ�ܶ�
		// ���ݶ�����Ų�ѯ ������ �õ��ͻ����
		List<HashMap<String, Object>> custList = allRecords
				.getCustomerIdByOrderId(OrderID);
		if (custList != null && custList.size() > 0) {
			log.info("��ѯ������Ϣ�� ��:" + custList.size() + "��");
			// һ��������Ӧһ���ͻ�
			customerId = custList.get(0).get("CUSTOMER_ID") + "";
		}
		// ���ݶ������ �� ��������(������OperationType) ��ѯ������ϸ��
		List<HashMap<String, Object>> list = allRecords.getProjectOrderInfo(
				OrderID, OperationType);
		if (list != null && list.size() > 0) {
			log.info("���ݶ�����ź͹����� ��ѯ������ϸ������ֻ�ǰ��»���һ�����շ��е�һ�ֲ�Ʒ  ��:" + list.size()
					+ "��");
			List<OrderInfoRecords> RecordList = new ArrayList<OrderInfoRecords>();
			for (HashMap<String, Object> map : list) {
				// �õ���Ʒ���
				productId = map.get("PRODUCT_ID") + "";
				// ���ݲ�Ʒ��� ��ѯ ��Ʒ��Դ��Ϣ�� �õ� ��ǰ��Ʒ�µ�Ӧ�ø���
				String appCounts = ""; // ��ǰ��Ʒ�µ�Ӧ�ø��� appCounts
				List<HashMap<String, Object>> appList = allRecords
						.getAppCountsByProductId(productId);
				if (appList != null && appList.size() > 0) {
					log.info("���ݲ�Ʒ��� ��ѯ ��Ʒ��Դ��Ϣ�� �õ���ǰ��Ʒ�µ�Ӧ�ø��� ��:"
							+ appList.size() + "��");
					appCounts = appList.get(0).get("COUNTS") + "";
					Description += appCounts;
				} else {
					Description += " ���ݲ�Ʒ��Ų�ѯ������";
				}
				// ���ݲ�Ʒ��Ż�ȡ��Ʒ����
				List<HashMap<String, Object>> productTypeList = allRecords
						.getProductType(productId);
				if (productTypeList != null && productTypeList.size() > 0) {
					log.info("���ݲ�Ʒ��Ż�ȡ��Ʒ���� ��:" + productTypeList.size() + "��");
					// һ����Ʒ��Ӧһ����Ʒ����
					productCategory = productTypeList.get(0).get(
							"PRODUCT_CATEGORY")
							+ "";
				} else {
					Description += " ���ݲ�Ʒ��Ų�ѯ������";
				}
				// (2)���� Ӧ�����Ʒ (1)���� �������Ʒ
				if (productType.equals(productCategory)) {
					// ���ݲ�Ʒ��� ��ѯ ��Ʒά�ȱ�
					List<HashMap<String, Object>> Itemlist = allRecords
							.getItemCodeByProductId(productId);
					if (Itemlist != null && Itemlist.size() > 0) {
						log.info("���ݲ�Ʒ��� ��ѯ �ʷ�ģ�ͱ� ��:" + Itemlist.size() + "��");
						for (HashMap<String, Object> m : Itemlist) {
							itemCode = m.get("ITEM_CODE") + ""; // �õ�ά�ȱ���
							resourceId = m.get("RESOURCE_ID") + ""; // �õ���Դ���
							// ��������1 ���� һ�����շ�
							if ("1".equals(OperationType)) {
								double oneProduct = freecount
										.getAmountCount(productId, resourceId,
												itemCode, "0", 0); // ֮ǰ���ݵ��ǹ����š�Ӧ�ø������ڸ�Ϊά�ȱ�š�0
								log.info("���� ��Ʒ���ʷ�ģ�ͺͲ�Ʒ��Ӧ�ø����������Ϊ:"
										+ oneProduct + "Ԫ");
								total += Integer
										.parseInt(new DecimalFormat("0")
												.format(oneProduct));
							}
							// ��������2 ���� �����շ�
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
									log.info("������ �굥�� Ҫ�½�");
								}
								if (eventsList == null) {
									boolean b = BillingBaseDao.createTable(); // ������(����id,ʱ��,���ڱ��,�ͻ����,�ӿ�֮��ı�ʶ��,��Ʒ���,ʵ�۽��(1ʵ����2ʵ�����3ʵ��ʧ��),ʵ�۽��,��������,��ʱɨ��״̬)
									log.info("������״̬ ��" + b);
								}

								// ����
								double monthProduct = freecount
										.getAmountCount(productId, resourceId,
												itemCode, "0", 0);
								// �ѵ����Ĳ�Ʒ�ܶ�����list������ �������շѣ�
								// monthProductTotal.add(monthProduct);
								// ������¼����
								OrderInfoRecords record = new OrderInfoRecords();
								record.setOrderId(OrderID);
								record.setProductNo(productId);
								record.setMonthCost(monthProduct);
								RecordList.add(record);
								// �� List<������¼bean>�� ����
								if (RecordList != null && RecordList.size() > 0) {
									int days = Common.getThisMonthDays(); // �õ���ǰ�µ�����
									double totalAvgPrice = 0; // ���в�Ʒ�� �վ����ú�
									double totalMonthPrice = 0; // ���в�Ʒ������ú�
									for (OrderInfoRecords ors : RecordList) {
										// ���վ� = ������Ʒ���ܷ���(����) / ��������
										double avgPrice = ors.getMonthCost()
												/ days;
										// �õ����в�Ʒ�� �վ����ú�
										totalAvgPrice += avgPrice;
										// �õ����в�Ʒ������ú�
										totalMonthPrice += ors.getMonthCost();
									}
									int totalAvgPriceFormat = Integer
											.parseInt(new DecimalFormat("0")
													.format(totalAvgPrice));
									// �����ǰʱ���Ǳ������һ�죬�ͷ��ز�������Ǿͷ����վ�����
									if (days == Integer.parseInt(Common
											.getNowDay())) {
										// �����в�Ʒ�� �վ����ú� *����ǰ�µ�����-1��
										double totalPrices = totalAvgPriceFormat
												* (days - 1);
										// ��� =
										// (��ǰ���������в�Ʒ��������ܺ�)-(��ǰ���������в�Ʒ���վ����õ��ܺ�*(��ǰ�µ�����-1))
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
										log.info("��ѯ��Ҫ�������������T_Usage_Monthly��"
												+ usageMonthdlyList.size());
										try {
											// �� �ʷ������(T_USAGE_MONTHLY) ��Ӽ�¼
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
															+ "");// ʱ�۽��
											// �ֶ���(����id,���,�ͻ���š�����ۻ��³��ۡ��¿۽�������š���Ʒ��š���Ʒ���͡�ɨ��״̬,״̬,���������״̬)
											usageMonthlyDao
													.insertTUsageMonthly(usageMonthlyBean);
										} catch (Exception e) {
											flag = "false";
											// Description +=
											// " �� �ʷ��������Ӽ�¼ʧ�� ԭ��"
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

			// ����ǰ����շ� ���� �굥����̬�����£���Ӽ�¼ (ע:һ����ֻ�ܴ���һ�ű�)
			try {
				TUsageEventsBean usageEventsBean = new TUsageEventsBean();
				usageEventsBean.setBILLCYCLE_ID(BillCycleId); // �������
				usageEventsBean.setCUSTOMER_ID(customerId); // �ͻ�id
				usageEventsBean.setPRUDUCT_ID(productId); // ��Ʒid
				usageEventsBean.setORDER_ID(OrderID); // ����ID
				usageEventsBean.setACCOUNT(Integer.parseInt(total + "")); // ʱ�۽��
				usageEventsBean.setFEE_TYPE(true); // �Ƿ��շ�
				usageEventsBean.setREALOCKSTAT(1); // �۷�״̬
				usageMonthlyDao.insertTUsageEvents(usageEventsBean);
			} catch (Exception e) {
				flag = "false";
				// Description += " ��  �굥����̬�����£���Ӽ�¼ʧ�� ԭ��"
				// + e.getMessage();
				log.error(e.getMessage());
			}

			RecordList = null;
		} else {
			flag = "false";
			Description = "���ݴ˶�����Ų�ѯ������";
		}
		if ("2".equals(productType)) {
			try {
				// ���۷����۽ӿ���ϸ��(����ID,�ӿ�֮��ı�ʶ��,�������,��������,ʱ���,���۽��壩,���۽��,�Դ���/�쳣����ϸ������Ϣ,����ʱ��)
				// ��������
				grantPriceForChargeDao.insertTGrantPriceForcharge(Common
						.getStrSSS(), BillingID, OrderID, OperationType,
						TimeStamp, total + "", flag, Description, Common
								.getDateTimeSSS());
			} catch (Exception e) {
				flag = "false";
				// Description += " ���۷����۽ӿ���ϸ���������ʧ��";
				log.error(e.getMessage());
			}
		}
		String result = "";
		// "2"���� Ӧ�����Ʒ "1"���� �������Ʒ
		if ("2".equals(productType)) {
			// ƴ ά��Ԫ�� (ʱ������ӿ�֮��ı�ʶ�������۽��壩�����۽�����Դ���/�쳣����ϸ������Ϣ)
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
			log.info("���ص�JSON:" + reJson);
			result = reJson.substring(1, reJson.length() - 1);
			reJson = null;
			params = null;
			values = null;
		} else {
			result = total + "";
		}
		custList = null;
		list = null;
		productId = null; // ��Ʒ���
		productCategory = null; // ��Ʒ����
		itemCode = null; // ά�ȱ���
		customerId = null; // �ͻ����
		total = 0; // ���в�Ʒ���ܶ�
		Description = null; // ����
		flag = null; // ���۽��
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
		String customerId = ""; // �ͻ����
		int total = 0; // ���в�Ʒ���ܶ�
		// ��ǰ����
		String time = new SimpleDateFormat("yyyyMM")
				.format(new Date());
		// ���ݶ�����Ų�ѯ ������ �õ��ͻ����
		List<HashMap<String, Object>> custList = allRecords
				.getCustomerIdByOrderId(OrderID);
		if (custList != null && custList.size() > 0) {
			log.info("��ѯ������Ϣ�� ��:" + custList.size() + "��");
			// һ��������Ӧһ���ͻ�
			customerId = custList.get(0).get("CUSTOMER_ID") + "";
		}
		List<HashMap<String, Object>> list = new BillingQueryDao().getYZFR(
				OrderID, time);
		double money = 0;
		OrderDetailBean orderDetailBean = BillingQuerys
				.getProductNumber(OrderID);
		log.info("����Ϊ��" + OrderID + ",��ƷΪ" + orderDetailBean.getPRODUCT_ID()
				+ "��������ѵ�ά�����£�");
		logs.info("����Ϊ��" + OrderID + ",��ƷΪ" + orderDetailBean.getPRODUCT_ID()
				+ "��������ѵ�ά�����£�");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String itemName=list.get(i).get("ITEM_NAME")+"";
				String itemCode=list.get(i).get("ITEM_CODE")+"";
				String fixedValue=list.get(i).get("FIXED_VALUE")+"";
				money += Double
						.parseDouble(list.get(i).get("FIXED_VALUE") + "");
				log.info("ά������:" + itemName + ",ά�ȱ��룺"
						+  itemCode+ ",ά�ȵ�����ѣ�"
						+ fixedValue);
				logs.info("ά������:" + itemName + ",ά�ȱ��룺"
						+  itemCode+ ",ά�ȵ�����ѣ�"
						+ fixedValue);
			}
		}
		log.info("����Ϊ��" + OrderID + "���ܽ��Ϊ" + money);
		logs.info("����Ϊ��" + OrderID + "���ܽ��Ϊ" + money);
		String dayStr = Common.getDateStr();

		// ��ѯ�����굥���Ƿ���ڣ���������ھʹ��������ڲ�������ÿ�¿۷���Ϣ��
		List<HashMap<String, Object>> eventsList = null;
		try {
			eventsList = GetBillingBaseDao().doSelect(
					"select ID from T_USAGE_EVENTS_"
							+ dayStr.substring(0, dayStr.length() - 2));
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("������ �굥�� Ҫ�½�");
		}
		if (eventsList == null) {
			boolean b = BillingBaseDao.createTable(); // ������(����id,ʱ��,���ڱ��,�ͻ����,�ӿ�֮��ı�ʶ��,��Ʒ���,ʵ�۽��(1ʵ����2ʵ�����3ʵ��ʧ��),ʵ�۽��,��������,��ʱɨ��״̬)
			log.info("������״̬ ��" + b);
		}

		// monthProductTotal.add(monthProduct);
		int totalAvgPriceFormat=0;
		int days = Common.getThisMonthDays(); // �õ���ǰ�µ�����
//		if (days != Integer.parseInt(Common.getNowDay())){
			// ���վ� = �������ܷ���(����) / ��������
			double avgPrice = money / days;
			// ����ת��
			totalAvgPriceFormat = Integer.parseInt(new DecimalFormat("0").format(avgPrice));
			logs.info("�վ���"+totalAvgPriceFormat);
			log.info("�վ���"+totalAvgPriceFormat);
//		}
		// �����ǰʱ���Ǳ������һ�죬�ͷ��ز�������Ǿͷ����վ�����
		if (days == Integer.parseInt(Common.getNowDay())) {
			// �����ж����� �վ����ú� *����ǰ�µ�����-1��
			double totalPrices = totalAvgPriceFormat * (days - 1);
			logs.info("�ѿ۷ѽ�"+totalPrices);
			log.info("�ѿ۷ѽ�"+totalPrices);
			// ��� = (��ǰ���������в�Ʒ��������ܺ�)-(��ǰ���������в�Ʒ���վ����õ��ܺ�*(��ǰ�µ�����-1))
			double balancePrice = money - totalPrices;
			total = Integer.parseInt(new DecimalFormat("0")
					.format(balancePrice));
			logs.info("�������һ�����۵ķ���Ϊ��"+total+"(����Ĳ��)");
			log.info("�������һ�����۵ķ���Ϊ�� "+total+"(����Ĳ��)");
		} else {
			total = totalAvgPriceFormat;
			logs.info("�����ܷ��ó��Ե��µ������õ�ÿ�����۵��վ�ֵΪ��"+total);
			log.info("�����ܷ��ó��Ե��µ������õ�ÿ�����۵��վ�ֵΪ��"+total);
		}

		List<HashMap<String, Object>> usageMonthdlyList = usageEventsDao
				.getUsageMonthly(OrderID, BillCycleId);
		if (usageMonthdlyList != null && usageMonthdlyList.size() < 1) {
			log.info("��ѯ��Ҫ�������������T_Usage_Monthly��" + usageMonthdlyList.size());
			try {
				// �� �ʷ������(T_USAGE_MONTHLY) ��Ӽ�¼
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
				usageMonthlyBean.setUNIT_RATE(money + "");// ʱ�۽��
				// �ֶ���(����id,���,�ͻ���š�����ۻ��³��ۡ��¿۽�������š���Ʒ��š���Ʒ���͡�ɨ��״̬,״̬,���������״̬)
				usageMonthlyDao.insertTUsageMonthly(usageMonthlyBean);
			} catch (Exception e) {
				// Description +=
				// " �� �ʷ��������Ӽ�¼ʧ�� ԭ��"
				// + e.getMessage();
				log.error(e.getMessage());
			}
		}

		// ����ǰ����շ� ���� �굥����̬�����£���Ӽ�¼ (ע:һ����ֻ�ܴ���һ�ű�)
		try {
			TUsageEventsBean usageEventsBean = new TUsageEventsBean();
			usageEventsBean.setBILLCYCLE_ID(BillCycleId); // �������
			usageEventsBean.setCUSTOMER_ID(customerId); // �ͻ�id
			usageEventsBean.setPRUDUCT_ID(""); // ��Ʒid
			usageEventsBean.setORDER_ID(OrderID); // ����ID
			usageEventsBean.setACCOUNT(Integer.parseInt(total + "")); // ʱ�۽��
			usageEventsBean.setFEE_TYPE(true); // �Ƿ��շ�
			usageEventsBean.setREALOCKSTAT(1); // �۷�״̬
			usageMonthlyDao.insertTUsageEvents(usageEventsBean);
		} catch (Exception e) {
			// Description += " ��  �굥����̬�����£���Ӽ�¼ʧ�� ԭ��"
			// + e.getMessage();
			log.error(e.getMessage());
		}
		String result = total + "";
		/*
		 * // ƴ ά��Ԫ�� (ʱ������ӿ�֮��ı�ʶ�������۽��壩�����۽�����Դ���/�쳣����ϸ������Ϣ) List<String>
		 * params = new ArrayList<String>(); List<String> values = new
		 * ArrayList<String>(); params.add("TimeStamp");
		 * params.add("BillingID"); params.add("Amount"); params.add("Result");
		 * params.add("Description");
		 * 
		 * values.add(TimeStamp); values.add(BillingID); values.add(total + "");
		 * values.add(flag); values.add(Description); String reJson =
		 * Common.changeJSON(params, values); log.info("���ص�JSON:" + reJson);
		 * result = reJson.substring(1, reJson.length() - 1);
		 */

		// �����ڴ�ռ��
		custList = null;
		customerId = null; // �ͻ����
		total = 0; // ���в�Ʒ���ܶ�

		// ���ؽ��
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new AppCalculateCharage().CommonFeeN(
				"1673140530000243", "2014-05-30 12:00:00"));
	}
}
