package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.WsOutbound;

import cn.edu.njupt.allgo.service.dao.LoginDAO;
import cn.edu.njupt.allgo.service.dao.impl.LogDAOimpl;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginDAO dao;
	private final Map<Integer, HttpSession> loginUserMap = new ConcurrentHashMap<Integer, HttpSession>();
	
	//String name = request.getHeader("userid");
	//String name = new String(request.getParameter("userid").getBytes("ISO-8859-1"), "UTF-8");

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request , response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		String uname = helper.getStr("uname");
		String upassword = helper.getStr("upassword");

		UserDataVo userdata = dao.login(uname, upassword);
		if(userdata != null){
			if (loginUserMap.get(userdata.getUid()) != null) {
				// 当前用户存在未过期session 删除旧用户
	            HttpSession sessionold = loginUserMap.get(userdata.getUid());
	            if(sessionold != null){
	            	// 注销已在线用户session
		            sessionold.invalidate();
		            loginUserMap.remove(userdata.getUid());
		            
		            @SuppressWarnings("unchecked")
					Map<Integer, WsOutbound> userMap = (Map<Integer, WsOutbound>) getServletContext().getAttribute("OnLineList");
		            WsOutbound outbound = userMap.get(userdata.getUid());
		            if(outbound!=null){
		            	Map<String, Object> outMap = new HashMap<String, Object>();
						outMap.put("response", "notlogin");
		            	String jsonString = JSONObject.fromObject(outMap).toString();
		    			CharBuffer buffer = CharBuffer.wrap(jsonString);
		    			try {
		    				outbound.writeTextMessage(buffer);
		    				outbound.flush();
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
		            }
	            }
	            
			}
			
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(432000);		//设置过期时间5天
			session.setAttribute("uid", userdata.getUid());
			session.setAttribute("uname", userdata.getUname());

			loginUserMap.put(userdata.getUid(), session);

			helper.put("response", "login");
			helper.put("userdata", userdata);
			helper.put("SessionId", session.getId());
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "用户名或密码错误");
			helper.put("error",outMap);
		}
		helper.send();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		dao = new LogDAOimpl();
		this.getServletContext().setAttribute("loginUserMap", loginUserMap);
	}

	
}
