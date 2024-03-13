package com.investlogr.domain.dao;

import com.investlogr.domain.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset,String> {
    public Optional<Asset> findByIsinCode(String isinCode);
}
