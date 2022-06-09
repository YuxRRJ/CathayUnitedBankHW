package com.rrj.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.bean.CoinInfo;
import com.rrj.bean.CoinInfoRep;
import com.rrj.bean.resp.ResponseWrap;
import com.rrj.config.ServiceConfigs;
import com.rrj.utils.CoinUtil;
import com.rrj.utils.OkHttpClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("coin")
public class CoinController
{
    private Logger logger = LogManager.getLogger(this.getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private OkHttpClientUtil client;

    @Autowired
    private CoinUtil coin;

    @Autowired
    private ServiceConfigs serviceConfigs;


    @GetMapping(value = "coindesk",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCoinDesk()
    {
        String responseStr = client.get(serviceConfigs.getCoinAPI());

        return ResponseEntity.ok(responseStr);
    }
    @GetMapping(value = "info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCoinInfo()
    {
        String coinInfoStr = client.get(serviceConfigs.getCoinAPI());
        HttpStatus status = HttpStatus.ACCEPTED;

        ResponseWrap responseWrap = new ResponseWrap();
        try {
            CoinInfoRep coinInfoRep = coin.parseCoin2Bean(coinInfoStr);

            responseWrap.setContent(coinInfoRep);



        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            responseWrap.setMsg(e.getMessage());
            logger.error(e);
        }

        return new ResponseEntity(responseWrap,status);
    }
}
