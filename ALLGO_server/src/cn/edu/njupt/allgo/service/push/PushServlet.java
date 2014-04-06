package cn.edu.njupt.allgo.service.push;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import cn.edu.njupt.allgo.service.dao.UnreadDAO;
import cn.edu.njupt.allgo.service.dao.impl.UnreadDAOimpl;
import cn.edu.njupt.allgo.service.vo.UnreadVo;

@WebServlet("/pull.ws")
public class PushServlet extends WebSocketServlet {

	private final Map<Integer, WsOutbound> userMap = new ConcurrentHashMap<Integer, WsOutbound>();

	@Override
	public void init() throws ServletException {
		super.init();
		//把所有在线用户的列表存放在application里
		this.getServletContext().setAttribute("OnLineList", userMap);
	}

	@Override
	protected boolean verifyOrigin(String origin) {
		// TODO Auto-generated method stub
		return super.verifyOrigin(origin);
	}

	//创建Inbound实例，WebSocketServlet子类必须实现的方法 
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		int uid = -1;
		if(request.getSession(false) != null){
			uid = (int) request.getSession(false).getAttribute("uid");
		}
		System.out.println("请求登录==>uid:"+uid);
		ChatMessageInbound cmi = new ChatMessageInbound(uid);
		return cmi;
	}

	//MessageInbound子类，完成收到WebSocket消息后的逻辑处理 
	class ChatMessageInbound extends MessageInbound {

			private int uid;

			public ChatMessageInbound(int uid) {
				this.uid = uid ;
			}
			
			@Override
			protected void onOpen(WsOutbound outbound) {
				if(uid == -1){		//过期用户直接下线
					Map<String, Object> outMap = new HashMap<String, Object>();
					outMap.put("response", "notlogin");
	            	String jsonString = JSONObject.fromObject(outMap).toString();
	    			CharBuffer buffer = CharBuffer.wrap(jsonString);
	    			try {
	    				outbound.writeTextMessage(buffer);
	    				outbound.flush();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
				}else{
					userMap.put(uid, outbound);
					System.out.println("[上线]==>uid:"+uid+"在线用户==>"+userMap.size());
					sendUnread(outbound);		//登录即检查有没有未读消息
				}
				super.onOpen(outbound);
			}
			
			//发送未读消息
			private void sendUnread(WsOutbound outbound){
				Map<String, Object> outMap = new HashMap<String, Object>();
				outMap.put("response", "remind_unread");
				UnreadDAO dao = new UnreadDAOimpl();
				List<UnreadVo> list = dao.getUnread(uid);
				if(list != null){
					outMap.put("remind_unread", list);
					String jsonString = JSONObject.fromObject(outMap).toString();
					CharBuffer buffer = CharBuffer.wrap(jsonString);
					try {
						outbound.writeTextMessage(buffer);
						outbound.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			protected void onClose(int status) {
				userMap.remove(uid);
				System.out.println("[下线]==>uid:"+uid+"在线用户==>"+userMap.size());
				super.onClose(status);
			}

			//二进制消息响应
			@Override
			protected void onBinaryMessage(ByteBuffer buffer) throws IOException {

			}

			//接收文本消息
			@Override
			protected void onTextMessage(CharBuffer buffer) throws IOException {
				String msg = buffer.toString();
				System.out.println("onTextMessage==>"+msg);
			}
			
		}
	
}
