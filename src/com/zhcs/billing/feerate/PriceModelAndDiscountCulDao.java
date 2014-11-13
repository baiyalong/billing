package com.zhcs.billing.feerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhcs.billing.use.dao.BillingMethod;
import com.zhcs.billing.util.BaseDao;

/**
 * 费率模型和优惠策略的公共算法
 * 
 * @author 杨丽丽
 * 
 */
public class PriceModelAndDiscountCulDao {

	private BaseDao getBaseDao() {
		return new BaseDao();
	}

	/**
	 * 计算总费用
	 * 
	 * @param PRODUCT_ID
	 *            产品编号
	 * @param RESOURCE_ID
	 *            产品资源编号
	 * @param ITEM_CODE
	 *            产品维度编码
	 * @param useValue
	 *            用户使用量
	 * @param PRICE
	 *            产品维度单价
	 * @return
	 */
	public double getAmountCount(String PRODUCT_ID, String RESOURCE_ID,
			String ITEM_CODE, String useValue, int PRICE) {

		double amount = 0;
		double usevalue = 0;
		if (useValue != null && !"".equals(useValue)) {
			usevalue = Double.parseDouble(useValue);
		}
		// 查询资费编号下的计费模型数据 MIN_X <= ? AND (MAX_X >? OR MAX_X=-1) AND
		String sql = " SELECT m.*,r.RULE_CODE FROM PRICE_MODEL m INNER JOIN PRICE_RULE r ON m.RULE_ID = r.RULE_ID WHERE m.PRODUCT_ID = ? AND m.RESOURCE_ID =?  AND m.ITEM_CODE = ? AND  m.MODEL_STATUS = 1";
		List params = new ArrayList();
		params.add(PRODUCT_ID);// 产品编号
		params.add(RESOURCE_ID);// 产品资源编号
		params.add(ITEM_CODE);// 维度编码
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
					String billingWay = map1.get("BILLING_WAY") + "";// 计费方式

					// 计费方式为 0 - 包时段 取固定值
					if ("1".equals(billingWay)) {
						amount = Double.parseDouble(map1.get("FIXED_VALUE") + "");
						flat = false;
					}
					// 计费方式为 2 - 固定费率、4 - 阶梯式
					if ("2".equals(billingWay) || "4".equals(billingWay)) {
						// 取得该费率编号的费率模型是否包含空间最小值(最小值为Y/N 则最大值为 N/Y)
						String INCLUDE_MIN_X = (String) map1.get("INCLUDE_MIN_X");
						String INCLUDE_MAX_X = (String) map1.get("INCLUDE_MAX_X");

						double MIN_X = 0;
						if (!map1.get("MIN_X").equals("")&& map1.get("MIN_X") != null) {
							MIN_X = Double.parseDouble(map1.get("MIN_X") + "");// 区间最小值
						}
						double MAX_X = 0;
						if (!map1.get("MAX_X").equals("")&& map1.get("MAX_X") != null) {
							MAX_X = Double.parseDouble(map1.get("MAX_X") + "");// 区间最大值
						}
						// 含空间最小值
						if ("Y".equals(INCLUDE_MIN_X)) {
							// 如果使用量大于等于空间最大值或者小于空间最小值说明不在该map的区间内
							// 将这条数据在list内删掉
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
						// // 含空间最小值
						// if ("Y".equals(INCLUDE_MIN_X)) {
						// // 如果使用量大于等于空间最大值或者小于空间最小值说明不在该map的区间内
						// // 将这条数据在list内删掉
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
							double FIXED_VALUE = 0;// 费用值
							if (!map2.get("FIXED_VALUE").equals("") && map2.get("FIXED_VALUE") != null) {
								FIXED_VALUE = Double.parseDouble(map2.get("FIXED_VALUE") + "");
								FIXED_VALUE = 0;
							}
							double INIT_VALUE = 0;// 起始值
							if (!map2.get("INIT_VALUE").equals("") && map2.get("INIT_VALUE") != null) {
								INIT_VALUE = Double.parseDouble(map2.get("INIT_VALUE") + "");
							}
							double MIN_X1 = 0;// 确定区间后的最小区间值
							if (!map2.get("MIN_X").equals("") && map2.get("MIN_X") != null) {
								MIN_X1 = Double.parseDouble(map2.get("MIN_X")+ "");
							}
							// 固定费率 = 增量值 * 步长
							double INCREMENT_VALUE = 0;// 增量值
							if (!map2.get("INCREMENT_VALUE").equals("") && map2.get("INCREMENT_VALUE") != null) {
								INCREMENT_VALUE = Double.parseDouble(map2.get("INCREMENT_VALUE") + "");
							}
							double STEP_SIZE = 0;// 步长
							if (!map2.get("STEP_SIZE").equals("") && map2.get("STEP_SIZE") != null) {
								STEP_SIZE = Double.parseDouble(map2.get("STEP_SIZE") + "");
								if (STEP_SIZE == 0) {
									STEP_SIZE = 1;
								}
							}
							// 通用公式: 最终的费用 = 固定费 + 起始值 + ( 使用值 - 区间最小值 ) / 固定费率
							amount = FIXED_VALUE + INIT_VALUE+ (usevalue - MIN_X1) * (INCREMENT_VALUE / STEP_SIZE);
							if (!"PR-PT".equals(map2.get("RULE_CODE").toString())) {//非普通计费规则
								amount = 0;
							}
							flat = false;
							//
						}
					}
				}
			}
		} else {// 没有对应的资费模型
			amount = BillingMethod.mul(usevalue, Double.parseDouble(PRICE + ""));
		}
		return amount;
	}

	/**
	 * 对计算的总费用进行优惠
	 * 
	 * @param amount
	 *            总费用
	 * @param ruleId
	 *            规则编号
	 * @author yanglili
	 */
	public double getDiscountAmount(double amount, String PRODUCT_ID) {
		// 优惠后的费用
		double discountAmount = 0;
		// 根据规则编号查询优惠信息 产品编号、MIN_VALUE、MAX_VALUE
		String sql = " SELECT * FROM PRICE_DISCOUNT WHERE DISCOUNT_STATUS = 1 AND PRODUCT_ID = ? ";
		// String sql =
		// "SELECT * FROM SCBM_T.PRICE_DISCOUNT a INNER JOIN SCBM_T.PRICE_RULE b ON a.RULE_ID = b.RULE_ID WHERE a.PRODUCT_ID = ? AND a.DISCOUNT_STATUS = 1";
		List params = new ArrayList();
		params.add(PRODUCT_ID);
		List<HashMap<String, Object>> list = getBaseDao().doSelect(sql, params);

		if (list!= null&&list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				// //规则编码
				// String BILLING_WAY = map.get("BILLING_WAY")+"";
				// 折扣率
				double RATE_VALUE = 0;
				if (!"".equals(map.get("RATE_VALUE"))&& map.get("RATE_VALUE") != null) {
					RATE_VALUE = Double.parseDouble(map.get("RATE_VALUE") + "");
				}
				// 起始金额
				// double MIN_VALUE = 0;
				// if(!"".equals(map.get("MIN_VALUE")) &&
				// map.get("MIN_VALUE")!=null){
				// MIN_VALUE =Double.parseDouble(map.get("MIN_VALUE")+"");
				// }
				// 截止金额
				// double MAX_VALUE = 0;
				// if(!"".equals(map.get("MAX_VALUE")) &&
				// map.get("MAX_VALUE")!=null){
				// MAX_VALUE =Double.parseDouble(map.get("MAX_VALUE")+"");
				// }
				// 优惠金额
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
