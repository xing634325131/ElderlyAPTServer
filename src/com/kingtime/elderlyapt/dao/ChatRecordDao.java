package com.kingtime.elderlyapt.dao;

import java.util.List;

import com.kingtime.elderlyapt.entity.ChatRecord;

/**
 * ������Ϣ�ӿ�
 * 
 * @author xp
 * 
 * @created 2014-8-26
 */
public interface ChatRecordDao {

	/**
	 * ͨ�����Ż�ȡ�û�������¼
	 * 
	 * @param acivityId
	 *            ����
	 * @return
	 */
	public List<ChatRecord> getChatRecords(int activityId);

	/**
	 * ������¼
	 * 
	 * @param record
	 *            ��¼ʵ��
	 * @return �������
	 */
	public boolean createChatRecord(ChatRecord record);
}
