package com.example.gradesystem.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一接口返回结构。前端约定：code == 0 表示成功。
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, "ok", data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(0, "ok", null);
    }

    public static Result<Void> fail(String msg) {
        return new Result<>(1, msg, null);
    }
}
