package com.kingtime.elderlyapt.servlet;

import java.io.IOException;
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

public class DutyServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<Duty> dutyList = new ArrayList<Duty>();
		DutyDao dutyDao = new DutyDaoImpl();

		String activityId = request.getParameter("activityId");
		System.out.println("activityId = " + activityId);
		if (!StringUtils.isEmpty(activityId)) {
			int id = Integer.valueOf(activityId);
			dutyList = dutyDao.getDutyList(id);
			String returnString = Duty.toString(dutyList);
			if(returnString != null){
				System.out.println(returnString);
				out.write(returnString);
			}
			else {
				return ;
			}
		} else {
			return ;
		}
		out.flush();
		out.close();
	}

}
