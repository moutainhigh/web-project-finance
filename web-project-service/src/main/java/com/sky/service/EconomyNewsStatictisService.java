package com.sky.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sky.model.EconomyNewsStatictis;

/**
 * Created by ThinkPad on 2019/5/14.
 */
public interface EconomyNewsStatictisService extends IService<EconomyNewsStatictis> {

    Page<EconomyNewsStatictis> getEconomyNewsStatisticsList(Integer page, Integer size , String name , String type , String startDate , String endDate);
}
