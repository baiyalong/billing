package com.zhcs.billing.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author LiuJie
 */
public class EnvironmentUtils {
	private static LoggerUtil log = LoggerUtil.getLogger(EnvironmentUtils.class);

	/**
	 * 得到当前机器的IP，如果获取失败返回null.
	 * @return IP
	 */
	public static String getIP() {
		String IP = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			IP = addr.getHostAddress().toString();
			addr=null;
		} catch (UnknownHostException e) {
			log.debug("getIP出现错误：" + e.getMessage());
		}

		return IP;
	}

	/**
	 * 得到当前机器的地址(机器名称),获取失败返回null.
	 * @return address
	 */
	public static String getAddress() {
		String address = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			address = addr.getHostName().toString();
			addr=null;
		} catch (UnknownHostException e) {
			log.debug("getAddress出现错误：" + e.getMessage());
		}

		return address;
	}

	/**
	 * 得到当前机器的mac地址，获取失败是返回null.
	 * @return mac
	 */
	public static String getMacAddress() {
		String mac = null;
		String line = null;

		String os = System.getProperty("os.name");

		if (os != null && os.startsWith("Windows")) {
			try {
				String command = "cmd.exe /c ipconfig /all";
				Process p = Runtime.getRuntime().exec(command);

				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream()));

				while ((line = br.readLine()) != null) {
					if (line.indexOf("Physical Address") > 0) {
						int index = line.indexOf(":") + 2;
						mac = line.substring(index);
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				log.debug("getMacAddress发生错误:" + e.getMessage());
			}
		}

		return mac;
	}

	/**
	 * 获取当前机器的Mac地址，该方法要求JDK1.6以上版本。
	 * @param host
	 * @return mac
	 */
	public static String getMacAddress(String host) {
		String mac = null;
		StringBuffer sb = new StringBuffer();

		try {
			NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress
					.getByName(host));
			byte[] macs = ni.getHardwareAddress();

			for (int i = 0; i < macs.length; i++) {
				mac = Integer.toHexString(macs[i] & 0xFF);
				if (mac.length() == 1) {
					mac = '0' + mac;
				}
				sb.append(mac + "-");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			log.debug("获取Mac地址发生错误:" + e.getMessage());
		} catch (UnknownHostException eex) {
			eex.printStackTrace();
			log.debug("获取Mac地址发生错误:" + eex.getMessage());
		}

		mac = sb.toString();
		mac = mac.substring(0, mac.length() - 1);

		return mac;
	}
}
