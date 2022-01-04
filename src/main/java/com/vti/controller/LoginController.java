package com.vti.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.LoginDto;
import com.vti.entiy.Account;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/v1/login")
@CrossOrigin("*")
public class LoginController {

	@Autowired
	private IAccountService accountService;

	@GetMapping
	public ResponseEntity<?> login(Principal principal) {
		String userName = principal.getName();

		// lấy ra account bằng username
		Account account = accountService.getAccountByUserName(userName);

		LoginDto loginDto = new LoginDto(account.getId(), account.getFullname());

		return new ResponseEntity<>(loginDto, HttpStatus.OK);

	}
}
