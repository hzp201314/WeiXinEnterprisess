package jsp.weixin.contacts.util;

import net.sf.json.JSONObject;
import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;

/**
 * ͨѶ¼��Ա������
 * 
 * @date 2015.4.22
 * */
public class MPerson {
	// ������Ա��ַ
	public static String CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
	// ���³�Ա��ַ
	public static String UPDATA_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
	// ɾ����Ա��ַ
	public static String DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=ID";
	// ����ɾ����Ա��ַ
	public static String DELETES_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=ACCESS_TOKEN";
	// ��ȡ��Ա��ַ
	public static String GET_PERSON_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=ID";
	// ��ȡ���ų�Ա��ַ
	public static String GET_GROUP_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=ID&fetch_child=0&status=0";
	// ��ȡ���ų�Ա�����ַ
	public static String GET_GROUPINFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=ID&fetch_child=0&status=0";
	// �����Ա��ע
	public static String INVITE_PERSON_CONCERN = "https://qyapi.weixin.qq.com/cgi-bin/invite/send?access_token=ACCESS_TOKEN ";

	/**
	 * ������Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺţ���ҵ�ڱ���Ψһ������Ϊ1~64���ַ�
	 * @param name
	 *            ��Ա���ơ�����Ϊ1~64���ַ�
	 * @param department
	 *            ��Ա��������id�б� ��ʽ�� "department": [x, y]
	 * @param position
	 *            ְλ��Ϣ
	 * @param mobile
	 *            �ֻ����롣��ҵ�ڱ���Ψһ��mobile/weixinid/email���߲���ͬʱΪ��
	 * @param gender
	 *            �Ա�gender=0��ʾ�У�=1��ʾŮ��Ĭ��gender=0
	 * @param tel
	 *            �칫�绰������Ϊ0~64���ַ�
	 * @param email
	 *            ���䡣����Ϊ0~64���ַ�����ҵ�ڱ���Ψһ
	 * @param weixinid
	 *            ΢�źš���ҵ�ڱ���Ψһ
	 * */
	public static String Create(String userid, String name, String department,
			String position, String mobile, String tel, String email,
			String weixinid) {
		String PostData = "{\"userid\": \"%s\",\"name\":\"%s\",\"department\":%s,\"position\": \"%s\",\"mobile\": \"%s\",\"tel\": \"%s\",\"email\": \"%s\",\"weixinid\":\"%s\"}";
		return String.format(PostData, userid, name, department, position,
				mobile, tel, email, weixinid);
	}

	/**
	 * ���³�Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺţ���ҵ�ڱ���Ψһ������Ϊ1~64���ַ�
	 * @param name
	 *            ��Ա���ơ�����Ϊ1~64���ַ�
	 * @param department
	 *            ��Ա��������id�б� ��ʽ�� "department": [x]
	 * @param position
	 *            ְλ��Ϣ
	 * @param mobile
	 *            �ֻ����롣��ҵ�ڱ���Ψһ��mobile/weixinid/email���߲���ͬʱΪ��
	 * @param gender
	 *            �Ա�gender=0��ʾ�У�=1��ʾŮ��Ĭ��gender=0
	 * @param tel
	 *            �칫�绰������Ϊ0~64���ַ�
	 * @param email
	 *            ���䡣����Ϊ0~64���ַ�����ҵ�ڱ���Ψһ
	 * @param weixinid
	 *            ΢�źš���ҵ�ڱ���Ψһ
	 * @param enable
	 *            ����/���ó�Ա��1��ʾ���ó�Ա��0��ʾ���ó�Ա
	 * */
	public static String Updata(String userid, String name, String department,
			String position, String mobile, String tel, String email,
			String weixinid, String enable) {
		String PostData = "{\"userid\": \"%s\",\"name\": \"%s\",\"department\":%s,\"position\": \"%s\",\"mobile\": \"%s\",\"tel\": \"%s\",\"email\": \"%s\",\"weixinid\": \"%s\",\"enable\": \"%s\"}";
		return String.format(PostData, userid, name, department, position,
				mobile, tel, email, weixinid, enable);
	}

	/**
	 * ɾ����Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static String Delete(String userid) {
		String delete_url = DELETE_URL.replace("ID", userid);
		return delete_url;
	}

	/**
	 * ����ɾ����Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static String Deletes(String userids) {
		String PostData = "{\"useridlist\":%s}";
		return String.format(PostData, userids);
	}

	/**
	 * �����Ա��ע
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static String Invite(String userid) {
		String PostData = "{\"userid\":\"%s\"}";
		return String.format(PostData, userid);
	}

	/**
	 * ��ȡ��Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static String GPerson(String userid) {
		String getperson_url = GET_PERSON_URL.replace("ID", userid);
		return getperson_url;
	}

	/**
	 * ��ȡ���ų�Ա
	 * 
	 * @param department_id
	 *            ��ȡ�Ĳ���id
	 * @param fetch_child
	 *            1/0���Ƿ�ݹ��ȡ�Ӳ�������ĳ�Ա ����ѡ��
	 * @param status
	 *            0��ȡȫ��Ա����1��ȡ�ѹ�ע��Ա�б�2��ȡ���ó�Ա�б�4��ȡδ��ע��Ա�б�status�ɵ��� ����ѡ��
	 * */
	public static String GGroup(String department_id) {
		String getgroup_url = GET_GROUP_URL.replace("ID", department_id);
		return getgroup_url;

	}

	/**
	 * ��� �޸ĳ�Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺţ���ҵ�ڱ���Ψһ������Ϊ1~64���ַ�
	 * @param name
	 *            ��Ա���ơ�����Ϊ1~64���ַ�
	 * @param department
	 *            ��Ա��������id�б� ��ʽ�� "department": [x, y]
	 * @param position
	 *            ְλ��Ϣ
	 * @param mobile
	 *            �ֻ����롣��ҵ�ڱ���Ψһ��mobile/weixinid/email���߲���ͬʱΪ��
	 * @param gender
	 *            �Ա�gender=0��ʾ�У�=1��ʾŮ��Ĭ��gender=0
	 * @param tel
	 *            �칫�绰������Ϊ0~64���ַ�
	 * @param email
	 *            ���䡣����Ϊ0~64���ַ�����ҵ�ڱ���Ψһ
	 * @param weixinid
	 *            ΢�źš���ҵ�ڱ���Ψһ
	 * */
	public static int UpdatePerson(String URL_TYPE, String userid, String name,
			String department, String position, String mobile, String tel,
			String email, String weixinid, String enable, String access_token) {

		int result = 0;
		String PostData = "";

		// ƴװ����
		if (URL_TYPE == "add") {
			PostData = Create(userid, name, position, department, mobile, tel,
					email, weixinid);
			result = WeixinUtil.PostMessage(access_token, "POST", CREATE_URL,
					PostData);
		} else {
			PostData = Updata(userid, name, department, position, mobile, tel,
					email, weixinid, enable);
			result = WeixinUtil.PostMessage(access_token, "POST", UPDATA_URL,
					PostData);
		}
		System.out.println(PostData);
		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}
		return result;
	}

	/**
	 * ɾ����Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static int UpdatePerson(String userid, String access_token) {
		// ƴװ����
		String PostData = Delete(userid);
		// ��ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST", PostData,
				PostData);
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}
		return result;
	}

	/**
	 * ����ɾ����Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static int DeletePersons(String userids, String access_token) {
		// ƴ������
		String PostData = Deletes(userids);
		System.out.println(PostData);
		// �ύ���ݣ���ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST", DELETES_URL,
				PostData);
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}
		return result;
	}

	/**
	 * ��ȡ��Ա
	 * 
	 * @param userid
	 *            Ա��UserID����Ӧ����˵��ʺ�
	 * */
	public static JSONObject GetPerson(String userid, String access_token) {
		// ƴ������
		String PostData = GPerson(userid);
		// �ύ���ݣ���ȡ���
		JSONObject jsonObject = WeixinUtil.GetMessage(access_token, "POST",
				GET_PERSON_URL, PostData);
		return jsonObject;
	}

	/**
	 * ��ȡ���ų�Ա
	 * 
	 * @param department_id
	 *            ��ȡ�Ĳ���id
	 * @param fetch_child
	 *            1/0���Ƿ�ݹ��ȡ�Ӳ�������ĳ�Ա ����ѡ��
	 * @param status
	 *            0��ȡȫ��Ա����1��ȡ�ѹ�ע��Ա�б�2��ȡ���ó�Ա�б�4��ȡδ��ע��Ա�б�status�ɵ��� ����ѡ��
	 * */
	public static JSONObject GetDepartmentPersons(String departmentid,
			String access_token) {
		// ƴ������
		String PostData = GGroup(departmentid);
		// �ύ���ݣ���ȡ���
		JSONObject jsonObject = WeixinUtil.GetMessage(access_token, "POST",
				GET_GROUP_URL, PostData);
		return jsonObject;
	}

	/**
	 * ��ȡ���ų�Ա(����)
	 * 
	 * @param department_id
	 *            ��ȡ�Ĳ���id
	 * @param fetch_child
	 *            1/0���Ƿ�ݹ��ȡ�Ӳ�������ĳ�Ա ����ѡ��
	 * @param status
	 *            0��ȡȫ��Ա����1��ȡ�ѹ�ע��Ա�б�2��ȡ���ó�Ա�б�4��ȡδ��ע��Ա�б�status�ɵ��� ����ѡ��
	 * */
	public static JSONObject GetDepartmentPersonsinfo(String departmentid,
			String access_token) {
		// ƴ������
		String PostData = GGroup(departmentid);
		// �ύ���ݣ���ȡ���
		JSONObject jsonObject = WeixinUtil.GetMessage(access_token, "POST",
				GET_GROUPINFO_URL, PostData);
		return jsonObject;
	}

	/**
	 * �����Ա��ע
	 * 
	 * @param userid
	 *            ��Ա�û���
	 * 
	 * @param invite_tips
	 *            ������ʾ�ֻ����֤�ſ���ʹ�ã�
	 */
	public static int Getinvite_personconcern(String userid, String access_token) {

		String PostData = Invite(userid);
		int result = WeixinUtil.PostMessage(access_token, "POST",
				INVITE_PERSON_CONCERN, PostData);
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}
		return result;
	}

	// ����

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		/** ������Ա */
		// ��ȡƾ֤
		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();
		/*
		 * UpdatePerson("add", "ww", "ww", "����Գ", "[1]", "15555551234",
		 * "0731-666666", "ww@qq.com", "a55555555", "",access_token);
		 */
		/** �޸ĳ�Ա */
		/*
		 * UpdatePerson("update", "ls", "ls", "����Գ", "[1]", "15212124545",
		 * "0731-888888", "ls@qq.com", "a456456465", "1",access_token);
		 */
		/** ɾ����Ա */
		/*
		 * UpdatePerson("zhangsan",access_token);
		 */
		/** ����ɾ����Ա */
		/*
		 * DeletePersons("[\"ls\",\"ww\"]",access_token);
		 */
		/** ��ȡ��Ա */
		/*
		 * GetPerson("liuwenzhuo",access_token);
		 */
		/** ��ȡ���ų�Ա */
		/*
		 * GetDepartmentPersons("1",access_token)
		 */
		/** ��ȡ�������� */
		/*
		 * GetDepartmentPersonsinfo("1",access_token);
		 */
		/** �����Ա��ע */
		/*
		 * Getinvite_personconcern("wuzhe",access_token);
		 */

	}

}
