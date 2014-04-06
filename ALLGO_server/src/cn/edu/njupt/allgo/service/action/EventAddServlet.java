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
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.ServerMsg;

/**活动补充
 * Servlet implementation class EventAddServlet
 */
@WebServlet("/event/add")
public class EventAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private int i = 0 ;
    private EventAddDAO dao = new EventDAOimpl();
    
    public EventAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int eid = helper.getInt("eid");
		String content = helper.getStr("content");

		EventAddVo eventadd = dao.creatAdd(eid, helper.time(), content);
		
		if(eventadd != null&& eventadd.getEaid()!= -1){
			helper.put("response", "event_add");
			helper.put("add", eventadd);
			
			ServletContext servletContext = this.getServletContext();
			ServerMsg message = new ServerMsg(14,eventadd,null);
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
