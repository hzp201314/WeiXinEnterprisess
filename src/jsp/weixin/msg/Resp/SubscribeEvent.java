package jsp.weixin.msg.Resp;

/*
 * 
 * ��ע/ȡ����ע�¼�
 * 
 * */
public class SubscribeEvent extends BaseEvent {

	// ��ҵӦ�õ�ID,����.����Ӧ�õ�����ҳ���ȡ;���idΪ0,���ʾ ��������ҵ�ŵĹ�ע/ȡ����ע�¼�
	private long AgentID;

	public long getAgentID() {
		return AgentID;
	}

	public void setAgentID(long agentID) {
		AgentID = agentID;
	}
}
