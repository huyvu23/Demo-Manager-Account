package com.vti.event;

import org.springframework.context.ApplicationEvent;

// -	Tạo Class này để khai báo Event, Event này dùng trong việc gửi Email
public class OnSendRegistrationUserConfirmViaEmailEvent extends ApplicationEvent {

	private String email;

	public OnSendRegistrationUserConfirmViaEmailEvent(String email) {
		super(email);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
