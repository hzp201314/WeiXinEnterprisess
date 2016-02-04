package jsp.weixin.oauth2.util;

/** 
 * Oauth2 Servlet�� 
 *  ��Ȩ��֤
 * @date 2015.04.27 
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jsp.weixin.ParamesAPI.util.ParamesAPI;
import jsp.weixin.ParamesAPI.util.WeixinUtil;

public class OAuth2Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String code = request.getParameter("code");
		if (!"authdeny".equals(code)) {
			String access_token = WeixinUtil.getAccessToken(ParamesAPI.corpId,
					ParamesAPI.secret).getToken();
			// agentid ��ת����ʱ���ڵ���ҵӦ��ID
			// ����Ա��ӵ��agent��ʹ��Ȩ�ޣ�agentid�������ת����ʱ���ڵ���ҵӦ��ID��ͬ
			String UserID = GOauth2Core.GetUserID(access_token, code, "3");
			request.setAttribute("UserID", UserID);
		} else {
			out.print("��Ȩ��ȡʧ�ܣ�����Ϊʲô���Լ���ԭ�򡣡���");
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
