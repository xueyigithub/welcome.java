package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;
import com.socket.xueyi.enums.StatusEnum;

import java.util.Date;

/**
 * 文件信息
 * 底层对象不关联外键
 * 注意：修改属性时候需要维护Dto
 * @author ChenTao
 * @date 2015年7月20日下午10:13:46
 */

public class FileInfo {

	@Expose private String id;

	/**
	 * 操作人User Id
	 */
	private String operatorId;

	/**
	 * 图片User Id
	 */


	@Expose private String download;



	/**
	 * 状态StatusEnum
	 */
	@Expose private StatusEnum status;

	/**
	 * 创建时间
	 */

	private Date createTime;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件路径
	 */
	private String fileUrl;

	public String getDownload() {
		return download;
	}

	public void setDownload(String download) {
		this.download = download;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
