//添加一个部署按钮
$("#add_deal_prop").click(function() {
	$('#deal_dept_window').window('open');
})

$("#cancel_deal_prop").click(function() {
	changePanel('deal_proposal_panel');
	$('#deal_dept_name').combobox("reload");
	$('#deal_help_dept_name').combobox("reload");
})


$("#deal_dept_window_cancel").click(function() {
	$('#deal_dept_window').window('close');
	$('#deal_dept_name').textbox('setValue', '');
	$('#deal_help_dept_name').textbox('setValue', '');
})

$("#deal_dept_window_submit").click(function() {
	var primaryDept = $('#deal_dept_name').val();
	var helpDept = $('#deal_help_dept_name').val();
	var flag = true;
	if (!$('#deal_dept_name').textbox("isValid")) {
		flag = false;
	}
	if(flag!=true){
		promptMessage("error_100007");
		return;
	}
	$.ajax({
		url : "create.do",
		type : "POST",
		//dataType : "JSON",
		data : {
			type : "deal",
			propNo:dealPropNo,
			primaryDept : primaryDept,
			helpDept : helpDept,
		},
		success : function(data, status) {
			promptMessage(data);
			$('#deal_dept_name').combobox("reload");
			$('#deal_help_dept_name').combobox("reload");
			$('#deal_dept_window').window('close');
			changePanel('deal_proposal_panel');
			$('deal_proposal_panel').datagrid("reload");
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
})



