package com.back.splitmeet.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.PayList;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.src.user.dto.GetReceiptRes;

@Repository
public interface PayListRepository extends JpaRepository<PayList, Long> {
	List<PayList> findALLByUserinfo(UserInfo userinfo);

	//@Query("select COALESCE(sum(r.dogCount),0) from Reservation r where r.companyId = :companyId"
	@Query("select COALESCE(sum(r.personCount),0) from PayList r where r.coBuyPost.idx = :idx")
	Integer sumPersonCount(Long idx);

	Integer countBy();
}
