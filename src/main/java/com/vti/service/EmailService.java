package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vti.entiy.Account;
import com.vti.entiy.RegistrationUserToken;
import com.vti.repository.IRegistrationUserTokenRepository;

@Component
@Service
public class EmailService implements IEmailService {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private IRegistrationUserTokenRepository registrationUserTokenRepository;

	@Override
	public void sendRegistrationUserConfirm(String email) {
		// Sử dụng lại getAccountByEmail hàm đã khai báo trong AccountService để lấy ra
		// Account theo email nhận được
		Account account = accountService.getAccountByEmail(email);

//		lấy token lưu trong bảng Registration_User_Token dựa vào account tìm dc bên trên(Lấy lại cả 1 bản ghi của token đó)
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.getByAccount(account);
		String token = registrationUserToken.getToken();

		String confirmationUrl = "http://localhost:8080/api/v1/accountsRegister/activeUser?token=" + token;

//		Tiêu đề = subject
		String subject = "Xác Nhận Đăng Ký Account";
//		Nội dung = content
		String content = "Bạn đã đăng ký thành công." + confirmationUrl;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject(subject);
		message.setText(content);

		mailSender.send(message);

	}

}
