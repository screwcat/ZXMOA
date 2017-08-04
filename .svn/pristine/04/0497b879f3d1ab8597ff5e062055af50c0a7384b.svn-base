package com.zx.moa.util;

import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.bean.ResultList;

/**
 * 返回结果编码常量类
 * 
 * @author MATF
 */
public class ResultHelper
{

    /**
     * 请求成功CODE
     */
    public final static String RET_SUCCESS = "000";// 请求成功!

    /**
     * 请求异常CODE
     */
    public final static String RET_ERROR = "002";// 操作失败，请联系客服!

    /**
     * 网络异常CODE
     */
    public final static String RET_NETWORK_ERROR = "001"; // 网络连接异常，请稍后重试!

    public final static String RET_CODE_NOTFOUND = "302";// 此帐号不存在

    public final static String RET_PWD_ERROR = "303"; // 密码错误

    /**
     * 登录账号密码
     */
    public final static String RET_CODE_BLANK = "300"; // 登录帐号为空

    public final static String RET_PWD_BLANK = "301"; // 登录密码为空

    /**
     * 其他异常
     */
    public final static String RET_OTHER_LOGIN = "998"; // 您的帐号可能在其他设备登录，如非本人操作请去ESM修改密码，再重新登录!

    public final static String RET_SESSION_TIMEOUT = "999"; // 登录超时,请重新登录!

    /**
     * 请求成功返回数据
     * 
     * @param t 返回数据
     * @return
     */
    public static <T> ResultBean<T> getSuccess(T t)
    {
        return new ResultBean<T>(RET_SUCCESS, "操作成功!", t);
    }

    public static <T> ResultBean<T> getSuccess(T t, String msg)
    {
        return new ResultBean<T>(RET_SUCCESS, msg, t);
    }

    /**
     * 请求成功返回数据（列表）
     * 
     * @param t 返回数据
     * @param count 数量
     * @return
     */
    public static <T> ResultBean<T> getSuccess(T t, Integer count)
    {
        return new ResultList<T>(RET_SUCCESS, "请求成功!", t, count);
    }

    /**
     * 请求错误返回数据
     * 
     * @param t 返回数据
     * @return
     */
    public static <T> ResultBean<T> getError(T t)
    {
        return new ResultBean<T>(RET_ERROR, "操作失败，请联系客服!", t);
    }

    /**
     * 请求错误返回数据
     * 
     * @param t 返回数据
     * @return
     */
    public static <T> ResultBean<T> getError(String msg, T t)
    {
        return new ResultBean<T>(RET_ERROR, msg, t);
    }
    
    /**
     * 请求错误返回数据
     * 
     * @param t 返回数据
     * @return
     */
    public static <T> ResultBean<T> getError(String code, String msg, T t)
    {
        return new ResultBean<T>(code, msg, t);
    }

    /**
     * 请求网络异常返回数据
     * 
     * @param t 返回数据
     * @return
     */
    public static <T> ResultBean<T> getNetworkError(T t)
    {
        return new ResultBean<T>(RET_NETWORK_ERROR, "网络连接异常，请稍后重试!", t);
    }

    /**
     * 帐号其他设备登录
     * 
     * @param t 返回数据
     * @return
     */
    public static <T> ResultBean<T> getOtherLoginError(T t)
    {
        // return new ResultBean<T>(RET_OTHER_LOGIN,
        // "您的帐号可能在其他设备登录，如非本人操作请去ESM修改密码，再重新登录!", t);
        return new ResultBean<T>(RET_OTHER_LOGIN, "请重新登录!", t);
    }

}
