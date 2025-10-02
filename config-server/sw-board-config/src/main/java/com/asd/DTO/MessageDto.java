package com.asd.DTO;

import java.time.LocalDateTime;

import com.asd.model.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
	private Long id;
    private String title;
    private String content;
    private String senderName;
    private String receiverName;
    private LocalDateTime writeDate;

    public static MessageDto toDto(Message message) {
        return new MessageDto(
    		message.getId(),
            message.getTitle(),
            message.getContent(),
            message.getSender().getName(),
            message.getReceiver().getName(),
            message.getWriteDate()
        );
    }
}