package jsp.weixin.msg.Util;

/** 
 * ������Ϣ�� 
 *  
 * @date 2015.04.27 
 */

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import jsp.weixin.contacts.util.MPerson;
import jsp.weixin.msg.Resp.Article;
import jsp.weixin.msg.Resp.File;
import jsp.weixin.msg.Resp.Image;
import jsp.weixin.msg.Resp.ImageMessage;
import jsp.weixin.msg.Resp.NewsMessage;
import jsp.weixin.msg.Resp.TextMessage;
import jsp.weixin.msg.Resp.Video;
import jsp.weixin.msg.Resp.Voice;

public class SMessage {
	// ���ͽӿ�
	public static String POST_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

	/**
	 * text��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��text
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param content
	 *            ��Ϣ����
	 * @param safe
	 *            ��ʾ�Ƿ��Ǳ�����Ϣ��0��ʾ��1��ʾ�ǣ�Ĭ��0
	 * */
	public static String STextMsg(String touser, String toparty, String totag,
			String agentid, TextMessage text) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"text\",\"agentid\": %s,\"text\": {\"content\": \"%s\"},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid,
				text.getContent());
	}

	/**
	 * image��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��image
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param media_id
	 *            ý����Դ�ļ�ID
	 * @param safe
	 *            ��ʾ�Ƿ��Ǳ�����Ϣ��0��ʾ��1��ʾ�ǣ�Ĭ��0
	 * */
	public static String SImageMsg(String touser, String toparty,
			String agentid, Image image) {
		String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"msgtype\": \"image\",\"agentid\": \"%s\",\"image\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, agentid,
				image.getMediaId());
	}

	/**
	 * voice��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��voice
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param media_id
	 *            ý����Դ�ļ�ID
	 * @param safe
	 *            ��ʾ�Ƿ��Ǳ�����Ϣ��0��ʾ��1��ʾ�ǣ�Ĭ��0
	 * */
	public static String SVoiceMsg(String touser, String toparty, String totag,
			String agentid, Voice voice) {
		String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"voice\",\"agentid\": \"%s\",\"voice\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid,
				voice.getMediaId());
	}

	/**
	 * video��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��video
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param media_id
	 *            ý����Դ�ļ�ID
	 * @param title
	 *            ��Ƶ��Ϣ�ı���
	 * @param description
	 *            ��Ƶ��Ϣ������
	 * @param safe
	 *            ��ʾ�Ƿ��Ǳ�����Ϣ��0��ʾ��1��ʾ�ǣ�Ĭ��0
	 */
	public static String SVideoMsg(String touser, String toparty, String totag,
			String agentid, Video video) {
		String PostData = "{\"touser\": \"%s\",\"toparty\": %s,\"totag\": %s,\"msgtype\":\"video\",\"agentid\": \"%s\",\"video\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid,
				video.getMediaId(), video.getTitle(), video.getDescription());
	}

	/**
	 * file��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��file
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param media_id
	 *            ý����Դ�ļ�ID
	 * @param safe
	 *            ��ʾ�Ƿ��Ǳ�����Ϣ��0��ʾ��1��ʾ�ǣ�Ĭ��0
	 * */
	public static String SFileMsg(String touser, String toparty, String totag,
			String agentid, File file) {
		String PostData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"file\",\"agentid\": %s,\"file\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";
		return String.format(PostData, touser, toparty, totag, agentid,
				file.getMedia_id());
	}

	/**
	 * news��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��news
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param articlesList
	 *            ͼ�ļ���
	 */
	public static String SNewsMsg(String touser, String toparty, String totag,
			String agentid, String articlesList) {
		String postData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"news\",\"agentid\": %s,\"news\": {\"articles\":%s}}";
		return String.format(postData, touser, toparty, totag, agentid,
				articlesList);
	}

	/**
	 * mpnews��Ϣ
	 * 
	 * @param touser
	 *            UserID�б���Ϣ�����ߣ�����������á�|���ָ��������������ָ��Ϊ@all�������ע����ҵӦ�õ�ȫ����Ա���͡�������
	 *            "touser": "UserID1|UserID2|UserID3"
	 * @param toparty
	 *            PartyID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"toparty":
	 *            " PartyID1 | PartyID2 "
	 * @param totag
	 *            TagID�б�����������á�|���ָ�����touserΪ@allʱ���Ա�������������"totag":
	 *            " TagID1 | TagID2 "
	 * @param msgtype
	 *            ��Ϣ���ͣ���ʱ�̶�Ϊ��mpnews
	 * @param agentid
	 *            ��ҵӦ�õ�id�����͡�����Ӧ�õ�����ҳ��鿴
	 * @param articlesList
	 *            mpnews����
	 */
	public static String SMpNewsMsg(String touser, String toparty,
			String totag, String agentid, String articlesList) {
		String postData = "{\"touser\": %s,\"toparty\": %s,\"totag\": %s,\"msgtype\": \"mpnews\",\"agentid\": %s,\"mpnews\": {\"articles\":%s}\"safe\":\"0\"}";
		return String.format(postData, touser, toparty, totag, agentid,
				articlesList);
	}

	// ������Ϣ����
	public static void main(String[] args) {
		// ��ȡƾ֤
		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();
		/**
		 * ����newsͼ����Ϣʾ��
		 * */
		/*
		 * // �½�ͼ�� Article article1 = new Article();
		 * article1.setTitle("news��Ϣ����-1"); article1.setDescription("");
		 * article1.setPicUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg"
		 * );
		 * article1.setUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg");
		 * Article article2 = new Article(); article2.setTitle("news��Ϣ����-2");
		 * article2.setDescription("");
		 * article2.setPicUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg"
		 * );
		 * article2.setUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg");
		 * // ����ͼ�� List<Article> list = new ArrayList<Article>();
		 * list.add(article1); list.add(article2); // ͼ��תjson String
		 * articlesList = JSONArray.fromObject(list).toString(); // Post������
		 * String PostData = SNewsMsg("3", "1", null, "3", articlesList);
		 */
		/**
		 * ����text�ı���Ϣ
		 * */
		/*
		 * TextMessage text = new TextMessage(); text.setContent("���ã�����");
		 * String PostData = STextMsg("3", "1", null, "3", text);
		 * System.out.println(PostData); int result =
		 * WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);
		 */
		/**
		 * ����imageͼƬ��Ϣ
		 * */
		/*
		 * Image image = new Image(); image.setMediaId(
		 * "1HOL6ohKorjnOOtztf9lKRtEXbPyd3vdAlxmdoGK5QVkpJ_igbWUnBHXw5mX2jBaE3vdJBmajLRxsWqmShWVDQA"
		 * ); String PostData = SImageMsg("liuwenzhuo", null, "3", image);
		 * System.out.println(PostData);
		 */

		/**
		 * ����voice��Ϣ
		 * */
		/*
		 * Voice voice = new Voice(); voice.setMediaId(
		 * "1tCxrxE4Wiy7aiwyadZPONJRHk8vOdTJPG_h8CLcDqtCRtxSmgHQGzdcIJ6QevNrgk7UItovukpx7b0p-6w1d9Q"
		 * ); String PostData = SVoiceMsg("liuwenzhuo", null, null, "3", voice);
		 */
		/**
		 * ����file��ͨ�ļ���Ϣ
		 * */
	/*	
		  File file = new File(); file.setMedia_id(
		  "1UeuS-aHpX5Znqqn2ZgBeNNK27yzlStvAGe9krxawnaYvyz64UCXG45QCKg2MBa7eDV50SlK0q5hxVDg3vOAjUQ"
		  ); String PostData = SFileMsg(null, "1", null, "3", file);
		 */
		/**
		 * ����video��Ƶ��Ϣ
		 * */
        /*
		Video video = new Video();
		video.setMediaId("13y7gq-intMsqm3yVYnXzWkE00rh2yjU733GkkW8ogyw0JVp88JPiiUUwLbSP3RuyPL66xcUICX8S9QTUTn2X8w");
		video.setDescription("�ܺÿ���Ŷ");
		video.setTitle("��Ƶ");
		String PostData = SVideoMsg("liuwenzhuo", null, null, "3", video);
		*/

		/*
		 * 
		 * ���ͱ���news��Ϣ
		 */
		/*
		 * Article article1 = new Article(); article1.setTitle("news��Ϣ����-1");
		 * article1.setDescription("");
		 * article1.setPicUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg"
		 * );
		 * article1.setUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg");
		 * Article article2 = new Article(); article2.setTitle("news��Ϣ����-2");
		 * article2.setDescription("");
		 * article2.setPicUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg"
		 * );
		 * article2.setUrl("http://localhost:8080/WeiXinEnterprisess/123.jpg");
		 * // ����ͼ�� List<Article> list = new ArrayList<Article>();
		 * list.add(article1); list.add(article2); // ͼ��תjson String String
		 * articlesList = JSONArray.fromObject(list).toString(); // Post������
		 * String PostData = SNewsMsg("3", "1", null, "3", articlesList);
		 */
//		  System.out.println(PostData);
//		int result = WeixinUtil.PostMessage(access_token, "POST", POST_URL,
//				PostData); // ��ӡ���
//		if (0 == result) {
//			System.out.println("�����ɹ�");
//		} else {
//			System.out.println("����ʧ��");
//		}
	}
}
