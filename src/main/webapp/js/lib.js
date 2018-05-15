
// 这是一段测试  测试vscode能更改myeclipse的内容
function promptMessage(data) {
	switch (data) {
	case "success_100003":
		$.messager.alert('提示', '部署成功!', 'info');
		break;
	case "success_100002":
		$.messager.alert('提示', '提交成功!', 'info');
		break;
	case "success_100001":
		$.messager.alert('提示', '附议成功!', 'info');
		break;

	case "success_100000":
		$.messager.alert('提示', '操作成功!', 'info');
		break;

	case "error_100001":
		$.messager.alert('错误', '您已附议过该提案,不可重复附议!', 'error');
		break;

	case "error_100002":
		$.messager.alert('错误', '不能自己附议自己提出的提案!', 'error');
		break;

	case "error_100003":
		$.messager.alert('错误', '无权进行此项操作!', 'error');
		break;

	case "error_100004":
		$.messager.alert('错误', '不是您的提案,无权进行此项操作!', 'error');
		break;

	case "error_100005":
		$.messager.alert('错误', '无权进行此项操作!只能审核会议,或者达到规定附议人数的提案!', 'error');
		break;

	case "error_100006":
		$.messager.alert('错误', '请选择正确的部门/代表团', 'error');
		break;

	case "error_100007":
		$.messager.alert('错误', '还有未完成的项', 'error');
		break;

	case "error_100008":
		$.messager.alert('错误', '请选中需要删除的成员!', 'error');
		break;

	case "error_100009":
		$.messager.alert('错误', '请选中需要修改的成员!', 'error');
		break;

	case "error_100010":
		$.messager.alert('错误', '请选中需要删除的部门!', 'error');
		break;

	case "error_100011":
		$.messager.alert('错误', '意见不能为空!', 'error');
		break;

	case "error_100012":
		$.messager.alert('错误', '请选择需要分管的部门!', 'error');
		break;

	case "error_100013":
		$.messager.alert('错误', '输入的会议名不能为空!', 'error');
		break;
		
	case "error_100014":
		$.messager.alert('错误', '请选择提案所附属的会议!', 'error');
		break;
		
	case "error_100015":
		$.messager.alert('错误', '会议不可保存!', 'error');
		break;

	default:
		$.messager.alert('错误', '发生未知错误!', 'error');
		break;
	}
}



//换功能面板
function changePanel(id) {
	$('.easyui-datagrid').datagrid('options').pageNumber=1;
	if (id == "create_proposal_panel") {
		$("#create_proposal_title_text").val("");
		CKEDITOR.instances.create_proposal_edit.setData("");
		$('.view_proposal_type_select').css('visibility', 'visible');
	} else if (id == "view_proposal_panel") {
		$('#view_proposal_table').datagrid('options').url = "findProp.do?type=AllPropInfo";
	}
	$(".right_content_panel").css("display", "none");
	$("#" + id).css("display", "block");
}

$(window).resize(function() {
	var contentHeihgt = $("#root_right_content").height() - 40;
	var contentWidth = $("#root_right_content").width();

	$('.easyui-datagrid').datagrid('resize', {
		height : contentHeihgt,
		width : contentWidth
	});
});