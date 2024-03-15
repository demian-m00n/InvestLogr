package com.investlogr.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.investlogr.domain.entity.Asset;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
public class TradingRecordDTO {

    private String content;

    private Boolean isBuy;

    private Boolean isUSD;

    private Double quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime tradingDate;

    private String isinCode;

    private Double price;
}
