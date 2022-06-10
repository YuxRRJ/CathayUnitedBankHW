package com.rrj.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinInfo;
import com.rrj.bean.CoinInfoResp;
import com.rrj.bean.CoinTable;
import com.rrj.service.CoinTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CoinUtil
{
    private ObjectMapper mapper = new ObjectMapper();
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CoinTableService coinTableService;

    public List<CoinInfo> parseCoinMap(JsonNode node , Set<String> keySet) throws JsonProcessingException {
        List<CoinInfo> coinInfos = new ArrayList<>();

        for(String coinType : keySet)
        {
            Map coinMap = mapper.convertValue(node.get(coinType),Map.class);
            CoinInfo coinInfo = new CoinInfo();

            double rate = (double) coinMap.get("rate_float");

            coinInfo.setCoinType(coinType);
            coinInfo.setCoinRate(rate);

            coinInfos.add(coinInfo);
        }

        return coinInfos;
    }

    public String parseTimeFormat(String dateTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date = dateFormat.parse(dateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return sdf.format(calendar.getTime());
    }

    public void addChineseName(List<CoinInfo> coinInfos)
    {
        for(CoinInfo coinInfo : coinInfos)
        {
            String coinType = coinInfo.getCoinType();
            CoinTable coinTable = coinTableService.queryByCoinType(coinType);
            coinInfo.setCoinCHNName(coinTable.getChineseName());
        }
    }
}
