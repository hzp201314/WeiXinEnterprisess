package jsp.weixin.msg.Resp;

/**
 * ��Ƶ
 * 
 * @author Engineer.Jsp
 * @date 2014.10.08*
 */
public class Video {
	// ý���ļ�id
	private String MediaId;
	// ����
	private String title;
	// ��Ƶ����
	private String description;
	// ����ͼ��ý��id
	private String ThumbMediaId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
