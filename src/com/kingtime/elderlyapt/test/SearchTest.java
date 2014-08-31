package com.kingtime.elderlyapt.test;

import com.kingtime.elderlyapt.dao.impl.ActivityInfoDaoImpl;
import com.kingtime.elderlyapt.util.SearchUtil;
import com.kingtime.elderlyapt.dao.impl.DutyDaoImpl;

public class SearchTest {
	public static void main(String[] args) {
		String sql = "select * from DutyDistribution where ";
		String searchString = "打扫 青志队";
		String[] searchs = searchString.split(" ");
		for (int i = 0; i < searchs.length; i++) {
			if (i == 0) {
				sql += "dutyName like '%" + searchs[i] + "%' or dutyContent like '%" + searchs[i] + "%' ";
			} else {
				sql += "or dutyName like '%" + searchs[i] + "%' or dutyContent like '%" + searchs[i] + "%' ";
			}
		}
		System.out.println(sql);
		
		new DutyDaoImpl().searchDuty(searchString);
		new ActivityInfoDaoImpl().searchActivity(searchString);
		SearchUtil.searchForActivity(searchString);
	}
}
