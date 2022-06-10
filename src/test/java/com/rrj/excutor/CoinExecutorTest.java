package com.rrj.excutor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinInfo;
import com.rrj.bean.CoinInfoResp;
import com.rrj.excutor.CoinExecutor;
import com.rrj.utils.CoinUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CoinExecutorTest
{
    private CoinExecutor coinExecutor = mock(CoinExecutor.class,CALLS_REAL_METHODS);
    private Logger logger = mock(Logger.class);
    private CoinUtil coin = mock(CoinUtil.class);
    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void init() {
        ReflectionTestUtils.setField(coinExecutor,"logger",logger);
        ReflectionTestUtils.setField(coinExecutor,"coin",coin);
        ReflectionTestUtils.setField(coinExecutor,"mapper",mapper);
    }

    @Test
    public void test_parseCoin2Bean() throws Exception {
        /** Arrange */
        String coinStr = "{\"time\":{\"updated\":\"Jun 9, 2022 10:48:00 UTC\"},\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"30,483.7961\",\"description\":\"United States Dollar\",\"rate_float\":30483.7961}}}";
        String timeStr = "2022/06/09 10:48:00";
        String coinType = "USD";
        String chnName = "美金";


        List<CoinInfo> coinInfos = new ArrayList<>();
        CoinInfo coinInfo = new CoinInfo();
        coinInfo.setCoinType(coinType);
        coinInfo.setCoinCHNName(chnName);
        coinInfo.setCoinRate(30483.7961);

        coinInfos.add(coinInfo);

        when(coin.parseTimeFormat(anyString())).thenReturn(timeStr);
        when(coin.parseCoinMap(any(),anySet())).thenReturn(coinInfos);

        /** Action */
        CoinInfoResp coinInfoResp = coinExecutor.parseCoin2Bean(coinStr);

        /** Assert */
        Assert.assertEquals(timeStr,coinInfoResp.getUpdateTime());

        for(CoinInfo actCoinInfo : coinInfoResp.getCoinInfos())
        {
            Assert.assertEquals(coinType,actCoinInfo.getCoinType());
            Assert.assertEquals(chnName,actCoinInfo.getCoinCHNName());
        }

        verify(coin,times(1)).addChineseName(any());
    }

    @Test
    public void test_parseCoin2Bean_Exception() throws Exception {
        /** Arrange */
        String coinStr = "{\"time\":{\"updated\":\"Jun 9, 2022 10:48:00 UTC\"},\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"30,483.7961\",\"description\":\"United States Dollar\",\"rate_float\":30483.7961}}}";

        when(coin.parseTimeFormat(anyString())).thenThrow(ParseException.class);

        /** Action */
        try {
            CoinInfoResp coinInfoResp = coinExecutor.parseCoin2Bean(coinStr);
        }catch (Exception e){
            /** Assert */
            Assert.assertTrue(e.toString().contains("ParseException"));
        }





    }

}
