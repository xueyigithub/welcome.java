package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;
import com.socket.xueyi.enums.NewsTypeEnum;
import com.socket.xueyi.enums.StatusEnum;

import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 校园新闻
 * @author ChenTao
 * @date 2015年11月19日上午1:45:33
 */
@Table(name="campusnews")
public class CampusNews implements Serializable {


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
	 * 类型NewsTypeEnum
	 */
	@Expose private NewsTypeEnum newsType;

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

	/**
	 * 摘要
	 */
	@Expose private String summary;

	/**
	 * borderId
	 */
	@Expose private Long border;


	public Long getBorder() {
		return border;
	}

	public void setBorder(Long border) {
		this.border = border;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public FileInfo getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(FileInfo titleImage) {
		this.titleImage = titleImage;
	}

	public NewsTypeEnum getNewsType() {
		return newsType;
	}

	public void setNewsType(NewsTypeEnum newsType) {
		this.newsType = newsType;
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

	@Override
	public String toString() {
		return "CampusNews{" +
				"id='" + id + '\'' +
				", operator=" + operator +
				", images=" + images +
				", titleImage=" + titleImage +
				", newsType=" + newsType +
				", status=" + status +
				", createTime=" + createTime +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
