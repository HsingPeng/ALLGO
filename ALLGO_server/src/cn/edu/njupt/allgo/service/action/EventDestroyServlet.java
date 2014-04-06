package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.EventDestroyDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.ServerMsg;

/**
 * Servlet implementation class EventDestroyServlet
 */
@WebServlet("/event/destroy")
public class EventDestroyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EventDestroyDAO dao = new EventDAOimpl();   
	
    public EventDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int eid = helper.getInt("eid");
		
		EventVo result= dao.destroyEvent(eid, helper.getUid());

		if(result != null){
			helper.put("response", "event_destroy");
			helper.put("eid", eid);
			
			ServletContext servletContext = this.getServletContext();
			ServerMsg message = new ServerMsg(15,result,null);
			servletContext.setAttribute("action"+UUID.randomUUID(), message);
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "提交错误");
			helper.put("error",outMap);
		}
		helper.send();
	}

}
