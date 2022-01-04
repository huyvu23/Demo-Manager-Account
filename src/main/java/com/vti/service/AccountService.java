package com.vti.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vti.entiy.Account;
import com.vti.entiy.AccountStatus;
import com.vti.entiy.Department;
import com.vti.entiy.Position;
import com.vti.entiy.RegistrationUserToken;
import com.vti.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForCreatingRegister;
import com.vti.form.AccountFormForUpdate;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.repository.IPositionRepository;
import com.vti.repository.IRegistrationUserTokenRepository;
import com.vti.specification.AccountSpecification;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IDepartmentRepository departmentRepository;

	@Autowired
	private IPositionRepository positionRepository;

//	Khai báo PasswordEncoder để mã hóa mật khẩu trên frontend về dạng base64
	@Autowired
	private PasswordEncoder passwordEncoder;

//	Khai báo ApplicationEventPublisher để bắn event
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private IRegistrationUserTokenRepository registrationUserTokenRepository;

//	hàm này để phân trang và search dữ liệu
	@Override
	public Page<Account> getAllAccounts(Pageable pageable, String search) {
		// TODO Auto-generated method stub
		Specification<Account> where = null;
		// nếu search khác rỗng
		if (!StringUtils.isEmpty(search)) {
			AccountSpecification nameSpecification = new AccountSpecification("fullname", "LIKE", search);
			AccountSpecification emailSpecification = new AccountSpecification("email", "LIKE", search);
			AccountSpecification departmentSpecification = new AccountSpecification("department.name", "LIKE", search);

			where = Specification.where(nameSpecification).or(emailSpecification).or(departmentSpecification);
		}
		return accountRepository.findAll(where, pageable);
	}

	@Override
	public Account getAccountById(short id) {
		return accountRepository.getById(id);
	}

	@Override
	public void createAccount(AccountFormForCreating form) {
		Account account = new Account();
//		lấy ra ID của phòng ban mà người dùng nhập vào trong form để set
		Department department = departmentRepository.getById(form.getDepartmentId());
		Position position = positionRepository.getById(form.getPositionId());
		account.setEmail(form.getEmail());
		account.setUsername(form.getUsername());
		account.setFullname(form.getFullname());
		account.setDepartment(department);
		account.setPosition(position);
		accountRepository.save(account);

	}

	@Override
	public void updateAccount(AccountFormForUpdate form2, short id) {
		Account account2 = accountRepository.getById(id);
		Department department = departmentRepository.getById(form2.getDepartmentId());
		Position position = positionRepository.getById(form2.getPositionId());
		account2.setFullname(form2.getFullname());
		account2.setDepartment(department);
		account2.setPosition(position);
		accountRepository.save(account2);

	}

	@Override
	public void deleteAccountById(short id3) {
		accountRepository.deleteById(id3);

	}

	@Override
	public Account getAccountByName(String name) {

		return accountRepository.findByUsername(name);
	}

//	User detail chứa password và username để đăng nhập,PHẦN NÀY ĐỂ LOGIN
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Từ username tìm ra account tương ứng
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList("user"));
	}

	@Override
	public Account getAccountByUserName(String username) {
		return accountRepository.findByUsername(username);
	}

//	Check xem email đã tồn tại trên hệ thống hay chưa
	@Override
	public Boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}

//	Check xem username đã tồn tại trên hệ thống hay chưa
	@Override
	public Boolean existsByUsername(String userName) {

		return accountRepository.existsByUsername(userName);
	}

	@Override
	public void createAccountRegister(AccountFormForCreatingRegister form3) {
		Account account2 = new Account();
//		lấy ra ID của phòng ban mà người dùng nhập vào trong form để set
		Department department = departmentRepository.getById(form3.getDepartmentId());
		Position position = positionRepository.getById(form3.getPositionId());
		account2.setEmail(form3.getEmail());
		account2.setUsername(form3.getUsername());
		account2.setFullname(form3.getFullname());
		account2.setDepartment(department);
		account2.setPosition(position);

//		mã hóa mật khẩu bằng .encode
		account2.setPassword(passwordEncoder.encode(form3.getPassword()));
		accountRepository.save(account2);
//		tạo mã token lưu vào DB tương ứng với account vừa tạo 
		createTokenNewUser(account2);

//		Gửi email xác thực người dùng
		sendRegistrationUserConfirmViaEmail(account2.getEmail());

	}

	private void sendRegistrationUserConfirmViaEmail(String email) {
//		Phát ra thông báo tôi đang cần gửi email:Event
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
	}

	private void createTokenNewUser(Account account2) {
//		Tạo token bằng UUID
		String newToken = UUID.randomUUID().toString();

		RegistrationUserToken registrationUserToken = new RegistrationUserToken(newToken, account2);
//		Lưu token xuống DB
		registrationUserTokenRepository.save(registrationUserToken);

	}

	@Override
	public void activeUser(String token) {
//		Tìm lại token đang lưu trên DB dựa vào thông tin token truyền về
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);
// 		Tìm lại account tương ứng với token nhận được
		Account account = registrationUserToken.getAccount();

//		Thực hiện active account với token tương ứng
		account.setStatus(AccountStatus.ACTIVE);

		accountRepository.save(account);
//		Xóa token sau khi active xong
		registrationUserTokenRepository.deleteById(registrationUserToken.getId());
	}

	@Override
	public Account getAccountByEmail(String email2) {
		// TODO Auto-generated method stub
		return accountRepository.getByEmail(email2);
	}

}
