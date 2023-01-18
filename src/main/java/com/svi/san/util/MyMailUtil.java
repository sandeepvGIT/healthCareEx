package com.svi.san.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("mailUtil")
public class MyMailUtil {
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean send(String to[],String cc[],String[] bcc[],String subject,String text,Resource[] file) {
		boolean sent=false;
		try {
			//create empty message object
			MimeMessage message=mailSender.createMimeMessage();
			//fill details (message,attachmentExist)
			MimeMessageHelper helper=new MimeMessageHelper(message, file!=null && file.length>0);
			helper.setTo(to);
			if(cc!=null)
				helper.setCc(cc);
			if(bcc!=null)
				helper.setBcc(cc);
			helper.setSubject(subject);
			helper.setText(text);
			if(file!=null) {
				for(Resource rob:file)
					helper.addAttachment(rob.getFilename(), rob);
			}
			//send mail
			mailSender.send(message);
			sent=true;
		}catch (Exception e) {
			e.printStackTrace();
			sent=false;
		}
		return sent;
	}
	
	//overloaded sent() method
	public boolean send(String to,String subject,String text,Resource file) {
		
		return send(new String[] {to},null,null,subject,text,file!=null? new Resource[] {file}:null);
	}
	
	public boolean send(String to,String subject,String text) {
		
		return send(to,subject,text,null);
	}

}
