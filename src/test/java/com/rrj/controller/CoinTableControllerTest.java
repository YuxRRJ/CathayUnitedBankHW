package com.rrj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinTable;
import com.rrj.bean.resp.ResponseWrap;
import com.rrj.controller.CoinTableController;
import com.rrj.service.CoinTableService;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CoinTableControllerTest
{
    private CoinTableController coinTableController = mock(CoinTableController.class,CALLS_REAL_METHODS);
    private Logger logger = mock(Logger.class);
    private CoinTableService coinTableService = mock(CoinTableService.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init(){
        ReflectionTestUtils.setField(coinTableController,"logger",logger);
        ReflectionTestUtils.setField(coinTableController,"coinTableService",coinTableService);
        ReflectionTestUtils.setField(coinTableController,"mapper",mapper);
    }

    @Test
    public void test_query(){
        /** Arrange */
        String coinType = "NTD";
        String chnName = "新台幣";
        String id = "c04";

        List<CoinTable> coins = new ArrayList<>();
        CoinTable coinTable = new CoinTable();
        coinTable.setCoinType(coinType);
        coinTable.setChineseName(chnName);
        coinTable.setId(id);

        coins.add(coinTable);

        when(coinTableService.queryAllCoinsList()).thenReturn(coins);

        /** Action */
        ResponseEntity response = coinTableController.query();

        /** Assert */
        Assert.assertEquals(200,response.getStatusCodeValue());

        ResponseWrap actualResp = mapper.convertValue(response.getBody(),ResponseWrap.class);

        //ResponseWrap
        Assert.assertNull(actualResp.getMsg());

        //CoinTable
        List objects = mapper.convertValue(actualResp.getContent(),List.class);

        for(Object object : objects)
        {
            CoinTable actCoinTable = mapper.convertValue(object,CoinTable.class);

            Assert.assertEquals(coinType,actCoinTable.getCoinType());
            Assert.assertEquals(chnName,actCoinTable.getChineseName());
            Assert.assertEquals(id,actCoinTable.getId());

        }
    }

    @Test
    public void test_insert(){
        /** Arrange */

        /** Action */
        ResponseEntity response = coinTableController.insert(any());

        /** Assert */
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void test_update(){
        /** Arrange */

        /** Action */
        ResponseEntity response = coinTableController.update(any());

        /** Assert */
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Test
    public void test_delete(){
        /** Arrange */

        /** Action */
        ResponseEntity response = coinTableController.delete(any());

        /** Assert */
        Assert.assertEquals(200,response.getStatusCodeValue());
    }


}
