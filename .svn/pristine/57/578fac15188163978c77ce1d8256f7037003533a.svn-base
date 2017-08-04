package com.zx.moa.util;

import java.util.Collection;

import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;

public class JPush
{
    private static final Logger LOG = LoggerFactory.getLogger(JPush.class);

    protected static final ObjectMapper mapper = new ObjectMapper().configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private String appKey;

    private String masterSecret;

    public String getAppKey()
    {
        return appKey;
    }

    public String getMasterSecret()
    {
        return masterSecret;
    }

    private JPushClient jpushClient;

    protected JPush(String appKey, String masterSecret)
    {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        ClientConfig config = ClientConfig.getInstance();
        config.setMaxRetryTimes(3);
        jpushClient = new JPushClient(masterSecret, appKey, null, config);
    }

    protected JPush(String appKey, String masterSecret, ClientConfig config)
    {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        jpushClient = new JPushClient(masterSecret, appKey, null, config);
    }

    public PushResult sendPush(PushPayload pushPayload) throws Exception
    {
        PushResult result = null;
        try
        {
            result = jpushClient.sendPush(pushPayload);
            LOG.info("Got result - " + result);
        }
        catch (APIConnectionException e)
        {
            LOG.error("Connection error. Should retry later. ", e);
            throw e;
        }
        catch (APIRequestException e)
        {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            throw e;
        }

        return result;
    }

    // 快捷地构建推送对象：所有平台，所有设备，内容为 alert 的通知。
    public PushPayload buildPushObject_all_all_alert(Object alert) throws Exception
    {
        String temp = null;
        if (alert instanceof String)
        {
            temp = alert.toString();
        }
        else
        {
            temp = mapper.writeValueAsString(alert);
        }

        return PushPayload.alertAll(temp);
    }

    // 快捷地构建推送对象：所有平台，所有设备，内容为 MSG_CONTENT 的消息。
    public PushPayload buildPushObject_all_all_message(Object msg_content) throws Exception
    {
        String temp = null;
        if (msg_content instanceof String)
        {
            temp = msg_content.toString();
        }
        else
        {
            temp = mapper.writeValueAsString(msg_content);
        }
        return PushPayload.messageAll(temp);
    }

    // 构建推送对象：所有平台，推送目标是别名为 alias，通知内容为 alert。
    public PushPayload buildPushObject_all_alias_alert(Object alert, String... alias)
    {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
                          .setNotification(Notification.alert(alert)).build();
    }

    // 构建推送对象：所有平台，推送目标是别名为 aliases，通知内容为 alert。
    public PushPayload buildPushObject_all_alias_alert(Object alert, Collection<String> aliases)
    {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(aliases))
                          .setNotification(Notification.alert(alert)).build();
    }

    // 构建推送对象：所有平台，推送目标是 alias，推送内容是 - 内容为 MSG_CONTENT 的消息。
    public PushPayload buildPushObject_all_alias_message(Object msg_content, String... alias) throws Exception
    {
        String temp = null;
        if (msg_content instanceof String)
        {
            temp = msg_content.toString();
        }
        else
        {
            temp = mapper.writeValueAsString(msg_content);
        }
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
                          .setMessage(Message.content(temp)).build();
    }

    // 构建推送对象：所有平台，推送目标是 aliases，推送内容是 - 内容为 MSG_CONTENT 的消息。
    public PushPayload buildPushObject_all_alias_message(Collection<String> aliases, Object msg_content)
                                                                                                        throws Exception
    {
        String temp = null;
        if (msg_content instanceof String)
        {
            temp = msg_content.toString();
        }
        else
        {
            temp = mapper.writeValueAsString(msg_content);
        }
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(aliases))
                          .setMessage(Message.content(temp)).build();
    }
}
