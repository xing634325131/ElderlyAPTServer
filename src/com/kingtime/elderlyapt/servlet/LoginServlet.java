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

import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.dao.impl.UserDaoImpl;
import com.kingtime.elderlyapt.entity.User;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = null;
		UserDao userDao = new UserDaoImpl();
		String username = null,password = null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter outPrintWriter = response.getWriter();
		
		try{
			List<FileItem> items =  upload.parseRequest(request);
			for(FileItem item:items){
				if(item.isFormField()){
					String fieldName = item.getFieldName();
					System.out.println("fieldname-->>"+fieldName);
					if(fieldName.toLowerCase().equals("username")){
						username = URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					}
					if(fieldName.toLowerCase().equals("pwd")){
						password = item.getString("UTF-8");
					}
				}
				else {
					//TODO
					return ;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("username=" + username + ",pwd=" + password + "\n");
		user = userDao.login(username, password);

		if (user != null) {
			//System.out.println(user.getPhoto().toString());
			outPrintWriter.write(user.toString());
			outPrintWriter.flush();
			outPrintWriter.close();
		}
	}

}
