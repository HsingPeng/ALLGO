package cn.edu.njupt.allgo.service.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class CometUtil {

	private static final String JSON = "application/json";
	
	
	/**
	 * 输出数据流
	 * @param response
	 * @param content
	 */
	public static void render(final HttpServletResponse response,final String contentType, final String content){
		PrintWriter writer;
		try {
			
			writer = response.getWriter();
			writer.write(content+"\n");
			writer.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, @SuppressWarnings("rawtypes") final Map map){
		String jsonString = JSONObject.fromObject(map).toString();
		render(response,JSON,jsonString);
	}

	
	
}
