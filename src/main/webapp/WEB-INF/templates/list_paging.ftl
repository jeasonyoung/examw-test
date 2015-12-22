<#-- 定义分页宏 -->

<#--  列表分页-->
<#macro list_paging current total path>

<div class="pager-plugin">
	<ul class="pages">
		<@list_paging_render_btn title="首页" pagenumber=current pagecount=total path=path/>
		<@list_paging_render_btn title="上一页" pagenumber=current pagecount=total path=path/>
		
		<#assign startPoint = 1>
		<#assign endPoint = 5>
		
		<#if (current > 3)>
			<#assign startPoint = current - 2>
			<#assign endPoint = current + 2>
		</#if>
		
		<#if (endPoint > total)>
			<#assign startPoint = total - 4>
			<#assign endPoint = total>
		</#if>
		
		<#if (startPoint < 1)>
			<#assign startPoint = 1>
		</#if>
		
		<#list startPoint..endPoint as page>
				<#if (page == current)>
					<li class="page-number pgCurrent">${page}</li>
				<#else>
					<a href="${path}<#if page == 1>/<#else>/${page}.html</#if>" target="_self"><li class="page-number">${page}</li></a>
				</#if>
		</#list>
		
		<@list_paging_render_btn title="下一页" pagenumber=current pagecount=total path=path/>
		<@list_paging_render_btn title="尾页" pagenumber=current pagecount=total path=path/>
	</ul>
	<div class="pages">
		<span>
			<b>${current}/${total}</b>
		<span>
		<input id="pagenumInput"/>
	</div>
	<ul class="pages">
		<li class='pgNext' >跳转</li>
	</ul>
</div>
<script type="text/javascript">
<!--
$(function(){
	$('.pages li').mouseover(function(){
		document.body.style.cursor="pointer";
	 }).mouseout(function(){
	 	 document.body.style.cursor="auto";
	 });
	 //跳转
	 $('.pgNext').click(function(){
	 	var num = Number($("#pagenumInput").val());
	 	if(!num){
	 		$("#pagenumInput").val("");
	 		return;
	 	}
	 	if(num<=0)num=1;
	 	if(num>=${total})num= ${total};
	 	if(num == 1){
	 		window.location.href="${path}/";
	 	}else{
	 		window.location.href="${path}/" + num +".html";
	 	}
	 });
});
//-->
</script>
</#macro>

<#-- 列表分页－绘制按钮-->
<#macro list_paging_render_btn title pagenumber pagecount path>
<#assign cls = "">
<#assign destPage = 1>

<#switch title>
	<#case "首页">
		<#assign destPage = 1>
		<#if (pagenumber <= 1)>
			<#assign cls = "pgEmpty">
		</#if>
	<#break>
	<#case "上一页">
		<#assign destPage = pagenumber - 1>
		<#if (pagenumber <= 1)>
			<#assign cls = "pgEmpty">
		</#if>
	<#break>
	<#case "下一页">
		<#assign destPage = pagenumber + 1>
		<#if (pagenumber >= pagecount)>
			<#assign cls = "pgEmpty">
		</#if>
	<#break>
	<#case "尾页">
		<#assign destPage = pagecount>
		<#if (pagenumber >= pagecount)>
			<#assign cls = "pgEmpty">
		</#if>
	<#break>
	<#default>
</#switch>
<#--  输出html -->
<#if (cls?length > 0)>
 	<li class="pgNext ${cls}">${title}</li>
<#else>
		<a href="${path}<#if destPage == 1>/<#else>/${destPage}.html</#if>" target="_self"><li class="pgNext">${title}</li></a>
</#if>
</#macro>