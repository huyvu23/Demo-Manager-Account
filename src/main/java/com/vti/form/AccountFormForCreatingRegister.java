package com.vti.form;

// class này là thông tin người dùng cần điền khi đăng kí
public class AccountFormForCreatingRegister {

	private String email;
	private String username;
	private String fullname;
	private short departmentId;
	private short positionId;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public short getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(short departmentId) {
		this.departmentId = departmentId;
	}

	public short getPositionId() {
		return positionId;
	}

	public void setPositionId(short positionId) {
		this.positionId = positionId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountFormForCreatingRegister(String email, String username, String fullname, short departmentId,
			short positionId, String password) {
		super();
		this.email = email;
		this.username = username;
		this.fullname = fullname;
		this.departmentId = departmentId;
		this.positionId = positionId;
		this.password = password;
	}

}
