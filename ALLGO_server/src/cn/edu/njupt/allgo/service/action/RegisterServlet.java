package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cn.edu.njupt.allgo.service.dao.RegisterDAO;
import cn.edu.njupt.allgo.service.dao.impl.RegisterDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegisterDAO dao = new RegisterDAOimpl();
	
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		String uname = helper.getStr("uname");
		int usex = helper.getInt("usex");
		String uemail = helper.getStr("uemail");
		String upassword = helper.getStr("upassword");
		
		String flag = dao.isExist(uname, uemail);
		
		if(flag != null){
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", flag);
			helper.put("error",outMap);
			helper.send();
		}else{
		
			UserDataVo vo = dao.register(uname, usex, uemail, upassword);
			
			if(vo != null){
				Part part = helper.getPart("avatar");
				if (part != null) {
		            part.write(getServletContext().getRealPath(
		                    "/photo/avatar") + "/" + vo.getUid() + ".jpg");
		        }
			}

			if(vo != null){
				helper.put("response", "register");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("uname", vo.getUname());
				map.put("uid",vo.getUid());
				helper.put("userinfo", map);
			}else{
				helper.put("response", "error");
				Map<String , Object> outMap = new HashMap<String , Object>() ;
				outMap.put("text", "注册错误");
				helper.put("error",outMap);
			}
			helper.send();
			
		}
		
	}

}
