package com.vti.entiy;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Department", catalog = "TestingSystem5")
public class Department implements Serializable {

	@Column(name = "DepartmentID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short id;

	@Column(name = "DepartmentName", length = 30, nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
	@Cascade(value = { CascadeType.REMOVE, CascadeType.SAVE_UPDATE })
	private List<Account> account;

	public Department() {
		super();
	}

	/**
	 * @return the id
	 */
	public short getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(short id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the account
	 */
	public List<Account> getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(List<Account> account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

}
