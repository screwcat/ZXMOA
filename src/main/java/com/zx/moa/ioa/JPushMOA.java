package com.zx.moa.ioa;

import cn.jpush.api.common.ClientConfig;

import com.zx.moa.util.JPush;
import com.zx.platform.syscontext.PlatformGlobalVar;

public class JPushMOA extends JPush
{
    public JPushMOA()
    {
        super(PlatformGlobalVar.SYS_PROPERTIES.get("moa.appKey"),
              PlatformGlobalVar.SYS_PROPERTIES.get("moa.masterSecret"));
    }

    public JPushMOA(ClientConfig config)
    {
        super(PlatformGlobalVar.SYS_PROPERTIES.get("moa.appKey"),
              PlatformGlobalVar.SYS_PROPERTIES.get("moa.masterSecret"), config);
    }

    /**
     * 下边扩展各应用特殊的PushPayload
     */

}
