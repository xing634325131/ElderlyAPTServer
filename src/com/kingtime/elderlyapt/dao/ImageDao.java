package com.kingtime.elderlyapt.dao;

import com.kingtime.elderlyapt.entity.PictureUpload;

/**
 * 获取图片接口
 * 
 * @author xp
 * 
 * @created 2014-8-7
 */
public interface ImageDao {
	/**
	 * 根据用户ID获取用户头像名称
	 * 
	 * @param uid用户ID
	 * @return 用户头像名称
	 */
	public String getUserPhoto(int uid);

	/**
	 * 根据活动ID获取活动主图片名称
	 * 
	 * @param activityId活动ID
	 * @return 活动主图片名称
	 */
	public String getActivityMainPic(int activityId);

	/**
	 * 保存用户上传的活动图片
	 * 
	 * @param pictureUpload
	 *            图片
	 * @return 操作结果
	 */
	public boolean saveUploadActivityImage(PictureUpload pictureUpload);

	/**
	 * 保存用户上传的头像
	 * 
	 * @param pictureUpload
	 *            图片
	 * @return 操作结果
	 */
	public boolean saveUploadUserPhoto(PictureUpload pictureUpload);

	/**
	 * 更新已经提前上传的活动图片，使之对应
	 * 
	 * @param activityId
	 *            活动编号
	 * @param imageName
	 *            图片名称
	 * @return
	 */
	public boolean updateUploadImage(int activityId, String imageName);
}
