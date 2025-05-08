package com.asd.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.asd.model.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime writeDate;
	private String userName;
	private Long count;
	private List<CommentDto> comments;
	
	public static BoardDetailDto toDto(Board board) {
        List<CommentDto> commentDtos = board.getComments().stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
        
        return new BoardDetailDto(
            board.getId(),
            board.getTitle(),
            board.getContent(),
            board.getWriteDate(),
            board.getUser().getName(),
            board.getCount(),
            commentDtos
        );
    }
}
