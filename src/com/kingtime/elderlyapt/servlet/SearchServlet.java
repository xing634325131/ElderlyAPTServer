package com.kingtime.elderlyapt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.dao.impl.UserDaoImpl;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.util.StringUtils;
import com.kingtime.elderlyapt.util.SearchUtil;;

public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<MyActivity> activities = new ArrayList<MyActivity>();
		
		String requestName = request.getParameter("requestName");
		String searchString = request.getParameter("searchString");
		if (requestName.equals("searchForActivity")) {
			System.out.println("searchString = " + searchString);
			activities = SearchUtil.searchForHaveVerifiedActivity(searchString);//只能搜索通过审核的活动
			System.out.println("return :" + MyActivity.toString(activities));
			if(activities.size() > 0){
				out.write(MyActivity.toString(activities));
			} else{
				return ;
			}
		} else{
			return ;
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
