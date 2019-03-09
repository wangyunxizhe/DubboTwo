package com.dubbo.two.server.controller;

import com.dubbo.two.server.request.RecordCreateRequest;
import com.dubbo.two.server.service.OrderRecordService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderRecordController {

    private static final Logger log = LoggerFactory.getLogger(OrderRecordController.class);

    private static final String prefix = "order";

    @Autowired
    private OrderRecordService recordService;

    /**
     * 用户下单--消费者端
     */
    @RequestMapping(value = prefix + "/record/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> createOrder(@RequestBody RecordCreateRequest recordCreateRequest) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("code", 1);
        res.put("msg", "成功");
        try {
            log.info("接收到请求数据：{}", recordCreateRequest);
            recordService.createOrder(recordCreateRequest);
            recordService.createOrderByMe(recordCreateRequest);
            res.put("data", recordCreateRequest.toString());
        } catch (Exception e) {
            log.error("用户下单发生异常：{}", e.fillInStackTrace());
            res.put("code", -1);
            res.put("msg", "失败");
        }
        return res;
    }

}
