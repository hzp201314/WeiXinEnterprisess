package jsp.weixin.msg.agent.until;

/**
 * 
 * ��ҵӦ�����ò���
 * 
 * */
public class EnterpriseAgent {
	// ��ҵӦ��id
	private String agentid;
	// �Ƿ�򿪵���λ���ϱ�
	private String report_location_flag;
	// ��ҵӦ��ͷ���mediaid
	private String logo_mediaid;
	// ��ҵӦ������
	private String name;
	// ��ҵӦ������
	private String decription;
	// ��ҵӦ�ÿ�������
	private String redirect_domain;
	// �Ƿ�����û����֪ͨ��0�������գ�1������
	private int isreportuser;
	// �Ƿ��ϱ��û�����Ӧ���¼���0�������գ�1������
	private int isreportenter;

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}


	public String getLogo_mediaid() {
		return logo_mediaid;
	}

	public void setLogo_mediaid(String logo_mediaid) {
		this.logo_mediaid = logo_mediaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public String getRedirect_domain() {
		return redirect_domain;
	}

	public void setRedirect_domain(String redirect_domain) {
		this.redirect_domain = redirect_domain;
	}

	public int getIsreportuser() {
		return isreportuser;
	}

	public void setIsreportuser(int isreportuser) {
		this.isreportuser = isreportuser;
	}

	public int getIsreportenter() {
		return isreportenter;
	}

	public void setIsreportenter(int isreportenter) {
		this.isreportenter = isreportenter;
	}

	public String getReport_location_flag() {
		return report_location_flag;
	}

	public void setReport_location_flag(String report_location_flag) {
		this.report_location_flag = report_location_flag;
	}

}
