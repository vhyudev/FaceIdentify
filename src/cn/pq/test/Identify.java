package cn.pq.test;

import java.net.URLEncoder;
import java.util.Date;

import cn.pq.test.util.Base64Util;
import cn.pq.test.util.FileUtil;
import cn.pq.test.util.HttpUtil;

public class Identify {
	/**
	    * ��Ҫ��ʾ���������蹤����
	    * FileUtil,Base64Util,HttpUtil,GsonUtils���
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * ����
	    */
	    public static String identify(String image,String group_id,String ext_fields,String user_top_num) {
	        // ����url
	        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
	        try {
	        
	            String imgParam = URLEncoder.encode(image, "UTF-8");

	           

	            String param = "group_id=" + group_id + "&user_top_num=1" + "&images=" + imgParam;

	            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
	            String accessToken = AuthService.getAuth();

	            String result = HttpUtil.post(url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static void main(String[] args) {
	    	
	    		/*Date date1 = new Date();
		    	long time1 = date1.getTime();
		        Identify.identify("E:/face/f292c038dae83fb65fa7576da8b0a436.jpg");
		    	Date date2 = new Date();
		        long time2 = date2.getTime();
		        System.out.println(time2-time1);*/
	    	 
	    }
}
