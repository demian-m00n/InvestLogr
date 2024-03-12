package com.investlogr.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "ASTS")
public class Asset {

    @Id
    private String isinCode;

    private String symbol;

    private String title;
}
