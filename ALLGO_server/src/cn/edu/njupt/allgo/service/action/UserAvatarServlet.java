package cn.edu.njupt.allgo.service.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cn.edu.njupt.allgo.service.utils.ServletHelper;

@WebServlet("/user/avatar")
@MultipartConfig
public class UserAvatarServlet extends HttpServlet {

	public UserAvatarServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		int uid = helper.getInt("uid");
		Part part = helper.getPart("avatar");
		if (part != null) {
            part.write(getServletContext().getRealPath(
                    "/photo/avatar") + "/" + uid + ".jpg");
        }
		if(part != null){
			helper.put("response", "user_avatar");
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "头像上传错误");
			helper.put("error",outMap);
		}
		helper.send();
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
