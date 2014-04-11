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

import cn.edu.njupt.allgo.service.dao.EventUnFollowDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.ServerMsg;

/**
 * Servlet implementation class EventUserFollowServlet
 */
@WebServlet("/event/unfollow")
public class EventUnFollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EventUnFollowDAO dao = new EventDAOimpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventUnFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int eid = helper.getInt("eid");
		int uid = helper.getInt("uid");

		boolean flag = dao.unfollow(eid, uid) ;
		
		if(flag){
			helper.put("response", "unfollow");

			ServletContext servletContext = this.getServletContext();
			ServerMsg message = new ServerMsg(11,eid,null);
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
