package com.galvanize.gmdb.gmdb.controller;

import java.sql.Date;
import java.time.LocalDate;
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
import com.galvanize.gmdb.gmdb.model.Review;
import com.galvanize.gmdb.gmdb.model.ReviewFeilds;
import com.galvanize.gmdb.gmdb.model.Reviewer;
import com.galvanize.gmdb.gmdb.repository.IMovieRepository;
import com.galvanize.gmdb.gmdb.repository.IReviewRepository;
import com.galvanize.gmdb.gmdb.repository.IReviewerRepository;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    
    @Autowired
    private IMovieRepository movieRepo;
    @Autowired
    private IReviewerRepository reviewerRepo;
    @Autowired
    private IReviewRepository reviewRepo;

    @GetMapping("")
    public List<Review> getAllReviews(){
        return this.reviewRepo.findAll();
    }

    @PostMapping("")
    public void saveReview(@RequestBody ReviewFeilds reviewFeilds)
    {
        Movie movie = movieRepo.findById(reviewFeilds.getMovie_id()).orElse(null);
        Reviewer reviewer = reviewerRepo.findById(reviewFeilds.getReviewer_id()).orElse(null);
        reviewer.setNumber_of_reviews(reviewer.getNumber_of_reviews()+1);
        this.reviewerRepo.save(reviewer);
        Date last_date = Date.valueOf(LocalDate.now());
        reviewFeilds.getReview().setLast_modified_date(last_date);
        reviewFeilds.getReview().setMovie(movie);
        reviewFeilds.getReview().setReviewer(reviewer);
        this.reviewRepo.save(reviewFeilds.getReview());
    }

    @PutMapping("")
    public void updateReview(@RequestBody ReviewFeilds reviewFeilds)
    {
        Movie movie = movieRepo.findById(reviewFeilds.getMovie_id()).orElse(null);
        Reviewer reviewer = reviewerRepo.findById(reviewFeilds.getReviewer_id()).orElse(null);
        Date last_date = Date.valueOf(LocalDate.now());
        reviewFeilds.getReview().setLast_modified_date(last_date);
        reviewFeilds.getReview().setMovie(movie);
        reviewFeilds.getReview().setReviewer(reviewer);
        this.reviewRepo.save(reviewFeilds.getReview());
    }

    @DeleteMapping("/{reviewer_id}/{review_id}")
    public void deleteReview(@PathVariable Long reviewer_id,@PathVariable Long review_id){
        List<Review> reviews=  this.reviewRepo.findByReviewerId(reviewer_id);
        Review review= reviews.stream().filter(i-> i.getId() == review_id).findFirst().orElse(null);
        Reviewer reviewer = review.getReviewer();
        reviewer.setNumber_of_reviews(reviewer.getNumber_of_reviews()-1);
        this.reviewerRepo.save(reviewer);
        this.reviewRepo.delete(review);
    }
}
