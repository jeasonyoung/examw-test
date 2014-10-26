<#--试题通用freemarker macro -->
<#--试题通用基本信息-->
<#macro item_base_share>
<div style="float:left;margin-top:5px;width:100%;">
	<label style="width:75px;">所属科目：</label>
	<input name="examId" class="easyui-combotree" data-options="url:'<@s.url '/settings/category/exams/tree'/>',required:true,lines:true,onLoadError:function(e){<@error_dialog 'e'/>},
			onChange:function(n,o){
				$('#${form}_subjectId').combobox('clear');
				$('#${form}_subjectId').combobox('reload','<@s.url '/settings/subject/all'/>?examId=' + n);
				$('#${form}_areaId').combobox('clear');
			}" style="width:368px;"/>
	<input id="${form}_subjectId" name="subjectId" class="easyui-combobox" data-options="url:'<@s.url '/settings/subject/all'/>?examId=${current_exam_id}',required:true,
			valueField:'id',textField:'name',onLoadError:function(e){<@error_dialog 'e'/>},
			onChange:function(n,o){
				$('#${form}_areaId').combobox('clear');
				$('#${form}_areaId').combobox('reload','<@s.url '/settings/subject/areas'/>?subjectId=' + n);
			}" style="width:298px;"/>
</div>
<div style="float:left;margin-top:2px;width:100%;">
	<label style="margin-top:5px;width:75px;">所属类型：</label>
	<#list PaperTypeMaps?keys as key>
		<label style="padding-top:0px;<#if (key_index > 0)>margin-left:15px;</#if>">
			<input type="radio" name="opt" value="${key}" <#if (key_index == 0)> checked="checked" </#if> />${PaperTypeMaps[key]}
		</label>
	</#list>
</div>
<div style="float:left;margin-top:2px;width:100%;">
	<div style="float:left;">
		<label style="width:75px;">试题来源：</label>
		<input name="sourceId" class="easyui-combobox" data-options="url:'<@s.url '/library/source/all'/>',valueField:'id',textField:'name',onLoadError:function(e){ <@error_dialog 'e'/> },
				icons:[{iconCls:'icon-clear',handler:function(e){ $(e.data.target).combobox('clear'); } }]" style="width:198px;" />
	</div>
	<div style="float:left;">
		<label style="width:75px;">所属地区：</label>
		<input id="${form}_cbb_areaId" name="areaId" class="easyui-combobox" data-options="url:'<@s.url '/settings/subject/areas'/>?subjectId=${current_subject_id}',
			valueField:'id',textField:'name',onLoadError:function(e){<@error_dialog 'e'/> },
			icons:[{iconCls:'icon-clear',handler:function(e){$(e.data.target).combobox('clear');}}]" style="width:198px"/>
	</div>
	<div style="float:left;">
		<label style="width:70px;">使用年份：</label>
		<input name="year" type="text" class="easyui-numberbox" data-options="min:0,value:${current_year}" style="width:70px;"/>
	</div>
</div>
</#macro>
<#--选择题选项-->
<#macro item_edit_opts_dg>
<table id="${dg}" class="easyui-datagrid" data-options="fit:true,border:true,striped:true,fitColumns:true,rownumbers:true,idField:'opt_id',
	onLoadError:function(e){<@error_dialog 'e'/>},onDblClickRow:function(rowIndex,rowData){${module}_edit_opts_window('编辑选项',rowIndex,rowData);}">
	<thead>
		<tr>
			<th data-options="field:'opt_id',checkbox:true"></th>
			<th data-options="field:'opt_content', width:100,align:'left',formatter:function(value,row,index){return value.replace(/<[^>]*>/g,'');}">选项内容</th>
			<th data-options="field:'opt',width:10,align:'center',formatter:function(value,row,index){ return '<input type=\'radio\' name=\'answer\' value='+row['opt_id'] +'/>';}">正确答案</th>
		</tr>
	</thead>
</table>
</#macro>
<#--选择题选项编辑-->
<#macro item_edit_opts_edit_window>
${module}_edit_opts_window = function(title,index,row){
	var d = $("<div/>").dialog({
		title:title,width:600,height:200,modal:true,
		href:"<@s.url '/library/item/edit/option/${current_item_type_value}'/>",
		buttons:[
		<@shiro.hasPermission name="${PER_UPDATE}">
		{
			text:"保存",iconCls:"icon-save",
			handler:function(){
				var data = $("#${module}_edit_opt_form").form("serialize");
				if($.trim(data["opt_content"]) == ""){
					$.messager.alert("警告","请输入选项内容！");
					return;
				}
				var index = $("#${dg}").datagrid("getRowIndex",data["opt_id"]);
				if(index > -1) {
					$("#${dg}").datagrid("updateRow",{index:index, row : data});
				}else{
					$("#${dg}").datagrid("appendRow",data);
				}
				d.dialog("close");
			}
		},
		</@shiro.hasPermission>
		{
			text:"关闭",iconCls:"icon-cancel",
			handler:function(){ d.dialog("close");}
		}],
		onClose:function(){$(this).dialog("destroy");},
		onLoad:function(){if(row){$("#${module}_edit_opt_form").form("load",row);}}
	});
};
</#macro>
<#--选项删除-->
<#macro item_edit_opts_delete>
<@shiro.hasPermission name="${PER_DELETE}">
${dg}_delete = function(){
	var rows = $("#${dg}").datagrid("getChecked");
	if(rows && rows.length > 0){
		$.messager.confirm("确认","您是否确认删除选中的数据?",function(r){
			if(!r)return;
			for(var i = 0; i < rows.length; i++){
				var index = $("#${dg}").datagrid("getRowIndex",rows[i]["opt_id"]);
				if(index > -1){
					$("#${dg}").datagrid("deleteRow",index);
				}
			}
		});
	}else{
		$.messager.alert("提示","未选中须删除的数据！");
	}
};
</@shiro.hasPermission>
</#macro>
