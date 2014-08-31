package com.kingtime.elderlyapt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.impl.ActivityInfoDaoImpl;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MyActivity;

public class ActivityServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<MyActivity> myActivities = new ArrayList<MyActivity>();
		MyActivity myActivity = new MyActivity();
		ActivityInfoDao infoDao = new ActivityInfoDaoImpl();

		String requestName = request.getParameter("requestName");
		System.out.println("ActivitySevlet request:" + requestName);
		if (requestName.equals("getActivityById")) {
			String activityId = request.getParameter("activityId");
			System.out.println("activityId = " + activityId);
			int id = Integer.valueOf(activityId);
			myActivity = infoDao.getActivityInfo(id);
			System.out.println(myActivity.toString());
			out.write(myActivity.toString());
		} else if (requestName.equals("categoryName")) {
			String categoryName = request.getParameter("categoryName");
			System.out.println("Activities by name:" + categoryName);
			myActivities = infoDao.getActivityInfoList(categoryName);
			System.out.println(MyActivity.toString(myActivities));
			if (myActivities.size() > 0) {
				out.write(MyActivity.toString(myActivities));
			}
		} else if (requestName.equals("categoryId")) {
			String categoryId = request.getParameter("categoryId");
			System.out.println("Activities by id:" + categoryId);
			myActivities = infoDao.getActivityInfoList(categoryId);
			System.out.println(MyActivity.toString(myActivities));
			if (myActivities.size() > 0) {
				out.write(MyActivity.toString(myActivities));
			}
		} else if (requestName.equals("updateActivityState")) {
			String activityId = request.getParameter("activityId");
			System.out.println("activityId = " + activityId);
			String stateId = request.getParameter("stateId");
			System.out.println("stateId = " + stateId);
			boolean result = infoDao.updateActivityState(Integer.valueOf(activityId), Integer.valueOf(stateId));
			System.out.println("return:" + result);
			out.write(result ? "true" : "false");
		} else if (requestName.equals("getActivitiesByVerify")) {
			myActivities = infoDao.getActivitiesByVerify();
			System.out.println(MyActivity.toString(myActivities));
			if (myActivities.size() > 0) {
				out.write(MyActivity.toString(myActivities));
			}
		} else if (requestName.equals("getPublished")) {
			String uid = request.getParameter("uid");
			myActivities = infoDao.getPostActivity(Integer.valueOf(uid));
			System.out.println(MyActivity.toString(myActivities));
			if (myActivities.size() > 0) {
				out.write(MyActivity.toString(myActivities));
			}
		} else if (requestName.equals("getNowJoined")) {
			String uid = request.getParameter("uid");
			myActivities = infoDao.getNowJoinedActivity(Integer.valueOf(uid));
			System.out.println(MyActivity.toString(myActivities));
			if (myActivities.size() > 0) {
				out.write(MyActivity.toString(myActivities));
			}
		} else if (requestName.equals("getEnded")) {
			String uid = request.getParameter("uid");

			System.out.println("uid = " + uid);
			myActivities = infoDao.getEndedActivity(Integer.valueOf(uid));
			System.out.println(MyActivity.toString(myActivities));
			if (myActivities.size() > 0) {
				out.write(MyActivity.toString(myActivities));
			}
		} else if (requestName.equals("getAppraiseState")) {
			String activityId = request.getParameter("activityId");
			System.out.println("activityId = " + activityId);

			boolean result = infoDao.getAppraiseState(Integer.valueOf(activityId));
			System.out.println("return:" + result);
			out.write(result ? "true" : "false");
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter outPrintWriter = response.getWriter();
		String requestName = null;
		String createActivityJSON = null;
		String appraiseJson = null;
		String activityId = null;
		MyActivity myActivity = null;
		List<Duty> duties = null;
		ActivityInfoDao infoDao = new ActivityInfoDaoImpl();

		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					System.out.println("fieldname-->>" + fieldName);
					if (fieldName.toLowerCase().equals("requestname")) {
						requestName = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if (fieldName.toLowerCase().equals("createactivityjson")) {
						createActivityJSON = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if (fieldName.toLowerCase().equals("appraisejson")) {
						appraiseJson = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if (fieldName.toLowerCase().equals("activityid")) {
						activityId = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
				} else {// ÎÄ¼þÓò
					return;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (requestName.equals("createNewActivity")) {
			System.out.println("requestName=" + requestName + ",createActivityJSON=" + createActivityJSON + "\n");
			try {
				myActivity = MyActivity.praseToMyActivity(createActivityJSON);
				duties = MyActivity.praseToDuty(createActivityJSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MyActivity returnActivity = infoDao.createActivity(myActivity, duties);

			if (returnActivity != null) {
				System.out.println("returnActivity:" + returnActivity.toString());
				outPrintWriter.write(returnActivity.toString());
			}
		} else if (requestName.equals("appriaseActivity")) {
			System.out.println("requestName=" + requestName + ",appraisejson=" + appraiseJson + ",activityId=" + activityId
					+ "\n");
			List<Evaluate> evaluates = null;
			try {
				evaluates = Evaluate.praseToEvaluate(appraiseJson);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean result = infoDao.appraiseJoinedUser(evaluates, Integer.valueOf(activityId));
			System.out.println("return:" + result);
			outPrintWriter.write(result ? "true" : "false");
		} else {
			return;
		}

		outPrintWriter.flush();
		outPrintWriter.close();
	}
}
