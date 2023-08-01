package com.back.splitmeet.repository.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.back.splitmeet.domain.user.userInfo;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class userInfoRepository {

	private final EntityManager em;

	public void save(userInfo userinfo) {
		em.persist(userinfo);
	}

	public userInfo findOne(Long userId) {
		return em.find(userInfo.class, userId);
	}

	public List<userInfo> findAll() {
		return em.createQuery("select m from userInfo m", userInfo.class)
			.getResultList();

	}

	public List<userInfo> findByEmail(String userEmail) {
		return em.createQuery("select m from userInfo m where m.userEmail = :userEmail", userInfo.class)
			.setParameter("userEmail", userEmail)
			.getResultList();
	}
}
