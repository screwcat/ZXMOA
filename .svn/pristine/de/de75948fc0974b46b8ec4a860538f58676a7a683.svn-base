package com.zx.moa.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zx.moa.util.bean.UserBean;
import com.zx.moa.util.gen.entity.ioa.PmPersonnel;


/**
 * @author gx 描述：是系统的帮助类
 * */
public class SysTools {
	private static Logger log = LoggerFactory.getLogger(SysTools.class);

	public static void saveLog(HttpServletRequest request, String msg) {
		try {
			HttpSession session = request.getSession();
			@SuppressWarnings("unused")
			UserBean user = (UserBean) session
					.getAttribute(GlobalVal.USER_SESSION);

			/*com.zx.emanage.util.gen.entity.SysLog bean = new com.zx.emanage.util.gen.entity.SysLog();
			SysLogDao sysLogDao = (SysLogDao) GlobalVal.ctx
					.getBean("syslogDao");

			// 插入日志表记录
			bean.setOper_behavior(msg);
			bean.setOper_ip(getIP(request));
			bean.setUser_name(user.getRealname());
			bean.setUser_code(user.getUserCode());
			bean.setUnit_name(user.getDeptSimpleName());
			bean.setOper_timestamp(new Timestamp(new Date().getTime()));
			bean.setOper_type("1");
			//sysLogDao.save(bean);*/
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	public static void saveLogToFile(PmPersonnel user, String url,
			String ip, String paramStr, long handleTime,String userAgent,String respStr,String respContentType,String sessionId) {
		//log.info("url:" + url +"|ip:"+ ip + "|userId:"+ user.getUserId() +"|userAgent:"+ userAgent + "|handleTime:" + handleTime  +"|params:"+ paramStr);
		//这个是用来调试的
		log.info("url:" + url+"|sessionId:"+ sessionId +"|ip:"+ ip +"|userAgent:"+ userAgent + "|handleTime:"+ handleTime  +"|params:"+ paramStr+"|respStr:"+ respStr+"|respContentType:"+ respContentType);
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
}
