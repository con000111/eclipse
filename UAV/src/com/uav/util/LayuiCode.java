package com.uav.util;

import java.util.HashMap;
import java.util.List;

public class LayuiCode  extends HashMap<String, Object> {


	public static LayuiCode data(Integer count,List<?> data){
		LayuiCode re=new LayuiCode();
		re.put("code",0);
		re.put("msg","Ğìsir");
		re.put("count", count);
		re.put("data",data);
		return re;
	}

}
