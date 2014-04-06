package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.njupt.allgo.service.dao.UserDataDAO;
import cn.edu.njupt.allgo.service.dao.impl.LogDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

/**
 * Servlet implementation class UserDataServlet
 */
@WebServlet("/user/data")
public class UserDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDataDAO dao = new LogDAOimpl();
	
    public UserDataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int uid = helper.getInt("uid");

		UserDataVo vo = dao.getUserData(uid);

		if(vo != null){
			helper.put("response", "userdata");
			helper.put("userdata", vo);
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "数据错误");
			helper.put("error",outMap);
		}
		helper.send();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
