package com.rrj.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinInfo;
import com.rrj.bean.CoinInfoRep;
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

    public CoinInfoRep parseCoin2Bean(String coinStr)
    {
        CoinInfoRep coinInfoRep = new CoinInfoRep();

        try {
            JsonNode jsonNode = mapper.readTree(coinStr);
            logger.info(jsonNode);

            String originUpdateTime = jsonNode.get("time").get("updated").asText();
            String updateTime = parseTimeFormat(originUpdateTime);

            Map bpiMap = mapper.convertValue(jsonNode.get("bpi"),Map.class);
            Set<String> coinTypes = bpiMap.keySet();

            List<CoinInfo> coinInfos = parseCoinMap(jsonNode.get("bpi"),coinTypes);
            addChineseName(coinInfos);

            coinInfoRep.setUpdateTime(updateTime);
            coinInfoRep.setCoinInfos(coinInfos);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return coinInfoRep;
    }

    private List<CoinInfo> parseCoinMap(JsonNode node , Set<String> keySet) throws JsonProcessingException {
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

    private String parseTimeFormat(String dateTime) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date = dateFormat.parse(dateTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return sdf.format(calendar.getTime());
    }

    private void addChineseName(List<CoinInfo> coinInfos)
    {
        for(CoinInfo coinInfo : coinInfos)
        {
            String coinType = coinInfo.getCoinType();
            CoinTable coinTable = coinTableService.queryByCoinType(coinType);
            coinInfo.setCoinCHNName(coinTable.getChineseName());
        }
    }
}
