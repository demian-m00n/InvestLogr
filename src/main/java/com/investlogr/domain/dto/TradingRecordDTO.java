package com.investlogr.domain.dto;

import com.investlogr.domain.entity.Asset;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
public class TradingRecordDTO {

    private String content;


    private Boolean isUSD;

    private Double quantity;

    private LocalDateTime tradingDate;

    private String isinCode;

    private Double price;
}
