
$.fn.zxbottommenu = function(options,param){
	var _def = {
			items:[],
			center:true,
			itemclick:function(item,index){},
			centerclick:function(){},
			centeradapter:function(){return '';},
			adapter:function(item,index){return item.text;}
		};
	var _methods = {
			getOptions:function(jq){return $(jq).data('zxbottommenu');},
	};
	if(options == null){
		options = $(this).zxOptions('options');
	}
	if(typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).data('zxbottommenu',_ops);
		_init($(this));
		
	}else if(typeof options == 'string'){
		var m = _methods[options];
		return m(this,param);
	}
	
	function _init(jq){
		var _ops = _methods.getOptions(jq);
		var _items = _ops.items;
		var count = _ops.center?_items.length+1:_items.length;
		$(jq).addClass('zxbottommenu');
		for(var i=0;i<_items.length;i++){
			var _item = {text:'',img:''};
			var item = $.extend(_item,_items[i]);
			$(jq).append('<div class="zxbottommenu_item">'+_ops.adapter(item,i)+'</div>');
			if((i+1) == _items.length/2 && _ops.center){
				$(jq).append('<div class="zxbottommenu_center">'+_ops.centeradapter(item,i)+'</div>');
			}
		}
		$(jq).find('.zxbottommenu_item,.zxbottommenu_center').css({'width':(100/count)+'%','float':'left'});
		$(jq).find('.zxbottommenu_item,.zxbottommenu_center').off('click');
		$(jq).find('.zxbottommenu_item').click(function(){
			var index = $(this).index();
			var _i = $(jq).find('.zxbottommenu_center').index();
			if(index == _i){
				return;
			}else if(index > _i){
				index--;
			}
			_ops.itemclick(_items[index],index);
		});
		$(jq).find('.zxbottommenu_center').click(function(){
			_ops.centerclick();
		});
	}
}

/**
 * tab切换页
 */
$.fn.tabview = function(options,param){
	var _def = {
		titles:[],
		views:[],
		showIndex:0,
		onChange:function(index,title){},
		onTitleClick:function(index,title){},
		panel:'',
		panelHeight:'auto',
		hidden2show:true,
		touch:false,
		adapter:function(title,index){return title.text;}
	};
	var _methods = {
			getOptions:function(jq){
				return $(jq).data('tabview');
			},
			showView:function(jq,index){
				$(jq).find('.tabview_view').hide();
				$(jq).find('.tabview_view:eq('+index+')').show();
			},
			showTitle:function(jq,index){
				$(jq).find('.tabview_title button').removeClass('active');
				$(jq).find('.tabview_title_'+index+' button').addClass('active');
			},
			show:function(jq,index){
				index = index == null?this.getOptions(jq).showIndex:index;
				this.showTitle(jq, index);
				this.showView(jq, index);
				var _index = this.getIndex(jq);
				_ops.onTitleClick(index,_ops[_index]);
				_ops.onChange(index,_ops[_index]);
//				_initTouch(jq);
			},
			getIndex:function(jq){
				return $(jq).find('.tabview_view:visible').index();
			},
			next:function(jq){
				var max = this.getOptions(jq).titles.length-1;
				var _index = this.getIndex(jq);
				_index = _index>=max?max:++_index;
				this.show(jq, _index);
			},
			prev:function(jq){
				var _index = this.getIndex(jq);
				_index = _index<=0?0:--_index;
				this.show(jq, _index);
			}
	};
	if(options == null){
		options = $(this).zxOptions('options');
	}
	if(typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).data('tabview',_ops);
		_init($(this));
		
	}else if(typeof options == 'string'){
		var m = _methods[options];
		return m(this,param);
	}
	
	function _init(jq){
		_initTitles(jq);
		_initViewPanel(jq);
		_methods.show(jq);
	}
	function _initTitles(jq){
		//navbar-fixed-top
		var _ops = _methods.getOptions(jq);
		var _titles = _ops.titles;
		$(jq).addClass('row');
		$(jq).append('<div class="btn-group btn-group-justified tabview_titles" role="group" aria-label="nav"></div>');
		var _top = $(jq).find('.tabview_titles').offset().top;
		$(window).scroll(function(){
			var _scrolltop = $(window).scrollTop();
			if(_top<_scrolltop){
				$(jq).find('.tabview_titles').addClass('navbar-fixed-top');
			}else{
				$(jq).find('.tabview_titles').removeClass('navbar-fixed-top');
			}
			
		});
		for(var i=0;i<_titles.length;i++){
			var _t = _titles[i];
			var _l = _t.label == null?'':'<span class="badge">'+_t.label+'</span>';
			$(jq).find('.tabview_titles').append('<div class="btn-group tabview_title tabview_title_'+i+'" role="group">'+
					'<button type="button" class="btn btn-primary">'+
					_ops.adapter(_t,i)+_l+'</button></div>');
		}
		$(jq).find('.tabview_title').off('click');
		$(jq).find('.tabview_title').click(function(){
			var index = $(this).index()
			_methods.show(jq,index);
			_ops = _methods.getOptions(jq);
		});
	}
	function _initViewPanel(jq){
		var _ops = _methods.getOptions(jq);
		var _views = _ops.views;
		$(jq).append('<div class="container tabview_viewpanel"></div>');
		for(var i=0;i<_views.length;i++){
			var _v = _views[i];
			$(_v).addClass('tabview_view');
			$(_v).appendTo($(jq).find('.tabview_viewpanel'));
		}
		$(jq).find('.tabview_view').hide();
		$(jq).find('.tabview_viewpanel').css('height',_ops.panelHeight);
		if(_ops.touch){
			_initTouch(jq);
		}
	}
	function _initTouch(jq){
		$(jq).swipe({			
			swipeLeft:function(){
				_methods.next(jq);
			},
			swipeRight:function() {
				_methods.prev(jq);
			}
		});
	}
}

/**
 * 数据展示
 */
$.fn.datashow = function(options,param){
	var _def = {
			colsize:{'col-xs-':1,'col-sm-':1,'col-md-':1,'col-lg-':1,'col-xl-':1},
			adapter:function(data){return '<div style="height:200px;border:1px solid black;">'+data+'</div>';},
			onitemclick:function(data,index,list){},
			sort:function(list){},
			isScroll:false,
			onup:function(){},
			ondown:function(){},
			empty:function(){return "<div>没有数据显示哦!</div>"},
			tip:function(){return "<div>正在加载数据!</div>"},
			datas_wapper:'datas_wapper',
			isup:true,
			isdown:true,
			optimize:false,
			optimizeNum:30
	};
	var _methods = {
		getColSize:function(jq,data){
			var _ops = _methods.getOptions(jq);
			var colsize = _ops.colsize;
			var _c = '';
			for(var key in colsize){
				var v = colsize[key];
				_c += ' '+key+(12/v);
			}
			return _c;
		},
		add:function(jq,data){
			$(jq).append(_methods.getView(jq, data));
			var _list = _methods.getList(jq);
			_list.push(data);
			$(jq).data('list',_list);
			_methods.check(jq);
			_methods.registerEvent(jq);
			_methods.refreshScroll(jq);
		},
		refreshScroll:function(jq){
			var _ops = _methods.getOptions(jq);
			if(_ops.isScroll){
				_ops.myscroll.refresh();
			}
		},
		insert:function(jq,data){
			$(jq).prepend(_methods.getView(jq, data));
			var _list = _methods.getList(jq);
			_list.unshift(data);
			$(jq).data('list',_list);
			_methods.check(jq);
			_methods.registerEvent(jq);
			_methods.refreshScroll(jq);
		},
		adds:function(jq,arr){
			for(var i=0;i<arr.length;i++){
				_methods.add(jq, arr[i]);
			}
		},
		inserts:function(jq,arr){
			for(var i=0;i<arr.length;i++){
				_methods.insert(jq, arr[i]);
			}
		},
		remove:function(jq,index){
			$(jq).find('.datashow_item').eq(index).remove();
			$(jq).data('list',_methods.getList(jq).remove(index));
		},
		setAdapter:function(jq,fun){
			_setOptions(jq, 'adapter', fun);
		},
		sort:function(jq){
			var _ops = this.getOptions(jq);
			var list = _methods.getList(jq);
			list = _ops.sort(list);
			$(jq).data('list',list);
		},
		reView:function(jq){
			_methods.clearView(jq);
			var list = _methods.getList(jq);
			_methods.inserts(jq,list);
		},
		clearView:function(jq){
			var _ops = _methods.getOptions(jq);
			$(jq).data('list',[]);
			$(jq).html('');
			_methods.check(jq);
		},
		check:function(jq){
			var _ops = _methods.getOptions(jq);
			var _list = _methods.getList(jq);
			if(_list.length == 0){
				$(jq).html('<div class="data_show_empty">'+_ops.empty()+'</div>');
			}else{
				$(jq).find('.data_show_empty').remove();
			}
		},
		tip:function(jq){
			var _ops = _methods.getOptions(jq);
			var _list = _methods.getList(jq);
			if(_list.length == 0){
				$(jq).html('<div class="data_show_empty">'+_ops.tip()+'</div>');
			}else{
				$(jq).find('.data_show_empty').remove();
			}
		},
		getList:function(jq){return _list = $(jq).data('list')||new Array();},
		getView:function(jq,data){return '<div class="datashow_item center-block'+_methods.getColSize(jq, data)+'">'+_methods.getOptions(jq).adapter(data)+'</div>';},
		registerEvent:function(jq){
			var _ops = this.getOptions(jq);
			$(jq).find('.datashow_item').off();
			$(jq).find('.datashow_item').on('click',function(){
				var index = $(this).index();
				var list = _methods.getList(jq);
				_ops.onitemclick(list[index],index,list);
			});
		},
		getOptions:function(jq){
			return $(jq).data('datashow');
		},
		getData:function(jq,index){
			var _list = $(jq).data('list');
			return (_list == null || index == null) ? null : _list[index];
		},
		clearPull:function(jq,p){
			if(p == null){
				$(jq).parent('.datashow_panel').find('.pull_up,.pull_down').hide();
			}else{
				$(jq).parent('.datashow_panel').find('.pull_'+p).hide();
			}
		},
		toTop:function(jq){
			var _ops = _methods.getOptions(jq);
			if(_ops.isScroll){
				_ops.myscroll.scrollTo(_ops.myscroll.x,0);
			}
		}
	};
	if(options == null){
		options = $(this).zxOptions('options');
	}
	if(typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).data('datashow',_ops);
//		_methods.clearView(this);
		$(this).addClass('row');
		$(this).after('<div class="container-fluid datashow_container"><div class="datashow_panel"></div></div>');
		$(this).appendTo($(this).next('.datashow_container').find('.datashow_panel'));
		_initScroll(this);
		_methods.tip(this);
	}else if(typeof options == 'string'){
		var m = _methods[options];
		return m(this,param);
	}
	function _setOptions(jq,key,val){
		var _ops = this.getOptions(jq);
		_ops[key] = val;
		$(this).data('datashow',_ops);
	}
	function _initScroll(jq){
		var _ops = $(jq).data('datashow');
		if(_ops.isScroll){
			var _p = $(jq).parent('.datashow_panel');
			$(jq).parent('.datashow_panel').append('<div class="pull_up"><span class="pull_up_ico">&nbsp;'+
						'</span><span class="pull_up_label">上拉加载...</span></div>');
			$(jq).parent('.datashow_panel').prepend('<div class="pull_down"><span class="pull_down_ico">&nbsp;'+
					'</span><span class="pull_down_label">下拉刷新...</span></div>');
			var _myScroll = new IScroll('#'+_ops.datas_wapper,{probeType:1, click:true,mouseWheel:false, scrollbars:true, fadeScrollbars:false, 
				interactiveScrollbars:false, tap:false, keyBindings:false,deceleration:0.0002,
				snap:false,listenX:false,listenY:true});
			_myScroll.on('scrollEnd', function () {
				if(isdirection){
					if(direction == 'up' && (_myScroll.directionY>0 || _myScroll.distY <0)){
						_p.find('.pull_up').removeClass('flip');
						_p.find('.pull_up').addClass('loading');
						_p.find('.pull_up .pull_up_label').text('加载中...');
						_ops.onup();
					}else if(direction == 'down' && (_myScroll.directionY<0 || _myScroll.distY >0)){
						_p.find('.pull_down').removeClass('flip');
						_p.find('.pull_down').addClass('loading');
						_p.find('.pull_down .pull_down_label').text('刷新中...');
						_ops.ondown();
					}else{
						_methods.clearPull(jq);
					}
				}
				isdirection = false;
				direction = '';
			});
			_myScroll.on('scroll', function () {
				if(isdirection){
					var direction_size = 45;
					if(_myScroll.directionY<0 || _myScroll.distY >0){
						if(_myScroll.y > direction_size || _myScroll.directionY ==0){
							if(direction == 'down'){
								_p.find('.pull_down .pull_down_label').text('释放进行刷新...');
								_p.find('.pull_down').addClass('flip');
								isdirection = true;
							}
						}
					}else if(_myScroll.directionY>0 || _myScroll.distY <0){
						if(_myScroll.y < _myScroll.maxScrollY - direction_size || _myScroll.directionY ==0){
							if(direction == 'up'){
								_p.find('.pull_up .pull_up_label').text('释放进行加载...');
								_p.find('.pull_up').addClass('flip');
								isdirection = true;
							}
						}
					}
				}
			});
			_ops.myscroll = _myScroll;
			var isdirection = false;
			var direction = '';
			_myScroll.on('scrollStart', function () {
				if(_myScroll.directionY<0 || _myScroll.distY >0){
					if(!_ops.isdown){
						return;
					}
					if(_myScroll.y > -5){
						_p.find('.pull_down').removeClass('loading');
						_p.find('.pull_down').show();
						_methods.refreshScroll(jq);
						_p.find('.pull_down .pull_down_label').text('下拉进行刷新...');
						isdirection = true;
						direction = 'down';
					}
				}else if(_myScroll.directionY>0 || _myScroll.distY <0){
					if(!_ops.isup){
						return;
					}
					if(_myScroll.y < _myScroll.maxScrollY+5){
						_p.find('.pull_up').removeClass('loading');
						_p.find('.pull_up').show();
						_methods.refreshScroll(jq);
						_p.find('.pull_up .pull_up_label').text('上拉进行加载...');
						isdirection = true;
						direction = 'up';
					}
					
				}
			});
			_ops.myscroll = _myScroll;
		}
		$(jq).data('datashow',_ops);
	}
}


/**
 * 弹出
 */
var _defModalindex = 1;
$.zxModal = function(options){
	var _zxModal = _defModalindex++;
	var _def = {
			size:'l',
			close:true,
			header:true,
			footer:true,
			buttons:[{text:'确定',handel:function(val,index){},clazz:'btn-primary',value:''},
			         {text:'取消',handel:function(val,index){},clazz:'btn-primary',value:''}],
			title:'this is my zxModal',
			body:'',
			onshow:function(e){},
			onshown:function(e){},
			onhide:function(e){},
			onhidden:function(e){},
			loaded:function(e){},
			delay:500,
			backdrop:'static',
			keyboard:false,
			show:false,
			anim:true
	};
	var _def_btn = {text:'OK',handel:function(val,index){},clazz:'btn-primary',value:''};
	var _ops = $.extend(_def,options);
	var _id = 'zxModal_'+_zxModal;
	var _size = _ops.size == 'l' ?' modal-lg':_ops.size == 's'?' modal-sm':'';
	var _h = '<div class="modal fade zxModal" id="'+_id+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
	_h += '<div class="modal-dialog'+_size+' zx-modal-dialog" role="document">';
	_h += '<div class="modal-content">';
	//header
	if(_ops.header){
		_h += '<div class="modal-header">';
		if(_ops.close){
			_h += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>'+
			'<span class="sr-only">Close</span></button>';
		}
		_h += '<h4 class="modal-title" id="'+_id+'Label">'+_ops.title+'</h4>';
		_h += '</div>';
	}
	//body
	_h += '<div class="modal-body">';
	_h += _ops.body;
    _h += '</div>';
    //footer
    if(_ops.footer){
    	_h += '<div class="modal-footer">';
    	if(_ops.buttons){
    		for(var i=0;i<_ops.buttons.length;i++){
    			var _b = _ops.buttons[i];
    			_b = $.extend(_def_btn,_b);
    			_h += '<button type="button" data-btnindex="'+i+'" id="'+_id+'_btn_'+i+'" class="btn '+_b.clazz+'" data-dismiss="modal">'+_b.text+'</button>';
    		}
    	}
    	_h += '</div>';
    }
    $('body').append(_h);
    //注册按钮事件
    if(_ops.footer && _ops.buttons){
    	for(var i=0;i<_ops.buttons.length;i++){
    		$('#'+_id+'_btn_'+i).off('click');
    		$('#'+_id+'_btn_'+i).click(function(){
    			var _i = $(this).data('btnindex');
    			var _b = _ops.buttons[_i];
    			$('#'+_id).modal('hide');
    			_b.handel(_b.value||_b.text,_i);
    		});
    	}
    }
    //Modal事件
    $('#'+_id).on('show.bs.modal', function (e) {_ops.onshow(e)});
    $('#'+_id).on('shown.bs.modal', function (e) {
    	_ops.onshown(e);
    	//处理背景点击不关闭动画
    	if(_ops.backdrop=='statis' && _ops.anim)
    		$('#'+_id).off('click');
    		$('#'+_id).click(function(e){
    			if($(e.target).attr('id') == _id){
    				$('#'+_id+' .modal-content').shaking();
    			}
    		});
    	});
    $('#'+_id).on('hide.bs.modal', function (e) {_ops.onhide(e)});
    $('#'+_id).on('hidden.bs.modal', function (e) {_ops.onhidden(e)});
    $('#'+_id).on('loaded.bs.modal', function (e) {_ops.loaded(e);});
    $('#'+_id).on('show.bs.modal', function (e) {
//    	$('#'+_id).find('.zx-modal-dialog').css('top',($(window).height()-58-$('#'+_id).find('.zx-modal-dialog').height())/3);
    	var _transform = ($(window).height()-58-$('#'+_id).find('.zx-modal-dialog').height())/3;
    	$('#'+_id).find('.zx-modal-dialog').css('top',_transform);
    });
    $('#'+_id).modal(_ops);
    
    //延迟显示
    setTimeout("$('#"+_id+"').modal('show')",_ops.delay);
    
}

$.zx={
		alert:function(text,title,callback){
			var _def = {
				size:'s',
				title:title||'提示',
				body:text||'',
				buttons:[{text:'确定',handel:function(){if(callback != null) callback();}}],
				delay:100,
				onhide:function(e){}
			};
			$.zxModal(_def);
		},
		confirm:function(title,text,callback){
			var _def = {
					size:'s',
					title:title||'提示',
					body:text||'',
					buttons:[{text:'确定',handel:function(){if(callback != null) callback(true);}},
						{text:'取消',handel:function(){if(callback != null) callback(false);}}],
					delay:100,
					onhide:function(e){}
				};
				$.zxModal(_def);
		}
};
var _defMessageIndex = 0;
var _defMessageTime = 3*1000;
$.message = function(msg,type,time){
	var _id = 'zxmessage_'+_defMessageIndex++;
	var _type = type == null ? 'success':type;
	$('body').append('<div id="'+_id+'" class="zxmessage alert alert-'+_type+'" role="alert">'+msg+'</div>');
	var _time = time == null ? _defMessageTime:time;
	var _timer = null;
	if(_time>0){
		_timer = setTimeout('$("#'+_id+'").remove()',_time);
	}
	$('#'+_id).click(function(){
		if(_timer != null){
			clearTimeout(_timer);
			$(this).remove();
		}
	});
}
$.fn.anim = function(c,endHandel){
	$(this).on('webkitAnimationEnd', function () {
		$(this).removeClass(c);
		if(endHandel != null) endHandel();
	});
	$(this).addClass(c);
}
//晃动动画
$.fn.shaking = function(o){
	$(this).anim('shaking');
}

/**
 * 工具
 */

$.zxUtil = {
		isEmpty:function(str){
			return str == null || str.trim() == '';
		},
		isNotEmpty:function(str){
			return !$.zxUtil.isEmpty(str);
		},
		json2str:function(json){ return this.isArray(json)?this.jsonArrToStr(json):this.jsonObjToStr(json)},
		jsonObjToStr:function(json){
			var str ="{";
			for(var item in json)
				str += this.isArray(json[item])?("'"+item+"':"+this.jsonArrToStr(json[item])+","):("'"+item+"':'"+json[item]+"',");
//			str = this.delLastDH(str);
			str = this.delEndStr(str, ',');
			str += "}";
			return str;
		},
		jsonArrToStr:function(json){
			var str = "[";
			for(var i=0;i<json.length;i++)
				str += this.isArray(json[i])?(this.jsonArrToStr(json[i]) +","):(this.jsonObjToStr(json[i]) +",");
			str =this.delEndStr(str, ',');
			str +="]";
			return str;
		},
		delLastDH :function(str){return (str.charAt(str.length-1) == ',')?str.substring(0,str.length-1):str;},
		str2json:function(str){try {return eval("("+str+")");} catch (e) {return {};}},
		replaceAll:function(str,from,to){},
		delStartStr:function(str,s){return (str.charAt(0) == s)?str.substring(1):str;},
		delEndStr:function(str,e){return (str.charAt(str.length-1) == e)?str.substring(0,str.length-1):str;},
		delStartAndEndStr:function(str,s,e){return $.zxUtil.delEndStr($.zxUtil.delStartStr(str,s),e);},
		zxIsShow:function(obj){return obj.css('display') != 'none';},
		getMonthMaxDay:function(y,m){ return m!=2?(_zxmaxDay[m-1]):(y%100==0?(y%400==0?29:28):(y%4==0?29:28));},
		isArray : function(obj) { return Object.prototype.toString.call(obj) === '[object Array]'; },
		isChinese:function isChinese(str){return !(/^([u4E00-u9FA5]|[uFE30-uFFA0])*$/.test(str));}
}

$.fn.zxOptions = function(key){
	key = $.zxUtil.isEmpty(key)?'options':key;
	var _result = {};
	var options = $(this).data(key);
	if($.zxUtil.isNotEmpty(options)){
			try {
				_result = eval('({' + options + '})');
			} catch (e) {
			}
	}
	return _result;
}
$.fn.zxSetOptions = function(key,val){
	key = $.zxUtil.isEmpty(key)?'options':key;
	if(typeof val == 'object'){
		val = $.zxUtil.delStartAndEndStr($.zxUtil.json2str(val),"{",'}');
	}
	var options = $(this).data(key,val);
}

/** 
 *  对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
    可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
    Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423      
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
 */        
Date.prototype.Format =function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {"0":"/u65e5","1":"/u4e00", "2":"/u4e8c","3":"/u4e09","4":"/u56db", "5":"/u4e94","6":"/u516d"};         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}

$.fn.zxget = function(url,data,doData,successEnd){
	var jq = $(this);
	$.ajax({
		url:_base_param.context_name+url,
		type:'get',
		data:data,
		success:function(result){
			if(result.ret_code == '000'){
				if(doData != null){
					doData(result.ret_data);
				}
			}else if(result.ret_code != null){
				$.message(result.ret_msg);
			}else{
				$('body').html(result);
			}
			if(successEnd != null){
				successEnd(result);
			}
			try {
				$(jq).datashow('clearPull');
			} catch (e) {}
		},
		timeout :10*1000,
		complete : function(XMLHttpRequest,status){
			if(status=='timeout'){
				$.message("请求超时!");
				try {
					$(jq).datashow('clearPull');
				} catch (e) {}
			}
		}
	});
}
$.fn.zxpost = function(url,data,doData,successEnd){
	$.ajax({
		url:_base_param.context_name+url,
		type:'post',
		data:{page:home_page,size:home_size},
		success:function(result){
			if(result.ret_code == '000'){
				if(doData != null){
					doData(result.ret_data);
				}
			}else if(result.ret_code != null){
				$.message(result.ret_msg);
			}else{
				$('body').html(result);
			}
			if(successEnd != null){
				successEnd(result);
			}
		}
	});
}
