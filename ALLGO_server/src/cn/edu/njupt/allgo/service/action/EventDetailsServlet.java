package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.EventDetailsDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventCommentVo;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.EventVo;

/**
 * Servlet implementation class EventDetailsServlet
 */
@WebServlet("/event/details")
public class EventDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventDetailsDAO dao = new EventDAOimpl();
	
    public EventDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		
		int eid = helper.getInt("eid");

		if(!dao.isExist(eid)){
			helper.put("response", "event_destroy");
			helper.put("eid", eid);
		}else{
			helper.put("response", "details");
			helper.put("eid", eid);
			List<EventFollowerVo> followers = dao.getFollowers(eid);
			List<EventAddVo> adds = dao.getAdd(eid);
			List<EventCommentVo> comments = dao.getComments(eid);
			helper.put("followers", followers);
			helper.put("followers_count", followers==null?0:followers.size());
			helper.put("adds", adds);
			helper.put("comments", comments);
			helper.put("comments_count", comments==null?0:comments.size());
		}
		helper.send();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
