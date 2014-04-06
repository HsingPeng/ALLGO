package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.EventCreatDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

/**创建活动
 * Servlet implementation class CreateEventServlet
 */
@WebServlet("/event/create")
public class EventCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EventCreatDAO dao = new EventDAOimpl();   
	
    public EventCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		String outline = helper.getStr("outline");
		String startdate = helper.getStr("startdate");
		String enddate = helper.getStr("enddate");
		String content = helper.getStr("content");
		String place = helper.getStr("place");
		String position = helper.getStr("position");
		String ecategoryname = helper.getStr("ecategoryname");
		int visible = helper.getInt("visible");

		EventVo event= dao.creatEvent(outline, helper.getUid(),helper.getUname(), startdate,
				enddate, content, place, position, ecategoryname, visible);

		if(event != null&& event.getEid()!= -1){
			helper.put("response", "event_create");
			helper.put("event_create", event);
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "提交错误");
			helper.put("error",outMap);
		}
		helper.send();
	}

}
