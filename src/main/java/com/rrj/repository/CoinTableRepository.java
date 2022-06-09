package com.rrj.repository;

import com.rrj.bean.CoinTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoinTableRepository extends CrudRepository<CoinTable, String>
{
    public CoinTable findByCoinType(String coinType);

}
