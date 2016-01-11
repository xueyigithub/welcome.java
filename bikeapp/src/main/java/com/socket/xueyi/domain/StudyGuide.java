package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;
import com.socket.xueyi.enums.StatusEnum;

import java.util.Date;

import lombok.Builder;
import lombok.Data;


/**
 * 就业指南
 * @author ChenTao
 * @date 2015年11月19日上午1:45:33
 */
@Data
@Builder
public class StudyGuide  {

	@Expose
	private String id;

	/**
	 * 状态StatusEnum
	 */
	@Expose private StatusEnum status;

	/**
	 * 创建时间
	 */
	@Expose private Date createTime;

	/**
	 * 浙师大网站的创建时间
	 */
	@Expose private Date originalTime;

	/**
	 * 标题
	 */
	@Expose private String title;

	/**
	 * 内容
	 */
	@Expose private String content;

	/**
	 * 工作城市
	 */
	@Expose private String workCity;


}
