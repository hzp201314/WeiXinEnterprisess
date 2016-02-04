package jsp.weixin.oauth2.util;

/** 
 * Oauth2�� 
 * @date 2015.04.27 
 * ��ȡ�û�code
 * 
 */
import net.sf.json.JSONObject;
import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;

public class GOauth2Core {
	public static String GET_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=a123#wechat_redirect";

	/**
	 * ��ҵ��ȡcode��ַ����
	 * 
	 * @param appid
	 *            ��ҵ��CorpID
	 * @param redirect_uri
	 *            ��Ȩ���ض���Ļص����ӵ�ַ����ʹ��urlencode�����ӽ��д���
	 * @param response_type
	 *            �������ͣ���ʱ�̶�Ϊ��code
	 * @param scope
	 *            Ӧ����Ȩ�����򣬴�ʱ�̶�Ϊ��snsapi_base
	 * @param state
	 *            �ض��������state��������ҵ������дa-zA-Z0-9�Ĳ���ֵ
	 * @param #wechat_redirect ΢���ն�ʹ�ô˲����ж��Ƿ���Ҫ���������Ϣ Ա�������ҳ�潫��ת��
	 *        redirect_uri/?code=CODE&state=STATE����ҵ�ɸ���code�������Ա����userid
	 * */
	public static String GetCode() {
		String get_code_url = "";
		get_code_url = GET_CODE.replace("CORPID", ParamesAPI.corpId).replace(
				"REDIRECT_URI", WeixinUtil.URLEncoder(ParamesAPI.REDIRECT_URI));
		JSONObject jsonObject = WeixinUtil.HttpRequest(get_code_url, "POST",
				null);
		if (null != jsonObject) {
			String UserId = jsonObject.getString("UserId");
			if (!"".equals(UserId)) {
				System.out.println("��ȡ��Ϣ�ɹ���o(��_��)o ��������UserID:" + UserId);
			} else {
				int errorrcode = jsonObject.getInt("errcode");
				String errmsg = jsonObject.getString("errmsg");
				System.out.println("�����룺" + errorrcode + "��������" + "������Ϣ��"
						+ errmsg);
			}
		} else {
			System.out.println("��ȡʧ���ˡ�����");
		}
		return get_code_url;
	}

	public static String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=3";

	/**
	 * ����code��ȡ��Ա��Ϣ
	 * 
	 * @param access_token
	 *            ���ýӿ�ƾ֤
	 * @param code
	 *            ͨ��Ա����Ȩ��ȡ����code��ÿ��Ա����Ȩ���ϵ�code����һ����codeֻ��ʹ��һ�Σ�5����δ��ʹ���Զ�����
	 * @param agentid
	 *            ��ת����ʱ���ڵ���ҵӦ��ID ����Ա��ӵ��agent��ʹ��Ȩ�ޣ�agentid�������ת����ʱ���ڵ���ҵӦ��ID��ͬ
	 * */
	public static String GetUserID(String access_token, String code,
			String agentid) {
		String UserId = "";
		String code_url=CODE_TO_USERINFO
				.replace("ACCESS_TOKEN", access_token).replace("CODE", code)
				.replace("AGENTID", agentid);
		JSONObject jsonobject = WeixinUtil.HttpRequest(code_url, "GET",
				null);
		if (null != jsonobject) {
			UserId = jsonobject.getString("UserId");
			if (!"".equals(UserId)) {
				System.out.println("��ȡ��Ϣ�ɹ���o(��_��)o ��������UserID:" + UserId);
			} else {
				int errorrcode = jsonobject.getInt("errcode");
				String errmsg = jsonobject.getString("errmsg");
				System.out.println("�����룺" + errorrcode + "��������" + "������Ϣ��"
						+ errmsg);
			}
		} else {
			System.out.println("��ȡ��Ȩʧ���ˣ���n���Լ���ԭ�򡣡���");
		}
		return UserId;
	}

	// ����
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// ��ȡƾ֤
		String token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();
		System.out.println(GetCode());

	}

}
