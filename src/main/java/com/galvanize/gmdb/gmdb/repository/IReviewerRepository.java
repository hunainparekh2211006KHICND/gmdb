package com.galvanize.gmdb.gmdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galvanize.gmdb.gmdb.model.Reviewer;

public interface IReviewerRepository extends JpaRepository<Reviewer,Long> {
    
}
