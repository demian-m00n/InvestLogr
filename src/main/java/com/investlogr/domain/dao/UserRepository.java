package com.investlogr.domain.dao;

import com.investlogr.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    Boolean existsByUsername(String username);

    User findByUsername(String username);
}
