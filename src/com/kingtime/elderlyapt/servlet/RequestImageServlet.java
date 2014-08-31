package com.kingtime.elderlyapt.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingtime.elderlyapt.dao.DutyDao;
import com.kingtime.elderlyapt.dao.impl.DutyDaoImpl;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.util.StringUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;

public class RequestImageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/jpeg");
		response.setCharacterEncoding("UTF-8");
		OutputStream out = response.getOutputStream();

		String requestName = request.getParameter("requestName");
		String imageName = request.getParameter("imageName");
		System.out.println("requestimage :" + requestName + "--" + imageName);
		if (!StringUtils.isEmpty(imageName)
				&& !StringUtils.isEmpty(requestName)) {
			String path = this.getServletContext().getRealPath(
					"\\Image\\" + requestName);
			File f = new File(path + "\\" + imageName);
			System.out.println("request iamge:" + f.getAbsolutePath());
			if(f.exists()){
				FileInputStream fStream = new FileInputStream(f.getAbsolutePath());
				byte data[] = new byte[fStream.available()];
				fStream.read(data);
				fStream.close();
				out.write(data);
			}else{
				System.err.println("Image not found !!");
			}
		} else {
			return;
		}
		out.flush();
		out.close();
	}

}
