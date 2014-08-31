package com.kingtime.elderlyapt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

import sample.RecordImpl;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.RecordDao;
import com.kingtime.elderlyapt.dao.impl.ActivityInfoDaoImpl;
import com.kingtime.elderlyapt.dao.impl.RecordDaoImpl;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MyActivity;

public class EvaluateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter outPrintWriter = response.getWriter();
		String requestName = null;
		Evaluate evaluate = new Evaluate();
		RecordDao recordDao = new RecordDaoImpl();

		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					System.out.println("fieldname-->>" + fieldName);
					if (fieldName.toLowerCase().equals("requestname")) {
						requestName = URLDecoder.decode(
								item.getString("UTF-8"), "UTF-8");
					}
					if (fieldName.toLowerCase().equals("uid")) {
						evaluate.setUid(Integer.valueOf(URLDecoder.decode(
								item.getString("UTF-8"), "UTF-8")));
					}
					if (fieldName.toLowerCase().equals("activityid")) {
						evaluate.setActivityId(Integer.valueOf(URLDecoder
								.decode(item.getString("UTF-8"), "UTF-8")));
					}
					if (fieldName.toLowerCase().equals("content")) {
						evaluate.setContent(URLDecoder.decode(
								item.getString("UTF-8"), "UTF-8"));
					}
					if (fieldName.toLowerCase().equals("credibility")) {
						evaluate.setCredibility(Float.valueOf(URLDecoder
								.decode(item.getString("UTF-8"), "UTF-8")));
					}
				} else {// ÎÄ¼þÓò
					return;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("requestName=" + requestName + ",content="
				+ evaluate.getContent() + "\n");
		System.out.println("activityid=" + evaluate.getActivityId() + ",credibility="
						+ evaluate.getCredibility() + "\n");

		boolean result = recordDao.evaluateActivity(evaluate);

		System.out.println("return:" + result);
		outPrintWriter.write(result ? "true" : "false");
		outPrintWriter.flush();
		outPrintWriter.close();
	}

}
