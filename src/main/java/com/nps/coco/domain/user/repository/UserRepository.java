package com.nps.coco.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.study.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
