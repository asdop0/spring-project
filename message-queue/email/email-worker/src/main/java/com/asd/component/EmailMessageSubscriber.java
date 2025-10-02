package com.asd.component;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.asd.DTO.EmailMessageDto;
import com.asd.service.EmailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailMessageSubscriber implements MessageListener {
	private final ObjectMapper objectMapper;
    private final EmailSenderService emailSenderService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
    	System.out.println("이메일 시도");
        try {
            String json = new String(message.getBody());
            EmailMessageDto dto = objectMapper.readValue(json, EmailMessageDto.class);
            System.out.println("이메일 수신: " + dto.getEmail());

            emailSenderService.sendEmail(dto.getEmail(), dto.getCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
