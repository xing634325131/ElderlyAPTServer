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

import org.json.JSONException;

import com.kingtime.elderlyapt.dao.RecordDao;
import com.kingtime.elderlyapt.dao.impl.RecordDaoImpl;
import com.kingtime.elderlyapt.entity.Record;
import com.kingtime.elderlyapt.util.StringUtils;

public class RecordServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		List<Record> records = new ArrayList<Record>();
		RecordDao recordDao = new RecordDaoImpl();

		String requestName = request.getParameter("requestName");
		if (requestName.equals("getJoinUserList")) {
			String activityId = request.getParameter("activityId");
			System.out.println("activityId = " + activityId);
			if (!StringUtils.isEmpty(activityId)) {
				records = recordDao.getRecordsByActivityId(Integer
						.valueOf(activityId));
				String returnString = Record.toString(records);
				System.out.println("return:" + returnString);
				if (returnString != null) {
					out.write(returnString);
				}
			} else {
				return;
			}
		} else if (requestName.equals("getUserJoinStateId")) {
			String activityId = request.getParameter("activityId");
			String uid = request.getParameter("uid");
			System.out.println("getstate: activityId=" + activityId + ",uid="
					+ uid);
			Record record = recordDao.getRecord(Integer.valueOf(uid),
					Integer.valueOf(activityId));
			int recordStateId = record == null ? 0 : record.getStateId();
			System.out.println("return:" + recordStateId);
			out.write(String.valueOf(recordStateId));
		} else if (requestName.equals("cancelUserJoin")) {
			String cancelRecordIdList = request
					.getParameter("cancelRecordIdList");
			if (cancelRecordIdList != null) {
				cancelRecordIdList = URLDecoder.decode(cancelRecordIdList,
						"UTF-8");
			}
			System.out.println("cancelRecordIdList = " + cancelRecordIdList);
			int[] cancelList = null;
			try {
				cancelList = StringUtils.prase(cancelRecordIdList);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			boolean cancelResult = recordDao.cancelUserDuty(cancelList);
			System.out.println("return :" + cancelResult);
			out.write(cancelResult ? "true" : "false");
		} else if (requestName.equals("selectDutyJoin")) {
			String dutyId = request.getParameter("dutyId");
			String uid = request.getParameter("uid");
			System.out.println("getapply: dutyId=" + dutyId + ",uid=" + uid);
			boolean applyResult = recordDao.userApplyDuty(Integer.valueOf(uid),
					Integer.valueOf(dutyId));
			System.out.println("return :" + applyResult);
			out.write(applyResult ? "true" : "false");
		}
		out.flush();
		out.close();
	}

}
