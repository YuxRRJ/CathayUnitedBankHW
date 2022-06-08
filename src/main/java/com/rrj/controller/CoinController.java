package com.rrj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("coin")
public class CoinController
{
    private ObjectMapper mapper = new ObjectMapper();
}
