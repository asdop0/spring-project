package com.asd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.DTO.MessageDto;
import com.asd.model.Message;
import com.asd.model.User;
import com.asd.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {
	private final MessageRepository messageRepository;

    //전달받은 쪽지 저장
    public void wirteMessage(Message message) {
        messageRepository.save(message);
    }

    //받은 쪽지함 출력
    public List<MessageDto> receivedMessages(User user) {
        List<Message> messages = messageRepository.findAllByReceiver(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages) {
            //받은 쪽지함에서 삭제하지 않은 쪽지만 추가
            if(!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }   

    //보낸 쪽지함 출력
    public List<MessageDto> sentMessages(User user) {
        List<Message> messages = messageRepository.findAllBySender(user);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(Message message : messages) {
            //보낸 쪽지함에서 삭제하지 않은 쪽지만 추가
            if(!message.isDeletedBySender()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

  //받은 쪽지 삭제
    @Transactional
    public void deleteMessageByReceiver(Long id, User user) {
        Message message = messageRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("[deleteMessageByReceiver] 메시지를 찾을 수 없습니다.")
        );

        if(user.equals(message.getReceiver())) {
            message.deleteByReceiver(); //받은 사람의 메시지 삭제
            messageRepository.save(message);
            if (message.isDeleted()) {
                //받은 사람과 보낸 사람 모두 삭제했으면 삭제
                messageRepository.delete(message);
            }
        } else {
            throw new IllegalArgumentException("[deleteMessageByReceiver] 유저 정보가 일치하지 않습니다.");
        }
    }

    //보낸 쪽지 삭제
    @Transactional
    public void deleteMessageBySender(Long id, User user) {
        Message message = messageRepository.findById(id).orElseThrow(() -> 
        	new IllegalArgumentException("[deleteMessageBySender] 메시지를 찾을 수 없습니다.")
        );

        if(user.equals(message.getSender())) {
            message.deleteBySender(); //보낸 사람의 메시지 삭제
            messageRepository.save(message);
            if (message.isDeleted()) {
                //받은 사람과 보낸 사람 모두 삭제했으면 삭제
                messageRepository.delete(message);
            }
        } else {
        	throw new IllegalArgumentException("[deleteMessageBySender] 유저 정보가 일치하지 않습니다.");
        }


    }
}
