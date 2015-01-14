<#--试卷详情-->
<#include "/inc.ftl" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${paperName}-试卷详情</title>
		<#--头部资源-->
		<@header_resources/>
	</head>

	<body>
		<#--top banner-->
		<@topbox/>
		<div class="h20"></div>
		<div class="main">
			<div class="weizhi fl"><span></span>当前位置：<a href="#">首页</a><i>&gt;</i>${examName}<i>&gt;</i>${subjectName}</div>
		</div>
		<div class="h1d"></div>
		<div class="h1f"></div>
		<div class="h20"></div>
		<div class="main">
			<div class="titbox bulebg fl"><span><div class="cheng-btn"><a href="#">选择产品</a></div></span>${subjectName}</div><div class="titboxbg"></div>
		    <div class="content yinying">
		        <div class="tx fl">
		            <div class="tx-pic"><img src="http://img.examw.com/test/tx-pic.png" width="350" height="330"></div>
		            <div class="tx-fr">
		                <div class="title fl">${paperName}</div>
		                <div class="list">
		                    <ul>
		                        <li>考试币：<span>${price}个</span></li>
		                        <li>年份：${year}年</li>
		                        <li>类型：${typeName}</li>
		                        <li>总分：${score}分</li>
		                        <li>总题数：${total}题</li>
		                        <li>作答：${time}分钟</li>
		                    </ul>
		                </div>
		                <div class="botton-box">
		                    <div class="botton"><div class="cheng-btn"><a href="http://tiku.examw.com/library/paper/do/multi/${paperId}">普通模考</a></div></div>
		                    <div class="botton"><div class="hong-btn"><a href="http://tiku.examw.com/library/paper/do/single/${paperId}">全真模考</a></div></div>
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
		                <li class="out" onMouseOver="this.className='over'" onMouseOut="this.className='out'">
		                    <div class="title">
		                        <em>&middot;<a href="#" target="_blank" title="${p.text}">${p.text}</a></em>
		                        <span>${p.price}元</span>
		                        <span class="red">${p.discount}元</span>
		                        <span>${p.total}题</span>
		                        <i><div class="buy"><a name="index-products-buy"  id="${p.id}" Price="${p.price}" GoodPrice="${p.discount}" Title="${p.text}" href="#" target="_blank">我要购买</a></div><div class="tiyan"><a href="/${abbr}/${p.id}.html" target="_blank">免费体验</a></div></i>
		                    </div>
		                </li>
		                </#list>
		            </ul>
		        </div>
		    </div>
		    </#if>
		    <div class="h20"></div>
		    <div class="content yinying">
		        <div class="txbox">
		        	<div class="name">题型介绍</div>
		            <div class="cont">
		            	<div class="title">
		                	<i><div class="title"><b>单项选择题</b></div><p>一、单项选择题(共44题，每题1分，每题的备选项中，只有一个最符合题意)</p></i>
		                </div>
		                <div class="timu fl">
		                	<h2>第 1 题 关于施工总承包方项目管理任务的说法，正确的是( )。 </h2>
		                    <p>A.施工总承包方一般不承担施工任务，只承担施工的总体管理和协调工作 </p>
		                    <p>B.施工总承包方只负责所施工部门的施工安全，对业主指定分包商的施工安全不承担责任</p>
		                    <p>C.施工总承包方不与分包商直接签订施工合同，均有业主方签订</p>
		                    <p>D.施工总承包方应负责施工资源的供应组织</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 2 题 关于施工总承包方项目管理任务的说法，正确的是( )。 </h2>
		                    <p>A.施工总承包方一般不承担施工任务，只承担施工的总体管理和协调工作 </p>
		                    <p>B.施工总承包方只负责所施工部门的施工安全，对业主指定分包商的施工安全不承担责任</p>
		                    <p>C.施工总承包方不与分包商直接签订施工合同，均有业主方签订</p>
		                    <p>D.施工总承包方应负责施工资源的供应组织</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 3 题 关于施工总承包方项目管理任务的说法，正确的是( )。 </h2>
		                    <p>A.施工总承包方一般不承担施工任务，只承担施工的总体管理和协调工作 </p>
		                    <p>B.施工总承包方只负责所施工部门的施工安全，对业主指定分包商的施工安全不承担责任</p>
		                    <p>C.施工总承包方不与分包商直接签订施工合同，均有业主方签订</p>
		                    <p>D.施工总承包方应负责施工资源的供应组织</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		            </div>
		            <div class="cont">
		            	<div class="title">
		                	<i><div class="title"><b>多项选择题</b></div><p>二、多项选择题(共12题，每题2分，每题的备选项中，有2个或2个以上符合题意，至少有1个错项。错选，本题不得分；少选，所选的每个选项得0．5分)</p></i>
		                </div>
		                <div class="timu fl">
		                	<h2>第 1 题 关于施工总承包方项目管理任务的说法，正确的是( )。 </h2>
		                    <p>A.施工总承包方一般不承担施工任务，只承担施工的总体管理和协调工作 </p>
		                    <p>B.施工总承包方只负责所施工部门的施工安全，对业主指定分包商的施工安全不承担责任</p>
		                    <p>C.施工总承包方不与分包商直接签订施工合同，均有业主方签订</p>
		                    <p>D.施工总承包方应负责施工资源的供应组织</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 2 题 关于施工总承包方项目管理任务的说法，正确的是( )。 </h2>
		                    <p>A.施工总承包方一般不承担施工任务，只承担施工的总体管理和协调工作 </p>
		                    <p>B.施工总承包方只负责所施工部门的施工安全，对业主指定分包商的施工安全不承担责任</p>
		                    <p>C.施工总承包方不与分包商直接签订施工合同，均有业主方签订</p>
		                    <p>D.施工总承包方应负责施工资源的供应组织</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 3 题 关于施工总承包方项目管理任务的说法，正确的是( )。 </h2>
		                    <p>A.施工总承包方一般不承担施工任务，只承担施工的总体管理和协调工作 </p>
		                    <p>B.施工总承包方只负责所施工部门的施工安全，对业主指定分包商的施工安全不承担责任</p>
		                    <p>C.施工总承包方不与分包商直接签订施工合同，均有业主方签订</p>
		                    <p>D.施工总承包方应负责施工资源的供应组织</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		            </div>
		            <div class="cont">
		            	<div class="title">
		                	<i><div class="title"><b>判断题</b></div><p>三、判断题（本类题共20题，每小题1分，共20分。）</p></i>
		                </div>
		                <div class="timu fl">
		                	<h2>第 1 题 关于施工总承包方项目管理任务的说法，是只承担施工的总体管理和协调工作。 </h2>
		                    <p><label><input type="radio" name="" value="1">对</label></p>
		                    <p><label><input type="radio" name="" value="2">错</label></p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 2 题 关于施工总承包方项目管理任务的说法，是只承担施工的总体管理和协调工作。 </h2>
		                    <p><label><input type="radio" name="" value="1">对</label></p>
		                    <p><label><input type="radio" name="" value="2">错</label></p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 3 题 关于施工总承包方项目管理任务的说法，是只承担施工的总体管理和协调工作。 </h2>
		                    <p><label><input type="radio" name="" value="1">对</label></p>
		                    <p><label><input type="radio" name="" value="2">错</label></p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		            </div>
		            <div class="cont">
		            	<div class="title">
		                	<i><div class="title"><b>材料题</b></div><p>四、案例分析题（本类题共20题，每小题1分，共20分。）</p></i>
		                </div>
		                <div class="timu fl">
		                	<h2>回答题材料：
		高级会计师的基本任务条件之一是取得博士学位高级会计师的基本任务条件之一是取得博士学位高级会计师的基本任务条件之一是取得博士学位
		要求：根据以上资料，回答以下问题</h2>
		                </div>
		                <div class="timu fl">
		                	<h2>第 1 题 高级会计师的基本任务条件之一是取得博士学位，并担任会计师职务( )。</h2>
		                    <p>A.3-5年</p>
		                    <p>B.2-5年</p>
		                    <p>C.1-5年</p>
		                    <p>D.5年以上</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 2 题 高级会计师的基本任务条件之一是取得博士学位，并担任会计师职务( )。</h2>
		                    <p>A.3-5年</p>
		                    <p>B.2-5年</p>
		                    <p>C.1-5年</p>
		                    <p>D.5年以上</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		                <div class="timu fl">
		                	<h2>第 3 题 高级会计师的基本任务条件之一是取得博士学位，并担任会计师职务( )。</h2>
		                    <p>A.3-5年</p>
		                    <p>B.2-5年</p>
		                    <p>C.1-5年</p>
		                    <p>D.5年以上</p>
		                    <b>【正确答案-参考解析】：参加考试可见</b>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
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