package com.rrj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinTable;
import com.rrj.service.CoinTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("coinTable")
public class CoinTableController
{
    private Logger logger = LogManager.getLogger(this.getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CoinTableService coinTableService;

    @GetMapping("query")
    public String query()
    {
        List<CoinTable> coins = new ArrayList<>();

        try {
            coins = coinTableService.queryAllCoinsList();
            System.err.println(mapper.writeValueAsString(coins));
        } catch (Exception e) {
            logger.error("查詢失敗");
        }
        return "";
    }
}
