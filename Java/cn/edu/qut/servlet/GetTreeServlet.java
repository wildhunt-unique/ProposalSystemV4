package cn.edu.qut.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.qut.beans.Node;
import cn.edu.qut.beans.TreeNode;
import cn.edu.qut.service.AccountManagement;
import cn.edu.qut.service.ProposalManagement;

@WebServlet("/TreeNode.do")
public class GetTreeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetTreeServlet() {
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
		String treeType = request.getParameter("treeType");
		String id = request.getParameter("id");
		ArrayList<TreeNode> treeNode = null;
		ArrayList<Node> deptNode = null;
		ArrayList<Node> meetingNode = null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		PrintWriter out = response.getWriter();
		
		switch (treeType) {
		case "dept":
			treeNode = AccountManagement.getDepartmentTree();
			jsonStr = mapper.writeValueAsString(treeNode);
			break;

		case "depu":
			treeNode = AccountManagement.getDeputationTree();
			jsonStr = mapper.writeValueAsString(treeNode);
			break;

		case "memByDepu":
			treeNode = AccountManagement.getMemTreeByDepu(Integer.parseInt(id));
			jsonStr = mapper.writeValueAsString(treeNode);
			break;
			
		case "memByDept":
			treeNode = AccountManagement.getMemTreeByDept(Integer.parseInt(id));
			jsonStr = mapper.writeValueAsString(treeNode);
			break;

		case "deptNode":
			deptNode = AccountManagement.getDeptNode();
			jsonStr = mapper.writeValueAsString(deptNode);
			break;
			
		case"meeting":
			meetingNode =ProposalManagement.findAllConferenceNode();
			jsonStr = mapper.writeValueAsString(meetingNode);
			break;

		default:
			break;
		}

		out.write(jsonStr);
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
