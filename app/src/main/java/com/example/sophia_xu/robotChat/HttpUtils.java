package com.example.sophia_xu.robotChat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sophia_Xu on 2015/7/14.
 */
public class HttpUtils {

    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "2bff5686f22a409b7387c3210667aac7";

    public static String doGet(String msg){
        String result = "";
        String url = setParams(msg);
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        try {
            java.net.URL urlNet = new java.net.URL(url);
            HttpURLConnection con = (HttpURLConnection) urlNet.openConnection();
            con.setReadTimeout(5*1000);
            con.setConnectTimeout(5*1000);
            con.setRequestMethod("GET");

            is = con.getInputStream();
            int len = -1;
            byte [] buf = new byte[128];
            baos = new ByteArrayOutputStream();

            while((len = is.read())!= -1){
                baos.write(buf,0,len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }


    private static String setParams(String msg){

        String url = "";
        try {
            url = URL+"?key="+API_KEY+"&info="+ URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}
