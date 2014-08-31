package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.User;

/**
 * �û��ӿ���
 * 
 * @author xp
 * 
 * @created 2014-4-25
 */
public interface UserDao {

	/**
	 * ��¼����
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return ���ظ��û�ʵ���������¼ʧ�ܷ���Ϊ��
	 */
	public User login(String username, String password);

	/**
	 * �����û�ID��ȡ�û���Ϣ
	 * 
	 * @param uid
	 *            �û�ID
	 * @return ���ظ��û�ʵ������������ڷ���Ϊ��
	 */
	public User getUser(int uid);

	/**
	 * ��ȡ����ϵͳ�û����Ա���л�Ƽ���ϵͳ�����û�Ϊ�����ˣ�
	 * 
	 * @return �û��б�
	 */
	public List<User> getAllUser();

	/**
	 * �����û���Ϣ�����û�ע��
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @return ���ظ��û�ʵ�������ע��ʧ�ܷ���Ϊ��
	 */
	public User register(User user);

	/**
	 * ���ݻ��Ż�ȡ��������Ϣ
	 * 
	 * @param activityId
	 *            ����
	 * @return ��������Ϣ
	 */
	public User getUserByActivityId(int activityId);

	/**
	 * �����û�
	 * 
	 * @param updateUser
	 *            �û�ʵ��
	 * @return ������ɺ󷵻������û�ʵ��
	 */
	public User updateUserInfo(User updateUser);

	/**
	 * �����û������ȣ������߸��´����ߵ�������
	 * 
	 * @param evaluate
	 *            ����ʵ��
	 * @return �������
	 */
	public boolean updateUserCredibility(Evaluate evaluate);

	/**
	 * �����û�������,�����߸��²����û���������
	 * 
	 * @param evaluate
	 * @return
	 */
	public boolean updateUserCredibilityByAppraise(Evaluate evaluate);

	/**
	 * �����û�����
	 * 
	 * @param uid
	 *            �û����
	 * @param integral
	 *            ����
	 * @return �������
	 */
	public boolean updateUserIntegral(int uid, int integral);
}
