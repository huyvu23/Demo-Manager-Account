package com.vti.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.vti.service.IEmailService;

// -	Tạo Class này để Listener và xử lý Event cho sự kiện gửi Email. Cần xây dựng thêm Service để gửi Email trong chương trình
@Component
public class SendRegistrationUserConfirmViaEmailListener
		implements ApplicationListener<OnSendRegistrationUserConfirmViaEmailEvent> {
	@Autowired
	private IEmailService emailService;

	@Override
//								Lắng nghe được sự kiện
	public void onApplicationEvent(OnSendRegistrationUserConfirmViaEmailEvent event) {
		sendConfirmViaEmail(event.getEmail());

	}

//	Gửi Email đi
	private void sendConfirmViaEmail(String email) {
		emailService.sendRegistrationUserConfirm(email);

	}

}
