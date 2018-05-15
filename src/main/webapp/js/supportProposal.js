$("#support_proposal_submit_btn").click(function() {
	$.messager.confirm('警告', '是否确定附议该提案?', function(r) {
		if (r) {
			$.ajax({
				url : "create.do",
				type : "GET",
				data : {
					type:"support",
					propNo : supportPropNo
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
});

