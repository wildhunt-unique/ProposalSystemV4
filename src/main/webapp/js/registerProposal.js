//分类 点开
$('#register_proposal_pass_btn').click(function() {
	$('#register_dept_window').window('open');
})


//分类 取消
$('#register_proposal_pass_cancel').click(function() {
	changePanel("register_prop_panel");
	$('#register_proposal_table').datagrid("reload");
})

//分类 确认
$('#register_dept_window_submit').click(function() {
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "register",
			propNo : registerPropNo,
			registerType : $('#register_type').val()
		},
		success : function(data, status) {
			promptMessage(data);
			$('#register_dept_window').window('close');
			changePanel('register_prop_panel');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
})

//分类 取消
$('#register_dept_window_cancel').click(function() {
	$('#register_dept_window').window('close');
})


//部门握手
//处理 取消
$('#cancel_primary_prop').click(function() {
	changePanel('result_primary_prop_panel');
	CKEDITOR.instances.primary_proposal_edit.setData("");
})

//处理 確定
$('#add_primary_prop').click(function() {
	$.messager.confirm('警告', '是否确认提交处理结果,提交之后不能随意修改!', function(r) {
		if (r) {
			$.ajax({
				url : "create.do",
				type : "POST",
				data : {
					type : "result",
					result : CKEDITOR.instances.primary_proposal_edit.getData(),
					propNo:primaryPropNo
				},
				success : function(data, status) {
					promptMessage(data);
					if(data="success_100000"){
						changePanel('result_primary_prop_panel');
						$('.result_primary_prop_panel').datagrid('reload');
						CKEDITOR.instances.primary_proposal_edit.setData("");
					}
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
})


//成员握手
//满意
$('#feedback_proposal_btn_satisfy').click(function() {
	$.messager.confirm('警告', '是否确认反馈处理结果为:满意!', function(r) {
		if (r) {
			feedBakc("满意");
		}
	});
})

//不满意
$('#feedback_proposal_btn_unSatisfy').click(function() {
	$.messager.confirm('警告', '是否确认反馈处理结果为:不满意!', function(r) {
		if (r) {
			feedBakc("不满意");
		}
	});
})


function feedBakc(satisfyState){
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "feedbackSatisfy",
			propSatisf:satisfyState,
			propNo:minePropNo
		},
		success : function(data, status) {
			promptMessage(data);
			changePanel('mine_proposal_panel');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
}

//提出意见 点开
$('#feedback_proposal_btn_feedback').click(function(){
	$('#add_idea_window').window('open');
})

//提出意见 确定
$('#add_idea_window_submit').click(function(){
	var ideaContent = CKEDITOR.instances.add_idea_edit.getData();
	if(ideaContent == ""){
		promptMessage("error_100011");
		return;
	}
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "idea",
			ideaContent:ideaContent,
			propNo:minePropNo
		},
		success : function(data, status) {
			promptMessage(data);
			CKEDITOR.instances.add_idea_edit.setData("");
			changePanel('mine_proposal_panel');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
	
	$('#add_idea_window').window('close');
})

//提出意见 取消
$('#add_idea_window_cancel').click(function(){
	CKEDITOR.instances.add_idea_edit.setData("");
	$('#add_idea_window').window('close');
})

//部门处理时查看意见  点开
$('.view_idea_primary_prop').click(function(){
	$('#view_idea_window').window('open');
})

//查看意见 确定
$('#view_idea_window_submit').click(function(){
	$('#view_idea_window').window('close');
})
