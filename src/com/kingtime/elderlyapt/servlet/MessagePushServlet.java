package com.kingtime.elderlyapt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.dao.impl.MessagePushDaoImpl;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

public class MessagePushServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		List<MessagePush> messagePushs = new ArrayList<MessagePush>();
		MessagePushDao pushDao = new MessagePushDaoImpl();
		String requestName = request.getParameter("requestName");
		int uid = Integer.valueOf(request.getParameter("uid"));
		
		System.out.println("MessagePush requestName:" + requestName);
		if (requestName.equals("requestAllMessages")) {// 获取所有消息
			messagePushs = pushDao.getMessages(uid);
			if (messagePushs.size() > 0) {
				System.out.println("return messages:" + MessagePush.toString(messagePushs));
				out.write(MessagePush.toString(messagePushs));
			}
		} else if (requestName.equals("getMessageByCategory")) {
			String requestCategory = request.getParameter("requestCategory");
			if (requestCategory.equals("requestByActivity")) {
				messagePushs.addAll(pushDao.getMessages(uid, MessagePush.CATEGORY_JOIN));
				messagePushs.addAll(pushDao.getMessages(uid, MessagePush.CATEGORY_STATE));
				messagePushs.addAll(pushDao.getMessages(uid, MessagePush.CATEGORY_USER));
				messagePushs.addAll(pushDao.getMessages(uid, MessagePush.CATEGORY_VERIFY));
			} else if (requestCategory.equals("requestByEvaluate")) {
				messagePushs = pushDao.getMessages(uid, MessagePush.CATEGORY_CREDIBILITY);
			} else if (requestCategory.equals("requestByCoins")) {
				messagePushs = pushDao.getMessages(uid, MessagePush.CATEGORY_INTEGRAL);
			} else {
				return;
			}
			if (messagePushs.size() > 0) {
				System.out.println("request category:" + requestCategory + ",return messages:"
						+ MessagePush.toString(messagePushs));
				out.write(MessagePush.toString(messagePushs));
			}
		}

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
