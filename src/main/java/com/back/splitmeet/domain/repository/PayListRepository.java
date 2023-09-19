package com.back.splitmeet.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.PayList;
import com.back.splitmeet.domain.UserInfo;
import com.back.splitmeet.src.user.dto.GetReceiptRes;

@Repository
public interface PayListRepository extends JpaRepository<PayList, Long> {
	List<GetReceiptRes> findALLByUserinfo(UserInfo userinfo);
}
