package jsp.weixin.ParamesAPI.util;

/**
 * @author Engineer-Jsp
 * @date 2014.10.09
 * ��������ͨ����*/
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import jsp.weixin.menu.util.Menu;
import net.sf.json.JSONObject;

public class WeixinUtil {
	/**
	 * ����https���󲢻�ȡ���
	 * 
	 * @param requestUrl
	 *            �����ַ
	 * @param requestMethod
	 *            ����ʽ��GET��POST��
	 * @param outputStr
	 *            �ύ������
	 * @return JSONObject(ͨ��JSONObject.get(key)�ķ�ʽ��ȡjson���������ֵ)
	 */
	public static JSONObject HttpRequest(String request, String RequestMethod,
			String output) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			//���ô���
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cmproxy.gmcc.net", 8081));
			// ��������
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection(proxy);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if (output != null) {
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			// ������
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input,
					"UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			// �ر����ӡ��ͷ���Դ
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	// ��ȡaccess_token�Ľӿڵ�ַ��GET��
	public final static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET";

	// ��ȡjsapi_ticket�Ľӿڵ�ַ
	public final static String jsapi_ticket_url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKE";

	/**
	 * ��ȡaccess_token
	 * 
	 * @param CorpID
	 *            ��ҵId
	 * @param SECRET
	 *            �������ƾ֤��Կ��ÿ��secret�����˶�Ӧ�á�ͨѶ¼���ӿڵĲ�ͬȨ�ޣ���ͬ�Ĺ�����ӵ�в�ͬ��secret
	 * @return
	 */
	public static AccessToken getAccessToken(String corpID, String secret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("CorpID", corpID).replace(
				"SECRET", secret);
		JSONObject jsonObject = HttpRequest(requestUrl, "GET", null);
		// �������ɹ�
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				System.out.println("��ȡtoken�ɹ�:"
						+ jsonObject.getString("access_token") + "��������"
						+ jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// ��ȡtokenʧ��
				String error = String.format("��ȡtokenʧ�� errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return accessToken;
	}

	/**
	 * ��ȡjsapi_ticket
	 * 
	 * @param access_token
	 *            ��Ч��ƾ֤
	 * @return
	 */
	public static Jsapi_Ticket getjsapi_ticket(String access_token) {
		Jsapi_Ticket jsapi_Ticket = null;
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKE",
				access_token);
		JSONObject jsonObject = HttpRequest(requestUrl, "GET", null);
		// �������ɹ�
		if (null != jsonObject) {
			try {
				jsapi_Ticket = new Jsapi_Ticket();
				jsapi_Ticket.setTicket(jsonObject.getString("ticket"));
				jsapi_Ticket.setExpires_in(jsonObject.getInt("expires_in"));
				System.out.println("��ȡtiket�ɹ�:"
						+ jsonObject.getString("ticket") + "��������"
						+ jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				jsapi_Ticket = null;
				// ��ȡticketʧ��
				String error = String.format(
						"��ȡticketʧ�� errcode:{%s} errmsg:{%s}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return jsapi_Ticket;
	}

	// �˵�������POST��
	public static String menu_create_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";

	// �˵�ɾ��(POST)
	public static String menu_delete_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN&agentid=AGENTID";

	// ��ȡ�˵�
	public static String get_menu_url = "https://qyapi.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN&agentid=AGENTID";

	/**
	 * �����˵�
	 * 
	 * @param menu
	 *            �˵�ʵ��
	 * @param accessToken
	 *            ��Ч��access_token
	 * @param agentid
	 *            ��ҵӦ�õ�id�����ͣ�����Ӧ�õ�����ҳ��鿴
	 * @return 0��ʾ�ɹ�������ֵ��ʾʧ��
	 */
	public static int createMenu(Menu menu, String accessToken, String AGENTID) {
		int result = 0;

		// ƴװ�����˵���url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken)
				.replace("AGENTID", AGENTID);
		// ���˵�����ת����json�ַ���
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// ���ýӿڴ����˵�
		JSONObject jsonObject = HttpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				String error = String.format("�����˵�ʧ�� errcode:{%s} errmsg:{%s}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return result;
	}

	/**
	 * ɾ���Զ���˵�
	 * 
	 * @param accessToken
	 *            ��Ч��access_token
	 * @param agentid
	 *            ��ҵӦ�õ�id�����ͣ�����Ӧ�õ�����ҳ��鿴
	 * */
	public static int deletemenu(String accessToken, String AGENTID) {
		int result = 0;
		// ƴװ�����˵���url
		String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken)
				.replace("AGENTID", AGENTID);
		// ���ýӿڴ����˵�
		JSONObject jsonObject = HttpRequest(url, "POST", "");

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				String error = String.format("�����˵�ʧ�� errcode:{%s} errmsg:{%s}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return result;
	}

	/**
	 * ��ȡ�Զ���˵��б�
	 * 
	 * 
	 * @param accessToken
	 *            ��Ч��access_token
	 * 
	 * @param AGENTID
	 *            ��ҵӦ�õ�id�����ͣ�����Ӧ�õ�����ҳ��鿴
	 * 
	 */
	public static JSONObject GetMenu(String accessToken, String AGENTID) {
		// ƴװ�����˵���url
		String url = get_menu_url.replace("ACCESS_TOKEN", accessToken).replace(
				"AGENTID", AGENTID);
		// ���ýӿڴ����˵�
		JSONObject jsonObject = HttpRequest(url, "POST", "");
		return jsonObject;
	}

	public static String URLEncoder(String str) {
		String result = str;
		try {
			result = java.net.URLEncoder.encode(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * �������������ж��ļ���չ��
	 * 
	 * @param contentType
	 *            ��������
	 * @return
	 */
	public static String getFileEndWitsh(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType))
			fileEndWitsh = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileEndWitsh = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileEndWitsh = ".amr";
		else if ("video/mp4".equals(contentType))
			fileEndWitsh = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileEndWitsh = ".mp4";
		return fileEndWitsh;
	}

	/**
	 * �����ύ������ͨ�÷���
	 * 
	 * @param access_token
	 *            ƾ֤
	 * @param RequestMt
	 *            ����ʽ
	 * @param RequestURL
	 *            �����ַ
	 * @param outstr
	 *            �ύjson����
	 * */
	public static int PostMessage(String access_token, String RequestMt,
			String RequestURL, String outstr) {
		int result = 0;
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = WeixinUtil.HttpRequest(RequestURL, RequestMt,
				outstr);
		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				result = jsonobject.getInt("errcode");
				String error = String.format("����ʧ�� errcode:{%s} errmsg:{%s}",
						jsonobject.getInt("errcode"),
						jsonobject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return result;
	}

	/**
	 * �����ύ������ͨ�÷���
	 * 
	 * @param access_token
	 *            ƾ֤
	 * @param RequestMt
	 *            ����ʽ
	 * @param RequestURL
	 *            �����ַ
	 * */
	public static JSONObject PostMessage(String access_token, String RequestMt,
			String RequestURL) {
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = WeixinUtil.HttpRequest(RequestURL, RequestMt,
				RequestURL);
		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				jsonobject.getInt("errcode");
				String error = String.format("����ʧ�� errcode:{%s} errmsg:{%s}",
						jsonobject.getInt("errcode"),
						jsonobject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return jsonobject;
	}

	/**
	 * �����ύ������ͨ�÷���
	 * 
	 * @param access_token
	 *            ƾ֤
	 * @param RequestMt
	 *            ����ʽ
	 * @param RequestURL
	 *            �����ַ
	 * @param outstr
	 *            �ύjson����
	 * */
	public static JSONObject GetMessage(String access_token, String RequestMt,
			String RequestURL, String outstr) {
		RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonobject = WeixinUtil.HttpRequest(RequestURL, RequestMt,
				outstr);
		if (null != jsonobject) {
			if (0 != jsonobject.getInt("errcode")) {
				jsonobject.getInt("errcode");
				String error = String.format("����ʧ�� errcode:{%s} errmsg:{%s}",
						jsonobject.getInt("errcode"),
						jsonobject.getString("errmsg"));
				System.out.println(error);
			}
		}
		return jsonobject;
	}
}
