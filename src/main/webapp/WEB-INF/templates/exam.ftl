<#--考试页面-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${examName}-考试页面</title>
		<#--头部资源-->
		<@header_resources/>
	</head>
	<body>
		<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="#">首页</a><i>&gt;</i>${examName}</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="titbox bulebg fl">免费下载试用</div><div class="titboxbg"></div>
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
		                <li class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
		                    <div class="title">
		                        <em>&middot;<a href="/${abbr}/${p.id}.html" target="_blank" title="${p.text}">${p.text}</a></em>
		                        <span>${p.price}元</span>
		                        <span class="red">${p.discount}元</span>
		                        <span>${p.total}题</span>
		                        <i>
		                        	<#--<div class="buy"><a name="index-products-buy" id="${p.id}" Price="${p.price}" GoodPrice="${p.discount}" Title="${p.text}" href="#" target="_blank">我要购买</a></div>-->
		                        	<div class="tiyan"><a href="/${abbr}/${p.id}.html" target="_blank">免费体验</a></div>
		                        </i>
		                    </div>
		                </li>
	                </#list>
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
		<#--footer-->
		<@footer/>
	</body>
</html>