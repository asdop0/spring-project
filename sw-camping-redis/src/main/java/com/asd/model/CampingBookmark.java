package com.asd.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CAMPING")
public class CampingBookmark extends Bookmark{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Camping_id")
    private Camping camping;
}
