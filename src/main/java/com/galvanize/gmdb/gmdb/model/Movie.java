package com.galvanize.gmdb.gmdb.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="movies")
@Getter
@Setter
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer year_released;
    private String genre;
    private Integer run_time;

    @OneToMany(mappedBy="movie",targetEntity=Review.class,cascade = CascadeType.ALL)
    private List<Review> reviews;
}
