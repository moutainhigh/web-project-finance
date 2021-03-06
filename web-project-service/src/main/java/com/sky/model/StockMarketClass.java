package com.sky.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sky.core.model.BaseModel;
import lombok.Data;

/**
 * Created by ThinkPad on 2019/5/29.
 */
@Data
@TableName("stock_market_class")
public class StockMarketClass extends BaseModel<StockMarketClass> {

    /**
     *一级分类
     */
    @TableField("first_class")
    private String firstClass ;

    /**
     *二级分类
     */
    @TableField("second_class")
    private String secondClass ;

    /**
     *三级分类
     */
    @TableField("third_class")
    private String thirdClass ;

    /**
     *类型编码
     */
    @TableField("class_code")
    private String classCode ;

    /**
     *类型名称
     */
    @TableField("class_name")
    private String className ;

    /**
     *大类名称:行业，地域，题材
     */
    @TableField("class_type")
    private String classType ;

}
