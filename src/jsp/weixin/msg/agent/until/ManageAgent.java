package jsp.weixin.msg.agent.until;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * 
 * ������ҵӦ��
 * 
 * @time 2015.04.27
 * 
 * */
public class ManageAgent {
	// ��ȡ��ҵ��ĳ��Ӧ�õĻ�����Ϣ
	private static String GetAgentInfo_URL = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&agentid=AGENTID";
	// ������ҵӦ�õ�ѡ��������Ϣ���磺����λ���ϱ��ȡ�
	private static String SetAgentInfo_URL = "https://qyapi.weixin.qq.com/cgi-bin/agent/set?access_token=ACCESS_TOKEN";
	// ��ȡsecret���ڹ������ڵ�Ӧ�øſ����᷵�ع�������Ӧ�õ�id�����ơ�ͷ�����Ϣ
	private static String GetAgentInfos_URL = "https://qyapi.weixin.qq.com/cgi-bin/agent/list?access_token=ACCESS_TOKEN";

	/**
	 * ��ȡ��ҵ��Ӧ��
	 * 
	 * @param access_token
	 *            ��Ч��ƾ֤
	 * @param agentid
	 *            ��ȡ��ҵid
	 * 
	 * */
	public static JSONObject Get_AgentInfo(String access_token, String agentid) {
		// ƴ��url����
		String request_url = GetAgentInfo_URL.replace("ACCESS_TOKEN",
				access_token).replace("AGENTID", agentid);
		// ��������,��ȡ����ֵ
		JSONObject jsonObject = WeixinUtil.PostMessage(access_token, "POST",
				request_url);
		return jsonObject;
	}

	/**
	 * ������ҵӦ�õ�ѡ��������Ϣ
	 * 
	 * @param access_token
	 *            ��Ч��ƾ֤
	 * @param agent
	 *            Ӧ�����ò���
	 *
	 * */
	public static int SetAgentInfo(String access_token, EnterpriseAgent agent) {
		// ƴ��url����
		String request_url = SetAgentInfo_URL.replace("ACCESS_TOKEN",
				access_token);
		String PostData = "{\"agentid\":\"%s\",\"report_location_flag\":%s,\"logo_mediaid\":\"%s\",\"name\":\"%s\",\"description\":\"%s\",\"redirect_domain\":\"%s\",\"isreportuser\":%s,\"isreportenter\":%s}";
		PostData = String.format(PostData, agent.getAgentid(),
				agent.getReport_location_flag(), agent.getLogo_mediaid(),
				agent.getName(), agent.getDecription(),
				agent.getRedirect_domain(), agent.getIsreportuser(),
				agent.getIsreportenter());
		System.out.println(request_url);
		System.out.println(PostData);
		// ��������,��ȡ����ֵ
		int result = WeixinUtil.PostMessage(access_token, "GET", request_url,
				PostData);

		return result;
	}

	/**
	 * ��ȡӦ�øſ�
	 * 
	 * @param access_token
	 *            ��Ч��ƾ֤
	 * @param agentid
	 *            ��ҵӦ�õı��
	 * 
	 * */
	public static JSONObject Get_AgentInfos(String access_token, String agentid) {
		// ƴ��url����
		String request_url = GetAgentInfos_URL.replace("ACCESS_TOKEN",
				access_token).replace("AGENTID", agentid);
		// ��������,��ȡ����ֵ
		JSONObject jsonObject = WeixinUtil.PostMessage(access_token, "POST",
				request_url);
		return jsonObject;
	}

	// ���Խӿ�
	public static void main(String[] args) {
		// ��ȡƾ֤

		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();
		/**
		 * ��ȡӦ�û�����Ϣ
		 * */

		/*
		 * JSONObject jsonObject = Get_AgentInfo(access_token, "3");
		 * System.out.println(jsonObject);
		 */

		/**
		 * ����Ӧ��ѡ��
		 * */

		EnterpriseAgent eAgent = new EnterpriseAgent();
		eAgent.setAgentid("5");
		eAgent.setReport_location_flag("0");
		eAgent.setLogo_mediaid("1rf2uM2SVAOtEyxG93pYCkZ-u3oCPzgQ8i9JwuqjSNupzjIsrZxTz_bTxjASc0-0Q");
		eAgent.setName("����Ӧ��");
		eAgent.setDecription("ͶƱ����");
		eAgent.setRedirect_domain("www.baidu.com");
		eAgent.setIsreportuser(0);
		eAgent.setIsreportenter(0);

		int result = SetAgentInfo(access_token, eAgent);
		if (0 != result) {
			System.out.println("����ʧ��");
		} else {
			System.out.println("���óɹ�");
		}

		/**
		 * ��ȡӦ�û�����Ϣ�б�
		 * */
		/*
		 * JSONObject jsonObject = Get_AgentInfos(access_token, "3");
		 * System.out.println(jsonObject);
		 */
	}
}
