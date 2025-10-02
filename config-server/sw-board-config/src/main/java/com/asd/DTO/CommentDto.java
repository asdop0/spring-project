package com.asd.DTO;

import java.time.LocalDateTime;

import com.asd.model.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private String nickName;
	private String comment;
	private LocalDateTime writeDate;
	
	public static CommentDto toDto(Comment comment) {
        return new CommentDto(
        	comment.getId(),
        	comment.getUser().getNickname(),
        	comment.getComment(),
        	comment.getWriteDate()
        );
    }
}
