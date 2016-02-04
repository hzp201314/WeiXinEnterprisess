package jsp.weixin.media.util;

/**
 * ý��ӿ���
 * @author Engineer.Jsp
 * @date 2014.10.10*/
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;
import net.sf.json.JSONObject;

public class MUDload {

	/**
	 * �ϴ�ý���ļ�
	 * 
	 * @param accessToken
	 *            �ӿڷ���ƾ֤
	 * @param type
	 *            ý���ļ����ͣ��ֱ���ͼƬ��image����������voice������Ƶ��video������ͨ�ļ�(file)
	 * @param media
	 *            form-data��ý���ļ���ʶ����filename��filelength��content-type����Ϣ
	 * @param mediaFileUrl
	 *            ý���ļ���url �ϴ���ý���ļ����� ͼƬ��image��:1MB��֧��JPG��ʽ
	 *            ������voice����2MB�����ų��Ȳ�����60s��֧��AMR��ʽ ��Ƶ��video����10MB��֧��MP4��ʽ
	 *            ��ͨ�ļ���file����10MB
	 * */
	public static WeixinMedia uploadMedia(String accessToken, String type,
			String mediaFileUrl) {
		WeixinMedia weixinMedia = null;
		// ƴװ�����ַ
		String uploadMediaUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken)
				.replace("TYPE", type);

		// �������ݷָ���
		String boundary = "------------7da2e536604c8";
		try {
			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl
					.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// ��������ͷContent-Type
			uploadConn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// ��ȡý���ļ��ϴ������������΢�ŷ�����д���ݣ�
			OutputStream outputStream = uploadConn.getOutputStream();

			URL mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl
					.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("POST");

			// ������ͷ�л�ȡ��������
			String contentType = meidaConn.getHeaderField("Content-Type");
			// �������������ж��ļ���չ��
			String fileExt = WeixinUtil.getFileEndWitsh(contentType);
			// �����忪ʼ
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream
					.write(String
							.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n",
									fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n",
					contentType).getBytes());

			// ��ȡý���ļ�������������ȡ�ļ���
			BufferedInputStream bis = new BufferedInputStream(
					meidaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				// ��ý���ļ�д�����������΢�ŷ�����д���ݣ�
				outputStream.write(buf, 0, size);
			}
			// ���������
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			bis.close();
			meidaConn.disconnect();

			// ��ȡý���ļ��ϴ�������������΢�ŷ����������ݣ�
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			System.out.println(buffer);
			bufferedReader.close();
			inputStreamReader.close();
			// �ͷ���Դ
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();

			// ʹ��JSON-lib�������ؽ��
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
			weixinMedia = new WeixinMedia();
			weixinMedia.setType(jsonObject.getString("type"));
			// type���� ����ͼ��thumb�� ʱ�ķ��ؽ�����������Ͳ�һ��
			if ("thumb".equals(type))
				weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			else
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
			weixinMedia.setCreatedAt(jsonObject.getInt("created_at"));
		} catch (Exception e) {
			weixinMedia = null;
			String error = String.format("�ϴ�ý���ļ�ʧ�ܣ�%s", e);
			System.out.println(error);
		}
		return weixinMedia;
	}

	public static JSONObject send(String token, String fileType, String filePath)
			throws Exception {
		String result = null;
		File file = new File(filePath);
		System.out.println(file.getName());
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}
		/**
		 * ��һ����
		 */
		// ƴװ�����ַ
		String uploadMediaUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", token).replace(
				"TYPE", fileType);
		URL urlObj = new URL(uploadMediaUrl);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST"); // ��Post��ʽ�ύ����Ĭ��get��ʽ
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post��ʽ����ʹ�û���
		// ��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// ���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);
		con.setRequestProperty("Content-disposition:filename=", file.getName());
		// ����������Ϣ
		// ��һ���֣�
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // �����������
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-disposition: form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		System.out.println(sb);
		// ��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// �����ͷ
		out.write(head);
		// �ļ����Ĳ���
		// ���ļ������ļ��ķ�ʽ ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// ��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// ����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("����POST��������쳣��" + e);
			e.printStackTrace();
			throw new IOException("���ݶ�ȡ�쳣");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		JSONObject jsonObj = JSONObject.fromObject(result);
		return jsonObj;
	}

	/**
	 * ��ȡý���ļ�
	 * 
	 * @param accessToken
	 *            �ӿڷ���ƾ֤
	 * @param media_id
	 *            ý���ļ�id
	 * @param savePath
	 *            �ļ��ڷ������ϵĴ洢·��
	 * */
	public static String downloadMedia(String mediaId, String savePath,
			String access_token) {

		String filePath = null;
		// ƴ�������ַ
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token).replace(
				"MEDIA_ID", mediaId);
		System.out.println(requestUrl);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// ��ȡ�ļ���
			String fileName = conn.getHeaderField("Content-Disposition");
			// ��mediaId��Ϊ�ļ���
			filePath = savePath + fileName;

			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			String info = String.format("����ý���ļ��ɹ���filePath=" + filePath);
			System.out.println(info);
		} catch (Exception e) {
			filePath = null;
			String error = String.format("����ý���ļ�ʧ�ܣ�%s", e);
			System.out.println(error);
		}
		return filePath;
	}

	/**
	 * �ϴ���ý���ļ�
	 * 
	 * @param filetype
	 *            �ļ�����
	 * @param fileurl
	 *
	 *            �ļ�·��
	 */

	public static void UploadFile(String filetype, String fileurl,
			String access_token) throws Exception {
		JSONObject jsonObject = send(access_token, "file", fileurl);
		String media_id = jsonObject.getString("media_id");
		if (null != media_id) {
			System.out.println(media_id);
			System.out.println("�ϴ��ɹ���");
		} else {
			System.out.println("�ϴ�ʧ�ܣ�");
		}
	}

	private static final String upload_wechat_url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * �����ļ��ϴ��ļ���΢�ŷ�����
	 * 
	 * @param accessToken
	 * @param type
	 * @param file
	 * @return
	 */
	public static JSONObject uploadByFile(String accessToken, String type,
			File file) {
		JSONObject jsonObject = null;
		String last_wechat_url = upload_wechat_url.replace("ACCESS_TOKEN",
				accessToken).replace("TYPE", type);
		// �������ݷָ��
		String boundary = "----------sunlight";
		try {
			URL url = new URL(last_wechat_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + boundary);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();// ����������ݷָ���
			StringBuilder sb = new StringBuilder();
			sb.append("--");
			sb.append(boundary);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
					+ file.getName() + "\"\r\n");
			System.out.println(file.getName());
			sb.append("Content-Type:application/octet-stream\r\n\r\n");

			byte[] data = sb.toString().getBytes();
			out.write(data);
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024 * 8];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.write("\r\n".getBytes()); // ����ļ�ʱ�������ļ�֮��������
			in.close();
			out.write(end_data);
			out.flush();
			out.close();

			// ����BufferedReader����������ȡURL����Ӧ
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			// ʹ��json����
			jsonObject = JSONObject.fromObject(buffer.toString());
			System.out.println(jsonObject);

		} catch (Exception e) {
			System.out.println("����POST��������쳣��" + e);
			e.printStackTrace();
		}
		return jsonObject;
	}

	// ����
	public static void main(String[] args) throws Exception {
		// ��ȡƾ֤

		String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
				ParamesAPI.secret).getToken();

		/**
		 * �ϴ���ý���ļ�
		 *
		 **/

		// UploadFile("image", "D:\\Workspace\\456.jpg", access_token);
//		uploadByFile(access_token, "image", new File("D:\\Workspace\\456.jpg"));
		/**
		 * 
		 * ���ض�ý���ļ�
		 * 
		 * */

		// downloadMedia(
		// "1rf2uM2SVAOtEyxG93pYCkZ-u3oCPzgQ8i9JwuqjSNupzjIsrZxTz_bTxjASc0-0Q",
		// "D:/WorkSpace", access_token);

		// ��ַ
		/*
		 * WeixinMedia weixinMedia = uploadMedia(access_token, "file",
		 * "http://localhost:8080/WeiXinEnterprisess/123.jpg"); // media_id
		 * System.out.println(weixinMedia.getMediaId()); // ����
		 * System.out.println(weixinMedia.getType()); // ʱ���
		 * System.out.println(weixinMedia.getCreatedAt());
		 */

		// ��ӡ���

	}
}
