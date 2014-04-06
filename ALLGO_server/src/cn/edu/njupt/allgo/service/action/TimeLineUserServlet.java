package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.TimeLineUserDAO;
import cn.edu.njupt.allgo.service.dao.impl.TimeLineDAOimpl;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventVo;

/**得到用户组织的活动
 * Servlet implementation class UserTimeLineServlet
 */
@WebServlet("/event/user_timeline")
public class TimeLineUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TimeLineUserDAO dao = new TimeLineDAOimpl();   

    public TimeLineUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int uid = helper.getInt("uid");
		int page = helper.getInt("page");
		int pagenum = helper.getInt("pagenum");
		
		List<EventVo> list = dao.getUserEvent(uid, page, pagenum);
		
		if(list != null){
			helper.put("response", "user_timeline");
			helper.put("user_timeline", list);
			helper.put("list_count", null);
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "没有更多");
			helper.put("error",outMap);
		}
		helper.send();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		
	}

}
