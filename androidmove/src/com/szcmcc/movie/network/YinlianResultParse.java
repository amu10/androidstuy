package com.szcmcc.movie.network;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.szcmcc.movie.bean.YinlianResultBean;

public class YinlianResultParse {  
   public static ArrayList<YinlianResultBean> getBean(InputStream inputStream) throws Exception{  
      ArrayList<YinlianResultBean> books = null;  
      YinlianResultBean bean = null;  
       XmlPullParser parser = Xml.newPullParser();  
      parser.setInput(inputStream, "UTF-8");  
         
       int event = parser.getEventType();//产生第一个事件  
        while(event!=XmlPullParser.END_DOCUMENT){  
           switch(event){  
            case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
                books = new ArrayList<YinlianResultBean>();//初始化books集合  
            	
              break;  
            case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件  
                if("upomp".equals(parser.getName())){//判断开始标签元素是否是book  
                	bean = new YinlianResultBean(); 
                	bean.pluginVersion = parser.getAttributeValue(1);//得到book标签的属性值，并设置book的id  
               } 
                if(bean!=null){  
                   if("merchantId".equals(parser.getName())){//判断开始标签元素是否是name  
                	   bean.merchantId = parser.nextText();
                   }else if("respCode".equals(parser.getName())){
                	   bean.respCode = parser.nextText();
                   }else if("respDesc".equals(parser.getName())){
                	   bean.respDesc = parser.nextText();
                   }
               }  
                break;  
            case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件  
               if("upomp".equals(parser.getName())){//判断结束标签元素是否是book  
                    books.add(bean);//将book添加到books集合  
                    bean = null;  
                }  
                break;  
         }  
            event = parser.next();//进入下一个元素并触发相应事件  
        }//end while  
       return books;  
   }  
}  
