<#--考试页面-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><#if ((title??) && (title?length > 0))>${title} <#else> ${examName}考试题库：${examName}考试真题及答案、模拟试题、章节练习题等考试试题及答案解析 - 中华考试网题库</#if></title>
		<#--头部资源-->
		<#if ((keywords??) && (keywords?length > 0))>
			<meta name="keywords" content="${keywords}" >
		<#else>
			<meta name="keywords" content="${examName}考试题库,${examName}考试真题,${examName}考试模拟试题,${examName}考试答案" >
		</#if>
		<#if ((description??) && (description?length > 0))>
			<meta name="description" content="${description}">
		<#else>
			<meta name="description" content="中华考试网${examName}考试题库为大家提供2015${examName}考试模拟试题、历年真题及答案、章节练习题、网校名师预测试题、考前冲刺试题等考试试题及答案解析，支持网页版、安卓、苹果手机版下载试用。">
		</#if>
		<@header_resources/>
	</head>
	<body>
		<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="http://test.examw.com/">题库首页</a><i>&gt;</i>${examName}</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="titbox bulebg fl">免费试用</div><div class="titboxbg"></div>
		    <div class="content yinying">
		        <div class="porduct-list">
		            <ul>
		                <li class="porduct-top">
		                    <div class="title">
		                        <em class="txt">产品名称</em>
		                        <span>产品价格</span>
		                        <span>优惠价</span>
		                        <span>试题数量</span>
		                        <i>免费体验</i>
		                    </div>
		                </li>
	                <#if products??>
	                <#list products as p>
		                <li>
		                    <div class="title">
		                        <em>&middot;<a href="/${abbr}/${p.id}.html"  title="${p.text}">${p.text}</a></em>
		                        <span>${p.price}元</span>
		                        <span class="red">${p.discount}元</span>
		                        <span>${p.total}题</span>
		                        <i>
		                        	<#--<div class="buy"><a name="index-products-buy" id="${p.id}" Price="${p.price}" GoodPrice="${p.discount}" Title="${p.text}" href="#" >我要购买</a></div>-->
		                        	<div class="tiyan"><a href="/${abbr}/${p.id}.html" target="_blank">免费体验</a></div>
		                        </i>
		                    </div>
		                </li>
	                </#list>
	                <#else>
	                	<li class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">暂无产品信息</li>
	                </#if>
		            </ul>
		        </div>
		    </div>
		    <div class="h30"></div>
		    <@soft_feature />
		</div>
		<div class="h30"></div>
		<div class="h1d"></div>
		<#--最新试卷、最热试卷、常见问题-->
		<@news_hots_question/>
		<div class="h1d"></div>
		<div class="h30"></div>
		<#-- 考试资讯 -->
		<@examw_info />		
		<div class="h30"></div>
		<#--footer-->
		<@footer/>
	</body>
</html>