package com.investlogr.service;

import com.investlogr.domain.dto.TradingRecordDTO;
import com.investlogr.domain.entity.TradingRecord;

import java.util.List;

public interface TradingRecordService {

    TradingRecord createTradingRecord(TradingRecordDTO tradingRecordDTO,String username);
    List<TradingRecordDTO> readAllTradingRecords(String username);
    TradingRecordDTO readTradingRecord(Long id);
    void deleteTradingRecord(Long id);
    TradingRecordDTO updateTradingRecord(Long id,TradingRecordDTO tradingRecordDTO);

    List<TradingRecordDTO> readAllTradingRecordsAboutAsset(String username,String isinCode);

}
