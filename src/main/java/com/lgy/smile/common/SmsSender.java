package com.lgy.smile.common;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Slf4j
public class SmsSender {
	final DefaultMessageService messageService;

    
	// 생성자 (  API KEY 세팅  )
    public SmsSender() {    	
    	this.messageService = NurigoApp.INSTANCE.initialize("NCSPEGIC6JFWV5FB", "S7YVLM84JJUWLMAWDRI91TFBPGMGRGBS", "https://api.coolsms.co.kr");
    }
	
    // 단일 문자 발송 
    public int send(String to) {
    	int certificationNumber = (int) (Math.random() * 99999) + 10000; // ==> 인증번호
        Message message = new Message();
        message.setFrom("01086214360");
        
        message.setTo( to.replace("-", "") );
        message.setText("[싱글벙글] 본인 인증 번호를 입력해주시기 바랍니다 :  "+ certificationNumber );
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        
        return certificationNumber;
    }
}
