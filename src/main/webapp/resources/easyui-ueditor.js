/**
 * easyui-ueditor整合插件。
 * author:农民叔叔
 * date:2014-08-19.
 */
(function($,UE){
	//定义插件
	$.fn.ueditor = function(options,param){
		if(typeof options == 'string'){//方法调用
			return $.fn.ueditor.methods[options](this,param); 
		}
		options =  options || {};
		return this.each(function(){
			var state = $.data(this,'ueditor');
			if(state){
				$.extend(state.options, options);
			}else{
				state = $.data(this,'ueditor',{
					options : $.extend({},$.fn.ueditor.defaults,$.fn.ueditor.parseOptions(this),options)
				});
			}
			_create(this);//创建编辑器对象
		});
	};
	//创建ueditor编辑器对象
	function _create(target){
		var isArea = /^(?:textarea)$/i.test(target.nodeName);
		if(!isArea){
			$.messager.alert('错误','插件限定使用TextArea作为U Editor的装载容器(当前为：'+target.nodeName +')！','error');
			return;
		}
		var state = $.data(target,'ueditor'),
			   targetId = _getTargetId(target);
		state['editor'] = UE.getEditor(targetId,state.options);
		//初始化事件
		_initEvents(target);
	};
	//获取ueditor的ID。
	function _getTargetId(target){
		var t =  $(target),name =  t.attr('name');
		if($.trim(t.attr('id')) != '') return  t.attr('id');
		var targetId  = target.nodeName +'_'+ _getRandom(9999),frm = t.closest('form');
		if(frm && $.trim(frm.attr('id')) != '' && ($.trim(name) != '')){
			targetId =  frm.attr('id') + '_' + name;
		}
		t.attr('id', targetId);
		return targetId;
	}
	//获取随机数
	function _getRandom(n){
		return Math.floor(Math.random() * n + 1) + '_';
	}
	//初始化事件
	function _initEvents(target){
		var state = $.data(target,'ueditor'),
			   opts =  state.options;
		$.each($.fn.ueditor.events,function(i,n){
			var event = 'on' + n, e = opts[event];
			state.editor.addListener(n,function(){
				if(n == 'contentChange'){
					var val = opts['value'] = $(target).ueditor('getValue');
					$(target).val(val);
				}
				 if($.isFunction(e)){
					 e.apply(target,arguments);
				 }
			});
		});
	}
	//获取内容
	function _getValue(target){
		var state = $.data(target,'ueditor');
		return state.editor.getContent();
	};
	//设置内容
	function _setValue(target,value){
		//alert('_setValue_id=' + $(target).attr('id'));
		if(target && $(target).attr('id')){//解决直接引用赋值时editor的body报错
			var editor = UE.getEditor($(target).attr('id'));
			editor.ready(function(){
				editor.setContent(value);
			});
		}
	};
	//同步数据
	function _sync(target){
		var state = $.data(target,'ueditor');
		if(state && state.editor){
			state.editor.sync();
		}
	};
	//销毁编辑器对象
	function _destroy(target){
		if(!target) return;
		var state = $.data(target,'ueditor');
		if(!state || !state.editor) return;
		state.editor.destroy();
		$(target).remove();
		//alert('_destroy_ok');
	};
	//方法定义
	$.fn.ueditor.methods = {
			//获取配置选项
			options:function(jq){
				var state = $.data(jq[0],'ueditor');
				return state['options'];
			},
			//获取编辑器对象
			editor:function(jq){
				var state = $.data(jq[0],'ueditor');
				return state['editor'];
			},
			//获取编辑器内容
			getValue:function(jq){
				return _getValue(jq[0]);
			},
			//设置编辑器内容
			setValue:function(jq,value){
				return jq.each(function(){
					_setValue(this,value);
				});
			},
			//同步数据
			sync:function(jq){
				return jq.each(function(){
					_sync(this);
				});
			},
			//销毁编辑器实例
			destroy:function(jq){
				return jq.each(function(){
					_destroy(this);
				});
			}
	};
	//事件配置
	$.fn.ueditor.events = ['destroy','contentChange'];
	//默认配置
	$.fn.ueditor.defaults  = {
			toolbars:[['fullscreen','source','|','bold','italic','underline','fontborder','strikethrough','superscript','subscript','removeformat','formatmatch',
			           		'autotypeset','pasteplain','|','forecolor','backcolor','insertorderedlist','insertunorderedlist','cleardoc','|']],
			zIndex:9999,
			charset:'utf-8',
			enableAutoSave:false,
			oncontentChange:function(){},
			ondestroy:function(){}
	};
	//配置解析
	$.fn.ueditor.parseOptions = function(target){
		var opts = $.extend({},$.parser.parseOptions(target));
		if(opts["width"]) opts["initialFrameWidth"] = opts["width"]; 
		if(opts["height"]) opts["initialFrameHeight"] = opts["height"];
		return opts;
	};
	//挂钩panel的自动销毁
	$.extend($.fn.dialog.defaults,{
		onBeforeDestroy:function(){
			$('.easyui-ueditor').ueditor('destroy');
		}
	});
	//挂钩form的加载数据
	$.extend($.fn.form.defaults,{
		onLoadSuccess:function(data){
			if(!data) return;
			var editors = $('textarea[class=easyui-ueditor]');
			if(editors){
				$.each(editors,function(){
					var n = $(this).attr('name');
					if(n && data[n]){
						var editor = $(this).prev('.easyui-ueditor');
						if(editor && $(editor).attr('id')){
							//alert('editor=' + editor + ',id=' +$(editor).attr('id') + ',value=' +data[n]);
							$(editor).ueditor('setValue',data[n]);
						}
					}
				});
			}
		}
	});
	//添加到插件管理器中
	$.parser.plugins.push("ueditor");
})(jQuery,UE);