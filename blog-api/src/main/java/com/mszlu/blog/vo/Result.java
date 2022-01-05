package com.mszlu.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;

/**
 * @author by away
 * @date 2022/1/5 13:21
 */
@AllArgsConstructor
@Data
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;


    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false,code,msg,null);
    }
}
