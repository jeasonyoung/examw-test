<#--选择题预览-->
<#macro item_choice info>
<div style="float:left;width:100%;">
	<dl style="float:left;margin-left:15px;">
		<dt><#if (info.orderNo > 0)>${info.orderNo}.</#if> ${info.content}</dt>
		<dt>[选项]:</dt>
		<#list info.children as c>
			<dd>${c.content}</dd>
		</#list>
		<dt>[答案]:</dt>
		<#if info.answer ??>
			<#list info.children as c>
				<#if info.answer?index_of(c.id) != -1>
					<dd>${c.content}</dd>
				</#if>
			</#list>
		</#if>
		<dt>[解析]:</dt>
		<dd>${info.analysis}</dd>
	</dl>
</div>
</#macro>
<#--判断题预览-->
<#macro item_judge info>
<div style="float:left;width:100%;">
	<dl style="float:left;margin-left:15px;">
		<dt><#if (info.orderNo > 0)>${info.orderNo}.</#if>${info.content}</dt>
		<dt>[答案]:</dt>
		<#if (info.answer??) && (ItemJudgeAnswers??)>
			<#list ItemJudgeAnswers?keys as key>
				<#if (key == info.answer)>
					<dd>${ItemJudgeAnswers[key]}</dd>
				</#if>
			</#list>
		</#if>
		<dt>[解析]:</dt>
		<dd>${info.analysis}</dd>
	</dl>
</div>
</#macro>
<#--问答题预览-->
<#macro item_qanda info>
<div style="float:left;width:100%;">
	<dl style="float:left;margin-left:15px;">
		<dt><#if (info.orderNo > 0)>${info.orderNo}.</#if>${info.content}</dt>
		<dt>[答案]:</dt>
		<dd>${info.answer}</dd>
		<dt>[解析]:</dt>
		<dd>${info.analysis}</dd>
	</dl>
</div>
</#macro>
<#--共享题干题预览-->
<#macro item_share_title info>
<div style="float:left;width:100%;">
	<dl style="float:left;margin-left:15px;">
		<dt><#if (info.orderNo > 0)>${info.orderNo}.</#if>${info.content}</dt>
		<dt>[子题集合]:</dt>
		<#list info.children as c>
			<dd>
				<#switch c.type>
					<#case 1><#--单选-->
					<#case 2><#--多选-->
					<#case 3><#--不定向选-->
						<@item_choice c/>
					<#break>
					<#case 4><#--判断题-->
						<@item_judge c/>
					<#break>
					<#case 5><#--问答题-->
						<@item_qanda c/>
					<#break>
					<#default>
					<span style="float:left;margin-left:15px;">子题型［${c.typeName} - ${c.type}］暂不支持预览！</span>
				</#switch>
			</dd>
		</#list>
	</dl>
</div>
</#macro>
<#--共享答案题预览-->
<#macro item_share_answer info>
<div style="float:left;width:100%;">
	<dl style="float:left;margin-left:15px;">
		<dt><#if (info.orderNo > 0)>${info.orderNo}.</#if>${info.content}</dt>
		<#if info.children??>
			<dt>[共享答案]:</dt>
			<#assign len = (info.children?size) - 1 />
			<#list info.children as c>
				<#if (c_index < len)>
				<dd>${c.content}</dd>
				</#if>
			</#list>
			<dt>[子题集合]:</dt>
			<#assign child = info.children[len] />
			<#if ((child??) && (child.children??))>
				<#list child.children as c>
					<dl style="float:left;margin-left:15px;">
						<dd>${c.content}</dd>
						<#list info.children as p>
						<dt>[答案]</dt>
						<#if c.answer?index_of(p.id) != -1>
							<pre>${p.content}</pre>
							<#break>
						</#if>
						</#list>
						<dt>[解析]:</dt>
						<dd>${c.analysis}</dd>
					</dl>
				</#list>
			</#if>
		</#if>
	</dl>
</div>
</#macro>