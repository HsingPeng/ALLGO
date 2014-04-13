package cn.edu.njupt.allgo.logic;

public interface AddEventLogic {

	/**
	 * 
	 * @param outline
	 * @param startdate
	 * @param enddate
	 * @param content
	 * @param place
	 * @param position
	 * @param ecategoryname
	 * @param visible
	 */
	public void addEvent(String outline,String startdate,
			String enddate,String content,String place,String position ,String ecategoryname,int visible);

	
}

