package com.zhcs.billing.feerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhcs.billing.use.dao.BillingMethod;
import com.zhcs.billing.util.BaseDao;

/**
 * ����ģ�ͺ��Żݲ��ԵĹ����㷨
 * 
 * @author ������
 * 
 */
public class PriceModelAndDiscountCulDao {

	private BaseDao getBaseDao() {
		return new BaseDao();
	}

	/**
	 * �����ܷ���
	 * 
	 * @param PRODUCT_ID
	 *            ��Ʒ���
	 * @param RESOURCE_ID
	 *            ��Ʒ��Դ���
	 * @param ITEM_CODE
	 *            ��Ʒά�ȱ���
	 * @param useValue
	 *            �û�ʹ����
	 * @param PRICE
	 *            ��Ʒά�ȵ���
	 * @return
	 */
	public double getAmountCount(String PRODUCT_ID, String RESOURCE_ID,
			String ITEM_CODE, String useValue, int PRICE) {

		double amount = 0;
		double usevalue = 0;
		if (useValue != null && !"".equals(useValue)) {
			usevalue = Double.parseDouble(useValue);
		}
		// ��ѯ�ʷѱ���µļƷ�ģ������ MIN_X <= ? AND (MAX_X >? OR MAX_X=-1) AND
		String sql = " SELECT m.*,r.RULE_CODE FROM PRICE_MODEL m INNER JOIN PRICE_RULE r ON m.RULE_ID = r.RULE_ID WHERE m.PRODUCT_ID = ? AND m.RESOURCE_ID =?  AND m.ITEM_CODE = ? AND  m.MODEL_STATUS = 1";
		List params = new ArrayList();
		params.add(PRODUCT_ID);// ��Ʒ���
		params.add(RESOURCE_ID);// ��Ʒ��Դ���
		params.add(ITEM_CODE);// ά�ȱ���
		/*
		 * params.add(Integer.parseInt(useValue));
		 * params.add(Integer.parseInt(useValue));
		 */

		List<HashMap<String, Object>> list = getBaseDao().doSelect(sql, params);
		if (list!= null && list.size() > 0) {
			boolean flat = true;
			while (flat) {
				for (int i = 0; i < list.size(); i++) {
					Map map1 = list.get(i);
					String billingWay = map1.get("BILLING_WAY") + "";// �Ʒѷ�ʽ

					// �Ʒѷ�ʽΪ 0 - ��ʱ�� ȡ�̶�ֵ
					if ("1".equals(billingWay)) {
						amount = Double.parseDouble(map1.get("FIXED_VALUE") + "");
						flat = false;
					}
					// �Ʒѷ�ʽΪ 2 - �̶����ʡ�4 - ����ʽ
					if ("2".equals(billingWay) || "4".equals(billingWay)) {
						// ȡ�ø÷��ʱ�ŵķ���ģ���Ƿ�����ռ���Сֵ(��СֵΪY/N �����ֵΪ N/Y)
						String INCLUDE_MIN_X = (String) map1.get("INCLUDE_MIN_X");
						String INCLUDE_MAX_X = (String) map1.get("INCLUDE_MAX_X");

						double MIN_X = 0;
						if (!map1.get("MIN_X").equals("")&& map1.get("MIN_X") != null) {
							MIN_X = Double.parseDouble(map1.get("MIN_X") + "");// ������Сֵ
						}
						double MAX_X = 0;
						if (!map1.get("MAX_X").equals("")&& map1.get("MAX_X") != null) {
							MAX_X = Double.parseDouble(map1.get("MAX_X") + "");// �������ֵ
						}
						// ���ռ���Сֵ
						if ("Y".equals(INCLUDE_MIN_X)) {
							// ���ʹ�������ڵ��ڿռ����ֵ����С�ڿռ���Сֵ˵�����ڸ�map��������
							// ������������list��ɾ��
							if (MAX_X < usevalue || usevalue < MIN_X) {
								if (MAX_X != -1) {
									list.remove(map1);
								}
							}
						} else {// INCLUDE_MIN_X =="N"
							if (MAX_X < usevalue || usevalue <= MIN_X) {
								list.remove(map1);
							}
						}
						// // ���ռ���Сֵ
						// if ("Y".equals(INCLUDE_MIN_X)) {
						// // ���ʹ�������ڵ��ڿռ����ֵ����С�ڿռ���Сֵ˵�����ڸ�map��������
						// // ������������list��ɾ��
						// if (MAX_X <= usevalue || usevalue < MIN_X) {
						// if (MAX_X!=-1) {
						// list.remove(map1);
						// }
						// }
						// } else {// INCLUDE_MIN_X =="N"
						// if (MAX_X < usevalue || usevalue <= MIN_X) {
						// list.remove(map1);
						// }
						// }

						if (list.size() == 1) {
							Map map2 = list.get(0);
							double FIXED_VALUE = 0;// ����ֵ
							if (!map2.get("FIXED_VALUE").equals("") && map2.get("FIXED_VALUE") != null) {
								FIXED_VALUE = Double.parseDouble(map2.get("FIXED_VALUE") + "");
								FIXED_VALUE = 0;
							}
							double INIT_VALUE = 0;// ��ʼֵ
							if (!map2.get("INIT_VALUE").equals("") && map2.get("INIT_VALUE") != null) {
								INIT_VALUE = Double.parseDouble(map2.get("INIT_VALUE") + "");
							}
							double MIN_X1 = 0;// ȷ����������С����ֵ
							if (!map2.get("MIN_X").equals("") && map2.get("MIN_X") != null) {
								MIN_X1 = Double.parseDouble(map2.get("MIN_X")+ "");
							}
							// �̶����� = ����ֵ * ����
							double INCREMENT_VALUE = 0;// ����ֵ
							if (!map2.get("INCREMENT_VALUE").equals("") && map2.get("INCREMENT_VALUE") != null) {
								INCREMENT_VALUE = Double.parseDouble(map2.get("INCREMENT_VALUE") + "");
							}
							double STEP_SIZE = 0;// ����
							if (!map2.get("STEP_SIZE").equals("") && map2.get("STEP_SIZE") != null) {
								STEP_SIZE = Double.parseDouble(map2.get("STEP_SIZE") + "");
								if (STEP_SIZE == 0) {
									STEP_SIZE = 1;
								}
							}
							// ͨ�ù�ʽ: ���յķ��� = �̶��� + ��ʼֵ + ( ʹ��ֵ - ������Сֵ ) / �̶�����
							amount = FIXED_VALUE + INIT_VALUE+ (usevalue - MIN_X1) * (INCREMENT_VALUE / STEP_SIZE);
							if (!"PR-PT".equals(map2.get("RULE_CODE").toString())) {//����ͨ�Ʒѹ���
								amount = 0;
							}
							flat = false;
							//
						}
					}
				}
			}
		} else {// û�ж�Ӧ���ʷ�ģ��
			amount = BillingMethod.mul(usevalue, Double.parseDouble(PRICE + ""));
		}
		return amount;
	}

	/**
	 * �Լ�����ܷ��ý����Ż�
	 * 
	 * @param amount
	 *            �ܷ���
	 * @param ruleId
	 *            ������
	 * @author yanglili
	 */
	public double getDiscountAmount(double amount, String PRODUCT_ID) {
		// �Żݺ�ķ���
		double discountAmount = 0;
		// ���ݹ����Ų�ѯ�Ż���Ϣ ��Ʒ��š�MIN_VALUE��MAX_VALUE
		String sql = " SELECT * FROM PRICE_DISCOUNT WHERE DISCOUNT_STATUS = 1 AND PRODUCT_ID = ? ";
		// String sql =
		// "SELECT * FROM SCBM_T.PRICE_DISCOUNT a INNER JOIN SCBM_T.PRICE_RULE b ON a.RULE_ID = b.RULE_ID WHERE a.PRODUCT_ID = ? AND a.DISCOUNT_STATUS = 1";
		List params = new ArrayList();
		params.add(PRODUCT_ID);
		List<HashMap<String, Object>> list = getBaseDao().doSelect(sql, params);

		if (list!= null&&list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				// //�������
				// String BILLING_WAY = map.get("BILLING_WAY")+"";
				// �ۿ���
				double RATE_VALUE = 0;
				if (!"".equals(map.get("RATE_VALUE"))&& map.get("RATE_VALUE") != null) {
					RATE_VALUE = Double.parseDouble(map.get("RATE_VALUE") + "");
				}
				// ��ʼ���
				// double MIN_VALUE = 0;
				// if(!"".equals(map.get("MIN_VALUE")) &&
				// map.get("MIN_VALUE")!=null){
				// MIN_VALUE =Double.parseDouble(map.get("MIN_VALUE")+"");
				// }
				// ��ֹ���
				// double MAX_VALUE = 0;
				// if(!"".equals(map.get("MAX_VALUE")) &&
				// map.get("MAX_VALUE")!=null){
				// MAX_VALUE =Double.parseDouble(map.get("MAX_VALUE")+"");
				// }
				// �Żݽ��
				double DISCOUNT_VALUE = 0;
				if (!"".equals(map.get("DISCOUNT_VALUE"))&& map.get("DISCOUNT_VALUE") != null) {
					DISCOUNT_VALUE = Double.parseDouble(map.get("DISCOUNT_VALUE") + "");
				}
				discountAmount = BillingMethod.sub(amount, DISCOUNT_VALUE) * RATE_VALUE * 0.01;
			}
		} else {
			discountAmount = amount;
		}
		return discountAmount;
	}
}
