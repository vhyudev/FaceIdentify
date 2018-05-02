package cn.pq.test;

import java.net.URLEncoder;

import cn.pq.face.Quantity;
import cn.pq.test.util.Base64Util;
import cn.pq.test.util.FileUtil;
import cn.pq.test.util.HttpUtil;

public class FaceDetect {
	/**
	    * ��Ҫ��ʾ���������蹤����
	    * FileUtil,Base64Util,HttpUtil,GsonUtils���
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * ����
	    */
	    public static String detect(String image,String max_face_num,String face_fields) {
	        // ����url
	        String url = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
	        try {
	            // �����ļ�·��
	           /* String filePath = filepath;
	            byte[] imgData = FileUtil.readFileByBytes(filePath);
	            String imgStr = Base64Util.encode(imgData);*/
	            String imgParam = URLEncoder.encode(image, "UTF-8");

	            String param = "max_face_num=" + max_face_num + "&face_fields=" + face_fields + "&image=" + imgParam;
	           
	            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
	            String accessToken = AuthService.getAuth();

	            String result = HttpUtil.post(url, accessToken, param);
	      
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	  
}
