package com.example.gradesystem.common;

/**
 * 业务异常：参数校验失败、重复录入等可预期错误，统一返回给前端提示。
 */
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}
