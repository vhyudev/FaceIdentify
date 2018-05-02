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
        request.setCharacterEncoding("utf-8");  //���ñ���  
        response.setContentType("text/html;charset=UTF-8");
        
        //��ȡ�ļ���Ҫ�ϴ�����·��  
        String path = this.getServletContext().getRealPath("/WEB-INF/upload");
        System.out.println(path);
        //·�������򴴽�·��
        File file = new File(path);
        if(!file.exists()){
    	  file.mkdirs();
        }
      //��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        factory.setRepository(new File(path));  
        //���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��  
        factory.setSizeThreshold(1024*1024) ;  
       //��ˮƽ��API�ļ��ϴ�����  
        ServletFileUpload upload = new ServletFileUpload(factory);  
        try {  
            //�����ϴ�����ļ�  
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);  
            
            boolean flag=FaceUtil.listFaceDetect(list,request,response); //�ж�����ͼƬ���ǲ�������
             if(flag){
            	//����ͼƬ��������ҵ���߼�
            	 String uid=UUID.randomUUID().toString().replaceAll("-", "");//���ݿ��������
            	 Map<String, String> params = new HashMap<>();//�洢�ı�����
            	 params.put("uid", uid);
            	 int i=1;
            	for(FileItem item : list) {  
            		//�����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ  
                    if(item.isFormField()){                     
                    	String name = item.getFieldName();
                    	String value=item.getString("utf-8").trim() ;
                    	if(StringUtils.isBlank(value)){
                    			response.getWriter().write("{\"msg\":\"��ѱ���д����\"}");
                    			return;
                    	}
                    	params.put(name, value);
                    }else{ 
                        //�����ļ���
                        FaceUtil.pictureToFile(path, uid, i, item);
                        //����ٶ�������
                        FaceUtil.toBaiDudb(item, uid);
                        i++;
                    }
                }
            	//��ע������Ϣ�������ݿ�
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
            	//ͼƬ��������ҵ���߼�
            	response.getWriter().write("{\"msg\":\"no persion face!\",\"code\":\"1\"}");
           }  
        }catch(IOException e){
        	e.printStackTrace();
        	response.getWriter().write("{\"msg\":\"�ļ���д�쳣!\",\"code\":\"2\"}");
        }catch(SQLException e){
        	e.printStackTrace();  
        	response.getWriter().write("{\"msg\":\"�ı���Ϣ�洢�쳣!\",\"code\":\"3\"}");
        }catch(JSONException e){
        	e.printStackTrace();  
        	response.getWriter().write("{\"msg\":\"ͼƬ��ʽ�쳣!\",\"code\":\"4\"}");
        }catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            response.getWriter().write("{\"msg\":\"ϵͳ�쳣!\",\"code\":\"5\"}");
        }  
    }  //dopost����
}
