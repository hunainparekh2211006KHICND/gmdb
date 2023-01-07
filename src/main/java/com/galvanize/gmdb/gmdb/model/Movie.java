package com.galvanize.gmdb.gmdb.model;

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
}
