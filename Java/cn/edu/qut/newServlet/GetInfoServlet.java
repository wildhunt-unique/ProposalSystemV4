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

import cn.edu.qut.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.qut.DAO.ProposalDAO;
import cn.edu.qut.beans.DeptInfo;
import cn.edu.qut.beans.DepuInfo;
import cn.edu.qut.beans.MemberInfo;
import cn.edu.qut.beans.NewsInfo;
import cn.edu.qut.beans.ProposalInfo;
import cn.edu.qut.beans.SortByDate;
import cn.edu.qut.entity.News;
import cn.edu.qut.service.AccountManagement;
import cn.edu.qut.service.ProposalManagement;

@WebServlet("/getInfo.do")
public class GetInfoServlet extends HttpServlet {

    /**
     * Constructor of the object.
     */
    public GetInfoServlet() {
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
     * <p>
     * This method is called when a form has its tag value method equals to get.
     *
     * @param request  the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException      if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     * <p>
     * This method is called when a form has its tag value method equals to
     * post.
     *
     * @param request  the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException      if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String type = request.getParameter("type");
        String id = request.getParameter("id");

        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        String memberNo = null;
        Member LoginMember = (Member) request.getSession().getAttribute("LoginMember");
        MemberInfo memberInfo = null;
        switch (type) {

            case "loginMemberInfo"://找到当前登陆人的信息
                memberInfo = AccountManagement.getMemberInfoByNo(LoginMember.getMemberNo());
                jsonStr = mapper.writeValueAsString(memberInfo);
                break;

            case "depu":// 找到代表团信息
                DepuInfo depuInfo = null;
                depuInfo = AccountManagement.getDepuInfoById(Integer.parseInt(id));
                jsonStr = mapper.writeValueAsString(depuInfo);
                break;

            case "dept":// 找到部门信息
                DeptInfo deptInfo = null;
                deptInfo = AccountManagement.getDeptInfoById(Integer.parseInt(id));
                jsonStr = mapper.writeValueAsString(deptInfo);
                break;

            case "mem":// 找到一个成员的信息
                memberNo = request.getParameter("memberNo");
                memberInfo = AccountManagement.getMemberInfoByNo(memberNo);
                jsonStr = mapper.writeValueAsString(memberInfo);
                break;

            case "prop":// 找到一个提案的信息
                String propNo = request.getParameter("propNo");
                ProposalInfo proposalInfo = ProposalManagement.getProposalInfoByNo(propNo);
                jsonStr = mapper.writeValueAsString(proposalInfo);
                break;

            case "news"://得到一个新闻的具体信息
                id = request.getParameter("id");
                NewsInfo newsInfo = ProposalManagement.getNewsById(Integer.parseInt(id));
                jsonStr = mapper.writeValueAsString(newsInfo);
                break;

            case "allNews"://得到所有新闻的头信息

                ArrayList<NewsInfo> newsInfos_ArrayList = ProposalManagement.findAllNews();
                jsonStr = mapper.writeValueAsString(newsInfos_ArrayList);
                break;

            case "isSameNo":// 是否重复的工号
                memberNo = request.getParameter("memberNo");
                if (AccountManagement.judgeMemberNo(memberNo)) {
                    jsonStr = "true";
                } else {
                    jsonStr = "false";
                }
                break;

            default:
                break;
        }

        out.write(jsonStr);
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
