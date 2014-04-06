package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.EventFollowDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.ServerMsg;

/**
 * Servlet implementation class EventUserFollowServlet
 */
@WebServlet("/event/follow")
public class EventFollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EventFollowDAO dao = new EventDAOimpl();   
	
    public EventFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int eid = helper.getInt("eid");
		int uid = helper.getInt("uid");
		String uname = helper.getStr("uname");
		EventFollowerVo vo = null;
		vo = dao.isfollowed(eid, uid);
		if(vo != null){
			helper.put("response", "follow");
			helper.put("follow", vo);
		}else{
			vo = dao.follow(eid, uname, uid);
			if(vo != null){
				helper.put("response", "follow");
				helper.put("follow", vo);
				
				ServletContext servletContext = this.getServletContext();
				ServerMsg message = new ServerMsg(10,vo,null);
				servletContext.setAttribute("action"+UUID.randomUUID(), message);
			}else{
				helper.put("response", "error");
				Map<String , Object> outMap = new HashMap<String , Object>() ;
				outMap.put("text", "提交错误");
				helper.put("error",outMap);
			}
		}
		
		
		
		helper.send();
	}

}
