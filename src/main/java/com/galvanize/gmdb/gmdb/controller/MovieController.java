package com.galvanize.gmdb.gmdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id){
        return this.movieRepo.findById(id).orElse(null);
    }

    @PostMapping("")
    public void saveMovie(@RequestBody Movie movie)
    {
        this.movieRepo.save(movie);
    }

    @PutMapping("")
    public void updateMovie(@RequestBody Movie movie)
    {
        this.movieRepo.save(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id)
    {
        this.movieRepo.deleteById(id);
    }
}
