package com.zx.moa.ioa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.zx.platform.syscontext.util.StringUtil;

/**
 * 版权所有：版权所有(C) 2016，卓信金控
 * 系统名称：
 * @ClassName: CheckingUtil
 * 模块名称：
 * @Description: 内容摘要：
 * @author zhaowei
 * @date 2017年7月5日
 * @version V1.0
 * history:
 * 1、2017年7月5日 zhaowei 创建文件
 */
public class CheckingUtil
{

    private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

    private static SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");

    private static final Pattern dateTimeReg = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");

    private static final Pattern timeReg = Pattern.compile("\\d{2}:\\d{2}:\\d{2}$");

    private static final Pattern dateReg = Pattern.compile("\\d{4}-\\d{2}-\\d{2}$");

    @SuppressWarnings("unused")
    private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 讲传入map转为实体bean key 对应bean属性名称(区分大小写)
     * @param map 参数map
     * @param clazz 实体bean的class
     * @return 
     * @throws Exception 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T MapToBean(Map<String, Object> map, Class<T> clazz) throws Exception
    {
        Class demo = Class.forName(clazz.getName());
        T obj = (T) demo.newInstance();
        Field[] field = demo.getDeclaredFields();
        for (int i = 0; i < field.length; i++)
        {
            Object val = map.get(field[i].getName());
            if (val != null)
            {
                val = changeType(val, field[i].getType().getSimpleName());
                Method method = obj.getClass().getMethod("set" + doFirstLetterUp(field[i].getName()), field[i].getType());
                method.invoke(obj, val);
                // BeanUtils.setProperty(obj, field[i].getName(), val);
            }
        }
        return obj;
    }

    public static Object changeType(Object val, String type)
    {
        if (type.equals("int") || type.equals("Integer"))
        {
            return Integer.parseInt(val.toString());
        }
        if (type.equals("String"))
        {
            return val.toString();
        }
        if (type.equals("Long") || type.equals("long"))
        {
            return Long.parseLong(val.toString());
        }
        if (type.equals("Double") || type.equals("double"))
        {
            return Double.parseDouble(val.toString());
        }
        if (type.equals("Float") || type.equals("float"))
        {
            return Float.parseFloat(val.toString());
        }
        if (type.equals("Byte") || type.equals("byte"))
        {
            return Byte.parseByte(val.toString());
        }
        if (type.equals("Short") || type.equals("Short"))
        {
            return Short.parseShort(val.toString());
        }
        if (type.equals("Char") || type.equals("char"))
        {
            return val.toString().charAt(0);
        }
        if (type.equals("Timestamp"))
        {
            if (StringUtil.isEmpty(val.toString()) || val instanceof Timestamp)
            {
                return val;
            }
            String t = dateStrFormatter(val.toString(), "yyyy-MM-dd HH:mm:ss");
            Long l;
            try
            {
                l = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t).getTime();
                return new Timestamp(l);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return val;
    }

    /**
     * 日期字符串格式化
     * @param dateStr 日期字符串（yyyy-MM-dd HH:mm:ss、yyyy-MM-dd、HH:mm:ss）
     * @param format 返回字符串格式
     * @return 如果日期参数错误返回空字符串
     */
    public static String dateStrFormatter(String dateStr, String format)
    {
        try
        {
            Date d;
            if (dateTimeReg.matcher(dateStr).matches())
            {
                d = sdfDateTime.parse(dateStr);
            }
            else if (dateReg.matcher(dateStr).matches())
            {
                d = sdfDate.parse(dateStr);
            }
            else if (timeReg.matcher(dateStr).matches())
            {
                d = sdfTime.parse(dateStr);
            }
            else
            {
                throw new Exception("日期格式错误!");
            }
            return new SimpleDateFormat(format).format(d);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String doFirstLetterUp(String str)
    {
        String first = str.substring(0, 1);
        String other = str.substring(1);
        return first.toUpperCase() + other;
    }

    /**
     * @Title: getMonthFeistAndFinal
     * @Description: 获取当前月份第一天跟最后一天
     * @return 
     * @author: zhaowei
     * @time:2017年7月6日 上午11:29:45
     * history:
     * 1、2017年7月6日 zhaowei 创建方法
    */
    public static Map<String, Object> getMonthFeistAndFinal()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 当月
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        map.put("date_start", format.format(c.getTime()));
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

        map.put("date_end", format.format(ca.getTime()));
        return map;

    }

    /**
     * 计算第一个时间 - 第二个时间的差值（毫秒）
     * @param time1
     * @param time2
     * @return 毫秒值
     */
    public static long calculateTwoTime(String time1, String time2)
    {
        Date data1 = getTime(time1);
        Date data2 = getTime(time2);
        long l1 = data1.getTime();
        long l2 = data2.getTime();
        return l1 - l2;
    }

    /**
     * 获取时间(非当前年月日的时间,是1970年1月1日（格林威治时间）的时间)
     * 修改：如果传入数据为yyyy-MM-dd HH:mm:ss 时为传入日期的时间(2015-11-16)
     * @param date 时间字符串,格式：HH:mm:ss或yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getTime(String date)
    {
        String dt[] = date.split(" ");
        if (dt.length == 2)
        {
            date = dt[1];
        }
        String a[] = date.split(":");
        if (a.length == 2)
        {
            date += ":00";
        }
        else if (a.length == 3)
        {
            date = a[0] + ":" + a[1] + ":00";
        }
        if (date != null)
        {
            try
            {
                return dt.length == 2 ? sdfDateTime.parse(dt[0] + " " + date) : sdfTime.parse(date);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取时间 
     * @param date 时间字符串,格式yyyy-MM-dd 
     * @return
     */
    public static String getNow()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    /**
     * 获取时间 
     * @param date 时间字符串
     * @return
     */
    public static String getNowT()
    {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String time = format.format(date);
        return time;
    }

    public static Map<Integer, String> getVacateMap()
    {

        // 事假31、病假32、年假33、产假34、婚假35、丧假36、工伤假37、哺乳假38、护理假39、路程假54、27补签申请、29调休申请、28.酬勤申请、52.出差申请、64.项目酬勤申请
        Integer IntArr[] = { 31, 32, 33, 34, 35, 36, 37, 38, 39, 54, 27, 29, 28, 52, 64 };
        Map<Integer, String> m = new HashMap<Integer, String>();
        m.put(31, "事假");
        m.put(32, "病假");
        m.put(33, "年假");
        m.put(34, "产假");
        m.put(35, "婚假");
        m.put(36, "丧假");
        m.put(37, "工伤假");
        m.put(38, "哺乳假");
        m.put(39, "护理假");
        m.put(54, "路程假");
        m.put(27, "补签申请");
        m.put(28, "酬勤申请");
        m.put(29, "调休申请");
        m.put(52, "出差申请");
        m.put(64, "项目酬勤申请");
        return m;
    }

    /**
     * @Title: getFirstAndFinalDay
     * @Description: 获取输入年月日的第一天跟最后一天
     * @return 
     * @author: quwenrui
     * @time:2017年7月8日 上午11:29:45
     * history:
     * 1、2017年7月6日 quwenrui 创建方法
    */
    public static Map<String, Object> getFirstAndFinalDay(String time)
    {
        String[] timeStr = time.split("-");
        int year=Integer.parseInt(timeStr[0]) ;
        int month = Integer.parseInt(timeStr[1]) - 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> map = new HashMap<String, Object>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        // 获取当前月最后一天第一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        map.put("date_end", format.format(cal.getTime()));
        // 获取当前月第一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        map.put("date_start", format.format(cal.getTime()));
        return map;

    }


    /**
    * 获得指定日期的前一天
    * @param specifiedDay
    * @return
    * @throws Exception
    */
    public static String getSpecifiedDayBefore(String specifiedDay)
    {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    /**
     * @Title: getYearFeistAndFinal
     * @Description: 当前年份第一天最后一天
     * @return 
     * @author: zhaowei
     * @time:2017年7月11日 下午7:57:48
     * history:
     * 1、2017年7月11日 zhaowei 创建方法
    */
    public static Map<String, Object> getYearFeistAndFinal()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String year = format.format(new Date());
        map.put("year_start", year + "-01-01");
        map.put("year_end", year + "-12-31");
        return map;
    }

    public static String getWeek(Date date)
    {
        String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0)
        {
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static List<String> getMonByTX()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        // 当前
        c.setTime(new Date());
        c.add(Calendar.MONTH, 0);
        Date y = c.getTime();
        String u = format.format(y);
        // System.out.println("当前月：" + u);

        // 过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        // System.out.println("过去一个月：" + mon);
        // 过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, +1);
        Date f = c.getTime();
        String x = format.format(f);
        // System.out.println("后一个月：" + x);
        List<String> l = new ArrayList<String>();
        l.add(u);
        l.add(mon);
        l.add(x);
        return l;
    }

    public static String getSpecifiedYearBefore(String specifiedDay, int i)
    {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int y = c.get(Calendar.YEAR);
        c.set(Calendar.YEAR, y + i);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

}
