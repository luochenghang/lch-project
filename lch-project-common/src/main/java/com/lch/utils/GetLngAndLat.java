package com.lch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GetLngAndLat {
//https://apis.map.qq.com/ws/geocoder/v1/?address=湖北省天门市&key=TPJBZ-CNWRU-NWLVS-B7C3E-64VP2-DPFNG
    public static final String map_url_top = "https://apis.map.qq.com/ws/geocoder/v1/?address=";

    //https://lbs.qq.com  首先在腾讯地图上注册/登录
    //https://lbs.qq.com/console/mykey.html?console=mykey  然后申请KEY：
    public static final String map_key = "&key=TPJBZ-CNWRU-NWLVS-B7C3E-64VP2-DPFNG";


    public static Location getURLContent(String address) {
        String urlStr =map_url_top + address + map_key;
        //请求的url
        URL url = null;
        //请求的输入流
        BufferedReader in = null;
        //输入流的缓冲
        StringBuffer sb = new StringBuffer();
        try{
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8") );
            String str = null;
            //一行一行进行读入
            while((str = in.readLine()) != null) {
                sb.append( str );
            }
        } catch (Exception ex) {

        } finally{
            try{
                if(in!=null) {
                    in.close(); //关闭流
                }
            }catch(IOException ex) {

            }
        }
        String result =sb.toString();
        Address a = (Address) JsonUtils.fromJsonString(result, Address.class);
        Location location = null;
        if (a!=null && a.getMessage().equals("query ok")){
            location = a.getResult().getLocation();
        }
        return location;
    }

    public static void main(String[] args) {
        Location s = getURLContent("湖北");


        System.out.println(s);
//        System.out.println(address.getResult().getLocation().getLat());
//        System.out.println(address.getResult().getLocation().getLng());
    }
}
