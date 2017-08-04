package com.zx.moa.ioa.task.web;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zx.moa.ioa.task.persist.ReleaseTaskDao;
import com.zx.moa.ioa.task.service.IReleaseTaskMapperService;
import com.zx.moa.ioa.task.service.ITaskMessageService;
import com.zx.moa.ioa.util.PushConstant;
import com.zx.moa.ioa.util.PushManage;
import com.zx.moa.util.DateUtil;
import com.zx.moa.util.GlobalVal;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;
import com.zx.platform.syscontext.PlatformGlobalVar;
import com.zx.platform.syscontext.util.StringUtil;

@Controller
public class ReleaseTaskMapperController
{

    private HttpServletRequest request = null;

    @Autowired
    private IReleaseTaskMapperService releaseTaskMapperServiceImpl;

    @Autowired
    private ReleaseTaskDao releaseTaskDao;

    @Autowired
    private ITaskMessageService taskMessageService;

    /*
     * 发送modi请求
     */
    private Map<String, Object> getModiConnection(String interfaceNumber, Map<String, Object> map) throws ClientProtocolException
    {

        String url = HttpClientUtil.getSysUrl("nozzleUrl");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("interface_num", interfaceNumber));
        nvps.add(new BasicNameValuePair("sys_num", PushConstant.TASK_APP_NAME));
        String releaseTask = JSONObject.toJSONString(map);

        nvps.add(new BasicNameValuePair("releaseTask", releaseTask));
        nvps.add(new BasicNameValuePair("user_id", "0"));

        Map<String, Object> resmap = new HashMap<String, Object>();
        try
        {
            resmap = HttpClientUtil.post(url, nvps, Map.class);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            resmap.put("message", e.getMessage());
        }
        return resmap;
    }

    // 非空验证
    private String isNotBlank(String requestname)
    {
        String res;
        if (StringUtil.isNotBlank(request.getParameter(requestname)))
        {
            res = request.getParameter(requestname);
            return res;
        }
        else
        {
            return null;
        }

    }

    /**
     * @Title: logout
     * @Description: TODO(选择发布/指派/转移/协同人接口)
     * @param requestinfo
     * @return 
     * @author: zhangmingjian
     * @time:2016年12月9日 下午2:48:24
     * history:
     * 1、2016年12月9日 Lixiaolong 修改方法
     */
    @RequestMapping(value = "/ioa/getTaskUserList.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> logout(HttpServletRequest requestinfo) throws Exception
    {

        String version = requestinfo.getParameter("v");
        if (null == version)
        {
            // -----------------老版本-----------------------
            PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);
            request = requestinfo;
            Map<String, Object> resultMap = new HashMap<String, Object>();

            String page = "", page_size = "", page_type = "", personnel_name = "";
            if (null != request.getParameter("page_size"))
            {
                page = request.getParameter("page_size");
                if (Integer.parseInt(page) < 0)
                {
                    ResultHelper.getError("page_size不能小于0", String.class);
                }
            }
            else
            {
                page = "1";
            }
            if (null != request.getParameter("page"))
            {
                page = request.getParameter("page");
                if (Integer.parseInt(page) < 0)
                {
                    ResultHelper.getError("page不能小于0", String.class);
                }
            }
            else
            {
                page_size = "10";
            }
            page_size = request.getParameter("size");
            page_type = request.getParameter("type");

            personnel_name = request.getParameter("search");
            // 查询参数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", StringUtil.isNotBlank(page) ? Integer.parseInt(page) : page);
            map.put("pagesize", StringUtil.isNotBlank(page_size) ? Integer.parseInt(page_size) : page_size);
            map.put("personnel_id", pm.getPersonnel_id());
            map.put("search", personnel_name);
            map.put("dept_id", pm.getPersonnel_deptid());

            Map<String, Object> self = new HashMap<String, Object>();
            self.put("personnel_id", pm.getPersonnel_id());
            self.put("personnel_name", pm.getPersonnel_name());
            self.put("personnel_shortcode", pm.getPersonnel_shortcode());
            self.put("personnel_deptname", pm.getPersonnel_deptname());
            self.put("parent_deptname", pm.getParentDept());
            self.put("personnel_postname", pm.getPersonnel_postname());
            self.put("personnel_sex", pm.getPersonnel_sex());
            if (StringUtil.isNotBlank(personnel_name))
            {
                // 查询人员
                resultMap.put("search", releaseTaskMapperServiceImpl.selectPersonnelInfoByNameAndCode(map));

            }
            else
            {

                // 所属部门信息
                resultMap.put("dept", releaseTaskMapperServiceImpl.selectPersonnelInfoByDept(map));
            }
            if (("2").equals(page_type))
            {
                // 查询默认发布人
                map.put("statistic_type", 1);

                if (releaseTaskMapperServiceImpl.selectReleasePersonnelById(map).size() > 0)
                {
                    resultMap.put("def_publish", releaseTaskMapperServiceImpl.selectReleasePersonnelById(map).get(0));
                }
                else
                {

                    resultMap.put("def_publish", self);
                }
            }
            // 常用人信息
            map.put("statistic_type", page_type);
            List<Map<String, Object>> often = releaseTaskMapperServiceImpl.selectReleasePersonnelById(map);

            if (!("4").equals(map.get("statistic_type").toString()) && !("5").equals(map.get("statistic_type").toString()))
            {
                often.add(0, self);
            }
            resultMap.put("often", often);

            return ResultHelper.getSuccess(resultMap);
            // -----------------老版本-----------------------
        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(version, "1.1.7") >= 0)
        {
            // -----------------1.1.7-----------------------
            PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);
            request = requestinfo;
            Map<String, Object> resultMap = new HashMap<String, Object>();

            String page = "", page_size = "", page_type = "", personnel_name = "";
            if (null != request.getParameter("page_size"))
            {
                page = request.getParameter("page_size");
                if (Integer.parseInt(page) < 0)
                {
                    ResultHelper.getError("page_size不能小于0", String.class);
                }
            }
            else
            {
                page = "1";
            }
            if (null != request.getParameter("page"))
            {
                page = request.getParameter("page");
                if (Integer.parseInt(page) < 0)
                {
                    ResultHelper.getError("page不能小于0", String.class);
                }
            }
            else
            {
                page_size = "10";
            }
            page_size = request.getParameter("size");
            page_type = request.getParameter("type");

            personnel_name = request.getParameter("search");
            // 查询参数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", StringUtil.isNotBlank(page) ? Integer.parseInt(page) : page);
            map.put("pagesize", StringUtil.isNotBlank(page_size) ? Integer.parseInt(page_size) : page_size);
            map.put("personnel_id", pm.getPersonnel_id());
            map.put("search", personnel_name);
            map.put("dept_id", pm.getPersonnel_deptid());

            Map<String, Object> self = new HashMap<String, Object>();
            self.put("personnel_id", pm.getPersonnel_id());
            self.put("personnel_name", pm.getPersonnel_name());
            self.put("personnel_shortcode", pm.getPersonnel_shortcode());
            self.put("personnel_deptname", pm.getPersonnel_deptname());
            self.put("parent_deptname", pm.getParentDept());
            self.put("personnel_postname", pm.getPersonnel_postname());
            self.put("personnel_sex", pm.getPersonnel_sex());
            if (StringUtil.isNotBlank(personnel_name))
            {
                // 查询人员
                resultMap.put("search", releaseTaskMapperServiceImpl.selectPersonnelInfoByNameAndCode(map));

            }
            else
            {
                if (!("3").equals(page_type) && !("5").equals(page_type))
                {
                    // 所属部门信息
                    resultMap.put("dept", releaseTaskMapperServiceImpl.selectPersonnelInfoByDept(map));
                }

            }
            if (("2").equals(page_type))
            {
                // 查询默认发布人
                map.put("statistic_type", 1);

                if (releaseTaskMapperServiceImpl.selectReleasePersonnelById(map).size() > 0)
                {
                    resultMap.put("def_publish", releaseTaskMapperServiceImpl.selectReleasePersonnelById(map).get(0));
                }
                else
                {

                    resultMap.put("def_publish", self);
                }
            }
            // 2发布人、3指派、5 协同 新增返回分组
            if (("2").equals(page_type) || ("3").equals(page_type) || ("5").equals(page_type))
            {
                Map<String, Object> searchmap = new HashMap<>();
                searchmap.put("personnel_id", pm.getPersonnel_id());
                searchmap.put("page", StringUtil.isNotBlank(page) ? Integer.parseInt(page) : page);
                searchmap.put("pagesize", StringUtil.isNotBlank(page_size) ? Integer.parseInt(page_size) : page_size);
                List<Map<String, Object>> group_info = releaseTaskMapperServiceImpl.getGroupInfo(searchmap);
                resultMap.put("group_info", group_info);
            }

            // 常用人信息
            map.put("statistic_type", page_type);
            List<Map<String, Object>> often = releaseTaskMapperServiceImpl.selectReleasePersonnelById(map);

            if (!("4").equals(map.get("statistic_type").toString()) && !("5").equals(map.get("statistic_type").toString()))
            {
                often.add(0, self);
            }
            resultMap.put("often", often);

            return ResultHelper.getSuccess(resultMap);

            // -----------------1.1.7-----------------------
        }
        return null;

    }

    /**
     * @Title: getTaskInfo
     * @Description: TODO(任务详情)
     * @param requestinfo
     * @return 
     * @author: zhangmingjian
     * @throws Exception 
     * @time:2016年4月29日 上午10:42:23
     * history:
     * 1、2016年12月9日 Lixiaolong 修改方法
     */
    @RequestMapping(value = "/ioa/getTaskInfo.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getTaskInfo(HttpServletRequest requestinfo) throws Exception
    {
        request = requestinfo;

        String version = request.getParameter("v");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("version", version);
        if (null == version)
        {
            // -------------老版本--------------------------
            String task_id = isNotBlank("task_id");

            map.put("task_id", task_id);
            Map<String, Object> resmap = releaseTaskMapperServiceImpl.selectTaskById(map);
            resmap.remove("task_num");
            return ResultHelper.getSuccess(resmap);

            // -------------老版本--------------------------
        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(version, "1.1.7") >= 0)
        {
            // ------------1.1.7--------------------------
            String task_id = isNotBlank("task_id");

            map.put("task_id", task_id);
            Map<String, Object> resmap = releaseTaskMapperServiceImpl.selectTaskByIdfor117(map);
            resmap.remove("task_num");
            return ResultHelper.getSuccess(resmap);

            // ------------1.1.7--------------------------
        }
        return null;

    }

    /*
     * 修改默认人
     */
    @RequestMapping(value = "/ioa/updateDefPublishUser.do", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> updateDefPublishUser(HttpServletRequest requestinfo)
    {
        PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);
        request = requestinfo;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("login_personnel_id", pm.getPersonnel_id());
        map.put("def_personnel_id", isNotBlank("personnel_id"));
        String message = "";
        try
        {
            Map<String, Object> resmap = getModiConnection("IOA_OUT_006", map);
            Boolean bl = (Boolean) resmap.get("flag");

            if (bl)
            {
                return ResultHelper.getSuccess(null);
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
            message = e.getMessage();
        }
        ResultBean<Map<String, Object>> re = new ResultBean<Map<String, Object>>();
        re.setRet_msg(message);
        return ResultHelper.getError(null);

    }

    @RequestMapping(value = "/moa/uploadFile.do", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public ResultBean<String> uploadFile(@RequestParam(value = "imgFile")
    MultipartFile imgFile, HttpServletRequest requestinfo)
    {

        PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);
        String url = "";
        try
        {

            url = saveUploadFile(imgFile.getInputStream(), pm.getPersonnel_shortcode(), imgFile.getOriginalFilename());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (StringUtil.isNotBlank(url))
        {
            return ResultHelper.getSuccess(url);
        }
        else
        {
            return ResultHelper.getError(url);
        }
    }

    /*
     * @Title: saveUploadFile
     * 
     * @Description: 上传文件
     * 
     * @param @param fileStream 上传的文件流
     * 
     * @param @param shortcode 当前用户的shortcode
     * 
     * @param @param srcFileName 上传文件名
     * 
     * @param @return
     * 
     * @param @throws IOException
     * 
     * @return String
     * 
     * @throws
     * 
     * @author zhangmingjian
     * 
     * @date 2016年4月25日 上午10:48:26
     */

    public static String saveUploadFile(InputStream fileStream, String shortcode, String srcFileName) throws IOException
    {

        String catalog = DateUtil.date2String(new Date(), "yyyyMM");
        String postfix = "";
        int random = (int) (Math.random() * 1000 / 1);
        if (srcFileName.lastIndexOf(".") > -1)
        {
            postfix = srcFileName.substring(srcFileName.lastIndexOf(".")).toLowerCase();
        }
        else
        {
            return "未知文件名";
        }
        String typename = "";
        String fileName = shortcode + "_" + System.currentTimeMillis() + "_" + random;
        String picture[] = { ".jpg", ".png", ".gif", ".bmp", ".JPEG", ".tiff", ".psd", ".png", ".pcx", ".dxf", ".wmf", ".emf", ".lic", ".eps", ".tga" };
        String voice[] = { ".mp3", ".wma", ".ogg", ".AAC", ".AMR-NB", ".WAV", ".amr", ".MIDI", ".ALAC", ".A-law", ".IMA4" };
        String realPath = "";
        Boolean fl = true;

        for (String str : voice)
        {
            if (str.equalsIgnoreCase(postfix))
            {
                typename = "moaVoice";
                fl = false;
                break;
            }
        }
        if (fl)
        {
            for (String str : picture)
            {
                if (str.equalsIgnoreCase(postfix))
                {
                    typename = "moaPicture";
                    fl = false;
                    break;
                }
            }
        }
        if (fl)
        {
            typename = "moaOther";
            fl = false;
        }

        int old_width = 0;
        int old_height = 0;
        Image img = null;
        // 原图的流
        InputStream stream1 = null;
        // 压缩图片的流
        InputStream stream2 = null;
        if (typename.equals("moaPicture"))
        {
            // 将文件流缓存到内存中
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileStream.read(buffer)) > -1)
            {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            InputStream stream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            stream1 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            stream2 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            img = ImageIO.read(stream);
            // 处理图片宽或高超出200后将图片压缩
            old_width = img.getWidth(null);
            old_height = img.getHeight(null);
        }
        else
        {
            stream2 = fileStream;
        }

        realPath = PlatformGlobalVar.SYS_PROPERTIES.get("realmoaupload") + "/" + typename + "/" + catalog;
        if (StringUtil.isBlank(realPath))
        {
            return null;
        }

        File dir = new File(realPath);

        String imageUrl = realPath + "/" + fileName + postfix;
        String url = "";
        File file = new File(imageUrl);
        File parentDir = file.getParentFile();
        if (!parentDir.exists())
        {
            parentDir.mkdirs();
        }
        if (!file.exists())
        {
            file.createNewFile();
            // file.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(stream2);
        byte[] cacheArray = new byte[2048];
        int size;
        while ((size = bis.read(cacheArray)) != -1)
        {
            fout.write(cacheArray, 0, size);
        }
        fout.close();
        bis.close();

        url = "/" + typename + "/" + catalog + "/" + fileName + postfix;

        if (typename.equalsIgnoreCase("moaPicture"))
        {
            int new_width = -1;
            int moaPicture = -1;
            int new_height = -1;
            String imgUrl = "";

            if (old_width > 200 || old_height > 200)
            {
                if (old_width >= old_height)
                {
                    new_width = 200;
                    new_height = (int) 200d * old_height / old_width;
                }
                else
                {
                    new_height = 200;
                    new_width = (int) 200d * old_width / old_height;
                }
            }
            else
            {
                new_width = old_width;
                new_height = old_height;
            }

            url = url + "?filename=" + typename + "Mini";
            typename = typename + "Mini";
            imageUrl = PlatformGlobalVar.SYS_PROPERTIES.get("realmoaupload") + "/" + typename + "/" + catalog + "/" + fileName + postfix;
            System.out.println(imageUrl);
            if (new_width != -1 && new_height != -1)
            {
                imgUrl = zipImageFile(stream1, new_width, new_height, 1f, imageUrl, img);
            }

        }
        return url;
    }

    /**
     * 直接指定压缩后的宽高： (先保存原文件，再压缩、上传) 壹拍项目中用于二维码压缩
     * 
     * @param oldFile
     *            要进行压缩的文件全路径
     * @param width
     *            压缩后的宽度
     * @param height
     *            压缩后的高度
     * @param quality
     *            压缩质量
     * @param smallIcon
     *            文件名的小小后缀(注意，非文件后缀名称),入压缩文件名是yasuo.jpg,则压缩后文件名是yasuo(+smallIcon
     *            ).jpg
     * @return 返回压缩后的文件的全路径
     */
    private static String zipImageFile(InputStream fileStream, int width, int height, float quality, String smallIcon, Image img)
    {

        if (fileStream == null)
        {
            return null;
        }
        String newImage = null;
        try
        {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = img;
            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(img.getScaledInstance(width, height, Image.SCALE_REPLICATE), 0, 0, null);

            // tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            // String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            // /** 压缩后的文件名 */
            // newImage = filePrex + smallIcon +
            // oldFile.substring(filePrex.length());

            File file = new File(smallIcon);
            File parentDir = file.getParentFile();
            if (!parentDir.exists())
            {
                parentDir.mkdirs();
            }
            if (!file.exists())
            {
                file.createNewFile();
                // file.createNewFile();
            }

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(file);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return newImage;
    }

    /**
    * @Title: createTask
    * @Description: TODO(新建任务)
    * @param requestinfo
    * @return ResultBean<Map<String,Object>>
    * @author: zhangmingjian
     * @throws Exception 
     * @throws NumberFormatException 
    * @time:2016年12月9日 下午2:40:37
    * history:
    * 1、2016年12月9日 Lixiaolong 修改方法
    */
    @RequestMapping(value = "/ioa/createTask.do", method = { RequestMethod.POST })
    @ResponseBody
    public synchronized ResultBean<Map<String, Object>> createTask(HttpServletRequest requestinfo) throws NumberFormatException, Exception
    {

        String version = requestinfo.getParameter("v");
        if (null == version)
        {
            // --------------------------版本号为空走老版本-------------------------------------
            String errInfo = "";
            request = requestinfo;
            String name[] = { "task_title", "publish_user_id", "publish_user_code", "publish_user_name", "finish_time", "feedback_cycle", "remind_time", "task_pid", "feedback", "info" };
            HashMap<String, Object> map = new HashMap<String, Object>();

            for (int i = 0; i < name.length; i++)
            {
                map.put(name[i], isNotBlank(name[i]));
            }

            if (0 != Integer.parseInt(map.get("task_pid").toString()))
            {
                Map<String, Object> taskmap = new HashMap<String, Object>();
                taskmap.put("task_id", map.get("task_pid"));
                taskmap = releaseTaskDao.selectTaskByPid(taskmap);
                if (1 != Integer.parseInt(taskmap.get("task_status").toString()))
                {
                    String info = "指派任务失败,该任务的父任务不是进行中的任务";
                    taskmap.clear();
                    taskmap.put("message", info);
                    return ResultHelper.getError(taskmap);
                }
            }

            PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);
            map.put("login_personnel_id", pm.getPersonnel_id());

            try
            {
                Map<String, Object> resmap = (HashMap<String, Object>) getModiConnection("IOA_OUT_005", map);
                ObjectMapper om = new ObjectMapper();
                if ((Boolean) resmap.get("flag"))
                {
                    Map<String, Object> sumap = new HashMap<String, Object>();
                    HashMap<String, Object> msgMap = new HashMap<String, Object>();
                    HashMap<String, Object> extras = new HashMap<String, Object>();
                    HashMap<String, Object> temp = new HashMap<String, Object>();

                    sumap.put("message", resmap.get("ioainfo").toString());
                    if (StringUtil.isNotBlank(resmap.get("ioainfo").toString()))
                    {

                        try
                        {
                            List<Map<String, Object>> messageMap = om.readValue(resmap.get("ioainfo").toString(), new TypeReference<List<Map<String, Object>>>()
                            {
                            });
                            if (messageMap.size() > 0)
                            {
                                temp.putAll(messageMap.get(0));
                            }

                        }
                        catch (JsonParseException e1)
                        {
                            e1.printStackTrace();
                        }
                        catch (JsonMappingException e1)
                        {
                            e1.printStackTrace();
                        }
                        catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    }

                    String id[] = resmap.get("task_id").toString().split(",");

                    // 处理根任务时推送的消息
                    List<Map<String, Object>> infolist;
                    try
                    {
                        infolist = om.readValue(map.get("feedback").toString(), new TypeReference<List<Map<String, Object>>>()
                        {
                        });

                        extras.clear();
                        extras.putAll(temp);
                        // 如果是子任务向接受反馈人推送消息
                        if (!map.get("task_pid").toString().equals("0"))
                        {
                            Map<String, Object> taskmap = new HashMap<String, Object>();
                            taskmap.put("task_id", map.get("task_pid").toString());
                            taskmap = releaseTaskDao.selectTaskByPid(taskmap);
                            extras.put("task_id", map.get("task_pid"));
                            msgMap.clear();

                            msgMap.put("accept_feedback_name", pm.getPersonnel_name() + pm.getPersonnel_shortcode());

                            StringBuffer sf = new StringBuffer();
                            // 拼接反馈人姓名和工号
                            for (int i = 0; i < infolist.size(); i++)
                            {
                                sf.append(infolist.get(i).get("feedback_user_name").toString() + infolist.get(i).get("feedback_user_code").toString());
                                sf.append(",");
                            }
                            msgMap.put("feedback_user_name", sf.substring(0, sf.length() - 1));

                            PushManage.pushMessageByCode(taskmap.get("accept_feedback_code").toString(), PushConstant.TASK_accept_feedback_NEW, msgMap, extras);
                            for (int i = 0; i < infolist.size(); i++)
                            {
                                // 向反馈人推送消息
                                extras.clear();
                                extras.putAll(temp);
                                extras.put("task_id", id[i]);
                                PushManage.pushMessageByCode(infolist.get(i).get("feedback_user_code").toString(), PushConstant.TASK_FEEDBACK_NEW, msgMap, extras);
                            }
                        }
                        else
                        {
                            for (int i = 0; i < infolist.size(); i++)
                            {
                                // 如果是发布任务推送消息
                                msgMap.clear();
                                msgMap.put("accept_feedback_name", map.get("publish_user_name").toString() + map.get("publish_user_code").toString());
                                extras.put("task_id", id[i]);
                                // 向反馈人推送消息
                                extras.clear();
                                extras.putAll(temp);
                                extras.put("task_id", id[i]);
                                PushManage.pushMessageByCode(infolist.get(i).get("feedback_user_code").toString(), PushConstant.TASK_FEEDBACK_NEW, msgMap, extras);
                            }

                        }

                        // 创建定时任务
                        Map<String, Object> jobmap = new HashMap<String, Object>();

                        for (int i = 0; i < id.length; i++)
                        {
                            jobmap.put("task_id", Integer.parseInt(id[i]));
                            jobmap.put("remind_time", map.get("remind_time"));
                            jobmap.put("finish_time", (map.get("finish_time").toString()));
                            taskMessageService.taskUnfinishedRemind(jobmap);
                        }

                        return ResultHelper.getSuccess(sumap);
                    }
                    catch (JsonParseException e)
                    {
                        e.printStackTrace();
                    }
                    catch (JsonMappingException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                    ResultBean<Map<String, Object>> rb = new ResultBean<Map<String, Object>>();
                    rb.setRet_code("002");
                    if (resmap.get("message") != null && !((String) resmap.get("message")).equals(""))
                    {
                        rb.setRet_msg((String) resmap.get("message"));
                    }
                    else
                    {
                        rb.setRet_msg("操作失败");
                    }

                    return rb;
                }
            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();
                errInfo = e.getMessage();
            }
            Map<String, Object> err = new HashMap<String, Object>();
            err.put("err", errInfo);
            return ResultHelper.getError(err);
            // --------------------------版本号为空走老版本-------------------------------------
        }
        else if (com.zx.moa.ioa.util.StringUtil.compareVersion(version, "1.1.7") >= 0)
        {
            // --------------------------版本号大于等于1.1.7走新版本-------------------------------------
            String errInfo = "";
            request = requestinfo;
            // 1.1.7版本新增任务类型id，任务类型名称
            String name[] = { "task_title", "publish_user_id", "publish_user_code", "publish_user_name", "finish_time", "feedback_cycle", "remind_time", "task_pid", "feedback", "info", "task_type_id", "task_type_name" };
            HashMap<String, Object> map = new HashMap<String, Object>();

            for (int i = 0; i < name.length; i++)
            {
                map.put(name[i], isNotBlank(name[i]));
            }
            // 如果类型为会议 或公告，则反馈周期设置为不限制0
            if ("2".equals(map.get("task_type_id").toString()) || "3".equals(map.get("task_type_id").toString()))
            {
                map.put("feedback_cycle", 0);
            }

            if (0 != Integer.parseInt(map.get("task_pid").toString()))
            {
                Map<String, Object> taskmap = new HashMap<String, Object>();
                taskmap.put("task_id", map.get("task_pid"));
                taskmap = releaseTaskDao.selectTaskByPid(taskmap);
                if (1 != Integer.parseInt(taskmap.get("task_status").toString()))
                {
                    String info = "指派任务失败,该任务的父任务不是进行中的任务";
                    taskmap.clear();
                    taskmap.put("message", info);
                    return ResultHelper.getError(taskmap);
                }
            }

            PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);
            map.put("login_personnel_id", pm.getPersonnel_id());

            try
            {
                Map<String, Object> resmap = (HashMap<String, Object>) getModiConnection("IOA_OUT_005", map);
                ObjectMapper om = new ObjectMapper();
                if ((Boolean) resmap.get("flag"))
                {
                    Map<String, Object> sumap = new HashMap<String, Object>();
                    HashMap<String, Object> msgMap = new HashMap<String, Object>();
                    HashMap<String, Object> extras = new HashMap<String, Object>();
                    HashMap<String, Object> temp = new HashMap<String, Object>();

                    sumap.put("message", resmap.get("ioainfo").toString());
                    if (StringUtil.isNotBlank(resmap.get("ioainfo").toString()))
                    {

                        try
                        {
                            List<Map<String, Object>> messageMap = om.readValue(resmap.get("ioainfo").toString(), new TypeReference<List<Map<String, Object>>>()
                            {
                            });
                            if (messageMap.size() > 0)
                            {
                                temp.putAll(messageMap.get(0));
                            }

                        }
                        catch (JsonParseException e1)
                        {
                            e1.printStackTrace();
                        }
                        catch (JsonMappingException e1)
                        {
                            e1.printStackTrace();
                        }
                        catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    }

                    String id[] = resmap.get("task_id").toString().split(",");

                    // 处理根任务时推送的消息

                    List<Map<String, Object>> infolist;
                    try
                    {
                        infolist = om.readValue(map.get("feedback").toString(), new TypeReference<List<Map<String, Object>>>()
                        {
                        });

                        extras.clear();
                        extras.putAll(temp);
                        // 如果是子任务向接受反馈人推送消息
                        if (!map.get("task_pid").toString().equals("0"))
                        {
                            Map<String, Object> taskmap = new HashMap<String, Object>();
                            taskmap.put("task_id", map.get("task_pid").toString());
                            taskmap = releaseTaskDao.selectTaskByPid(taskmap);
                            extras.put("task_id", map.get("task_pid"));
                            msgMap.clear();

                            msgMap.put("accept_feedback_name", pm.getPersonnel_name() + pm.getPersonnel_shortcode());

                            StringBuffer sf = new StringBuffer();
                            // 拼接反馈人姓名和工号
                            for (int i = 0; i < infolist.size(); i++)
                            {
                                sf.append(infolist.get(i).get("feedback_user_name").toString() + infolist.get(i).get("feedback_user_code").toString());
                                sf.append(",");
                            }
                            msgMap.put("feedback_user_name", sf.substring(0, sf.length() - 1));

                            PushManage.pushMessageByCode(taskmap.get("accept_feedback_code").toString(), PushConstant.TASK_accept_feedback_NEW, msgMap, extras);
                            for (int i = 0; i < infolist.size(); i++)
                            {
                                // 向反馈人推送消息
                                extras.clear();
                                extras.putAll(temp);
                                extras.put("task_id", id[i]);
                                PushManage.pushMessageByCode(infolist.get(i).get("feedback_user_code").toString(), PushConstant.TASK_FEEDBACK_NEW, msgMap, extras);
                            }
                        }
                        else
                        {
                            for (int i = 0; i < infolist.size(); i++)
                            {
                                // 如果是发布任务推送消息
                                msgMap.clear();
                                msgMap.put("accept_feedback_name", map.get("publish_user_name").toString() + map.get("publish_user_code").toString());
                                extras.put("task_id", id[i]);
                                // 向反馈人推送消息
                                extras.clear();
                                extras.putAll(temp);
                                extras.put("task_id", id[i]);
                                PushManage.pushMessageByCode(infolist.get(i).get("feedback_user_code").toString(), PushConstant.TASK_FEEDBACK_NEW, msgMap, extras);
                            }

                        }

                        // 创建定时任务
                        Map<String, Object> jobmap = new HashMap<String, Object>();

                        for (int i = 0; i < id.length; i++)
                        {
                            jobmap.put("task_id", Integer.parseInt(id[i]));
                            jobmap.put("remind_time", map.get("remind_time"));
                            jobmap.put("finish_time", (map.get("finish_time").toString()));
                            int task_type_id = Integer.parseInt(map.get("task_type_id").toString());
                            if (task_type_id == 1)
                            {
                                taskMessageService.taskUnfinishedRemind(jobmap);
                            }
                            if (task_type_id == 2 || task_type_id == 3)
                            {
                                taskMessageService.taskUnfinishedOver(jobmap);
                            }
                        }

                        return ResultHelper.getSuccess(sumap);
                    }
                    catch (JsonParseException e)
                    {
                        e.printStackTrace();
                    }
                    catch (JsonMappingException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                    ResultBean<Map<String, Object>> rb = new ResultBean<Map<String, Object>>();
                    rb.setRet_code("002");
                    if (resmap.get("message") != null && !((String) resmap.get("message")).equals(""))
                    {
                        rb.setRet_msg((String) resmap.get("message"));
                    }
                    else
                    {
                        rb.setRet_msg("操作失败");
                    }

                    return rb;
                }
            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();
                errInfo = e.getMessage();
            }
            Map<String, Object> err = new HashMap<String, Object>();
            err.put("err", errInfo);
            return ResultHelper.getError(err);
            // --------------------------版本号大于等于1.1.7，走新版本-------------------------------------
        }
        return null;

    }

    @RequestMapping(value = "/ioa/initTask.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> intiTask(HttpServletRequest request)
    {
        PmPersonnel pm = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        map.put("personnel_id", pm.getPersonnel_id());
        map.put("statistic_type", 1);
        List<Map<String, Object>> list = releaseTaskMapperServiceImpl.selectReleasePersonnelById(map);
        if (list == null || list.size() == 0)
        {
            result.put("personnel_id", pm.getPersonnel_id());
            result.put("personnel_name", pm.getPersonnel_name());
            result.put("personnel_shortcode", pm.getPersonnel_shortcode());
            result.put("personnel_deptname", pm.getPersonnel_deptname());
            result.put("parent_deptname", pm.getParentDept());
            result.put("personnel_postname", pm.getPersonnel_postname());
        }
        else
        {
            result = list.get(0);
        }
        return ResultHelper.getSuccess(result);
    }

    /**
     * @Title: getPersonnelInfoByGroup
     * @Description: TODO(1.1.7新增查询分组人员信息)
     * @return ResultBean
     * @author: Lixiaolong
     * @time:2016年12月9日 下午6:02:46
     * history:
     * 1、2016年12月9日 Lixiaolong 创建方法
     */
    @RequestMapping(value = "ioa/getPersonnelInfoByGroup.do", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ResultBean<Map<String, Object>> getPersonnelInfoByGroup(HttpServletRequest request)
    {
        PmPersonnel pm = (PmPersonnel) request.getSession().getAttribute(GlobalVal.USER_SESSION);
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        String group_id = request.getParameter("group_id");
        if (StringUtil.isBlank(group_id))
        {
            Map<String, Object> err = new HashMap<String, Object>();
            err.put("ret_msg", "参数不能为空");
            err.put("ret_code", 002);
            return ResultHelper.getError(null);
        }
        else
        {
            params.put("group_id", group_id);
            Map<String, Object> results = releaseTaskMapperServiceImpl.getPersonnelInfoByGroup(params);
            return ResultHelper.getSuccess(results);
        }

    }

    /**
     * @Title: uploadImage
     * @Description: 考勤申请上传图片
     * @param imgFile
     * @param requestinfo
     * @return 
     * @author: sunlq
     * @time:2017年7月11日 下午2:37:26
     * history:
     * 1、2017年7月11日 sunlq 创建方法
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ioa/uploadImage.do", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public ResultBean<Map<String, Object>> uploadImage(@RequestParam(value = "imgFile")
    MultipartFile imgFile, HttpServletRequest requestinfo)
    {
        try
        {
            PmPersonnel pm = (PmPersonnel) requestinfo.getSession().getAttribute(GlobalVal.USER_SESSION);

            final String srcFileName = imgFile.getOriginalFilename();
            if (srcFileName.lastIndexOf(".") > -1)
            {
                String postfix = srcFileName.substring(srcFileName.lastIndexOf(".") + 1).toUpperCase();
                if ("JPG".equals(postfix) || "JPEG".equals(postfix) || "PNG".equals(postfix) || "BMP".equals(postfix))
                {

                }
                else
                {
                    return ResultHelper.getError("只允许上传JPG、JPEG、PNG、BMP格式文件", null);
                }
            }
            else
            {
                return ResultHelper.getError("只允许上传JPG、JPEG、PNG、BMP格式文件", null);
            }

            ByteArrayResource resource = new ByteArrayResource(imgFile.getBytes())
            {
                @Override
                public String getFilename()
                {
                    return srcFileName;
                }
            };

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
            form.add("personnel_id", pm.getPersonnel_id());
            form.add("imgFile", resource);

            String url = HttpClientUtil.getSysUrl("server.ioaUrl") + "/moa/uploadImage.do";

            Map<String, Object> map = restTemplate.postForObject(url, form, Map.class);
            if (map == null)
            {
                return ResultHelper.getError(null);
            }
            else
            {
                return ResultHelper.getSuccess(map);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultHelper.getError(null);
        }
    }

    @Autowired
    private RestTemplate restTemplate;

}
