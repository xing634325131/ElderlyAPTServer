package com.kingtime.elderlyapt.dao;

import com.kingtime.elderlyapt.entity.PictureUpload;

/**
 * ��ȡͼƬ�ӿ�
 * 
 * @author xp
 * 
 * @created 2014-8-7
 */
public interface ImageDao {
	/**
	 * �����û�ID��ȡ�û�ͷ������
	 * 
	 * @param uid�û�ID
	 * @return �û�ͷ������
	 */
	public String getUserPhoto(int uid);

	/**
	 * ���ݻID��ȡ���ͼƬ����
	 * 
	 * @param activityId�ID
	 * @return ���ͼƬ����
	 */
	public String getActivityMainPic(int activityId);

	/**
	 * �����û��ϴ��ĻͼƬ
	 * 
	 * @param pictureUpload
	 *            ͼƬ
	 * @return �������
	 */
	public boolean saveUploadActivityImage(PictureUpload pictureUpload);

	/**
	 * �����û��ϴ���ͷ��
	 * 
	 * @param pictureUpload
	 *            ͼƬ
	 * @return �������
	 */
	public boolean saveUploadUserPhoto(PictureUpload pictureUpload);

	/**
	 * �����Ѿ���ǰ�ϴ��ĻͼƬ��ʹ֮��Ӧ
	 * 
	 * @param activityId
	 *            ����
	 * @param imageName
	 *            ͼƬ����
	 * @return
	 */
	public boolean updateUploadImage(int activityId, String imageName);
}
