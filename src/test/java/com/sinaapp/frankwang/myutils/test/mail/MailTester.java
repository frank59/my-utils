package com.sinaapp.frankwang.myutils.test.mail;

import com.sinaapp.frankwang.myutils.mail.MailMessage;
import com.sinaapp.frankwang.myutils.mail.MailSender;

public class MailTester {

	public static void main(String[] args) {
		test01();
	}

	private static void test01() {
		try {
			send("frank59404.student@sina.com", "邮件发送测试", "tetstt");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public static void send(String email, String title, String content)
			throws Exception {
		MailMessage message = new MailMessage();

		message.setHost("mail.sina.com");
		message.setPort(25);
		message.setNeedAuth(true);
		message.setUsername("frank59404.student");
		message.setPassword("123456");
		message.setFromname("frank59404");
		message.setFrom("frank59404.student@youku.com");
		message.setTo(email);
		message.setSubject(title);

		message.setHTMLText(true);
		message.setText(content);

		System.out.println("开始发送。。。");
		MailSender.send(message);
		System.out.println("完成！");
	}

}
