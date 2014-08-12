<#include "comm.ftl" />
<#assign base="library_item"/>
<#assign module = "${base}_${CURRENT_ITEM_TYPE_VALUE}" />
<#assign form = "${module}_edit_form" />
<#assign dg = "${module}_opts_dg"/>
<#--试题基础信息-->
<#macro item_base>
<div style="float:left;width:100%;display:none;">
	<input name="id" type="hidden" />
	<input name="type" type="hidden" value="${CURRENT_ITEM_TYPE_VALUE}" />
	<input name="typeName" type="hidden" value="${CURRENT_ITEM_TYPE_NAME}" />
	<input name="status" type="hidden" value="${CURRENT_ITEM_STATUS_VALUE}" />
</div>
<#if !CURRENT_ITEM_ISCHILD>
<div style="float:left;margin-top:7px;width:100%;">
	<label style="width:75px;">所属科目：</label>
	<input name="examId" class="easyui-combotree" data-options="
			url:'<@s.url '/settings/category/exams-tree'/>',
			required:true,
			onLoadError:function(e){
					<@error_dialog 'e'/>
			},
			onChange:function(n,o){
				$('#${form}_cbb_subjectId').combobox('clear');
				$('#${form}_cbb_subjectId').combobox('reload','<@s.url '/settings/subject/all'/>?examId=' + n);
			}
		" style="width:368px;"/>
	<input id="${form}_cbb_subjectId" name="subjectId" class="easyui-combobox" data-options="
			<#if ((CURRENT_EXAM_ID?length) > 0)>
		     url:'<@s.url '/settings/subject/all'/>?examId=${CURRENT_EXAM_ID}',
		   </#if>
			required:true,
			valueField:'id',
			textField:'name',
			onLoadError:function(e){
				<@error_dialog 'e'/>
			}
	" style="width:298px;"/>
</div>
<div style="float:left;margin-top:2px;width:100%;">
	<label style="margin-top:5px;width:75px;">所属类型：</label>
	<label style="padding-top:0px;"><input type="radio" name="opt" value="${OPT_REAL_VALUE}" />${OPT_REAL_NAME}</label>
	<label style="padding-top:0px;margin-left:15px;"><input type="radio" name="opt" value="${OPT_SIMU_VALUE}" />${OPT_SIMU_NAME}</label>
	<label style="padding-top:0px;margin-left:15px;"><input type="radio" name="opt" value="${OPT_FORECAST_VALUE}" />${OPT_FORECAST_NAME}</label>
	<label style="padding-top:0px;margin-left:15px;"><input type="radio" name="opt" value="${OPT_PRACTICE_VALUE}" checked="checked" />${OPT_PRACTICE_NAME}</label>
</div>
<div style="float:left;margin-top:7px;width:100%;">
	<div style="float:left;">
		<label style="width:75px;">试题来源：</label>
		<input name="sourceId" class="easyui-combobox" data-options="
				url:'<@s.url '/library/source/all'/>',
				valueField:'id',
				textField:'name',
				onLoadError:function(e){
					<@error_dialog 'e'/>
				}
			" style="width:368px;"/>
	</div>
	<div style="float:left;">
		<label style="width:70px;">难度值：</label>
		<input name="level" type="text" class="easyui-numberbox" data-options="min:0,value:0" style="text-align:right;width:80px;"/>
	</div>
	<div style="float:left;">
		<label style="width:75px;">使用年份：</label>
		<input name="year" type="text" class="easyui-numberbox" data-options="min:0,value:${CURRENT_YEAR}" style="text-align:right;width:75px;"/>
	</div>
</div>
</#if>
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
				return $.trim(value);
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
	function edit_window(title,index,row){
		var d = $("<div/>").dialog({
			title:title,
			width:500,
			height:200,
			href:"<@s.url '/library/item/edit/option/${CURRENT_ITEM_TYPE_VALUE}'/>",
			modal:true,
			buttons:[
			<@shiro.hasPermission name="${PER_UPDATE}">
			{
				text:"保存",
				iconCls:"icon-save",
				handler:function(){
					var data = $("#${module}_edit_opt_form").form("serialize");
					if($.trim(data["opt_content"]) == ""){
						$.messager.alert("警告","请输入选项内容！");
						return;
					}
					var index = dg.datagrid("getRowIndex",data["opt_id"]);
					if(index > -1) {
						dg.datagrid("updateRow",{index:index, row : data});
					}else{
						dg.datagrid("appendRow",data);
					}
					d.dialog("close");
				}
			},
			</@shiro.hasPermission>
			{
				text:"关闭",
				iconCls:"icon-cancel",
				handler:function(){
					d.dialog("close");
				}
			}],
			onClose:function(){
				$(this).dialog("destroy");
			},
			onLoad:function(){
				if(row){
					$("#${module}_edit_opt_form").form("load",row);
					$("#${module}_edit_opt_form textarea").kindeditor("setValue",row.opt_content);
				}
			}
		});
	};
	<@shiro.hasPermission name="${PER_DELETE}">
	${dg}_delete = function(){
		var rows = dg.datagrid("getChecked");
		if(rows && rows.length > 0){
			$.messager.confirm("确认","您是否确认删除选中的数据?",function(r){
				if(!r)return;
				for(var i = 0; i < rows.length; i++){
					var index = dg.datagrid("getRowIndex",rows[i]["opt_id"]);
					if(index > -1){
						dg.datagrid("deleteRow",index);
					}
				}
			});
		}else{
			$.messager.alert("提示","未选中须删除的数据！");
		}
	};
	</@shiro.hasPermission>
	//============================================================================================
	var post = {};
	//validate
	${module}_validate = function(){
		var valid = $("#${form}").form("validate");
		if(!valid) return false;
		post["id"] = $("#${form} input[name='id']").val();
		post["type"] = $("#${form} input[name='type']").val();
		post["typeName"] = $("#${form} input[name='typeName']").val();
		<#if !CURRENT_ITEM_ISCHILD>
		post["status"] = $("#${form} input[name='status']").val();
		post["examId"] = $("#${form} input[name='examId']").val();
		post["subjectId"] = $("#${form} input[name='subjectId']").val();
		post["sourceId"] = $("#${form} input[name='sourceId']").val();
		post["level"] = $("#${form} input[name='level']").val();
		post["year"] = $("#${form} input[name='year']").val();
		post["opt"] = $("#${form} input[name='opt']:checked").val();
		</#if>
		post["content"] = $.trim($("#${form} textarea[name='content']").val());
		if($.trim(post["content"]) == ""){
			$.messager.alert("警告","请输入试题内容！");
			return false;
		}
		var rows = dg.datagrid("getRows");
		if(rows == null || rows.length == 0){
			$.messager.alert("警告","请添加选项！");
			return false;
		}
		$("#${form} input[name='answer']:checked").each(function(i,n){
			if(i == 0){
				post["answer"] = $.trim($(this).val());
			}else{
				post["answer"] += "," + $.trim($(this).val());
			}
		});
		if($.trim(post["answer"]) == ""){
			$.messager.alert("警告","请确定选项中的正确答案！");
			return false;
		}
		post["analysis"] = $.trim($("#${form} textarea[name='analysis']").val());
		if($.trim(post["analysis"]) == ""){
			$.messager.alert("警告","请输入答案解析！");
			return false;
		}
		return valid;
	};
	//save update
	${module}_update = function(){
		post["children"] = [];
		var rows = dg.datagrid("getRows");
		if(rows){
			$.each(rows,function(i,n){
				var opt  = {};
				opt["id"] = n["opt_id"];
				opt["content"] = n["opt_content"];
				opt["orderNo"] = i + 1;
				post["children"].push(opt);
			});
		}
		return post;
	};
	//load
	${module}_load = function(row){
		if(!row)return;
		$("#${form}").form("load",row);
		if(row.content)$("#${form} textarea[name='content']").kindeditor("setValue",row.content);
		if(row.children && $.isArray(row.children)){
			$.each(row.children,function(i,n){
				var opt = {};
				opt["opt_id"] = n["id"];
				opt["opt_content"] = n["content"];
				dg.datagrid("appendRow",opt);
			});
		}
		if(row.answer){
			$.each(row.answer.split(","),function(i,n){
				if($.trim(n) != ""){
					$("#${form} input[name='answer'][value='"+n+"']").attr("checked",true);
				}
			});
		}
		if(row.analysis)$("#${form} textarea[name='analysis']").kindeditor("setValue",row.analysis);
	};
	//============================================================================================
});
//-->
</script>
<form id="${form}" method="POST" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',collapsible:false,height:210,border:false">
		<@item_base />
		<div style="float:left;margin-left:10px;margin-top:5px;">
			<textarea style="float:left" name="content" class="easyui-kindeditor"  data-options="minWidth:762,minHeight:<#if CURRENT_ITEM_ISCHILD>162<#else>82</#if>" rows="5" cols="20" />
		</div>
	</div>
	<div data-options="region:'center',title:'选项',
		<#if CURRENT_OPTS_STATUS>
		tools:[
			<@shiro.hasPermission name='${PER_UPDATE}'>
			{
				iconCls:'icon-add',
				text:'添加选项',
				handler:function(){
					${dg}_add();
				}
			},
			</@shiro.hasPermission>
			<@shiro.hasPermission name='${PER_DELETE}'>
			{
				iconCls:'icon-remove',
				text:'删除选项',
				handler:function(){
					${dg}_delete();
				}
			}
			</@shiro.hasPermission>
			],
		</#if>
		border:false">
		<table id="${dg}"></table>
	</div>
	<div data-options="region:'south',title:'答案解析',collapsible:false,height:150,border:false">
		<div style="float:left;margin-left:10px;margin-top:5px;">
			<textarea style="float:left" name="analysis" class="easyui-kindeditor"  data-options="minWidth:762,minHeight:75" rows="5" cols="20"/>
		</div>
	</div>
</form>
</#macro>