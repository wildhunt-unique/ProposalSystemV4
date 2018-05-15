package cn.edu.qut.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.qut.DAO.MemberDAO;
import cn.edu.qut.entity.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String empNo_String = request.getParameter("user_name");
		String empPassWrod_String = request.getParameter("user_password");
		String isLoginSuccess = "false";
		
		Member member = null;
		member = MemberDAO.findMemberByLogin(empNo_String, empPassWrod_String);
		
		if (member != null) {
			isLoginSuccess = "true";
			request.getSession().setAttribute("LoginMember", member);
		}
		
		response.getWriter().write(isLoginSuccess);
	}

}
