package com.asd.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Camping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String district;
	
	private double latitude;
	private double longitude;
	
	@Column(name = "write_date", updatable = false, insertable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime writeDate;
	
	private String address;
	
	private String phonenumber;
	
	private String homepage;
	
	@OneToMany(mappedBy="camping", cascade = CascadeType.ALL)
	private List<CampingReview> campingReviews;
	
	@OneToMany(mappedBy="camping", cascade = CascadeType.ALL)
	private List<CampingBookmark> campingBookmarks;
	
	public void addCampingReview(CampingReview campingReview) {
		campingReviews.add(campingReview);
		campingReview.setCamping(this);
	}
	
	public void addCampingBookmark(CampingBookmark campingBookmark) {
		campingBookmarks.add(campingBookmark);
		campingBookmark.setCamping(this);
	}
}
