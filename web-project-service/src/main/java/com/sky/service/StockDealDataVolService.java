package com.sky.service;

import com.baomidou.mybatisplus.service.IService;
import com.sky.model.StockDealDataVol;

import java.util.List;

/**
 * Created by ThinkPad on 2020/3/24.
 */
public interface StockDealDataVolService extends IService<StockDealDataVol> {

    List<StockDealDataVol> spiderStockDealDataVol(String stockCode ,String dealDay);
}
