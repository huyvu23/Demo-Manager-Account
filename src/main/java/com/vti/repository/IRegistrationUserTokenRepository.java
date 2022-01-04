package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.entiy.Account;
import com.vti.entiy.RegistrationUserToken;

// -	Tạo thêm repository này để thực hiện lưu thông tin Token xuống DB
public interface IRegistrationUserTokenRepository extends JpaRepository<RegistrationUserToken, Integer> {

	public RegistrationUserToken findByToken(String token);

	public RegistrationUserToken getByAccount(Account acccount);
}
