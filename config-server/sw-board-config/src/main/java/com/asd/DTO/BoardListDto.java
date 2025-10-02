package com.asd.DTO;

import java.time.LocalDateTime;

import com.asd.model.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDto {
	private Long id;
	private String title;
	private LocalDateTime writeDate;
	private String nickName;
	private Long count;
	
	public static BoardListDto toDto(Board board) {
		return new BoardListDto(
            board.getId(),
            board.getTitle(),
            board.getWriteDate(),
            board.getUser().getNickname(),
            board.getCount()
        );
	}
}
