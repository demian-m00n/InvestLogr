package com.investlogr.domain.dao;

import com.investlogr.domain.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset,String> {
    public Asset findByIsinCode(String isinCode);
}
