package com.dubbo.two.server.request;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

@ToString
@Data
public class RecordCreateRequest implements Serializable {

    private Integer itemId;//商品id

    private Integer total;//下单数量

    private String customerName;//客户姓名

}
