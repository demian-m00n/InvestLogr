package com.investlogr.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TRD_RCDS")
public class TradingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradingRecordId;

    @Lob
    private String content;

    private LocalDateTime tradingDate;

    private Boolean isUSD;

    private Boolean isBuy;

    private Double price;

    private Double quantity;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    @ManyToOne
    @JoinColumn(name = "isin")
    private Asset asset;


    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

}
