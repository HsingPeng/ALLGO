package cn.edu.njupt.allgo.util;

import java.util.ArrayList;

import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UnreadVo;

public class ArrayListUtil {

	/**
	 * 对EventVo进行从大到小排序
	 * @param list
	 */
	public static void sortEventVo (ArrayList<EventVo> list) {
		
 	   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
 	     for ( int j = list.size() - 1 ; j > i; j -- ) {
 	       if (list.get(j).getEid() > list.get(i).getEid()) {
 	         EventVo eventVo = list.get(i);
 	         list.set(i, list.get(j));
 	         list.set(j, eventVo);
 	       }
 	      }
 	    }
 	   //return list ;
 	}
	
	/**
	 * 对UnreadVo进行从大到小排序
	 * @param list
	 */
	public static void sortUnreadVo(ArrayList<UnreadVo> list) {
		
	 	   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
	 	     for ( int j = list.size() - 1 ; j > i; j -- ) {
	 	       if (list.get(j).getRemindid() > list.get(i).getRemindid()) {
	 	         UnreadVo unreadVo = list.get(i);
	 	         list.set(i, list.get(j));
	 	         list.set(j, unreadVo);
	 	       }
	 	      }
	 	    }
	 	}

	/**
	 * 去除重复
	 * @param list
	 */
    public static <E> void removeDuplicate(ArrayList<E> list) {
    	   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
    	     for ( int j = list.size() - 1 ; j > i; j -- ) {
    	       if (list.get(j).equals(list.get(i))) {
    	         list.remove(j);
    	       }
    	      }
    	   }
    	}
}
