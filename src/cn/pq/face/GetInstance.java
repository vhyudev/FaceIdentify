package cn.pq.face;

import cn.pq.faceService.FaceService;
import cn.pq.faceServiceImpl.BaiDuFaceService;

public class GetInstance {
	public static FaceService fs= new BaiDuFaceService();
	//��ȡ�ٶȶ���
	public static FaceService getFS(){
		return fs;
	}
}
