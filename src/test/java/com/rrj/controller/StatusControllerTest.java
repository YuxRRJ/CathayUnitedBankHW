package com.rrj.controller;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

public class StatusControllerTest
{
    private StatusController statusController = mock(StatusController.class,CALLS_REAL_METHODS);
    private Logger logger = mock(Logger.class);

    @Test
    public void test_faq()
    {
        /** Arrange */
        ReflectionTestUtils .setField(statusController,"logger",logger);

        /** Action */
        String result = statusController.faq();

        /** Assert */
        Assert.assertEquals("Service OK !",result);
        verify(logger,times(1)).info(anyString());
    }
}
/** Arrange */
/** Action */
/** Assert */
