package com.investlogr.service;


import com.investlogr.domain.dao.AssetRepository;
import com.investlogr.domain.dao.TradingRecordRepository;
import com.investlogr.domain.dao.UserRepository;
import com.investlogr.domain.dto.TradingRecordDTO;
import com.investlogr.domain.entity.Asset;
import com.investlogr.domain.entity.TradingRecord;
import com.investlogr.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradingRecordServiceImpl implements TradingRecordService{

    private final TradingRecordRepository tradingRecordRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    @Override
    public TradingRecord createTradingRecord(TradingRecordDTO tradingRecordDTO,String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Asset asset = assetRepository.findByIsinCode(tradingRecordDTO.getIsinCode()).orElseThrow();

        TradingRecord tradingRecord = TradingRecord.builder()
                .price(tradingRecordDTO.getPrice())
                .content(tradingRecordDTO.getContent())
                .isBuy(tradingRecordDTO.getIsBuy())
                .isUSD(tradingRecordDTO.getIsUSD())
                .tradingDate(tradingRecordDTO.getTradingDate())
                .quantity(tradingRecordDTO.getQuantity())
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .asset(asset)
                .user(user)
                .build();

        return tradingRecordRepository.save(tradingRecord);
    }

    @Override
    public List<TradingRecordDTO> readAllTradingRecords(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return tradingRecordRepository.findTradingRecordsByUser(user).stream().map((r) ->
             TradingRecordDTO.builder()
                .isinCode(r.getAsset().getIsinCode())
                .content(r.getContent())
                .isBuy(r.getIsBuy())
                .isUSD(r.getIsUSD())
                .price(r.getPrice())
                .quantity(r.getQuantity())
                .tradingDate(r.getTradingDate())
                .build()).collect(Collectors.toList());
    }

    @Override
    public TradingRecordDTO readTradingRecord(Long id) {
        TradingRecord tradingRecord = tradingRecordRepository.findById(id).orElseThrow();

        return TradingRecordDTO.builder()
                .isinCode(tradingRecord.getAsset().getIsinCode())
                .content(tradingRecord.getContent())
                .isBuy(tradingRecord.getIsBuy())
                .isUSD(tradingRecord.getIsUSD())
                .price(tradingRecord.getPrice())
                .quantity(tradingRecord.getQuantity())
                .tradingDate(tradingRecord.getTradingDate())
                .build();
    }

    @Override
    public void deleteTradingRecord(Long id) {
        tradingRecordRepository.deleteById(id);
    }

    @Override
    public TradingRecordDTO updateTradingRecord(Long id, TradingRecordDTO tradingRecordDTO) {
        TradingRecord tradingRecord = tradingRecordRepository.findById(id).orElseThrow();

        TradingRecord updatedTradingRecord = TradingRecord.builder()
                .tradingRecordId(id)
                .price(tradingRecordDTO.getPrice())
                .content(tradingRecordDTO.getContent())
                .isUSD(tradingRecordDTO.getIsUSD())
                .isBuy(tradingRecordDTO.getIsBuy())
                .tradingDate(tradingRecordDTO.getTradingDate())
                .quantity(tradingRecordDTO.getQuantity())
                .regDate(tradingRecord.getRegDate())
                .modDate(LocalDateTime.now())
                .user(tradingRecord.getUser())
                .asset(tradingRecord.getAsset())
                .build();

        tradingRecordRepository.save(updatedTradingRecord);

        return tradingRecordDTO;
    }

    @Override
    public List<TradingRecordDTO> readAllTradingRecordsAboutAsset(String username, String isinCode) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Asset asset = assetRepository.findByIsinCode(isinCode).orElseThrow();
        return tradingRecordRepository.findTradingRecordsByUserAndAsset(user,asset).stream().map((r) ->
                TradingRecordDTO.builder()
                        .isinCode(r.getAsset().getIsinCode())
                        .content(r.getContent())
                        .isBuy(r.getIsBuy())
                        .isUSD(r.getIsUSD())
                        .price(r.getPrice())
                        .quantity(r.getQuantity())
                        .tradingDate(r.getTradingDate())
                        .build()).collect(Collectors.toList());
    }
}
