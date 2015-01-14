package com.szcmcc.movie.map;

import java.util.Comparator;



/**
 * ���ռ۸�����
 * @author huangyu
 *
 */
public class ComparatorHotelDistance implements Comparator<MapPoi> {

	@Override
	public int compare(MapPoi arg0, MapPoi arg1) {
		
		MapPoi hotel0=arg0;
		MapPoi hotel1=arg1;
		
		double distance0 = hotel0.getDistance();
		double distance1 = hotel1.getDistance();
		
		 if(!"".equals(distance0) && !"null".equals(distance0)
				 && !"".equals(distance1) && !"null".equals(distance1)){
			 double val= (distance0 - distance1);
			 return val>0?1:(val==0?0:-1);
		 }else if("".equals(distance0) && "".equals(distance1)){
			 return 0;
		 }else if(!"".equals(distance0) && !"null".equals(distance0) &&
				 "".equals(distance1)){
			 return -1;  
		 }else if(!"".equals(distance0) && !"null".equals(distance0) &&
				 "null".equals(distance1)){
			 return -1;
		 } else if("".equals(distance0) && !"".equals(distance1)
				 && !"null".equals(distance1)){
			 return 1;
		 }else if("".equals(distance0)&&"null".equals(distance1)){
			 return 0;
		 }else if("null".equals(distance0) && "".equals(distance1)){
			 return 0;
		 }else if("null".equals(distance0) && "null".equals(distance1)){
			 return 0;
		 }else if("null".equals(distance0) && !"".equals(distance1)
				 &&!"null".equals(distance1)){
			 return 1;
		 }
		 
		 
		 
		  return 0;
	}
}
