package com.sky.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sky.core.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ThinkPad on 2019/5/27.
 */
@Data
@TableName("stock_company_product")
public class StockCompanyProduct extends BaseModel<StockCompanyProduct> {

    /**
     * 上市代码
     */
    @TableField("stock_code")
    private String stockCode ;

    /**
     * 数据发布时间
     */
    @TableField("publish_date")
    private String publishDate ;

    /**
     * 经营分类1—行业分类;2—产品分类;3-地域分类
     */
    @TableField("pruduct_type")
    private String pruductType ;

    /**
     * 类型名称
     */
    @TableField("product_name")
    private String productName ;

    /**
     * 主营收入
     */
    @TableField("product_revenue")
    private String productRevenue ;

    /**
     * 主营收入率
     */
    @TableField("product_revenue_rate")
    private String productRevenueRate ;

    /**
     * 主营成本
     */
    @TableField("product_cost")
    private String productCost ;

    /**
     * 主营成本率
     */
    @TableField("product_cost_rate")
    private String productCostRate ;

    /**
     * 主营利润
     */
    @TableField("product_profit")
    private String productProfit ;

    /**
     * 主营利润率
     */
    @TableField("product_profit_rate")
    private String productProfitRate ;

    /**
     * 毛利率
     */
    @TableField("product_gross_rate")
    private String productGrossRate ;

}
