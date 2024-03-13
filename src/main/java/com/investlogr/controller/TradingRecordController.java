package com.investlogr.controller;

import com.investlogr.domain.dto.TradingRecordDTO;
import com.investlogr.domain.entity.TradingRecord;
import com.investlogr.service.TradingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TradingRecordController {

    private final TradingRecordService tradingRecordService;

    @PostMapping(value = "/tradingrecords",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long register(@RequestBody TradingRecordDTO dto, Authentication authentication){
        TradingRecord tradingRecord = tradingRecordService.createTradingRecord(dto,authentication.getName());

        return tradingRecord.getTradingRecordId();
    }

    @GetMapping(value = "/tradingrecords/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public TradingRecordDTO read(@PathVariable(name = "id") Long id){
        return tradingRecordService.readTradingRecord(id);
    }

    @DeleteMapping("/tradingrecords/{id}")
    public void remove(@PathVariable(name = "id") Long id){
        tradingRecordService.deleteTradingRecord(id);
    }

    @PutMapping("/tradingrecords/{id}")
    public void modify(@PathVariable(name = "id")Long id, TradingRecordDTO dto){
        tradingRecordService.updateTradingRecord(id, dto);
    }

    @GetMapping(value = "/tradingrecords",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TradingRecordDTO> list(@RequestParam(defaultValue = "ALL") String isinCode,
                                       Authentication authentication){
        String username = authentication.getName();
        if(isinCode.equals("ALL")) return tradingRecordService.readAllTradingRecords(username);
        return tradingRecordService.readAllTradingRecordsAboutAsset(username,isinCode);
    }

}
