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

		// 每月初扫描所有有效的结算规则
		List<PartnerSettlementRuleBean> li = null;
		try {
			li = BillingQuery.PartnerSettlementRule();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 对每一条有效的结算规则进行处理
		// 1。固定费用
		// 2.按使用量 - 需扫描上月使用量
		// 3.按比例分成 - 需扫描上月收益
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
