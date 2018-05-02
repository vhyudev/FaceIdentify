package cn.pq.faceServiceImpl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pq.faceService.FaceService;
import cn.pq.test.FaceAdd;
import cn.pq.test.FaceDetect;
import cn.pq.test.Identify;

public class BaiDuFaceService implements FaceService{

	@Override
	public String detect(String image,String max_face_num,String face_fields) {
		// TODO Auto-generated method stub
		String detect = FaceDetect.detect(image, max_face_num,face_fields);
		
		return detect;
	}

	
	@Override
	public String identify(String image,String group_id,String ext_fields,String user_top_num) {
		// TODO Auto-generated method stub
		return Identify.identify(image, group_id, ext_fields, user_top_num);
	}


	@Override
	public String add(String uid, String group_id, String image, String user_info, String action_type) {
		// TODO Auto-generated method stub
		
		return FaceAdd.add(uid, group_id, image, user_info, action_type);
	}

}
