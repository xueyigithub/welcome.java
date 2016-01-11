package com.socket.xueyi.domain;

import com.google.gson.annotations.Expose;
import com.socket.xueyi.enums.GenderEnum;
import com.socket.xueyi.enums.RoleEnum;
import com.socket.xueyi.enums.StatusEnum;

import java.util.Date;

/**
 * 用户
 * 底层对象不关联外键
 * 注意：修改属性时候需要维护Dto
 * @author ChenTao
 * @date 2015年11月18日下午9:41:14
 */

public class User {


	@Expose private String id;

	/**
	 * 头像FileInfo Id
	 */
	@Expose private String headPortraitId;

	/**
	 * 角色RoleEnum
	 */
	private RoleEnum role;

	/**
	 * 状态StatusEnum
	 */
	@Expose private StatusEnum status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 登录名称
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 昵称姓名
	 */
	@Expose private String nickName;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 性别GenderEnum
	 */
	private GenderEnum gender;

	/**
	 * 电话
	 */
	private String phoneNumber;

	/**
	 * 电话短号
	 */
	private String phoneShorter;
	
	/**
	 * 身份证
	 */
	private String identityCard;

	/**
	 * 学号或工号
	 */
	private String jobNumber;

	/**
	 * 用户联系地址
	 */
	private String contactAddr;

	/**
	 * 邮编
	 */
	private String zipcode;

	/**
	 * 最近一次修改时间
	 */
	private Date modifyTime;

	/**
	 * 用户描述
	 */
	private String description;

	public String getHeadPortraitId() {
		return headPortraitId;
	}

	public void setHeadPortraitId(String headPortraitId) {
		this.headPortraitId = headPortraitId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RoleEnum getRole() {
		return role;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneShorter() {
		return phoneShorter;
	}

	public void setPhoneShorter(String phoneShorter) {
		this.phoneShorter = phoneShorter;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
