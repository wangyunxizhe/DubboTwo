package com.dubbo.two.server.data;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RecordResponse implements Serializable{

    private Integer code;

    private String msg;

    private Integer data;
}