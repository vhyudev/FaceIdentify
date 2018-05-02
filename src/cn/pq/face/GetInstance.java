package cn.pq.face;

import cn.pq.faceService.FaceService;
import cn.pq.faceServiceImpl.BaiDuFaceService;

public class GetInstance {
	public static FaceService fs= new BaiDuFaceService();
	//获取百度对象
	public static FaceService getFS(){
		return fs;
	}
}
