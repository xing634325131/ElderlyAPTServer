package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Duty;

/**
 * ְ��ӿ�
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public interface DutyDao {

	/**
	 * ���ݻ��Ż�ȡ�ְ���б�
	 * 
	 * @param activityId
	 *            ����
	 * @return
	 */
	public List<Duty> getDutyList(int activityId);

	/**
	 * ����ְ���Ż�ȡְ������
	 * 
	 * @param dutyId
	 *            ְ����
	 * @return
	 */
	public Duty getDuty(int dutyId);

	/**
	 * �ڲ��������ı�󣬸���ְ��Ŀǰ����
	 * 
	 * @param dutyId
	 *            �����ְ����
	 * @param updateNum
	 *            ����������������ʾȡ������
	 * @return �������
	 */
	public boolean updateDutyNowNum(int dutyId, int updateNum);

	/**
	 * 
	 * ��ȡְ��ǰ��������
	 * 
	 * @param dutyId
	 *            ְ����
	 * @return
	 */
	public int getNowNum(int dutyId);

	/**
	 * ����ְ��
	 * 
	 * @param duty
	 *            ְ��ʵ��
	 * @return �������
	 */
	public boolean createDuty(Duty duty);

	/**
	 * ����ְ��
	 * 
	 * @param searchString
	 *            �����ַ���
	 * @return ����������ID�б�
	 */
	public int[] searchDuty(String searchString);
}
