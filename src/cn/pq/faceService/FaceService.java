package cn.pq.faceService;

public interface FaceService {

	/**
	 * 人脸检测
	 * @param image base64编码后的图片数据，需urlencode，编码后的图片大小不超过2M
	 * @param max_face_num 多处理人脸的数目，默认值为1，仅检测图片中面积最大的那个人脸
	 * @param face_fields 包括age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities信息，逗号分隔，默认只返回人脸框、概率和旋转角度
	 * @return
	 */
	public String detect(String image,String max_face_num,String face_fields);

	/**
	 * 人脸添加
	 * @param uid 用户id（由数字、字母、下划线组成），长度限制128B。
	 * @param group_id 用户组id，标识一组用户（由数字、字母、下划线组成），长度限制128B。如果需要将一个uid注册到多个group下，group_id需要用多个逗号分隔，每个group_id长度限制为48个英文字符。注：group无需单独创建，注册用户时则会自动创建group。
			   产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
	 * @param image  base64编码后的图片数据，需urlencode，每次只支持单张图片，编码后的图片大小不超过10M。为保证后续识别的效果较佳，建议注册的人脸，为用户正面人脸，且保持人脸都在图片之内
	 * @param user_info 	用户资料，长度限制256B
	 * @param action_type 非必选   参数包含app、replace。如果为“replace”，则每次注册时进行替换replace（新增或更新）操作，默认为append操作。例如：uid在库中已经存在时，对此uid重复注册时，新注册的图片默认会追加到该uid下，如果手动选择action_type:replace，则会用新图替换库中该uid下所有图片。
	 * @return
	 */
	public String add(String uid,String group_id,String image,String user_info,String action_type);
	
	/**
	 * 人脸查找
	 * @param image base64编码后的图片数据，需urlencode，每次只支持单张图片，编码后的图片大小不超过10M
	 * @param group_id 用户组id（由数字、字母、下划线组成），长度限制128B，如果需要查询多个用户组id，用逗号分隔
	 * @param ext_fields 特殊返回信息，多个用逗号分隔，取值固定: 目前支持faceliveness(活体检测)。注：需要用于判断活体的图片，图片中的人脸像素面积需要不小于100px*100px，人脸长宽与图片长宽比例，不小于1/3
	 * @param user_top_num 识别后返回的用户top数，默认为1，最多返回5个
	 * @return 
	 */
	public String identify(String image,String group_id,String ext_fields,String user_top_num);
}
