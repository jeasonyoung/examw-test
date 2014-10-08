<#include "comm.ftl" />

<#--试题基础信息-->
<#macro item_base>

</#macro>


<#--选择题模版-->
<#macro item_choice answer_view>
<script type="text/javascript">
<!--
$(function(){
	var dg = $("#${dg}").datagrid({
		fit:true,
		fitColumns:true,
		rownumbers:true,
		border:true,
		striped:true,
		idField:"opt_id",
		columns:[[{
			field:"opt_id",
			checkbox:true
		},{
			title:"选项内容",
			field:"opt_content",
			width:100,
			align:"left",
			formatter: function(value,row,index){
				return value.replace(/<[^>]*>/g,"");
			}
		},{
			title:"正确答案",
			field:"opt",
			width:10,
			align:"center",
			formatter: function(value,row,index){
				return "<input type='${answer_view}' name='answer' value='"+row["opt_id"] +"'/>";
			}
		}]],
		onLoadError:function(e){
			<@error_dialog "e"/>
		},
		onDblClickRow:function(rowIndex,rowData){
			edit_window("编辑选项",rowIndex,rowData);
		}
	});
	<@shiro.hasPermission name="${PER_UPDATE}">
	${dg}_add = function(){
		edit_window("新增选项",0,null);
	};
	</@shiro.hasPermission>
	//edit_window
	
	
	//============================================================================================
	var post = {};
	//validate
	${module}_validate = function(){
		
		
		post["typeName"] = $("#${form} input[name='typeName']").val();
		<#if !CURRENT_ITEM_ISCHILD>
		post["status"] = $("#${form} input[name='status']").val();
		post["examId"] = $("#${form} input[name='examId']").val();
		post["subjectId"] = $("#${form} input[name='subjectId']").val();
		post["sourceId"] = $("#${form} input[name='sourceId']").val();
		post["year"] = $("#${form} input[name='year']").val();
		post["opt"] = $("#${form} input[name='opt']:checked").val();
		</#if>
		<#if CURRENT_IS_STRUCTURE>
		<#if !CURRENT_ITEM_ISCHILD>
		post["structureId"] = $("#${form} input[name='structureId']").val();
		post["orderNo"] = $("#${form} input[name='orderNo']").val();
		</#if>
		</#if>
		
	};
	//save update
	${module}_update = function(){
		
	};
	//load
	${module}_load = function(row){
		
		
		
		
	};
	//============================================================================================
});
//-->
</script>
<form id="${form}" method="POST" class="easyui-layout" data-options="fit:true,border:false">

</form>
</#macro>