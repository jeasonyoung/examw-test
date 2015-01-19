<#--试卷列表-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${examName}试卷列表</title>
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
			<div class="weizhi fl"><span></span>当前位置：<a href="/">首页</a><i>&gt;</i>${examName}</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="titbox bulebg fl"><span><div class="cheng-btn"><a href="#">选择产品</a></div></span>${examName}</div><div class="titboxbg"></div>
		    <div class="content yinying">
		        <div class="siftbox fl">
		            <div class="f-l fl">科目：</div>
		            <div class="f-r fr">
		               <div class="list">
		                    <ul>
		                        <li <#if (subjectCode??) && (subjectCode?length==0)>class="bg">全部<#else>><a href="/${examAbbr}/list/index.html">全部</a></#if></li>
		                        <#if (subjects??)&&(subjects?size>0)>
		                        <#list subjects as s>
		                        <li <#if (subjectCode??)&&(subjectCode==s.id)>class="bg">${s.text}<#else>><a href="/${examAbbr}/${s.id}/list/index.html">${s.text}</a></#if></li>
		                        </#list>
		                        </#if>
		                    </ul>
		                </div>
		            </div>
		        </div>
		    </div>
		    <div class="h20"></div>
		    <div class="content yinying">
		    	<#if (papers??)&&(papers?size>0)>
		        <div class="shjlist">
		            <ul>
		            	<#list papers as p>
		                <li class="shjout" onMouseOver="this.className='shjover'" onMouseOut="this.className='shjout'">
		                    <div class="title">
		                        <em>
		                            <div class="title"><a href="/${examAbbr}/${p.category}/${p.id}.html" target="_blank" title="${p.text}">${p.text}</a></div>
		                            <p>总题：${p.items?string("###")} 题</p><p>考试时长：${p.times?string("###")}分钟</p><p>总分：${p.total?string("###")}分</p><#--<p>题型：普通模拟题</p>--><p>${p.users?string("###")}人次参考</p>
		                        </em>
		                        <span>${p.createTime?string("yyyy-MM-dd")}</span>
		                        <b>${p.price?string("###")}考试币</b>
		                        <div class="buybtn"><div class="hei-btn"><a href="/${examAbbr}/${p.category}/${p.id}.html">进入做题</a></div></div>
		                    </div>
		                </li>
		                </#list>
		            </ul>
		        </div>
		        <div class="h20"></div>
		        <div style="width:100%;">
		        	<div id="${examAbbr}_paper" class="pager-plugin"></div>
		        	<script type="text/javascript" language="javascript">
		        	<!--
		        	$(function(){
		        		$("#${examAbbr}_paper").pager({ 
		        			pagenumber: ${current}, 
		        			pagecount: ${total}, 
		        			buttonClickCallback:function(index){
		        				if(index == 1){
		        					window.location.href="${path}/index.html";
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