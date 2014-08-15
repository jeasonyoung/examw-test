<#--试题试图-->
<!--css begin -->
<style>
<!--
.container  label,.container  span
{
	float:left;
	display:block;
}
.container  label
{
	text-align:right;
	margin-top:3px;
}
.container  span
{
	text-align:left;
	margin-top:6px;
	border-bottom:solid 1px #ccc;
}
.container dl,.container dt,.container dd
{
	float:left;
	margin-top:1px;
	width:95%;
	//border:solid 1px #ccc;
}
.container dt
{
	list-style:none;
	margin:0;
}
.container dd
{
	margin-top:5px;
}
.container fieldset
{
	float:left;
	width:99%;
	border:solid 1px #ccc;
}
.container pre
{
	float:left;
	margin-left:15px;
}
h2,h3 {
  margin-top: 0;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-weight: normal;
  color: #333;
}
hr {
	color:#f5f5f5;
}
-->
</style>
<#--题目信息头-->
<#macro item_header item>
<div style="float:left;margin-top:12px;width:100%;">
	<div style="float:left;">
		<label style="width:75px;">所属考试：</label>
		<span style="width:168px;">${item.examName}<br/></span>
	</div>
	<div style="float:left;">
		<label style="width:75px;">所属科目：</label>
		<span style="width:168px;">${item.subjectName}<br/></span>
	</div>
	<div style="float:left;">
		<label style="width:75px;">所属题型：</label>
		<span style="width:168px;">${item.typeName}<br/></span>
	</div>
</div>
<div style="float:left;margin-top:12px;width:100%;">
	<div style="float:left;">
		<label style="width:75px;">使用年份：</label>
		<span style="width:50px;text-align:center;">${item.year?string("0000")}<br/></span>
	</div>
	<div style="float:left;">
		<label style="width:68px;">难度值：</label>
		<span style="width:50px;text-align:center;">${item.level}<br/></span>
	</div>
	<div style="float:left;">
		<label style="width:75px;">所属类型：</label>
		<span style="width:168px;">${item.optName}<br/></span>
	</div>
	<div style="float:left;">
		<label style="width:75px;">试题来源：</label>
		<span style="width:168px;">${item.sourceName}<br/></span>
	</div>
</div>
<div style="float:left;width:99%;">
	<hr style="width:96%;color:#ccc"/>
</div>
</#macro>
<#--题目信息体-->
<#macro item_body item>
<div style="float:left;width:100%;">
	<dl style="float:left;">
		<dt style="margin-left:10px;">
			<pre><#if item.serial??>${item.serial}</#if>${item.content}<#if ((item.score??)&&(item.score > 0))>(${item.score} 分)</#if></pre>
		</dt>
		<#nested>
	</dl>
</div>
<div style="float:left;margin-top:12px;width:98%;">
	<fieldset>
		<legend>答案解析</legend>
		<pre>${item.analysis}</pre>
	</fieldset>
</div>
</#macro>
<#--1.试题（单选题）-->
<#macro single item>
	<@item_body item>
		<#if item.children ??>
			<dt>
				<#list item.children as i>
					<dd>
					<label><input type="radio" disabled="disabled" <#if (item.answer == i.id)>checked="checked"</#if>/>${i.content}</label>
					</dd>
				</#list>
			</dt>
		</#if>
	</@item_body>
</#macro>
<#--2.试题（多选题）-->
<#macro multy item>
	<@item_body item>
		<#if item.children ??>
			<dt>
				<#list item.children as i>
					<dd>
					<label><input type="checkbox" disabled="disabled" <#list item.answer?split(",") as a>  <#if (a == i.id)>checked="checked"</#if> </#list>/>${i.content}</label>
					</dd>
				</#list>
			</dt>
		</#if>
	</@item_body>
</#macro>
<#--3.试题（不定向选）-->
<#macro uncertain item>
	<@multy item/>
</#macro>
<#--4.试题（判断题）-->
<#macro judge item>
	<@item_body item>
		<dt>
			<dd>
				<label><input type="radio" disabled="disabled" <#if (item.answer == ItemJudgeAnswer_Right_Value )>checked="checked"</#if>/>${ItemJudgeAnswer_Right_Name}</label>
			</dd>
			<dd>
				<label><input type="radio" disabled="disabled" <#if (item.answer == ItemJudgeAnswer_Wrong_Value )>checked="checked"</#if>/>${ItemJudgeAnswer_Wrong_Name}</label>
			</dd>
		</dt>
	</@item_body>
</#macro>
<#--5.试题（问答题）-->
<#macro qanda item>
<div style="float:left;width:100%;">
	<pre>${item.content}</pre>
</div>
<div style="float:left;margin-top:12px;width:98%;">
	<fieldset>
		<legend>正确答案</legend>
		<pre>${item.answer}</pre>
	</fieldset>
</div>
<div style="float:left;margin-top:12px;width:98%;">
	<fieldset>
		<legend>答案解析</legend>
		<pre>${item.analysis}</pre>
	</fieldset>
</div>
</#macro>
<#--6.试题（共享题干题）-->
<#macro share_title item>
<div style="float:left;width:100%;">
	<pre>${item.content}</pre>
</div>
<div style="float:left;margin-top:12px;width:98%;">
<fieldset>
<legend>子题集合</legend>
<#list item.children as c>
	<#switch c.type>
		<#case 1><#--单选-->
			<@single c/>
		<#break>
		<#case 2><#--多选-->
			<@multy c/>
		<#break>
		<#case 3><#--不定向选-->
			<@uncertain c/>
		<#break>
		<#case 4><#--判断题-->
			<@judge c/>
		<#break>
		<#case 5><#--问答题-->
			<@qanda c/>
		<#break>
	</#switch>
</#list>
</fieldset>
</div>
</#macro>
<#--7.试题（共享答案题）-->
<#macro share_answer item>
<div style="float:left;width:100%;">
	<pre>${item.content}</pre>
</div>
<#if item.children??>
<#assign len = (item.children?size) - 1 />
<div style="float:left;width:99%;">
	<dt>
		<#list item.children as c>
			<#if (c_index < len)>
			<dd>
				<label>${c.content}</label>
			</dd>
			</#if>
		</#list>
	</dt>
</div>
<div style="float:left;margin-top:12px;width:98%;">
<fieldset>
<legend>子题集合</legend>
<#assign child = item.children[len] />
<#if ((child??) && (child.children??))>
	<#list child.children as c>
	<div style="float:left;width:100%;">
		<pre>${c.content}</pre>
	</div>
	<div style="float:left;margin-top:12px;width:98%;">
		<fieldset>
			<legend>正确答案</legend>
			<#list item.children as p>
			<#if p.id == c.answer>
				<pre>${p.content}</pre>
				<#break>
			</#if>
			</#list>
		</fieldset>
	</div>
	<div style="float:left;margin-top:12px;width:98%;">
		<fieldset>
			<legend>答案解析</legend>
			<pre>${c.analysis}</pre>
		</fieldset>
	</div>
	</#list>
</#if>
</fieldset>
</div>
</#if>
</#macro>