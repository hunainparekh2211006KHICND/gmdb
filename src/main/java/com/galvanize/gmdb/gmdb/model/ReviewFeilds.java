package com.galvanize.gmdb.gmdb.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFeilds {
    private Long movie_id;
    private Long reviewer_id;
    private Review review;

    public ReviewFeilds(){};
    public ReviewFeilds(Long movie_id,Long reviewer_id,Review review){
        this.movie_id = movie_id;
        this.reviewer_id = reviewer_id;
        this.review = review;
    };
}
