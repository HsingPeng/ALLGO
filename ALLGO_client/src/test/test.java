package test;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.HTTP;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.FriendEventVo;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * 测试类，专门用来JUnit测试
 * @author 深蓝
 *
 */
public class test extends AndroidTestCase {
	
	public void test9(){
		String ss= "2014年4月10日10:8";
		System.out.println(DateUtil.changeDate(ss));
	}
	
	public void test8(){
		/*long day=l/(24*60*60*1000);
		long hour=(l/(60*60*1000)-day*24);
		long min=((l/(60*1000))-day*24*60-hour*60);
		long s=(l/1000-day*24*60*60-hour*60*60-min*60);*/

		//String ss = "Fri Apr 11 08:00:00 GMT+08:00 2014";
		String ss= "Mon Feb 15 08:00:00 CST 2014";
		System.out.println(DateUtil.smartDate(ss,""));
	}
	public void test7(){
		String ss = "周一 3月 31 11:58:00 GMT+08:00 2014";
		//ss = DateUtil.saveDate(ss);
		Log.i("Http",ss);
	}
	
	public void test6(){
		String ss = "2014年03月11日 周二-16:30" ;
		ss = ss.replaceAll(" [^a]*\\-", "");
		//ss = ChangeDateUtil.changeDate("2014年3月11日6:3");
		Log.i("Http",ss);
	}
	
	public void test5(){

        //RequestParams params = new RequestParams();
        //params.addHeader("name", "value");
        //params.addQueryStringParameter("name", "value");

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                "http://www.baidu.com",
                //params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    	Log.i("Http" ,"onStart" ) ;
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                    	Log.i("Http" , "onSuccess" + responseInfo.result ) ;
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                    	Log.i("Http" ,"error==>" +  msg ) ;
                    }
                });
    }
	
	public void test4(){
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("uname", "中文");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "http://192.168.1.104:8080/ALLGO_SERVER/login",
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    	Log.i("Http" ,"onStart" ) ;
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                    	Log.i("Http" , "onSuccess" + responseInfo.result ) ;
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    	Log.i("Http" ,"error==>" +  msg ) ;
                    }
                });
	}
	public void test3(){

        RequestParams params = new RequestParams();
        params.addQueryStringParameter("uname", "中文");
        //params.addHeader("appkey" , "0000001");
        HttpUtils http = new HttpUtils();
        try {
            ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST,
            		"http://10.0.2.2:8080/ALLGO_SERVER/login", params);
            Log.i("Http" , responseStream.readString() ) ;
        } catch (Exception e) {
            LogUtils.e(e.getMessage(), e);
        }
    
	}
	
	
	public void test2(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");list.add("2");list.add("3");list.add("4");list.add("5");
		for(int i = 0 ; i<3 && i<list.size() ; i++){
			Log.i("DB","list==>" + list.get(i));
		}
	}
	public void test1(){
		ArrayList<FriendEventVo> eventsDate =new ArrayList<FriendEventVo>() ;
		eventsDate.add(new FriendEventVo(11002617,"去栖霞山爬山",123456,"千军万马1",
    			"Mon Feb 15 08:00:00 GMT+08:00 2014",null,"去栖霞山爬山","栖霞山",
    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
    			0,0));
		eventsDate.add(new FriendEventVo(11002618,"去栖霞山爬山",123456,"千军万马2",
    			"Mon Feb 15 08:00:00 GMT+08:00 2014",null,"去栖霞山爬山","栖霞山",
    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
    			0,0));
		eventsDate.add(new FriendEventVo(11002619,"去栖霞山爬山",123456,"千军万马3",
    			"Mon Feb 15 08:00:00 GMT+08:00 2014",null,"去栖霞山爬山","栖霞山",
    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
    			0,0));
		
		try{
		    DbUtils db = DbUtils.create(this.getContext(),"123456.db");
		    db.configAllowTransaction(true);
	        db.configDebug(true);
	        db.saveBindingIdAll(eventsDate);
	        
	        //List<EventVo> events = db.findAll(Selector.from(FriendEventVo.class));
	        //Log.i("DB", "Parents size:" + events.get(0) + "\n") ;

	        
		}catch(DbException e){
	    	Log.e("DB", "error :" + e.getMessage() + "\n");
	    }
	}
}
