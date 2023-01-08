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
}
