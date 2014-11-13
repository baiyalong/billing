package com.zhcs.billing.feerate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class test {
	public static void main(String[] args) {
		/*String json2="{\"CustomerID\":null,\"ProductID\":null,\"AppID\":null,\"Dimensions\":[{\"Name\":\"WD-SMS-T\",\"Type\":\"String\",\"Value\":\"100\",\"Properties\":null}],\"Properties\":null,\"OperationType\":1,\"SerialNo\":\"92810b0c-9d5b-44d6-adf5-df6e7e4c1ba3\",\"TimeStamp\":\"20140324101940\",\"BillingID\":\"87d0d16f-105b-4495-a0e2-064e0d47d723\"}";
		String json="{'TimeStamp':'20140318100125','BillingID':'1','OrderID':'126a1e60-5417-45ba-b1e1-aa68dec25a7d','OperationType':'2'}";
		JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
	    svr.setServiceClass(IGrantPriceForChargeBus.class);
	    //svr.setAddress("http://localhost:90/feebilling/grantPriceForChargeBus?wsdl");
	    svr.setAddress("http://10.0.209.182:9001/Billing/BillingWebService.asmx");
//	    http://10.0.209.182:9001/Billing/BillingWebService.asmx

	    IGrantPriceForChargeBus hw = (IGrantPriceForChargeBus) svr.create();
	    //System.out.println(hw.GrantPriceForCharge(json));
	    System.out.println(hw.GrantPriceForLock(json2));*/
		Md5("e4926cc5dd254cd1b5b2d838c2d7825c-zhkf-kf10010_ro-user1-数据队列-呼叫中心");
	    
	}
	private static void Md5(String plainText ) { 
		try { 
		MessageDigest md = MessageDigest.getInstance("MD5"); 
		md.update(plainText.getBytes()); 
		byte b[] = md.digest(); 

		int i; 

		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
		i = b[offset]; 
		if(i<0) i+= 256; 
		if(i<16) 
		buf.append("0"); 
		buf.append(Integer.toHexString(i)); 
		} 

		System.out.println("result: " + buf.toString());//32位的加密 

		System.out.println("result: " + buf.toString().substring(8,24));//16位的加密 

		} catch (NoSuchAlgorithmException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 
		} 
	
}
