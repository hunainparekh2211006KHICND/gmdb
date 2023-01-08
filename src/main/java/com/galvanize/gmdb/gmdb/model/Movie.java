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

    public Movie(){};

    public Movie(Long id,String title,Integer year_released,String genre,Integer run_time){
        this.id = id;
        this.title = title;
        this.year_released = year_released;
        this.genre = genre;
        this.run_time = run_time;
    }

    public Movie(String title,Integer year_released,String genre,Integer run_time){
        this.title = title;
        this.year_released = year_released;
        this.genre = genre;
        this.run_time = run_time;
    }
}
