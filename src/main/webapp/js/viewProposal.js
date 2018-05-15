$('#view_proposal_search').click(function(){
	$('#view_proposal_table').datagrid('options').pageNumber=1;
	$('#view_proposal_table').datagrid('options').url = "findProp.do?type=ByCondition&propState="+$('#proposal_state_combobox').val()+"&propType="+$('#proposal_type_combobox').val()+"&beginDate="+$('#view_proposal_date_begin').val()+"&endDate="+$('#view_proposal_date_end').val();
	$("#view_proposal_table").datagrid('reload');
})

$("#view_proposal_reset").click(function(){
	$('#view_proposal_table').datagrid('options').pageNumber=1;
	$('#proposal_state_combobox').combobox("reload");
	$('#proposal_type_combobox').combobox("reload");
	$('#view_proposal_date_begin').textbox('setValue', "");
	$('#view_proposal_date_end').textbox('setValue', "");
});