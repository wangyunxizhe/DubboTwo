package com.dubbo.two.server.service;

import com.dubbo.two.server.data.RecordResponse;
import com.dubbo.two.server.request.RecordCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderRecordService {

    private static final Logger log = LoggerFactory.getLogger(OrderRecordService.class);

    //dubboOne暴露的rest协议接口地址
    private static final String URL = "http://localhost:9013/dubboone/record/create";

    private OkHttpClient httpClient = new OkHttpClient();

    @Autowired
    private ObjectMapper objectMapper;//将对象转为字符串对象的序列化主键

    @Autowired
    private HttpService httpService;//自己写的基于okhttp3的通用化的http服务类

    /**
     * 负责处理controller传来的业务逻辑
     */
    public void createOrder(RecordCreateRequest recordCreateRequest) throws Exception {
        try {
            //1，使用OkHttpClient，构造builder
            Request.Builder builder = new Request.Builder().url(URL).header("Content-Type", "application/json");
            //2，构造请求体
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(recordCreateRequest));//将对象转为String，再以json格式构造请求体
            //3，构造请求
            Request request = builder.post(requestBody).build();
            //4，发起请求
            Response response = httpClient.newCall(request).execute();
            log.info("createOrder响应结果：{}", response.body().toString());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 负责处理controller传来的业务逻辑
     * <p>
     * 通过自己写的基于okhttp3的通用化的http服务类来请求生产者的dubbo接口
     */
    public void createOrderByMe(RecordCreateRequest recordCreateRequest) throws Exception {
        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/json");
            String res = httpService.post(URL, headerMap,
                    "application/json", objectMapper.writeValueAsString(recordCreateRequest));
            log.info("createOrderByMe响应结果：{}", res);

            //将得到的json格式的字符串解析成map
            //可以针对响应数据字段比较少的场景
            Map<String, Object> resMap = objectMapper.readValue(res, Map.class);
            log.info("createOrderByMe响应结果--map解析：{} ", resMap);
            Integer code = (Integer) resMap.get("code");
            String msg = (String) resMap.get("msg");
            Integer data = (Integer) resMap.get("data");
            log.info("code={} msg={} data={} ", code, msg, data);

            //将得到的json格式的字符串解析成对象
            //这种方式更加通用
            RecordResponse response = objectMapper.readValue(res, RecordResponse.class);
            log.info("createOrderByMe响应结果--对象解析：{} ", response);
        } catch (Exception e) {
            throw e;
        }
    }

}
