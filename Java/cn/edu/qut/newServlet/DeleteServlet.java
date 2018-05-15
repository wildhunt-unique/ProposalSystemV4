package cn.edu.qut.newServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.qut.service.AccountManagement;

@WebServlet("/del.do")
public class DeleteServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeleteServlet() {
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
		PrintWriter out = response.getWriter();

		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String stateCode = "";

		switch (type) {
		case "dept":
			Integer deptId = Integer.parseInt(id);
			stateCode = AccountManagement.deleteDept(deptId);
			break;

		case "depu":
			Integer depuId = Integer.parseInt(id);
			stateCode = AccountManagement.deleteDepu(depuId);
			break;

		case "mem":
			String memberNos = request.getParameter("memberNos");
			String[] memberNo_List = memberNos.split(",");
			ArrayList<String> memArrayList = new ArrayList<String>();
			for (int i = 0; i < memberNo_List.length; i++) {
				memArrayList.add(memberNo_List[i]);
			}
			stateCode = AccountManagement.deleteMember(memArrayList);
			break;
		default:
			break;
		}

		out.write(stateCode);
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
