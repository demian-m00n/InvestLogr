package com.investlogr.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_no")
    User user;

    @ManyToOne
    @JoinColumn(name = "isin")
    Asset asset;
}
