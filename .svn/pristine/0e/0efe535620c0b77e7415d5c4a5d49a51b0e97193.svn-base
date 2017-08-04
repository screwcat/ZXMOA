package com.zx.moa.wms.loan.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zx.moa.ioa.documentsmanage.service.IDocumentsService;
import com.zx.moa.util.HttpClientUtil;
import com.zx.moa.util.ResultHelper;
import com.zx.moa.util.bean.ResultBean;
/**
 * 获取图片
 * @author baisong
 */
@Controller
public class WmsDocumentsController {
	/**
	 * 获取图片流
	 * @param request
	 * @param response
	 */
	@RequestMapping("wms/getImg.do")
	public void getImg(HttpServletRequest request,HttpServletResponse response){
		String url = request.getParameter("url");//获取图片地址
		url = HttpClientUtil.getSysUrl("server.wmsGetFileUrl") + url;
		HttpClientUtil.loadImg(url, response);
	}
}
