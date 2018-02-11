package com.dingtalk.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public class DingHttps{


public String getAttendanceRecord(String access_token,String employeeId,String starttime,String endtime){
	String url = "https://oapi.dingtalk.com/attendance/list?access_token=" + access_token;
	String param ="{"
	+ "\"workDateFrom\": \""+ starttime +" 00:00:00\","
	+ "\"workDateTo\": \""+ endtime +" 00:00:00\","
	+ "\"userIdList\":[\"" + employeeId + "\"],"
	+ "\"offset\":0,"
	+ "\"limit\":50,"
	+ "}";
	String result = sendPost(url, param);
	
	return result;
}
public JSONArray getDepartment(String  access_token){
   	String url = "https://oapi.dingtalk.com/department/list?access_token="+access_token;
	JSONArray department = new JSONArray();

    try {

        URL urlGet = new URL(url);

        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

        http.setRequestMethod("GET"); // 必须是get方式请求
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setDoOutput(true);
        http.setDoInput(true);

        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

        http.connect();

        InputStream is = http.getInputStream();
        int size = is.available();

        byte[] jsonBytes = toByteArray(is);
        String message = new String(jsonBytes, "UTF-8");
        //byte[] jsonBytes = new byte[size];
        /*String result = "";
        
        int n = 0;
        while (-1 !=(n=is.read(jsonBytes))) {
        	String message = new String(jsonBytes, "UTF-8");
        	System.out.println("========"+message.substring(0, n));
        	System.out.println(n);
        	result = result+message.substring(0, n); 
        	
		}
        System.out.println("result======="+result);*/
        JSONObject demoJson =JSONObject.fromObject(message);

        department = demoJson.getJSONArray("department");

       

        is.close();

    } catch (Exception e) {

        e.printStackTrace();

    }
    System.out.println(department);
    return department;
}
public JSONArray getUser(String access_token,String department_id){
	String url = "https://oapi.dingtalk.com/user/simplelist?access_token=" + access_token+ "&department_id=" + department_id;

	JSONArray userlist = new JSONArray();

    try {

        URL urlGet = new URL(url);

        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

        http.setRequestMethod("GET"); // 必须是get方式请求

        //http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        http.setDoOutput(true);

        http.setDoInput(true);

        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

        http.connect();

        InputStream is = http.getInputStream();

        int size = is.available();

        byte[] jsonBytes = new byte[size];

        is.read(jsonBytes);

        String message = new String(jsonBytes, "UTF-8");

        JSONObject demoJson =JSONObject.fromObject(message);

        userlist = demoJson.getJSONArray("userlist");

       

        is.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

    return userlist;
}
	
public  String getAccess_token(String corpid,String corpsecret) {

    String url = "https://oapi.dingtalk.com/gettoken?corpid=" + corpid+ "&corpsecret=" + corpsecret;

    String accessToken = null;

    try {

        URL urlGet = new URL(url);

        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

        http.setRequestMethod("GET"); // 必须是get方式请求

        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        http.setDoOutput(true);

        http.setDoInput(true);

        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

        http.connect();

        InputStream is = http.getInputStream();

        int size = is.available();

        byte[] jsonBytes = new byte[size];

        is.read(jsonBytes);

        String message = new String(jsonBytes, "UTF-8");

        JSONObject demoJson =JSONObject.fromObject(message);

        accessToken = demoJson.getString("access_token");

        is.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

    return accessToken;

}




public static String sendPost(String url, String param) {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性 注意Authorization生成
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),"utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
    } catch (Exception e) {
        System.out.println("发送 POST 请求出现异常！" + e);
        e.printStackTrace();
    }
    // 使用finally块来关闭输出流、输入流
    finally {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    return result;
}

private static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
        output.write(buffer, 0, n);
    }
    return output.toByteArray();
}
}