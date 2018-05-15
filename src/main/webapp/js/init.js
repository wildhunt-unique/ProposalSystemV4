/*-------------------------------------页面启动函数------------------------------------*/
var reEditPropNo;
$(function() {

	$.ajax({
		url:"getInfo.do",
		type:"GET",
		dataType:"JSON",
		data:{
			type:"loginMemberInfo"
		},
        success : function(data, status) {
			$('#user_name').html(data.memberName);
            $('#user_id').html("&lt;"+data.memberId+"&gt;");
            var menuUrl = data.memberClassId;
            menuUrl = "json/"+menuUrl+".json";
            console.log(menuUrl);
            $("#left_menu_tree_content").tree("options").url = menuUrl;
            $('#left_menu_tree_content').tree('reload');
        },
        error : function(e) {
            alert("请检查网络连接!!");
        }
	})

	reEditPropNo = 'null';
	
	var contentHeihgt = $("#root_right_content").height() - 40;
	var contentWidth = $("#root_right_content").width();

	$('.easyui-datagrid').datagrid('resize', {
		height : contentHeihgt,
		width : contentWidth
	});
	$('#account_manage_deparment_table').datagrid('resize', {
		width : contentWidth - 250
	});

	//$('#deal_prop_content_panel_tabs').css("width",contentWidth+"px");

	//初始显示首页
	$("#home_panel").css("display", "block");

	//功能树绑定事件
	$('#left_menu_tree_content').tree({
		onClick : function(node) {
			//viewTable(node.id);
			$("." + node.id).datagrid("reload");
			changePanel(node.id);

		}
	});

	//点击代表团树事件
	$('#deputation_tree').tree({
		onClick : function(node) {
			//viewTable(node.id);
			var type = "depu";
			var id = node.id;
			$("#account_manage_deparment_table").datagrid('options').pageNumber=1;
			$('#account_manage_deparment_table').datagrid('options').url = "findMem.do?type=" + type + "&id=" + id;
			$("#account_manage_deparment_table").datagrid('reload');
		},
		onContextMenu : function(e, node) { //给结点添加右键菜单
			e.preventDefault();

			$('#depuMenuProp').menu('show', {
				left : e.pageX,
				top : e.pageY
			});

		}
	});

	//点击部门树事件
	$('#deparment_tree').tree({
		onClick : function(node) {
			//viewTable(node.id);
			var type = "dept";
			var id = node.id;
			$("#account_manage_deparment_table").datagrid('options').pageNumber=1;
			$('#account_manage_deparment_table').datagrid('options').url = "findMem.do?type=" + type + "&id=" + id;
			$("#account_manage_deparment_table").datagrid('reload');
		},
		onContextMenu : function(e, node) { //给结点添加右键菜单
			e.preventDefault();
			$('#deptMenuProp').menu('show', {
				left : e.pageX,
				top : e.pageY
			});

		}
	});

	$('#proposal_state_combobox').combobox({
		url : 'json/proposal_state.json',
		valueField : 'id',
		textField : 'text'
	});

	$('#proposal_type_combobox').combobox({
		url : 'json/proposal_type.json',
		valueField : 'id',
		textField : 'text'
	});


	$('#create_proposal_type_combobox').combobox({
		url : 'json/create_proposal_type.json',
		valueField : 'id',
		textField : 'text'
	});

	$('#proposal_deal_state_combobox').combobox({
		url : 'json/proposal_deal_state.json',
		valueField : 'id',
		textField : 'text'
	});

	CKEDITOR.replace('create_proposal_edit', {
		height : (contentHeihgt - 180) + "px"
	});

	CKEDITOR.replace('create_news_edit', {
		height : (contentHeihgt - 180) + "px"
	});

	CKEDITOR.replace('primary_proposal_edit', {
		height : (contentHeihgt - 180) + "px"
	});

	CKEDITOR.replace('add_idea_edit', {
		height : "380px"
	});

	var tableHeight = $('.datagrid-body').height()-25;
	var rows = 1;
	rows = parseInt(tableHeight / 25);
	
	$('.easyui-datagrid').datagrid({
		pageSize:rows,
		pageList:[rows,10,20,30]
	})
	
	//我的提案 查看提案具体信息
	$("#mine_proposal_table").datagrid({
		onClickRow : function(index, row) {
			$("#create_proposal_title_text").val("");
			var propNo = row["propNo"];
			var propState = row["propState"];
			minePropNo = propNo;
			reEditPropNo = propNo;
			$('#view_idea_content').html("");
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("mine_prop_content_panel");
					if (data.propState == "保存" || data.propState == "待审核") { //保存或者审核通过的
						$('.view_proposal_type_select').css('visibility', 'hidden');
						CKEDITOR.instances.create_proposal_edit.setData(data.propContent);
						changePanel("create_proposal_panel");
						$("#create_proposal_title_text").val(data.propTitle);
					} else { //不能修改的情况
						$("#prop_content_div").html(data.propContent);
						//先设置所有的都不显示或者为空
						$('.feedback_proposal_btn').css('visibility', 'hidden');
						$('#view_idea_content').html("");

						if (data.propState == "实施中") { //实施中的情况
							var handShakeState = data.handShake; //先得到 目前的到哪步了
							if (data.propDealType == "立案") {

								if (handShakeState == 0) { //部门还没有回复
									$('#dept_result_context_panel').html("<span>等待部门返回实施结果</span>");
								} else if (handShakeState == 1) { // 部门回复了 但未提出意见
									$('.feedback_proposal_btn').css('visibility', 'visible');
									$('.view_idea_primary_prop').css('visibility', 'hidden'); //除了 查看意见 其他都能看
								} else if (handShakeState == 2) { //成员已经提出过意见
									$('.feedback_proposal_btn').css('visibility', 'visible');
									$('#feedback_proposal_btn_feedback').css('visibility', 'hidden'); //除了 提出意见 都能看到
									$('#dept_result_context_panel').html(data.implResult);
									$('#view_idea_content').html(data.propIdea);
								} else if (handShakeState == 3) { //部门返回了结果
									$('.feedback_proposal_btn').css('visibility', 'visible');
									$('#feedback_proposal_btn_feedback').css('visibility', 'hidden'); //除了 提出意见 都能看到
									$('#dept_result_context_panel').html(data.implResult);
									$('#view_idea_content').html(data.propIdea);
								}

							} else if (data.propDealType == "建议") {

								if (handShakeState == 0) { //部门还没有回复
									$('#dept_result_context_panel').html("<span>等待部门返回实施结果</span>");
								} else if (handShakeState == 1) { // 部门回复了 但未提出意见
									$('.feedback_proposal_btn').css('visibility', 'visible');
									$('.view_idea_primary_prop').css('visibility', 'hidden'); //查看意见 就没了
									$('#feedback_proposal_btn_feedback').css('visibility', 'hidden'); //提出意见 就没了
								}
							}

						} else if (data.propState == "实施完成") { //实施完成的情况
							if (data.propDealType == "立案") {
								$('.view_idea_primary_prop').css('visibility', 'visible'); //除了 查看意见 其他都能看
							}
							$('#dept_result_context_panel').html(data.implResult);
							$('#view_idea_content').html(data.propIdea);
						} else { //通过和部署中
							$("#dept_result_context_panel").html("尚无实施结果");
						}
					}
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//分管提案具体面板
	$('#manage_proposal_table').datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("view_proposal_content_panel");

					$("#view_prop_content_panel").html(data.propContent);
					$('#view_prop_passType').html(data.propDealType);
					$('#view_prop_primary').html(data.primaryDeptName);
					$('#view_prop_help').html(data.heleDeptName);

					$("#view_prop_result_panel").html(data.implResult);
					if ($('#view_prop_result_panel').html() == "") {
						$("#view_prop_result_panel").html("尚无处理结果");
					}

					$('#view_prop_satisfy').html(data.satisfyState);
					if ($('#view_prop_satisfy').html() == "") {
						$('#view_prop_satisfy').html("提出人尚未设置");
					}
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})


	//查看提案具体面板
	$('#view_proposal_table').datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			$('.primary_prop').css('visibility', 'visible');
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("view_proposal_content_panel");

					$("#view_prop_content_panel").html(data.propContent);
					$('#view_prop_passType').html(data.propDealType);
					$('#view_prop_primary').html(data.primaryDeptName);
					$('#view_prop_help').html(data.heleDeptName);

					$("#view_prop_result_panel").html(data.implResult);
					if ($('#view_prop_result_panel').html() == "") {
						$("#view_prop_result_panel").html("尚无处理结果");
					}

					$('#view_prop_satisfy').html(data.satisfyState);
					if ($('#view_prop_satisfy').html() == "") {
						$('#view_prop_satisfy').html("提出人尚未设置");
					}
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//协办提案具体面板
	$('#result_help_proposal_table').datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			$('.primary_prop').css('visibility', 'visible');
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("view_proposal_content_panel");

					$("#view_prop_content_panel").html(data.propContent);
					$('#view_prop_passType').html(data.propDealType);
					$('#view_prop_primary').html(data.primaryDeptName);
					$('#view_prop_help').html(data.heleDeptName);

					$("#view_prop_result_panel").html(data.implResult);
					if ($('#view_prop_result_panel').html() == "") {
						$("#view_prop_result_panel").html("尚无处理结果");
					}

					$('#view_prop_satisfy').html(data.satisfyState);
					if ($('#view_prop_satisfy').html() == "") {
						$('#view_prop_satisfy').html("提出人尚未设置");
					}

				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//点击部署表格事件
	$("#deal_proposal_table").datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			var propState = row["propState"];
			dealPropNo = propNo;
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("deal_prop_content_panel");
					$("#deal_content_div").html(data.propContent);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//附议提案 查看附议提案具体信息
	$("#support_proposal_table").datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			supportPropNo = propNo;
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("support_prop_content_panel");
					$("#support_content_panel").html(data.propContent);
					$("#support_proposal_type").html(data.propType);
					$("#support_proposal_owner").html(data.memberName);
					$("#support_proposal_sup").html(data.memberSup);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//审核提案
	$("#check_proposal_table").datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			checkPropNo = propNo;
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("check_prop_content_panel");
					$("#check_content_panel").html(data.propContent);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//首页查看新闻点击
	$("#view_news_table").datagrid({
		onClickRow : function(index, row) {
			var newsId = row["newsId"];
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "news",
					id : newsId,
				},
				success : function(data, status) {
					changePanel("news_content_panel");
					$("#news_content_edit").html(data.newsContent);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//决议提案点击事件
	$("#register_proposal_table").datagrid({
		onClickRow : function(index, row) {
			var propNo = row["propNo"];
			registerPropNo = propNo;
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					changePanel("register_prop_content_panel");
					$("#register_content_panel").html(data.propContent);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

	//承办提案点击 
	$("#result_primary_proposal_table").datagrid({
		onClickRow : function(index, row) {
			CKEDITOR.instances.primary_proposal_edit.setData(""); //现清空
			$('#state_primary_prop').html("");
			$('#view_idea_content').html("");
			$('.primary_prop').css('visibility', 'hidden');
			var propNo = row["propNo"];
			primaryPropNo = propNo;
			$.ajax({
				url : "getInfo.do",
				type : "POST",
				dataType : "JSON",
				data : {
					type : "prop",
					propNo : propNo,
				},
				success : function(data, status) {
					$("#primary_content_panel").html(data.propContent);
					if (data.propState == "实施中") {
						var handShakeState = data.handShake; //先得到 目前的到哪步了
						if (data.propDealType == "立案") {
							if (handShakeState == 0) { //尚未有处理
								$('.primary_prop').css('visibility', 'visible');
								$('#view_idea_primary_prop').css('visibility', 'hidden');
								$('#state_primary_prop').html("等待部门进行处理");
							} else if (handShakeState == 1) {
								CKEDITOR.instances.primary_proposal_edit.setData(data.implResult); //处理增加内容
								$('#state_primary_prop').html("等待提案提出人进行回复");
							} else if (handShakeState == 2) {
								$('.primary_prop').css('visibility', 'visible');
								$('#view_idea_primary_prop').css('visibility', 'visible');
								$('#view_idea_content').html(data.propIdea);
								$('#state_primary_prop').html("提案提出人提出意见,等待部门进行回复");
								CKEDITOR.instances.primary_proposal_edit.setData(data.implResult); //处理增加内容
							} else if (handShakeState == 3) {
								$('#view_idea_primary_prop').css('visibility', 'visible');
								CKEDITOR.instances.primary_proposal_edit.setData(data.implResult); //处理增加内容
								$('#view_idea_content').html(data.propIdea);
								$('#state_primary_prop').html("部门修改处理结果,等待提案提出人进行回复");
							}
						} else if (data.propDealType == "建议") {
							if (handShakeState == 0) {
								$('.primary_prop').css('visibility', 'visible');
								$('#view_idea_primary_prop').css('visibility', 'hidden');
								$('#state_primary_prop').html("等待部门进行处理");
							} else if (handShakeState == 1) {
								CKEDITOR.instances.primary_proposal_edit.setData(data.implResult); //处理增加内容
								$('#state_primary_prop').html("等待提案提出人进行回复");
							}
						}

					} else if (data.propState == "实施完成") {
						$('#state_primary_prop').html("实施完成,处理结果为:" + data.satisfyState);
						if (data.propDealType == "立案") {
							$('#view_idea_primary_prop').css('visibility', 'visible');
							$('#view_idea_content').html(data.propIdea);
						}
						CKEDITOR.instances.primary_proposal_edit.setData(data.implResult); //处理增加内容
					}
					changePanel("primary_prop_content_panel");
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	})

});

/*-------------------------------------END 页面启动函数------------------------------------*/