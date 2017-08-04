package com.zx.moa.util.init;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.zx.moa.util.GlobalVal;

public class BackSystemInitServlet extends HttpServlet {
	private static final long serialVersionUID = 4124099489926662290L;

	@Override
	public void init() throws ServletException {
		//进行系统的初始化信息
		GlobalVal.init();
	}
}