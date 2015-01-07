<#--常见问题列表-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>常见问题列表</title>
		<#--头部资源-->
		<@header_resources/>
		<#--分页-->
		<link href="http://img.examw.com/test/pager.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="http://img.examw.com/jquery.pager.js"></script>
	</head>
	<body>
		<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="#">首页</a><i>&gt;</i>常见问题</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
		    <div class="newlist-l fl yinying">
		    	<div class="zixun fl"><b>— 资讯中心 —</b><a href="#">考试资讯</a><a href="#">常见问题</a></div>
		    	<div class="tit fl"><b>最新试卷</b></div>
		        <div class="cont fl">
		        	<#if newsPapers??>
		        	<ul class="list-shj" id="list-shj">
				        <#list newsPapers as data>
					    <li>
					    	<p>&middot;<a href="#" title="${data.text}">${data.text}</a></p>
					        <p class="btn"><span>${data.total}人参与</span><a href="index-papers-${data.id}">免费测试</a></p>
					    </li>
					    </#list>
					</ul>
					</#if>
		        </div>
		        <div class="h20"></div>
		        <div class="tit fl"><b>热门试卷</b></div>
		        <div class="cont fl">
		            <#if hotsPapers??>
		        	<ul class="list-shj" id="list-shj">
						<#list hotsPapers as data>
					    <li>
					    	<p>&middot;<a href="#" title="${data.text}">${data.text}</a></p>
					        <p class="btn"><span>${data.total}人参与</span><a href="index-papers-${data.id}">免费测试</a></p>
					    </li>
					    </#list>
					</ul>
					</#if>
		        </div>
		    </div>
		    <div class="newlist-r fr">
		    	<div class="tit fl"><b>常见问题</b></div>
		    	<#if questions??>
		        <div class="cont fl">
		        	<ul class="list">
		            	<#list questions as q>
		                <li><i>${q.createTime?string("yyyy-MM-dd")}</i>&middot;<a href="#" title="${q.text}">${q.text}</a></li>
		            	</#list>
		            </ul>
		        </div>
		        <div class="h20"></div>
		        <div class="page fl">
		             <div id="${prefix}_paper" class="pager-plugin"></div>
		        	<script type="text/javascript" language="javascript">
		        	<!--
		        	$(function(){
		        		$("#${prefix}_paper").pager({pagenumber:${current},pagecount:${total},buttonClickCallback:function(index){ window.location.href="${prefix}-" + index +".html"; } });
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