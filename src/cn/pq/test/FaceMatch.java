package cn.pq.test;

import java.net.URLEncoder;

import cn.pq.test.util.Base64Util;
import cn.pq.test.util.FileUtil;
import cn.pq.test.util.HttpUtil;

public class FaceMatch {
	 /**
	    * ��Ҫ��ʾ���������蹤����
	    * FileUtil,Base64Util,HttpUtil,GsonUtils���
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * ����
	    */
	    public static String match() {
	        // ����url
	        String url = "https://aip.baidubce.com/rest/2.0/face/v2/match";
	        try {
	            // �����ļ�·��
	            String filePath = "E:\\face\\4c622e27e61d1e188a90d5127d711b09.jpg";
	            byte[] imgData = FileUtil.readFileByBytes(filePath);
	            String imgStr = Base64Util.encode(imgData);
	            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

	            String filePath2 = "E:\\face\\9a8deac9f7453045b07ff5dd8948ea9a.jpg";
	            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
	            String imgStr2 = Base64Util.encode(imgData2);
	            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

	            String param = "images=" + imgParam + "," + imgParam2;

	            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
	            String accessToken = "24.983a303267e5a5cb9768da20ce14c8df.2592000.1526015338.282335-11079244";

	            String result = HttpUtil.post(url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static void main(String[] args) throws Exception {
	    	String post = HttpUtil.post("http://localhost:8080/FaceIdentify/FaceDetect", null, "filepath=E:\\face\\4c622e27e61d1e188a90d5127d711b09.jpg");
	    	System.out.println(post);
	    }
}
