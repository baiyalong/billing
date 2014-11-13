package com.zhcs.billing.util;

import java.util.Properties;
import java.io.*;

import com.mysql.jdbc.JDBC4CallableStatement;

public class PropertieUtils {
	private static LoggerUtil log = LoggerUtil.getLogger(PropertieUtils.class);
	
	public Properties read(String file){
		Properties props = new Properties();
		String url = "/"+this.getClass().getClassLoader().getResource(file).toString().substring(6);
		String empUrl = url.replace("%20", " ");
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(empUrl));
			props.load(in);
			in.close();
		} catch (FileNotFoundException e1) {
			log.error("", e1);
		} catch (IOException e1) {
			log.error("", e1);
		}
		return props;
	}	
	
	/*public static void main(String[] args) {
		Properties pp = new PropertieUtils().read("jdbc.properties");
		String pp1 = pp.getProperty("url");
		
		
	}*/

}
