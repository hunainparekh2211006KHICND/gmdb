package com.galvanize.gmdb.gmdb.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFeilds {
    private Long movie_id;
    private Long reviewer_id;
    private Review review;
}
