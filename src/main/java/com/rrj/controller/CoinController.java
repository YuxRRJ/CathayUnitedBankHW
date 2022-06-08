package com.rrj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrj.utils.OkHttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coin")
public class CoinController
{
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private OkHttpClientUtil client;

    @Autowired
    private ServiceConfigs serviceConfigs;

    @GetMapping(value = "coindesk",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCoinDesk()
    {
        String ResponseStr = client.get();
    }
}
