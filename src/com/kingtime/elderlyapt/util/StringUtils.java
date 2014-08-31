package com.kingtime.elderlyapt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * �ַ������߰�
 * 
 * @author xp
 * @created 2014-4-28
 */
public class StringUtils {

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * �Դ����ݿ���ȡ����ʱ�����ݽ�һ�����������yyyy-MM-dd HH:mm:ss��ʽ
	 * 
	 * @param dateFrom
	 * @return
	 */
	public static String getDate(String dateFrom) {
		if (dateFrom != null) {
			return dateFrom.substring(0, dateFrom.length() - 2);
		}
		return null;
	}

	/**
	 * �ж������ַ����Ƿ�Ϊ��
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * ���õ���JSON���ݽ���������
	 * 
	 * @param cancelRecordIdList
	 *            ȡ�������¼�б�
	 * @return
	 * @throws JSONException
	 */
	public static int[] prase(String cancelRecordIdList) throws JSONException {
		// System.err.println(cancelRecordIdList);
		JSONArray array = new JSONArray(cancelRecordIdList);
		int[] result = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			result[i] = jsonObject.getInt("recordId");
		}
		return result;
	}

	/**
	 * ���ַ���תλ��������
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * ����date1��date2�Ƚ�
	 * 
	 * @param date1
	 * @param date2
	 * @return date1��date2�����ȣ��򷵻�true
	 */
	public static boolean compareDate(Date date1, Date date2) {
		if (date1.before(date2) || date1.equals(date2)) {
			return true;
		}
		return false;
	}

	/**
	 * ����date1�����ӳٵķ��ӣ�����date2�Ƚ�
	 * 
	 * @param date1
	 * @param date2
	 * @param delayMin
	 *            �ӳ�ʱ�䣬��λ������
	 * @return �ı���date1��date2�����ȣ��򷵻�true
	 */
	public static boolean compareDateDelay(Date date1, Date date2, int delayMin) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.add(Calendar.MINUTE, delayMin);
		date1 = calendar.getTime();
		return compareDate(date1, date2);
	}

	/**
	 * �õ���ǰʱ��
	 * 
	 * @return ���ַ�����ʽ���ؽ��
	 */
	public static String getNow() {
		Calendar calendar = Calendar.getInstance();
		return dateFormater.get().format(calendar.getTime());
	}
}
