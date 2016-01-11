package com.socket.xueyi.domain;


import java.io.Serializable;
import java.util.Date;
import com.google.gson.annotations.Expose;
import com.socket.xueyi.enums.CityEnum;
import com.socket.xueyi.enums.StatusEnum;


/**
 * 游玩指南
 * @author ChenTao
 * @date 2015年11月19日上午1:45:33
 */

public class PlayGuide implements Serializable {

	private static final long serialVersionUID = 452502101250329930L;


	@Expose
	private String id;

	/**
	 * 城市CityEnum
	 */
	@Expose private CityEnum city;

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

	/**
	 * 摘要
	 */
	@Expose private String summary;

	/**
	 * 百度页码
	 */
	@Expose private Integer baiduPage;

	public Integer getBaiduPage() {
		return baiduPage;
	}

	public void setBaiduPage(Integer baiduPage) {
		this.baiduPage = baiduPage;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CityEnum getCity() {
		return city;
	}

	public void setCity(CityEnum city) {
		this.city = city;
	}

	public FileInfo getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(FileInfo titleImage) {
		this.titleImage = titleImage;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
