package com.galvanize.gmdb.gmdb.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.gmdb.gmdb.model.Reviewer;
import com.galvanize.gmdb.gmdb.repository.IReviewerRepository;

@RestController
@RequestMapping("/reviewers")
public class ReviewerController {

    @Autowired
    private IReviewerRepository reviewerRepo;
    
    @GetMapping("")
    public List<Reviewer> getAllReviewers(){
        return this.reviewerRepo.findAll();
    }

    @GetMapping("/{id}")
    public Reviewer getReviewerById(@PathVariable Long id){
        return this.reviewerRepo.findById(id).orElse(null);
    }

    @PostMapping("")
    public void saveReviewer(@RequestBody Reviewer reviewer)
    {
        reviewer.setNumber_of_reviews(0L);
        Date joining_date = Date.valueOf(LocalDate.now());
        reviewer.setJoining_date(joining_date);
        this.reviewerRepo.save(reviewer);
    }

    @DeleteMapping("/{id}")
    public void deleteReviewer(@PathVariable Long id){
        this.reviewerRepo.deleteById(id);
    }
}
