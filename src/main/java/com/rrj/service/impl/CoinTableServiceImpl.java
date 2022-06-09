package com.rrj.service.impl;

import com.rrj.bean.CoinTable;
import com.rrj.repository.CoinTableRepository;
import com.rrj.service.CoinTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CoinTableServiceImpl implements CoinTableService
{
    @Autowired
    private CoinTableRepository coinTableRepository;

    @Override
    public List<CoinTable> queryAllCoinsList()
    {
        Iterable<CoinTable> iterable = coinTableRepository.findAll();
        List<CoinTable> list=new ArrayList<CoinTable>();
        Iterator<CoinTable> iterator = iterable.iterator();
        while(iterator.hasNext()){
            CoinTable next = iterator.next();
            list.add(next);
        }
        return list;
    }
}
