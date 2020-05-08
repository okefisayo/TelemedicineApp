package xyz.cglzwz.chatroom.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class time {
    private final static DateFormat format=new SimpleDateFormat("MM/dd/yyyy");
    public boolean isExpire(String date){
        //DateFormat format=new SimpleDateFormat("MM/dd/yyyy");
        try{
            Date now=format.parse(date);
            if(System.currentTimeMillis()>now.getTime()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean OneDayBefore(String date){
        String[] date1=date.split("/");
        try{
            Date now=format.parse(date);
            if(System.currentTimeMillis()>now.getTime()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
}
