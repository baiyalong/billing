package com.zhcs.billing.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Common {
	private static LoggerUtil log = LoggerUtil.getLogger(Common.class);
	// private static final int ID_LENGTH = 36;
	private static final String billingIDStart = "BILL_";
	private static final String acountIDStart = "ACCOUNT_";
	private static final int timeNum = 17;
	// private static final int randNum = 11;

	static JaxWsDynamicClientFactory dcf = null;
	// JaxWsDynamicClientFactory
	// .newInstance();
	static Client client = null;

	// dcf
	// .createClient("http://172.17.0.15:9001/BillingWebService.asmx?wsdl");;

	/**
	 * �Ƿ���ȷ������
	 * */
	public static boolean isValidDate(String Date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			dateFormat.parse(Date);
			dateFormat = null;
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * ��ȡ��ǰʱ���Timestamp����
	 * 
	 * @return Timestamp
	 */
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * �ַ���ת��ʱ��
	 * */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		format = null;
		return date;
	}

	/**
	 * 
	 * @param timestamp
	 * @return yyyy-MM-dd
	 */
	public static String getDate(Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String ts = df.format(timestamp);
		df = null;
		return ts;
	}

	/**
	 * ���ص�ǰʱ���ʽ yyyyMMdd
	 * 
	 * @param timestamp
	 * @return yyyyMMdd
	 */
	public static String getDateStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String ts = df.format(new Date());
		df = null;
		return ts;
	}

	/**
	 * 
	 * @param timestamp
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime(Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ts = df.format(timestamp);
		df = null;
		return ts;
	}

	/**
	 * 
	 * @return yyyy-MM-dd HH:mm:ss SSS
	 */
	public static String getDateTimeSSS() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ts = df.format(new Timestamp(System.currentTimeMillis()));
		df = null;
		return ts;
	}

	public static String getNowDay() {
		SimpleDateFormat df = new SimpleDateFormat("dd");
		String ts = df.format(new Timestamp(System.currentTimeMillis()));
		df = null;
		return ts;
	}

	/**
	 * num Ϊ���������� num ����ʱ�� num Ϊ���������� num ��ǰ��ʱ�� reqtime��ʽ��yyyy-MM-dd HH:mm:ss��
	 * 
	 * @param String
	 *            reqtime
	 * @param int num
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getNumDay(String reqtime, int num) {
		String str = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = df.parse(reqtime);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.DAY_OF_MONTH, num);// ���ڵ�ǰһ��
			Date useEnd = cd.getTime();
			str = df.format(useEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * num Ϊ���������� num ����ʱ�� num Ϊ���������� num ��ǰ��ʱ�� reqtime��ʽ��yyyy-MM-dd��
	 * 
	 * @param String
	 *            reqtime
	 * @param int num
	 * @return String yyyy-MM-dd
	 */
	public static String getNumDayNoHms(String reqtime, int num) {
		String str = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(reqtime);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.DAY_OF_MONTH, num);
			Date useEnd = cd.getTime();
			str = df.format(useEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * num Ϊ���������� num �º��ʱ�� num Ϊ���������� num ��ǰ��ʱ�� reqtime��ʽ��yyyyMM��
	 * 
	 * @param String
	 *            reqtime
	 * @param int num
	 * @return String yyyyMM
	 */
	public static String getNumDayYyyyMM(String reqtime, int num) {
		String str = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			Date date = df.parse(reqtime);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			cd.add(Calendar.MONTH, num);
			Date useEnd = cd.getTime();
			str = df.format(useEnd);
			df = null;
			useEnd = null;
			cd = null;
			date = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * @return Thread + ���� + ��λ����ַ���
	 */
	public static String getThreadSSSStr() {
		StringBuffer ts = new StringBuffer();

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss SSS");
		ts.append("Thread");
		ts.append(df.format(new Timestamp(System.currentTimeMillis())));
		ts.append(getRandomString());
		df = null;
		return ts.toString();
	}

	/**
	 * @return ��λ����ַ���+����
	 */
	public static String getStrSSS() {
		StringBuffer ts = new StringBuffer();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		ts.append(getRandomString());
		ts.append(df.format(new Timestamp(System.currentTimeMillis())));
		df = null;
		return ts.toString();
	}

	/**
	 * @param timestamp
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDateTime2Date(Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ts = df.format(timestamp);
		try {
			return df.parse(ts);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		df = null;
		ts = null;
		return timestamp;
	}

	/**
	 * @return ��λ����ַ���
	 */
	public static String getRandomString() {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * �õ���ǰ�µ�����
	 * 
	 * @return
	 */
	public static int getThisMonthDays() {
		Calendar cal = Calendar.getInstance();
		int days = cal.getActualMaximum(Calendar.DATE);
		return days;
	}

	public static String createBillingID() {
		long timeLong = System.currentTimeMillis();
		int timeLen = String.valueOf(timeLong).length() > timeNum ? timeNum
				: String.valueOf(timeLong).length();
		String time = String.valueOf(timeLong).substring(0, timeLen);
		String ranStr = Common.getRandomString();// UUID.randomUUID().toString();
		String billingID = billingIDStart + time + ranStr;
		log.info("create a billing id:" + billingID + " length:"
				+ billingID.length());
		return billingID;
	}

	public static String createAccountID(String orderid) {
		long timeLong = System.currentTimeMillis();
		int timeLen = String.valueOf(timeLong).length() > timeNum ? timeNum
				: String.valueOf(timeLong).length();
		String time = String.valueOf(timeLong).substring(0, timeLen);
		String billingID = "BILL_" + time + orderid;
		log.info("create a billing id:" + billingID + " length:"
				+ billingID.length());
		return billingID;
	}

	public static String genRandomNum(int pwd_len) {
		// 35����Ϊ�����Ǵ�0��ʼ�ģ�26����ĸ+10������
		final int maxNum = 36;
		int i; // ���ɵ������
		int count = 0; // ���ɵ�����ĳ���
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// �����������ȡ����ֵ����ֹ���ɸ�����

			i = Math.abs(r.nextInt(maxNum)); // ���ɵ������Ϊ36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}

		return pwd.toString();
	}

	/**
	 * �õ���ǰ������IP�������ȡʧ�ܷ���null.
	 * 
	 * @return IP
	 */
	public static String getIP() {
		String IP = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			IP = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			log.debug("getIP���ִ���" + e.getMessage());
		}

		return IP;
	}

	/**
	 * �õ���ǰ�����ĵ�ַ(��������),��ȡʧ�ܷ���null.
	 * 
	 * @return address
	 */
	public static String getAddress() {
		String address = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			address = addr.getHostName().toString();
		} catch (UnknownHostException e) {
			log.debug("getAddress���ִ���" + e.getMessage());
		}

		return address;
	}

	/**
	 * �õ���ǰ������mac��ַ����ȡʧ���Ƿ���null.
	 * 
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
	 * 
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

	/**
	 * ��ȡ����ip��ַ������
	 * 
	 * @return ����ip��ַ
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}

	/**
	 * ת����XML
	 * 
	 * @author runway liying
	 * */
	public static Document transToXml(String xmlStr) {
		SAXReader saxReader = new SAXReader(); // ��ý�����
		Document document = null; // ����xml�ļ�
		try {
			document = saxReader.read(new ByteArrayInputStream(xmlStr
					.getBytes("UTF-8")));
		} catch (Exception e) {
			return null;
		}
		saxReader = null;
		return document;
	}

	/**
	 * xml �ڵ�Ԫ���Ƿ�Ϊ��
	 * 
	 * @author runway liying
	 * */
	public static boolean isEmpty(Element elements, String elementText) {
		boolean Result = true;
		if (elements.element(elementText).getText().trim() == null) {
			Result = false;
		}
		return Result;
	}

	/** ȡxml�ڵ�ֵ */
	public static String elementText(Element element, String key) {
		String Str = element.element(key).getText();
		if (element.element(key).getText().trim() != null) {
			Str = element.element(key).getText();
		}
		return Str;
	}

	/**
	 * @param rootNode
	 *            ���ڵ�
	 * @param nodes
	 *            �ӽڵ�
	 * @param Property
	 *            �ڵ�����
	 * @param value
	 *            �ڵ�ֵ
	 * */
	public static List<String> List(List<Element> rootNode,
			List<Element> nodes, String property, String value) {
		List<String> list = new ArrayList();
		for (Element rootElement : rootNode) {
			nodes = rootElement.elements(property);
			for (Element element : nodes) {
				if (isEmpty(element, value)) {
					list.add(elementText(element, value));
				}
			}
		}
		return list;
	}

	/**
	 * �õ���ǰ�ꡢ��
	 * */
	public static String getDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(dt);
	}

	/**
	 * ��T_Bill_Cycle_Seq���������
	 * */
	public static String insertBillTBillCycleSeq() {
		try {
			Common billingID = new Common();
			BillingBaseDao bdao = new BillingBaseDao();
			String id = billingID.createBillingID();
			String BillCycle = getDate();
			List params = new ArrayList();
			params.add(id);
			params.add(BillCycle);
			params.add(1);
			int flag = bdao.doSaveOrUpdate(
					"insert into T_BILL_CYCLE_SEQ values(?,?,?)", params);
			params = null;
			if (flag == 0) {
				log.info("��ӻ����ʧ�� ��");
			} else {
				log.info("��ӻ���³ɹ� ��");
			}
			return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.err);
		}

		return "";
	}

	/**
	 * ��װ��json��ʽ
	 * 
	 * @param params
	 * @param values
	 * @param array
	 * @return
	 */

	public static String changeJSON(List<String> paramsList,
			List<String> valuesList) {
		JSONArray array = new JSONArray();
		JSONObject jso = new JSONObject();
		for (int i = 0; i < paramsList.size(); i++) {
			jso.put(paramsList.get(i), valuesList.get(i));
		}
		array.add(jso);
		return array.toString();
	}

	/**
	 * �õ�����
	 * */
	public static String GetBillCycle() {
		/*** ���� T_Bill_Cycle_Seq �� Bill_Cycle_Stat�ֶε�״̬���õ����� ***/
		String dateStr = "";
		String sql = "select BILL_CYCLE from T_BILL_CYCLE_SEQ where BILL_CYCLE_STAT = ?";
		List<Comparable> params = new ArrayList<Comparable>();
		params.add("1");

		List<HashMap<String, Object>> list = new BillingBaseDao().doSelect(sql,
				params);
		if (list.size() > 0 && list != null) {
			if (list.get(0).get("BILL_CYCLE") != null) {
				dateStr = list.get(0).get("BILL_CYCLE").toString();
			} else {
				log.error("Common Class execute GetBillCycle������ T_BILL_CYCLE_SEQ �� BILL_CYCLE �ֶ� ������!");
			}
		} else {
			log.error("Common Class execute GetBillCycle������ T_BILL_CYCLE_SEQ ����������Ҫ����!");
		}
		list = null;
		return dateStr;
	}

	/**
	 * �˻��۷ѽӿ�
	 * 
	 * @param wsdl
	 * @param method
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Object[] accountPayInerface(String wsdl, String method,
			String json) throws Exception {
		// JaxWsDynamicClientFactory dcf =
		// JaxWsDynamicClientFactory.newInstance();

		System.out.println("jsonjsonjsonjsonjsonjsonjson" + json);
		Object[] res = null;
		try {
			res = client.invoke(method, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
