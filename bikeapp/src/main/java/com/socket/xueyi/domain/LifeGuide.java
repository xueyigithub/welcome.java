package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;
import com.socket.xueyi.enums.NewsTypeEnum;
import com.socket.xueyi.enums.StatusEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 生活指南
 * @author ChenTao
 * @date 2015年11月19日上午1:45:33
 */
public class LifeGuide implements Serializable {


	@Expose private String id;

	/**
	 * 操作人User
	 */

	@Expose private User operator;

	/**
	 * 图片List "FileInfo"
	 */

	@Expose private List<FileInfo> images;
	/**
	 * 标题图片 "FileInfo"
	 */

	@Expose private FileInfo titleImage;


	/**
	 * 状态StatusEnum
	 */

	@Expose private StatusEnum status;

	/**
	 * 创建时间
	 */
	@Expose private Date createTime;

	/**
	 * 标题
	 */
	@Expose private String title;

	/**
	 * 内容
	 */
	@Expose private String content;

	public FileInfo getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(FileInfo titleImage) {
		this.titleImage = titleImage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public List<FileInfo> getImages() {
		return images;
	}

	public void setImages(List<FileInfo> images) {
		this.images = images;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
