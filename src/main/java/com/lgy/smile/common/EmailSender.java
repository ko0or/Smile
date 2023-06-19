package com.lgy.smile.common;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.context.support.GenericXmlApplicationContext;

/* ======================================================================================================== >>



# 외부에서 사용하고싶다면 ?
*. 두번째 위치의 매개변수 sendType.종류 2가지중 하나 선택 !	
	
	본인인증용 메일발송 
		==>  new MailService().sendEmail("받을사람 이메일 주소@도메인.com", sendType.checkForUser);
	
	임시비밀번호 발급용 메일발송 
		==> new MailService().sendEmail("받을사람 이메일 주소@도메인.com", sendType.forgetPassword);



/* ======================================================================================================== >>

# Enum
		-> 이메일을 보낼때 타입을 설정하기위해 사용
		[타입종류] check : 회원 신규가입시
		[타입종류] forget : 비밀번호 분실해서 다시 찾을때
		


# 메소드 흐름 .. ?
	[1] 외부에서 해당 클래스 객체생성 + sendEmail(수신자이멜주소,  Enum타입) 메소드 사용 
	-> [2] sendMail() 메소드 호출시, 메시지 전송을 위한 세팅을 하기위해 ready() 메소드를 호출 
	-> [3] 그리고 sendMail() 메소드로 돌아와서,  매개변수로 받은 Enum타입 종류에 따라 분기처리함 
	-> [4] 분기처리내용:  Enum 타입에 따라   본인인증용 메일발송 or 임시 비밀번호 발급용 메일발송



# 중요 메소드 종류	

	sendEmail(String타입, Enum타입)
	    @ 특징 : emailSendReady 메소드로 메일전송준비, 그 다음 이메일 전송
	    -> 매개변수 String 타입 : 메일을 받아야할 대상 ( 회원 ) 의 이메일 주소
	    -> 매개변수 Enum 타입 : 매개변수로 받은 Enum 타입에 따라 메일전송시 내용을 분기처리함 ( 본인인증용 or 비밀번호 찾기용 )
		
	ready(String타입)
	    @ 특징 : 위에 있는 sendEmail() 메소드가 동작되기위한, 기본 세팅을 해주는 메소드
	    -> 매개변수 String 타입 : 메일 발송에 필요한 객체를 생성, 세팅
	  
	
	setContentCheckForUser(int타입)
	setContentForgetPassword(int타입)
	    @ 특징 : 메일 전송에 필요한 본문내용을 세팅해주는 메소드들,  위의 sendEmail() 메소드의 분기처리된 결과에 의해 호출된다.
	    -> 매개변수 int 타입 : 위의 sendEmail() 메소드 내부에서 생성된 랜덤숫자를 받아서,  본문내용에 같이 작성한다
	    
	    
	  	
<< ========================================================================================================  */

public class EmailSender {

	// sendEmail() 메소드의 2번째 매개변수에 사용될 타입 ( 본인인증 : checkForUser, 임시 비밀번호발급 :
	// checkForUser )
	enum sendType {

		create, forget

	}

	// 이메일 발송내용 == >> 본인확인시 (신규가입 or 비밀번호 찾기)
	private String setContentCheckForUser(int certificationNumber) {
		return "<div style=\"display: flex; justify-content: center; align-items: center; height: 500px; background-color:antiquewhite;\"> <div style=\"width: 100%; height: 300px; text-align: center; background-color: white; padding: 20px;\"> <h2>팀 프로젝트 테스트</h2><br /> <p>본인확인 인증번호 ["
				+ certificationNumber + "]를 입력해주세요.</p> </div> </div>";
	}

	// 이메일 발송내용 == >> 임시비밀번호 발급요청시
	private String setContentForgetPassword(int certificationNumber) {
		return "<div style=\"display: flex; justify-content: center; align-items: center; height: 500px; background-color:antiquewhite;\"> <div style=\"width:100%; height: 300px; text-align: center; background-color: white; padding: 20px;\"> <h2>팀 프로젝트 테스트</h2><br /> <p>임시 비밀번호가  ["
				+ certificationNumber + "] 으로 발급되었습니다.</p><sub><i>*</i> 로그인 후 꼭 비밀번호를 변경해주세요 !</sub> </div> </div>";
	}

	public boolean sendEmail(String to, sendType type) {
		try {

			emailTitle = "메일 제목";

			ready(to); // ==> 이메일 발송을 위해 필요한 정보를 세팅해주는 ready() 메소드 호출
			int certificationNumber = (int) (Math.random() * 99999) + 10000; // ==> 인증번호 준비

			/* ===== 매개변수로 받은 Enum 타입에 따라 발송될 내용을 분기처리 ( 본인인증용 or 임시비밀번호 발급용 ) ===== */
			if (type.equals(sendType.create) == true) {
				emailContent.append(setContentCheckForUser(certificationNumber)); // ==> 메일 발송시 본인인증용으로 발송

			} else if (type.equals(sendType.forget) == true) {
				emailContent.append(setContentForgetPassword(certificationNumber)); // ==> 메일 발송시 임시비밀번호 발급용으로 발송
			}

			
			
			
			
			
			
			
			/* ======================================================================================================== >>
			  
			  # 이 다음부터는, 메일 발송관련 설정에 관한 내용들이 정리되어있음으로 수정 X
			  
			  
			 << ======================================================================================================== */
			message.setSubject(emailTitle);
			message.setContent(emailContent.toString(), "text/html; charset=utf-8");

			Transport.send(message);
			System.out.println("이메일 전송 완료 :) ");
			return true;

		} catch (MessagingException me) {
			me.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void ready(String to) throws MessagingException {

		// 의존성 주입
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		mailConfig = ctx.getBean("mailConfig", EmailSenderConfig.class);
		System.out.println(mailConfig.toString());

		// SMTP 설정
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", mailConfig.getHost());
		properties.setProperty("mail.smtp.auth", mailConfig.getAuth());
		properties.setProperty("mail.smtp.port", mailConfig.getPort());
		properties.setProperty("mail.smtp.ssl.enable", mailConfig.getSsl());

		// 메일 발송자 세팅 ( 아이디 + @ + smtp. 뒤에있는 값 )
		from = mailConfig.getNaverId() + "@" + mailConfig.getHost().split("smtp.")[1];

		// 메일 발송자 계정 로그인
		auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfig.getNaverId(), mailConfig.getNaverPw());
			}
		};

		// 객체생성
		messageBody = new MimeBodyPart();
		message = new MimeMessage(Session.getDefaultInstance(properties, auth));
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// 발송준비
		emailContent = new StringBuffer();
		messageBody.setContent(emailContent.toString(), "text/html; charset=utf-8");
		message.setContent(new MimeMultipart(messageBody));
	}

	// 전역변수들 관리
	private static EmailSenderConfig mailConfig = null;

	private String from = null;
	private Authenticator auth = null;
	private Properties properties = null;

	private MimeMessage message = null;
	private MimeBodyPart messageBody = null;

	private static String emailTitle = null;
	private static StringBuffer emailContent = null;
}
