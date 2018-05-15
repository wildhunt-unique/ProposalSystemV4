package cn.edu.qut.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.New;

import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;

public class TreeNode {
	private String id;
	private String text;
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>();

	/**
	 * 创建一个部门信息树 树的id节点是部门的id值 树的text值是部门的部门名
	 * 
	 * @author wildhunt_unique
	 * @param departments
	 *            部门的集合
	 * @return 部门树的节点
	 */
	public static ArrayList<TreeNode> createDeptTree(List<Department> departments) {
		ArrayList<TreeNode> reTreeNode = new ArrayList<TreeNode>();
		TreeNode headNode = new TreeNode();
		headNode.setText("所有部门");
		headNode.setId("AllDepartment");

		ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();

		for (Department department : departments) {
			if (department.getFlag().equals("false")) {// 被删掉的就算了
				continue;
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setId(department.getDeptId() + "");
			treeNode.setText(department.getDeptName());
			treeNodes.add(treeNode);
		}
		headNode.setChildren(treeNodes);
		reTreeNode.add(headNode);
		return reTreeNode;
	}

	/**
	 * 创建一个代表团信息树 树的id节点是代表团的id值 树的text值是代表团的代表团名
	 * 
	 * @author wildhunt_unique
	 * @param departments
	 *            代表团的集合
	 * @return 代表团树的节点
	 */
	public static ArrayList<TreeNode> createDepuTree(List<Deputation> deputations) {
		ArrayList<TreeNode> reTreeNode = new ArrayList<TreeNode>();
		TreeNode headNode = new TreeNode();
		headNode.setText("所有代表团");
		headNode.setId("AllDeputation");

		ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for (Deputation deputation : deputations) {
			if (deputation.getFlag().equals("false")) {// 被删掉的就算了
				continue;
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setId(deputation.getDepuId() + "");
			treeNode.setText(deputation.getDepuName());
			treeNodes.add(treeNode);
		}
		headNode.setChildren(treeNodes);
		reTreeNode.add(headNode);
		return reTreeNode;
	}

	/**
	 * 创建一个代表团成员信息树 树的id节点是成员的id值 树的text值是成员的名字
	 * 
	 * @author wildhunt_unique
	 * @param 代表团对象
	 * @return 代表团成员树的节点
	 */
	public static ArrayList<TreeNode> createDepuMemTree(Deputation deputations) {
		ArrayList<TreeNode> reTreeNode = new ArrayList<TreeNode>();

		Set<Member> members = deputations.getMembers();
		for (Member member : members) {
			if (member.getFlag().equals("false") || member.getMemberClass().equals("root")) {
				continue;
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setId(member.getMemberId() + "");
			treeNode.setText(member.getMemberName());
			reTreeNode.add(treeNode);
		}

		return reTreeNode;
	}

	/**
	 * 创建一个部门成员信息树 树的id节点是成员的id值 树的text值是成员的名字
	 * 
	 * @author wildhunt_unique
	 * @param 部门对象
	 * @return 部门成员成员树的节点
	 */
	public static ArrayList<TreeNode> createDeptMemTree(Department department) {
		ArrayList<TreeNode> reTreeNode = new ArrayList<TreeNode>();

		Set<Member> members = department.getMembers();
		for (Member member : members) {
			if (member.getFlag().equals("false") || member.getMemberClass().equals("root")) {
				continue;
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setId(member.getMemberId() + "");
			treeNode.setText(member.getMemberName());
			reTreeNode.add(treeNode);
		}

		return reTreeNode;
	}

	public TreeNode() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

}
