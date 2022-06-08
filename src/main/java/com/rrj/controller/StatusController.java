package com.rrj.controller;

import com.rrj.config.ServiceConfigs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController
{
    @Autowired
    private ServiceConfigs serviceConfigs;

    private Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping("faq")
    public String faq()
    {
        logger.info("Service OK !");
        return "Service OK !";
    }

    @GetMapping("config")
    public String getConfig()
    {
        logger.info("serviceConfigs : "+serviceConfigs.getCoinAPI());
        return serviceConfigs.getCoinAPI();
    }
}
