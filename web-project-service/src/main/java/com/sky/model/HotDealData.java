package com.sky.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sky.core.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ThinkPad on 2019/10/19.
 */
@Data
@TableName("hot_deal_data")
public class HotDealData extends BaseModel<HotDealData> {

    /**
     * 周期类型
     */
    @TableField("deal_period")
    private Integer dealPeriod ;

    /**
     * 时间
     */
    @TableField("deal_time")
    private String dealTime ;

    /**
     * 代码
     */
    @TableField("hot_code")
    private String hotCode ;

    /**
     * 开盘价
     */
    @TableField("open_price")
    private BigDecimal openPrice ;

    /**
     * 收盘价
     */
    @TableField("close_price")
    private BigDecimal closePrice ;

    /**
     * 最高价
     */
    @TableField("high_price")
    private BigDecimal highPrice ;

    /**
     * 最低价
     */
    @TableField("low_price")
    private BigDecimal lowPrice ;

    /**
     * 成交量
     */
    @TableField("deal_count")
    private BigDecimal dealCount ;

    /**
     * 成交额
     */
    @TableField("deal_money")
    private BigDecimal dealMoney ;

    /**
     * 振幅
     */
    @TableField("amplitude")
    private String amplitude ;

    /**
     *换手率
     */
    @TableField("hand_rate")
    private BigDecimal handRate ;
}
