package cn.pq.test.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetBase64 {
	public static String get64(InputStream is){
		 BufferedInputStream bf = new BufferedInputStream(is);
         ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
                 byte[] buff = new byte[1024];  
                int rc = 0;  
               try {
				while (-1 !=(rc = bf.read(buff, 0, 1024))) {  
				        swapStream.write(buff, 0, rc);  
				      }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
                byte[] in2b = swapStream.toByteArray();  
             
         
                String image = Base64Util.encode(in2b);//»ñÈ¡Í¼Æ¬µÄbase64Âë
                return image;
	}
}
