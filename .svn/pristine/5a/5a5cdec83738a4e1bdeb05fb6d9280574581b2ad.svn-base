package com.zx.moa.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;

public class SysUtil
{
	private static String USER_AGENT = "MOBEL_REQUEST";
	
	public static final String SSO_MODULE = "SSO_MODULE";
	
	public static final String[] SUPERUSERS = new String[]{"100001"};
    /**
     * 获取List中指定属性的值为propertyVal的列表
     * 
     * @param List
     * @param propertyName
     * @param propertyVal
     */
    public static List<Map<String, Object>> getSamePropertyList(List<Map<String, Object>> list, String propertyName,
                                                                String propertyVal)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list)
        {
            String val = map.get(propertyName).toString();
            if (val == null)
            {
                continue;
            }
            if (val.equals(propertyVal))
            {
                resultList.add(map);
            }
        }
        if (resultList.size() == 0)
        {
            return null;
        }
        return resultList;
    }

    /**
     * 根据传入的属性名称、遍历list返回属性值与propertyVal相等的记录
     * 
     * @param list
     * @param propertyName
     * @param propertyVal
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> getSamePropertyListBean(Collection<T> list, String propertyName, Object propertyVal)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }
        List<T> resultList = new ArrayList<T>();
        for (T bean : list)
        {
            Object val = null;
            try
            {
                if (bean instanceof java.util.Map)
                {
                    val = ((java.util.Map) bean).get(propertyName);
                }
                else
                {
                    PropertyDescriptor p = new PropertyDescriptor(propertyName, bean.getClass());
                    val = p.getReadMethod().invoke(bean);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (val == null)
            {
                continue;
            }
            if (val.equals(propertyVal))
            {
                resultList.add(bean);
            }
        }
        if (resultList.size() == 0)
        {
            return null;
        }
        return resultList;
    }

    /**
     * 返回与传入属性propertyName值相同的一个对象
     * 
     * @param list
     * @param propertyName
     * @param propertyVal
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static <T> T getSamePropertyBean(Collection<T> list, String propertyName, Object propertyVal)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }
        for (T bean : list)
        {
            Object val = null;
            try
            {
                if (bean instanceof java.util.Map)
                {
                    val = ((java.util.Map) bean).get(propertyName);
                }
                else
                {
                    PropertyDescriptor p = new PropertyDescriptor(propertyName, bean.getClass());
                    val = p.getReadMethod().invoke(bean);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (val == null)
            {
                continue;
            }
            if (val.equals(propertyVal))
            {
                return bean;
            }
        }
        return null;
    }

    /**
     * 获取浏览器的ip地址
     * 
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (!checkIP(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!checkIP(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!checkIP(ip))
        {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip))
        {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取用户对象
     * 
     * @param request
     * @return
     */
    public static PmPersonnel getUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        PmPersonnel user = (PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        return user;
    }
    
    public static PmPersonnel getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        PmPersonnel user=(PmPersonnel) session.getAttribute(GlobalVal.USER_SESSION);
        return user;
                
    }
    /**
     * 获取浏览器类型
     * 
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request)
    {
        String agent = request.getHeader("User-Agent");
        return agent;
    }

    private static boolean checkIP(String ip)
    {
        if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split("\\.").length != 4)
        {
            return false;
        }
        return true;
    }

    /**
     * 模糊匹配时将传入的参数的特殊字符(%\_)进行转义，并将前后增加通配符%
     * 
     * @param sqlParam
     * @return
     */
    public static String getSqlLikeParam(String sqlParam)
    {
        sqlParam = sqlParam.replace("\\", "\\\\");
        sqlParam = sqlParam.replace("%", "\\%");
        sqlParam = sqlParam.replace("_", "\\_");
        return "%" + sqlParam + "%";
    }

    public static String ajaxFileTextEncoding(String srcStr, HttpServletRequest request)
    {
        String res;
        String agent = request.getHeader("User-Agent").toLowerCase();
        if (agent.indexOf("firefox") > 0 || agent.indexOf("msie 10") > 0 || agent.indexOf("msie 9") > 0
            || agent.indexOf("chrome") > 0)
        {
            try
            {
                res = new String(srcStr.getBytes("UTF-8"), "ISO8859-1");
                return res;
            }
            catch (UnsupportedEncodingException e)
            {
                // log.error(e.getMessage());
            }
        }
        else if (agent.indexOf("msie 8") > 0)
        {
            return srcStr;
        }

        return srcStr;
    }

    /**
     * 比较传入的两个javaBean内容是否一致，不比较last_update_user_id,last_update_timestamp
     * 
     * @param obj1
     * @param obj2
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static <T> String checkBean(T obj1, T obj2)
    {
        Class clazz = obj1.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new HashMap<String, String>();
        for (Field field : fields)
        {
            try
            {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o1 = getMethod.invoke(obj1);
                Object o2 = getMethod.invoke(obj2);
                if ((o1 == null && o2 == null))
                {
                    continue;
                }
                if ((o1 == null || o2 == null))
                {
                    map.put(field.getName(), ":" + o1 + "--》" + o2);
                    continue;
                }
                if (o1 instanceof String)
                {
                    if (!o1.equals(o2))
                    {
                        map.put(field.getName(), ":" + o1 + "--》" + o2);
                    }
                }
                else if (o1 instanceof BigDecimal)
                {
                    if (((BigDecimal) o1).compareTo((BigDecimal) o2) != 0)
                    {
                        map.put(field.getName(), ":" + o1 + "--》" + o2);
                    }
                }
                else if (o1 instanceof Timestamp)
                {
                    if (((Timestamp) o1).getTime() != ((Timestamp) o2).getTime())
                    {
                        map.put(field.getName(), ":" + o1 + "--》" + o2);
                    }
                }
                else if (o1 instanceof Date)
                {
                    if (((Date) o1).getTime() != ((Date) o2).getTime())
                    {
                        map.put(field.getName(), ":" + o1 + "--》" + o2);
                    }
                }
                else if (o1 instanceof Integer)
                {
                    if (((Integer) o1).intValue() == ((Integer) o2).intValue())
                    {
                        map.put(field.getName(), ":" + o1 + "--》" + o2);
                    }
                }
                else
                {
                    if (!o1.toString().equals(o2.toString()))
                    {
                        map.put(field.getName(), ":" + o1 + "--》" + o2);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        if (map.size() > 0)
        {
            return new Gson().toJson(map);
        }
        else
        {
            return "";
        }
    }
    /**
     * 分页工具
     * @param list
     * @param count
     * @return
     */
    public static Map<String, Object> putInMap(List<?> list, Integer count)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 获取用户request
     * @return
     */
    public static HttpServletRequest getRequest(){
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes(); 
		HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
		return request;
    }
	
	public synchronized static String makeUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "_");
	}
	
	public static boolean isSuperUser(String code){
		return Arrays.asList(SUPERUSERS).contains(code);
	}
	
	/**
	 * 根据map里的key替换形如【#need_replace_value#】参数(need_replace_value)的值
	 */
	public static String replaceParamValueByMap(String oldString, Map<String, String> paramMap) {
	    if(paramMap == null) {
	        return oldString;
	    }
	    for(String key : paramMap.keySet()) {
	        if(paramMap.get(key) != null) {
	            oldString = oldString.replace("#" + key + "#", paramMap.get(key));
	        }
	    }
	    return oldString;
	}
	
}
