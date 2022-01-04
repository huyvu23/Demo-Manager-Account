package com.vti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.form.AccountFormForCreatingRegister;
import com.vti.service.IAccountService;

// class này dành cho chức năng đăng kí
@RestController
@RequestMapping(value = "api/v1/accountsRegister")
@CrossOrigin("*")
public class AccountRegisterController {

	@Autowired
	private IAccountService accountService;

	@PostMapping()
	public ResponseEntity<?> createAccountRegister(@RequestBody AccountFormForCreatingRegister form3) {
		accountService.createAccountRegister(form3);
		return new ResponseEntity<String>("New creation successful, Please check your Email!", HttpStatus.CREATED);

	}

	@GetMapping("/activeUser")
//	Chúng ta sử dụng @RequestParam để bắt các giá trị các tham số mà người dùng truyền vào trên URL theo định dạng key và value.
	public ResponseEntity<?> activeUserViaEmail(@RequestParam String token) {

//		active user
		accountService.activeUser(token);
		return new ResponseEntity<>("Active success", HttpStatus.OK);

	}
}
