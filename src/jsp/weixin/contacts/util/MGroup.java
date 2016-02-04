package jsp.weixin.contacts.util;

import net.sf.json.JSONObject;
import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;

/**
 * ͨѶ¼���Ź�����
 * 
 * 
 * @date 2015.4.22
 */
public class MGroup {

	// �������ŵ�ַ
	public static String CREATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
	// ���²��ŵ�ַ
	public static String UPDATE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
	// ɾ�����ŵ�ַ
	public static String DELETE_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=ID";
	// ��ȡ�����б��ַ
	public static String GETLIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";

	/**
	 * ��������
	 * 
	 * @param name
	 *            �������ơ���������Ϊ1~64���ַ�
	 * @param parentid
	 *            ���ײ���id��������idΪ1
	 * */
	public static String Create(String name, String parentid) {
		String Postjson = "{\"name\":\"%s\",\"parentid\":\"%s\"}";
		return String.format(Postjson, name, parentid);
	}

	/**
	 * ���²���
	 * 
	 * @param name
	 *            ���µĲ������ơ���������Ϊ0~64���ַ����޸Ĳ�������ʱָ���ò���
	 * @param id
	 *            ����id
	 * */
	public static String Update(String name, String id) {
		String Postjson = "{\"id\":\"%s\",\"name\":\"%s\"";
		return String.format(Postjson, name, id);
	}

	/**
	 * ɾ������
	 * 
	 * @param id
	 *            ����id
	 * */
	public static String Delete(String id) {
		String delete_url = DELETE_URL.replace("ID", id);
		return delete_url;
	}

	// ������ɾ��
	public static int UpdateDepartment(String URL_TYPE, String deptmentid,
			String departmentname, String access_token) {
		// ƴװ����
		/* String PostData = Create("�½�����", "1"); */

		// ɾ������
		/* String PostData = Delete("5"); */

		// �޸Ĳ���
		/* String PostData = Update("6", "ʵʩ��"); */

		// ��ѯ����

		/* System.out.println(PostData); */
		int result = 0;
		String PostData = null;
		// �ύ����,��ȡ���
		if (URL_TYPE.equals("add")) {
			PostData = Create(departmentname, deptmentid);
			result = WeixinUtil.PostMessage(access_token, "POST", CREATE_URL,
					PostData);
		} else if (URL_TYPE.equals("delete")) {
			PostData = Delete(deptmentid);
			result = WeixinUtil.PostMessage(access_token, "POST", DELETE_URL,
					PostData);
		} else {
			PostData = Update(deptmentid, departmentname);
			result = WeixinUtil.PostMessage(access_token, "POST", UPDATE_URL,
					PostData);
		}

		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}
		return result;
	}

	// ��ȡ�����б�
	public static JSONObject GetAll_Department(String access_token) {

		JSONObject jsonObject = WeixinUtil.PostMessage(access_token, "POST",
				GETLIST_URL);
		return jsonObject;
	}

	/**
	 * ���Է���
	 */
	public static void main(String[] args) {
		// ��ȡƾ֤
		//@SuppressWarnings("unused")

		
		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();
		System.out.println(access_token);
		/**
		 * ���Ի�ȡ�����б� System.out.println(GetAll_Department(),access_token); ��Ӳ���
		 * System.out.println(UpdateDepartment("add", "1", "���۲�"),access_token);
		 * �޸Ĳ��� System.out.println(UpdateDepartment("update", "7",
		 * "���̲�"),access_token);
		 * ɾ������System.out.println(UpdateDepartment("delete", "7",
		 * ""),access_token);
		 */

	}
}
