package cn.edu.qut.newServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.qut.entity.Member;
import cn.edu.qut.service.AccountManagement;

@WebServlet("/modify.do")
public class ModifyServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ModifyServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String stateCode = "";
		String type = request.getParameter("type");
		String deptId = null;
		String depuId = null;
		long id = 0;
		switch (type) {
		case "depu"://修改代表团
			depuId = request.getParameter("depuId");
			String depuName = request.getParameter("depuName");
			String memberId = request.getParameter("mamageId");//改团长
			
			if (memberId!=null && !memberId.equals("")) {
				id = Integer.parseInt(memberId);
			}
			stateCode = AccountManagement.modifyDepu(Integer.parseInt(depuId), id, depuName);
			break;

		case "dept"://修改部门
			deptId = request.getParameter("deptId");
			String deptName = request.getParameter("deptName");
			String deptMaster = request.getParameter("deptMaster");
			stateCode = AccountManagement.modifyDept(Integer.parseInt(deptId), deptName,deptMaster);
			break;

		case "mem"://修改成员
			String memberName = request.getParameter("memberName");
			String memberNo = request.getParameter("memberNo");
			String memberPassword = request.getParameter("memberPass");
			deptId = request.getParameter("deptId");
			depuId = request.getParameter("depuId");
			String memberClass = request.getParameter("memberClass");
			
			Member member = new Member();
			member.setMemberName(memberName);
			member.setMemberNo(memberNo);
			member.setMemberPassword(memberPassword);
			member.setMemberClass(memberClass);
			
			stateCode = AccountManagement.alterMember(member, Integer.parseInt(deptId), Integer.parseInt(depuId));
			break;
		default:
			break;
		}
		response.getWriter().write(stateCode);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
