package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entiy.Account;

//														(tên class,dữ liệu khóa chính)
public interface IAccountRepository extends JpaRepository<Account, Short>, JpaSpecificationExecutor<Account> {

	public Account findByUsername(String name);

	public Boolean existsByEmail(String email);

	public Boolean existsByUsername(String userName);

	public Account getByEmail(String email2);

}
