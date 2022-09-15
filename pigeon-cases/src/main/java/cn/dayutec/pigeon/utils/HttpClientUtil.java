package cn.dayutec.pigeon.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String sendGet(String url) {
        // 创建http GET请求
        HttpGet httpGet = new
                //请求的地址
                HttpGet(url);
        try (
                // 创建Httpclient对象
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                // 执行请求
                CloseableHttpResponse response = httpClient.execute(httpGet);
        ) {
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                LOG.info("sendGet请求发送成功：" + content);
                return content;
            } else {
                LOG.info("sendGet请求发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendPost(String url, StringEntity jsonStr) {
        String content = null;
        // 创建http POST请求
        HttpPost httpPost = new
                //请求的地址
                HttpPost(url);
        jsonStr.setContentEncoding("UTF-8");
        // 发送json数据需要设置contentType
        jsonStr.setContentType("application/json");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(jsonStr);
        try (
                // 创建Httpclient对象
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                // 执行请求
                CloseableHttpResponse response = httpClient.execute(httpPost);
        ) {
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                LOG.info("sendPost请求发送成功：" + content);
                return content;
            } else {
                LOG.info("sendPost请求发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
