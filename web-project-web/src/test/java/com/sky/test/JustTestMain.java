package com.sky.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sky.core.utils.DateUtils;
import com.sky.core.utils.FileUtils;
import com.sky.core.utils.HttpUtil;
import com.sky.core.utils.SpiderUtils;
import com.sky.model.ForexDealData;
import com.sky.model.ForexDealDataOneMinute;
import com.sky.model.StockCompanyBusinessProfit;
import com.sky.model.StockMoneyFlow;
import org.assertj.core.util.Sets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2019/12/25/025.
 */
public class JustTestMain {

    public static void main(String[] args){
        String stockCode = "603986";
        String market = "SH";
        if (!stockCode.substring(0, 1).equals("6")) {
            market = "SZ";
        }

        String[] publishDays = {"","2019-03-31","2017-12-31","2016-09-30","2015-06-30","2014-03-31","2012-12-31","2011-09-30","2010-06-30","2009-03-31","2007-12-31","2006-09-30","2005-06-30","2004-03-31","2003-06-30"};
        LinkedHashSet<StockCompanyBusinessProfit> set = Sets.newLinkedHashSet();
        for(String publishDay : publishDays){
            String url = "http://f10.eastmoney.com/NewFinanceAnalysis/lrbAjax?companyType=4&reportDateType=0&reportType=2&endDate="+ publishDay +"&code=" + market + stockCode;
            String content = HttpUtil.getHtmlContentByGet(url);
            System.out.println(content);
            if(!content.equals("\"null\"")){
                content = content.replace("\\" , "");
                content = content.substring(1);
                content = content.substring(0,content.length()-1);
                JSONArray jsonArray = JSON.parseArray(content);
                for(int i = 0 ; i < jsonArray.size() ; i ++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String REPORTDATE = jsonObject.getString("REPORTDATE").replace("/" , "-").replace(" 0:00:00","").replace(" ","");//发布时间
                    String OPERATEREVE = jsonObject.getString("OPERATEREVE").replace(" ","");//营业总收入
                    String TOTALOPERATEEXP = jsonObject.getString("TOTALOPERATEEXP").replace(" ","");//营业总成本
                    String OPERATEPROFIT = jsonObject.getString("OPERATEPROFIT").replace(" ","");//营业利润
                    String SUMPROFIT = jsonObject.getString("SUMPROFIT").replace(" ","");//总利润
                    String NETPROFIT = jsonObject.getString("NETPROFIT").replace(" ","");//净利润
                    System.out.println(REPORTDATE + "==============" + OPERATEREVE + "==============" + TOTALOPERATEEXP + "==============" + OPERATEPROFIT + "==============" + SUMPROFIT + "==============" + NETPROFIT);
                    StockCompanyBusinessProfit profit = new StockCompanyBusinessProfit();
                    profit.setStockCode(stockCode);
                    profit.setPublishDay(REPORTDATE);
                    profit.setBusinessIncome(OPERATEREVE);
                    profit.setBusinessCost(TOTALOPERATEEXP);
                    profit.setBusinessProfit(OPERATEPROFIT);
                    profit.setTotalProfit(SUMPROFIT);
                    profit.setPureProfit(NETPROFIT);
                    set.add(profit);
                }
            }else{
                break;
            }
        }
        for(StockCompanyBusinessProfit profit : set){
            System.out.println(profit.toString());
        }
    }

    private static String caculateDay(String dayString){
        return dayString.substring(0,4) + "-" + dayString.substring(4,6) + "-" + dayString.substring(6,8);
    }

    private static String caculateTime(String timeString){
        return timeString.substring(0,2) + ":" + timeString.substring(2,4) + ":" + timeString.substring(4,6);
    }

    public static String getDifferentRoot(String sameRoot){
        Set<String> list = new HashSet<>();
        String[] roots = sameRoot.split(",");
        for(String root: roots){
            boolean just = false ;
            for(String roote : roots){
                if(!root.equals(roote) && ( root.indexOf(roote) != -1 || roote.indexOf(root) !=  -1 )){
                    just = true ;
                    System.out.println(roote);
                    if(root.indexOf(roote) != 0){
                        list.add(root);
                    }
                    if(roote.indexOf(root) != 0){
                        list.add(roote);
                    }
                }
            }
            if(!just){
                list.add(root);
            }
        }
        String str = "";
        for (String root : list) {
            str += root + ",";
        }

        return str;
    }
}
