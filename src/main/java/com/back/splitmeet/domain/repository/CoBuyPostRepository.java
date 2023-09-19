package com.back.splitmeet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.CoBuyPost;

@Repository
public interface CoBuyPostRepository extends JpaRepository<CoBuyPost, Long> {
}
