package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.EventAddDAO;
import cn.edu.njupt.allgo.service.dao.EventDetailsDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.ServerMsg;

/**活动补充
 * Servlet implementation class EventAddServlet
 */
@WebServlet("/event/base")
public class EventBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EventDetailsDAO dao = new EventDAOimpl();
    
    public EventBaseServlet(){
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int eid = helper.getInt("eid");

		EventVo event = dao.isExist(eid);
		
		if(event != null){
			helper.put("response", "event_base");
			helper.put("event_base", event);
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "获取错误或活动不存在");
			helper.put("error",outMap);
		}
		helper.send();
	}

}
