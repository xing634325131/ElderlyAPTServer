package com.kingtime.elderlyapt.service;

/**
 * �״̬������� �ڳ����涨�Ļʱ�������û�û���ֶ��л��״̬��ϵͳ�������л�
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public interface ActivityStateTaskService {

	/**
	 * ���»״̬
	 * 
	 * @param stateId
	 *            Ҫ���µĻ״̬
	 * @param delayMin
	 *            �ӳٸ���ʱ��
	 * @return ����������������ݸ���
	 */
	public int updateActivityState(int stateId, int delayMin);
}
