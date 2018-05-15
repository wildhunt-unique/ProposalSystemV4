package cn.edu.qut.newServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.qut.beans.MemberInfo;
import cn.edu.qut.beans.SortMemByNo;
import cn.edu.qut.common.PageInfo;
import cn.edu.qut.service.AccountManagement;

/**
 * Servlet implementation class GetMemberServlet
 */
@WebServlet("/findMem.do")
public class FindMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FindMemberServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String rowsString = null;
		String pageString = null;
		ArrayList<MemberInfo> memberInfos = null;
		ObjectMapper mapper = new ObjectMapper();
		rowsString = request.getParameter("rows");  
		pageString = request.getParameter("page"); 
		
		switch (type) {
		case "depu":
			if (id.equals("AllDeputation")) { // 所有
				memberInfos = AccountManagement.findMemberByDepuId(null);
			} else { // 按照id查
				memberInfos = AccountManagement.findMemberByDepuId(Integer.parseInt(id));
			}
			break;

		case "dept":
			if (id.equals("AllDepartment")) { // 所有
				memberInfos = AccountManagement.findMemberByDepaId(null);
			} else { // 按照id
				memberInfos = AccountManagement.findMemberByDepaId(Integer.parseInt(id));
			}
			break;
			
		case"ByName":
			memberInfos = AccountManagement.findMemByName(id);
			break;
			
		default:
			break;
		}
		
		int page = Integer.parseInt(pageString);
		int rows = Integer.parseInt(rowsString);
		Collections.sort(memberInfos, new SortMemByNo());
		PageInfo pageInfo = PageInfo.cratePageInfo(memberInfos, page, rows);
		String jsonStr = mapper.writeValueAsString(pageInfo);
		PrintWriter out = response.getWriter();
		out.write(jsonStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
