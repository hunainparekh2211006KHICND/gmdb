package com.galvanize.gmdb.gmdb.model;

import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String review_text;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date last_modified_date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="movie_id",referencedColumnName="id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="reviewer_id",referencedColumnName="id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Reviewer reviewer;

    public Review(){};

    public Review(Long id,String review_text,Date last_modified_date,Movie movie,Reviewer reviewer){
        this.id = id;
        this.review_text = review_text;
        this.last_modified_date = last_modified_date;
        this.movie = movie;
        this.reviewer = reviewer;
    }

    public Review(String review_text){
        this.review_text = review_text;
    }

    public Review(Long id,String review_text){
        this.review_text = review_text;
        this.id = id;
    }
}
