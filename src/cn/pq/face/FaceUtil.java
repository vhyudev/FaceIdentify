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
	 * �ж�form�������ý���ļ��Ƿ�������
	 * @param list ����Ŀ
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
                  //����json���
                  JSONObject jsonObj1 = new JSONObject(info);
                  Object result_num=null;
                  result_num = jsonObj1.get("result_num");
				 if(Double.parseDouble(result_num.toString())==0){//��������ͼƬ
                	  //repo.getWriter().write("{\"msg\":ͼƬ��������!}");
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
		     	//���������ж�                                                         	
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
     	                                                                 	
		     	//���Ƿ�������                                                            
 				if(result_numd<=0||blurd>=0.7||yawd>=20||pitchd>=20||rolld>=20||
 						  mouthd>0.7||left_eyed>0.6||right_eyed>0.6||nosed>0.7||
 						  left_checkd>0.8||right_checkd>0.8||chin_contourd>0.6){
 					//����
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
	 * ͼƬ�����������
	 * @param path �ļ��洢·��
	 * @param filename �ļ���(��Ӧ�������� �������������� ͼƬid)
	 * @param num (���ڶ���ͼƬ���)
	 * @param item(ͼƬ�ļ�����Ŀ)
	 * @throws IOException 
	 */
	 public static void pictureToFile(String path,String filename,Integer num,FileItem item) throws IOException{
		  
			  OutputStream out = new FileOutputStream(new File(path,filename+"("+num+").jpg")); 
		      InputStream in = item.getInputStream() ;  
		      int length = 0 ;  
		      byte [] buf = new byte[1024] ;  
		      while( (length = in.read(buf) ) != -1){  
		          //��   buf ������ ȡ������ д�� ���������������  
		          out.write(buf, 0, length);  
		      }  
		      in.close();  
		      out.close();  
	  
	  }
	 
	
	 /**
	  *  ��ͼƬ���ٶ�������
	  * @param item(ͼƬ�ļ�����Ŀ)
	  * @param uid �û�������Ƭ��id
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
	 * @param params map���Ϸ�װ���ı�����
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
				
				return "{\"sign\":\"0\",\"errmsg\":\"���ýӿ�ʧ��\"}";
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
					return "{\"sign\":\"0\",\"flag\":\"1\",\"msg\":\"δƥ�䵽����\",\"score\":\""+score+"\"}";
					
				}else{
					//����uid��ѯ��Ա��Ϣ
					PersonInfo personInfo;
					personInfo = FaceUtil.queryByKey(uId);
					
					 if(personInfo == null){
						 return "{\"sign\":\"0\",\"flag\":\"2\",\"msg\":\"ʶ��ɹ�����δ�鵽��Ϣ\"}";
					 }else{
						//��ȡ��Ա��Ϣ
						 
						 
						 
						 
						 return "{\"sign\":\"0\",\"flag\":\"0\",\"msg\":\"�ɹ�\",\"personId\":\""+uId+"\"}";
					 } 			
				}		 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "{\"sign\":\"0\",\"flag\":\"1\",\"msg\":\"json����ʧ��\"}" ;
			}catch(SQLException e){
				e.printStackTrace();
				return "{\"sign\":\"0\",\"flag\":\"1\",\"msg\":\"���ݿ��ѯʧ��\"}";
			}	
		}
	 
	 public static Map<String,String> handleParam(HttpServletRequest req){
		 BASE64Encoder encoder = new BASE64Encoder();
		 //��ȡ�ϴ����ļ�
		 DiskFileItemFactory factory = new DiskFileItemFactory();
        // �õ������ļ���·��������("D://tomcat6//webapps//test//upload")
//	        String path = req.getSession().getServletContext().getRealPath("/upload");
//	        System.out.println("����·��"+path);
//	        // ��ʱ�ļ���·��
//	        System.out.println(req.getContentType());
        String repositoryPath = req.getSession().getServletContext().getRealPath("/upload/temp");
        System.out.println("���·��"+repositoryPath);
        File file = new File(repositoryPath);
        if(!file.exists()){
    	  file.mkdirs();
        }
        // �趨��ʱ�ļ���ΪrepositoryPath
        factory.setRepository(new File(repositoryPath));
        // �趨�ϴ��ļ���ֵ������ϴ��ļ�����1M���Ϳ�����repository
        // ��������ļ����в�����ʱ�ļ�������ֱ�����ڴ��н��д���
        factory.setSizeThreshold(1024 * 1024);
        // ��ͻ��˷��ؽ��
    
        // ����һ��ServletFileUpload����
        ServletFileUpload uploader = new ServletFileUpload(factory);
        InputStream in = null;
       
        Map<String,String> paramMap = new HashMap<String,String>();//����map
        String imgCodes = "";//�����ͼƬbase64code ���м��ö��Ÿ���
        try {

            /**
             * ����uploader�е�parseRequest���������Ի�������е�������ݣ� ��һ��FileItem���͵�ArrayList��
             * FileItem��ָorg.apache.commons.fileupload�ж���ģ������Դ���һ���ļ���
             * Ҳ���Դ���һ����ͨ��formfield
             */
            ArrayList<FileItem> list = (ArrayList<FileItem>) uploader
                    .parseRequest(req);
            
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {// �������ͨ��formfield
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    paramMap.put(name, value);
                    System.out.println(name + ":" + value);
                } else {// ������ļ�
                	String imaCode = "";//ͼƬbasse64��
                    String value = fileItem.getName();
                    // value.lastIndexOf("\\")���ء�\\�������ֵ�λ���±�
                    int start = value.lastIndexOf("\\");
                    // substring ��ȡ�ַ�����ʽ֮һ
                    String fileName = value.substring(start + 1);
                    // �����а���������д��path()�£���uploadĿ¼�£���ΪfileName���ļ���
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
