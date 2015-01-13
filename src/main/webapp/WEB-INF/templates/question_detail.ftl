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
		    	<#--最新试卷-->
		    	<@question_detail_news_papers />
		        <div class="h20"></div>
		        <#--最热试卷-->
		        <@question_detail_hots_papers />
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