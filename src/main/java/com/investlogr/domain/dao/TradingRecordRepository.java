package com.investlogr.domain.dao;

import com.investlogr.domain.entity.Asset;
import com.investlogr.domain.entity.TradingRecord;
import com.investlogr.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingRecordRepository extends JpaRepository<TradingRecord,Long> {

    List<TradingRecord> findTradingRecordsByUser(User user);

    List<TradingRecord> findTradingRecordsByUserAndAsset(User user, Asset asset);

}
