package com.sky.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sky.core.utils.CommonHttpUtil;
import com.sky.core.utils.SpiderUtils;
import com.sky.core.utils.Tools;
import com.sky.model.StockCompanyBase;
import com.sky.model.StockCompanyValueCompare;
import com.sky.service.StockCompanyBaseService;
import com.sky.service.StockCompanyValueCompareSerivce;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by ThinkPad on 2019/9/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticTest {

    @Test
    public void test(){
        String url = "http://push2.eastmoney.com/api/qt/stock/get?ut=fa5fd1943c7b386f172d6893dbfba10b&invt=2&fltt=2&fields=f43,f57,f58,f169,f170,f46,f44,f51,f168,f47,f164,f116,f60,f45,f52,f50,f48,f167,f117,f71,f161,f49,f530,f135,f136,f137,f138,f139,f141,f142,f144,f145,f147,f148,f140,f143,f146,f149,f55,f62,f162,f92,f173,f104,f105,f84,f85,f183,f184,f185,f186,f187,f188,f189,f190,f191,f192,f107,f111,f86,f177,f78,f110,f262,f263,f264,f267,f268,f250,f251,f252,f253,f254,f255,f256,f257,f258,f266,f269,f270,f271,f273,f274,f275,f127,f199,f128,f193,f196,f194,f195,f197,f80,f280,f281,f282,f284,f285,f286,f287&secid=0.002616&cb=jQuery183029528189124867765_1570337713424&_=1570337713781";
        String resultJson = CommonHttpUtil.sendGet(url);

        resultJson = resultJson.substring(resultJson.indexOf("(")  + 1 ,resultJson.indexOf(")"));
        JSONObject jsonObject = JSON.parseObject(resultJson);
        JSONObject dataJson = jsonObject.getJSONObject("data");
        BigDecimal totalValue = dataJson.getBigDecimal("f116");
        BigDecimal flowValue = dataJson.getBigDecimal("f117");
        BigDecimal currentPrice = dataJson.getBigDecimal("f43");
        BigDecimal totalCount = dataJson.getBigDecimal("f84");
        BigDecimal flowCount = dataJson.getBigDecimal("f85");



        String url2 = "http://push2.eastmoney.com/api/qt/slist/get?spt=1&np=3&fltt=2&invt=2&fields=f9,f12,f13,f14,f20,f23,f37,f45,f49,f134,f135,f129,f1000,f2000,f3000&ut=bd1d9ddb04089700cf9c27f6f7426281&cb=jQuery18303250630669859258_1570336648576&secid=0.002616&_=1570336648910";
        String resultJson2 = CommonHttpUtil.sendGet(url2);
        resultJson2 = resultJson2.substring(resultJson2.indexOf("(")  + 1 ,resultJson2.indexOf(")"));
        JSONArray jsonArray = JSON.parseObject(resultJson2).getJSONObject("data").getJSONArray("diff");
        System.out.println(jsonArray.toString());
        BigDecimal totalAsset = BigDecimal.ZERO ;
        BigDecimal pureAsset = BigDecimal.ZERO ;
        BigDecimal pureIntrest = BigDecimal.ZERO ;
        BigDecimal marketIntrestRate = BigDecimal.ZERO ;
        BigDecimal marktePureRate = BigDecimal.ZERO ;
        BigDecimal pocessIntrestRate = BigDecimal.ZERO ;
        BigDecimal pureIntrestRate = BigDecimal.ZERO ;
        BigDecimal roe = BigDecimal.ZERO ;

        BigDecimal mtotalAsset = BigDecimal.ZERO ;
        BigDecimal mpureAsset = BigDecimal.ZERO ;
        BigDecimal mpureIntrest = BigDecimal.ZERO ;
        BigDecimal mmarketIntrestRate = BigDecimal.ZERO ;
        BigDecimal mmarktePureRate = BigDecimal.ZERO ;
        BigDecimal mpocessIntrestRate = BigDecimal.ZERO ;
        BigDecimal mpureIntrestRate = BigDecimal.ZERO ;
        BigDecimal mroe = BigDecimal.ZERO ;

        BigDecimal ototalAsset = BigDecimal.ZERO ;
        BigDecimal opureAsset = BigDecimal.ZERO ;
        BigDecimal opureIntrest = BigDecimal.ZERO ;
        BigDecimal omarketIntrestRate = BigDecimal.ZERO ;
        BigDecimal omarktePureRate = BigDecimal.ZERO ;
        BigDecimal opocessIntrestRate = BigDecimal.ZERO ;
        BigDecimal opureIntrestRate = BigDecimal.ZERO ;
        BigDecimal oroe = BigDecimal.ZERO ;


        String stockCode = "";
        String stockName = "";
        String sectorCode = "";
        String sectorName = "";
        BigDecimal orderCount = BigDecimal.ZERO;
        for(int i = 0 ; i < jsonArray.size() ; i++){
            if(i == 0){
              JSONObject comJson = jsonArray.getJSONObject(i);
                stockCode = comJson.getString("f12");
                stockName = comJson.getString("f14");

               totalAsset = comJson.getBigDecimal("f20");
               pureAsset = comJson.getBigDecimal("f135");
               pureIntrest = comJson.getBigDecimal("f45");
               marketIntrestRate = comJson.getBigDecimal("f9");
               marktePureRate = comJson.getBigDecimal("f23");
               pocessIntrestRate = comJson.getBigDecimal("f49");
               pureIntrestRate = comJson.getBigDecimal("f129");
               roe = comJson.getBigDecimal("f37");

                ototalAsset = comJson.getBigDecimal("f1020");
                opureAsset = comJson.getBigDecimal("f1135");
                opureIntrest = comJson.getBigDecimal("f1045");
                omarketIntrestRate = comJson.getBigDecimal("f1009");
                omarktePureRate = comJson.getBigDecimal("f1023");
                opocessIntrestRate = comJson.getBigDecimal("f1049");
                opureIntrestRate = comJson.getBigDecimal("f1129");
                oroe = comJson.getBigDecimal("f1037");
            }

            if(i == 1){
                JSONObject comJson = jsonArray.getJSONObject(i);
                sectorCode = comJson.getString("f12");
                sectorName = comJson.getString("f14");
                orderCount = comJson.getBigDecimal("f134");

                mtotalAsset = comJson.getBigDecimal("f2020");
                mpureAsset = comJson.getBigDecimal("f2135");
                mpureIntrest = comJson.getBigDecimal("f2045");
                mmarketIntrestRate = comJson.getBigDecimal("f2009");
                mmarktePureRate = comJson.getBigDecimal("f2023");
                mpocessIntrestRate = comJson.getBigDecimal("f2049");
                mpureIntrestRate = comJson.getBigDecimal("f2129");
                mroe = comJson.getBigDecimal("f2037");

            }
        }

        String url3 = "http://f10.eastmoney.com/IndustryAnalysis/IndustryAnalysisAjax?code=SZ002616&icode=456";
        String resultJson3 = CommonHttpUtil.sendGet(url3);
        JSONObject jsonObject9 = JSONObject.parseObject(resultJson3);
        JSONArray czxbj = jsonObject9.getJSONObject("czxbj").getJSONArray("data");
        JSONArray gzbj = jsonObject9.getJSONObject("gzbj").getJSONArray("data");
        JSONArray dbfxbj = jsonObject9.getJSONObject("dbfxbj").getJSONArray("data");
        JSONArray gsgmzsz = jsonObject9.getJSONArray("gsgmzsz");

        BigDecimal czxOrder = BigDecimal.ZERO ;
        BigDecimal mgsyGrowthRate = BigDecimal.ZERO ;
        BigDecimal yysrGrowthRate = BigDecimal.ZERO ;
        BigDecimal jlrGrowthRate = BigDecimal.ZERO ;

        BigDecimal vmgsyGrowthRate = BigDecimal.ZERO ;
        BigDecimal vyysrGrowthRate = BigDecimal.ZERO ;
        BigDecimal vjlrGrowthRate = BigDecimal.ZERO ;

        BigDecimal mmgsyGrowthRate = BigDecimal.ZERO ;
        BigDecimal myysrGrowthRate = BigDecimal.ZERO ;
        BigDecimal mjlrGrowthRate = BigDecimal.ZERO ;
        for(int i = 0 ; i < czxbj.size() ; i ++){
            JSONObject jsonObject5 = czxbj.getJSONObject(i);
            if(i == 0){
                czxOrder = jsonObject5.getBigDecimal("pm");
                mgsyGrowthRate = jsonObject5.getBigDecimal("jbmgsyzzlfh");
                yysrGrowthRate = jsonObject5.getBigDecimal("yysrzzlfh");
                jlrGrowthRate = jsonObject5.getBigDecimal("jlrzzlfh");
            }

            if(i == 1){
                vmgsyGrowthRate = jsonObject5.getBigDecimal("jbmgsyzzlfh");
                vyysrGrowthRate = jsonObject5.getBigDecimal("yysrzzlfh");
                vjlrGrowthRate = jsonObject5.getBigDecimal("jlrzzlfh");
            }

            if(i == 2){
                mmgsyGrowthRate = jsonObject5.getBigDecimal("jbmgsyzzlfh");
                myysrGrowthRate = jsonObject5.getBigDecimal("yysrzzlfh");
                mjlrGrowthRate = jsonObject5.getBigDecimal("jlrzzlfh");
            }
        }


        BigDecimal gzOrder = BigDecimal.ZERO ;
        BigDecimal gzPEG = BigDecimal.ZERO ;
        BigDecimal VgzPEG = BigDecimal.ZERO ;
        BigDecimal MgzPEG = BigDecimal.ZERO ;
        for(int i = 0 ; i < gzbj.size() ; i ++){
            JSONObject jsonObject5 = gzbj.getJSONObject(i);
            if(i == 0){
                gzOrder = jsonObject5.getBigDecimal("pm");
                gzPEG = jsonObject5.getBigDecimal("peg");
            }

            if(i == 1){
                VgzPEG = jsonObject5.getBigDecimal("peg");
            }
            if(i == 2){
                MgzPEG = jsonObject5.getBigDecimal("peg");
            }
        }


        BigDecimal dbOrder = BigDecimal.ZERO ;
        BigDecimal dbROE = BigDecimal.ZERO ;
        BigDecimal dbJLR = BigDecimal.ZERO ;
        BigDecimal dbZCZZL = BigDecimal.ZERO ;
        BigDecimal dbQYCS = BigDecimal.ZERO ;

        BigDecimal VdbROE = BigDecimal.ZERO ;
        BigDecimal VdbJLR = BigDecimal.ZERO ;
        BigDecimal VdbZCZZL = BigDecimal.ZERO ;
        BigDecimal VdbQYCS = BigDecimal.ZERO ;

        BigDecimal MdbROE = BigDecimal.ZERO ;
        BigDecimal MdbJLR = BigDecimal.ZERO ;
        BigDecimal MdbZCZZL = BigDecimal.ZERO ;
        BigDecimal MdbQYCS = BigDecimal.ZERO ;

        for(int i = 0 ; i < dbfxbj.size() ; i ++){
            JSONObject jsonObject5 = dbfxbj.getJSONObject(i);
            if(i == 0){
                dbOrder = jsonObject5.getBigDecimal("pm");
                dbROE = jsonObject5.getBigDecimal("roepj");
                dbJLR = jsonObject5.getBigDecimal("jllpj");
                dbZCZZL = jsonObject5.getBigDecimal("zzczzlpj");
                dbQYCS = jsonObject5.getBigDecimal("qycspj");
            }

            if(i == 1){
                VdbROE = jsonObject5.getBigDecimal("roepj");
                VdbJLR = jsonObject5.getBigDecimal("jllpj");
                VdbZCZZL = jsonObject5.getBigDecimal("zzczzlpj");
                VdbQYCS = jsonObject5.getBigDecimal("qycspj");
            }

            if(i == 2){
                MdbROE = jsonObject5.getBigDecimal("roepj");
                MdbJLR = jsonObject5.getBigDecimal("jllpj");
                MdbZCZZL = jsonObject5.getBigDecimal("zzczzlpj");
                MdbQYCS = jsonObject5.getBigDecimal("qycspj");
            }
        }

        BigDecimal ltOrder = BigDecimal.ZERO ;
        String zsz = "" ;
        String ltsz = "" ;
        String yysr = "" ;
        String jlr = "" ;

        String Vzsz = "" ;
        String Vltsz = "" ;
        String Vyysr = "" ;
        String Vjlr = "" ;

        String Mzsz = "" ;
        String Mltsz = "" ;
        String Myysr = "" ;
        String Mjlr = "" ;

        for(int i = 0 ; i < gsgmzsz.size() ; i ++){
            JSONObject jsonObject5 = gsgmzsz.getJSONObject(i);
            if(i == 0){
                ltOrder = jsonObject5.getBigDecimal("pm");
                zsz = jsonObject5.getString("zsz");
                ltsz = jsonObject5.getString("ltsz");
                yysr = jsonObject5.getString("yysr");
                jlr = jsonObject5.getString("jlr");
            }

            if(i == 1){
                Vzsz = jsonObject5.getString("zsz");
                Vltsz = jsonObject5.getString("ltsz");
                Vyysr = jsonObject5.getString("yysr");
                Vjlr = jsonObject5.getString("jlr");
            }

            if(i == 2){
                Mzsz = jsonObject5.getString("zsz");
                Mltsz = jsonObject5.getString("ltsz");
                Myysr = jsonObject5.getString("yysr");
                Mjlr = jsonObject5.getString("jlr");
            }

        }

        String url4 = "http://baike.eastmoney.com/item/%E5%B9%BF%E4%B8%9C%E9%95%BF%E9%9D%92(%E9%9B%86%E5%9B%A2)%E8%82%A1%E4%BB%BD%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8";
        Document doc = SpiderUtils.HtmlJsoupGet(url4);
        Elements elements = doc.getElementsByClass("company_intro");
        String business = elements.get(0).html();
        System.out.println();
    }

    @Autowired
    private StockCompanyValueCompareSerivce stockCompanyValueCompareSerivce ;

    @Autowired
    private StockCompanyBaseService stockCompanyBaseService ;

    @Test
    public void test2(){
        List<StockCompanyBase> list = stockCompanyBaseService.selectList(null);
//        List<StockCompanyValueCompare> compareList = stockCompanyValueCompareSerivce.selectList(null);
        for(StockCompanyBase companyBase : list){
            String market = "SH";
            String marketNum = "1";
            if(companyBase.getStockPlate().indexOf("深交所") != -1){
                market = "SZ";
                marketNum = "0";
            }
            if(StringUtils.isNotBlank(companyBase.getStockACode())){
//                boolean just = false ;
//                for(StockCompanyValueCompare compare : compareList){
//                       if(companyBase.getStockACode().equals(compare.getStockCode())){
//                           just = true ;
//                           break;
//                       }
//                }
//                if(!just){
                    StockCompanyValueCompare valueCompare = stockCompanyValueCompareSerivce.spiderStockCompanyValueCompare("2019" ,market , marketNum ,companyBase.getStockACode() ,companyBase.getCompanyName());
                    System.out.println(valueCompare.toString());
                    StockCompanyValueCompare companyValueCompare = stockCompanyValueCompareSerivce.selectOne(new EntityWrapper<StockCompanyValueCompare>().where("stock_code = {0}" , valueCompare.getStockCode()));
                    if(companyValueCompare == null){
                        stockCompanyValueCompareSerivce.insert(valueCompare);
                    }else{
                        valueCompare.setId(companyValueCompare.getId());
                        stockCompanyValueCompareSerivce.updateById(valueCompare);
                    }
//                }

            }

        }

    }


    @Test
    public void test222222() throws InterruptedException, ExecutionException {

        // 开始时间
        long start = System.currentTimeMillis();
        List<StockCompanyBase> list = stockCompanyBaseService.selectList(null);
        // 每500条数据开启一条线程
        int threadSize = 500;
        // 总数据条数
        int dataSize = list.size();
        // 线程数
        int threadNum = dataSize / threadSize + 1;
        // 定义标记,过滤threadNum为整数
        boolean special = dataSize % threadSize == 0;

        // 创建一个线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 定义一个任务集合
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
        Callable<Integer> task = null;
        List<StockCompanyBase> cutList = null;

        // 确定每条线程的数据
        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                if (special) {
                    break;
                }
                cutList = list.subList(threadSize * i, dataSize);
            } else {
                cutList = list.subList(threadSize * i, threadSize * (i + 1));
            }
            // System.out.println("第" + (i + 1) + "组：" + cutList.toString());
            final List<StockCompanyBase> listStr = cutList;
            task = new Callable<Integer>() {

                @Override
                public Integer call() throws Exception {
                    for(StockCompanyBase companyBase : listStr){
                        String market = "SH";
                        String marketNum = "1";
                        if(companyBase.getStockPlate().indexOf("深交所") != -1){
                            market = "SZ";
                            marketNum = "0";
                        }

                        if(StringUtils.isNotBlank(companyBase.getStockACode())) {

                            StockCompanyValueCompare valueCompare = stockCompanyValueCompareSerivce.spiderStockCompanyValueCompare("2019" ,market , marketNum ,companyBase.getStockACode() ,companyBase.getCompanyName());
                            System.out.println(valueCompare.toString());
                            StockCompanyValueCompare companyValueCompare = stockCompanyValueCompareSerivce.selectOne(new EntityWrapper<StockCompanyValueCompare>().where("stock_code = {0}" , valueCompare.getStockCode()));
                            if(companyValueCompare == null){
                                stockCompanyValueCompareSerivce.insert(valueCompare);
                            }else{
                                valueCompare.setId(companyValueCompare.getId());
                                stockCompanyValueCompareSerivce.updateById(valueCompare);
                            }

                            Thread.sleep(1000);
                        }
                    }
                    return 1;
                }
            };
            // 这里提交的任务容器列表和返回的Future列表存在顺序对应的关系
            tasks.add(task);
        }

        List<Future<Integer>> results = exec.invokeAll(tasks);

        for (Future<Integer> future : results) {
            System.out.println(future.get());
        }

        // 关闭线程池
        exec.shutdown();
        System.out.println("线程任务执行结束");
        System.err.println("执行任务消耗了 ：" + (System.currentTimeMillis() - start) + "毫秒");
    }


/**
 * 问题：
 * 1、每月方向
 * 2、每月涨幅
 * 3、每月最高值
 * 4、每月最高值时间——第几周、第几天
 * 5、每月最低值
 * 6、每月最低值时间——第几周、第几天
 *
 * 7、每周每天的方向
 * 8、每周每天的涨幅
 * 9.每周每天的最高值
 * 10.每周每天的成交量
 * 11、每周每天的最高值出现的时间——几点
 * 12、每周每天的最低值
 * 13、每周每天的最低值出现时间
 *
 *14、每天哪个时间短的涨幅最大
 * 15、每天哪个时间短的成交量最大
 * 16、开盘前半个小时对后市的影响
 * 17、收盘前半个小时对后市的影响
 * 18、每个时间段对后市的影响
 * 19、每个时间段封涨停对后市的影响
 *
 * 20、每天大额成交量都集中在哪个时间段
 *
 *
 *
 *方向比率
 * 涨幅
 * 振幅
 *
 * 涨幅最高第几周
 * 涨幅最高那几天
 * 振幅最高第几周
 * 振幅最高那几天
 *
 *
 *1、哪个时间区间的成家量大
 * 2、哪个时间区间的价格波动大
 * 3、哪个时间区间涨停对后市影响大
 * 4、每个时间区间价格的变动对后市的影响
 *
 *
 * 哪个行业和市场的相关性高
 * 哪个企业与行业相关性高
 * 哪个
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/**
 * 重大事件——①重大合同
 *         ——②投资相关
 *         ——③股权激励
 *
 * 财务报告——①定期报告
 *         ——②利润分配
 *         ——③业绩报告
 *         ——④业绩快报
 *
 * 融资公告——①新股发行
 *         ——②增发
 *         ——③配股
 *
 * 资产重组——①要约收购
 *         ——②吸收合并
 *         ——③回购
 *
 * 信息变动
 *
 * 持股变动
 *
 * 风险提示
 *
 *
 *
 *龙虎榜——从中可以看到龙虎榜单中的股票在哪个证券营业部的成交量较大。该数据有助于了解当日异动个股的资金进出情况，判断是游资所为还是机构所为
 *      ——条件——①日价格涨幅偏离值±7%
 *              ——②日价格振幅达到15%
 *              ——③日换手率达到20%
 *              ——④连续三个交易日内，涨幅偏离值累计达到20%
 *              ——⑤每个条件都选前3名的上榜，深市是分主板、中小板、创业板分别取前3的
 *
 *
 *
 *
 *
 *
 *
 */

/**
 * 归纳起来有六个方面——K线形态
 *                   ——均线
 *                   ——技术指标
 *                   ——成交量
 *                   ——热点
 *                   ——主力成本
 *
 * 股市技术分析的四大要素——价
 *                       ——量
 *                       ——时
 *                       ——空
 *
 *
 *大多数单一利用股票的收盘价、开盘价、最高价或最低价而发明出的技术指标
 */

/**
 * MACD——异同移动平均线——进行主体的判断
 *     ——快线与慢线的差离值
 *     ——通过对数据的变化衡量主体的释放效果
 *     ——原理——如果没有任何事件刺激、长周期与短周期的数据集应该保持不变并始终一致
 *             ——在事件的不断刺激下，短周期的数据集机会产生不断的波动，长时间看来就会不断偏离长周期数据集
 *             ——在不断事件的刺激下，短周期和长周期的最终都会进行方向选择，刺激因素越大，偏离值就会越大，最终当主体中的所有话题释放完全后
 *                 即没有重大事件刺激时，小事件对数据的刺激作用比较小，最终短周期的数据集会趋于稳定，最终与长周期的数据集再次保持平衡
 * DKJ——随机指标
 *    ——周期内出现过的最高价、最低价及最后一个计算周期的收盘价及这三者之间的比例关系
 *    ——判断超买超卖
 *    ——融合了移动平均线速度上的观念
 *    ——同时也融合了动量观念、强弱指标和移动平均线的一些优点
 *    ——利用价格波动的真实波幅来反映价格走势的强弱和超买超卖现象
 *    ——在价格尚未上升或下降之前发出买卖信号的一种技术工具
 *    ——短期波动剧烈或者瞬间行情幅度太大时，投机性太强的个股KD值容易高位钝化或低位钝化
 *    ——对于交易量太小的个股不是很适用
 *    ——对于绩优股，准确率却是很高
 *    ——受到基本面、政策面及市场活跃程度的影响时，在任何强势市场中，超买超卖状态都可能存在相当长的一段时期，趋势逆转不一定即刻发生
 *    ——当股价交叉突破支撑压力线时，若此时K、D线又在超买区或超卖区相交，KD线提供的股票买卖信号就更为有效。而且，在此位上K、D来回交叉越多越好。
 *    ——当K值和D值上升或下跌的速度减弱，倾斜度趋于平缓是短期转势的预警信号。这种情况对于大盘热门股及股价指数的准确性较高。而对冷门股或小盘股的准确性较低。
 *    ——K线与D线的交叉突破在80以上或20以下时较为准确。当这种交叉突破在50左右发生时，表明市场走势陷入盘局，正在寻找突破方向。此时，K线与D线的交叉突破所提供的买卖信号无效
 *    ——原理——运用数据集中最大值、最小值衡量了数据集分布的临界范围
 *            ——运用当前数据与数据集中最小值的关系，判定数据所处数据集的位置
 *            ——理想状态下、任何数据都不会超过该数据集的临界值、都会在其范围内进行运动
 *            ——最终的目的是找到阻力位和支撑位
 *            ——所以——数据特征波动剧烈的数据不适用该方法——波动过大、数据总是超过该范围，那么临界值就失去了意义
 *                    ——数据特征波动太小的数据不适用该方法——波动太小、数据趋于稳定，稍小的变动就会让数据达到临界值
 *                    ——表现理想的数据集最适合该种指标——绩优股最适合
 * RSI——相对强弱指标
 *    ——根据一定时期内上涨和下跌幅度之和的比率制作出的一种技术曲线
 *    ——反映出市场在一定时期内的景气程度
 *    ——计测市场供需关系和买卖力道的方法及指标
 *    ——向上的力量与向下的力量进行比较，若向上的力量较大，则计算出来的指标上升；若向下的力量较大，则指标下降，由此测算出市场走势的强弱
 *    ——RSI =N日内收盘涨幅的平均值/(N日内收盘涨幅均值+N日内收盘跌幅均值) ×100
 *    ——参数较小的短期RSI曲线如果位于参数较大的长期RSI曲线之上,则目前行情属多头市场
 *    ——由于参数较大的RSI计算的时间范围较大,因而结论会更可靠。但同均线系统一样无法回避反应较慢的缺点,这是 在使用过程中要加以注意的
 *    ——不同的参数,其区域的划分就不同。一般而言,参数越大,分界线离中心线50就越近,离100和0就越远
 *    ——RSI值如果超过50,表明市场进入强市,可以考虑买入,但是如果继续进入"极强"区,就要考虑物极必反,准备卖出了
 *    ——以一特定时期内股价的变动情况来推测价格未来的变动方向
 *    ——计算一定时间内股价涨幅与跌幅之比，测量价格内部的体质强弱，根据择强汰弱原理选择出强势股
 *    ——RSI指标只能是从某一个角度观察市场后给出的一个信号,所能给投资者提供的只是一个辅助的参考,并不意味着市场趋势就一定向RSI指标预示的方向发展
 *    ——指标的时间参数不同,其给出的结果就会不同
 *    ——较短周期的RSI指标虽然比较敏感,但快速震荡的次数较多,可靠性较差；较长周期的RSI指标尽管信号可靠,但指标的敏感性不够,反应迟缓,因而经常出现错过买卖良机的现象
 *    ——RSI是通过收盘价计算的,如果当天行情的波幅很大,上下影线较长时,RSI就不可能较为准确反映此时行情的变化
 *    ——超买、超卖出现后导致的指标钝化现象容易发出错误的操作信号
 *    ——在"牛市"和"熊市"的中间阶段,RSI值升至90以上或降到10以下的情况时有发生,此时指标钝化后会出现模糊的误导信息,若依照该指标操作可能会出现失误,错过盈利机会或较早进入市场而被套牢
 *    ——RSI指标与股价的"背离"走势常常会发生滞后现象
 *    ——一方面,市场行情已经出现反转,但是该指标的"背离"信号却可能滞后出现；另一方面,在各种随机因素的影响下,有时"背离"现象出现数次后行情才真正开始反转,同时在研判指标"背离"现象时,真正反转所对应的"背离"出现次数并无定论,一次、两次或三次背离都有出现趋势变化的可能,在实际操作中较难确认
 *    ——当RSI值在50附近波动时该指标往往失去参考价值
 *    ——一般而言,RSI值在40到60之间研判的作用并不大。按照RSI的应用原则,当RSI从50以下向上突破50分界线时代表股价已转强；RSI从50以上向下跌破50分界线则代表股价已转弱。但实际情况经常是让投资者一头雾水,股价由强转弱后却不跌,由弱转强后却不涨的现象相当普遍。这是因为在常态下,RSI会在大盘或个股方向不明朗而盘整时,率先整理完毕并出现走强或走弱的现象
 *    ——原理——运用周期内的均值与上涨幅度+下跌幅度的均值之比判断双方力量
 *            ——选用幅度、是因为无论拉高还想出货，均值都能体现
 *            ——出货的次数多，跌值都会比升值大
 *
 * BOLL——价格的标准差及其信赖区间——路径指标——支撑压力类指标
 *     ——确定股价的波动范围及未来走势
 *     ——BOLL指标却与价格的形态和趋势有着密不可分的联系
 *     ——股价在上轨线以上或在下轨线以下只反映该股股价相对较高或较低，
 *     ——平均线与阻力线(或支撑线)构成的上行(楚游)通道对于把握股价的中长期走势有着强烈的指示作用
 *     ——%b主要是表示当前价格在布林线中的位置，它是进行交易决策时的关键性指标
 *     ——在盘整的时候会失去作用或产生偏差
 *     ——观察布林线指标开口的大小，来参考卖出和买入
 *     ——开口逐渐变小，多空双方力量趋于一致，股价将会选择方向突破，而且开口越小，股价突破的力度就越大
 *     ——布林线指标只告诉我们这些股票随时会突破，但却没有告诉我们股价突破的方向，最佳的买入时机是在股价放量向上突破，布林线指标开口扩大后
 *     ——布林线指标本身没有提供明确的卖出讯号，但可以利用股价跌破布林线股价平均线作为卖出讯号
 *     ——作用——布林线可以指示支撑和压力位置
 *             ——布林线可以显示超买、超卖
 *             ——布林线可以指示趋势
 *             ——布林线具备通道功能
 *     ——给出的是一个指数或股价波动的上轨和下轨的区间,同时通过一条中线来配合对趋势进行判断
 *     ——重点在于波动带的变动和指数或股价对波动带的穿越
 *     ——对于波段操作的指导性较强
 *     ——原理——利用数据集的均值、标准差判断数据的分布临界区间
 *             ——在没有任何刺激性事件的情况下、数据集始终分布在该区间内
 *             ——如果商品需求相对较好、数据集往往会在均线以上分布、如果需求不足、数据在均线下分布
 *             ——理想状态是数据集呈现一条直线、但在一些事件的刺激下、数据集会向上、向下运动，此时整个数据集呈现相同趋势
 *             ——在下跌、横盘后、数据向上运动触及上轨后下降、触及均线后又上升、说明看涨情绪已经有所提高、但不能确定后市是否上升
 *             ——只有在数据集持续在均线上方运动，且突破上轨后才能确定事件刺激后方向已经形成
 *             ——喇叭口的大小说明的数据集的临界区间、临界区间越小、稳定性越强
 *             ——上升时区间小说明、稳定上升、耐力较好
 *             ——横向运动区间越来越小、价格分歧越来越小、选择方向时、逆向操作较少、合力的情况下价格变动较大
 * WR——威廉超买超卖指数——Williams Overbought/Oversold Index——摆动类指标
 *   ——利用“最后一周期”之最高价、最低价、收市价，计算当日收盘价所处“最后一周期”(过去一定时间，比如7天等)内的价格区间之相对百分位置
 *   ——依“当日收盘价”的摆动点，以兼具超买超卖和强弱分界的指标，其度量市场处于超买还是超卖状态
 *   ——主要作用在于辅助其他指标确认短期买卖信号
 *   ——当WR高于80，即处于超卖状态，行情即将见底，应当考虑买进
 *   ——当WR低于20，即处于超买状态，行情即将见顶，应当考虑卖出
 *   ——为了既能改进威廉指标的缺陷，但又不失W&R指标的原有特性，最佳的方案是设置三条W&R指标线。这三条线的参数分别设置为13，34，89
 *   ——反映过于灵敏，指标波动频率过快，引起信号频发现象，误差率也非常高，过多的信号和严重的误差率造成投资者不敢轻易使用它
 *   ——过于侧重于短线，经过仔细研究，发现并非是指标设计上的错误，只是仅仅在参数设置上过于偏重于短线，时间参数设置普遍采用一些随意性和不科学的参数,如5、10、20等，也有的设置成6、12等，都未认真考虑市场本身的运行规律
 *   ——过短的时间参数是造成指标过于侧重于短线和极度敏感的重要原因。但是，随意延长时间参数设置的周期，又会使W&R指标丧失原有的应用价值
 *   ——原理——通过数据临界值判断当前数据所处数据的具体位置
 *           ——找到阻力位和支撑位
 * OBV——能量潮——On Balance Volume
 *    ——通过统计成交量变动的趋势来推测股价趋势
 *    ——从价格的变动及成交量的增减关系，推测市场气氛
 *    ——主要理论基础是市场价格的变化必须有成交量的配合，股价的波动与成交量的扩大或萎缩有密切的关连
 *    ——通常股价上升所需的成交量总是较大；下跌时，则成交量可能放大，也可能较小。价格升降而成交量不相应升降，则市场价格的变动难以为继
 *    ——理论成立的依据——投资者对股价的评论越不一致，成交量越大；反之，成交量就小。因此，可用成交量来判断市场的人气和多空双方的力量
 *                      ——重力原理。上升的物体迟早会下跌，而物体上升所需的能量比下跌时多
 *                      ——惯性原则——动则恒动、静则恒静。只有那些被投资者或主力相中的热门股会在很大一段时间内成交量和股价的波动都比较大，而无人问津的冷门股，则会在一段时间内，成交量和股价波幅都比较小
 *    ——由于OBV的计算方法过于简单化，所以容易受到偶然因素的影响，为了提高OBV的准确性，可以采取多空比率净额法对其进行修正
 *    ——多空比率净额= [（收盘价－最低价）－（最高价-收盘价）] ÷（ 最高价－最低价）×V
 *    ——应用——当股价上升而OBV线下降，表示买盘无力，股价可能会回跌
 *            ——股价下降时而OBV线上升，表示买盘旺盛，逢低接手强股，股价可能会止跌回升
 *            ——OBV线缓慢上升，表示买气逐渐加强，为买进信号
 *            ——OBV线急速上升时，表示力量将用尽为卖出信号
 *            ——OBV线从正的累积数转为负数时，为下跌趋势，应该卖出持有股票。反之，OBV线从负的累积数转为正数时，应该买进股票
 *            ——OBV线最大的用处，在于观察股市盘局整理后，何时会脱离盘局以及突破后的未来走势，OBV线变动方向是重要参考指数，其具体的数值并无实际意义
 *            ——
 *    ——OBV线系依据成交量的变化统计绘制而成，因此OBV线属于技术性分析，与属于经济性的基本分析无关
 *    ——OBV线为股市短期波动的重要判断方法，但运用OBV线应配合股价趋势予以研判分析
 *    ——BV线能帮助确定股市突破盘局后的发展方向
 *    ——OBV的走势，可以局部显示出市场内部主要资金的移动方向，显示当期不寻常的超额成交量是徘徊于低价位还是在高价位上产生，可使技术分析者领先一步深入了解市场内部原因
 *    ——OBV线适用范围比较偏向于短期进出，与基本分析丝毫无关。同时OBV也不能有效反效反映当期市场的转手情况
 *    ——是建立在国外成熟市场上的经验总结。把它移植到国内必然要经过一番改造才行。比如价涨量增，用在内地股市坐庄的股票上就不灵了，这时股价涨得越高成交量反而越少。这是因为主力控盘较重，股价在上涨过程中没有获利筹码加以兑现，所以此时股票会涨得很“疯”，但成交量并不增加，OBV自然就无法发挥作用
 *    ——涨跌停板的股票也会导致指标失真
 *
 * BIAS——乖离率、偏离率
 *     ——通过计算市场指数或收盘价与某条移动平均线之间的差距百分比，以反映一定时期内价格与其MA偏离程度的指标
 *     ——从而得出价格在剧烈波动时因偏离移动平均趋势而造成回档或反弹的可能性，以及价格在正常波动范围内移动而形成继续原有势的可信度
 *     ——用百分比来表示价格与MA间的偏离程度
 *     ——收盘价与某条移动平均价格之间的差距
 *     ——第一，对于风险不同的股票应区别对待。有业绩保证且估值水平合理的个股，在下跌情况下乖离率通常较低时就开始反弹。这是由于持有人心态稳定不愿低价抛售，同时空仓投资者担心错过时机而及时买入的结果。反之，对绩差股而言，其乖离率通常在跌至绝对值较大时，才开始反弹。
 *     ——第二，要考虑流通市值的影响。流通市值较大的股票，不容易被操纵，走势符合一般的市场规律，适宜用乖离率进行分析。而流通市值较小的个股或庄股由于容易被控盘，因此在使用该指标时应谨慎。
 *     ——第三，在股价的低位密集成交区，由于筹码分散，运用乖离率指导操作时成功率较高，而在股价经过大幅攀升后，在机构的操纵下容易暴涨暴跌，此时成功率则相对较低
 *     ——乖离率=[(当日收盘价-N日平均价)/N日平均价]*100%
 *     ——N，一般5、6、10、12、24、30和72。在实际运用中，短线使用6日乖离率较为有效，中线则放大为10日或12日
 *     ——当乖离率由负值变为正值的过程中，如果移动平均线也向上移动，可以跟进做多；当乖离率由正值变为负值的过程中，如果移动平均线向上移动，可以持金待售，如果移动平均线向下移动，则要卖出金价
 *     ——当乖离率接近历史最大值时，预示着多方发威已接近极限，行情随时都可能向下，投资者应减仓，而不能盲目追高。当乖离率接近历史最小值时，预示着空方发威接近极限，行情随时都可能掉头向上，投资者不应再割肉出局而应逢低吸纳
 *     ——股价与三十日平均线乖离率达+16%以上为超买现象，是卖出时机；当其达-16%以下为超卖现象，为买入时机。
 *     ——股价与各种平均线的乖离容易偏高，但发生次数并不多。
 *     ——正的乖离率愈大，表示短期获利愈大，则获利回吐可能性愈高；负的乖离率愈大，则空头回补的可能性也愈高。
 *     ——股价与十日平均线乖离率达+8%以上为超买现象，是卖出时机;当其达-8%以下时为超卖现象，为买入时机
 *     ——暴涨与暴跌，会使乖离达到意想不到的百分比，但出现次数极少，时间也短，可视为一特例
 *     ——缺陷是买卖信号过于频繁，因此要与随机指标(KDJ)、布林线指标（BOLL）搭配使用
 *     ——5日乖离率波动常态为正负10
 *     ——从市场经验看，10日平均移动线作为基期效果较好。以下跌为例，10日乖离率通常在-7%至-8%时开始反弹。考虑到这个数值的安全系数不高，一般情况下，更为安全的进场时机应选择在-10%至-11%之间。同样出于稳健，短线在上涨时可以在+8%附近卖出
 *     ——5日乖离率波动的常态范围在正负2之间
 *     ——在持续上升行情中，5日、10日乖离率均在0轴位之上，且10日乖离率在5日乖离率之上，5日乖离率接近或略超过正2时，指数将横盘或回调整理。整理过程中，5日乖离率调头向下，在接近或略低于0轴位时，即反弹向上，说明上升趋势没有改变。
 *     ——在上升行情中，当5日乖离率超过正5，可视为转势信号，此时应密切注视指数变化；当5日乖离率与指数出现顶背离时，可视为顶部信号，此时应尽快离场观望
 *     ——在下跌行情中，5日乖离率超过负6，将有反弹发生。在反弹过程中，5日10日乖离率均在0轴位之下，且5日乖离率在10日乖离率之上，当5日乖离率向上超过正2时，反弹将结束
 *     ——下跌行情中5日乖离率大大超过负的常态范围，且指数与5日乖离率出现底背离时，既指数创出新低而5日乖离率却走出一波比一波高的形态，说明大盘触底成功，可适时介入，见好就收。
 *     ——乖离率指标非常适合与两种技术指标进行组合运用，一种是随机指标KDJ，另一种是布林线指标BOLL
 *     ——KD指标和BIAS指标可以使得反弹行情中的操作变得及时准确
 *     ——BIAS指标的功用是确认股价是否超跌，而KD指标的作用是显示个股是否有拐头向上的动能
 *         ——将BIAS指标的参数设置为24日，将KD指标的参数设置为9；3；3。
 *         —— BIAS指标要小于-6，这只是确认该股超跌的初选条件
 *         ——KD指标产生黄金交叉，K线上穿D线。
 *         ——KD交叉同时，KD指标中的D值要小于16。
 *     ——乖离率指标与布林线指标的结合运用适合在超跌反弹行情中的买入：对于这类反弹行情，投资者不宜采用追涨，而要结合技术分析方法，运用BIAS和布林线指标的组合分析，把握个股进出时机
 *         ——当BIAS的三条短期均线全部小于0时
 *         ——股价也已经触及BOLL的下轨线LB
 *         ——布林线正处于不断收敛状态中的
 *         ——BIAS的短期均线上穿长期均线，并且成交量逐渐放大的
 * BRAR——人气意愿指标——能量指标
 *     ——有效地提供辨认高价圈及低价圈
 *     ——AR指标是通过比较一段周期内的开盘价在该周期价格中的高低。从而反映市场买卖人气的技术指标
 *     ——N日AR=(N日内（H－O）之和除以N日内（O－L）之和)*100
 *     ——H为当日最高价，L为当日最低价，O为当日开盘价，N为设定的时间参数，一般原始参数日设定为26日
 *     ——BR指标是通过比较一段周期内的收盘价在该周期价格波动中的地位，来反映市场买卖意愿程度的技术指标
 *     ——N日BR=N日内（H－CY）之和除以N日内（CY－L）之和*100
 *     ——H为当日最高价，L为当日最低价，CY为前一交易日的收盘价，N为设定的时间参数，一般原始参数日设定为26日
 *     ——灵活运用该指标，却能够抓住局部底部，特别适合做反弹
 *     ——BR<AR，且BR<100，可考虑逢低买进
 *     ——AR和BR同时急速上升，意味着股价已近顶部，持股者应逢高卖出
 *     ——BR急速上升，而AR处在盘整或小跌时，应逢高卖出
 *     ——BR从高峰回跌，跌幅达1至2倍时，若AR无警戒讯号出现，应逢低买进
 *     ——原理——AR > BR——当天振幅较大，上涨情绪仍在，但因为是低开，但已经减弱，需注意
 *             ——BR > AR——当天进行高开，需根据情况而定
 * CCI——顺势指标
 *    ——测量价格是否已超出常态分布范围
 *    ——属于超买超卖类指标中较特殊的一种
 *    ——波动于正无穷大和负无穷大之间。但是，又不需要以0为中轴线
 *    ——引进价格与固定期间的股价平均区间的偏离程度的概念，强调股价平均绝对偏差在股市技术分析中的重要性
 *    ——短期内暴涨暴跌，不会出现指标钝化现象
 *    ——测量脱离价格正常范围的变异性
 *    ——有一个相对的技术参照区域：+100和—100
 *    ——+100以上为超买区，—100以下为超卖区，+100到—100之间为震荡区，
 *    ——专门针对极端情况设计的
 *    ——当CCI扫描到异常股价波动时，立求速战速决，胜负瞬间立即分晓，赌输了也必须立刻了结
 *    ——当CCI指标曲线在+100线～-100线的常态区间里运行时,CCI指标参考意义不大，可以用KDJ等其它技术指标进行研判
 *    ——当CCI指标曲线从上向下突破+100线而重新进入常态区间时，表明市场价格的上涨阶段可能结束，将进入一个比较长时间的震荡整理阶段，应及时平多做空
 *    ——当CCI指标曲线从上向下突破-100线而进入另一个非常态区间（超卖区）时，表明市场价格的弱势状态已经形成，将进入一个比较长的寻底过程，可以持有空单等待更高利润
 *    ——在超卖区运行了相当长的一段时间后开始掉头向上，表明价格的短期底部初步探明，可以少量建仓。CCI指标曲线在超卖区运行的时间越长，确认短期的底部的准确度越高
 *    ——CCI指标曲线从下向上突破-100线而重新进入常态区间时，表明市场价格的探底阶段可能结束，有可能进入一个盘整阶段，可以逢低少量做多
 *    ——CCI指标曲线从下向上突破+100线而进入非常态区间(超买区)时，表明市场价格已经脱离常态而进入强势状态，如果伴随较大的市场交投，应及时介入成功率将很大
 *    ——CCI指标曲线从下向上突破+100线而进入非常态区间(超买区)后，只要CCI指标曲线一直朝上运行，表明价格依然保持强势可以继续持有待涨。但是，如果在远离+100线的地方开始掉头向下时，则表明市场价格的强势状态将可能难以维持，涨势可能转弱，应考虑卖出。如果前期的短期涨幅过高同时价格回落时交投活跃，则应该果断逢高卖出或做空
 *    ——CCI主要是在超买和超卖区域发生作用，对急涨急跌的行情检测性相对准确
 *    ——当CCI曲线在远离﹢100线上方的高位时，如果CCI曲线的走势形成M头或三重顶等顶部反转形态，可能预示着股价由强势转为弱势，股价即将大跌，应及时卖出股票。如果股价的曲线也出现同样形态则更可确认，其跌幅可以用M头或三重顶等形态理论来研判
 *    ——当CCI曲线在远离﹣100线下方的低位时，如果CCI曲线的走势出现W底或三重底等底部反转形态，可能预示着股价由弱势转为强势，股价即将反弹向上，可以逢低少量吸纳股票。如果股价曲线也出现同样形态更可确认，其涨幅可以用W底或三重底形态理论来研判
 *    ——CCI曲线的形态中M头和三重顶形态的准确性要大于W底和三重底
 *    ——CCI（N日）=（TP－MA）÷MD÷0.015
 *    ——TP=（最高价+最低价+收盘价）÷3
 *    ——MA=近N日收盘价的累计之和÷N
 *    ——MD=近N日（MA－收盘价）的累计之和÷N
 *    ——
 *    ——0～100为范围的超买超卖指标，专门是为常态行情设计的
 *    ——CCI指标就是专门对付极端行情的
 *    ——立求速战速决，胜负瞬间立即分晓，赌输了也必须立刻加速逃逸
 *    ——
 *    ——基本研判
 *    ——顶背离
 *    ——底背离
 *
 * DMI——动向指标或趋向指标——Directional Movement Index
 *    ——一种中长期股市技术分析
 *    ——分析股票价格在涨跌过程中买卖双方力量均衡点的变化情况
 *    ——寻找股票价格涨跌过程中，股价藉以创新高价或新低价的功能
 *    ——绝大部分都是以每一日的收盘价的走势及涨跌幅的累计数来计算出不同的分析数据，其不足之处在于忽略了每一日的高低之间的波动幅度
 *    ——+DI、-DI——多空指标
 *    ——ADX、ADXR——趋向指标
 *    ——+DI多方
 *    ——-DI空方
 *    ——+DI在-DI上方,股票行情以上涨为主;+DI在-DI下方，股票行情以下跌为主
 *    ——当+DI向上交叉-DI，是买进信号，相反,当+DI向下交叉-DI，是卖出信号
 *    ——-DI从20以下上升到50以上,股票价格很有可能会有一波中级下跌行情
 *    ——+DI从20以下上升到50以上,股票价格很有可能会有一波中级上涨行情
 *    ——+DI和-DI以20为基准线上下波动时，该股票多空双方拉锯战,股票价格以箱体整理为主
 *    ——
 *    ——ADX和ADXR是+DI和-DI的引导指标,同时也是判断股票行情的趋势指标
 *    ——当ADX从上面下穿ADXR时所形成的交叉点叫做死叉，当ADX与ADXR形成死叉时股票上涨行情将终结，如果ADX和ADXR下行至20左右并交织波动时，说明股票将横盘整理,没有上涨行情
 *    ——当ADX在50以上反转向下，不管股票价格是上涨还是下跌,都即将反转
 *    ——当ADX从下面上穿ADXR时，所形成的交叉点叫做ADX金叉ADXR;当ADX与ADXR发生金叉时，预示着股票将出现一波上涨行情，ADX的ADXR运行至50以上时，将可能产生一轮中级以上的行情,ADX和ADXR上行至80以上时，那么市场将很有可能是翻倍以上的大行情
 *    ——当4根线间距收窄时，表明股票行情处于盘整中，DMI指标失真
 *    ——
 *    ——计算当日动向值——上升动向（+DM）——当日的最高价减去前一日的最高价，如果<=0 则+DM=0
 *                      ——下降动向（-DM）——前一日的最低价减去当日的最低价，如果<=0 则-DM=0
 *                          ——再比较+DM和-DM，较大的那个数字保持，较小的数字归0
 *                      ——无动向——当日的+DM和﹣DM同时等于零
 *    ——计算真实波幅（TR）——当日价格较前一日价格的最大变动值
 *    ——计算方向线DI——+DI代表上升方向线——+DI=（+DM÷TR）×100
 *                    ——-DI代表下降方向线——-DI=（-DM÷TR）×100
 *    ——计算动向平均数ADX——+DI和—DI间的差的绝对值除以总和的百分比得到动向指数DX
 *                         ——DX=(DI DIF÷DI SUM) ×100
 *                         ——DI DIF为上升指标和下降指标的差的绝对值——DI SUM为上升指标和下降指标的总和
 *                         ——
 *    ——计算评估数值ADXR——ADXR=（当日的ADX+前n日的ADX）÷2
 *    ——
 *    ——当股价走势向上发展，而同时+DI从下方向上突破-DI时，表明市场上有新多买家进场，为买入信号，如果ADX伴随上升，则预示股价的涨势可能更强劲
 *    ——当股价走势向下发展时，而同时+DI从上向下突破-DI时，表明市场上做空力量在加强，为卖出信号，如果ADX伴随下降 [1]  ，则预示跌势将加剧
 *    ——当股价维持某种上升或下降行情时，+DI和﹣DI的交叉突破信号比较准确，但当股价维持盘整时，应将+DI和-DI交叉发出的买卖信号视为无效
 *    ——
 *    ——判断行情趋势——当行情走势由横盘向上发展时，ADX值会不断递增。因此，当ADX值高于前一日时，可以判断当前市场行情仍在维持原有的上升趋势，即股价将继续上涨，如果+DI和﹣DI同时增加，则表明当前上升趋势将十分强劲
 *                    ——当行情走势进入横盘阶段时，ADX值会不断递减。因此，判断行情时，应结合股价走势（+DI和-DI）走势进行判断
 *                    ——当行情走势由盘整向下发展时，ADX值也会不断递减。因此，当ADX值低于前一日时，可以判断当前市场行情仍维持原有的下降趋势，即股价将继续下跌，如果+DI和-DI同时减少，则表示当前的跌势将延续
 *    ——判断行情是否盘整——当市场行情在一定区域内小幅横盘盘整时，ADX值会出现递减情况。当ADX值降至20以下，且呈横向窄幅移动时，可以判断行情为牛皮盘整，上升或下跌趋势不明朗，投资者应以观望为主，不可依据+DI和-DI的交叉信号来买卖股票
 *    ——判断行情是否转势——当ADX值在高点由升转跌时，预示行情即将反转。在涨势中的ADX在高点由升转跌，预示涨势即将告一段落；在跌势中的ADX值从高位回落，预示跌势可能停止
 *    ——四线交叉原则——当+DI线同时在ADX线和ADXR线及-DI线以下（特别是在50线以下的位置时），说明市场处于弱市之中，股市向下运行的趋势还没有改变，股价可能还要下跌，投资者应持币观望或逢高卖出股票为主，不可轻易买入股票。这点是DMI指标研判的重点
 *                    ——当+DI线和-DI线同处50以下时，如果+DI线快速向上突破-DI线，预示新的主力已进场，股价短期内将大涨。如果伴随大的成交量放出，更能确认行情将向上，投资者应迅速短线买入股票
 *                    ——当+DI线从上向下突破-DI线（即-DI线从下向上突破+DI线）时，此时不论+DI和-DI处在什么位置都预示新的空头进场，股价将下跌，投资者应短线卖出股票或以持币观望为主
 *                    ——当+DI线、-DI线、ADX线和ADXR线等四线同时在50线以下绞合在一起窄幅横向运动，说明市场处于波澜不兴，股价处于横向整理之中，此时投资者应以持币观望为主
 *                    ——当+DI线、ADX线和ADXR线等三线同时在50线以下的位置，而此时三条线都快速向上发散，说明市场人气旺盛，股价处在上涨走势之中，投资者可逢低买入﹣或持股待涨。（这点中因为-DI线是下降方向线，其对上涨走势反应不灵，故不予以考虑）
 *                    ——对于牛股来说，ADX在50以上向下转折，仅仅回落到40——60之间，随即再度掉头向上攀升，而且股价在此期间走出横盘整理的态势。随着ADX再度回升，股价向上再次大涨，这是股价拉升时的征兆。这种情况经常出现在一些大涨的牛股中，此时DMI指标只是提供一个向上大趋势即将来临的参考。在实际操作中，则必须结合均线系统和均量线及其他指标一起研判
 *    ——
 *    ——DI交叉信号对于汇市的反应比其他指标要慢很多
 *    ——此指标比较适合中长线投资人使用,短线投资人不建议使用
 *    ——有些时候明明ADX指标已经发生转折了,但是汇价却没有随之转变,这就是指标失效现象
 *    ——
 *    ——4个小指标(PDI、MDI、ADX、ADXR等)出现汇集粘合迹象，一般历经10—15个交易日
 *    ——粘合表明多空双方处于十分胶着状态，也表明整理过程中，市场处于极度“平静”状态中，这其实从一个侧面说明当时市场浮动筹码趋于一个十分少的状态中
 *    ——经过粘合后(或粘合期间)，价格走势中收出的阳线非常密集，一般来说，阳K线占比达80%以上。在这种情况下，说明市场经过充分的整理后，多头开始逐渐“暗中”发力，才会持续收出占比较大比例的阳K线。而此时处于价格“爆发”前期，市场主流资金悄悄买多，且具有持续性和较大的力度，才导致价格缓慢走高
 *    ——具备上述2个条件下的品种(或股票)，一般后期都会有较大幅度的上涨行情，如上图的股指期货走势。那么，在出现上述信号后，进行买入动作，随后耐心持有，最后需要解决的一个问题就是何时卖出(平仓)的问题了
 *    ——当ADX向下交叉ADXR，且此时ADX已达80以上时，为卖出(平仓多单)信号。首先当ADX持续走高，并达到80以上区域时，说明市场已经经过了较大的上涨行情。而当ADX向下交叉ADXR，则说明市场开始出现明显的抛空动能，这将(至少短期阶段性)结束之前上涨趋势
 *    ——
 * CR——能量指标——中间意愿指标、价格动量指标
 *   ——分析股市多空双方力量对比、把握买卖股票时机的一种中长期技术分析工具
 *   ——理论出发点是：中间价是股市最有代表性的价格
 *   ——为避免AR、BR指标的不足，在选择计算的均衡价位时，CR指标采用的是上一计算周期的中间价
 *   ——比中间价高的价位其能量为“强”，比中间价低的价位其能量为“弱”。
 *   ——CR指标以上一个计算周期（如N日）的中间价比较当前周期（如日）的最高价、最低价，计算出一段时期内股价的“强弱”，从而在分析一些股价的异常波动行情时，有其独到的功能
 *   ——CR指标不但能够测量人气的热度、价格动量的潜能，而且能够显示出股价的压力带和支撑带
 *   ——CR（N日）=P1÷P2×100
 *   ——P1=Σ（H－YM），表示N日以来多方力量的总和；P2=Σ（YM－L），表示N日以来空方力量的总和。H表示今日的最高价，L表示今日的最低价YM表示昨日（上一个交易日）的中间价
 *   ——当CR由下向上穿过"副地震带"时，股价会受到次级压力。反之，当CR从上向下穿过"副地震带"时，股价会受到次级支撑的压力
 *   ——当CR由下向上穿过"主地震带"时，股价会受到相对强大的压力；反之，当CR由上自下穿过"主地震带"时，股价会受到相对强大的支撑力
 *   ——CR跌穿a、b、c、d四条线，再由低点向上爬升160时，为短线获利的一个良机，应适当卖出股票
 *   ——CR跌至40以下时，是建仓良机。而CR高于300~400时，应注意适当减仓
 *   ——在一定程度上，CR指标具有领先股价走势的示警作用，尤其是在股价见顶或筑底方面，能比股价曲线领先出现征兆。若股价曲线与CR指标曲线之间出现背离现象，则可能预示着股价走势即将反转
 *   ——CR数值出现负值，一律当成0对待
 *   ——CR值为100时也表示中间的意愿买卖呈平衡状态
 *   ——当CR数值在75——125之间（有的设定为80——150）波动时，表明股价属于盘整行情，投资者应以观望为主
 *   ——在牛市行情中（或对于牛股），当CR数值大于300时，表明股价已经进入高价区，可能随时回挡，应择机抛出
 *   ——对于反弹行情而言，当CR数值大于200时，表明股价反弹意愿已经到位，可能随时再次下跌，应及时离场
 *   ——在盘整行情中，当CR数值在40以下时，表明行情调整即将结束，股价可能随时再次向上，投资者可及时买进
 *   ——在熊市行情末期，当CR数值在30以下时，表明股价已经严重超跌，可能随时会反弹向上。投资者可逢低吸纳
 *   ——CR指标对于高数值的研判的准确性要高于CR对低数值的研判。即提示股价进入高价位区的能力比提示低价位区强
 *   ——当CR曲线在高位形成M头或三重顶等顶部反转形态时，可能预示着行情由强势转为弱势，股价即将大跌（特别是对于前期涨幅过大的股票），如果股价的K线也出现同样形态则更可确认，其跌幅可以用M头或三重顶形态理论来研判
 *   ——当CR曲线在低位出现W底或三重底等底部反转形态时，可能预示着行情由弱势转为强势，股价即将反弹向上，如果股价K线也出现同样形态则更可确认，其涨幅可以用W底或三重底等形态来判断
 *   ——相对而言，CR指标的高位M头或三重顶的判断的准确性要比其底部的W底或三重底要高
 *   ——
 *   ——CR曲线与股价曲线配合使用
 *   ——当CR指标曲线节节向上攀升，而股价曲线也同步上升，则意味着股价走势是处于强势上涨的阶段，股价走势将维持向上攀升的态势，投资者可坚决持股待涨
 *   ——当CR指标曲线继续下跌，而股价曲线也同步下跌，则意味着股价走势是处于弱势下跌的阶段，弱势格局难以改变，此时，投资者应以持币观望为主
 *   ——当CR指标曲线开始从高位掉头向下回落，而股价曲线却还在缓慢向上扬升，则意味着股价走势可能出现“顶背离”现象，特别是股价刚刚经历过了一段比较大涨幅的上升行情以后。当CR指标曲线在高位出现“顶背离现象”后，投资者应及时获利了结
 *   ——当CR指标曲线从底部开始向上攀升，而股价曲线却继续下跌，则意味着股价走势可能出现“底背离”现象，特别是股价前期经过了一轮时间比较长、跌幅比较大的下跌行情以后。当CR指标曲线在底部出现“底背离”现象以后，投资者可以少量分批建仓
 *   ——CR指标对“顶背离”研判的准确性要远远高于对“底背离”的研判
 *   ——
 *   ——通常情况下，CR指标值低于90时，可以放心的买进。这里90这一数值也只是参考
 *   ——CR指标的取值越小，说明买进的时候就越安全。如果CR指标急剧向上运动的时候，投资人可以依照实际情况考虑卖出
 *   ——CR指标如果和汇价的顶部或者底部形成背离的时候，就是应该采取行动的时候
 *   ——CR指标经常会出现负值，这时候，可以简单的把CR指标看作为零
 *   ——
 * PSY——情绪指标
 *    ——具有一定的参考意
 *    ——PSY=(N日内上涨天数/N)*100
 *    ——一段下跌（上升）行情展开前，超买(超卖)的最高（低）点通常会出现两次。在出现第二次超买(超卖)的最高（低）点时，一般是卖出（买进）时机。由于PSY指标具有这种高点密集出现的特性，可给投资者带来充裕时间进行研判与介入
 *    ——PSY指标在25～75之间为常态分布。PSY指标主要反映市场心理的超买超卖，因此，当心理线指标在常态区域内上下移动时，一般应持观望态度
 *    ——PSY指标超过75或低于25时，表明股价开始步入超买区或超卖区,此时需要留心其动向。当PSY指标百分比值超过83或低于17时，表明市场出现超买区或超卖区，价位回跌或回升的机会增加，投资者应该准备卖出或买进，不必在意是否出现第二次信号。这种情况在个股中比较多见
 *    ——当PSY指标百分比值<10，是极度超卖。抢反弹的机会相对提高，此时为短期较佳的买进时机；反之，如果PSY指标百分比值>90，是极度超买。此时为短期卖出的有利时机
 *    ——当PSY曲线和PSYMA曲线同时向上运行时，为买入时机；相反，当PSY曲线与PSYMA曲线同时向下运行时，为卖出时机。而当PSY曲线向上突破PSYMA曲线时，为买入时机；相反，当PSY曲线向下跌破PSYMA曲线后，为卖出时机
 *    ——当PSY曲线向上突破PSYMA曲线后，开始向下回调至PSYMA曲线，只要PSY曲线未能跌破PSYMA曲线，都表明股价属于强势整理。一旦PSY曲线再度返身向上时，为买入时机；当PSY曲线和PSYMA曲线同时向上运行一段时间后，PSY曲线远离PSYMA曲线时，一旦PSY曲线掉头向下，说明股价上涨的动能消耗较大，为卖出时机
 *    ——当PSY曲线和PSYMA曲线再度同时向上延伸时，投资者应持股待涨；当PSY曲线在PSYMA曲线下方运行时，投资者应持币观望
 *    ——当PSY曲线和PSYMA曲线始终交织在一起，于一个波动幅度不大的空间内运动时，预示着股价处于盘整的格局中，投资者应以观望为主
 *    ——
 *    ——中短期的研判指标
 *    ——反映市场上投资者的心理的超买或超卖
 *    ——反映市场能量的一种辅助指标
 *    ——PSY指标的取值始终是处在0——100之间，0值是PSY指标的下限极值，100是PSY指标的上限极值。50值为多空双方的分界线
 *    ——PSY值大于50为PSY指标的多方区域，说明N日内上涨的天数大于下跌的天数，多方占主导地位，投资者可持股待涨
 *    ——PSY值小于50为PSY指标的空方区域，说明N日内上涨的天数小于下跌的天数，空方占主导地位，投资者宜持币观望
 *    ——PSY在50左右徘徊，则反映近期股票指数或股价上涨的天数与下跌的天数基本相等，多空力量维持平衡，投资者以观望为主
 *    ——一般情况下，PSY值的变化都在25——75之间，反映股价处在正常的波动状态，投资者可以按照原有的思路买卖股票
 *    ——在盘整局面中，PSY指标的值应该在以50为中心的附近，上下限一般定为25和75（有的定为30和70），说明多空双方基本处于平衡状态。如果PSY超出了这个平衡状态，就是PSY指标的超买超卖
 *    ——当PSY达到或超过75时。说明在N天内，上涨的天数远大于下跌的天数，多方的力量很强大而且持久。但从另外一个方面来看，由于上涨天数多，股票累计的获利盘也多，市场显示出超买的迹象，特别是在涨幅较大的情况下，股价上升的压力就会很大，股价可能很快回落调整，投资者应多加注意
 *    ——当PSY达到或低于25时，说明在N天内，下跌的天数远大于上涨的天数，空方力量比较强大，市场上悲观气氛比较浓，股价一路下跌。但从另一方面看，由于下跌的天数较多，市场上显示超卖的迹象，特别是在跌幅较大的情况下，市场抛盘稀少，抛压较轻，股价可能会反弹向上
 *    ——如果PSY值出现大于90或小于10这种极端超买超卖情况，投资者更要多加注意
 *    ——在多头市场和空头市场开始初期，可将超买、超卖线调整至85和15，到行情发展中后期再调回至75和25，这样更有利于PSY指标的研判
 *    ——
 *    ——无趋势——PSY指标的无趋势性是指PSY值在40——60之间上下振荡，表明近期多空力量旗鼓相当，股价涨跌基本平衡。反映到PSY的曲线图上就是PSY曲线在40——60线区间里小幅上下运动或成一条横线运动。此时，投资者宜采取观望态度，等趋势形成后再做买入和卖出的决策
 *    ——向上趋势——PSY指标的向上趋势性有两种情况。一是指PSY值大部分时间是处在50上，即使偶尔下滑至50以下也会很快回升至50以上并向上爬升，另一种是指PSY值从50以下开始向上一举冲过50并缓慢向上攀升。表明近期多头力量强于空头力量，股价一路上涨。这两种情况反映到PSY曲线图上就是PSY曲线在50线以上缓慢向上运动或从50线以下向上一路攀升的向上倾斜曲线。当PSY向上趋势形成后，投资者应积极买入股票或持股待涨，至到向上趋势改变
 *    ——向下趋势——PSY指标的向下趋势性也有两种情况。一是指PSY值大部分时间是处在50以下，另一种是PSY值从50以上开始向下回落跌破50线并继续向下滑落。表明空方力量过于强大，股价一路下跌。这两种情况表明空头力量强于多头力量，股价一路下跌
 *    ——
 *
 *
 *
 */





























}
