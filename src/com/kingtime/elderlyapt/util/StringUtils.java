package com.kingtime.elderlyapt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 字符串工具包
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
	 * 对从数据库中取出的时间数据进一步处理，整理成yyyy-MM-dd HH:mm:ss格式
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
	 * 判断输入字符串是否为空
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
	 * 将得到的JSON数据解析成数组
	 * 
	 * @param cancelRecordIdList
	 *            取消申请记录列表
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
	 * 将字符串转位日期类型
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
	 * 日期date1与date2比较
	 * 
	 * @param date1
	 * @param date2
	 * @return date1比date2早或相等，则返回true
	 */
	public static boolean compareDate(Date date1, Date date2) {
		if (date1.before(date2) || date1.equals(date2)) {
			return true;
		}
		return false;
	}

	/**
	 * 日期date1加上延迟的分钟，再与date2比较
	 * 
	 * @param date1
	 * @param date2
	 * @param delayMin
	 *            延迟时间，单位：分钟
	 * @return 改变后的date1比date2早或相等，则返回true
	 */
	public static boolean compareDateDelay(Date date1, Date date2, int delayMin) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.add(Calendar.MINUTE, delayMin);
		date1 = calendar.getTime();
		return compareDate(date1, date2);
	}

	/**
	 * 得到当前时间
	 * 
	 * @return 以字符串形式返回结果
	 */
	public static String getNow() {
		Calendar calendar = Calendar.getInstance();
		return dateFormater.get().format(calendar.getTime());
	}
}
