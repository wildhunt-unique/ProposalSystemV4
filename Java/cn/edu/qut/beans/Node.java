package cn.edu.qut.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.edu.qut.entity.Conference;
import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;

public class Node {
	private String id;
	private String text;

	public Node() {
	}

	
	/**
	 * 所有会议下拉列表
	 * */
	public static ArrayList<Node> createNodeConList(List<Conference> conferences){
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		for (Conference conference : conferences) {
			Node node = new Node();
			node.setId(conference.getConId()+"");
			node.setText(conference.getConName());
			nodes.add(node);
		}
		
		return nodes;
	}
	
	/**
	 * 所有部门下拉列表
	 */
	public static ArrayList<Node> createNodeDeptList(List<Department> departments) {
		ArrayList<Node> nodes = new ArrayList<Node>(0);

		for (Department department : departments) {
			if (department.getFlag().equals("false")) {// 被删掉的就算了
				continue;
			}
			Node node = new Node();
			node.setId(department.getDeptId() + "");
			node.setText(department.getDeptName());
			nodes.add(node);
		}

		return nodes;
	}

	/**
	 * 所有代表团下拉列表
	 */
	public static ArrayList<Node> createNodeDepuList(List<Deputation> deputations) {
		ArrayList<Node> nodes = new ArrayList<Node>(0);

		for (Deputation deputation : deputations) {
			if (deputation.getFlag().equals("false")) {// 被删掉的就算了
				continue;
			}
			Node node = new Node();
			node.setId(deputation.getDepuId() + "");
			node.setText(deputation.getDepuName());
			nodes.add(node);
		}

		return nodes;
	}

	/**
	 * 一个部门下所有成员
	 * */
	public static ArrayList<Node> createNodeDeptMem(Department department) {
		ArrayList<Node> nodes = new ArrayList<Node>(0);
		Set<Member> members = department.getMembers();
		for (Member member : members) {
			if (member.getFlag().equals("false") || member.getMemberClass().equals("root")) {// 被删掉的就算了
				continue;
			}
			Node node = new Node();
			node.setId(member.getMemberId() + "");
			node.setText(member.getMemberName());
			nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 一个代表团所有成员
	 * */
	public static ArrayList<Node> createNodeDepuMem(Deputation deputation) {
		ArrayList<Node> nodes = new ArrayList<Node>(0);
		Set<Member> members = deputation.getMembers();
		for (Member member : members) {
			if (member.getFlag().equals("false") || member.getMemberClass().equals("root")) {// 被删掉的就算了
				continue;
			}
			Node node = new Node();
			node.setId(member.getMemberId() + "");
			node.setText(member.getMemberName());
			nodes.add(node);
		}
		return nodes;
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

}
