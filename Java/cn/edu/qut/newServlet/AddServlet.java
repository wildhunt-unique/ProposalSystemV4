package cn.edu.qut.newServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;
import cn.edu.qut.service.AccountManagement;

@WebServlet("/add.do")
public class AddServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddServlet() {
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
		doPost(request, response);
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String type = request.getParameter("type");
		String name = null;
		String stateCode = "";

		switch (type) {

		case "dept":// 增加部门
			Department dpt = new Department();
			name = request.getParameter("name");
			dpt.setDeptName(name);
			stateCode = AccountManagement.addDepartment(dpt);
			break;

		case "depu":// 增加代表团
			Deputation deputation = new Deputation();
			name = request.getParameter("name");
			deputation.setDepuName(name);
			stateCode = AccountManagement.addDeputation(deputation);
			break;
			
		case "mem"://增加成员
			String memberName = request.getParameter("memberName");
			String memberNo = request.getParameter("memberNo");
			String memberPass = request.getParameter("memberPass");
			String deptId = request.getParameter("deptId");
			String depuId = request.getParameter("depuId");
			Member member = new Member();
			member.setMemberName(memberName);
			member.setMemberNo(memberNo);
			member.setMemberPassword(memberPass);
			member.setMemberClass("user");// 默认级别
			if (depuId.equals("")) {
				stateCode = AccountManagement.addMember(member, Integer.parseInt(deptId), 10000);// 默认代表团
			} else {
				stateCode = AccountManagement.addMember(member, Integer.parseInt(deptId), Integer.parseInt(depuId));
			}
			break;

		default:
			stateCode = "error";
			break;
		}

		out.write(stateCode);
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
