package com.investlogr.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USRS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;

    private String username;

    private String password;

    private String role;

    private String email;

    private String name;

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }
}
