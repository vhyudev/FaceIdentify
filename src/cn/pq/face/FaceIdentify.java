package cn.pq.face;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pq.faceService.FaceService;
import cn.pq.faceServiceImpl.BaiDuFaceService;
import cn.pq.test.util.HttpUtil;
import sun.misc.BASE64Encoder;
/**
 * 人脸检测接口
 * @author Administrator
 *
 */
public class FaceIdentify extends HttpServlet{
	 @Override
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
		 
		 String result = "";//平台返回的信息
		 OutputStream out=resp.getOutputStream();
		 resp.setCharacterEncoding("UTF-8");
		 resp.setHeader("Content-Type", "text/html;charset=UTF-8");
		 //封装参数
		 Map<String,String>paramMap = FaceUtil.handleParam(req);
		 if(paramMap == null){
			out.write("{\"sign\":\"1\",\"errmsg\":\"系统错误\"}".getBytes("UTF-8"));
		    out.flush();
		    out.close();
		 }
   		 FaceService  faceService =GetInstance.getFS();
   		 //调用百度人脸查找接口
   		result = faceService.identify(paramMap.get("imgCodes"), paramMap.get("group_id"), paramMap.get("ext_fields"), paramMap.get("user_top_num"));
        
        String response = FaceUtil.resultHandle(result);
		out.write(response.getBytes("UTF-8"));
	    out.flush();
	    out.close();

	}
		

	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是doPost()方法...");
        doGet(req,resp);
	 }
}
