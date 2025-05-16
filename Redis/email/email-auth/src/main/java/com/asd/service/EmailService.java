package com.asd.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.asd.DTO.EmailMessageDto;
import com.asd.DTO.EmailVerifyResponseDto;
import com.asd.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final RedisRepository redisRepository;
	private final ObjectMapper objectMapper;
	private final String CHANNEL = "emailQueue";
	
	public void publishEmailMessage(String email) {
		String code = generateVerificationCode(); // 인증 코드 생성
		redisRepository.saveData("email:" + email, code); // 인증 코드 저장
		
        try {
        	EmailMessageDto message = new EmailMessageDto(email, code);
            String json = objectMapper.writeValueAsString(message);
            redisRepository.sendEmailAuthMessage(CHANNEL, json);
            System.out.println("메시지 발행 완료: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
	
	private String generateVerificationCode() {
        int code = 100000 + new Random().nextInt(900000);
        return String.valueOf(code);
    }
	
	public EmailVerifyResponseDto verifyEmail(EmailMessageDto dto) {
		String value = redisRepository.getData("email:" + dto.getEmail()); // 인증 코드 조회
		System.out.println("redis에 저장된 코드: " + value);
		System.out.println("전달받은 코드: " + dto.getCode());
		if (value != null && dto.getCode().equals(value)) { // 인증 코드 비교
			redisRepository.deleteData("email:" + dto.getEmail()); // 인증 코드 삭제
			System.out.println("이메일 인증 성공");
			return new EmailVerifyResponseDto(true, "이메일 인증 성공");
		} else {
			System.out.println("이메일 인증 실패");
			return new EmailVerifyResponseDto(false, "이메일 인증 실패");
		}
	}
}
