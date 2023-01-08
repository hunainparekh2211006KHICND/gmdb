package com.galvanize.gmdb.gmdb.model;

import java.sql.Date;
// import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviewers")
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joining_date;
    private Long number_of_reviews;

    public Reviewer(){};

    public Reviewer(Long id,String username,Date joining_date,Long number_of_reviews){
        this.id = id;
        this.username = username;
        this.joining_date = joining_date;
        this.number_of_reviews = number_of_reviews;
    }

    public Reviewer(String username){
        this.username = username;
    }
}
