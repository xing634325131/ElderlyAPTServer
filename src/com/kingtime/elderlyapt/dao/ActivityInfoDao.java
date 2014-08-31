package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MyActivity;

/**
 * ���Ϣ�ӿ���
 * 
 * @author xp
 * 
 * @created 2014-4-28
 */
public interface ActivityInfoDao {

	/**
	 * ���ݻ����ȡ���Ϣ�б�ʵ��
	 * 
	 * @param requestCategory
	 *            ����
	 * @return ��б�
	 */
	public List<MyActivity> getActivityInfoList(String requestCategory);

	/**
	 * �õ��ض����趨ʱ�ı�״̬�Ļ�б������������У�������ֹ��������ʼ�������еĻ
	 * 
	 * @param stateId
	 *            Ҫ���µĻ״̬
	 * @return ��б�
	 */
	public List<MyActivity> getAllNeedTaskActivity(int stateId);

	/**
	 * ���ݻid��ȡ���Ϣ
	 * 
	 * @param activityId
	 *            �ID
	 * @return ���ظ���Ʒ��Ϣʵ��������������򷵻�Ϊ��
	 */
	public MyActivity getActivityInfo(int activityId);

	/**
	 * �����
	 * 
	 * @param myActivity
	 *            ���������
	 * @param duties
	 *            �ְ��
	 * @return ���ش�����ʵ��
	 */
	public MyActivity createActivity(MyActivity myActivity, List<Duty> duties);

	/**
	 * ���»״̬
	 * 
	 * @param activityId
	 *            ����
	 * @param stateId
	 *            ״̬���
	 * @return
	 */
	public boolean updateActivityState(int activityId, int stateId);

	/**
	 * ���µ�ǰ��������
	 * 
	 * @param acitvityId
	 *            ����
	 * @param num
	 *            ��������
	 * @return
	 */
	public boolean updateActivityNowNum(int activityId, int num);

	/**
	 * �������˻�б�
	 * 
	 * @return
	 */
	public List<MyActivity> getActivitiesByVerify();

	/**
	 * �����û���Ż�ȡ���û����������л
	 * 
	 * @param uid
	 *            �û����
	 * @return ��б�
	 */
	public List<MyActivity> getPostActivity(int uid);

	/**
	 * �����û���Ż�ȡ��ǰ�û������루��δ�������Ļ
	 * 
	 * @param uid
	 *            �û����
	 * @return ��б�
	 */
	public List<MyActivity> getNowJoinedActivity(int uid);

	/**
	 * �����û���Ż�ȡ���û������Ѿ�������Ļ���Ѿ�������
	 * 
	 * @param uid
	 *            �û����
	 * @return ����
	 */
	public List<MyActivity> getEndedActivity(int uid);

	/**
	 * ��ȡ��������Ƿ񱻻����������
	 * 
	 * @param activityId
	 *            ����
	 * @return ����״̬��true�������۹���false��û�б����۹���
	 */
	public boolean getAppraiseState(int activityId);

	/**
	 * ���������ۻ������
	 * 
	 * @param evaluates
	 *            ����
	 * @param activityId
	 *            ����
	 * @return �������
	 */
	public boolean appraiseJoinedUser(List<Evaluate> evaluates, int activityId);

	/**
	 * �����
	 * 
	 * @param searchString
	 *            �����ؼ���
	 * @return �������������Ҫ�Ļ�б�
	 */
	public List<MyActivity> searchActivity(String searchString);
}
