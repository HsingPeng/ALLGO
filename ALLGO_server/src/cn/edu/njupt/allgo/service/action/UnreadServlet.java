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

import cn.edu.njupt.allgo.service.dao.UnreadDAO;
import cn.edu.njupt.allgo.service.dao.impl.UnreadDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.UnreadVo;

/**
 * Servlet implementation class UnreadServlet
 */
@WebServlet("/remind/unread")
public class UnreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UnreadDAO dao = new UnreadDAOimpl();
	
    public UnreadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int uid = helper.getInt("uid");

		List<UnreadVo> list= dao.getUnread(uid);

		if(list != null){
			helper.put("response", "remind_unread");
			helper.put("remind_unread", list);
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "数据错误");
			helper.put("error",outMap);
		}
		helper.send();
	}

}
