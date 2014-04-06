package cn.edu.njupt.allgo.service.filter;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.WsOutbound;

import cn.edu.njupt.allgo.service.utils.CommonUtil;

@WebFilter(filterName = "IsLoginFilter", urlPatterns = { "/*" })
public class IsLoginFilter implements Filter {

	private ServletContext servletContext;


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String uri = req.getRequestURI().substring(req.getContextPath().length()+1);
		System.out.println("IsLoginFilter==>"+uri);
		HttpSession session = req.getSession(false);
		switch(uri){
		case "login":
			filterChain.doFilter(request, response);
			break;
		case "register":
			filterChain.doFilter(request, response);
			break;
		case "pull.ws":		//放到下级处理
			filterChain.doFilter(request, response);
			break;
		default:
			if(session!=null){
				filterChain.doFilter(request, response);
			}else{
				Map<String, Object> outMap = new HashMap<String, Object>();
				outMap.put("response", "notlogin");
				CommonUtil.renderJson(resp, outMap);
				return;
			}
			break;
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
