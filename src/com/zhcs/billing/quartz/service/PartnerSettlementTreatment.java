package com.zhcs.billing.quartz.service;

import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.PartnerSettlementRuleBean;
import com.zhcs.billing.use.dao.BillingInsert;
import com.zhcs.billing.use.dao.BillingQuery;
import com.zhcs.billing.util.LoggerUtil;

public class PartnerSettlementTreatment extends Task implements Job {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(PartnerSettlementTreatment.class);
	private static Logger log = LoggerFactory
			.getLogger(PartnerSettlementTreatment.class);

	@Override
	public void execute(HashMap map) {
		// TODO Auto-generated method stub

		// ÿ�³�ɨ��������Ч�Ľ������
		List<PartnerSettlementRuleBean> li = null;
		try {
			li = BillingQuery.PartnerSettlementRule();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ��ÿһ����Ч�Ľ��������д���
		// 1���̶�����
		// 2.��ʹ���� - ��ɨ������ʹ����
		// 3.�������ֳ� - ��ɨ����������
		if (li != null && !li.isEmpty()) {
			for (PartnerSettlementRuleBean bean : li) {
				if (settled(bean)) {
					continue;
				}

				switch (bean.getSETTLEMENT_RULE()) {
				case 1:
					BillingInsert.PartnerSettlementRecords(bean,
							bean.getSETTLEMENT_RULE_PARAM());
					break;
				case 2:
					int count = BillingQuery.PartnerSettlementCount(bean);
					BillingInsert.PartnerSettlementRecords(bean,
							count * bean.getSETTLEMENT_RULE_PARAM());

					break;
				case 3:
					int profit = BillingQuery.PartnerSettlementProfit(bean);
					BillingInsert.PartnerSettlementRecords(bean,
							profit * bean.getSETTLEMENT_RULE_PARAM() / 100);
					break;
				default:
					break;
				}
			}
		}

	}

	private boolean settled(PartnerSettlementRuleBean bean) {
		// TODO Auto-generated method stub
		boolean res = false;

		return res;
	}

}
