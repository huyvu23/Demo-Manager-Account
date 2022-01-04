package com.vti.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDto;
import com.vti.entiy.Account;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdate;
import com.vti.service.IAccountService;

// @RestController chỉ có thể trả về data trong response body. Thích hợp cho các controller để cung cấp API.
@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin("*")
public class AccountController {

	@Autowired // = AccountDemo accountDemo = new AccountDemo(new Account_Dao_Hibernate());
				// anotation đang inject cho mình
	private IAccountService accountService;

	@GetMapping
//	Pageable để phân trang
	public ResponseEntity<?> getListAccount(Pageable pageable, @RequestParam(required = false) String search) {
		Page<Account> page = accountService.getAllAccounts(pageable, search);
//										new Function<Đầu vào, Đầu ra>
		Page<AccountDto> page2 = page.map(new Function<Account, AccountDto>() {

			@Override
			public AccountDto apply(Account account) {
				AccountDto accountDto = new AccountDto();
				accountDto.setId(account.getId());
				accountDto.setEmail(account.getEmail());
				accountDto.setUsername(account.getUsername());
				accountDto.setFullname(account.getFullname());
				accountDto.setDepartment(account.getDepartment().getName());
				accountDto.setPosition(account.getPosition().getName().toString());
				accountDto.setCreateDate(account.getCreateDate());

				return accountDto;
			}

		});

//		for (Account account : listAccounts) {
//			AccountDto accountDto = new AccountDto(account.getId(), account.getEmail(), account.getUsername(),
//					account.getFullname(), account.getDepartment().getName(),
//					account.getPosition().getName().toString(), account.getCreateDate());
//			listAccountDtos.add(accountDto);
//		}

		return new ResponseEntity<>(page2, HttpStatus.OK);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable(name = "id") short id) {
		Account account = accountService.getAccountById(id);

		AccountDto accountDto = new AccountDto(account.getId(), account.getUsername(), account.getFullname(),
				account.getEmail(), account.getDepartment().getName(), account.getPosition().getName().name(),
				account.getCreateDate());
		return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);

	}

	@PostMapping
//	Requestbody đón giá trị của người dùng trả về thông qua backend
	public ResponseEntity<?> createAccount(@RequestBody AccountFormForCreating form) {
		accountService.createAccount(form);
		return new ResponseEntity<String>("Create sucess", HttpStatus.CREATED);

	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateAccount(@PathVariable(name = "id") short id,
			@RequestBody AccountFormForUpdate form2) {
		accountService.updateAccount(form2, id);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable(name = "id") short id3) {
		accountService.deleteAccountById(id3);
		return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);

	}

//	có nhiều hơn 1 mapping cho 1 đường dẫn
	@GetMapping(value = "/user/{name}")
	public ResponseEntity<?> getAccountByName(@PathVariable(name = "name") String name) {
		Account account2 = accountService.getAccountByName(name);
		AccountDto accountDto2 = new AccountDto(account2.getId(), account2.getEmail(), account2.getUsername(),
				account2.getFullname(), account2.getDepartment().getName(), account2.getPosition().getName().name(),
				account2.getCreateDate());
		return new ResponseEntity<>(accountDto2, HttpStatus.OK);

	}

	@GetMapping(value = "/EmailExists/{email}")
	public ResponseEntity<?> existsByEmail1(@PathVariable(name = "email") String email) {
		Boolean resultEmail = accountService.existsByEmail(email);
		return new ResponseEntity<>(resultEmail, HttpStatus.OK);

	}

	@GetMapping(value = "/UserNameExists/{username}")
	public ResponseEntity<?> existsByUserName1(@PathVariable(name = "username") String userName) {
		Boolean resultUserName = accountService.existsByUsername(userName);
		return new ResponseEntity<>(resultUserName, HttpStatus.OK);

	}

}
