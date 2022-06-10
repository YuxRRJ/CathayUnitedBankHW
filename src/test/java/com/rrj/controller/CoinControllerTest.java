package com.rrj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinInfo;
import com.rrj.bean.CoinInfoResp;
import com.rrj.bean.resp.ResponseWrap;
import com.rrj.config.ServiceConfigs;
import com.rrj.excutor.CoinExecutor;
import com.rrj.utils.OkHttpClientUtil;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CoinControllerTest
{
    private CoinController coinController = mock(CoinController.class,CALLS_REAL_METHODS);
    private Logger logger = mock(Logger.class);
    private OkHttpClientUtil client = mock(OkHttpClientUtil.class);
    private CoinExecutor coin = mock(CoinExecutor.class);
    private ServiceConfigs serviceConfigs = mock(ServiceConfigs.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init()
    {
        ReflectionTestUtils.setField(coinController,"logger",logger);
        ReflectionTestUtils.setField(coinController,"client",client);
        ReflectionTestUtils.setField(coinController,"coin",coin);
        ReflectionTestUtils.setField(coinController,"serviceConfigs",serviceConfigs);
    }

    @Test
    public void test_getCoinDesk() {
        /** Arrange */
        String fakeData = "success";
        when(client.get(any())).thenReturn(fakeData);

        /** Action */
        ResponseEntity response = coinController.getCoinDesk();

        /** Assert */
        Assert.assertEquals(200,response.getStatusCode().value());
        Assert.assertEquals(fakeData,response.getBody());

    }

    @Test
    public void test_getCoinInfo(){
        /** Arrange */
        String fakeData = "success";
        String updateTime = "2022/06/09 11:11:11";
        String coinType = "NTD";
        String chnName = "新台幣";
        double coinRate = 123.45;

        CoinInfoResp coinInfoResp = new CoinInfoResp();
        List<CoinInfo> coinInfos = new ArrayList<>();
        CoinInfo coinInfo = new CoinInfo();
        coinInfo.setCoinType(coinType);
        coinInfo.setCoinCHNName(chnName);
        coinInfo.setCoinRate(coinRate);

        coinInfos.add(coinInfo);

        coinInfoResp.setUpdateTime(updateTime);
        coinInfoResp.setCoinInfos(coinInfos);

        when(client.get(any())).thenReturn(fakeData);
        when(coin.parseCoin2Bean(anyString())).thenReturn(coinInfoResp);

        /** Action */
        ResponseEntity response = coinController.getCoinInfo();

        /** Assert */
        Assert.assertEquals(200,response.getStatusCode().value());

        ResponseWrap actualResp = mapper.convertValue(response.getBody(),ResponseWrap.class);

        //ResponseWrap
        Assert.assertNull(actualResp.getMsg());

        //CoinInfoResp
        CoinInfoResp actualCoinInfoResp = mapper.convertValue(actualResp.getContent(),CoinInfoResp.class);
        Assert.assertEquals(updateTime,actualCoinInfoResp.getUpdateTime());

        for(CoinInfo actualCoinInfo : actualCoinInfoResp.getCoinInfos())
        {
            Assert.assertEquals(coinType,actualCoinInfo.getCoinType());
            Assert.assertEquals(chnName,actualCoinInfo.getCoinCHNName());
        }
    }
    
}
