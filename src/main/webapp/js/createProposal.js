$('#create_news_submit').click(function() {
	if ($("#create_news_title_text").val() == "" || CKEDITOR.instances.create_news_edit.getData() == "") {
		$.messager.alert('错误', '标题和内容皆不能为空!', 'error');
	}
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "news",
			newsTitle : $("#create_news_title_text").val(),
			newsContent : CKEDITOR.instances.create_news_edit.getData(),
		},
		success : function(data, status) {
			promptMessage(data);
			if (data == "success_100000") {
				$("#create_news_title_text").val("");
				CKEDITOR.instances.create_news_edit.setData("");
				changePanel("home_panel");
				$("#view_news_table").datagrid('reload');
			}
		},
		error : function(e) {
			alert("请检查网络连接!!");
		}
	});
})


//提交按钮
$("#create_proposal_submit").click(function() {
	if ($("#create_proposal_title_text").val() == "" || CKEDITOR.instances.create_proposal_edit.getData() == "") {
		$.messager.alert('错误', '标题和内容皆不能为空!', 'error');
	} else {  
		if ((reEditPropNo == 'null' || reEditPropNo == null) && $("#create_proposal_type_combobox").val() == "meeting_proposal") {
			$('#add_meeting_window').window('open');
		} else {
			confirmSumbitProp();
		}
	}
});

//选择会议,取消
$('#add_meeting_cancel').click(function() {
	$('#add_meeting_window').window('close');
})

//选择会议 确定
$('#add_meeting_submit').click(function() {
	var conId = $('#select_meeting_id').val();
	if (conId == "") {
		promptMessage('error_100014');
		return;
	}
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "meetingProp",
			propTitle : $("#create_proposal_title_text").val(),
			propContent : CKEDITOR.instances.create_proposal_edit.getData(),
			propState : "待审核",
			conId : conId
		},
		success : function(data, status) {
			promptMessage(data);
			if (data == "success_100000") {
				$("#create_proposal_title_text").val("");
				CKEDITOR.instances.create_proposal_edit.setData("");
				changePanel("mine_proposal_panel");
				$("#mine_proposal_table").datagrid('reload');
				reEditPropNo = 'null'; //修改部分的
			}
		},
		error : function(e) {
			alert("请检查网络连接!!");
		}
	});
	$('#add_meeting_window').window('close');

})

//$("#create_proposal_save").click(function() {
//	if ($("#create_proposal_title_text").val() == "" || CKEDITOR.instances.create_proposal_edit.getData() == "") {
//		$.messager.alert('错误', '标题和内容皆不能为空!', 'error');
//	} else {
//		if ($("#create_proposal_type_combobox").val() == "meeting_proposal") {
//			promptMessage("error_100015");
//			return;
//		} else {
//			confirmSumbitProp();
//		}
//	}
//});

$("#create_proposal_cancel").click(function() {
	$.messager.confirm('警告', '是否取消当前编辑?未保存的操作将无法找回!', function(r) {
		if (r) {
			$("#create_proposal_title_text").val("");
			CKEDITOR.instances.create_proposal_edit.setData("");

			changePanel("mine_proposal_panel");
			$("#mine_proposal_table").datagrid('reload');
			reEditPropNo = 'null'; //修改部分的
		}
	});
});

function confirmSumbitProp() {
	$.messager.confirm('警告', '是否确认提交?', function(r) {
		if (r) {
			$.ajax({
				url : "create.do",
				type : "POST",
				data : {
					type : "prop",
					propTitle : $("#create_proposal_title_text").val(),
					propContent : CKEDITOR.instances.create_proposal_edit.getData(),
					propType : $("#create_proposal_type_combobox").val(),
					propNo : reEditPropNo, //修改部分的
					propState : "待审核"
				},
				success : function(data, status) {
					promptMessage(data);
					if (data == "success_100000") {
						$("#create_proposal_title_text").val("");
						CKEDITOR.instances.create_proposal_edit.setData("");

						changePanel("mine_proposal_panel");
						$("#mine_proposal_table").datagrid('reload');
						reEditPropNo = 'null'; //修改部分的
					}
				},
				error : function(e) {
					alert("请检查网络连接!!");
				}
			});
		}
	});
}

//function confirmSaveProp() {
//	$.messager.confirm('提示', '保存后可在我的提案中查看,并继续编辑,切勿重复保存!', function(r) {
//		if (r) {
//			$.ajax({
//				url : "create.do",
//				type : "POST",
//				data : {
//					type : "prop",
//					propTitle : $("#create_proposal_title_text").val(),
//					propContent : CKEDITOR.instances.create_proposal_edit.getData(),
//					propType : $("#create_proposal_type_combobox").val(),
//					propNo : editPropNo, //修改部分的
//					propState : "保存"
//				},
//				success : function(data, status) {
//					promptMessage(data);
//					if (data == "success_100000") {
//						$.messager.alert('提示', '保存成功!', 'info');
//
//						$("#create_proposal_title_text").val("");
//						CKEDITOR.instances.create_proposal_edit.setData("");
//
//						changePanel("mine_proposal_panel");
//						$("#mine_proposal_table").datagrid('reload');
//						editPropNo = 'null'; //修改部分的
//					}
//				},
//				error : function(e) {
//					alert("请检查网络连接！！");
//				}
//			});
//		}
//	});
//}

//关于会议的 添加一个会议
$('#add_meeting_btn').click(function() {
	var conName = $('#add_meeting_input').val();
	if (conName == "") {
		promptMessage('error_100013');
		return;
	}
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "meeting",
			conName : conName
		},
		success : function(data, status) {
			promptMessage(data);
			$('#add_meeting_input').textbox('setValue', '');
			$('#select_meeting_id').combobox('reload');
			$('#view_meeting_id').combobox('reload');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
})

//查看某次会议提案
$('#view_meeting_btn_open').click(function() {
	$('#view_meeting_window').window('open');
})


//查看会议 确定
$('#view_meeting_submit').click(function() {
	var conId = $('#view_meeting_id').val();
	$('#view_proposal_table').datagrid('options').pageNumber=1;
	$('#view_proposal_table').datagrid('options').url = "findProp.do?type=meetingProp&conId=" + conId;
	$("#view_proposal_table").datagrid('reload');
	$('#view_meeting_window').window('close');
})

//查看会议 取消
$('#view_meeting_cancel').click(function() {
	$('#view_meeting_window').window('close');
})