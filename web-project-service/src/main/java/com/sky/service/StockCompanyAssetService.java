package com.sky.service;

import com.baomidou.mybatisplus.service.IService;
import com.sky.model.StockCompanyAsset;
import com.sky.vo.StockCompanyAssetVO;

import java.util.List;

/**
 * Created by ThinkPad on 2019/10/22.
 */
public interface StockCompanyAssetService extends IService<StockCompanyAsset> {

    boolean spiderStockCompanyAsset(String stockCode ,Integer page);

    List<StockCompanyAssetVO> getCompanyAssetGrowList(String stockCode);
}
