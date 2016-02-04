/**
 * �Թ���ƽ̨���͸������˺ŵ���Ϣ�ӽ���ʾ������.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

/**
 * ���org.apache.commons.codec.binary.Base64��
 * ��Ҫ����ܰ�commons-codec-1.9����commons-codec-1.8�������汾��
 * �ٷ����ص�ַ��http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package jsp.weixin.encryption.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * �ṩ���պ����͸�����ƽ̨��Ϣ�ļӽ��ܽӿ�(UTF8������ַ���).
 * <ol>
 * <li>�������ظ�������Ϣ������ƽ̨</li>
 * <li>�������յ�����ƽ̨���͵���Ϣ����֤��Ϣ�İ�ȫ�ԣ�������Ϣ���н��ܡ�</li>
 * </ol>
 * ˵�����쳣java.security.InvalidKeyException:illegal Key Size�Ľ������
 * <ol>
 * <li>�ڹٷ���վ����JCE������Ȩ�޲����ļ���JDK7�����ص�ַ��
 * http://www.oracle.com/technetwork/java/javase
 * /downloads/jce-7-download-432124.html</li>
 * <li>���غ��ѹ�����Կ���local_policy.jar��US_export_policy.jar�Լ�readme.txt</li>
 * <li>�����װ��JRE��������jar�ļ��ŵ�%JRE_HOME%\lib\securityĿ¼�¸���ԭ�����ļ�</li>
 * <li>�����װ��JDK��������jar�ļ��ŵ�%JDK_HOME%\jre\lib\securityĿ¼�¸���ԭ���ļ�</li>
 * </ol>
 */
public class WXBizMsgCrypt {
	Charset CHARSET = StandardCharsets.UTF_8;
	Base64 base64 = new Base64();
	byte[] aesKey;
	String token;
	String corpId;

	/**
	 * ���캯��
	 * 
	 * @param token
	 *            ����ƽ̨�ϣ����������õ�token
	 * @param encodingAesKey
	 *            ����ƽ̨�ϣ����������õ�EncodingAESKey
	 * @param CorpId
	 *            ��ҵ��corpid
	 * 
	 * @throws AesException
	 *             ִ��ʧ�ܣ���鿴���쳣�Ĵ�����;���Ĵ�����Ϣ
	 */
	public WXBizMsgCrypt(String token, String encodingAesKey, String CorpId)
			throws AesException {
		if (encodingAesKey.length() != 43) {
			throw new AesException(AesException.IllegalAesKey);
		}

		this.token = token;
		this.corpId = CorpId;
		aesKey = Base64.decodeBase64(encodingAesKey + "=");
	}

	// ����4���ֽڵ������ֽ���
	byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}

	// ��ԭ4���ֽڵ������ֽ���
	int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	// �������16λ�ַ���
	String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * �����Ľ��м���.
	 * 
	 * @param text
	 *            ��Ҫ���ܵ�����
	 * @return ���ܺ�base64������ַ���
	 * @throws AesException
	 *             aes����ʧ��
	 */
	String encrypt(String randomStr, String text) throws AesException {
		ByteGroup byteCollector = new ByteGroup();
		byte[] randomStrBytes = randomStr.getBytes(CHARSET);
		byte[] textBytes = text.getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
		byte[] corpidBytes = corpId.getBytes(CHARSET);

		// randomStr + networkBytesOrder + text + corpid
		byteCollector.addBytes(randomStrBytes);
		byteCollector.addBytes(networkBytesOrder);
		byteCollector.addBytes(textBytes);
		byteCollector.addBytes(corpidBytes);

		// ... + pad: ʹ���Զ������䷽ʽ�����Ľ��в�λ���
		byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
		byteCollector.addBytes(padBytes);

		// ������յ��ֽ���, δ����
		byte[] unencrypted = byteCollector.toBytes();

		try {
			// ���ü���ģʽΪAES��CBCģʽ
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

			// ����
			byte[] encrypted = cipher.doFinal(unencrypted);

			// ʹ��BASE64�Լ��ܺ���ַ������б���
			String base64Encrypted = base64.encodeToString(encrypted);

			return base64Encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.EncryptAESError);
		}
	}

	/**
	 * �����Ľ��н���.
	 * 
	 * @param text
	 *            ��Ҫ���ܵ�����
	 * @return ���ܵõ�������
	 * @throws AesException
	 *             aes����ʧ��
	 */
	String decrypt(String text) throws AesException {
		byte[] original;
		try {
			// ���ý���ģʽΪAES��CBCģʽ
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey,
					0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

			// ʹ��BASE64�����Ľ��н���
			byte[] encrypted = Base64.decodeBase64(text);

			// ����
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.DecryptAESError);
		}

		String xmlContent, from_corpid;
		try {
			// ȥ����λ�ַ�
			byte[] bytes = PKCS7Encoder.decode(original);

			// ����16λ����ַ���,�����ֽ����corpId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

			int xmlLength = recoverNetworkBytesOrder(networkOrder);

			xmlContent = new String(Arrays.copyOfRange(bytes, 20,
					20 + xmlLength), CHARSET);
			from_corpid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength,
					bytes.length), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.IllegalBuffer);
		}

		// corpid����ͬ�����
		if (!from_corpid.equals(corpId)) {
			throw new AesException(AesException.ValidateCorpidError);
		}
		return xmlContent;

	}

	/**
	 * ������ƽ̨�ظ��û�����Ϣ���ܴ��.
	 * <ol>
	 * <li>��Ҫ���͵���Ϣ����AES-CBC����</li>
	 * <li>���ɰ�ȫǩ��</li>
	 * <li>����Ϣ���ĺͰ�ȫǩ�������xml��ʽ</li>
	 * </ol>
	 * 
	 * @param replyMsg
	 *            ����ƽ̨���ظ��û�����Ϣ��xml��ʽ���ַ���
	 * @param timeStamp
	 *            ʱ����������Լ����ɣ�Ҳ������URL������timestamp
	 * @param nonce
	 *            ������������Լ����ɣ�Ҳ������URL������nonce
	 * 
	 * @return ���ܺ�Ŀ���ֱ�ӻظ��û������ģ�����msg_signature, timestamp, nonce,
	 *         encrypt��xml��ʽ���ַ���
	 * @throws AesException
	 *             ִ��ʧ�ܣ���鿴���쳣�Ĵ�����;���Ĵ�����Ϣ
	 */
	public String EncryptMsg(String replyMsg, String timeStamp, String nonce)
			throws AesException {
		// ����
		String encrypt = encrypt(getRandomStr(), replyMsg);

		// ���ɰ�ȫǩ��
		if (timeStamp == "") {
			timeStamp = Long.toString(System.currentTimeMillis());
		}

		String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);

		// System.out.println("���͸�ƽ̨��ǩ����: " + signature[1].toString());
		// ���ɷ��͵�xml
		String result = XMLParse.generate(encrypt, signature, timeStamp, nonce);
		return result;
	}

	/**
	 * ������Ϣ����ʵ�ԣ����һ�ȡ���ܺ������.
	 * <ol>
	 * <li>�����յ����������ɰ�ȫǩ��������ǩ����֤</li>
	 * <li>����֤ͨ��������ȡxml�еļ�����Ϣ</li>
	 * <li>����Ϣ���н���</li>
	 * </ol>
	 * 
	 * @param msgSignature
	 *            ǩ��������ӦURL������msg_signature
	 * @param timeStamp
	 *            ʱ�������ӦURL������timestamp
	 * @param nonce
	 *            ���������ӦURL������nonce
	 * @param postData
	 *            ���ģ���ӦPOST���������
	 * 
	 * @return ���ܺ��ԭ��
	 * @throws AesException
	 *             ִ��ʧ�ܣ���鿴���쳣�Ĵ�����;���Ĵ�����Ϣ
	 */
	public String DecryptMsg(String msgSignature, String timeStamp,
			String nonce, String postData) throws AesException {

		// ��Կ�������˺ŵ�app secret
		// ��ȡ����
		Object[] encrypt = XMLParse.extract(postData);

		// ��֤��ȫǩ��
		String signature = SHA1.getSHA1(token, timeStamp, nonce,
				encrypt[1].toString());

		// ��URL�е�ǩ���Ƚ��Ƿ����
		// System.out.println("�������յ�URL�е�ǩ����" + msg_sign);
		// System.out.println("������У��ǩ����" + signature);
		if (!signature.equals(msgSignature)) {
			throw new AesException(AesException.ValidateSignatureError);
		}

		// ����
		String result = decrypt(encrypt[1].toString());
		return result;
	}

	/**
	 * ��֤URL
	 * 
	 * @param msgSignature
	 *            ǩ��������ӦURL������msg_signature
	 * @param timeStamp
	 *            ʱ�������ӦURL������timestamp
	 * @param nonce
	 *            ���������ӦURL������nonce
	 * @param echoStr
	 *            ���������ӦURL������echostr
	 * 
	 * @return ����֮���echostr
	 * @throws AesException
	 *             ִ��ʧ�ܣ���鿴���쳣�Ĵ�����;���Ĵ�����Ϣ
	 */
	public String VerifyURL(String msgSignature, String timeStamp,
			String nonce, String echoStr) throws AesException {
		String signature = SHA1.getSHA1(token, timeStamp, nonce, echoStr);

		if (!signature.equals(msgSignature)) {
			throw new AesException(AesException.ValidateSignatureError);
		}

		String result = decrypt(echoStr);
		return result;
	}

}