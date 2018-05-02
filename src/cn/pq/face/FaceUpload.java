package cn.pq.face;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;




public class FaceUpload extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {  
        request.setCharacterEncoding("utf-8");  //设置编码  
        response.setContentType("text/html;charset=UTF-8");
        
        //获取文件需要上传到的路径  
        String path = this.getServletContext().getRealPath("/WEB-INF/upload");
        System.out.println(path);
        //路径不存则创建路径
        File file = new File(path);
        if(!file.exists()){
    	  file.mkdirs();
        }
      //获得磁盘文件条目工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        factory.setRepository(new File(path));  
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
        factory.setSizeThreshold(1024*1024) ;  
       //高水平的API文件上传处理  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        try {  
            //可以上传多个文件  
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);  
            
            boolean flag=FaceUtil.listFaceDetect(list,request,response); //判断所有图片都是不是人脸
             if(flag){
            	//所有图片都是人脸业务逻辑
            	 String uid=UUID.randomUUID().toString().replaceAll("-", "");//数据库表中主键
            	 Map<String, String> params = new HashMap<>();//存储文本参数
            	 params.put("uid", uid);
            	 int i=1;
            	for(FileItem item : list) {  
            		//如果获取的 表单信息是普通的 文本 信息  
                    if(item.isFormField()){                     
                    	String name = item.getFieldName();
                    	String value=item.getString("utf-8").trim() ;
                    	if(StringUtils.isBlank(value)){
                    			response.getWriter().write("{\"msg\":\"请把表单填写完整\"}");
                    			return;
                    	}
                    	params.put(name, value);
                    }else{ 
                        //存入文件夹
                        FaceUtil.pictureToFile(path, uid, i, item);
                        //存入百度人脸库
                        FaceUtil.toBaiDudb(item, uid);
                        i++;
                    }
                }
            	//将注册人信息存入数据库
            	FaceUtil.infoToDataBase(params); 
            	/*{
            		"msg": "add success",
            		"code": "0",
            		"data": [{
            			"persionid": "123",
            			"name": "heheda"
            		}]
            	}*/
            	response.getWriter().write("{\"msg\":\"add success!\",\"code\":\"0\",\"data\":[{\"persionId\":\""+uid+"\",\"username\":\""+params.get("username")+"\""
            			+ ",\"idcardnum\":\""+params.get("idcardnum")+"\",\"gender\":\""+params.get("gender")+"\",\"phone\":\""+params.get("phone")+"\",\"address\":\""+params.get("address")+"\"}]}");
           }else{
            	//图片不是人脸业务逻辑
            	response.getWriter().write("{\"msg\":\"no persion face!\",\"code\":\"1\"}");
           }  
        }catch(IOException e){
        	e.printStackTrace();
        	response.getWriter().write("{\"msg\":\"文件读写异常!\",\"code\":\"2\"}");
        }catch(SQLException e){
        	e.printStackTrace();  
        	response.getWriter().write("{\"msg\":\"文本信息存储异常!\",\"code\":\"3\"}");
        }catch(JSONException e){
        	e.printStackTrace();  
        	response.getWriter().write("{\"msg\":\"图片格式异常!\",\"code\":\"4\"}");
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            response.getWriter().write("{\"msg\":\"系统异常!\",\"code\":\"5\"}");
        }  
    }  //dopost结束
}
