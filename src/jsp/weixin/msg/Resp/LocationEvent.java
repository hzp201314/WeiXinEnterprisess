package jsp.weixin.msg.Resp;

/*
 * 
 * �ϱ�����λ���¼�
 * 
 * */
public class LocationEvent extends BaseEvent {

	// ����λ��γ��
	private String Latitude;
	// ����λ�þ���
	private String Longitude;
	// ����λ�þ���
	private String Precision;
	// ��ҵӦ�õ�id
	private long AgentID;

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	public long getAgentID() {
		return AgentID;
	}

	public void setAgentID(long agentID) {
		AgentID = agentID;
	}

}
