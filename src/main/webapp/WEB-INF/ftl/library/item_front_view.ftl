<#assign answerflag=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]/>
<#assign xuhao = 0/>	<#-- 计算序号 -->
<#assign total = 0/>	<#-- 总数 -->
<#macro option_flag index>${answerflag[index]}</#macro>

<#macro show_item father item index>
	<#if item.type == 1>	<#--单选-->
		<@item_choose parent=father i=item input="radio" index=index/>
	<#elseif item.type == 2 || item.type == 3> <#--多选|不定项-->
		<@item_choose parent=father i=item input="checkbox" index=index/>
	<#elseif item.type == 4> <#--判断-->
		<@item_judge parent=father i=item index=index/>
	<#elseif item.type == 5> <#--问答-->
		<@item_qanda i=item index=index/>
	<#elseif item.type == 6> <#--共享题干-->
		<@item_share_title i=item index=index/>
	<#elseif item.type == 7> <#--共享答案-->
		<@item_share_answer i=item index=index/>
	<#else>
	</#if>
</#macro>
<#macro item_choose parent i input index>
	<div name="item_whole" class="box fl" item_type="${i.type}" item_id="${i.id}" >
       <div class="timu fl" ><a name="1" id="1"></a>
           <i>${index}.</i>
              <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl" >
       		<#if i.children??>
            <div class="list">
                <ul>
                <#list i.children?sort_by(["orderNo"]) as option>
                <li class="out"><#if i.answer?contains(option.id)><span><@option_flag option_index/>. </span>
                	<#else>
                	<i><@option_flag option_index/>．</i>
                	</#if>
                	<em><#if option.content?matches("[A-Z]{1}[.][\\W\\w]*")>${option.content?substring(2)}<#else>${option.content}</#if></em>
                </li>
                </#list>
                </ul>
            </div>
            </#if>
         </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${i.id}" ></div>
               <div class="f-l fl"><i>参考答案：</i><@calculate_right_answer parent i/></div>
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')">收起解析</a></i><em class="jiexi-h"></em></div>
               </div>
         </div>
         <@item_analysis i index/>
    </div>
</#macro>
<#macro item_judge parent i index>
	<div class="box fl" item_type="${i.type}" item_id="${i.id}" item_status="${i.answerStatus}">
		<#if MODEL != "multi" && i.parentContent??>
		<div id="font14" class="fenxiti fl">
			<i>材料题</i>
			<em>${i.parentContent}</em>
		</div>
		</#if>
        <div class="timu fl" ><a name="10" id="10"></a>
            <i>${index}.</i>
            <em><span>[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
        </div>
         <div class="daanbox fl">
               <div class="zhankai-bg" item_id="${(i.id)}" ></div>
               <div class="f-l fl"><i>参考答案：</i>
               			<em class="dui">
               			<#if i.answer == ANSWER_JUDGE_RIGTH>
               			对
               			<#else>
               			错
               			</#if>
               			</em>
               	</div>
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')">收起解析</a></i><em class="jiexi-h"></em></div>
         </div>
         <@item_analysis i index/>
    </div>
</#macro>
<#-- 问答题 -->
<#macro item_qanda i index>
	<div class="box fl">
       <div class="timu fl"></a>
           <i>${index}.</i>
           <em><span id="cailiao14">[${i.typeName}]</span><#if i.pid??><span onclick="showCommonTitle('${i.pid}')" style="cursor:pointer">[查看材料]</span></#if>${i.content}</em>
       </div>
       <div class="xz-daan fl">
            <textarea readonly="readonly" name="" cols="" rows="" class="wenben">${i.userAnswer}</textarea>
       </div>
       <div class="daanbox fl">
               <div class="fr" id="font14">
                    <div class="f-r fr"><i><a href="javascript:void(0)" onclick="toggleAnalysis(this,'${i.id}')">收起解析</a></i><em class="jiexi-h"></em></div>
               </div>
         </div>
         <div class="jiexi-box fl" name="jiexi" item_id="${(i.id)}" >
              <#if i.answer??>
              <div class="cankaobox fl">
                   <i>参考答案：</i>${i.answer}
              </div>
              </#if>
              <#if i.analysis??>
              <div class="cankaobox fl">
                   <i>参考解析：</i>${i.analysis}
              </div>
              </#if>
              <div class="h10"></div>
         <div>
    </div>
</#macro>
<#-- 共享题干题  -->
<#macro item_share_title i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}"><i>${i.typeName}</i><em><#if i.content?matches("\\s*[(][一二三四五六七八九十]{1}[)][\\W\\w]*")>${(i.content?trim)?substring(3)}<#else>${i.content}</#if></em></div>
		<#list i.children?sort_by(["orderNo"]) as child>
        	<@show_item null child index+child_index/>
        </#list>
        <#assign xuhao = xuhao + i.children?size />
</#macro>
<#-- 共享答案题   -->
<#macro item_share_answer i index>
	<div class="fenxiti fl" fenxi_item_id = "${i.id}">
		<i>${i.typeName}</i>
		<em>${i.content}<br/>
		<div class="share_answer">
		<#list i.children?sort_by(["orderNo"]) as child>
			<#if child_index != (i.children?size - 1)>
				<@option_flag child_index/>. ${child.content}<br/>
			</#if>
		</#list>
		</div>
		</em>
	</div>
	<#assign share_answer_item = (i.children?sort_by(["orderNo"]))?last/>
	<@show_share_answer_item share_answer_item i index/>
    <#assign xuhao = xuhao + share_answer_item.children?size />
</#macro>
<#macro item_share_answer_content i>
	<#list i.children?sort_by(["orderNo"]) as child>
		<#if child_index != (i.children?size - 1)>
			<@option_flag child_index/>. ${child.content}<br/>
		</#if>
	</#list>
</#macro>
<#macro show_share_answer_item items parent index>
	<#list items.children as i>
		<@show_item parent i index+i_index/>
	</#list>
</#macro>
<#-- 计算正确答案 -->
<#macro calculate_right_answer parent i>
	 <em class="dui">
	 <#if parent??>	<#-- 共享答案题  -->
	 	<#list parent.children?sort_by(["orderNo"]) as option>
        	<#if i.answer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
        	</#if>
     	</#list>
	 <#else>
     	<#list i.children?sort_by(["orderNo"]) as option>
        	<#if i.answer?index_of(option.id)!=-1>
          		<@option_flag option_index/> 
        	</#if>
     	</#list>
     </#if>
     </em>
</#macro>

<#macro item_analysis i index>
	<div class="jiexi-box fl" name="jiexi" item_id="${(i.id)}" >
              <div class="cankaobox fl">
                   <i>参考解析：</i>${i.analysis}
              </div>
              <div class="h10"></div>
    </div>
</#macro>
<#-- 答题卡 -->
<#macro answer_card items>
	<#list items as item>
		<#if item_index == 0>
			<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
		<li><a class="weida" href="javascript:void(0)" onclick="focusTo(this,${item_index+1})" item_id="${item.id}" s_item_id="${item.id}" pid="${item.pid?default(item.id)}">${item_index+1}</a></li>
		<#if item_index != 0 && (item_index+1)%5==0>
			 	</ul>
        	</div>
     	</div>
     	<div class="five fl">
       			<div class="list">
          			<ul>
		</#if>
	</#list>
           </ul>
        </div>
     </div>
</#macro>