package com.galvanize.gmdb.gmdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.gmdb.gmdb.model.Movie;
import com.galvanize.gmdb.gmdb.repository.IMovieRepository;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private IMovieRepository movieRepo;
    
    @GetMapping("")
    public List<Movie> getAllMovies()
    {
        return movieRepo.findAll();
    }
}
