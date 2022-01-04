package com.vti.entiy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "`Registration_User_Token`")
public class RegistrationUserToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`id`", unique = true, nullable = false)
	private int id;

	@Column(name = "`token`", nullable = false, length = 36, unique = true)
	private String token;

//	maping theo kiểu 1:1 vì 1 account chỉ có 1 token
//	từ userid ==> sẽ thấy dc tất cả thông tin của account đó (https://hocspringboot.net/2020/10/21/onetoone-trong-spring-boot-la-gi/ -- xem link này cho hiểu)
//	targetEntity = Account.class ==> liên kết đến kiểu dữ liệu gì
	@OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "`userid`")
	private Account account;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "`expiryDate`", nullable = false)
	private Date expiryDate;

	public RegistrationUserToken() {
	}

	public RegistrationUserToken(String token, Account account) {
		this.token = token;
		this.account = account;

		// 1h
		expiryDate = new Date(System.currentTimeMillis() + 360000);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
