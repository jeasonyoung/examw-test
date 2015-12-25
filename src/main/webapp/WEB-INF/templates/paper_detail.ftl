<#--试卷详情-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${paperName} - ${examName}${subjectName}题库 - 中华考试网</title>
		<meta name="keywords" Content="${paperName},${examName}考试题库"/>
		<meta name="Description" Content="${paperName}及答案、${examName}考试题库在线测试试卷、提供试题答案及解析 - ${examName}${subjectName}试卷 - 中华考试网题库系统"/>
		<#--头部资源-->
		<@header_resources/>
	</head>

	<body>
		<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="http://test.examw.com/">题库首页</a><i>&gt;</i><a href="/${abbr}/list/">${examName}</a><i>&gt;</i><a href="/${abbr}/${subjectCode}/">${subjectName}</a><i>&gt;</i>${paperName}</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="titbox bulebg fl"><span><div class="cheng-btn"><a href="/${abbr}/">选择产品</a></div></span>${subjectName}</div><div class="titboxbg"></div>
		    <div class="content yinying">
		        <div class="tx fl">
		            <div class="tx-pic"><img rel="nofollow" src="http://img.examw.com/test/tx-pic.png" width="350" height="330"></div>
		            <div class="tx-fr">
		                <div class="title fl">${paperName}</div>
		                <div class="list">
		                    <ul>
		                        <li>考试币：<span>${price}个</span></li>
		                        <li>年份：${year?c}年</li>
		                        <li>类型：${typeName}</li>
		                        <li>总分：${score}分</li>
		                        <li>总题数：${total}题</li>
		                        <li>作答：${time}分钟</li>
		                    </ul>
		                </div>
		                <div class="botton-box">
		                    <div class="botton"><div class="cheng-btn"><a rel="nofollow" href="http://tiku.examw.com/library/paper/do/multi/${paperId}">普通模考</a></div></div>
		                    <div class="botton"><div class="hong-btn"><a rel="nofollow" href="http://tiku.examw.com/library/paper/do/single/${paperId}">全真模考</a></div></div>
		                </div>
		            </div>
		        </div>
		    </div>
		    <div class="h30"></div>
		    <#if products??>
		    <div class="titbox bulebg fl">相关产品</div><div class="titboxbg"></div>
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
		                <#list products as p>
		                <li class="out">
		                    <div class="title">
		                        <em>&middot;<a href="/${abbr}/${p.id}.html"  title="${p.text}">${p.text}</a></em>
		                        <span>${p.price}元</span>
		                        <span class="red">${p.discount}元</span>
		                        <span>${p.total}题</span>
		                        <i>
		                        <#-- <div class="buy"><a name="index-products-buy"  id="${p.id}" Price="${p.price}" GoodPrice="${p.discount}" Title="${p.text}" href="#" >我要购买</a></div> -->
		                        <div class="tiyan"><a href="/${abbr}/${p.id}.html" target="_blank">免费体验</a></div>
		                        </i>
		                    </div>
		                </li>
		                </#list>
		            </ul>
		        </div>
		    </div>
		    </#if>
		    <div class="h20"></div>
		    
		    <#if paper??>
		    <div class="content yinying">
		    	<div class="txbox">
		    		<div class="name">题型介绍</div>
		    		<#if paper.structures??>
		    		<#list paper.structures as structrue>
		    		<#if (structrue_index > 1)>
		    			<#break>
		    		</#if>
		    		<div class="cont">
		    			<div class="title">
		    				<i><div class="title"><b>${structrue.title}</b></div><p>(${structrue.description})</p></i>
		    			</div>
		    			<#if structrue.items??>
		    			<#list structrue.items?sort_by("orderNo") as item>
		    				<#if (item_index > 1)>
		    					<#break>
		    				</#if>
		    				<#if item.content??>
		    				<div class="timu fl">
		    					<h2><#if item.orderNo??>${item.orderNo}.</#if>${item.content}</h2>
		    					<#if item.children ??>
		    					<#list item.children as opt>
		    						<#if opt.content??>
		    					 	<p>${opt.content}</p>
		    					 	</#if>
		    					</#list>
		    					</#if>
		    					<b>【正确答案-参考解析】：参加考试可见</b>
		    				</div>
		    				</#if>
		    			</#list>
		    			</#if>
		    		</div>
		    		</#list>
		    		</#if>
		    	</div>
		    </div>
		    </#if>
		
		<div class="h30"></div>
		<div class="h1d"></div>
		<div class="newbox fl">
			<div class="main">
		        <div class="newshj2">
		        	<#--最新试卷-->
		        	<@news_papers />
		        </div>
		        <div class="newshj2 prno">
		        	 <#--最热试卷-->
		        	<@hots_papers />
		        </div>
		    </div>
		</div>
		<div class="h1d"></div>
		<div class="h30"></div> 
		<#--footer-->
		<@footer/>
	</body>
</html>