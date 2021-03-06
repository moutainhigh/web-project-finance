package com.sky.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sky.core.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ThinkPad on 2019/10/21.
 */
@Data
@TableName("futures_deal_data")
public class FuturesDealData extends BaseModel<FuturesDealData> {

    /**
     * 周期类型
     */
    @TableField("deal_period")
    private Integer dealPeriod ;

    /**
     * 年
     */
    @TableField("point_year")
    private Integer pointYear ;

    /**
     * 月
     */
    @TableField("point_month")
    private Integer pointMonth ;

    /**
     * 周
     */
    @TableField("point_week")
    private Integer pointWeek ;

    /**
     * 日
     */
    @TableField("point_day")
    private Integer pointDay ;

    /**
     * 时间
     */
    @TableField("deal_time")
    private String dealTime ;

    /**
     * 代码
     */
    @TableField("futures_code")
    private String futuresCode ;

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
}
