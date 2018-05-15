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

import cn.edu.qut.DAO.ProposalDAO;
import cn.edu.qut.beans.ProposalInfo;
import cn.edu.qut.beans.SortByDate;
import cn.edu.qut.beans.SortPropByNo;
import cn.edu.qut.common.PageInfo;
import cn.edu.qut.entity.Member;
import cn.edu.qut.service.AccountManagement;
import cn.edu.qut.service.ProposalManagement;

/**
 * Servlet implementation class FindAllProposalServlet
 */
@WebServlet("/findProp.do")
public class FindProposalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FindProposalServlet() {
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
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		String type = request.getParameter("type");
		ArrayList<ProposalInfo> proposalInfo_List = new ArrayList<ProposalInfo>();
		Member member = null;
		String rowsString = null;
		String pageString = null;
		rowsString = request.getParameter("rows");  
		pageString = request.getParameter("page"); 

		switch (type) {
		case "AllPropInfo":// 查看所有的提案信息
			proposalInfo_List = ProposalManagement.findAllProposalInfo();
			break;

		case "CheckPropInfo":// 查看可以被审核的信息
			member = (Member) request.getSession().getAttribute("LoginMember");
			proposalInfo_List = ProposalManagement.findCheckProposalInfos(member.getMemberId());
			break;

		case "MinePropInfo":
			member = (Member) request.getSession().getAttribute("LoginMember");
			proposalInfo_List = ProposalManagement.findProposalByMember(member.getMemberId());
			break;

		case "DealPropInfo":// 查看可以被部署的提案信息
			proposalInfo_List = ProposalManagement.findAllPassProp();
			break;

		case "SupportPropInfo":// 找到可以进行附议的提案 findProp.do?type=SupportPropInfo
			member = (Member) request.getSession().getAttribute("LoginMember");
			proposalInfo_List = ProposalManagement.findSupportPropByMember(member.getMemberId());
			break;

		case "ByCondition":// 条件查询
			String propState = request.getParameter("propState");
			String propType = request.getParameter("propType");
			String beginDate = request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			proposalInfo_List = ProposalManagement.findPropByCondition(propState, propType, beginDate, endDate);
			break;

		case "registerPropInfo":// 查找可以被分类的提案
			proposalInfo_List = ProposalManagement.findPassProp();
			break;

		case "resultPrimaryPropInfo":// 登陆人所在部门所承办的所有提案
			member = (Member) request.getSession().getAttribute("LoginMember");
			proposalInfo_List = ProposalManagement.findPrimaryProp(member.getMemberId());

			break;

		case "resultHelpPropInfo":/// 登陆人所在部门所协办的所有提案
			member = (Member) request.getSession().getAttribute("LoginMember");
			proposalInfo_List = ProposalManagement.findHelpProp(member.getMemberId());
			break;

		case "managePropInfo":// 登陆人分管的部门
			member = (Member) request.getSession().getAttribute("LoginMember");
			proposalInfo_List = ProposalManagement.findProposalByPrex(member.getMemberId());
			break;

		case "meetingProp":// 按照会议找提案
			String conId = request.getParameter("conId");
			proposalInfo_List = ProposalManagement.findProposalByConId(Integer.parseInt(conId));
			break;

		default:
			break;
		}

		Collections.sort(proposalInfo_List, new SortPropByNo());
		int page = Integer.parseInt(pageString);
		int rows = Integer.parseInt(rowsString);
		PageInfo pageInfo = PageInfo.cratePageInfo(proposalInfo_List, page, rows);
		jsonStr = mapper.writeValueAsString(pageInfo);
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
