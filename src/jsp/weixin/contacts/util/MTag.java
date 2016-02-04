package jsp.weixin.contacts.util;

import net.sf.json.JSONObject;
import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;

/**
 * ͨѶ¼��ǩ������
 * 
 * @date 2015.4.22
 * */
public class MTag {
	// ������ǩ��ַ
	public static String CREATE_TAG_URL = "https://qyapi.weixin.qq.com/cgi-bin/tag/create?access_token=ACCESS_TOKEN";
	// ���±�ǩ��ַ
	public static String UPDATA_TAG_URL = "https://qyapi.weixin.qq.com/cgi-bin/tag/update?access_token=ACCESS_TOKEN";
	// ɾ����ǩ��ַ
	public static String DELETE_TAG_URL = "https://qyapi.weixin.qq.com/cgi-bin/tag/delete?access_token=ACCESS_TOKEN&tagid=ID";
	// ��ȡ��ǩ��Ա��ַ
	public static String GET_TAG_PERSON = "https://qyapi.weixin.qq.com/cgi-bin/tag/get?access_token=ACCESS_TOKEN&tagid=ID";
	// ���ӱ�ǩ��Ա��ַ
	public static String ADD_TAG_PERSON = "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers?access_token=ACCESS_TOKEN";
	// ɾ����ǩ��Ա��ַ
	public static String DELETE_TAG_PERSON = "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers?access_token=ACCESS_TOKEN";
	// ��ȡ��ǩ�б�
	public static String GET_TAG_LIST = "https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=ACCESS_TOKEN";

	/**
	 * ������ǩ
	 * 
	 * @param tagname
	 *            ��ǩ���ơ�����Ϊ1~64���ַ�����ǩ����������ͬ��ı�ǩ������Ҳ������ȫ�ֱ�ǩ����
	 * */
	public static String Create_Tag(String tagname) {
		String PostData = "{\"tagname\":\"%s\"}";
		return String.format(PostData, tagname);
	}

	/**
	 * ���±�ǩ����
	 * 
	 * @param tagid
	 *            ��ǩID
	 * @param tagname
	 *            ��ǩ���ơ��64���ַ�
	 * */
	public static String Updata_Tag(String tagid, String tagname) {
		String PostData = "{\"tagid\":\"%s\",\"tagname\":\"%s\"}";
		return String.format(PostData, tagid, tagname);
	}

	/**
	 * ɾ����ǩ
	 * 
	 * @param tagid
	 *            ��ǩID
	 * */
	public static String Delete_Tag(String tagid) {
		String delete_url = DELETE_TAG_URL.replace("ID", tagid);
		return delete_url;
	}

	/**
	 * ��ȡ��ǩ��Ա
	 * 
	 * @param tagid
	 *            ��ǩID
	 * */
	public static String Get_Tag_Person(String tagid) {
		String get_tagperson_url = GET_TAG_PERSON.replace("ID", tagid);
		return get_tagperson_url;
	}

	/**
	 * ���ӱ�ǩ��Ա
	 * 
	 * @param tagid
	 *            ��ǩID
	 * @param userlist
	 *            ��ҵԱ��ID�б� ��ʽ��"userlist":[ "user1","user2"]
	 * */
	public static String Add_Tag_Person(String tagid, String userlist,
			String partylist) {
		String PostData = "{\"tagid\": \"%s\",\"userlist\":%s,\"[partylist\":%s}";
		return String.format(PostData, tagid, userlist, partylist);
	}

	/**
	 * ɾ����ǩ��Ա
	 * 
	 * @param tagid
	 *            ��ǩID
	 * @param userlist
	 *            ��ҵԱ��ID�б� ��ʽ��"userlist":[ "user1","user2"]
	 * */
	public static String Delete_Tag_Person(String tagid, String userlist,
			String partylist) {
		String PostData = "{\"tagid\": \"%s\",\"userlist\":%s,\"partylist\":%s}";
		return String.format(PostData, tagid, userlist, partylist);
	}

	/**
	 * ������ǩ
	 * 
	 * @param tagname
	 *            ��ǩ���ơ�����Ϊ1~64���ַ�����ǩ����������ͬ��ı�ǩ������Ҳ������ȫ�ֱ�ǩ����
	 * */
	public static int CreateTag(String tagname,String access_token) {
		// ƴװ����
		String PostData = Create_Tag(tagname);
		// �ύ���ݣ���ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST",
				CREATE_TAG_URL, PostData);
		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}

		return result;
	}

	/**
	 * ���±�ǩ
	 * 
	 * @param tagid
	 *            ��ǩID
	 * @param tagname
	 *            ��ǩ���ơ�����Ϊ1~64���ַ�����ǩ����������ͬ��ı�ǩ������Ҳ������ȫ�ֱ�ǩ����
	 * */
	public static int UpdateTag(String tagid, String tagname,String access_token) {
		// ƴװ����
		String PostData = Updata_Tag(tagid, tagname);
		// �ύ���ݣ���ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST",
				UPDATA_TAG_URL, PostData);
		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}

		return result;
	}

	/**
	 * ɾ����ǩ
	 * 
	 * @param tagid
	 *            ��ǩID
	 * */
	public static int DeleteTag(String tagid,String access_token) {
		// ƴװ����
		String PostData = Delete_Tag(tagid);
		// �ύ���ݣ���ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST",
				DELETE_TAG_URL, PostData);
		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}

		return result;
	}

	/**
	 * ��ȡ��ǩ��Ա
	 * 
	 * @param tagid
	 *            ��ǩID
	 * */
	public static JSONObject GetTagPerson(String tagid,String access_token) {
		// ƴװ����
		String PostData = Get_Tag_Person(tagid);
		// �ύ���ݣ���ȡ���
		JSONObject jsonObject = WeixinUtil.GetMessage(access_token, "POST",
				GET_TAG_PERSON, PostData);
		return jsonObject;
	}

	/**
	 * ���ӱ�ǩ��Ա
	 * 
	 * @param tagid
	 *            ��ǩID
	 * @param userlist
	 *            ��ҵԱ��ID�б� ��ʽ��"userlist":[ "user1","user2"]
	 * @param partylist
	 *            ��ҵ����ID�б���ʽ "partylist": [4] ע�⣺userlist��partylist����ͬʱΪ��
	 * */
	public static int AddTagPersons(String tagid, String userlist,
			String partylist,String access_token) {

		// ƴװ����
		String PostData = Add_Tag_Person(tagid, userlist, partylist);
		// �ύ���ݣ���ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST",
				ADD_TAG_PERSON, PostData);
		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}

		return result;

	}

	/**
	 * ɾ����ǩ��Ա
	 * 
	 * @param tagid
	 *            ��ǩID
	 * @param userlist
	 *            ��ҵԱ��ID�б� ��ʽ��"userlist":[ "user1","user2"]
	 * @param partylist
	 *            ��ҵ����ID�б���ʽ "partylist": [4] ע�⣺userlist��partylist����ͬʱΪ��
	 * */
	public static int DeleteTagPersons(String tagid, String userlist,
			String partylist,String access_token) {
		// ƴװ����
		String PostData = Delete_Tag_Person(tagid, userlist, partylist);
		// �ύ���ݣ���ȡ���
		int result = WeixinUtil.PostMessage(access_token, "POST",
				DELETE_TAG_PERSON, PostData);
		// ��ӡ���
		if (0 == result) {
			System.out.println("�����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}

		return result;
	}

	/**
	 * 
	 * ��ȡ��ǩ�б�
	 * 
	 * 
	 * */
	public static JSONObject GetTagList(String access_token) {
		// �ύ���ݣ���ȡ���
		JSONObject jsonObject = WeixinUtil.PostMessage(access_token, "POST",
				GET_TAG_LIST);
		return jsonObject;
	}

	// ���Ա�ǩ����
	public static void main(String[] args) {
		// ��ȡƾ֤
		@SuppressWarnings("unused")
		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();
		/** �½���ǩ */
		/* CreateTag("�½���ǩ2",access_token); */
		/** ���±�ǩ */
		/* UpdateTag("3", "�½�",access_token); */
		/** ɾ����ǩ */
		/* DeleteTag("3",access_token); */
		/** ��ȡ��ǩ��Ա */
		/* System.out.println(GetTagPerson("1"),access_token); */
		/** ���ӱ�ǩ��Ա */
		/* AddTagPersons("4", "[\"liuwenzhuo\",\"wuzhe\"]", "",access_token); */
		/** ɾ����ǩ��Ա */
		/* DeleteTagPersons("4", "[\"liuwenzhuo\",\"wuzhe\"]", "",access_token); */
		/** ��ȡ��ǩ�б� */
		/* System.out.println(GetTagList(),access_token); */
	}
}
