package com.back.splitmeet.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.GeneralPost;

@Repository
public interface GeneralPostRepository extends JpaRepository<GeneralPost, Long> {
}
