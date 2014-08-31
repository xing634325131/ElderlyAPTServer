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

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.dao.impl.ActivityInfoDaoImpl;
import com.kingtime.elderlyapt.dao.impl.UserDaoImpl;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.util.StringUtils;

public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		User user = new User();
		UserDao userDao = new UserDaoImpl();
		String activityId = request.getParameter("activityId");
		String uid = request.getParameter("uid");
		if (!StringUtils.isEmpty(activityId)) {
			System.out.println("activityId = " + activityId);
			user = userDao.getUserByActivityId(Integer.valueOf(activityId));
		} else if (!StringUtils.isEmpty(uid)) {
			System.out.println("uid = " + uid);
			user = userDao.getUser(Integer.valueOf(uid));
		} else{
			return ;
		}
		System.out.println(user.toString());
		out.write(user.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter outPrintWriter = response.getWriter();
		String requestName = null;
		User updateUser = new User();
		UserDao userDao = new UserDaoImpl();
		
		try{
			List<FileItem> items =  upload.parseRequest(request);
			for(FileItem item:items){
				if(item.isFormField()){
					String fieldName = item.getFieldName();
					System.out.println("fieldname-->>"+fieldName);
					if(fieldName.toLowerCase().equals("requestname")){
						requestName = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if(fieldName.toLowerCase().equals("username")){
						updateUser.setName(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("gender")){
						updateUser.setGender(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("phone")){
						updateUser.setPhone(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("resphone")){
						updateUser.setResPhone(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("email")){
						updateUser.setEmail(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("interest")){
						updateUser.setInterest(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("address")){
						updateUser.setAddress(URLDecoder.decode(item.getString("UTF-8"), "UTF-8"));
					}
					if(fieldName.toLowerCase().equals("uid")){
						updateUser.setUid(Integer.valueOf(item.getString("UTF-8")));
					}
				}
				else {//ÎÄ¼þÓò
					return ;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("requestName=" + requestName + ",uid=" + updateUser.getUid());
		User returnUser = userDao.updateUserInfo(updateUser);
		
		if (returnUser != null) {
			System.out.println("returnUser:" + returnUser.toString());
			outPrintWriter.write(returnUser.toString());
			outPrintWriter.flush();
			outPrintWriter.close();
		}
	}

}
