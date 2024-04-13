package com.nps.coco.domain.member.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendEmailMessage(String email) throws Exception {
        String code = createCode(); // 인증코드 생성
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
        message.setSubject("[coco] 회원가입 인증 번호 안내" + code); // 이메일 제목
        message.setText(createHtml(code), "utf-8", "html"); // 내용 설정(Template Process)

        // 보낼 때 이름 설정하고 싶은 경우
        // message.setFrom(new InternetAddress([이메일 계정], [설정할 이름]));

        emailSender.send(message); // 이메일 전송
    }

    private String createHtml(String code) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div align=\"center\" style=\"font-size: 15px\">");
        sb.append("[인증 코드] ").append(code);
        sb.append("<br/><br/>인증번호를 입력해 주세요.<br/><br/>");
        return sb.toString();
    }

//    private String setContext(String code) { // 타임리프 설정하는 코드
//        Context context = new Context();
//        context.setVariable("code", code); // Template에 전달할 데이터 설정
//        return templateEngine.process("mail", context); // mail.html
//    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }
}
