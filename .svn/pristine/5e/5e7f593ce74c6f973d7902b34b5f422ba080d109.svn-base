package com.zx.moa.ioa.systemmanage.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.util.AudioChange;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.platform.syscontext.PlatformGlobalVar;

/**
 * @ClassName: NozzleController
 * @Description: 内容摘要：MOA对外方法，无需登录
 * @author sunlq
 * @date 2016年10月26日
 * @version V1.0 history: 1、2016年10月26日 sunlq 创建文件
 */
@RequestMapping("/nozzle")
@Controller
public class NozzleController
{

    /**
     * @Title: getAudioMP3
     * @Description: 获取语音,转换成MP3格式返回
     * @param path
     * @param request
     * @param response
     * @return
     * @author: sunlq
     * @time:2016年10月26日 上午11:31:16 history: 1、2016年10月26日 sunlq 创建方法
     */
    @RequestMapping(value = "/getAudioMP3.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> getAudioMP3(@RequestParam(required = true)
    String path, HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String path2 = "/moaMP3" + path.substring(path.indexOf("/", 2)).replaceAll("\\.(?i)(amr|wav|ogg)$", ".mp3");
            String targetPath = PlatformGlobalVar.SYS_PROPERTIES.get("realmoaupload") + path2;

            File file = new File(targetPath);
            if (!file.exists())
            {
                String sourcePath = PlatformGlobalVar.SYS_PROPERTIES.get("realmoaupload") + path;
                AudioChange.changeToMp3(sourcePath, targetPath);
            }

            /*String[] moaUrl = PlatformGlobalVar.SYS_PROPERTIES.get("moaUrl").split(":");
            if (moaUrl.length == 3)
            {
                path2 = moaUrl[0] + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + moaUrl[2] + path2;
            }
            else if (moaUrl.length == 2)
            {
                moaUrl[1] = moaUrl[1].substring(2);
                moaUrl[1] = moaUrl[1].substring(moaUrl[1].indexOf("/"));
                path2 = moaUrl[0] + "://" + InetAddress.getLocalHost().getHostAddress() + moaUrl[1] + path2;
            }
            else
            {
                path2 = PlatformGlobalVar.SYS_PROPERTIES.get("moaUrl") + path2;
            }*/

            return ResultHelper.getSuccess(path2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultHelper.getError(e.getMessage(), null);
        }
    }

}
