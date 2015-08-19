package com.zhcs.billing.use.dao;

public class SqlString {
	// ���ݶ����Ų�ѯ��Ʒ��ţ���Ч�깺��Ʒ��//
	public static String getProductNumber = "SELECT od.ORDER_ID,od.SUBSCRIBER_ID,od.PRODUCT_ID FROM ORDER_DETAIL od INNER JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID=si.SUBSCRIBER_ID where od.ORDER_ID = ? AND si.SUBSCRIBER_STATUS = 3;";

	// ���ݶ����Ų�ѯ�깺�ײͱ��-�ײ��ʷѣ���Ч�깺��
	public static String getSubscriptionPackageNumber = "SELECT * FROM ORDER_DETAIL od c JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID = si.SUBSCRIBER_ID "
			+ "INNER JOIN SUBSCRIPTION_PACKAGE sp ON od.SUBSCRIBER_ID = sp.SUBSCRIBER_ID "
			+ "WHERE od.ORDER_ID = ? AND si.SUBSCRIBER_STATUS = 3 AND sp.SP_STATUS = 1 AND sp.VALID_FROM <= ? AND sp.VALID_TILL >= ?";

	// ���ݶ�����Ų�ѯ�ײ��ʷ�
	public static String getTCZF = "SELECT sum(pp.PACKAGE_PRICE) as SUM FROM ORDER_DETAIL od "
			+ "INNER JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID = si.SUBSCRIBER_ID AND si.SUBSCRIBER_STATUS = 3 "
			+ "AND od.ORDER_ID = ?  AND si.BILLING_DATE <= ? "
			+ "INNER JOIN SUBSCRIPTION_PACKAGE sp ON od.SUBSCRIBER_ID = sp.SUBSCRIBER_ID "
			+ "INNER JOIN PRODUCT_PACKAGE pp ON sp.PACKAGE_ID = pp.PACKAGE_ID";

	// ���ݶ�����Ų�ѯ��װ��
	public static String getCZF = "SELECT sum(pm.FIXED_VALUE) as SUM FROM ORDER_DETAIL od "
			+ "INNER JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID = si.SUBSCRIBER_ID AND si.SUBSCRIBER_STATUS = 3 AND od.ORDER_ID = ?  AND si.BILLING_DATE <= ? "
			+ "INNER JOIN SUBSCRIPTION_ITEM sitem ON od.SUBSCRIBER_ID = sitem.SUBSCRIBER_ID "
			+ "INNER JOIN SUBSCRIPTION_RESOURCE sr ON sitem.SR_ID = sitem.SR_ID "
			+ "INNER JOIN PRICE_MODEL pm ON sitem.PRODUCT_ID = pm.PRODUCT_ID AND sr.RESOURCE_ID = pm.RESOURCE_ID AND sitem.ITEM_ID = pm.ITEM_ID "
			+ "INNER JOIN PRICE_RULE pr ON pm.RULE_ID = pr.RULE_ID AND pr.RULE_CODE = 'PR-CZF'";

	// ���ݶ�����Ų�ѯ�����
	public static String getYZF = "SELECT sum(pm.FIXED_VALUE) as SUM FROM ORDER_DETAIL od "
			+ "INNER JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID = si.SUBSCRIBER_ID AND si.SUBSCRIBER_STATUS = 3 AND od.ORDER_ID = ?  AND si.BILLING_DATE <= ? "
			+ "INNER JOIN SUBSCRIPTION_ITEM sitem ON od.SUBSCRIBER_ID = sitem.SUBSCRIBER_ID "
			+ "INNER JOIN SUBSCRIPTION_RESOURCE sr ON sitem.SR_ID = sitem.SR_ID "
			+ "INNER JOIN PRICE_MODEL pm ON sitem.PRODUCT_ID = pm.PRODUCT_ID AND sr.RESOURCE_ID = pm.RESOURCE_ID AND sitem.ITEM_ID = pm.ITEM_ID "
			+ "INNER JOIN PRICE_RULE pr ON pm.RULE_ID = pr.RULE_ID AND pr.RULE_CODE = 'PR-YZF'";

	// ���ݲ�Ʒ��Ų�ѯ��Դ//
	public static String getProductResource = "SELECT RESOURCE_ID,PRODUCT_ID,RESOURCE_NAME,NODE_TYPE,PARENT_ID FROM PRODUCT_RESOURCE WHERE PRODUCT_ID = ? AND RESOURCE_STATUS = 1";
	// ���ݲ�Ʒ��Ų�ѯά��//
	public static String getProductItem = "SELECT ITEM_ID,PRODUCT_ID,RESOURCE_ID,ITEM_CODE,ITEM_NAME,PRICE FROM PRODUCT_ITEM WHERE PRODUCT_ID = ? AND DETAIL_STATUS = 1";
	// �����깺��š���Ʒ��š�ɨ��ʱ���ѯ�깺��ϸ
	public static String getSubscriptionItem = "SELECT PRODUCT_ID,SI_ID,SR_ID,ITEM_ID,PD_ID,SUBSCRIBE_AMOUNT,REMAINING_AMOUNT FROM SUBSCRIPTION_ITEM WHERE SUBSCRIBER_ID = ? AND PRODUCT_ID = ?";
	// ���ݶ�����ѯ�����ϴο۷����//
	public static String getCulOrderDetail = "SELECT ORDER_ID,CODE,CTYPE,DEDUCT_COST,REALITY FROM T_CUL_ORDER_DETAIL WHERE CTYPE='0' AND ORDER_ID = ? AND SCANNING_WAY = ?  AND date_format(SCANNING_TIME,'%Y-%m') = date_format(?,'%Y-%m')  ORDER BY CREATE_TIME DESC";

	// ���ݶ�����Ų�ѯ�����(������)

	public static String getYZFR = "SELECT ITM.ITEM_NAME,pm.ITEM_CODE,pm.FIXED_VALUE FROM ORDER_DETAIL od "
			+ "INNER JOIN PRODUCT_INFO P ON P.PRODUCT_ID=od.PRODUCT_ID and P.PRODUCT_CATEGORY='1' "
			+ "INNER JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID = si.SUBSCRIBER_ID AND si.SUBSCRIBER_STATUS = 3 AND od.ORDER_ID = ?  AND si.BILLING_DATE <= ? "
			+ "INNER JOIN SUBSCRIPTION_ITEM sitem ON od.SUBSCRIBER_ID = sitem.SUBSCRIBER_ID "
			+ " AND sitem.SUBSCRIBE_AMOUNT>0 "
			+ "INNER JOIN SUBSCRIPTION_RESOURCE sr ON sitem.SR_ID = sitem.SR_ID "
			+ "INNER JOIN PRICE_MODEL pm ON sitem.PRODUCT_ID = pm.PRODUCT_ID AND sr.RESOURCE_ID = pm.RESOURCE_ID AND sitem.ITEM_ID = pm.ITEM_ID "
			+ "INNER JOIN PRICE_RULE pr ON pm.RULE_ID = pr.RULE_ID AND pr.RULE_CODE = 'PR-YZF' "
			+ "and MODEL_STATUS=1 LEFT JOIN PRODUCT_ITEM ITM ON pm.ITEM_CODE=ITM.ITEM_CODE GROUP BY pm.ITEM_ID ";

	/*
	 * public static String getYZFR =
	 * "SELECT ITM.ITEM_NAME,T.ITEM_CODE,T.FIXED_VALUE FROM ORDER_DETAIL od                              "
	 * +
	 * "INNER JOIN PRODUCT_INFO P ON P.PRODUCT_ID=od.PRODUCT_ID                                            "
	 * +
	 * "INNER JOIN SUBSCRIPTION_INFO si ON od.SUBSCRIBER_ID = si.SUBSCRIBER_ID AND si.SUBSCRIBER_STATUS = 3 "
	 * +
	 * "AND od.ORDER_ID = ?  AND si.BILLING_DATE <= ?                                                       "
	 * +
	 * " INNER JOIN PRICE_MODEL T ON P.PRODUCT_ID=T.PRODUCT_ID                                              "
	 * +
	 * "LEFT JOIN PRODUCT_ITEM ITM ON T.ITEM_CODE=ITM.ITEM_CODE                                             "
	 * +
	 * "INNER JOIN                                                                                          "
	 * +
	 * " PRICE_RULE PR ON  PR.RULE_ID=T.RULE_ID                                                             "
	 * +
	 * " and PR.RULE_CODE='PR-YZF' and MODEL_STATUS=1 GROUP BY ITEM_CODE                                    "
	 */;
}
