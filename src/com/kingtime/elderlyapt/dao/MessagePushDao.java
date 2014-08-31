package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.MessagePush;

/**
 * ������Ϣ�ӿ�
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public interface MessagePushDao {

	/**
	 * ������Ϣ
	 * 
	 * @param messagePush
	 *            ��Ϣʵ��
	 * @return �������
	 */
	public boolean createMessage(MessagePush messagePush);

	/**
	 * ����������Ϣ
	 * 
	 * @param messagePushs
	 *            ��Ϣ�б�
	 * @return �������
	 */
	public boolean createMessage(List<MessagePush> messagePushs);

	/**
	 * 
	 * �õ��û���������Ϣ�б�
	 * 
	 * @param uid
	 *            �û����
	 * @return ��Ϣ�б�
	 */
	public List<MessagePush> getMessages(int uid);

	/**
	 * ������Ϣ����ȡ������Ϣ
	 * 
	 * @param uid
	 *            �û����
	 * @param messageCategoryId
	 *            ��Ϣ�����
	 * @return ��Ϣ�б�
	 */
	public List<MessagePush> getMessages(int uid, int messageCategoryId);

	/**
	 * �趨����״̬Ϊ������
	 * 
	 * @param uid
	 *            �û����
	 * @return �������
	 */
	public boolean updateToPushed(int uid);

	/**
	 * �趨ĳһ������͵�����״̬Ϊ������
	 * 
	 * @param uid
	 *            �û����
	 * @param messageCategoryId
	 *            �������
	 * @return
	 */
	public boolean updateToPushed(int uid, int messageCategoryId);
}
