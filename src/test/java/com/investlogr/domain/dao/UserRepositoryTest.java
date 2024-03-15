package com.investlogr.domain.dao;

import com.investlogr.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void registerUserTest(){
        User user = User.builder()
                .username("admin")
                .password("password")
                .nickname("nickname")
                .role("ROLE_ADMIN")
                .build();

        repository.save(user);
    }

    @Test
    public void findByUsernameTest(){

        User user = repository.findByUsername("admin").orElseThrow();

        assertNotNull(user);

    }

}