package cn.pq.face;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pq.entity.PersonInfo;
import cn.pq.test.util.DataSourceUtils;
import cn.pq.test.util.GetBase64;
import sun.misc.BASE64Encoder;
public class FaceUtil {
	/**
	 * 判断form表单传入的媒体文件是否是人脸
	 * @param list 表单条目
	 * @param re
	 * @param repo
	 * @return
	 * @throws Exception
	 */
	public static boolean listFaceDetect(List<FileItem> list,HttpServletRequest re,HttpServletResponse repo) throws JSONException, Exception{
		boolean b=false;
		 for(FileItem item : list)  
         {  
           if(!item.isFormField()){  
            	  InputStream is = item.getInputStream();
           		  String image = GetBase64.get64(is);
           		  String info=GetInstance.getFS().detect(image, "1", "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
                  //解析json结果
                  JSONObject jsonObj1 = new JSONObject(info);
                  Object result_num=null;
                  result_num = jsonObj1.get("result_num");
				 if(Double.parseDouble(result_num.toString())==0){//不是人脸图片
                	  //repo.getWriter().write("{\"msg\":图片不是人脸!}");
                 	return false;
                  }
                  JSONArray result =(JSONArray)jsonObj1.get("result");
                  Object result2 = result.get(0);
                  
                  JSONObject jsonObj =new JSONObject(result2.toString());
                  
	              Object yaw = jsonObj.get("yaw");
	              Object pitch = jsonObj.get("pitch");
	              Object roll = jsonObj.get("roll");
	              Object obj = jsonObj.get("qualities");
	                    
		     	  JSONObject jsonObj2 = new JSONObject(obj.toString());
		     	  
		     	  Object blur = jsonObj2.get("blur");                                
		     	  Object obj2= jsonObj2.get("occlusion");  
		     	  
		     	  JSONObject jsonObj3 = new JSONObject(obj2.toString()); 
		     	  
		     	  Object left_eye = jsonObj3.get("left_eye");                      	
		     	  Object right_eye = jsonObj3.get("right_eye");                    	
		     	  Object nose = jsonObj3.get("nose");                              	
		     	  Object mouth = jsonObj3.get("mouth");                            	
		     	  Object left_check = jsonObj3.get("left_cheek");                  	
		     	  Object right_check = jsonObj3.get("right_cheek");                	
		     	  Object chin_contour = jsonObj3.get("chin");                      	
		     	//人脸参数判断                                                         	
		     	double result_numd=Double.parseDouble(result_num.toString());    	
		     	double blurd=Double.parseDouble(blur.toString());                	
		     	double yawd=Double.parseDouble(yaw.toString());                  	
		     	double pitchd=Double.parseDouble(pitch.toString());              	
		     	double rolld=Double.parseDouble(roll.toString());                	
		     	double left_eyed=Double.parseDouble(left_eye.toString());        	
		     	double right_eyed=Double.parseDouble(right_eye.toString());      	
		     	double nosed=Double.parseDouble(nose.toString());                	
		     	double mouthd=Double.parseDouble(mouth.toString());              	
		     	double left_checkd=Double.parseDouble(left_check.toString());    	
		     	double right_checkd=Double.parseDouble(right_check.toString());  	
		     	double chin_contourd=Double.parseDouble(chin_contour.toString());	
     	                                                                 	
		     	//断是否是人脸                                                            
 				if(result_numd<=0||blurd>=0.7||yawd>=20||pitchd>=20||rolld>=20||
 						  mouthd>0.7||left_eyed>0.6||right_eyed>0.6||nosed>0.7||
 						  left_checkd>0.8||right_checkd>0.8||chin_contourd>0.6){
 					//不是
 					b=false;
 					break;
 				}else{
 					b=true;
 				}
 	
             }    
         } 
		return b;
	}
	/**
	 * 图片存入服务器中
	 * @param path 文件存储路径
	 * @param filename 文件名(对应此人数据 主键和人脸库中 图片id)
	 * @param num (用于多张图片编号)
	 * @param item(图片文件的条目)
	 * @throws IOException 
	 */
	 public static void pictureToFile(String path,String filename,Integer num,FileItem item) throws IOException{
		  
			  OutputStream out = new FileOutputStream(new File(path,filename+"("+num+").jpg")); 
		      InputStream in = item.getInputStream() ;  
		      int length = 0 ;  
		      byte [] buf = new byte[1024] ;  
		      while( (length = in.read(buf) ) != -1){  
		          //在   buf 数组中 取出数据 写到 （输出流）磁盘上  
		          out.write(buf, 0, length);  
		      }  
		      in.close();  
		      out.close();  
	  
	  }
	 
	
	 /**
	  *  存图片到百度人脸库
	  * @param item(图片文件的条目)
	  * @param uid 用户人脸照片的id
	  * @return
	 * @throws IOException 
	  */
	 public static String toBaiDudb(FileItem item ,String uid) throws IOException{
		 
				InputStream is = item.getInputStream();
				String image = GetBase64.get64(is);
				return GetInstance.getFS().add(uid, "hyqp_1", image, "HYPQ_user", "append");	
		}
	 
	/**
	 * 
	 * @param params map集合封装的文本参数
	 * @throws SQLException 
	 */
	 public static void infoToDataBase(Map<String, String> params) throws SQLException{
			QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	        String sql="insert into userinfo(userid,name,idcard,gender,address,phone,date) values(?,?,?,?,?,?,?) ";
			Date nowTime=new Date(); 
			SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String ftime = time.format(nowTime); 
			
			qr.update(sql,params.get("uid"),params.get("username"),params.get("idcardnum"),
			params.get("gender"),params.get("address"),params.get("phone"),ftime);
			
		}
	 //--------------
	 public static PersonInfo queryByKey(String uId) throws SQLException{
		 QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		 String sql = "select * from userinfo where userid=?";

		 PersonInfo personInfo = qr.query(sql, uId, new BeanHandler<>(PersonInfo.class));
		 
		 return personInfo;
		
	 }
	 public static String resultHandle(String result){
			JSONObject jo = null;
			JSONArray ja = null;
			String uId = "";
			if("".equals(result)){
				
				return "{\"sign\":\"0\",\"errmsg\":\"调用接口失败\"}";
			}
			try {
				jo = new JSONObject(result);
				ja = jo.getJSONArray("result");
				JSONObject face = ja.getJSONObject(0);
				JSONArray scores = face.getJSONArray("scores");
				String score = scores.getString(0);
				double scoreD = Double.parseDouble(score);
				uId = face.getString("uid");
				String userInfo = face.getString("user_info");
				if(scoreD<80){
					return "{\"sign\":\"0\",\"flag\":\"1\",\"msg\":\"未匹配到人脸\",\"score\":\""+score+"\"}";
					
				}else{
					//根据uid查询人员信息
					PersonInfo personInfo;
					personInfo = FaceUtil.queryByKey(uId);
					
					 if(personInfo == null){
						 return "{\"sign\":\"0\",\"flag\":\"2\",\"msg\":\"识别成功，但未查到信息\"}";
					 }else{
						//获取人员信息
						 
						 
						 
						 
						 return "{\"sign\":\"0\",\"flag\":\"0\",\"msg\":\"成功\",\"personId\":\""+uId+"\"}";
					 } 			
				}		 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "{\"sign\":\"0\",\"flag\":\"1\",\"msg\":\"json解析失败\"}" ;
			}catch(SQLException e){
				e.printStackTrace();
				return "{\"sign\":\"0\",\"flag\":\"1\",\"msg\":\"数据库查询失败\"}";
			}	
		}
	 
	 public static Map<String,String> handleParam(HttpServletRequest req){
		 BASE64Encoder encoder = new BASE64Encoder();
		 //获取上传的文件
		 DiskFileItemFactory factory = new DiskFileItemFactory();
        // 得到绝对文件夹路径，比如("D://tomcat6//webapps//test//upload")
//	        String path = req.getSession().getServletContext().getRealPath("/upload");
//	        System.out.println("绝对路径"+path);
//	        // 临时文件夹路径
//	        System.out.println(req.getContentType());
        String repositoryPath = req.getSession().getServletContext().getRealPath("/upload/temp");
        System.out.println("相对路径"+repositoryPath);
        File file = new File(repositoryPath);
        if(!file.exists()){
    	  file.mkdirs();
        }
        // 设定临时文件夹为repositoryPath
        factory.setRepository(new File(repositoryPath));
        // 设定上传文件的值，如果上传文件大于1M，就可能在repository
        // 所代表的文件夹中产生临时文件，否则直接在内存中进行处理
        factory.setSizeThreshold(1024 * 1024);
        // 向客户端返回结果
    
        // 创建一个ServletFileUpload对象
        ServletFileUpload uploader = new ServletFileUpload(factory);
        InputStream in = null;
       
        Map<String,String> paramMap = new HashMap<String,String>();//参数map
        String imgCodes = "";//存多张图片base64code ，中间用逗号隔开
        try {

            /**
             * 调用uploader中的parseRequest方法，可以获得请求中的相关内容， 即一个FileItem类型的ArrayList。
             * FileItem是指org.apache.commons.fileupload中定义的，他可以代表一个文件，
             * 也可以代表一个普通的formfield
             */
            ArrayList<FileItem> list = (ArrayList<FileItem>) uploader
                    .parseRequest(req);
            
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {// 如果是普通的formfield
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    paramMap.put(name, value);
                    System.out.println(name + ":" + value);
                } else {// 如果是文件
                	String imaCode = "";//图片basse64码
                    String value = fileItem.getName();
                    // value.lastIndexOf("\\")返回“\\”最后出现的位置下标
                    int start = value.lastIndexOf("\\");
                    // substring 截取字符串方式之一
                    String fileName = value.substring(start + 1);
                    // 将其中包含的内容写到path()下，即upload目录下，名为fileName的文件中
//	                    fileItem.write(new File(path, fileName));
                    in = fileItem.getInputStream();
                    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
                    byte[] buff = new byte[1024];  
                    int rc = 0;  
                    while ((rc = in.read(buff, 0, 100)) > 0) {  
                       swapStream.write(buff, 0, rc);  
                    }  
                    byte[] in2b = swapStream.toByteArray();  

                    imaCode = "".equals(imaCode)?encoder.encode(in2b):imaCode+","+encoder.encode(in2b);
                    imgCodes = "".equals(imgCodes)? imaCode:imgCodes + "," +imaCode;
                }
                
                
            }
            paramMap.put("imgCodes", imgCodes);
        }catch (Exception e) {
	         e.printStackTrace();
	        
		        return null;
	     }finally{
	     	if(in != null){
	     		try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	     	}
	     	
	     }
        return paramMap;
	 }
	
}
