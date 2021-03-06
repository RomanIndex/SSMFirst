package com.ssm.base.util;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssm.admin.entity.SsmPrivilege;
import com.ssm.admin.service.ModuleService;
import com.ssm.admin.service.PrivilegeService;
import com.ssm.admin.view.RecursionChildVo;
import com.ssm.common.enumeration.OperateEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.ssm.base.view.Config;

public class SecurityFilter extends HttpServlet implements Filter{

	//private static final long serialVersionUID = -6214204746258756402L;

	public static Logger logger = Logger.getLogger(SecurityFilter.class.getName());
	
	@Autowired private ModuleService moduleService;
    @Autowired private PrivilegeService privilegeService;
	
	private FilterConfig filterConfig;	// 过滤器配置对象
	private String[] freePages;		// 不操作的页面数组
    private String toPage = null;	// 无效时返回的页面
	
	public void init(FilterConfig config) throws ServletException {
		int index = 0;
        this.filterConfig = config;
        //以下从配置文件获取配置信息
        this.toPage = config.getInitParameter("toPage");
        String strPages = config.getInitParameter("freePages");
        
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
        
        if(toPage == null || strPages == null || toPage.trim().length() == 0 || strPages.trim().length() == 0) {
            throw new ServletException("web.xml中filterServlet没有配置初始化参数toPage或freePage.");
        }
        
        StringTokenizer strTokenizer = new StringTokenizer(strPages, ";");//同split()，但不推荐使用，效率低还麻烦
        this.freePages = new String[strTokenizer.countTokens()];
        
        while(strTokenizer.hasMoreTokens()) {
            freePages[index++] = strTokenizer.nextToken();
        }

        if(!isFreePage(toPage)) {
            throw new ServletException("web.xml中filter初始化参数toPage的值必须是freePage中的某个页面.");
        }
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String requestURI = request.getRequestURI();
        //System.out.println("SecurityFilter--doFilter："+ requestURI);
        
        if(!isFreePage(requestURI)) {
            //Session失效
        	if(!isValidSession(request)) {
        		try {
                    String toPageURL = request.getContextPath() + toPage;
                    response.encodeRedirectURL(toPageURL);
                    response.sendRedirect(toPageURL); //转发响应
                } catch(IOException ex) {
                	ex.printStackTrace();
                }
        	}else {
        	    String account = (String)request.getSession().getAttribute(Config.SSM_ACCOUNT);
                List<SsmPrivilege> privileges = privilegeService.listByAccountAndOperate(account, OperateEnum.menu);
        		List<RecursionChildVo> menus = (List<RecursionChildVo>) moduleService.privilege2menu(privileges).getData();
        		request.getSession().setAttribute("menu", menus);
        	}
        }else{
            if(requestURI.indexOf("freeView") > -1){
                //response.sendRedirect(requestURI);
                request.getRequestDispatcher(requestURI).forward(request, response);
                System.out.println("转发后的请求："+ request.getRequestURI());
                return;
            }
        }

        //如果响应未提交,交给过滤器链
        if(!response.isCommitted()) {
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch(Exception ex) {
                filterConfig.getServletContext().log(ex.getMessage());
            }
        }
	}
	
	private boolean isFreePage(String requestURI) {
        int iLength = freePages.length;
        
        if(requestURI.indexOf("login/check") >= 0 //登录验证
        		|| requestURI.indexOf("logout") >= 0 //退出
        		|| requestURI.indexOf("login") >= 0 //退出
        		|| requestURI.indexOf("login.ftl") >= 0
                || requestURI.endsWith(".js")
                || requestURI.endsWith(".css")
                || requestURI.endsWith(".html")
                || requestURI.endsWith(".ftl")){
            return true;
        }
        
        for(int i = 0; i < iLength; i++) {
            if(requestURI.endsWith(freePages[i])) {
                return true;
            }
        }
        
        return false;
    }
	
	private boolean isValidSession(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String isLogin = (String)httpRequest.getSession().getAttribute(Config.SECURITY_LOGIN_KEY);
        boolean bIsValid = Config.SECURITY_IS_LOGIN.equals(isLogin);
        return bIsValid;
    }

	@Override
	public void destroy() {
		System.out.println("----------destroy SecurityFilter-------------");
	}

}