<#--最新试卷-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<#if examName??>
		<title>${examName}最新考试试卷：2015年${examName}考试试题及答案 - 中华考试网题库</title>
		<meta name="keywords" Content="${examName}考试试题,${examName}考试试卷,${examName}考试题库"/>
		<meta name="Description" Content="${examName}最新考试试卷：2015年${examName}考试试题及答案、模拟试题、历年真题、考前预测、冲刺试题及答案等等 - 中华考试网题库"/>
		<#else>
		<title>中华考试网题库最新考试试卷：2015年中华考试网题库试题及答案 - 中华考试网题库</title>
		<meta name="keywords" Content="中华考试网题库试题,中华考试网题库试卷,中华考试网题库"/>
		<meta name="Description" Content="中华考试网题库最新考试试卷：中华考试网题库试题及答案、模拟试题、历年真题、考前预测、冲刺试题及答案等等 - 中华考试网题库"/>
		</#if>
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
			<div class="weizhi fl"><span></span>当前位置：<a href="http://test.examw.com/">题库首页</a><i>&gt;</i><#if examName??>${examName}</#if>最新试卷排行</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="titbox bulebg fl">最新试卷排行</div><div class="titboxbg"></div>
		    <div class="content yinying">
		    	<#if (papers??)&&(papers?size>0)>
		        <div class="shjlist">
		            <ul>
		            	<#list papers as p>
		                <li class="shjout" onMouseOver="this.className='shjover'" onMouseOut="this.className='shjout'">
		                    <div class="title">
		                        <em class="nobg">
		                        	<div class="bgcheng">${p.order}</div>
		                            <div class="title"><a href="${p.category}/${p.id}.html"  title="${p.text}">${p.text}</a></div>
		                            <p>总题：${p.items?string("###")} 题</p><p>考试时长：${p.times?string("###")}分钟</p><p>总分：${p.total?string("###")}分</p><#--<p>题型：普通模拟题</p>--><p>${p.users?string("###")}人次参考</p>
		                        </em>
		                        <span>${p.createTime?string("yyyy-MM-dd")}</span>
		                        <b>${p.price?string("###")}考试币</b>
		                        <div class="buybtn"><div class="hei-btn"><a href="${p.category}/${p.id}.html">进入做题</a></div></div>
		                    </div>
		                </li>
		                </#list>
		            </ul>
		        </div>
		        <div class="h20"></div>
		         <div style="width:100%;">
		        	<div id="${prefix}_paper_paging" class="pager-plugin"></div>
		        	<script type="text/javascript" language="javascript">
		        	<!--
		        	$(function(){
		        		$("#${prefix}_paper_paging").pager({ 
		        			pagenumber: ${current}, 
		        			pagecount: ${total}, 
		        			buttonClickCallback:function(index){
		        				if(index == 1){
		        					window.location.href="${path}/";
		        				}else{
		        					window.location.href="${path}/" + index +".html";
		        				}
		        			}
		        		});
		        	});
		        	//-->
		        	</script>
		        </div>
		         </#if>
		        <div class="h20"></div>
		    </div>
		</div>
		<div class="h30"></div>
		<#--footer-->
		<@footer/>
	</body>
</html>
