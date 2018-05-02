package cn.pq.test;

import cn.pq.test.util.HttpUtil;

public class FaceDelete {
	 /**
	    * 重要提示代码中所需工具类
	    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
	    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	    * 下载
	    */
	    public static String delete() {
	        // 请求url
	        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
	        try {
	            String param = "uid=" + "test_user_4";

	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            String accessToken ="24.983a303267e5a5cb9768da20ce14c8df.2592000.1526015338.282335-11079244";

	            String result = HttpUtil.post(url, accessToken, param);
	            System.out.println(result);
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static void main(String[] args) {
	        FaceDelete.delete();
	    }
}
