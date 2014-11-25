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
 * �Ʒѷ���
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
		System.out.println("�۷�" + s);
	}

	private static Logger log = LoggerFactory.getLogger(BillingMethod.class);
	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(BillingMethod.class);

	// ������,ɨ�跽ʽ,ʱ��
	public static synchronized String compute(OrderInfoBean orderInfoBean,
			String SCANNING_WAY, String timeInterval) {
		if (orderInfoBean.getORDER_ID() == null
				|| "".equals(orderInfoBean.getORDER_ID())
				|| timeInterval == null || "".equals(timeInterval)) {
			log.error("����Ĳ�������");
			logUtil.error("����Ĳ�������");
			return "0";
		}

		// ����һ�Ŷ�����ȡ��Ʒ���
		OrderDetailBean detailBean = BillingQuery
				.getProductNumber(orderInfoBean.getORDER_ID());
		try {
			if (detailBean == null || detailBean.getPRODUCT_ID() == null
					|| "".equals(detailBean.getPRODUCT_ID())) {
				log.error("������" + orderInfoBean.getORDER_ID() + "��û�в�Ʒ");
				logUtil.error("������" + orderInfoBean.getORDER_ID() + "��û�в�Ʒ");
				return "0";
			}
		} catch (Exception e) {
			log.error("������" + orderInfoBean.getORDER_ID() + "��ȡ��Ʒ�����쳣"
					+ e.getMessage());
			logUtil.error("������" + orderInfoBean.getORDER_ID() + "��ȡ��Ʒ�����쳣"
					+ e.getMessage());
			return "0";
		}
		// �������������еĲ�Ʒ��ȡ��Ʒ�����е���Դ��Ϣ
		List<ProductResourceBean> resourceBeans = BillingQuery
				.getResourceInfo(detailBean.getPRODUCT_ID());
		// ���ݲ�Ʒ��ȡ����γ����Ϣ
		List<ProductItemBean> itemBeans = BillingQuery
				.getProductItem(detailBean.getPRODUCT_ID());
		// ���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸ
		List<SubscriptionItemBean> subscriptionItemBeans = BillingQuery
				.getSubscriptionItems(detailBean.getSUBSCRIBER_ID(),
						detailBean.getPRODUCT_ID());
		for (ProductItemBean ItemBean : itemBeans) {
			for (SubscriptionItemBean subscription : subscriptionItemBeans) {
				if (ItemBean.getITEM_ID().equals(subscription.getITEM_ID())
						&& ItemBean.getPRODUCT_ID().equals(
								subscription.getPRODUCT_ID())) {
					ItemBean.setSI_ID(subscription.getSI_ID());// �깺��ϸ���
					ItemBean.setPD_ID(subscription.getPD_ID());// �ײ���ϸ���
					// ItemBean.setUSAGE_AMOUNT(subscription.getSUBSCRIBE_AMOUNT());//�깺��
					ItemBean.setITEM_AMOUNT(subscription.getREMAINING_AMOUNT());// ����ʣ����
				}
			}
		}
		subscriptionItemBeans = null;
		// ��ȡ�ۻ���
		BillingCumulative.getJson(orderInfoBean.getORDER_ID(), SCANNING_WAY,
				timeInterval);// ����,ɨ�跽ʽ,ʱ��

		// ���ݶ����ŵ��Ʒ���ϵͳ��ȡ��Ʒγ���ۻ��� ������ʱ�䡢ɨ�跽ʽ��
		List<TScanningAddTotalBean> addTotalBeans = BillingQuery.getCumulants(
				orderInfoBean, timeInterval, SCANNING_WAY);
		// ����ģ�ͺ��Żݲ��ԵĹ����㷨
		PriceModelAndDiscountCulDao model = new PriceModelAndDiscountCulDao();
		// ���ۻ�����ֵ��γ��
		for (ProductItemBean itemBean : itemBeans) {
			Map<String, Integer> map = new HashMap<String, Integer>();// �ۼ���map
			Map<String, Integer> map2 = new HashMap<String, Integer>();// ����ʹ����map
			for (TScanningAddTotalBean bean : addTotalBeans) {
				if (addTotalBeans == null || addTotalBeans.size() == 0) {
					log.info("������" + orderInfoBean.getORDER_ID()
							+ "û�в�ѯ����Ʒά���ۻ���");
					logUtil.info("������" + orderInfoBean.getORDER_ID()
							+ "û�в�ѯ����Ʒά���ۻ���");
				} else {
					try {
						if ("1".equals(bean.getSCANNING_WAY())) {
							// ��ͬγ����ȡ���һ��ͬһγ���ۻ��� ��Ʒγ�ȱ���
							if (itemBean.getITEM_CODE().equals(bean.getWD_NO())) {
								map.put(bean.getRESOURCE_ID(),
										bean.getWD_ADD_TOTAL());
								map2.put(bean.getRESOURCE_ID(),
										bean.getCURRENT_ADD_TOTAL());
							}
						} else if ("5".equals(bean.getSCANNING_WAY())) {// ��5����ɨ��
							// ��ͬγ���ۼ�Ϊͬһγ���ۻ��� ��Ʒγ�ȱ���
							if (itemBean.getITEM_CODE().equals(bean.getWD_NO())) {
								itemBean.setUSAGE_AMOUNT(itemBean
										.getUSAGE_AMOUNT()
										+ bean.getWD_ADD_TOTAL());
								itemBean.setCURRENT_ADD_TOTAL(itemBean
										.getCURRENT_ADD_TOTAL()
										+ bean.getCURRENT_ADD_TOTAL());// ͬγ�ȵĵ�����
							}
						}
					} catch (Exception e) {
						log.error("��Ʒά�ȣ�" + itemBean.getITEM_NAME()
								+ "�����ۻ��������쳣" + e.getMessage());
						logUtil.error("��Ʒά�ȣ�" + itemBean.getITEM_NAME()
								+ "�����ۻ��������쳣" + e.getMessage());
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

			// ����ò�Ʒά�����ײ�
			if (itemBean.getPD_ID() != null) {
				if (itemBean.getCURRENT_ADD_TOTAL() > 0) {
					// ʹ����-�ײ�ʣ����
					int ua = itemBean.getCURRENT_ADD_TOTAL()
							- itemBean.getITEM_AMOUNT();
					// �ײ�ʣ����-ʹ����
					int cat = itemBean.getITEM_AMOUNT()
							- itemBean.getCURRENT_ADD_TOTAL();
					itemBean.setITEM_AMOUNT(cat);// ��ʹ���������ײ�ʣ����ʱ��ȡ0
					itemBean.setMONEY(ua > 0 ? ua * itemBean.getPRICE() : 0);// ��ʹ���������ײ�ʣ����ʱ���������ְ���׼�����շ�
					itemBean.setTwoMONEY(itemBean.getMONEY());// ��������
					// �������ݿ� �깺��ϸ��ţ��ײ�ʣ����
					BillingQuery.updateSubscriptionItem(itemBean.getSI_ID(),
							itemBean.getITEM_AMOUNT());
					log.info(itemBean.getITEM_NAME()
							+ "�ۻ�����"
							+ itemBean.getUSAGE_AMOUNT()
							+ ",�ײͰ�ʣ��������"
							+ (itemBean.getITEM_AMOUNT() > 0 ? itemBean
									.getITEM_AMOUNT() : 0) + ",������:"
							+ (ua > 0 ? ua : 0) + ",�������ַ��ã�"
							+ itemBean.getMONEY() + "��");
					logUtil.info(itemBean.getITEM_NAME()
							+ "�ۻ�����"
							+ itemBean.getUSAGE_AMOUNT()
							+ ",�ײͰ���ʣ��������"
							+ (itemBean.getITEM_AMOUNT() > 0 ? itemBean
									.getITEM_AMOUNT() : 0) + ",�������ַ��ã�"
							+ itemBean.getMONEY() + "��");
				}
			} else {// �ò�Ʒû���ײ�
				if (itemBean.getUSAGE_AMOUNT() > 0) {
					// �����Ʒγ�ȱ�׼����
					itemBean.setMONEY(itemBean.getUSAGE_AMOUNT()
							* itemBean.getPRICE());// ��׼����
					log.info(itemBean.getITEM_NAME() + "�ۻ�����"
							+ itemBean.getUSAGE_AMOUNT() + ",���ۣ�"
							+ itemBean.getPRICE() + "������׼���۷���:"
							+ itemBean.getMONEY() + "��");
					logUtil.info(itemBean.getITEM_NAME() + "�ۻ�����"
							+ itemBean.getUSAGE_AMOUNT() + ",���ۣ�"
							+ itemBean.getPRICE() + "������׼���۷���:"
							+ itemBean.getMONEY() + "��");
					// �����Ʒά�ȶ�������
					double money = model.getAmountCount(
							itemBean.getPRODUCT_ID(),
							itemBean.getRESOURCE_ID(), itemBean.getITEM_CODE(),
							itemBean.getUSAGE_AMOUNT() + "",
							itemBean.getPRICE());
					itemBean.setTwoMONEY(money);// ��������
					log.info(itemBean.getITEM_NAME() + "�ۻ�����"
							+ itemBean.getUSAGE_AMOUNT() + ",�����������ۣ�" + money
							+ "��");
					logUtil.info(itemBean.getITEM_NAME() + "�ۻ�����"
							+ itemBean.getUSAGE_AMOUNT() + ",�����������ۣ�" + money
							+ "��");
				}
			}
		}

		// ��γ�ȼƷѵ��������۽����ӵ���Դ
		for (ProductResourceBean resource : resourceBeans) {
			for (ProductItemBean item : itemBeans) {
				try {
					if (resource.getRESOURCE_ID().equals(item.getRESOURCE_ID())) {
						resource.setMONEY(add(resource.getMONEY(),
								item.getMONEY()));// ��Ʒγ�ȱ�׼����
						resource.setTwoMONEY(add(resource.getTwoMONEY(),
								item.getTwoMONEY()));// ��Ʒγ�ȶ�������
						log.info("��Ʒ��Դ" + resource.getRESOURCE_NAME()
								+ "�ı�׼����Ϊ��" + resource.getMONEY() + "��,��������Ϊ��"
								+ resource.getTwoMONEY() + "��++");
						logUtil.info("��Ʒ��Դ" + resource.getRESOURCE_NAME()
								+ "�ı�׼����Ϊ��" + resource.getMONEY() + "��,��������Ϊ��"
								+ resource.getTwoMONEY() + "��++");
					}
				} catch (Exception e) {
					log.error("�Ѳ�Ʒά�ȣ�" + item.getITEM_NAME() + "�ķ�����ӵ���Ʒ��Դ��"
							+ resource + "�����쳣��" + e.getMessage());
					logUtil.error("�Ѳ�Ʒά�ȣ�" + item.getITEM_NAME()
							+ "�ķ�����ӵ���Ʒ��Դ��" + resource + "�����쳣��"
							+ e.getMessage());
				}

			}
		}
		// ��ȡ��Դ���ڵ���
		String root = BillingQuery.getRootResourceInfo(detailBean
				.getPRODUCT_ID());
		// ���ڵ���Դ
		List<ProductResourceBean> rootResources = new ArrayList<ProductResourceBean>();
		for (ProductResourceBean productResourceBean : resourceBeans) {
			if (productResourceBean.getPARENT_ID().equals(root)) {// ����Դ
				rootResources.add(productResourceBean);
				computingResources(productResourceBean);
			}
		}
		ProductInfoBean infoBean = new ProductInfoBean();
		infoBean.setPRODUCT_ID(detailBean.getPRODUCT_ID());
		for (ProductResourceBean rootResource : rootResources) {
			// ����Դ�Ķ������۽�����Ϊ��Ʒ�������۽��
			if (infoBean.getPRODUCT_ID().equals(rootResource.getPRODUCT_ID())) {
				infoBean.setMONEY(infoBean.getMONEY() + rootResource.getMONEY());// һ������
				infoBean.setTwoMONEY(infoBean.getTwoMONEY()
						+ rootResource.getTwoMONEY());// ��������
				TCulOrderDetailBean bean = new TCulOrderDetailBean();
				bean.setID(Common.createBillingID());// ����ID
				bean.setORDER_ID(orderInfoBean.getORDER_ID());// ������
				bean.setCODE(rootResource.getRESOURCE_ID());// ��Դ���
				bean.setCTYPE("1");// 0�����Ʒ��1������Դ
				bean.setSCANNING_TIME(timeInterval);// �Ʒ���ϵͳɨ�趩������ʱ��
				bean.setSCANNING_WAY(SCANNING_WAY);// ɨ�跽ʽ
				bean.setBEFORE_AMOUNT(String.valueOf(rootResource.getMONEY()));// ��Դ�ʷ�ģ��ǰ���
				bean.setAFTER_AMOUNT(String.valueOf(rootResource.getTwoMONEY())); // ��Դ�ʷ�ģ�ͺ���
				BillingInsert.AddTCulOrderDetail(bean);// ���Ʒ�ϵͳ���ݿ��������Ʒ��Դ�����������ۼ�¼����Ӽ�¼
				log.info("��Դ��" + rootResource.getRESOURCE_NAME() + "��׼���۷��ý�"
						+ rootResource.getMONEY() + "��,�������۷��ý�"
						+ rootResource.getTwoMONEY() + "�塣");
				logUtil.info("��Դ��" + rootResource.getRESOURCE_NAME()
						+ "��׼���۷��ý�" + rootResource.getMONEY() + "��,�������۷��ý�"
						+ rootResource.getTwoMONEY() + "�塣");
			}
		}
		// ���㶩������Ʒ���
		TCulOrderDetailBean bean = new TCulOrderDetailBean();
		bean.setID(Common.createBillingID());// ����ID
		bean.setORDER_ID(orderInfoBean.getORDER_ID());// ������
		bean.setCODE(infoBean.getPRODUCT_ID());// ��Ʒ���
		bean.setCTYPE("0");// 0�����Ʒ��1������Դ
		bean.setSCANNING_TIME(timeInterval);// �Ʒ���ϵͳɨ�趩������ʱ��
		bean.setSCANNING_WAY(SCANNING_WAY);// ɨ�跽ʽ
		// �����Ʒ�Żݺ���
		double SUMARY = model.getDiscountAmount(infoBean.getTwoMONEY(),
				infoBean.getPRODUCT_ID());
		infoBean.setSUMARY(SUMARY);

		bean.setBEFORE_AMOUNT(String.valueOf(infoBean.getTwoMONEY()));// �Ż�ǰ���
		bean.setAFTER_AMOUNT(String.valueOf(infoBean.getSUMARY())); // �Żݺ���
		bean.setCOUNT_COST(bean.getAFTER_AMOUNT());// �ۼƼ������
		log.info("��Ʒ��" + infoBean.getPRODUCT_ID() + "�Ż�ǰ�Ľ��Ϊ"
				+ infoBean.getTwoMONEY() + "�壬�Żݺ�Ľ��Ϊ��" + infoBean.getSUMARY()
				+ "�塣");
		logUtil.info("��Ʒ��" + infoBean.getPRODUCT_ID() + "�Ż�ǰ�Ľ��Ϊ"
				+ infoBean.getTwoMONEY() + "�壬�Żݺ�Ľ��Ϊ��" + infoBean.getSUMARY()
				+ "�塣");
		// ���ݶ���\ʱ�����µ�һ�� ȡ���Ѿ��۷�
		// ��ѯ�����ϴο۷����
		List<TCulOrderDetailBean> culOrderDetailBeans = BillingQuery
				.getCulOrderDetailBeans(orderInfoBean.getORDER_ID(),
						timeInterval, SCANNING_WAY);
		// �Ѿ��۳�����DEDUCT_COST
		for (TCulOrderDetailBean tCulOrderDetailBean : culOrderDetailBeans) {
			try {
				// ���ڸò�Ʒ���ϴμ�¼
				if ("0".equals(tCulOrderDetailBean.getCTYPE())) {
					bean.setDEDUCT_COST(add(
							new Double(tCulOrderDetailBean.getDEDUCT_COST()),
							new Double(tCulOrderDetailBean.getREALITY()))
							.toString());
					break;
				}
			} catch (Exception e) {
				log.error("���Ҷ���:" + bean.getORDER_ID()
						+ "�Ѿ��۳�����DEDUCT_COST�����쳣��" + e.getMessage());
				logUtil.error("���Ҷ���:" + bean.getORDER_ID()
						+ "�Ѿ��۳�����DEDUCT_COST�����쳣��" + e.getMessage());
			}
		}
		// ��������µĵ�һ����û��ǰһ����¼
		if ("".equals(bean.getDEDUCT_COST()) || null == bean.getDEDUCT_COST()) {
			bean.setDEDUCT_COST("0");
		}
		// ����Ӧ�ÿ۵ķ���
		// ���ο۷�REALITY=�Żݺ���-�ѿ۽��
		Double reality = sub(infoBean.getSUMARY(),
				new Double(bean.getDEDUCT_COST()));
		Long moneyl = Math.round(reality);// doubleתlong ��������
		bean.setREALITY(moneyl > 0 ? moneyl.toString() : "0");
		BillingInsert.AddTCulOrderDetail(bean);// ���Ʒ�ϵͳ���ݿ��������Ʒ�������ۼ�¼����Ӽ�¼
		log.info("������" + bean.getORDER_ID() + "���ο۷ѣ�" + moneyl + "��(�Żݺ��"
				+ infoBean.getSUMARY() + "��,��ǰ�Ѿ��۷ѽ�" + bean.getDEDUCT_COST()
				+ "��)");
		logUtil.info("������" + bean.getORDER_ID() + "���ο۷ѣ�" + moneyl + "��(�Żݺ��"
				+ infoBean.getSUMARY() + "��,��ǰ�Ѿ��۷ѽ�" + bean.getDEDUCT_COST()
				+ "��)");
		return bean.getREALITY();// �����˴�ɨ��ʵ�ʿ۷� ��λ��
	}

	/**
	 * ������Դ�Ķ������۽��
	 * 
	 * @param bean
	 */
	public static void computingResources(ProductResourceBean bean) {
		List<ProductResourceBean> list = bean.getProductResourceBeans();
		for (ProductResourceBean resourceBean : list) {
			computingResources(resourceBean);
			// һ�����۵Ľ��
			bean.setMONEY(add(bean.getMONEY(), resourceBean.getMONEY()));
			// �ʷ�ģ�͵Ľ��
			bean.setTwoMONEY(add(bean.getTwoMONEY(), resourceBean.getTwoMONEY()));
			if (resourceBean.getNODE_TYPE() != 8) {
				log.info("��Ʒ��Դ" + resourceBean.getRESOURCE_NAME() + "�ı�׼����Ϊ��"
						+ bean.getMONEY() + "��,��������Ϊ��" + bean.getTwoMONEY()
						+ "��--");
				logUtil.info("��Ʒ��Դ" + resourceBean.getRESOURCE_NAME()
						+ "�ı�׼����Ϊ��" + bean.getMONEY() + "��,��������Ϊ��"
						+ bean.getTwoMONEY() + "��--");
			}
		}
	}

	private static final int DEF_DIV_SCALE = 10;

	/**
	 * ����double�����
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
	 * ����Double�����
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
	 * ����Double�����
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
	 * ����Double�����
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
