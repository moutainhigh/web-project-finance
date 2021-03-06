package com.sky.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by lxl on 2018/9/22.
 */
public class HttpUtil {

    public static String getHtmlContentByGet(String url){
        return getHtmlContent(url , 1);
    }

    public static String getHtmlContentByPost(String url){
        return getHtmlContent(url , 2);
    }

    public static String getHtmlContent(String urls ,Integer type){
        HttpClientBuilder custom = HttpClients.custom();//创建httpclient

        CloseableHttpClient build = custom.build();//通过构建器构建一个httpclient对象，可以认为是获取到一个浏览器对象
        CloseableHttpResponse response = null;
        String content = null;
        try {
            URL url = new URL(urls);
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            if(type == 1){
                HttpGet httpGet = new HttpGet(uri);//把url封装到get请求中
                response = build.execute(httpGet);//使用client执行get请求,获取请求的结果，请求的结果被封装到response中
            }else{
                HttpPost httpPost = new HttpPost(uri);//把url封装到post请求中
                response = build.execute(httpPost);//使用client执行get请求,获取请求的结果，请求的结果被封装到response中
            }
            HttpEntity entity = response.getEntity();//表示获取返回的内容实体对象
            content = EntityUtils.toString(entity ,"UTF-8");//解析实体中页面的内容，返回字符串形式
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return content;
    }

}
