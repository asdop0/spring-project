package com.asd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.EmailMessageDto;
import com.asd.DTO.EmailVerifyResponseDto;
import com.asd.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {
	private final EmailService emailService;
	
	@PostMapping("/email-request")
    public ResponseEntity<?> requestEmail(@RequestParam String email) {
    	emailService.publishEmailMessage(email);
        return ResponseEntity.ok("인증 이메일 전송 완료");
    }
	
	@GetMapping("/email-verify")
	public EmailVerifyResponseDto verifyEmail(@RequestBody EmailMessageDto dto) {
		return emailService.verifyEmail(dto);
	}
}
