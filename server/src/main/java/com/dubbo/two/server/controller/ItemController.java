package com.dubbo.two.server.controller;

import com.dubbo.one.api.response.BaseResponse;
import com.dubbo.one.api.service.ItemService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    private static final String prefix = "item";

    @Autowired
    private ItemService itemService;

    /**
     * 消费者列表查询
     */
    @RequestMapping(value = prefix + "/list", method = RequestMethod.GET)
    public Map<String, Object> list() {
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("code", "0");
        resMap.put("msg", "成功");
        try {
            BaseResponse response = itemService.listItems();
            if (response != null && response.getCode().equals(0)) {
                resMap.put("data", response.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("code", "-1");
            resMap.put("msg", "失败");
        }
        return resMap;
    }

    /**
     * 消费者分页查询
     */
    @RequestMapping(value = prefix + "/page/list", method = RequestMethod.GET)
    public Map<String, Object> pageList(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageSize == null || pageNo <= 0 || pageSize <= 0) {
            pageNo = 1;
            pageSize = 2;
        }
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("code", "0");
        resMap.put("msg", "成功");
        try {
            BaseResponse response = itemService.listPageItems(pageNo, pageSize);
            if (response != null && response.getCode().equals(0)) {
                resMap.put("data", response.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("code", "-1");
            resMap.put("msg", "失败");
        }
        return resMap;
    }

    /**
     * 消费者分页模糊查询
     */
    @RequestMapping(value = prefix + "/page/list/params", method = RequestMethod.GET)
    public Map<String, Object> pageListByParam(Integer pageNo, Integer pageSize, String name) {
        if (pageNo == null || pageSize == null || pageNo <= 0 || pageSize <= 0) {
            pageNo = 1;
            pageSize = 2;
        }
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("code", "0");
        resMap.put("msg", "成功");
        try {
            BaseResponse response = itemService.listPageByParams(pageNo, pageSize, name);
            if (response != null && response.getCode().equals(0)) {
                resMap.put("data", response.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("code", "-1");
            resMap.put("msg", "失败");
        }
        return resMap;
    }

}
