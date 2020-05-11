package address.utils;

public class MyStringParser {
	
	public static int getId(String selectedList) { // 리턴이 있으면 무조건 get 을 붙이자.  
		return Integer.parseInt(selectedList.split("[.]")[0]);
				
	}
}
