package com.example.feedservice.domain.mail;

import com.example.feedservice.global.exception.ApiException;
import com.example.feedservice.global.exception.ErrorType;
import com.example.feedservice.global.util.RedisUtil;
import java.util.NoSuchElementException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private RedisUtil redisUtil;

    public String checkIsAuthCode(String email) {
        String redisValue = redisUtil.getData("authCode:" + email);
        if (redisValue != null) {
            return redisValue;
        }
        return null;
    }

    public String getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

    public void validateSetAuthCode(String email) {
        String redisValue = redisUtil.getData("authCode:" + email);

        if (redisValue == null) {
            throw new ApiException(ErrorType.USER_NOT_FOUND);
        }
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public boolean authenticateRandomNumber(String randomNumber, String email) {
        String redisValue = redisUtil.getData("authCode:" + email);

        if (redisValue == null) {
            throw  new NoSuchElementException("authentication code expired for mail : " + email);
        }
        if (redisValue.equals(randomNumber)) {
            return false;
        }
        return true;
    }
}
