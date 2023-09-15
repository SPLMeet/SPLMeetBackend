package com.back.splitmeet.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.CoBuyPostImg;

@Repository
public interface CoBuyPostImgRepository extends JpaRepository<CoBuyPostImg, Long> {
	CoBuyPostImg findTopByLocalId(Long localId);

	List<CoBuyPostImg> findAllByLocalId(Long localId);
}
