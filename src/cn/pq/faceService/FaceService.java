package cn.pq.faceService;

public interface FaceService {

	/**
	 * �������
	 * @param image base64������ͼƬ���ݣ���urlencode��������ͼƬ��С������2M
	 * @param max_face_num �ദ����������Ŀ��Ĭ��ֵΪ1�������ͼƬ����������Ǹ�����
	 * @param face_fields ����age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities��Ϣ�����ŷָ���Ĭ��ֻ���������򡢸��ʺ���ת�Ƕ�
	 * @return
	 */
	public String detect(String image,String max_face_num,String face_fields);

	/**
	 * �������
	 * @param uid �û�id�������֡���ĸ���»�����ɣ�����������128B��
	 * @param group_id �û���id����ʶһ���û��������֡���ĸ���»�����ɣ�����������128B�������Ҫ��һ��uidע�ᵽ���group�£�group_id��Ҫ�ö�����ŷָ���ÿ��group_id��������Ϊ48��Ӣ���ַ���ע��group���赥��������ע���û�ʱ����Զ�����group��
			   ��Ʒ���飺��������ҵ�����󣬿��Խ���Ҫע����û�������ҵ�񻮷֣����䵽��ͬ��group�£����簴�ջ�Ա�ֻ�β����Ϊgroupid������ˢ��֧������Ա�Ʒ����ѵȣ��������Ծ����ܿ���ÿ��group�µ��û�����������������������׼ȷ��
	 * @param image  base64������ͼƬ���ݣ���urlencode��ÿ��ֻ֧�ֵ���ͼƬ��������ͼƬ��С������10M��Ϊ��֤����ʶ���Ч���ϼѣ�����ע���������Ϊ�û������������ұ�����������ͼƬ֮��
	 * @param user_info 	�û����ϣ���������256B
	 * @param action_type �Ǳ�ѡ   ��������app��replace�����Ϊ��replace������ÿ��ע��ʱ�����滻replace����������£�������Ĭ��Ϊappend���������磺uid�ڿ����Ѿ�����ʱ���Դ�uid�ظ�ע��ʱ����ע���ͼƬĬ�ϻ�׷�ӵ���uid�£�����ֶ�ѡ��action_type:replace���������ͼ�滻���и�uid������ͼƬ��
	 * @return
	 */
	public String add(String uid,String group_id,String image,String user_info,String action_type);
	
	/**
	 * ��������
	 * @param image base64������ͼƬ���ݣ���urlencode��ÿ��ֻ֧�ֵ���ͼƬ��������ͼƬ��С������10M
	 * @param group_id �û���id�������֡���ĸ���»�����ɣ�����������128B�������Ҫ��ѯ����û���id���ö��ŷָ�
	 * @param ext_fields ���ⷵ����Ϣ������ö��ŷָ���ȡֵ�̶�: Ŀǰ֧��faceliveness(������)��ע����Ҫ�����жϻ����ͼƬ��ͼƬ�е��������������Ҫ��С��100px*100px������������ͼƬ�����������С��1/3
	 * @param user_top_num ʶ��󷵻ص��û�top����Ĭ��Ϊ1����෵��5��
	 * @return 
	 */
	public String identify(String image,String group_id,String ext_fields,String user_top_num);
}
