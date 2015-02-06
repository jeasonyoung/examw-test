<#--首页-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>题库首页</title>
		<#--头部资源-->
		<@header_resources/>
	</head>
	<body>
		<#--top banner-->
		<@topbox/>
		<div class="bannerbox fl" style="background:url(http://img.examw.com/test/banner.jpg) center no-repeat"></div>
		<div class="h30"></div>
		<div class="main">
			<#if categories??>
			<div class="titbox bulebg fl">考试分类导航</div><div class="titboxbg"></div>
			<a id="category" name="category"></a>
			<#list categories as category>
				<div class="fenlei fl">
					<div class="fl-l">${category.text}</div>
					<div class="fl-r">
						<#if (category.exams??) && (category.exams?size>0)>
						<ul class="fl-list">
							<#list category.exams as exam>
								<li><a href="/${exam.id}" target="_blank" title="${exam.text}">${exam.text}</a></li>
							</#list>
						</ul>
						</#if>
					</div>
				</div>
			</#list>
			</#if>
		    <div class="h30"></div>
		    <@soft_feature />
		</div>
		<div class="h30"></div>
		<div class="h1d"></div>
		<#--最新试卷、最热试卷、常见问题-->
		<@news_hots_question/>
		<div class="h1d"></div>
		<div class="h30"></div>
		<#--友情链接-->
		<@friend_link/>
		<#--footer-->
		<@footer/>
	</body>
</html>