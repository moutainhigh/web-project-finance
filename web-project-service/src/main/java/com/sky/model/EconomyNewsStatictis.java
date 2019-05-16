package com.sky.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sky.core.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * Created by ThinkPad on 2019/5/14.
 */
@TableName("economy_news_statictis")
public class EconomyNewsStatictis extends BaseModel<EconomyNewsStatictis> {

    /**
     * ID
     */
    @TableField("news_code")
    private String newsCode ;

    /**
     * 标题
     */
    @TableField("news_title")
    private String newsTitle ;

    /**
     * 时间
     */
    @TableField("news_time")
    private Date newsTime ;

    /**
     * 区域
     */
    @TableField("news_region")
    private Integer newsRegion ;

    /**
     * 国家
     */
    @TableField("news_contry")
    private String newsContry ;

    /**
     * 类型
     */
    @TableField("news_type")
    private Integer newsType ;

    /**
     * 关键词
     */
    @TableField("news_key_word")
    private String newsKeyWord ;

    /**
     * 内容
     */
    @TableField("news_content")
    private String newsContent ;


    @TableField(exist = false)
    List<EconomyNewsInfluence> influencelist ;

    public List<EconomyNewsInfluence> getInfluencelist() {
        return influencelist;
    }

    public void setInfluencelist(List<EconomyNewsInfluence> influencelist) {
        this.influencelist = influencelist;
    }

    public String getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(String newsCode) {
        this.newsCode = newsCode;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public Integer getNewsRegion() {
        return newsRegion;
    }

    public void setNewsRegion(Integer newsRegion) {
        this.newsRegion = newsRegion;
    }

    public String getNewsContry() {
        return newsContry;
    }

    public void setNewsContry(String newsContry) {
        this.newsContry = newsContry;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public String getNewsKeyWord() {
        return newsKeyWord;
    }

    public void setNewsKeyWord(String newsKeyWord) {
        this.newsKeyWord = newsKeyWord;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}