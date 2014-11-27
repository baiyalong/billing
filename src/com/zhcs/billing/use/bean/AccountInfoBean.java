package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountInfoBean {

	private String CUSTOMER_ID;
	private String ACCOUNT_ID;

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}

	public String getACCOUNT_ID() {
		return ACCOUNT_ID;
	}

	public void setACCOUNT_ID(String aCCOUNT_ID) {
		ACCOUNT_ID = aCCOUNT_ID;
	}

	public static List<AccountInfoBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<AccountInfoBean> beans = new ArrayList<AccountInfoBean>();
		for (HashMap<String, Object> hashMap : list) {
			AccountInfoBean bean = new AccountInfoBean();
			bean.setACCOUNT_ID(hashMap.get("ACCOUNT_ID") != null ? hashMap.get(
					"ACCOUNT_ID").toString() : null);
			bean.setCUSTOMER_ID(hashMap.get("CUSTOMER_ID") != null ? hashMap
					.get("CUSTOMER_ID").toString() : null);

			beans.add(bean);
		}
		return beans;
	}

}
