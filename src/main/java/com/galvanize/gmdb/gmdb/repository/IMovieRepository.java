package com.galvanize.gmdb.gmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.galvanize.gmdb.gmdb.model.Movie;

public interface IMovieRepository extends JpaRepository<Movie,Long>{
    
}
