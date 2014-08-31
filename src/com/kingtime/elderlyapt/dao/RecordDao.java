package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.Record;

/**
 * �����¼�ӿ�
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public interface RecordDao {

	/**
	 * ���ݻ��Ż�ȡ��ǰ�ѲμӸû����Ա��¼
	 * 
	 * @param activityId
	 *            ����
	 * @return
	 */
	public List<Record> getRecordsByActivityId(int activityId);

	/**
	 * ����ְ���ȡ��ǰ�����ְ�����Ա��¼
	 * 
	 * @param dutyId
	 * @return
	 */
	public List<Record> getRecordsByDutyId(int dutyId);

	/**
	 * ���ݼ�¼��Ż�ȡ��¼����
	 * 
	 * @param recordId
	 *            ��¼���
	 * @return
	 */
	public Record getRecordByRecordId(int recordId);

	/**
	 * ��ȡ�û�����״̬
	 * 
	 * @param uid
	 *            �û����
	 * @param activityId
	 *            ����
	 * @return ���ؼ�¼ʵ�������û�в��룬�򷵻�Ϊ��
	 */
	public Record getRecord(int uid, int activityId);

	/**
	 * �»ְ������
	 * 
	 * @param uid
	 *            �û����
	 * @param dutyId
	 *            ְ����
	 * @return �������
	 */
	public boolean userApplyDuty(int uid, int dutyId);

	/**
	 * ȡ��ְ������
	 * 
	 * @param cancelList
	 *            ȡ����¼�б�
	 * @return
	 */
	public boolean cancelUserDuty(int[] cancelList);
	
	/**
	 * ���ݻ��Ÿ����û�����״̬
	 * @param activityId ����
	 * @param stateId ״̬���
	 * @return
	 */
	public boolean updateStateId(int activityId, int stateId);
	
	/**
	 * �����û���Ż�ȡ���û����������ְ����
	 * @param uid �û����
	 * @return ��¼�б�
	 */
	public List<Record> getRecordByUid(int uid);
	
	/**
	 * ���ۻ
	 * @param evaluate ����ʵ��
	 * @return �������
	 */
	public boolean evaluateActivity(Evaluate evaluate);
}
