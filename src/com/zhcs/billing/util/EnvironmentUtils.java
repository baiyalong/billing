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
	 * �õ���ǰ������IP�������ȡʧ�ܷ���null.
	 * @return IP
	 */
	public static String getIP() {
		String IP = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			IP = addr.getHostAddress().toString();
			addr=null;
		} catch (UnknownHostException e) {
			log.debug("getIP���ִ���" + e.getMessage());
		}

		return IP;
	}

	/**
	 * �õ���ǰ�����ĵ�ַ(��������),��ȡʧ�ܷ���null.
	 * @return address
	 */
	public static String getAddress() {
		String address = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			address = addr.getHostName().toString();
			addr=null;
		} catch (UnknownHostException e) {
			log.debug("getAddress���ִ���" + e.getMessage());
		}

		return address;
	}

	/**
	 * �õ���ǰ������mac��ַ����ȡʧ���Ƿ���null.
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
				log.debug("getMacAddress��������:" + e.getMessage());
			}
		}

		return mac;
	}

	/**
	 * ��ȡ��ǰ������Mac��ַ���÷���Ҫ��JDK1.6���ϰ汾��
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
			log.debug("��ȡMac��ַ��������:" + e.getMessage());
		} catch (UnknownHostException eex) {
			eex.printStackTrace();
			log.debug("��ȡMac��ַ��������:" + eex.getMessage());
		}

		mac = sb.toString();
		mac = mac.substring(0, mac.length() - 1);

		return mac;
	}
}
