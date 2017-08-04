package com.zx.moa.ioa.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.zx.moa.util.SysUtil;
import com.zx.platform.syscontext.util.StringUtil;

/**
 * 单客户端SESSION管理
 * @author MATF
 *
 */
public class ZXSessionManage implements HttpSessionListener{
	
	private static final Logger log = Logger.getLogger(ZXSessionManage.class);
	
	public static String IS_OUT = "IS_OUT";
	
	public static String ZSSESSION_KEY = "ZSSESSION_KEY";

	private static Map<String,ZXSession> _container = new ConcurrentHashMap<>();
	
	private static ZXSessionManage _instance = new ZXSessionManage();
	
	private static List<String> _sessionIds = new ArrayList<String>();
	
	public ZXSessionManage(){}
	
	/**
	 * 获取存在的SESSION管理器
	 * @return
	 */
	public static ZXSessionManage getInstance(){
		return _instance;
	}
	
	/**
	 * 注册一个客户端登录
	 * @param user_code
	 * @param app_name
	 * @param session
	 */
	public synchronized void register(String user_code,String app_name,HttpSession session){
	    session.removeAttribute(IS_OUT);
		String key = getKey(user_code, app_name);
		ZXSession old = _container.get(key);
		if(old != null){
			if(session.getId() != null && old.getSession() != null && session.getId().equals(old.getSession().getId())){
			    old.setSession(session);
			    return;
			}
			kickOut(old);
			//old.clean();
		}
		ZXSession zs = new ZXSession(session, app_name, user_code);
		_container.put(key, zs);
		log.debug(key+"->注册成功!"+session.getId());
	}
	
	public void clearUser(String user_code,String app_name){
		String key = getKey(user_code, app_name);
		_container.remove(key);
	}
	
	/**
	 * 获取session管理Key
	 * @param user_code
	 * @param app_name
	 * @return
	 */
	private String getKey(String user_code,String app_name){
		return app_name + "-" + user_code;
	}
	
	/**
	 * 将原用户踢出
	 * @param zs
	 */
	private void kickOut(ZXSession zs){
		HttpSession session = zs.getSession();
		if(session != null){
			try{
			    if(_sessionIds.contains(session.getId())){
    				session.setAttribute(IS_OUT, true);
    				log.debug("被踢出登录!"+session.getId());
			    }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断是否被其他用户踢出
	 * @param session
	 * @return
	 */
	public static boolean isOut(HttpSession session){
		Boolean flag = (Boolean) session.getAttribute(IS_OUT);
		if(flag != null && flag){
			return true;
		}
		return false;
	}
	
	public static void clearIsOut(HttpSession session){
		session.removeAttribute(IS_OUT);
	}
	
	/**
	 * 判断是否为单客户端登录
	 * @param request
	 * @return
	 */
	public static boolean isCheck(HttpServletRequest request){
		String app_name = request.getHeader(SysUtil.SSO_MODULE);
		return StringUtil.isNotBlank(app_name);
	}
	
	/**
	 * SESSION
	 * @author MATF
	 *
	 */
	public class ZXSession {

		private HttpSession session;
		
		private String app_name;
		
		private String user_code;
		
		public ZXSession(HttpSession session, String app_name, String user_code) {
			super();
			this.session = session;
			this.app_name = app_name;
			this.user_code = user_code;
		}

		public HttpSession getSession() {
			return session;
		}

		public void setSession(HttpSession session) {
			this.session = session;
		}

		public String getApp_name() {
			return app_name;
		}

		public void setApp_name(String app_name) {
			this.app_name = app_name;
		}

		public String getUser_code() {
			return user_code;
		}

		public void setUser_code(String user_code) {
			this.user_code = user_code;
		}
		
		public void clean(){
			this.app_name = null;
			this.session = null;
			this.user_code = null;
		}

	}
	
	 @Override
     public void sessionCreated(HttpSessionEvent httpsessionevent)
     {
         // TODO Auto-generated method stub
	     _sessionIds.add(httpsessionevent.getSession().getId());
     }

     @Override
     public void sessionDestroyed(HttpSessionEvent httpsessionevent)
     {
         // TODO Auto-generated method stub
         _sessionIds.remove(httpsessionevent.getSession().getId());
     }
	
}
