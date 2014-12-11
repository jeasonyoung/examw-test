<#--加载页面的脚本头信息-->
<#assign jquery_version = "1.11.1" />
<#assign easyui_version = "easyui.1.4"/>
<#assign ueditor_version = "ueditor.1.4.3"/>

<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta name="viewport" content="width=device-width,initial-scale=1.0"/>
<!-- easyui css -->
<link rel="stylesheet" href="<@s.url "/resources/${easyui_version}/themes/icon.css"/>" type="text/css"/>
<link rel="stylesheet" href="<@s.url "/resources/${easyui_version}/themes/color.css"/>" type="text/css"/>
<link rel="stylesheet" href="<@s.url "/resources/${easyui_version}/themes/bootstrap/easyui.css"/>" type="text/css"/>
<!--jquery js -->
<script type="text/javascript" src="<@s.url "/resources/jquery.min.${jquery_version}.js"/>"></script>
<!--easyui js-->
<script type="text/javascript" src="<@s.url "/resources/${easyui_version}/jquery.easyui.min.js"/>"></script>
<script type="text/javascript" src="<@s.url "/resources/${easyui_version}/locale/easyui-lang-zh_CN.js"/>"></script>
<!--ueditor-->
<script type="text/javascript" charset="utf-8" src="<@s.url '/resources/${ueditor_version}/ueditor.config.js'/>"></script>
<script type="text/javascript" charset="utf-8" src="<@s.url '/resources/${ueditor_version}/ueditor.all.js'/>"></script>
<script type="text/javascript" src="<@s.url "/resources/easyui-ueditor.js"/>"></script>
<!--easyui ext-->
<script type="text/javascript" src="<@s.url "/resources/easyui-ext.js"/>"></script>
<!-- custom css -->
<link rel="stylesheet" href="<@s.url "/resources/default.css"/>" type="text/css"/>