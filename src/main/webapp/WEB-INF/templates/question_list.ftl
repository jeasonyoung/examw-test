<#--常见问题列表-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>常见问题列表</title>
		<meta name="keywords" Content=""/>
		<meta name="Description" Content=""/>
		<meta name="UpdateTime" Content="${updateTime?string("yyyy-MM-dd HH:mm:ss")}"/>
		<#--头部资源-->
		<@header_resources/>
		<#--分页-->
		<@header_resources_paging/>
	</head>
	<body>
		<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="/">首页</a><i>&gt;</i>常见问题</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
		    <div class="newlist-l fl yinying">
		    	<div class="zixun fl"><b>— 资讯中心 —</b><a href="#">考试资讯</a><a href="#">常见问题</a></div>
		    	<#--最新试卷-->
		    	<@question_list_news_papers />
		        <div class="h20"></div>
		       <#--最热试卷-->
		       <@question_list_hots_papers />
		    </div>
		    <div class="newlist-r fr">
		    	<div class="tit fl"><b>常见问题</b></div>
		    	<#if (questions??)&&(questions?size>0)>
		        <div class="cont fl">
		        	<ul class="list">
		            	<#list questions as q>
		                <li><i>${q.createTime?string("yyyy-MM-dd")}</i>&middot;<a href="/questions/${q.id}.html" title="${q.text}">${q.text}</a></li>
		            	</#list>
		            </ul>
		        </div>
		        <div class="h20"></div>
		        <div class="page fl">
		             <div id="${prefix}_paper" class="pager-plugin"></div>
		        	<script type="text/javascript" language="javascript">
		        	<!--
		        	$(function(){
		        		$("#${prefix}_paper").pager({pagenumber:${current},pagecount:${total},buttonClickCallback:function(index){
		        		 	var  url = "${path}";
		        		 	if(index == 1){
		        		 		url += "/index.html";
		        		 	}else{
		        		 		url += "/" + index + ".html";
		        		 	}
		        		 	window.location.href = url;
		        		} });
		        	});
		        	//-->
		        	</script>
		        </div>
		        </#if>
		    </div>	
		</div>
		<div class="h30"></div>
		<#--footer-->
		<@footer/>
	</body>
</html>