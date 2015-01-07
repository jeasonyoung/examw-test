<#-- 常见问题详细 -->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${question.text}</title> 
		<#--头部资源-->
		<@header_resources/>
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
		        <div class="cont fl">
		        	<div class="new-name fl">
		            	<h1>${question.text}</h1>
		                <div class="xinxi fl"><em>${question.createTime?string("yyyy-MM-dd")}</em><#--<em>责编：examw011</em>--></div>
		            </div>
		            <div class="new-content fl"> 
		            	${question.content}
		            </div>
		        </div>
		    </div>	
		</div>
		<div class="h30"></div>
		 <#--footer-->
		<@footer/>
	</body>
</html>