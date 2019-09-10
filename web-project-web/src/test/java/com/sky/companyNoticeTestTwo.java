package com.sky;

import com.sky.service.StockCompanyNoticeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ThinkPad on 2019/9/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class companyNoticeTestTwo {

    private static final long  sleep = 300;

    @Autowired
    private StockCompanyNoticeService stockCompanyNoticeService ;

    @Test
    public void processStockCompanyNotice() throws InterruptedException {
        String bigClass = "融资公告";
        String middleClass = "";
        String url ="";
        for(int j = 1 ; j <= 3 ; j++){
            if(j == 1){
                middleClass = "增发";
                for (int i = 1 ; i <= 1000 ; i++) {
                    url = "http://data.eastmoney.com/notices/getdata.ashx?StockCode=&FirstNodeType=2&CodeType=1&PageIndex="+ i +"&PageSize=100&jsObj=ZyMUZLii&SecNodeType=3&TIME=&rt=52248967";
                    boolean just = stockCompanyNoticeService.spiderStockCompanyNotice(url , bigClass , middleClass);
                    Thread.sleep(sleep);

                    if(just){
                        break;
                    }
                }
            }

            if(j == 2){
                middleClass = "新股发行";
                for (int i = 1 ; i <= 1000 ; i++) {
                    url = "http://data.eastmoney.com/notices/getdata.ashx?StockCode=&FirstNodeType=2&CodeType=1&PageIndex="+ i +"&PageSize=100&jsObj=eWwiIBWO&SecNodeType=2&TIME=&rt=52248968";
                    boolean just = stockCompanyNoticeService.spiderStockCompanyNotice(url , bigClass , middleClass);
                    Thread.sleep(sleep);

                    if(just){
                        break;
                    }
                }
            }

            if(j == 3){
                middleClass = "配股";
                for (int i = 1 ; i <= 1000 ; i++) {
                    url = "http://data.eastmoney.com/notices/getdata.ashx?StockCode=&FirstNodeType=2&CodeType=1&PageIndex="+ i +"&PageSize=100&jsObj=YjRTOdkP&SecNodeType=4&TIME=&rt=52248969";
                    boolean just = stockCompanyNoticeService.spiderStockCompanyNotice(url , bigClass , middleClass);
                    Thread.sleep(sleep);
                    if(just){
                        break;
                    }
                }
            }
        }
    }

}