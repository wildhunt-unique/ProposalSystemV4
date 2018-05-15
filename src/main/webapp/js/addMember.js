$('#new_account_window_cancel').click(function() {
	$("#add_mem_name").textbox('setValue', '');
	$("#add_mem_no").textbox('setValue', '');
	$("#add_mem_pass").textbox('setValue', '');
	$("#add_mem_rePass").textbox('setValue', '');
	$('#new_account_window').window('close');
})

$.extend($.fn.validatebox.defaults.rules, {
	isNumber : {
		validator : function(value, param) {
			return /^-?\d+\.?\d*$/.test(value);
		},
		message : '请输入正确的数字!'
	},

	rePassWord : {
		validator : function(value, param) {
			return value == $("#add_mem_pass").val();
		},
		message : '两次密码不一致!'
	},


	mPassWord : {
		validator : function(value, param) {
			return value == $("#modify_mem_pass").val();
		},
		message : '两次密码不一致!'
	},
	sameNo : {
		validator : function(value, param) {
			var flag = "";

			$.ajax({
				url : "getInfo.do",
				type : "POST",
				async : false,
				data : {
					type : "isSameNo",
					memberNo : value
				},
				success : function(data, status) {
					flag = data;
				},
				error : function(e) {
					alert("请检查网络连接");
				}
			});
			return !flag.indexOf("true");
		},
		message : '该工号已存在!'
	}
});

//新加账号
$("#new_account_window_submit").click(function() {
	if ($("#add_mem_dept").val() == "AllDepartment" || $("#add_mem_depu").val() == "AllDeputation") {
		promptMessage("error_100006");
		return;
	}

	if (checkAddMemberSubmit() != "true") {
		promptMessage("error_100007");
		return;
	} else {
		$.ajax({
			url : "add.do",
			type : "POST",
			data : {
				type : "mem",
				memberName : $("#add_mem_name").val(),
				memberNo : $("#add_mem_no").val(),
				memberPass : $("#add_mem_pass").val(),
				deptId : $("#add_mem_dept").val(),
				depuId : $("#add_mem_depu").val()
			},
			success : function(data, status) {
				promptMessage(data);
				$('#account_manage_deparment_table').datagrid('reload');

				$("#add_mem_name").textbox('setValue', '');
				$("#add_mem_no").textbox('setValue', '');
				$("#add_mem_pass").textbox('setValue', '');
				$("#add_mem_rePass").textbox('setValue', '');
				$('#new_account_window').window('close');
			},
			error : function(e) {
				alert("请检查网络连接！！");
			}
		});
	}
})



function checkAddMemberSubmit() {
	var flag = true;
	if (!$('#add_mem_name').textbox("isValid")) {
		flag = false;
	}
	;
	if (!$('#add_mem_no').textbox("isValid")) {
		flag = false;
	}
	;
	if (!$('#add_mem_pass').textbox("isValid")) {
		flag = false;
	}
	;
	if (!$('#add_mem_rePass').textbox("isValid")) {
		flag = false;
	}
	;
	if (!$('#add_mem_dept').textbox("isValid")) {
		flag = false;
	}
	;

	if (flag) {
		return "true";
	} else {
		return "false";
	}
}



//账户管理的删除管理
$(".account_manage_btn_mem_delete").click(function() {
	var ids = [];
	var memberNos = "";
	var rows = $('#account_manage_deparment_table').datagrid('getSelections');
	if (rows.length == 0) {
		promptMessage("error_100008");
		return false;
	}

	$.messager.confirm('警告', '是否确认删除选中的成员!', function(r) {
		if (r) {
			for (var i = 0; i < rows.length; i++) {
				memberNos = memberNos + rows[i].memberNo + ",";
			}
			$.ajax({
				url : "del.do",
				type : "POST",
				data : {
					type : "mem",
					memberNos : memberNos
				},
				success : function(data, status) {
					promptMessage(data);
					$('#account_manage_deparment_table').datagrid('reload');
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
});

//点开编辑按钮
$("#account_manage_btn_edit").click(function() {
	var rows = $('#account_manage_deparment_table').datagrid('getSelections');
	if (rows.length == 0) {
		promptMessage("error_100009");
		return false;
	}
	var memberNo = rows[0].memberNo;

	$.ajax({
		url : "getInfo.do",
		type : "POST",
		dataType : "JSON",
		data : {
			type : "mem",
			memberNo : memberNo
		},
		success : function(data, status) {
			$('#modify_mem_name').textbox('setValue', data.memberName);
			$('#modify_mem_no').textbox('setValue', data.memberNo);

			$('#modify_mem_pass').textbox('setValue', '');
			$('#modify_mem_rePass').textbox('setValue', '');

			$('#modify_mem_dept').combotree('setValue', data.deptId);
			$('#modify_mem_depu').combotree('setValue', data.depuId);
			$('#modify_mem_class').combotree('setValue', data.memberClassId);

			$('#modify_pass_btn').attr("isPass", "false");
			$('#modify_pass_btn').linkbutton({
				text : '修改密码'
			});
			$('.modify_mem_pass_tr').css("visibility", "hidden");

			$('#modify_account_window').window('open');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
});

//修改密码按钮
$("#modify_pass_btn").click(function() {
	var isPass = $('#modify_pass_btn').attr("isPass");
	if (isPass == "false") { //点击修改密码
		$('#modify_pass_btn').attr("isPass", "true");
		$('#modify_pass_btn').linkbutton({
			text : '取消'
		});
		$('.modify_mem_pass_tr').css("visibility", "visible");
	} else { //点击取消
		$('#modify_pass_btn').attr("isPass", "false");
		$('#modify_pass_btn').linkbutton({
			text : '修改密码'
		});
		$('.modify_mem_pass_tr').css("visibility", "hidden");
	}
});



//確認修改按鈕
$("#modify_account_window_submit").click(function() {
	var modifyPassword;
	if (checkModifyMemberSubmit() == "false") {
		promptMessage("error_100007");
		return;
	} else {
		var passState = $('#modify_pass_btn').attr("isPass");

		if (passState == "false") { //不改密码
			modifyPassword = "";
		} else {
			modifyPassword = $("#modify_mem_pass").val();
		}

		console.log(modifyPassword);

		$.messager.confirm('警告', '是否保存当前修改!', function(r) {
			if (r) {
				$.ajax({
					url : "modify.do",
					type : "POST",
					data : {
						type : "mem",
						memberName : $("#modify_mem_name").val(),
						memberNo : $("#modify_mem_no").val(),
						memberPass : modifyPassword,
						deptId : $("#modify_mem_dept").val(),
						depuId : $("#modify_mem_depu").val(),
						memberClass : $("#modify_mem_class").val()
					},
					success : function(data, status) {
						promptMessage(data);
						$('#account_manage_deparment_table').datagrid('reload');
						$('#modify_account_window').window('close');
					},
					error : function(e) {
						alert("请检查网络连接！！");
					}
				});
			}
		});
	}
});

function checkModifyMemberSubmit() {
	var flag = true;
	if (!$('#modify_mem_name').textbox("isValid")) {
		flag = false;
	}

	if ($('#modify_pass_btn').attr("isPass") == "true") {
		if (!$('#modify_mem_pass').textbox("isValid")) {
			flag = false;
		}
		if (!$('#modify_mem_rePass').textbox("isValid")) {
			flag = false;
		}
	}

	if (!$('#modify_mem_dept').textbox("isValid")) {
		flag = false;
	}

	if (flag) {
		return "true";
	} else {
		return "false";
	}
}


//取消修改按鈕
$("#modify_account_window_cancel").click(function() {
	$('#modify_account_window').window('close');
});


//添加部门 点开
$(".account_manage_btn_dept_add").click(function() {
	$("#add_dept_name").textbox('setValue', '');
	$('#new_dept_window').window('open');
});

//添加部门取消

$("#new_dept_window_cancel").click(function() {
	$("#add_dept_name").textbox('setValue', '');
	$('#new_dept_window').window('close');
});


//添加部门 确认
$("#new_dept_window_submit").click(function() {
	if (!$('#add_dept_name').textbox("isValid")) {
		promptMessage("error_100007");
		return;
	}
	$.ajax({
		url : "add.do",
		type : "POST",
		data : {
			type : "dept",
			name : $('#add_dept_name').val()
		},
		success : function(data, status) {
			promptMessage(data);
			$('.easyui-tree').tree('reload');
			$('.easyui-combotree').combotree('reload');
			$('.easyui-combobox').combobox('reload');
			$("#add_dept_name").textbox('setValue', '');
			$('#new_dept_window').window('close');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
});

//删除部门
$(".account_manage_btn_dept_dele").click(function() {
	var id = $("#deparment_tree").tree('getSelected').id;
	var text = $("#deparment_tree").tree('getSelected').text;
	$.messager.confirm('警告', '是否删除 ' + text, function(r) {
		if (r) {
			$.ajax({
				url : "del.do",
				type : "POST",
				data : {
					type : "dept",
					id : id
				},
				success : function(data, status) {
					promptMessage(data);
					$('.easyui-tree').tree('reload');
					$('.easyui-combotree').combotree('reload');
					$('.easyui-combobox').combobox('reload');
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
});

//修改部门 点开
$(".account_manage_btn_dept_edit").click(function() {
	modifyDeptId = $("#deparment_tree").tree('getSelected').id;
	$.ajax({
		url : "getInfo.do",
		type : "POST",
		dataType : "JSON",
		data : {
			type : "dept",
			id : modifyDeptId
		},
		success : function(data, status) {
			var manageId = data.deptMaster;
			$('#modify_dept_window').window('open');
			$('#modify_dept_name').textbox('setValue', data.deptName);
			$('#modify_dept_manage').combotree('reload', "TreeNode.do?treeType=memByDept&id=" + modifyDeptId);
			$('#modify_dept_manage').combotree('setValue', manageId);
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
});

//修改部门 确认
$("#modify_dept_window_submit").click(function() {
	if (!$('#modify_dept_name').textbox("isValid")) {
		promptMessage("error_100007");
		return;
	}

	$.ajax({
		url : "modify.do",
		type : "POST",
		data : {
			type : "dept",
			deptName : $('#modify_dept_name').val(),
			deptId : modifyDeptId,
			deptMaster : $('#modify_dept_manage').val()
		},
		success : function(data, status) {
			promptMessage(data);
			$('#modify_dept_window').window('close');
			$('.easyui-tree').tree('reload');
			$('.easyui-combotree').combotree('reload');
			$('#account_manage_deparment_table').datagrid('reload');
			$('.easyui-combobox').combobox('reload');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
});

//修改部门 取消
$("#modify_dept_window_cancel").click(function() {
	$('#modify_dept_window').window('close');
})

//添加代表团 点开
$(".account_manage_btn_depu_add").click(function() {
	$("#add_depu_name").textbox('setValue', '');
	$('#new_depu_window').window('open');
});

//添加代表团 取消

$("#new_depu_window_cancel").click(function() {
	$("#add_depu_name").textbox('setValue', '');
	$('#new_depu_window').window('close');
});


//添加代表团 确认
$("#new_depu_window_submit").click(function() {
	if (!$('#add_depu_name').textbox("isValid")) {
		promptMessage("error_100007");
		return;
	}
	$.ajax({
		url : "add.do",
		type : "POST",
		data : {
			type : "depu",
			name : $('#add_depu_name').val()
		},
		success : function(data, status) {
			promptMessage(data);
			$('#new_depu_window').window('close');
			$('.easyui-tree').tree('reload');
			$('.easyui-combotree').combotree('reload');
			$('.easyui-combobox').combobox('reload');
			$("#add_depu_name").textbox('setValue', '');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
});

//删除代表团
$(".account_manage_btn_depu_dele").click(function() {
	var id = $("#deputation_tree").tree('getSelected').id;
	var text = $("#deputation_tree").tree('getSelected').text;
	$.messager.confirm('警告', '是否删除 ' + text, function(r) {
		if (r) {
			$.ajax({
				url : "del.do",
				type : "POST",
				data : {
					type : "depu",
					id : id
				},
				success : function(data, status) {
					promptMessage(data);
					$('.easyui-tree').tree('reload');
					$('.easyui-combotree').combotree('reload');
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
});

//修改代表团 点开
$(".account_manage_btn_depu_edit").click(function() {
	modifyDepuId = $("#deputation_tree").tree('getSelected').id;
	$.ajax({
		url : "getInfo.do",
		type : "POST",
		dataType : "JSON",
		data : {
			type : "depu",
			id : modifyDepuId
		},
		success : function(data, status) {
			var manageId = data.deputManger;
			$('#modify_depu_window').window('open');
			$('#modify_depu_name').textbox('setValue', data.depuName);
			$('#modify_depu_manage').combotree('reload', "TreeNode.do?treeType=memByDepu&id=" + modifyDepuId);
			$('#modify_depu_manage').combotree('setValue', manageId);
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
});

//修改代表团 确认
$("#modify_depu_window_submit").click(function() {
	if (!$('#modify_depu_name').textbox("isValid")) {
		promptMessage("error_100007");
		return;
	}

	$.ajax({
		url : "modify.do",
		type : "POST",
		data : {
			type : "depu",
			depuName : $('#modify_depu_name').val(),
			mamageId : $('#modify_depu_manage').val(),
			depuId : modifyDepuId
		},
		success : function(data, status) {
			promptMessage(data);
			$('#modify_depu_window').window('close');
			$('.easyui-tree').tree('reload');
			$('.easyui-combotree').combotree('reload');
			$('#account_manage_deparment_table').datagrid('reload');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});

});

//修改代表团 取消
$("#modify_depu_window_cancel")


//按姓名查找人
$("#search_user").bind('keypress', function(event) {
	if (event.keyCode == 13) {
		$("#account_manage_deparment_table").datagrid('options').pageNumber=1;
		$('#account_manage_deparment_table').datagrid('options').url = "findMem.do?type=ByName&id=" + $("#search_user").val();
		$("#account_manage_deparment_table").datagrid('reload');
	}
});


//分管校长
$("#account_manage_btn_add_dept").click(function() {
	var rows = $('#account_manage_deparment_table').datagrid('getSelections');
	if (rows.length == 0) {
		promptMessage("error_100009");
		return false;
	}
	var memberNo = rows[0].memberNo;
	var memberName = rows[0].memberName;
	$('#manage_dept_memName').textbox('setValue', memberName);
	$('#manage_dept_memId').textbox('setValue', memberNo);
	$('#manage_dept_window').window('open');
})

$("#manage_dept_window_cancel").click(function() {
	$('#manage_dept_window').window('close');
})

$("#manage_dept_window_submit").click(function() {
	if($('#manage_dept_name').val()==""){
		promptMessage("error_100009");
		return false;
	}
	$.ajax({
		url : "create.do",
		type : "POST",
		data : {
			type : "manage",
			manageNo : $('#manage_dept_memId').val(),
			deptId : $('#manage_dept_name').val(),
		},
		success : function(data, status) {
			promptMessage(data);
			$('#manage_dept_window').window('close');
		},
		error : function(e) {
			alert("请检查网络连接！！");
		}
	});
})


