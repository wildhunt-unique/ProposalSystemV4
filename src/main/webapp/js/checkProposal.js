$("#check_proposal_pass_btn").click(function() {
	$.messager.confirm('警告', '是否确定通过该提案?', function(r) {
		if (r) {
			$.ajax({
				url : "create.do",
				type : "GET",
				data : {
					type:"check",
					propNo : checkPropNo,
					propState : "通过"
				},
				success : function(data, status) {
					promptMessage(data);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
	//checkSubmit("通过");
});

$("#check_proposal_notPass_btn").click(function() {
	$.messager.confirm('警告', '是否确定驳回该提案?', function(r) {
		if (r) {
			$.ajax({
				url : "create.do",
				type : "GET",
				data : {
					type:"check",
					propNo : checkPropNo,
					propState : "驳回"
				},
				success : function(data, status) {
					promptMessage(data);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
	
	//checkSubmit("驳回");
});

function checkSubmit(propState){
	$.messager.confirm('警告', '是否确定'+propState+'该提案?', function(r) {
		if (r) {
			$.ajax({
				url : "create.do",
				type : "GET",
				data : {
					type:"check",
					propNo : checkPropNo,
					propState : propState
				},
				success : function(data, status) {
					promptMessage(data);
				},
				error : function(e) {
					alert("请检查网络连接！！");
				}
			});
		}
	});
}