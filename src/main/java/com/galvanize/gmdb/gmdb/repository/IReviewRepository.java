package com.galvanize.gmdb.gmdb.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.galvanize.gmdb.gmdb.model.Review;
// import com.galvanize.gmdb.gmdb.model.Reviewer;

public interface IReviewRepository extends JpaRepository<Review,Long>{
    public List<Review> findByReviewerId(Long reviewer_id);
}
