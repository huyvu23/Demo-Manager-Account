package com.vti.service;

import org.springframework.data.domain.Page;
// -	Chú ý extends UserDetailsService để quản lý User login
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vti.entiy.Account;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForCreatingRegister;
import com.vti.form.AccountFormForUpdate;

// để sử dụng userdetail để bảo mật cần extends UserDetailsService,
// class UserDetailsService để quản lý UserLogin từ Spring
public interface IAccountService extends UserDetailsService {
	public Page<Account> getAllAccounts(Pageable pageable, String search);

	public Account getAccountById(short id);

	public void createAccount(AccountFormForCreating form);

	public void updateAccount(AccountFormForUpdate form2, short id);

	public void deleteAccountById(short id3);

	public Account getAccountByName(String name);

	Account getAccountByUserName(String username);

	public Boolean existsByEmail(String email);

	public Boolean existsByUsername(String userName);

	public void createAccountRegister(AccountFormForCreatingRegister form3);

	public void activeUser(String token); // Dùng để Active User

	public Account getAccountByEmail(String email2);

}
