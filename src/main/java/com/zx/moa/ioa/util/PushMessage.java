package com.zx.moa.ioa.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSONObject;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：MOA移动办公系统
 * @ClassName: PushMessage
 * 模块名称：推送消息实体类
 * @Description: 内容摘要：推送消息实体类
 * @author sunlq
 * @date 2016年12月28日
 * @version V1.0
 * history:
 * 1、2016年12月28日 sunlq 创建文件
 */
public class PushMessage
{

    /**
     * 推送平台 默认所有平台
     */
    public Platform platform = PMPlatform.PLATFORM_ANDROID_IOS;

    /**
     * 发送到 默认发送到所有用户
     */
    public PMAudience audience = new PMAudience();

    /**
     * 消息
     */
    public PMMessage message = new PMMessage();

    public PMOptions options = new PMOptions();

    public PushMessage()
    {

    }

    /**
     * 添加推送平台
     * 
     * @param platform
     * @return
     */
    public PushMessage setPlatform(Platform platform)
    {
        this.platform = platform;
        return this;
    }

    /**
     * 添加消息信息
     * 
     * @param message 消息信息
     * @return
     */
    public PushMessage setMessage(String message)
    {
        this.message.msg_content = message;
        return this;
    }

    /**
     * 添加消息信息
     * 
     * @param message 消息信息
     * @param title 消息标题
     * @return
     */
    public PushMessage setMessage(String message, String title)
    {
        this.message.msg_content = message;
        this.message.title = title;
        return this;
    }

    /**
     * @Title: setExtras
     * @Description: 设置消息信息附属信息
     * @param extras
     * @return 
     * @author: sunlq
     * @time:2016年12月28日 下午1:13:40
     * history:
     * 1、2016年12月28日 sunlq 创建方法
     */
    public PushMessage setExtras(Map<String, Object> extras)
    {
        Map<String, String> map = new HashMap<String, String>();
        if (extras != null && extras.size() > 0)
        {
            Iterator<String> it = extras.keySet().iterator();
            while (it.hasNext())
            {
                String key = it.next();
                Object value = extras.get(key);
                if (value instanceof String)
                {
                    map.put(key, (String) value);
                }
                else if (value instanceof Integer)
                {
                    map.put(key, value.toString());
                }
                else
                {
                    map.put(key, JSONObject.toJSONString(extras.get(key)));
                }
            }
            this.message.extras = map;
        }
        if (map.get("badge") == null)
        {
            map.put("badge", "1");
        }
        return this;
    }

    /**
     * 添加发送标签
     * 
     * @param tag 标签名称
     * @return
     */
    public PushMessage setAudienceTag(String tag)
    {
        this.audience.tag.add(tag);
        return this;
    }

    /**
     * 添加发送标签
     * 
     * @param tag 标签名称
     * @return
     */
    public PushMessage setAudienceTag(String... tag)
    {
        this.audience.tag.addAll(Arrays.asList(tag));
        return this;
    }

    /**
     * 添加发送标签
     * 
     * @param tag 标签名称集合
     * @return
     */
    public PushMessage setAudienceTag(List<String> tag)
    {
        this.audience.tag.addAll(tag);
        return this;
    }

    /**
     * 添加发送别名
     * 
     * @param alias 别名
     * @return
     */
    public PushMessage setAudienceAlias(String alias)
    {
        this.audience.alias.add(alias);
        return this;
    }

    /**
     * 添加发送别名
     * 
     * @param alias 别名
     * @return
     */
    public PushMessage setAudienceAlias(String... alias)
    {
        this.audience.alias.addAll(Arrays.asList(alias));
        return this;
    }

    /**
     * 添加消息和别名
     * 
     * @param msg 消息内容
     * @param alias 别名
     * @return
     */
    public PushMessage setMsgAlias(String msg, String... alias)
    {
        this.message.msg_content = msg;
        this.audience.alias.addAll(Arrays.asList(alias));
        return this;
    }

    /**
     * 添加别名
     * 
     * @param alias 别名集合
     * @return
     */
    public PushMessage setAudienceAlias(List<String> alias)
    {
        this.audience.alias.addAll(alias);
        return this;
    }

    /**
     * 添加消息和别名
     * 
     * @param msg 消息内容
     * @param alias 别名集合
     * @return
     */
    public PushMessage setMsgAlias(String msg, List<String> alias)
    {
        this.message.msg_content = msg;
        this.audience.alias.addAll(alias);
        return this;
    }

    /**
     * 常用发送设备
     * 
     * @author MATF
     */
    public interface PMPlatform
    {
        /**
         * 默认发送设备（全部）
         */
        final Platform PLATFORM_DEFAULT = Platform.all();

        /**
         * 只发送ANDROID设备
         */
        final Platform PLATFORM_ANDROID = Platform.android();

        /**
         * 只发送IOS设备
         */
        final Platform PLATFORM_IOS = Platform.ios();

        /**
         * 发送IOS,android
         */
        final Platform PLATFORM_ANDROID_IOS = Platform.android_ios();
    }

    /**
     * 发送到默认发送全部
     * 
     * @author MATF
     */
    public static class PMAudience
    {
        /**
         * 发送标签OR
         */
        public List<String> tag = new ArrayList<String>();

        /**
         * 发送标签AND
         */
        public List<String> tag_and = new ArrayList<String>();

        /**
         * 发送别名 每个设备有唯一别名 现在使用的是登录时服务端返回的UUID
         */
        public List<String> alias = new ArrayList<String>();

        /**
         * 发送注册ID 客户端注册JPUSH服务器后返回客户端的唯一标识
         */
        public List<String> registration_id = new ArrayList<String>();

        /**
         * 创建发送者 如果没有填写将发送全部
         * 
         * @return
         */
        public Audience Builde()
        {
            boolean isAll = true;
            cn.jpush.api.push.model.audience.Audience.Builder builder = Audience.newBuilder();
            if (tag != null && tag.size() > 0)
            {
                builder.addAudienceTarget(AudienceTarget.tag(tag));
                isAll = false;
            }
            if (tag_and != null && tag_and.size() > 0)
            {
                builder.addAudienceTarget(AudienceTarget.tag_and(tag_and));
                isAll = false;
            }
            if (alias != null && alias.size() > 0)
            {
                builder.addAudienceTarget(AudienceTarget.alias(alias));
                isAll = false;
            }
            if (registration_id != null && registration_id.size() > 0)
            {
                builder.addAudienceTarget(AudienceTarget.registrationId(registration_id));
                isAll = false;
            }
            if (isAll)
            {
                return Audience.all();
            }
            return builder.build();
        }
    }

    /**
     * 发送消息
     * 
     * @author MATF
     */
    public static class PMMessage
    {
        /**
         * 消息内容必填项
         */
        public String msg_content;

        /**
         * 发送标题
         */
        public String title;

        /**
         * 消息类型
         */
        public String content_type;

        /**
         * JSON附加数据 用于透传给APP做处理使用
         */
        public Map<String, String> extras;

        /**
         * 创建消息 消息内容为空将抛出异常
         * 
         * @return
         */
        public Message builde()
        {
            cn.jpush.api.push.model.Message.Builder builder = Message.newBuilder();
            if (StringUtil.isBlank(msg_content))
            {
                throw new RuntimeException("推送消息内容不能为空!");
            }
            else
            {
                builder.setMsgContent(msg_content);
            }
            if (StringUtil.isNotBlank(title))
            {
                builder.setTitle(title);
            }
            if (StringUtil.isNotBlank(content_type))
            {
                builder.setContentType(content_type);
            }
            if (extras != null && extras.size() > 0)
            {
                builder.addExtras(extras);
            }
            return builder.build();
        }
    }

    /**
     * 可选参数
     * 
     * @author MATF
     */
    public class PMOptions
    {
        public Integer sendno;

        public Long time_to_live;

        public Long override_msg_id;

        public boolean apns_production = false;

        public Integer big_push_duration;
        
        public Options build()
        {
             Integer sendno2 = sendno;

             Long time_to_live2 = time_to_live;

             Long override_msg_id2 = override_msg_id;

             boolean apns_production2 = apns_production;

             Integer big_push_duration2 = big_push_duration;
            
            Options.Builder builder = Options.newBuilder();
            builder.setApnsProduction(apns_production2);
            if (sendno2 != null)
            {
                builder.setSendno(sendno2);
            }
            if (time_to_live2 != null)
            {
                builder.setTimeToLive(time_to_live2);
            }
            if (override_msg_id2 != null)
            {
                builder.setOverrideMsgId(override_msg_id2);
            }
            if (big_push_duration2 != null)
            {
                builder.setBigPushDuration(big_push_duration2);
            }
            return builder.build();
        }

    }

    /**
     * 创建推送信息
     * 
     * @return
     */
    public PushPayload buildMessage()
    {
        Builder builder = PushPayload.newBuilder();
        if (platform != null)
        {
            builder.setPlatform(platform);
        }
        else
        {
            builder.setPlatform(Platform.all());
        }
        if (audience != null)
        {
            builder.setAudience(audience.Builde());
        }
        if (message != null)
        {
           /* String msg_content = message.msg_content;
            if (msg_content != null && !msg_content.equals(""))
            {
                if (this.message.extras == null)
                {
                    message.extras = new HashMap<String, String>();
                    message.extras.put("reminder", msg_content);
                }
                else
                {
                    String reminder = message.extras.get("reminder");
                    if (reminder == null || reminder.equals(""))
                    {
                        message.extras.put("reminder", msg_content);
                    }
                }
            }*/
            builder.setMessage(message.builde());
        }
        if (options != null)
        {
            builder.setOptions(options.build());
        }

        return builder.build();
    }

    /**
     * @Title: buildIOSNotification
     * @Description: IOS发通知
     * @return 
     * @author: sunlq
     * @time:2016年12月28日 下午1:14:26
     * history:
     * 1、2016年12月28日 sunlq 创建方法
     */
    public PushPayload buildIOSNotification()
    {
        Builder builder = PushPayload.newBuilder();
        builder.setPlatform(Platform.ios());
        if (audience != null)
        {
            builder.setAudience(audience.Builde());
        }
        if (options != null)
        {
            builder.setOptions(options.build());
        }
        builder.setNotification(Notification.newBuilder()
                                            .addPlatformNotification(IosNotification.newBuilder()
                                                                                    .setContentAvailable(true)
                                                                                    .addExtras(message.extras)
                                                                                    .setAlert(message.msg_content)
.setBadge(Integer.parseInt(message.extras.get("badge").toString())).build()).build());
        return builder.build();
    }

    public void push()
    {
        PushManage.pushMessage(this, PushManage.DEF_APP_NAME);
    }
}
