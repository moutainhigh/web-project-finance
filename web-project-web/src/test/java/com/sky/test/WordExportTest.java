package com.sky.test;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;
import com.sky.core.utils.ChartUtils;
import com.sky.core.utils.FileUtils;
import com.sky.core.utils.ImgUtils;
import com.sky.core.utils.Serie;
import com.sky.model.StockCompanyAsset;
import com.sky.model.StockCompanyProduct;
import com.sky.model.StockCompanySector;
import com.sky.model.StockRiseRate;
import com.sky.service.*;
import com.sky.vo.CreateCompanyWorld_VO;
import com.sky.vo.StockCompanyAssetVO;
import com.sky.vo.StockCompanyProfitVO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ThinkPad on 2019/12/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WordExportTest {

    @Autowired
    private StockCompanySectorService stockCompanySectorService ;

    @Autowired
    private StockCompanyProductService stockCompanyProductService;

    @Autowired
    private StockCompanyProfitService stockCompanyProfitService ;

    @Autowired
    private StockCompanyAssetService stockCompanyAssetService ;

    @Autowired
    private StockRiseRateService stockRiseRateService;

   @Test
    public void main() {
        // 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);
        try {

            List<CreateCompanyWorld_VO> list = stockCompanySectorService.getCreateCompanyWorldList(null , null , null , null ,"水泥");
            RtfWriter2.getInstance(document,new FileOutputStream("E:/dataImg/水泥.doc"));

            document.open();

            for(CreateCompanyWorld_VO body : list){
                List<StockCompanyProduct> productList = stockCompanyProductService.getNewCompanyProductList("产品分类" , body.getStockCode());

                List<StockCompanyProduct> regionList = stockCompanyProductService.getNewCompanyProductList("地域分类" , body.getStockCode());

                createProfitDataImg( body.getStockCode());

                createProfitSeasonDataImg( body.getStockCode());

                createAssetDataImg( body.getStockCode());

                createCycleDataImg( body.getStockCode());

                document = getTableList(document , body, productList , regionList);
                FileUtils.deleteSamilarFile("E:/dataImg/" , body.getStockCode());
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Document getTableList(Document document , CreateCompanyWorld_VO body, List<StockCompanyProduct> productList , List<StockCompanyProduct> regionList){

        try {
            /*
             * 创建有三列的表格
             */
            Table table = new Table(6);
            table.setWidth(100);
            table.setBorderWidth(0);
            table.setBorderColor(Color.BLACK);
            table.setPadding(0);
            table.setSpacing(0);

            Font font = new Font();
            font.setStyle(Font.BOLD);
            font.setSize(9);

            Font fontContent = new Font();
            fontContent.setSize(9);

            Cell cell = new Cell();
            /*********************第 1 行*********************/
            Paragraph stockCode = new Paragraph("企业编码" ,font);
            cell = new Cell(stockCode);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph stockCodeContent = new Paragraph(body.getStockCode() ,fontContent);
            cell = new Cell(stockCodeContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph compayName = new Paragraph("企业名称" ,font);
            cell = new Cell(compayName);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph compayNameContent = new Paragraph(body.getCompanyName() ,fontContent);
            cell = new Cell(compayNameContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph compayIdentity = new Paragraph("企业性质" ,font);
            cell = new Cell(compayIdentity);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph compayIdentityContent = new Paragraph(body.getCompanyClass() ,fontContent);
            cell = new Cell(compayIdentityContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            /*********************第 2 行*********************/
            Paragraph buildTime = new Paragraph("成立时间" ,font);
            cell = new Cell(buildTime);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph buildTimeContent = new Paragraph(body.getEstablishDay() ,fontContent);
            cell = new Cell(buildTimeContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph publishTime = new Paragraph("上市时间" ,font);
            cell = new Cell(publishTime);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph publishTimeContent = new Paragraph(body.getPublishDay() ,fontContent);
            cell = new Cell(publishTimeContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph spaceYear = new Paragraph("历经时间" ,font);
            cell = new Cell(spaceYear);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph spaceYearContent = new Paragraph(body.getSpaceYear() ,fontContent);
            cell = new Cell(spaceYearContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            /*********************第 3 行*********************/
            Paragraph publishAmount = new Paragraph("发行股本" ,font);
            cell = new Cell(publishAmount);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph publishAmountContent = new Paragraph(body.getPublishCount() ,fontContent);
            cell = new Cell(publishAmountContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph flowAmount = new Paragraph("流通股本" ,font);
            cell = new Cell(flowAmount);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph flowAmountContent = new Paragraph(body.getFlowCount() ,fontContent);
            cell = new Cell(flowAmountContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph flowRate = new Paragraph("流通占比" ,font);
            cell = new Cell(flowRate);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph flowRateContent = new Paragraph(body.getFlowRate() ,fontContent);
            cell = new Cell(flowRateContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            /*********************第 4 行*********************/
            Paragraph marketOrder = new Paragraph("市场地位" ,font);
            cell = new Cell(marketOrder);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph marketOrderContent = new Paragraph(body.getMarketOrder() ,fontContent);
            cell = new Cell(marketOrderContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph sectorOrder = new Paragraph("产业地位" ,font);
            cell = new Cell(sectorOrder);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph sectorOrderContent = new Paragraph(body.getSectorOrder() ,fontContent);
            cell = new Cell(sectorOrderContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyType = new Paragraph("企业类型" ,font);
            cell = new Cell(companyType);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyTypeContent = new Paragraph(body.getCompanyType() ,fontContent);
            cell = new Cell(companyTypeContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            /*********************第 5 行*********************/
            Paragraph companyRegion = new Paragraph("地理位置" ,font);
            cell = new Cell(companyRegion);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyRegionContent = new Paragraph(body.getCompanyRegion() ,fontContent);
            cell = new Cell(companyRegionContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyStrength = new Paragraph("企业特点" ,font);
            cell = new Cell(companyStrength);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyStrengthContent = new Paragraph(body.getCompanyStrength() ,fontContent);
            cell = new Cell(companyStrengthContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyChance = new Paragraph("企业潜力" ,font);
            cell = new Cell(companyChance);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph companyChanceContent = new Paragraph(body.getCompanyChance() ,fontContent);
            cell = new Cell(companyChanceContent);
            cell.setVerticalAlignment(1);
            table.addCell(cell);


            /*********************第 6 行*********************/
            int rows = 0 ;
            if(productList.size() >= regionList.size()){
                rows = productList.size();
            }else{
                rows = regionList.size();
            }

            for(int i = 0 ; i < rows ; i ++){
                if(i == 0){
                    Paragraph mainProduct = new Paragraph("主营产品" ,font);
                    cell = new Cell(mainProduct);
                    cell.setRowspan(rows);
                    cell.setHorizontalAlignment(1);
                    cell.setVerticalAlignment(1);
                    table.addCell(cell);

                    StockCompanyProduct product = productList.get(i);
                    Paragraph mainProductContent = new Paragraph(product.getProductName() ,fontContent);
                    cell = new Cell(mainProductContent);
                    cell.setVerticalAlignment(1);
                    table.addCell(cell);

                    Paragraph mainProductRate = new Paragraph(product.getProductRevenueRate() ,fontContent);
                    cell = new Cell(mainProductRate);
                    cell.setVerticalAlignment(1);
                    table.addCell(cell);


                    Paragraph mainRegion = new Paragraph("主营地域" ,font);
                    cell = new Cell(mainRegion);
                    cell.setRowspan(rows);
                    cell.setHorizontalAlignment(1);
                    cell.setVerticalAlignment(1);
                    table.addCell(cell);

                    StockCompanyProduct region = regionList.get(i);
                    Paragraph mainRegionContent = new Paragraph(region.getProductName() ,fontContent);
                    cell = new Cell(mainRegionContent);
                    cell.setVerticalAlignment(1);
                    table.addCell(cell);

                    Paragraph mainRegionRate = new Paragraph(region.getProductRevenueRate() ,fontContent);
                    cell = new Cell(mainRegionRate);
                    cell.setVerticalAlignment(1);
                    table.addCell(cell);
                }else {
                    if(i < productList.size()){
                        StockCompanyProduct product = productList.get(i);
                        Paragraph mainProductContent = new Paragraph(product.getProductName() ,fontContent);
                        cell = new Cell(mainProductContent);
                        cell.setVerticalAlignment(1);
                        table.addCell(cell);

                        Paragraph mainProductRate = new Paragraph(product.getProductRevenueRate() ,fontContent);
                        cell = new Cell(mainProductRate);
                        cell.setVerticalAlignment(1);
                        table.addCell(cell);
                    }else{
                        table.addCell("");
                        table.addCell("");
                    }

                    if(i < regionList.size()){
                        StockCompanyProduct region = regionList.get(i);
                        Paragraph mainRegionContent = new Paragraph(region.getProductName() ,fontContent);
                        cell = new Cell(mainRegionContent);
                        cell.setVerticalAlignment(1);
                        table.addCell(cell);

                        Paragraph mainRegionRate = new Paragraph(region.getProductRevenueRate() ,fontContent);
                        cell = new Cell(mainRegionRate);
                        cell.setVerticalAlignment(1);
                        table.addCell(cell);
                    }else {
                        table.addCell("");
                        table.addCell("");
                    }
                }
            }


            /*********************第 7 行*********************/
            Paragraph productType = new Paragraph("产品分类" ,font);
            cell = new Cell(productType);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph productTypeContent = new Paragraph("" ,font);
            cell = new Cell(productTypeContent);
            cell.setVerticalAlignment(1);
            cell.setColspan(5);
            table.addCell(cell);

            /*********************第 8 行*********************/
            Paragraph marketClass = new Paragraph("市场划份" ,font);
            cell = new Cell(marketClass);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph marketClassContent = new Paragraph(body.getMarketClass() ,fontContent);
            cell = new Cell(marketClassContent);
            cell.setVerticalAlignment(1);
            cell.setColspan(2);
            table.addCell(cell);

            Paragraph internalAgree = new Paragraph("国际认同" ,font);
            cell = new Cell(internalAgree);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph internalAgreeContent = new Paragraph(body.getInternalOrganize() ,fontContent);
            cell = new Cell(internalAgreeContent);
            cell.setVerticalAlignment(1);
            cell.setColspan(2);
            table.addCell(cell);

            /*********************第 9 行*********************/
            Paragraph focusOrganize = new Paragraph("关注对象" ,font);
            cell = new Cell(focusOrganize);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph focusOrganizeContent = new Paragraph(body.getFocusOrganize() ,fontContent);
            cell = new Cell(focusOrganizeContent);
            cell.setVerticalAlignment(1);
            cell.setColspan(5);
            table.addCell(cell);

            /*********************第 10 行*********************/
            Paragraph productHot = new Paragraph("热点内容" ,font);
            cell = new Cell(productHot);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Paragraph productHotContent = new Paragraph(body.getGroupHot() ,fontContent);
            cell = new Cell(productHotContent);
            cell.setVerticalAlignment(1);
            cell.setColspan(5);
            table.addCell(cell);

            /*********************第 11 行*********************/
            Paragraph companyProfit = new Paragraph("盈利情况" ,font);
            cell = new Cell(companyProfit);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            Image img = Image.getInstance("E:\\dataImg\\"+ body.getStockCode() +"_profit.jpg");
            if(img != null){
                img.setAbsolutePosition(0, 0);
                img.setAlignment(Image.LEFT);
                img.scalePercent(42);
                cell = new Cell(img);
                cell.setColspan(5);
                table.addCell(cell);
            }else{
                cell = new Cell("");
                cell.setColspan(5);
                table.addCell(cell);
            }


            img = Image.getInstance("E:\\dataImg\\"+ body.getStockCode() +"_season_profit.jpg");
            if(img != null){
                img.setAbsolutePosition(0, 0);
                img.setAlignment(Image.LEFT);
                img.scalePercent(42);
                cell = new Cell(img);
                cell.setColspan(5);
                table.addCell(cell);
            }else{
                cell = new Cell("");
                cell.setColspan(5);
                table.addCell(cell);
            }

            /*********************第 12 行*********************/
            Paragraph companyAsset = new Paragraph("资产情况" ,font);
            cell = new Cell(companyAsset);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            img = Image.getInstance("E:\\dataImg\\"+ body.getStockCode() +"_asset.jpg");
            if(img != null){
                img.setAbsolutePosition(0, 0);
                img.setAlignment(Image.LEFT);
                img.scalePercent(42);
                cell = new Cell(img);
                cell.setColspan(5);
                table.addCell(cell);
            }else{
                cell = new Cell("");
                cell.setColspan(5);
                table.addCell(cell);
            }

            /*********************第 13 行*********************/
            Paragraph marketCycle = new Paragraph("市场周期" ,font);
            cell = new Cell(marketCycle);
            cell.setHorizontalAlignment(1);
            cell.setVerticalAlignment(1);
            table.addCell(cell);

            img = Image.getInstance("E:\\dataImg\\"+ body.getStockCode() +"_rate.jpg");
            if(img != null){
                img.setAbsolutePosition(0, 0);
                img.setAlignment(Image.LEFT);
                img.scalePercent(42);
                cell = new Cell(img);
                cell.setColspan(5);
                table.addCell(cell);
            }else{
                cell = new Cell("");
                cell.setColspan(5);
                table.addCell(cell);
            }

            document.add(table);
        }catch (Exception e){
            e.printStackTrace();
        }

        return document;
    }


    private void createProfitDataImg(String stockCode){
        try {
            List<StockCompanyProfitVO> list = stockCompanyProfitService.getCompanyProfitGrowList(stockCode);
            if(list.size() == 0 ){
                return;
            }
            List<String> title = new ArrayList<>();

            List<BigDecimal> totalProfit = new ArrayList<>();
            List<BigDecimal> belongProfit = new ArrayList<>();

            for(StockCompanyProfitVO profit : list){
                title.add(profit.getPublishYear());
                totalProfit.add(profit.getTotalProfit());
                belongProfit.add(profit.getBelongProfit());
            }
            if(totalProfit.size() == 0){
                return;
            }
            String[] categories = new String[title.size()];
            title.toArray(categories);
            Vector<Serie> series = new Vector<Serie>();
            // 柱子名称：柱子所有的值集合
            series.add(new Serie("总利润", totalProfit));
            series.add(new Serie("归属利润", belongProfit));
            // 1：创建数据集合
            DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);

            // 2：创建Chart[创建不同图形]
            JFreeChart chart = ChartFactory.createLineChart("利润增长", "", "收益（亿）", dataset);
            // 3:设置抗锯齿，防止字体显示不清楚
            ChartUtils.setAntiAlias(chart);// 抗锯齿
            // 4:对柱子进行渲染[[采用不同渲染]]
            ChartUtils.setLineRender(chart.getCategoryPlot(), false,true);//
            // 5:对其他部分进行渲染
            ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
            ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
            // 设置标注无边框
            chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

            ChartUtils.saveAsFile(chart , "E:/dataImg/"+ stockCode +"_profit.jpg" , 1024 , 420);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createProfitSeasonDataImg(String stockCode){
        try {
            List<StockCompanyProfitVO> list = stockCompanyProfitService.getCompanyProfitGrowList(stockCode);
            if(list.size() == 0 ){
                return;
            }
            List<String> title = new ArrayList<>();

            List<BigDecimal> firstSeasonProfit = new ArrayList<>();
            List<BigDecimal> secondSeasonProfit = new ArrayList<>();
            List<BigDecimal> threeSeasonProfit = new ArrayList<>();
            List<BigDecimal> forthtSeasonProfit = new ArrayList<>();

            for(StockCompanyProfitVO profit : list){
                title.add(profit.getPublishYear());
                firstSeasonProfit.add(profit.getFirstSeasonProfit());
                secondSeasonProfit.add(profit.getSecondSeasonProfit());
                threeSeasonProfit.add(profit.getThirdSeasonProfit());
                forthtSeasonProfit.add(profit.getForthSeasonProfit());
            }
            if(firstSeasonProfit.size() == 0){
                return;
            }
            String[] categories = new String[title.size()];
            title.toArray(categories);
            Vector<Serie> series = new Vector<Serie>();
            // 柱子名称：柱子所有的值集合
            series.add(new Serie("第一季度", firstSeasonProfit));
            series.add(new Serie("第二季度", secondSeasonProfit));
            series.add(new Serie("第三季度", threeSeasonProfit));
            series.add(new Serie("第四季度", forthtSeasonProfit));
            // 1：创建数据集合
            DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);

            // 2：创建Chart[创建不同图形]
            JFreeChart chart = ChartFactory.createLineChart("季度利润增长", "", "涨幅（亿）", dataset);
            // 3:设置抗锯齿，防止字体显示不清楚
            ChartUtils.setAntiAlias(chart);// 抗锯齿
            // 4:对柱子进行渲染[[采用不同渲染]]
            ChartUtils.setLineRender(chart.getCategoryPlot(), false,true);//
            // 5:对其他部分进行渲染
            ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
            ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
            // 设置标注无边框
            chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

            ChartUtils.saveAsFile(chart , "E:/dataImg/"+ stockCode +"_season_profit.jpg" , 1024 , 420);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createAssetDataImg(String stockCode){
        try {
            List<StockCompanyAssetVO> list = stockCompanyAssetService.getCompanyAssetGrowList(stockCode);
            if(list.size() == 0 ){
                return;
            }
            List<String> title = new ArrayList<>();

            List<BigDecimal> totalAsset = new ArrayList<>();
            List<BigDecimal> totalDebt = new ArrayList<>();
            List<BigDecimal> pureAsset = new ArrayList<>();

            for(StockCompanyAssetVO asset : list){
                title.add(asset.getPublishYear());
                totalAsset.add(asset.getTotalAsset());
                totalDebt.add(asset.getTotalDebt());
                pureAsset.add(asset.getPureAsset());
            }
            if(totalAsset.size() == 0){
                return;
            }
            String[] categories = new String[title.size()];
            title.toArray(categories);
            Vector<Serie> series = new Vector<Serie>();
            // 柱子名称：柱子所有的值集合
            series.add(new Serie("总资产", totalAsset));
            series.add(new Serie("净资产", pureAsset));
            series.add(new Serie("总负债", totalDebt));
            // 1：创建数据集合
            DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);

            // 2：创建Chart[创建不同图形]
            JFreeChart chart = ChartFactory.createLineChart("资产增长", "", "收益（亿）", dataset);
            // 3:设置抗锯齿，防止字体显示不清楚
            ChartUtils.setAntiAlias(chart);// 抗锯齿
            // 4:对柱子进行渲染[[采用不同渲染]]
            ChartUtils.setLineRender(chart.getCategoryPlot(), false,true);//
            // 5:对其他部分进行渲染
            ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
            ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
            // 设置标注无边框
            chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

            ChartUtils.saveAsFile(chart , "E:/dataImg/"+ stockCode +"_asset.jpg" , 1024 , 420);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createCycleDataImg(String stockCode){
        try {
            StockRiseRate riseRate = stockRiseRateService.selectOne(new EntityWrapper<StockRiseRate>().where("stock_code = {0} and start_time = '2015-01-01' and deal_period = 3" , stockCode ));
            if(riseRate == null){
                return;
            }
            Map<String ,JSONArray> map = new HashMap<>();
            List<BigDecimal> rateArr = new ArrayList<>();
            List<BigDecimal> upperArr = new ArrayList<>();
            List<BigDecimal> shockArr = new ArrayList<>();
            rateArr.add(riseRate.getOneRise() == null ? BigDecimal.ZERO : riseRate.getOneRise());
            rateArr.add(riseRate.getTowRise() == null ? BigDecimal.ZERO : riseRate.getTowRise());
            rateArr.add(riseRate.getThreeRise() == null ? BigDecimal.ZERO : riseRate.getThreeRise());
            rateArr.add(riseRate.getFourRise() == null ? BigDecimal.ZERO : riseRate.getFourRise());
            rateArr.add(riseRate.getFiveRise() == null ? BigDecimal.ZERO : riseRate.getFiveRise());
            rateArr.add(riseRate.getSixRise() == null ? BigDecimal.ZERO : riseRate.getSixRise());
            rateArr.add(riseRate.getSevenRise() == null ? BigDecimal.ZERO : riseRate.getSevenRise());
            rateArr.add(riseRate.getEightRise() == null ? BigDecimal.ZERO : riseRate.getEightRise());
            rateArr.add(riseRate.getNineRise() == null ? BigDecimal.ZERO : riseRate.getNineRise());
            rateArr.add(riseRate.getTenRise() == null ? BigDecimal.ZERO : riseRate.getTenRise());
            rateArr.add(riseRate.getElevenRise() == null ? BigDecimal.ZERO : riseRate.getElevenRise());
            rateArr.add(riseRate.getTwelveRise() == null ? BigDecimal.ZERO : riseRate.getTwelveRise());

            upperArr.add(riseRate.getOneAmplitude() == null ? BigDecimal.ZERO : riseRate.getOneAmplitude());
            upperArr.add(riseRate.getTowAmplitude() == null ? BigDecimal.ZERO : riseRate.getTowAmplitude());
            upperArr.add(riseRate.getThreeAmplitude() == null ? BigDecimal.ZERO : riseRate.getThreeAmplitude());
            upperArr.add(riseRate.getFourAmplitude() == null ? BigDecimal.ZERO : riseRate.getFourAmplitude());
            upperArr.add(riseRate.getFiveAmplitude() == null ? BigDecimal.ZERO : riseRate.getFiveAmplitude());
            upperArr.add(riseRate.getSixAmplitude() == null ? BigDecimal.ZERO : riseRate.getSixAmplitude());
            upperArr.add(riseRate.getSevenAmplitude() == null ? BigDecimal.ZERO : riseRate.getSevenAmplitude());
            upperArr.add(riseRate.getEightAmplitude() == null ? BigDecimal.ZERO : riseRate.getEightAmplitude());
            upperArr.add(riseRate.getNineAmplitude() == null ? BigDecimal.ZERO : riseRate.getNineAmplitude());
            upperArr.add(riseRate.getTenAmplitude() == null ? BigDecimal.ZERO : riseRate.getTenAmplitude());
            upperArr.add(riseRate.getElevenAmplitude() == null ? BigDecimal.ZERO : riseRate.getElevenAmplitude());
            upperArr.add(riseRate.getTwelveAmplitude() == null ? BigDecimal.ZERO : riseRate.getTwelveAmplitude());

            shockArr.add(riseRate.getOneShock() == null ? BigDecimal.ZERO : riseRate.getOneShock());
            shockArr.add(riseRate.getTowShock() == null ? BigDecimal.ZERO : riseRate.getTowShock());
            shockArr.add(riseRate.getThreeShock() == null ? BigDecimal.ZERO : riseRate.getThreeShock());
            shockArr.add(riseRate.getFourShock() == null ? BigDecimal.ZERO : riseRate.getFourShock());
            shockArr.add(riseRate.getFiveShock() == null ? BigDecimal.ZERO : riseRate.getFiveShock());
            shockArr.add(riseRate.getSixShock() == null ? BigDecimal.ZERO : riseRate.getSixShock());
            shockArr.add(riseRate.getSevenShock() == null ? BigDecimal.ZERO : riseRate.getSevenShock());
            shockArr.add(riseRate.getEightShock() == null ? BigDecimal.ZERO : riseRate.getEightShock());
            shockArr.add(riseRate.getNineShock() == null ? BigDecimal.ZERO : riseRate.getNineShock());
            shockArr.add(riseRate.getTenShock() == null ? BigDecimal.ZERO : riseRate.getTenShock());
            shockArr.add(riseRate.getElevenShock() == null ? BigDecimal.ZERO : riseRate.getElevenShock());
            shockArr.add(riseRate.getTwelveShock() == null ? BigDecimal.ZERO : riseRate.getTwelveShock());

            String[] categories = {"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
            Vector<Serie> series = new Vector<Serie>();
            // 柱子名称：柱子所有的值集合
            series.add(new Serie("涨幅", upperArr));
            series.add(new Serie("涨率", rateArr));
            series.add(new Serie("振幅", shockArr));
            // 1：创建数据集合
            DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);

            // 2：创建Chart[创建不同图形]
            JFreeChart chart = ChartFactory.createBarChart("资产增长", "", "收益（亿）", dataset);
            // 3:设置抗锯齿，防止字体显示不清楚
            ChartUtils.setAntiAlias(chart);// 抗锯齿
            // 4:对柱子进行渲染[[采用不同渲染]]
//            ChartUtils.setLineRender(chart.getCategoryPlot(), true,true);
            // 5:对其他部分进行渲染
            ChartUtils.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
            ChartUtils.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
            // 设置标注无边框
            chart.getLegend().setFrame(new BlockBorder(Color.WHITE));

            ChartUtils.saveAsFile(chart , "E:/dataImg/"+ stockCode +"_rate.jpg" , 1024 , 420);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
