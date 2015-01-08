<#--定义宏-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#--头部资源-->
<#macro header_resources>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="http://img.examw.com/test/pub.css" rel="stylesheet" type="text/css" />
<link href="http://img.examw.com/test/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://img.examw.com/jQuery.js"></script>
</#macro>
<#--topbox-->
<#macro topbox>
<div class="topbox">
	<div class="main">
        <div class="top-l">
            <ul>
                <li><a class="shy" href="http://www.examw.com/">首页</a></li>
                <li><a href="/ksbd/fdc/">宝典</a></li>
                <li><a href="/zl/class/">网校</a></li>
                <li><a href="http://test.examw.com/91/">模考</a></li>
                <li><a href="http://test.examw.com/91/">论坛</a></li>
                <li class="brno"><a href="http://book.examw.com/fdc/">图书</a></li>
            </ul>
        </div>
        <div class="top-r">
        	<div class="log"><a href="#" target="_blank">登陆</a></div>
            <div class="ge fl">|</div>
            <div class="log"><a href="#" target="_blank">注册</a></div>
            <div class="vip fl"><a href="#">个人中心</a></div>
        </div>
    </div>
</div>
<div class="main">
	<div class="logo fl"><a href="http://www.examw.com/"><img src="http://img.examw.com/index/logo.png" width="281" height="92" /></a></div>
    <div class="tel">
        <div class="telnum">4000-525-585</div>
        <div class="teltime">服务时间&nbsp;&nbsp;08：30 - 21：30</div>
    </div>
</div>
<div class="menubox fl bulebg">
	<div class="menu">
        <ul>
            <li class="cur"><a href="http://test.examw.com/91/">首页</a></li>
            <li><a href="#">考试分类</a></li>
            <li><a href="#">最新试卷</a></li>
            <li><a href="#">试卷排行</a></li>
            <li><a href="index-questions-1.html">常见问题</a></li>
        </ul>
        <div class="my"><a href="#" target="_blank"></a></div>
    </div>
</div>
</#macro>

<#--最新试卷，最热试卷，常见问题-->
<#macro news_hots_question>
<div class="newbox fl">
	<div class="main">
        <div class="newshj">
        	<#--最新试卷-->
        	<@news_papers />
        </div>
        <div class="newshj">
        	<#--最热试卷-->
        	<@hots_papers />
        </div>
        <#--常见问题-->
        <@questions_list />
    </div>
</div>
</#macro>
<#--最新试卷-->
<#macro news_papers>
<div class="newtit bulebg fl"><span><a href="index-news-more.html" target="_blank">更多&gt;&gt;</a></span>最新试卷</div><div class="newtitbg"></div>
<#if (newsPapers??)>
<ul class="list-shj" id="list-shj">
	<#list newsPapers as data>
    <li>
    	<p>&middot;<a href="#" title="${data.text}">${data.text}</a></p>
        <p class="btn"><span>${data.total}人参与</span><a href="index-papers-${data.id}">免费测试</a></p>
    </li>
    </#list>
</ul>
</#if>
</#macro>
<#--最热试卷-->
<#macro hots_papers>
<div class="newtit bulebg fl"><span><a href="index-hots-more.html" target="_blank">更多&gt;&gt;</a></span>热门试卷</div><div class="newtitbg"></div>
<#if (hotsPapers??)>
<ul class="list-shj" id="list-shj">
	<#list hotsPapers as data>
    <li>
    	<p>&middot;<a href="#" title="${data.text}">${data.text}</a></p>
        <p class="btn"><span>${data.total}人参与</span><a href="index-papers-${data.id}">免费测试</a></p>
    </li>
    </#list>
</ul>
</#if>
</#macro>
<#--常见问题-->
<#macro questions_list>
<div class="new-question">
	<div class="newtit bulebg fl"><span><a href="index-questions-more.html" target="_blank">更多&gt;&gt;</a></span>常见问题</div><div class="newtitbg"></div>
    <#if (questions??)>
    <ul class="list">
    	<#list questions as data>
        <li>&middot;<a href="index-questions-${data.id}.html" title="${data.text}">${data.text}</a></li>
        </#list>
    </ul>
    </#if>
</div>
</#macro>
<#--友情链接-->
<#macro friend_link>
<div class="link">
	<h4><a href="javascript:void(0)">友情链接</a><span>QQ：712931605</span></h4>
	<div class="box fl">{%=FriendLink(111,"")%}</div>
</div>
</#macro>
<#--footer-->
<#macro footer>
<div class="footer">
	<div class="h1f"></div>
	<div class="nva"><a href="/about/">关于本站</a>┊<a href="/about/map.htm">网站地图</a>┊<a href="/about/copyright.html">网站声明</a>┊<a href="/about/ads.html">广告服务</a>┊<a href="/link/">友情链接</a>┊<a href="/about/job/">诚聘英才</a>┊<a href="/about/contact.html">联系我们</a>┊<a href="http: //bbs.examw.com/forum-4-1.html">意见咨询</a></div>
	<div id="ft_about"></div>
</div>
<script language="javascript" src="http://img.examw.com/e.js"></script>
<script language="javascript" src="http://img.examw.com/test/c.js"></script>
</#macro>