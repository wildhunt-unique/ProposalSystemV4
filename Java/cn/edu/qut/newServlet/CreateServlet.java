package cn.edu.qut.newServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.qut.common.WordMap;
import cn.edu.qut.entity.Member;
import cn.edu.qut.entity.News;
import cn.edu.qut.entity.Proposal;
import cn.edu.qut.service.AccountManagement;
import cn.edu.qut.service.ProposalManagement;

@WebServlet("/create.do")
public class CreateServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CreateServlet() {
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
		String type = request.getParameter("type");
		String stateCode = "error";
		String propNo = null;
		String propState = null;
		Member LoginMember = null;
		String propContent = null;
		String propTitle = null;
		String propType = null;
		long memberId;
		Proposal proposal = null;

		switch (type) {
		case "meetingProp":
			propContent = request.getParameter("propContent");
			propTitle = request.getParameter("propTitle");
			propType = "会议";
			propState = request.getParameter("propState");
			String conId = request.getParameter("conId");

			LoginMember = (Member) request.getSession().getAttribute("LoginMember");
			memberId = LoginMember.getMemberId();

			proposal = new Proposal();
			proposal.setPropContent(propContent);
			proposal.setPropDate(new Date());
			proposal.setPropState(propState);
			proposal.setPropTitle(propTitle);
			proposal.setPropType(propType);
			stateCode = ProposalManagement.addMeetingProp(proposal, memberId, Integer.parseInt(conId));

			break;

		case "prop":// 新建提案
			propContent = request.getParameter("propContent");
			propTitle = request.getParameter("propTitle");
			propType = request.getParameter("propType");
			propState = request.getParameter("propState");
			propNo = request.getParameter("propNo");

			LoginMember = (Member) request.getSession().getAttribute("LoginMember");
			memberId = LoginMember.getMemberId();

			if (propNo == null || propNo.equals("null")) { // 新建
				proposal = new Proposal();
				proposal.setPropContent(propContent);
				proposal.setPropDate(new Date());
				proposal.setPropState(propState);
				proposal.setPropTitle(propTitle);
				proposal.setPropType(WordMap.translate(propType));
				stateCode = ProposalManagement.addProposal(proposal, memberId);
			} else {
				stateCode = ProposalManagement.modifyProposalByNo(memberId, propNo, propTitle, propContent,
						WordMap.translate(propType), propState);
			}
			break;

		case "support":// 创建一个附议
			propNo = request.getParameter("propNo");
			LoginMember = (Member) request.getSession().getAttribute("LoginMember");
			stateCode = ProposalManagement.addSupport(LoginMember.getMemberId(), propNo);
			break;

		case "check":// 创建一个审核结果
			propNo = request.getParameter("propNo");
			propState = request.getParameter("propState");
			LoginMember = (Member) request.getSession().getAttribute("LoginMember");

			if (LoginMember.getMemberClass().equals("admin")) {// 判断是不是团长,不然不给权限
				stateCode = ProposalManagement.checkProposalByNo(propState, propNo);
			} else {
				stateCode = "error_100003";
			}
			break;

		case "deal":// 创建分配部门
			String primaryDept = request.getParameter("primaryDept");
			String[] helpDept = request.getParameter("helpDept").split(",");
			propNo = request.getParameter("propNo");

			ArrayList<Integer> helpDeptEach = new ArrayList<Integer>();
			try {
				Integer primaryDeptId = Integer.parseInt(primaryDept);

				for (String string : helpDept) {
					if (string.equals("")) {
						continue;
					}
					Integer helpDeptEachId = Integer.parseInt(string);
					helpDeptEach.add(helpDeptEachId);
				}

				stateCode = ProposalManagement.addDeploy(primaryDeptId, propNo, helpDeptEach);
			} catch (Exception e) {
				// TODO: handle exception
				stateCode = "error";// 到时候该成请输入正确的内容
			}
			break;

		case "register":
			propNo = request.getParameter("propNo");
			String propPassType = request.getParameter("registerType");
			stateCode = ProposalManagement.setPassPropByNo(propNo, WordMap.translate(propPassType));
			break;

		case "result":
			LoginMember = (Member) request.getSession().getAttribute("LoginMember");
			String result = request.getParameter("result");
			propNo = request.getParameter("propNo");
			stateCode = ProposalManagement.addImplInfor(propNo, result, LoginMember.getMemberId());
			break;

		case "feedbackSatisfy":
			LoginMember = (Member) request.getSession().getAttribute("LoginMember");
			propNo = request.getParameter("propNo");
			String propSatisf = request.getParameter("propSatisf");
			stateCode = ProposalManagement.addPropStatisfy(LoginMember.getMemberNo(), propNo, propSatisf);
			break;

		case "idea":
			LoginMember = (Member) request.getSession().getAttribute("LoginMember");
			propNo = request.getParameter("propNo");
			String ideaContent = request.getParameter("ideaContent");
			stateCode = ProposalManagement.addPropAlterIdea(propNo, LoginMember.getMemberId(), ideaContent);
			break;

		case "manage":
			String memeberManageNo = request.getParameter("manageNo");
			String manageDeptIds = request.getParameter("deptId");
			String[] manageDeptId = manageDeptIds.split(",");
			ArrayList<Integer> deptIdList = new ArrayList<Integer>();
			for (String string : manageDeptId) {
				if (string.equals("")) {
					continue;
				}
				try {
					deptIdList.add(Integer.parseInt(string));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			stateCode = AccountManagement.addDeptManager(memeberManageNo, deptIdList);
			break;

		case "meeting":
			String conName = request.getParameter("conName");
			stateCode = ProposalManagement.addConference(conName);
			break;

		case "news":
			String newsTitle = request.getParameter("newsTitle");
			String newsContent = request.getParameter("newsContent");
			News news = new News();
			news.setNewsContent(newsContent);
			news.setNewsDate(new Date());
			news.setNewsTitle(newsTitle);
			stateCode = ProposalManagement.createNews(news);
			break;

		default:
			break;
		}

		response.getWriter().write(stateCode);

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
