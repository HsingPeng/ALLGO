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

import cn.edu.njupt.allgo.service.dao.EventCommentsCreatDAO;
import cn.edu.njupt.allgo.service.dao.impl.EventDAOimpl;
import cn.edu.njupt.allgo.service.utils.CommonUtil;
import cn.edu.njupt.allgo.service.utils.ServletHelper;
import cn.edu.njupt.allgo.service.vo.EventCommentVo;
import cn.edu.njupt.allgo.service.vo.ServerMsg;

/**提交活动评论
 * Servlet implementation class CommentsCreateServlet
 */
@WebServlet("/event/comments/create")
public class EventCommentsCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventCommentsCreatDAO dao = new EventDAOimpl();

    public EventCommentsCreateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletHelper helper = new ServletHelper(request,response);
		String comment = helper.getStr("texts");
		int uid = helper.getUid();
		int eid = helper.getInt("eid");
		String uname = helper.getUname();
		int replyuid = helper.getInt("replyuid");
		String replyuname = helper.getStr("replyuname");
		
		EventCommentVo commentVo =  dao.creatComment(comment,uid,eid,uname,replyuid,replyuname);
		if(commentVo != null&&commentVo.getCommentsid() != -1){
			helper.put("response", "create_comment");
			helper.put("create_comment", commentVo);
			helper.send();
			
			ServletContext servletContext = this.getServletContext();
			ServerMsg message = new ServerMsg(9,commentVo,null);
			servletContext.setAttribute("action"+UUID.randomUUID(), message);
			
		}else{
			helper.put("response", "error");
			Map<String , Object> outMap = new HashMap<String , Object>() ;
			outMap.put("text", "提交错误");
			helper.put("error",outMap);
			helper.send();
		}
	}

}
