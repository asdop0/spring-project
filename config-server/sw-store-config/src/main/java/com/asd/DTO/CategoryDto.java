package com.asd.DTO;

import com.asd.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	private Long id;
    private String name;
    
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
        		category.getId(),
        		category.getName()
        );
    }
}
