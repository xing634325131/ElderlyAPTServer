package com.kingtime.elderlyapt.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.dao.impl.MessagePushDaoImpl;
import com.kingtime.elderlyapt.dao.impl.UserDaoImpl;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.service.ActivityStateTaskService;
import com.kingtime.elderlyapt.service.impl.ActivityStateTaskServiceImpl;

public class Maintest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Elderly Apartment!");

		User user = new User();
		UserDao userDao = new UserDaoImpl();
		String username = "zhangsan";
		String password = "e10adc3949ba59abbe56e057f20f883e";
		user = userDao.login(username, password);
		if (user != null) {
			System.out.println("username:" + user.getName() + ",phone:" + user.getPhone());
		}

		// String begin = "2014-01-29 09:00:00.0";
		// //StringBuilder dateString = null;
		// String beginString = begin.substring(0, begin.length() - 3);
		// System.out.println(beginString);
		// //Date startDate = toDate(beginString);
		// Date endDate = toDate(begin);
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(endDate);
		// System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		// System.out.println(endDate.toString());
		// dateString.append(startDate.getMonth()).append("月");
		// dateString.append(startDate.getDay()).append("日");
		// dateString.append("至");
		// dateString.append(endDate.getMonth()).append("月");
		// dateString.append(endDate.getDay()).append("日");
		// System.out.println(dateString.toString());

		System.out.println(Integer.MIN_VALUE);

		String beginTime = "2014-01-29 09:00:00.0";
		StringBuilder dateString = new StringBuilder();
		Date startDate = toDate(beginTime);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		int month = startCalendar.get(Calendar.MONTH) + 1;
		dateString.append(Integer.valueOf(month)).append("月");
		dateString.append(startCalendar.get(Calendar.DAY_OF_MONTH)).append("日");
		dateString.append("至");
		System.out.println("Date:" + dateString.toString());

		// new ActivityInfoDaoImpl().updateActivityNowNum(100011, 2);
		Calendar calendar = Calendar.getInstance();
		System.out.println(dateFormater.get().format(calendar.getTime()));
		// System.out.println(StringUtils.compareDate(startDate,
		// calendar.getTime()));
		// System.out.println(StringUtils.compareDateDelay(startDate, startDate,
		// 0));
		ActivityStateTaskService taskService = new ActivityStateTaskServiceImpl();
		int i = taskService.updateActivityState(MyActivity.ACTIVITY_APPLY, 2);
		int j = taskService.updateActivityState(MyActivity.ACTIVITY_CLOSED, 2);
		int k = taskService.updateActivityState(MyActivity.ACTIVITY_WILL_START, 2);
		int l = taskService.updateActivityState(MyActivity.ACTIVITY_RUNNING, 5);
		System.out.println("i=" + i);
		System.out.println("j=" + j);
		System.out.println("k=" + k);
		System.out.println("l=" + l);
		
		
		MessagePush messagePush = new MessagePush();
		MessagePushDao pushDao = new MessagePushDaoImpl();
		messagePush.setPushCategory(1);
		messagePush.setToPushUserId(10001);
		messagePush.setPushContent("test");
		//System.out.println(pushDao.createMessage(messagePush));
		//System.out.println(pushDao.getMessages(10001, MessagePush.CATEGORY_JOIN));
		//System.out.println(pushDao.getMessages(10001));
		
		String teString = "获得 3";
		int isss = Integer.valueOf(teString.substring(3, teString.length()));
		System.out.println(isss);
	}

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

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
}
