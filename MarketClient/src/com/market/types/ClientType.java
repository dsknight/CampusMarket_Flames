package com.market.types;

public class ClientType {
	String username;
	String passward;
	String mail;
	String stuNo;
	String phone;
	int sex;
	public ClientType(String un,String pwd,String mail,String stuNo,String phone,int sex){
		this.username = un;
		this.passward = pwd;
		this.mail = mail;
		this.stuNo = stuNo;
		this.phone = phone;
		this.sex = sex;
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassward() {
		return passward;
	}
	public void setPassward(String passward) {
		this.passward = passward;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
}
