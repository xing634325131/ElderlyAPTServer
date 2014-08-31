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
import com.kingtime.elderlyapt.dao.ChatRecordDao;
import com.kingtime.elderlyapt.dao.impl.ChatRecordDaoImpl;
import com.kingtime.elderlyapt.entity.ChatRecord;

public class ChatRecordServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		ChatRecordDao recordDao = new ChatRecordDaoImpl();
		List<ChatRecord> records = new ArrayList<ChatRecord>();
		String requestName = request.getParameter("requestName");
		if (requestName.equals("getChatRecords")) {
			int activityId = Integer.valueOf(request.getParameter("activityId"));
			records = recordDao.getChatRecords(activityId);
			if (records.size() < 1) {
				System.out.println("return empty record!");
				return;
			}
			System.out.println("return record:" + ChatRecord.toString(records));
			out.write(ChatRecord.toString(records));
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
		String uid = null;
		String content = null;
		String activityId = null;
		ChatRecord chatRecord = null;
		ChatRecordDao recordDao = new ChatRecordDaoImpl();

		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					System.out.println("fieldname-->>" + fieldName);
					if (fieldName.toLowerCase().equals("requestname")) {
						requestName = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if (fieldName.toLowerCase().equals("uid")) {
						uid = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if (fieldName.toLowerCase().equals("content")) {
						content = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
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
		if (requestName.equals("sendChatMessage")) {
			System.out.println("requestName=" + requestName + ",content=" + content + "\n");
			chatRecord = new ChatRecord();
			chatRecord.setActivityId(Integer.valueOf(activityId));
			chatRecord.setContent(content);
			chatRecord.setUid(Integer.valueOf(uid));
			boolean result = recordDao.createChatRecord(chatRecord);
			System.out.println("return result:" + result);
			outPrintWriter.write(result ? "true" : "false");
		} else {
			return;
		}
		outPrintWriter.flush();
		outPrintWriter.close();
	}

}
