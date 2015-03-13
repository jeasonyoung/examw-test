<#--定义宏-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#--头部资源-->
<#macro header_resources>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#if updateTime??>
<meta name="UpdateTime" content="${updateTime?datetime}">
</#if>
<base target="_blank" />
<link href="http://img.examw.com/test/pub.css" rel="stylesheet" type="text/css" />
<link href="http://img.examw.com/test/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="gb2312" src="http://img.examw.com/jQuery.js"></script>
</#macro>
<#--头部资源－分页脚本-->
<#macro header_resources_paging>
<link href="http://img.examw.com/test/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="gb2312" src="http://img.examw.com/test/jquery.pager.js"></script>
</#macro>
<#--topbox-->
<#macro topbox>
<div class="topbox">
	<div class="main">
        <div class="top-l">
            <ul>
                <li><a class="shy" href="http://www.examw.com/">首页</a></li>
                <li><a href="http://www.examw.com/ksbd/">宝典</a></li>
                <li><a href="http://class.examw.com/">网校</a></li>
                <li><a href="http://test.examw.com/">题库</a></li>
                <li><a href="http://book.examw.com/">图书</a></li>
                <li class="brno"><a href="http://bbs.examw.com/">论坛</a></li>
            </ul>
        </div>
        <div class="top-r" id="LoginA">
        	<div class="log"><a rel="nofollow" href="http://test.examw.com/class/?DoMain=test" >登陆</a></div>
            <div class="ge fl">|</div>
            <div class="log"><a rel="nofollow" href="http://test.examw.com/class/?DoMain=test" >注册</a></div>
        </div>
    </div>
</div>
<div class="main">
	<div class="logo fl"><a href="http://www.examw.com/"><img rel="nofollow" src="http://img.examw.com/index/logo.png" width="281" height="92" /></a></div>
    <div class="tel">
        <div class="telnum">4000-525-585</div>
        <div class="teltime">服务时间&nbsp;&nbsp;08：30 - 21：30</div>
    </div>
</div>
<div class="menubox fl bulebg">
	<div class="menu">
        <ul>
            <li class="cur"><a href="http://test.examw.com/">题库首页</a></li>
            <li><a href="/#category">考试分类</a></li>
            <li><a href="/news/">最新试卷</a></li>
            <li><a href="/hots/">试卷排行</a></li>
            <li><a href="/questions/list/">常见问题</a></li>
        </ul>
        <#-- 暂时默认的是[一建工程经济产品] -->
        <div class="my"><a id="mytiku" rel="nofollow" href="http://tiku.examw.com/" ></a></div>
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
<#--试卷列表显示-->
<#macro papers_list_views papers>
<#if (papers??)&&(papers?size>0)>
<ul class="list-shj" id="list-shj">
	<#list papers as data>
    <li>
    	<p>&middot;<a href="${data.category}/${data.id}.html" title="${data.text}"><@text_length_limit data.text/></a></p>
        <p class="btn"><span>${data.total}人参与</span><a href="${data.category}/${data.id}.html">免费测试</a></p>
    </li>
    </#list>
</ul>
</#if>
</#macro>
<#--试卷名称字符串截断 -->
<#macro text_length_limit text><#if (text?length >25)>${text?substring(0,26)}...<#else>${text}</#if></#macro>
<#--最新试卷-->
<#macro news_papers>
<div class="newtit bulebg fl"><span><a <#if abbr??>href="/news/${abbr}/"<#else>href="/news/"</#if> >更多&gt;&gt;</a></span>最新试卷</div><div class="newtitbg"></div>
<@papers_list_views newsPapers/>
</#macro>
<#--常见问题列表－最新试卷 -->
<#macro question_list_news_papers>
<div class="tit fl"><b>最新试卷</b></div>
<div class="cont fl">
	<@papers_list_views newsPapers/>
</div>
</#macro>
<#--常见问题明细－最新试卷 -->
<#macro question_detail_news_papers>
<div class="tit fl"><b>最新试卷</b></div>
<div class="cont fl">
	<@papers_list_views newsPapers/>
</div>
</#macro>
<#--最热试卷-->
<#macro hots_papers>
<div class="newtit bulebg fl"><span><a <#if abbr??>href="/hots/${abbr}/"<#else>href="/hots/"</#if> >更多&gt;&gt;</a></span>热门试卷</div><div class="newtitbg"></div>
<@papers_list_views hotsPapers/>
</#macro>
<#--常见问题列表－最热试卷-->
<#macro question_list_hots_papers>
<div class="tit fl"><b>热门试卷</b></div>
<div class="cont fl">
    <@papers_list_views hotsPapers/>
</div>
</#macro>
<#--常见问题详细－最热试卷-->
<#macro question_detail_hots_papers>
<div class="tit fl"><b>热门试卷</b></div>
<div class="cont fl">
	<@papers_list_views hotsPapers/>
</div>
</#macro>
<#--常见问题-->
<#macro questions_list>
<div class="new-question">
	<div class="newtit bulebg fl"><span><a href="/questions/list/" >更多&gt;&gt;</a></span>常见问题</div><div class="newtitbg"></div>
    <#if (questions??)>
    <ul class="list">
    	<#list questions as data>
        <li>&middot;<a href="/questions/${data.id}.html" title="${data.text}"><@text_length_limit data.text/></a></li>
        </#list>
    </ul>
    </#if>
</div>
</#macro>
<#--友情链接-->
<#macro friend_link>
<div class="link">
	<h4><a href="javascript:;">友情链接</a><span>QQ：712931605</span></h4>
	<div class="box fl">$FriendLink$</div>
</div>
</#macro>
<#-- 考试资讯 -->
<#macro examw_info>
<div class="NavClass">
  <div class="Navbit">
    <ul class="clearfix">
      <li class="Navtit">- 考试资讯 -</li>
      <li class="dqcur" id="eclass1" ><a href="http://www.examw.com/gongcheng/">建筑工程</a></li>
      <li id="eclass2" ><a href="http://www.examw.com/acc/">会计考试</a></li>
      <li id="eclass3" ><a href="http://www.examw.com/zige/">职业资格</a></li>
      <li id="eclass4" ><a href="http://www.examw.com/yiyao/">医药考试</a></li>
      <li id="eclass5" ><a href="http://www.examw.com/waiyu/">外语考试</a></li>
      <li id="eclass6" ><a href="http://www.examw.com/waimao/">外贸考试</a></li>
      <li id="eclass7" ><a href="http://www.examw.com/xueli/">学历考试</a></li>
      <li id="eclass8" ><a href="http://www.examw.com/it/">计算机类</a></li>
    </ul>
  </div>
  <div class="Navbox">
    <div class="Showbox" id="hd_eclass1">
      <ul class="listbox">
        <li><a href="http://www.examw.com/jzs1/" id="hot">一级建造师</a></li>
        <li><a href="http://www.examw.com/zaojia/">造价工程师</a></li>
        <li><a href="http://www.examw.com/jianli/" id="hot">监理工程师</a></li>
        <li><a href="http://www.examw.com/zixun/" id="hot">咨询工程师</a></li>
        <li><a href="http://www.examw.com/fdc/" id="hot">房地产估价师 </a></li>
        <li><a href="http://www.examw.com/tddj/">土地登记代理</a></li>
        <li><a href="http://www.examw.com/gujia/">土地估价师</a></li>
        <li><a href="http://www.examw.com/zl/">质量工程师</a></li>
        <li><a href="http://www.examw.com/city/">城市规划师</a></li>
        <li><a href="http://www.examw.com/jiegou/">结构工程师</a></li>
        <li><a href="http://www.examw.com/wuye/" id="hot">物业管理师</a></li>
        <li><a href="http://www.examw.com/jzs2/" id="hot">二级建造师</a></li>
        <li><a href="http://www.examw.com/jianzhu/">注册建筑师</a></li>
        <li><a href="http://www.examw.com/aq/" id="hot">安全工程师</a></li>
        <li><a href="http://www.examw.com/pj/">安全评价师</a></li>
        <li><a href="http://www.examw.com/zjy/">造价员考试</a></li>
        <li><a href="http://www.examw.com/yt/">岩土工程师</a></li>
        <li><a href="http://www.examw.com/shebei/">设备监理师</a></li>
        <li><a href="http://www.examw.com/hj/">环境影响评价</a></li>
        <li><a href="http://www.examw.com/tz/">投资项目管理</a></li>
        <li><a href="http://www.examw.com/zhaobiao/" id="hot">招标师考试</a></li>
        <li><a href="http://www.examw.com/glzj/" id="hot">公路造价师</a></li>
        <li><a href="http://www.examw.com/gljl/" id="hot">公路监理师</a></li>
        <li><a href="http://www.examw.com/hggc/" id="hot">化工工程师</a></li>
        <li><a href="http://www.examw.com/ntgc/" id="hot">暖通工程师</a></li>
        <li><a href="http://www.examw.com/jps/" id="hot">给排水工程师</a></li>
        <li><a href="http://www.examw.com/jl/" id="hot">计量工程师</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass2">
      <ul class="listbox">
        <li><a href="http://www.examw.com/cy/">会计从业资格</a></li>
        <li><a href="http://www.examw.com/chuji/">初级会计职称</a></li>
        <li><a href="http://www.examw.com/zhongji/">中级会计职称</a></li>
        <li><a href="http://www.examw.com/cpa/">注册会计师</a></li>
        <li><a href="http://www.examw.com/gaoji/">高级会计师</a></li>
        <li><a href="http://www.examw.com/zq/">证券资格考试</a></li>
        <li><a href="http://www.examw.com/cia/">国际内审师</a></li>
        <li><a href="http://www.examw.com/ccbp/">银行从业资格</a></li>
        <li><a href="http://www.examw.com/jjs/">经济师</a></li>
        <li><a href="http://www.examw.com/tjs/">统计师</a></li>
        <li><a href="http://www.examw.com/cta/">注册税务师</a></li>
        <li><a href="http://www.examw.com/pinggu/">资产评估师</a></li>
        <li><a href="http://www.examw.com/sjs/">审计师</a></li>
        <li><a href="http://www.examw.com/acca/">ACCA/CAT</a></li>
        <li><a href="http://www.examw.com/price/">价格鉴证师</a></li>
        <li><a href="http://www.examw.com/tjcy/">统计资格从业</a></li>
        <li><a href="http://www.examw.com/jjcy/">基金从业资格</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass3">
      <ul class="listbox">
        <li><a href="http://www.examw.com/hr/">人力资源考试</a></li>
        <li><a href="http://www.examw.com/sf/">司法考试</a></li>
        <li><a href="http://www.examw.com/flgw/">企业法律顾问</a></li>
        <li><a href="http://www.examw.com/Teacher/">教师资格考试</a></li>
        <li><a href="http://www.examw.com/jsj/">职称计算机</a></li>
        <li><a href="http://www.examw.com/dy/">导游考试</a></li>
        <li><a href="http://www.examw.com/works/">社会工作者</a></li>
        <li><a href="http://www.examw.com/glzx/">管理咨询师</a></li>
        <li><a href="http://www.examw.com/ms/">秘书考试</a></li>
        <li><a href="http://gwy.examw.com/">公务员</a></li>
        <li><a href="http://www.examw.com/gx/">公选考试</a></li>
        <li><a href="http://www.examw.com/zj/">招警考试</a></li>
        <li><a href="http://www.examw.com/xds/">选调生</a></li>
        <li><a href="http://www.examw.com/sydw/">事业单位</a></li>
        <li><a href="http://www.examw.com/yys/">营养师</a></li>
        <li><a href="http://www.examw.com/cunguan/">村官</a></li>
        <li><a href="http://www.examw.com/xinli/">心理咨询师</a></li>
        <li><a href="http://www.examw.com/chuban/">出版专业资格</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass4">
      <ul class="listbox">
        <li><a href="http://www.examw.com/yishi/">执业医师</a></li>
        <li><a href="http://www.examw.com/linchuang/">临床执业医师</a></li>
        <li><a href="http://www.examw.com/yishi/cad/">临床助理医师</a></li>
        <li><a href="http://www.examw.com/yishi/zhongyi/">中医执业医师</a></li>
        <li><a href="http://www.examw.com/yishi/cda/">中医助理医师</a></li>
        <li><a href="http://www.examw.com/yishi/zxys/">中西医医师</a></li>
        <li><a href="http://www.examw.com/yishi/zxyzlys/">中西医助理</a></li>
        <li><a href="http://www.examw.com/yishi/Oral/">口腔执业医师</a></li>
        <li><a href="http://www.examw.com/yishi/Health/">公共卫生医师</a></li>
        <li><a href="http://www.examw.com/yishi/pha/">公卫助理医师</a></li>
        <li><a href="http://www.examw.com/yishi/shijianjineng/">实践技能</a></li>
        <li><a href="http://www.examw.com/yaoshi/">执业药师</a></li>
        <li><a href="http://wszg.examw.com/">卫生资格考试</a></li>
        <li><a href="http://wszg.examw.com/neike/">内科主治医师</a></li>
        <li><a href="http://wszg.examw.com/waike/">外科主治医师</a></li>
        <li><a href="http://wszg.examw.com/fuchanke/">妇产科医师</a></li>
        <li><a href="http://wszg.examw.com/wsyaoshi/">西药士/师</a></li>
        <li><a href="http://wszg.examw.com/wszyaoshi/">中药士/师</a></li>
        <li><a href="http://wszg.examw.com/wsjishi/">临床检验技师</a></li>
        <li><a href="http://www.examw.com/yishi/lichuanglilun/">临床医学理论</a></li>
        <li><a href="http://www.examw.com/zhongyililun/">中医理论</a></li>
        <li><a href="http://www.examw.com/yishi/oad/">口腔助理医师</a></li>
        <li><a href="http://www.examw.com/hushi/">执业护士</a></li>
        <li><a href="http://www.examw.com/hushi/hushi2/">初级护师</a></li>
        <li><a href="http://www.examw.com/hushi/hushi3/">主管护师</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass5">
      <ul class="listbox">
        <li><a href="http://www.examw.com/toefl/">托福考试</a></li>
        <li><a href="http://www.examw.com/ielts/">雅思考试</a></li>
        <li><a href="http://www.examw.com/cet4/">CET4</a></li>
        <li><a href="http://www.examw.com/cet6/">CET6</a></li>
        <li><a href="http://www.examw.com/bec/">商务英语</a></li>
        <li><a href="http://www.examw.com/pets/">公共英语</a></li>
        <li><a href="http://www.examw.com/zcyy/">职称英语</a></li>
        <li><a href="http://www.examw.com/zcjp/">职称日语</a></li>
        <li><a href="http://www.examw.com/fect/">金融英语</a></li>
        <li><a href="http://www.examw.com/tem/">专四专八</a></li>
        <li><a href="http://www.examw.com/GRE/">GRE考试</a></li>
        <li><a href="http://www.examw.com/toeic/">托业TOEIC</a></li>
        <li><a href="http://www.examw.com/catti/">翻译考试</a></li>
        <li><a href="http://www.examw.com/jp/">日语考试</a></li>
        <li><a href="http://www.examw.com/kr/">韩语考试</a></li>
        <li><a href="http://www.examw.com/fr/">法语考试</a></li>
        <li><a href="http://www.examw.com/de/">德语考试</a></li>
        <li><a href="http://www.examw.com/ru/">俄语考试</a></li>
        <li><a href="http://www.examw.com/sp/">西班牙语</a></li>
        <li><a href="http://www.examw.com/pt/">葡萄牙语</a></li>
        <li><a href="http://www.examw.com/italian/">意大利语</a></li>
        <li><a href="http://www.examw.com/syyy/">实用英语</a></li>
        <li><a href="http://www.examw.com/yw/">经典译文</a></li>
        <li><a href="http://www.examw.com/life/">生活英语</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass6">
      <ul class="listbox">
        <li><a href="http://www.examw.com/bgy/">报关员</a></li>
        <li><a href="http://www.examw.com/wxy/">外销员</a></li>
        <li><a href="http://www.examw.com/baojian/">报检员</a></li>
        <li><a href="http://www.examw.com/huoyun/">货运代理</a></li>
        <li><a href="http://www.examw.com/sws/">国际商务师</a></li>
        <li><a href="http://www.examw.com/wuliu/">物流师</a></li>
        <li><a href="http://www.examw.com/dz/">单证员</a></li>
        <li><a href="http://www.examw.com/gz/">跟单员</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass7">
      <ul class="listbox">
        <li><a href="http://www.examw.com/zikao/">自学考试</a></li>
        <li><a href="http://www.examw.com/kaoyan/">考研</a></li>
        <li><a href="http://www.examw.com/gaokao/">高考</a></li>
        <li><a href="http://www.examw.com/zhongkao/">中考</a></li>
        <li><a href="http://www.examw.com/chengkao/">成人高考</a></li>
        <li><a href="http://www.examw.com/MBA/">MBA考试</a></li>
      </ul>
    </div>
    <div class="Hidebox" id="hd_eclass8">
      <ul class="listbox">
        <li><a href="http://www.examw.com/ncre/">计算机等级</a></li>
        <li><a href="http://www.examw.com/NCRE/one/">计算机一级</a></li>
        <li><a href="http://www.examw.com/NCRE/2/">计算机二级</a></li>
        <li><a href="http://www.examw.com/ncre/three/">计算机三级</a></li>
        <li><a href="http://www.examw.com/ncre/four/">计算机四级</a></li>
        <li><a href="http://www.examw.com/soft/">软件水平</a></li>
        <li><a href="http://www.examw.com/microsoft/">微软认证</a></li>
        <li><a href="http://www.examw.com/cisco/">思科认证</a></li>
        <li><a href="http://www.examw.com/oracle/">Oracle</a></li>
        <li><a href="http://www.examw.com/linux/">Linux认证</a></li>
        <li><a href="http://www.examw.com/java/">JAVA认证</a></li>
        <li><a href="http://www.examw.com/jsj/">职称计算机</a></li>
        <li><a href="http://www.examw.com/os/">操作系统</a></li>
        <li><a href="http://www.examw.com/biancheng/">编程开发</a></li>
        <li><a href="http://www.examw.com/oa/">办公软件</a></li>
        <li><a href="http://www.examw.com/sheji/">设计制作</a></li>
      </ul>
    </div>
  </div>
</div>
</#macro>
<#-- 软件特色 -->
<#macro soft_feature>
<div class="titbox bulebg fl">软件特色</div>
<div class="titboxbg"></div>
<div class="tesebox fl">
	<div class="tese btno">
		<div class="pic fl">
			<img rel="nofollow" src="http://img.examw.com/test/01.jpg" width="530" height="299" />
		</div>
		<div class="cont fr">
			<div class="one"></div>
			<i>全真模考</i> <span>依托行业最先进智能算法，配合行业最全面考题，试卷严格按照历年考试出题情况、知识点分布情况及今年命题方向分析进行编制，模拟真实考试环境，让考试不再陌生，让你学习考试更简单！</span>
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com/">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fr">
			<img rel="nofollow" src="http://img.examw.com/test/02.jpg" width="530" height="299" />
		</div>
		<div class="cont fl">
			<div class="two"></div>
			<i>智能云数据</i> <span>根据海量用户做题的智能化分析判断数据，分析错题，易错知识点,
				历年真题出题机率，精准预测考点．考题．命题，全维度划定史上最小考试范围，无限接近考试，做的就是最接近考试的试题。</span> 
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com/">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fl">
			<img rel="nofollow" src="http://img.examw.com/test/03.jpg" width="530" height="299" />
		</div>
		<div class="cont fr">
			<div class="three"></div>
			<i>自由组卷</i> <span>自由组卷，可选题型、难度、错误率，等多种方式。从基础学习阶段，到冲刺复习阶段，再到考前检测阶段，您都能在题库内找到相应的练习，帮助您迅速提高成绩。</span>
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com/">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fr">
			<img rel="nofollow" src="http://img.examw.com/test/04.jpg" width="530" height="299" />
		</div>
		<div class="cont fl">
			<div class="four"></div>
			<i>错题收藏</i> <span>交卷之后可系统自动批改，错题自动记录并且都有详细解析， 便于复习薄弱考点。</span> 
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com/">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese">
		<div class="pic fl">
			<img rel="nofollow" src="http://img.examw.com/test/05.jpg" width="530" height="299" />
		</div>
		<div class="cont fr">
			<div class="five"></div>
			<i>专业答疑</i> <span>学贵知疑，学习疑难问老师，有疑问随时提交，专业答疑老师解决个性化学习难题。</span> 
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com/">立即做题</a></div></em>-->
		</div>
	</div>
	<div class="tese bbno">
		<div class="pic fr">
			<img rel="nofollow" src="http://img.examw.com/test/06.jpg" width="530" height="299" />
		</div>
		<div class="cont fl">
			<div class="six"></div>
			<i>终身使用</i> <span>一次购买，终身免费升级，并支持电脑，手机等多平台使用。考试一点通下载安装并注册成功之后，您也可以离线使用它了，充分利用您的碎片化时间进行学习。</span>
			<#--<em><div class="hong-btn"><a href="http://tiku.examw.com/">立即做题</a></div></em>-->
		</div>
	</div>
</div>
</#macro>
<#--footer-->
<#macro footer>
<div class="footer">
	<div class="h1f"></div>
	<div class="nva"><a rel="nofollow" href="http://www.examw.com/about/">关于本站</a>┊<a href="http://www.examw.com/about/map.htm">网站地图</a>┊<a rel="nofollow" href="http://www.examw.com/about/copyright.html">网站声明</a>┊<a rel="nofollow" href="http://www.examw.com/about/ads.html">广告服务</a>┊<a href="http://www.examw.com/link/">友情链接</a>┊<a rel="nofollow" href="http://www.examw.com/about/job/">诚聘英才</a>┊<a rel="nofollow" href="http://www.examw.com/about/contact.html">联系我们</a>┊<a rel="nofollow" href="http: //bbs.examw.com/forum-4-1.html">意见咨询</a></div>
	<div id="ft_about"></div>
</div>
<script type="text/javascript" language="javascript" src="http://img.examw.com/test/c.js"></script>
<script type="text/javascript" language="javascript" src="http://img.examw.com/e_u.js"></script>
<#-- 统计代码  -->
<iframe src="http://test.examw.com/count.html" height="0" width="0" scrolling="no"></iframe>
</#macro>

