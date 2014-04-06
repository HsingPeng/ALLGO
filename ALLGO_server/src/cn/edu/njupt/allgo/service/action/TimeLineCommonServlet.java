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

import cn.edu.njupt.allgo.service.dao.TimeLineCommonDAO;
import cn.edu.njupt.allgo.service.dao.impl.TimeLineDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventVo;

/**得到所有活动
 * Servlet implementation class TimeLineServlet
 */
@WebServlet("/event/timeline")
public class TimeLineCommonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TimeLineCommonDAO dao = new TimeLineDAOimpl();
	
    public TimeLineCommonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int page = helper.getInt("page");
		int pagenum = helper.getInt("pagenum");
		String position = helper.getStr("position");
		String ecategoryname = helper.getStr("ecategoryname");
		String StartTimeRangA = helper.getStr("StartTimeRangA");
		String StartTimeRangB = helper.getStr("StartTimeRangB");

		List<EventVo> list = dao.getCommonEvent(page, pagenum, position, ecategoryname, StartTimeRangA, StartTimeRangB);
		
		if(list != null){
			helper.put("response", "commonevent_timeline");
			helper.put("timeline", list);
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
