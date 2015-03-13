<#--产品页面-->
<#include "/inc.ftl" />
<#assign do_product_url = "http://tiku.examw.com/library/${product.id}"/><#--链接到考试前台产品界面-->
<html xmlns="http://www.w3.org/1999/xhtml">
	<head> 
		<title><#if product.title??&&((product.title?trim)!="")>${product.title}<#else>${product.text} - ${product.examName}题库免费试用 - 中华考试网</#if></title>
		<#--头部资源-->
		<#if product.keywords??&&((product.keywords?trim)!="")>
			<meta name="keywords" content="${product.keywords}" >
		<#else>
			<meta name="keywords" content="${product.text},${product.examName}考试题库" >
		</#if>
		<#if product.description??&&((product.description?trim)!="")>
			<meta name="description" content="${product.description}">
		<#else>
			<meta name="description" content="${product.text}、包含${product.examName}考试历年真题及答案、模拟试题、章节练习题、网校名师预测试题、考前冲刺试题等考试试题及答案解析，支持网页版、安卓、苹果手机版下载试用。">
		</#if>
		<@header_resources/>
	</head>

	<body>
	 	<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="http://test.examw.com/">题库首页</a><i>&gt;</i><a href="/${product.examAbbr}/">${product.examName}题库</a><i>&gt;</i>${product.text}</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="content yinying">
		    	<div class="contbox">
		        	<div class="downloadbox">
		            	<div class="pic"><div class="pic-tit">${product.examName}</div></div>
		                <div class="cont fr">
		                	<h2>${product.text}</h2>
		                    <div class="name">${product.content}</div>
		                    <div class="xinxi">
		                    	<div class="jia">促销：<span>￥</span><i>${product.discount?string("###")}</i><br />原价：<em>￥${product.price?string("###")}</em></div>
		                    </div>
		                    <ul class="list">
		                    	<#--
		                    	<li>运行环境：WinXP,Vista,Win7,Win8</li>
		                        <li>软件大小：207MB</li>
		                        -->
		                        <li>解题思路：${product.analysisTypeName}</li>
		                        <li>历年真题：${product.realTypeName}</li>
		                        <li>试题数量：${product.total?string("###")} 题</li>
		                        <li>软件等级：<i> ★ ★ ★ ★ ★</i></li>
		                        <#--
		                        <li>文件类型：.exe</li>
		                        <li>累计下载：58658</li>
		                        -->
		                    </ul>
		                    <div class="tishi">题库服务：由<a href="http://www.examw.com/" >中华考试网</a>提供在线咨询、购买安装指导、售后服务</div>
		                    <div class="btn"><div class="hong-btn"><a rel="nofollow" href="${do_product_url}">立即做题</a></div></div>
		                    <#--
		                    <div class="btn"><div class="lv-btn"><a rel="nofollow" name="index-products-buy"  id="${product.id}" Price="${product.price}" GoodPrice="${product.discount}" Title="${product.text}" href="#">立即购买</a></div></div>
		                    -->
		                </div>
		                <div class="h30"></div>
		                <div class="phonebox fl">
		                	<div class="tit fl">
		                    	<div class="ico"><h2>移动学习平台</h2>移动应用辅助学习</div>
		                    </div>
		                    <div class="ios">
		                    	<div  class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
		                    	<h2>iphone版</h2>
		                        <span>更新时间：2014-12-15</span>
		                        <div class="phone-btnbox"><div class="phonebtn"><a href="#">下 载</a></div></div>
		                        <div class="box">或扫描二维码下载<img src="pic/ewm01.jpg" width="121" height="121" /></div>
		                        </div>
		                    </div>
		                    <div class="ipad">
		                    	<div  class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
		                    	<h2>IOS&nbsp;&nbsp;ipad版</h2>
		                        <span>更新时间：2014-12-15</span>
		                        <div class="phone-btnbox"><div class="phonebtn"><a href="#">下 载</a></div></div>
		                        <div class="box">或扫描二维码下载<img src="pic/ewm01.jpg" width="121" height="121" /></div>
		                        </div>
		                    </div>
		                    <div class="pc">
		                    	<h2>PC版课程播放器</h2>
		                        <span>中华考试网播放器</span>
		                        <div class="phone-btnbox"><div class="phonebtn"><a href="#">下 载</a></div></div>
		                    </div>
		                    <div class="android">
		                    	<div  class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
		                    	<h2>Android版</h2>
		                        <span>更新时间：2014-12-15</span>
		                        <div class="phone-btnbox"><div class="phonebtn"><a href="#">下 载</a></div></div>
		                        <div class="box">或扫描二维码下载<img src="pic/ewm01.jpg" width="121" height="121" /></div>
		                        </div>
		                    </div>
		                    <div class="ipad brno android-ipad">
		                    	<div  class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
		                    	<h2>Android&nbsp;&nbsp;ipad版</h2>
		                        <span>更新时间：2014-12-15</span>
		                        <div class="phone-btnbox"><div class="phonebtn"><a href="#">下 载</a></div></div>
		                        <div class="box">或扫描二维码下载<img src="pic/ewm01.jpg" width="121" height="121" /></div>
		                        </div>
		                    </div>
		                </div>
		            </div>
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