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
			<#list categories as category>
				<div class="fenlei fl">
					<div class="fl-l">${category.text}</div>
					<div class="fl-r">
						<#if (category.exams??) && (category.exams?size>0)>
						<ul class="fl-list">
							<#list category.exams as exam>
								<li><a href="index-exams-${exam.id}.html" target="_blank" title="${exam.text}">${exam.text}</a></li>
							</#list>
						</ul>
						</#if>
					</div>
				</div>
			</#list>
			</#if>
		    <div class="h30"></div>
		    <div class="titbox bulebg fl">软件特色</div><div class="titboxbg"></div>
		    <div class="tesebox fl">
		        <div class="tese btno">
		        	<div class="pic fl"><img src="http://img.examw.com/test/01.jpg" width="530" height="299" /></div>
		            <div class="cont fr">
		            	<div class="one"></div>
		                <i>全真模考</i>
		                <span>依托行业最先进智能算法，配合行业最全面考题，试卷严格按照历年考试出题情况、知识点分布情况及今年命题方向分析进行编制，模拟真实考试环境，让考试不再陌生，让你学习考试更简单！</span>
		                <em><div class="hong-btn"><a href="#">立即做题</a></div></em>
		            </div>
		        </div>
		        <div class="tese">
		        	<div class="pic fr"><img src="http://img.examw.com/test/02.jpg" width="530" height="299" /></div>
		            <div class="cont fl">
		            	<div class="two"></div>
		                <i>大数据保过</i>
		                <span>根据海量用户做题的智能化分析判断数据，分析错题，易错知识点, 历年真题出题机率，精准预测考点．考题．命题，全维度划定史上最小考试范围，无限接近考试，做的就是最接近考试的试题。</span>
		                <em><div class="hong-btn"><a href="#">立即做题</a></div></em>
		            </div>
		        </div>
		        <div class="tese">
		        	<div class="pic fl"><img src="http://img.examw.com/test/03.jpg" width="530" height="299" /></div>
		            <div class="cont fr">
		            	<div class="three"></div>
		            	<i>自由组卷</i>
		                <span>自由组卷，可选题型、难度、错误率，等多种方式。从基础学习阶段，到冲刺复习阶段，再到考前检测阶段，您都能在题库内找到相应的练习，帮助您迅速提高成绩。</span>
		                <em><div class="hong-btn"><a href="#">立即做题</a></div></em>
		            </div>
		        </div>
		        <div class="tese">
		        	<div class="pic fr"><img src="http://img.examw.com/test/04.jpg" width="530" height="299" /></div>
		            <div class="cont fl">
		            	<div class="four"></div>
		            	<i>错题收藏</i>
		                <span>交卷之后可系统自动批改，错题自动记录并且都有详细解析， 便于复习薄弱考点。</span>
		                <em><div class="hong-btn"><a href="#">立即做题</a></div></em>
		            </div>
		        </div>
		        <div class="tese">
		        	<div class="pic fl"><img src="http://img.examw.com/test/05.jpg" width="530" height="299" /></div>
		            <div class="cont fr">
		            	<div class="five"></div>
		            	<i>专业答疑</i>
		                <span>学贵知疑，学习疑难问老师，有疑问随时提交，专业答疑老师解决个性化学习难题。</span>
		                <em><div class="hong-btn"><a href="#">立即做题</a></div></em>
		            </div>
		        </div>
		        <div class="tese bbno">
		        	<div class="pic fr"><img src="http://img.examw.com/test/06.jpg" width="530" height="299" /></div>
		            <div class="cont fl">
		            	<div class="six"></div>
		            	<i>终身使用</i>
		                <span>一次购买，终身免费升级，并支持电脑，手机等多平台使用。考试一点通下载安装并注册成功之后，您也可以离线使用它了，充分利用您的碎片化时间进行学习。</span>
		                <em><div class="hong-btn"><a href="#">立即做题</a></div></em>
		            </div>
		        </div>
		    </div>
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