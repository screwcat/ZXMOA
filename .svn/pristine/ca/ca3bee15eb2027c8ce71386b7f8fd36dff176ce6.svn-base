
/**
 * 目前结构这样
 */
$.fn.demo = function(options,param){
	//默认属性
	var _def = {
	};
	//提供外部使用方法
	var _methods = {
		demo:function(jq,param){
			return $(jq).val();
		}
	};
	//参数为空时，取data属性
	if(options == null){
		options = $(this).zxOptions('options');
	}
	//为属性对象时，执行初始化操作，调用方法就是（‘方法名’,参数）与easyui相同
	if(typeof options == 'object'){
		//没赋值属性使用默认值
		var _ops = $.extend(_def,options);
		
	}else if(typeof options == 'string'){
		//获取方法，执行，第一个参数是对象本身，不存在方法处理待定
		var m = _methods[options];
		return m(this,param);
	}
}

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

$.fn.zxtable = function(options,param){
	var _def = {
		titles:[],	
		selectone:true,
		clickunselect:true,
		rownum:false,
		data:{rows:[],total:0},
		pagination:false,
		size:10,
		border:true,
		onclickrow:function(row,index){},
		onselectrow:function(row,index){},
		onunselectrow:function(row,index){},
		titleclick:function(title){},
		responsive:false
	};
	var _methods = {
		getOptions:function(jq){return $(jq).data('zxtable');},
		review:function(jq){
			$(jq).find('tbody.zxtable_body').remove();
			_initBody(jq);
		},
		setData:function(jq,data){
			var _ops = this.getOptions(jq);
			_ops.data = data;
			$(jq).data('zxtable',_ops);
			_methods.review(jq);
		},
		getData:function(jq){return this.getOptions(jq).data;},
		selectrow:function(jq,index){
			if(!_methods.isselect(jq, index)){
				$(jq).find('tbody.zxtable_body tr').index(index).click();
			}
		},
		unselectrow:function(jq,index){
			if(_methods.isselect(jq, index)){
				$(jq).find('tbody.zxtable_body tr').index(index).click();
			}
		},
		isselect:function(jq,index){
			var _c = $(jq).find('tbody.zxtable_body tr').index(index).attr('class');
			return _c.indexOf('table-active')>=0;
		},
		updaterow:function(jq,row){
			var _i = row.index;
			var _d = row.data;
			var _data = _methods.getData(jq);
			_data.rows[_i]= _d;
			_methods.setData(jq, _data);
		},
		getRow:function(jq,index){return _methods.getData(jq)[index];}
	};
	if(options == null){
		options = $(this).zxOptions('options');
	}
	if(typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).data('zxtable',_ops);
		_init($(this));
		
	}else if(typeof options == 'string'){
		var m = _methods[options];
		return m(this,param);
	}
	
	function _init(jq){
		_initHeader(jq);
		_initBody(jq);
	}
	function _initHeader(jq){
		var _ops = _methods.getOptions(jq);
		var _titles = _ops.titles;
		var _c = 'table';
		_c += _ops.border?' table-bordered':'';
		$(jq).addClass(_c);
		if(_ops.responsive){
			$(jq).after('<div class="table-responsive table-condensed">');
			$(jq).appendTo($(jq).next('div.table-responsive'));
		}
		$(jq).append('<thead class="zxtable_thead"><tr class="row"></tr></thead>');
		for(var i=0;i<_titles.length;i++){
			var _title = {field : '',text : '',hidden:false,formatter:function(val,row,index){return val;}};
			var _t = _titles[i];
			if(!_t.hidden){
				$(jq).find('thead.zxtable_thead tr').append('<th class="zxtable_title zx_table_'+_t.field+'" field="'+_t.field+'">'+_t.text+'</th>');
			}
		}
		$(jq).find('thead.zxtable_thead tr th.zxtable_title').off('click');
		$(jq).find('thead.zxtable_thead tr th.zxtable_title').click(function(){
//			var _i = $(this).index();
			var _f = $(this).attr('field');
			_ops.titleclick(_getTitle(_f));
		})
	}
	function _initBody(jq){
		var _ops = _methods.getOptions(jq);
		var _titles = _ops.titles;
		var _data = _ops.data;
		$(jq).append('<tbody class="zxtable_body"></tbody>');
		for(var i=0;i<_ops.size;i++){
			$(jq).find('tbody.zxtable_body').append('<tr class="row zxtable_row zxtable_row_'+i+'" data-tableindex="'+i+'"></tr>');
			var _d = _data.rows[i];
			if(_d == null){
				_d = {};
			}
			for(var j=0;j<_titles.length;j++){
				var _title = {field : '',text : '',hidden:false,formatter:function(val,row,index){return val;}};
				var _t = $.extend(_title,_titles[j]);;
				if(!_t.hidden){
					var text = _d[_t.field];
					text = _t.formatter(text,_d,i);
					$(jq).find('tbody.zxtable_body tr.zxtable_row_'+i).append('<td class="zxtable_body zx_table_'+_t.field+'" field="'+_t.field+'">'+text+'</td>');
				}
			}
		}
		$(jq).find('tbody.zxtable_body tr').off('click');
		$(jq).find('tbody.zxtable_body tr').click(function(){
			var _i = $(this).data('tableindex');
			_ops.onclickrow(_ops.data.rows[_i],_i);
			if(_ops.selectone){
				$(jq).find('tbody.zxtable_body tr').removeClass('table-active');
			}
			var _c = $(this).attr('class');
			if(_c.indexOf('zxtable_select')>=0){
				$(this).removeClass('table-active');
				_ops.onunselectrow(_ops.data.rows[_i],_i);
			}else{
				$(this).addClass('table-active');
				_ops.onselectrow(_ops.data.rows[_i],_i);
			}
		});
	}
	function _getTitle(jq,field){
		var _ops = _methods.getOptions(jq);
		var _titles = _ops.titles;
		for(var i=0;i<_titels.length;i++){
			if(field == _titles[i].field){
				return _titles[i];
			}
		}
		return null;
	}
}

var _defNavIndex = 0;
$.fn.navone = function(options,param){
	//默认属性
	var _def = {
		back:function(){},
		menus:[],
		title:''
	};
	//提供外部使用方法
	var _methods = {
			getOptions:function(jq){
				return $(jq).data('navone');
			}
	};
	//参数为空时，取data属性
	if(options == null){
		options = $(this).zxOptions('options');
	}
	//为属性对象时，执行初始化操作，调用方法就是（‘方法名’,参数）与easyui相同
	if(typeof options == 'object'){
		//没赋值属性使用默认值
		var _ops = $.extend(_def,options);
		$(this).data('navone',_ops);
		_init($(this));
		
	}else if(typeof options == 'string'){
		//获取方法，执行，第一个参数是对象本身，不存在方法处理待定
		var m = _methods[options];
		return m(this,param);
	}
	
	function _init(jq){
		var _index = _defNavIndex++;
		var _ops = _methods.getOptions(jq);
		var _back_btn = '<button id="navbtn_back" type="button" class="navbtn_back btn bg-inverse pull-left" aria-label="">'+
		'<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span></button>';
		var _title = '<div class="nav_title">'+_ops.title+'</div>';
		var _menu = '<div class="dropdown pull-right">'+
		'<button id="nav_menu_'+_index+'" class="navbar-toggler" type="button" data-toggle="dropdown">&#9776;</button>'+
		'<div class="dropdown-menu dropdown-menu-left" aria-labelledby="nav_menu_'+_index+'">';
		for(var i=0;i<_ops.menus.length;i++){
			var _m = _ops.menus[i];
			_menu += '<button class="dropdown-item nav_menu_item" type="button">'+_m.text+'</button>';
		}
		_menu += '</div></div>';
		$(jq).append(_back_btn+_title+_menu);
//		$(jq)[0].innerHTML = _back_btn+_title+_menu;
		$(jq).addClass('navbar navbar-dark navbar-static-top bg-inverse zxnav');
//		$(jq).append(_title);
//		$(jq).append(_menu);
		$(jq).append('<div class="clearfix"></div>');
		$(jq).find('.navbtn_back').off('click');
		$(jq).find('.navbtn_back').click(function(){
			_ops.back();
		});
		$(jq).find('.nav_menu_item').off('click');
		$(jq).find('.nav_menu_item').click(function(){
			var _index = $(this).index();
			var _m = _ops.menus[_index];
			_m.handle(_m);
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
//			_l = _l == ''?'<span class="label label-danger">1</span>':_l;
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
//		$(jq).find('.tabview_viewpanel').off();
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
$.fn.datashow = function(options,param){// class="'+_doColSize(this.colsize)+'
	var _def = {
			colsize:{'col-xs-':1,'col-sm-':1,'col-md-':2,'col-lg-':4,'col-xl-':6},
			adapter:function(data){return '<div style="height:200px;border:1px solid black;">'+data+'</div>';},
			onitemclick:function(data,index,list){},
			sort:function(list){},
			isScroll:false,
			onup:function(){},
			ondown:function(){}
	};
	var _methods = {
		getColSize:function(jq,data){
			var _ops = this.getOptions(jq);
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
			_methods.registerEvent(jq);
			_methods.refreshScroll(jq);
		},
		refreshScroll:function(jq){
			var _ops = this.getOptions(jq);
			if(_ops.isScroll){
//				_ops.myscroll.destroy();
//				_initScroll(jq);
				_ops.myscroll.refresh();
			}
		},
		insert:function(jq,data){
			$(jq).prepend(_methods.getView(jq, data));
			var _list = _methods.getList(jq);
			_list.unshift(data);
			$(jq).data('list',_list);
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
//			var _ops = this.getOptions(jq);
//			_ops.adapter = fun;
//			$(this).data('datashow',_ops);
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
			$(jq).html();
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
		}
	};
	if(options == null){
		options = $(this).zxOptions('options');
	}
	if(typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).addClass('row');
		$(this).after('<div class="container-fluid datashow_container"><div class="datashow_panel"></div></div>');
		$(this).appendTo($(this).next('.datashow_container').find('.datashow_panel'));
		$(this).data('datashow',_ops);
		_initScroll(this);
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
			var _myScroll = new IScroll('#datas_wapper',{probeType:3, click:true,mouseWheel:true, scrollbars:true, fadeScrollbars:true, 
				interactiveScrollbars:false, tap:true, keyBindings:false,deceleration:0.0002});
			_myScroll.on('scrollEnd', function () {
				//xia
				if(_myScroll.directionY>0){
//					$('.pull_up').hide();
					_p.find('.pull_up').removeClass('flip');
					_p.find('.pull_up').addClass('loading');
					_p.find('.pull_up .pull_up_label').text('加载中...');
					_ops.onup();
				}else if(_myScroll.directionY<0){
//					$.message(_myScroll.y+'~~~~~~'+_myScroll.maxScrollY);
//					$('.pull_down').hide();
					_p.find('.pull_down').removeClass('flip');
					_p.find('.pull_down').addClass('loading');
					_p.find('.pull_down .pull_down_label').text('刷新中...');
					_ops.ondown();
				}
				direction = false;
			});
			_myScroll.on('scroll', function () {
				if(direction){
					var direction_size = 45;
					//xia
					if(_myScroll.directionY<0){
						if(_myScroll.y > direction_size){
							_p.find('.pull_down .pull_down_label').text('释放进行刷新...');
							_p.find('.pull_down').addClass('flip');
							direction = true;
						}
						//shang
					}else if(_myScroll.directionY>0){
						if(_myScroll.y < _myScroll.maxScrollY - direction_size){
							_p.find('.pull_up .pull_up_label').text('释放进行加载...');
							_p.find('.pull_up').addClass('flip');
							direction = true;
						}
					}
				}
			});
			_ops.myscroll = _myScroll;
			var direction = false;
			_myScroll.on('scrollStart', function () {
				//xia
//				$.message(_myScroll.y+'~~~~~~'+_myScroll.maxScrollY);
				if(_myScroll.directionY<0){
					if(_myScroll.y > -5){
						_p.find('.pull_down').removeClass('loading');
						_p.find('.pull_down').show();
						_methods.refreshScroll(jq);
						_p.find('.pull_down .pull_down_label').text('下拉进行刷新...');
						direction = true;
					}
					//shang
				}else if(_myScroll.directionY>0){
					if(_myScroll.y < _myScroll.maxScrollY+5){
						_p.find('.pull_up').removeClass('loading');
						_p.find('.pull_up').show();
						_methods.refreshScroll(jq);
						_p.find('.pull_up .pull_up_label').text('上拉进行加载...');
						direction = true;
					}
					
				}
			});
			_ops.myscroll = _myScroll;
		}
		$(jq).data('datashow',_ops);
	}
//	function _add(jq,data){
//		$(jq).append(data);
//	}
//	function _doColSize(_col_size){
//		var _result = '';
//		for(var key in _col_size) _result += ' '+key+(12/v);
//		return _result;
//	}
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
	_h += '<div class="modal-dialog'+_size+'" role="document">';
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
    			_b.handel(_b.value||_b.text,_i)
    			$('#'+_id).modal('hide')
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
    $('#'+_id).on('loaded.bs.modal', function (e) {_ops.loaded(e)});
    $('#'+_id).modal(_ops);
    //延迟显示
    setTimeout("$('#"+_id+"').modal('show')",_ops.delay);
}

$.zx={
		alert:function(title,text,callback){
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
 * 表单
 */
var _defFormindex = 1;
$.fn.zxinput = function(options){
	var _def = {
			label:'',
			labelvisible:true,
			value:'',
			inline:false,
			after:'',
			before:'',
			button:{},
			colsize:{'col-xs-':1,'col-sm-':1,'col-md-':2,'col-lg-':3,'col-xl-':3},
			onblur:function(e){}
	};
	if(options == null){
		options = $(this).zxOptions();
	}
	if(typeof options == 'object'){
		var _index = _defFormindex++;
		var _id = 'zxForm_input_'+_index;
		//没有id处理一个默认id
		var id = $(this).attr('id')||'input_'+_index;
		if(id === ('input_'+_index)){
			$(this).attr('id',id);
		}
		var _ops = $.extend(_def,options);
		$(this).data('zxinput',_ops);
		//没有form时input上加一个自定义form
		var _form = $(this).parent('form');
		if(_form ==null || _form.length==0){
			$(this).after('<form class="zxForm" id="zxForm_'+_index+'"></form>');
			_form = $('#zxForm_'+_index);
		}
		//处理inline
		if(_ops.inline){
			_form.addClass('form-inline');
			_form.addClass('row');
		}
		//处理是否有前置后置框
		if($.zxUtil.isEmpty(_ops.before) && $.zxUtil.isEmpty(_ops.after) && !_ops.inline){
			_form.append('<fieldset class="form-group" id="'+_id+'"></fieldset>');
			$(this).appendTo(_form.find('#'+_id));
		}else{
			_form.append('<div class="form-group" id="'+_id+'"></div>');
			_form.find('#'+_id).append('<div class="input-group"></div>');
			$(this).appendTo(_form.find('#'+_id+' .input-group'));
			if($.zxUtil.isNotEmpty(_ops.before)){
				$(this).before('<div class="input-group-addon">'+_ops.before+'</div>');
			}
			if($.zxUtil.isNotEmpty(_ops.after)){
				$(this).after('<div class="input-group-addon">'+_ops.after+'</div>');
			}
//			$(this).addClass('col-sm-10');
			var _col_size = _ops.colsize;
			for(var key in _col_size){
				var v = _col_size[key];
				_form.find('#'+_id).addClass(key+(12/v));
			}
			_form.find('#'+_id+' .input-group-addon').off('clcik');
			_form.find('#'+_id+' .input-group-addon').click(function(){
				$('#'+id).focus();
			});
			$(this).css('width','100%');
		}
		//处理label
		var _labelClass = _ops.labelvisible?' ':'class="sr-only" ';
		var _label = ' <label '+_labelClass+'for="'+id+'">'+_ops.label+'</label>';
		_form.find('#'+_id).prepend(_label);
		//事件
		$(this).blur(function(e){
			_ops.onblur(e);
//			alert(event.srcElement.id); 
		});
	}else{
	}
}
var _zxweek = ['日','一','二','三','四','五','六'];
var _zxmonth = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
var _zxmaxDay = [31,28,31,30,31,30,31,31,30,31,30,31];
$.fn.zxdatebox = function(options,param){
//	var _def = {_first:0,
//			buttons:[{text:'确定',zxclass:'date_ok',handel:function(jq){$(jq).val($(jq).zxdatebox("getDate"));$(jq).zxpanel("hide");}},
//			            {text:'今天',zxclass:'date_today',handel:function(jq){$(jq).zxdatebox("selectDate",new Date().Format("yyyy-MM-dd"))}},
//			            {text:'取消',zxclass:'date_cancel',handel:function(jq){$(jq).zxpanel("hide")}}],
//			            clickSelect:true};
	var _def = {_first:0,clickSelect:true,showFooter:true,label:''};
	var _methods = {
			getPanel : function(jq){return $('#'+$(jq).data('zxpanelid'))||null;},
			panelTop : function(jq){return $(jq).offset().top+$(jq).height()+5;},
			panelLeft : function(jq){return $(jq).offset().left;},
			getContent : function(jq,body){
				//处理header
				var _h = '<div class="date_header"><div class="date_prev">&lt;</div>'
						+'<div class="date_now"><span class="date_month"></span>&nbsp;&nbsp;<span class="date_year"></span></div><div class="date_next">&gt;</div></div>';
				//处理日历
				_h += '<table class="date_calendar "><thead class="thead-inverse">'+
						'<tr>'+_methods.doCalendarTr(jq,_zxweek)+'</tr></thead>'+
						'<tbody>'+body+'</tbody>'+
						'</table>';
				//处理footer
				_h += '<div class="date_fotter">'+_methods.doFooter(jq,_def.buttons)+'</div>';
				return _h;
			},
			showYMD:function(jq,date){
				$(jq).zxpanel('resize',{top:_methods.panelTop(jq),left:_methods.panelLeft(jq)});
				var _val = date||$('#'+_id).val();
				_val = _val == ''?new Date().Format('yyyy-MM-dd'):_val.split(' ')[0];
//				var _a = _val.split('-');
				var _days = _methods.getMonthDay(this,_val);
				var _body = _methods.doCalendarTr(this,_days);;
				$(jq).zxpanel('setBody',_methods.getContent(jq,_body));
				if(this.isSelectMonth(jq, date)){
					this.selectDate($(jq),$(jq).val());
				}
				this.setHeader($(jq),_val);
				$(jq).zxpanel('show');
				var _panel = this.getPanel(jq);
				_panel.find('.date_prev,.date_next,.date_now').off('click');
				_panel.find('.date_prev').click(function(){
					_methods.prevMonth($(jq));
				});
				_panel.find('.date_next').on('click',function(){
					_methods.nextMonth($(jq));
				});
				_panel.find('.date_now').on('click',function(){
					_methods.showYM($(jq),_methods.getDate(jq));
					event.stopPropagation();
				});
				_methods.initSelect($(jq));
			},
			showYM:function(jq,date){
				var a = date.split('-');
				var _val = $(jq).val();
				var m = (_val != '' && _val.split('-')[0] == a[0])?_val.split('-')[1]:null;
				//处理header
				var _h = '<div class="date_header"><div class="date_prev">&lt;</div>'
						+'<div class="date_now"><span class="date_year"></span></div><div class="date_next">&gt;</div></div>';
				//处理日历
				_h += _methods.doMonthTr(jq,m);
//				return _h;
				$(jq).zxpanel('change',_h);
				var _panel = this.getPanel(jq)
				_panel.find('.date_header .date_year').text(a[0]);
				_panel.find('.date_header .date_now,.date_header .date_prev,.date_header .date_next').off('click');
				_panel.find('.date_month_panel .date_month').off('click');
				_panel.find('.date_month_panel .date_month').click(function(){
					var _t = _panel.find('.date_header .date_year').text();
					var _m = $(this).index()+1;
					_methods.showYMD(jq, _t+'-'+_m+'-01');
					event.stopPropagation();
				});
				_panel.find('.date_header .date_prev').click(function(){
					var _t = _panel.find('.date_header .date_year').text();
					_panel.find('.date_header .date_year').text(parseInt(_t)-1);
				});
				_panel.find('.date_header .date_next').click(function(){
					var _t = _panel.find('.date_header .date_year').text();
					_panel.find('.date_header .date_year').text(parseInt(_t)+1);
				});
			},
			setHeader:function(jq,date){
				var a = date.split('-');
				_methods.getPanel(jq).find('.date_header .date_year').text(a[0]);
				_methods.getPanel(jq).find('.date_header .date_year').data('value',a[0]);
				_methods.getPanel(jq).find('.date_header .date_month').text(_zxmonth[a[1]-1]);
				_methods.getPanel(jq).find('.date_header .date_month').data('value',a[1]);
			},
			doFooter:function(jq,buttons){
				var _ops = this.getOptions(jq);
				if(!_ops.showFooter || buttons == null){
					return '';
				}
				var _h = '';
				var _f =_methods.getPanel(jq).find('.date_fotter');
				for(var i=0;i<buttons.length;i++){
					var _b = buttons[i];
					_h += '<div class="'+_b.zxclass+' date_btn">'+_b.text+'</div>';
				}
				return _h;
			},
			setFooter:function(jq,buttons){
				var _f =_methods.getPanel(jq).find('.date_fotter');
				_f.html(_methods.doFooter(jq,buttons));
				_f.find('div.date_btn').off('click');
				_f.find('div.date_btn').click(function(){
					var _i = $(this).index();
					buttons[_i].handel(jq);
				});
			},
			getHeader:function(jq){
				var _h =_methods.getPanel(jq).find('.date_header');
				var year = _h.find('.date_year').data('value');
				var month = _h.find('.date_month').data('value');
				return year+'-'+month;
			},
			getDate:function(jq){
				var ym = _methods.getHeader(jq);
				var _sel = _methods.getPanel(jq).find('.date_calendar td.date_day_select');
				var d = (_sel != null && _sel.length >0 )?_sel.find('span').text():'01';
				return ym+'-'+d;
			},
			getMonthDay:function(jq,date){
				var d = new Date();
				var a = date.split('-');
				d.setFullYear(a[0],a[1]-1,1);
				var week = d.getDay();
				//获取本月最大天数
				var _max = $.zxUtil.getMonthMaxDay(a[0],a[1]);
				var _result = new Array();
				//获取上个月天数
				_prevMax = $.zxUtil.getMonthMaxDay((a[1]-1)<=0?(a[0]-1):a[0],(a[1]-1)<=0?12:(a[1]-1));
				//处理应该显示显示的上月日期
				for(var j=0;j<week;j++) _result.push(_prevMax-week+j+1);
				//处理应该显示的本月日期
				for(var i=0;i<_max;i++) _result.push(i+1);
				//处理应该处理的下月日期
				for(var i=0;_result.length<42;i++) _result.push(i+1);
				return _result;
			},
			doCalendarTr:function(jq,_days){
				var _body = '';
				var _isThis = false;
				for(var i=0;i<_days.length;i++){
					//第一天为行开始
					if(i%7==0) _body+='<tr>';
					//处理是否为本月日期
					if(_days[i] == 1) _isThis = !_isThis;
					if(isNaN(_days[i])) _isThis = true;
					var _c = _isThis?'date_day_thismonth ':'date_day_othermonth ';
					//周日、周六为休息日
					if(i%7==0 || i%7==6) _c += 'dtae_day_rest ';
					_body += '<td class="'+_c+'"><span>'+_days[i]+'</span></td>'
					//第七天为行结束
					if(i%7==6) _body+='</tr>';
				}
				return _body;
			},
			doMonthTr:function(jq,m){
				var _h = '<div class="row date_month_panel">';
				for(var i=0;i<_zxmonth.length;i++){
					var _m = _zxmonth[i];
					_h += '<div class="col-sm-4 date_month'+(m == null || m != (i+1)?'':' bg-primary date_month_select')+'">'+_m+'</div>';
				}
				 _h +='</div>';
				 return _h;
			},
			isSelectMonth:function(jq,date){
				var a = (date||$(jq).val()).split('-');
				var b = $(jq).val().split('-');
				return ($(jq).val()!='')&&(date==null||(a[0]-b[0] == 0&&a[1]-b[1]==0));
			},
			selectDate:function(jq,date){
				var _date = this.getDate(jq);
				var a = date.split('-');
				a[0] = parseInt(a[0]);
				a[1] = parseInt(a[1]);
				var _a = _date.split('-');
				_a[0] = parseInt(_a[0]);
				_a[1] = parseInt(_a[1]);
				if(_a[0]!=a[0] || _a[1] != a[1]){
					this.setDate(jq,date);
				}
				this.selectDay(jq,a[2]);
			},
			selectDay:function(jq,day){
				this.getPanel(jq).find('tbody .date_day_thismonth').removeClass('bg-primary date_day_select');
				this.getPanel(jq).find('tbody .date_day_thismonth').eq(day-1).addClass('bg-primary date_day_select');
			},
			setDate:function(jq,date){
				this.setHeader(jq, date);
				this.setCalendar(jq, date);
				this.initSelect(jq);
			},
			setCalendar:function(jq,date){
				var _days = this.getMonthDay(jq, date);
				this.getPanel(jq).find('.date_calendar tbody').html(this.doCalendarTr(jq, _days));
				if(this.isSelectMonth(jq, date)){
					this.selectDate($(jq),$(jq).val());
				}
			},
			nextMonth:function(jq){
				var date = this.getDate(jq);
				var a = date.split('-');
				a[0] = parseInt(a[0]);
				a[1] = parseInt(a[1]);
				a[0] = a[1]+1>12?(a[0]+1):a[0];
				a[1] = a[1]+1>12?1:(a[1]+1);
				var ym = a[0]+'-'+a[1];
				this.setDate(jq, ym+'-01');
			},
			prevMonth:function(jq){
				var date = this.getDate(jq);
				var a = date.split('-');
				a[0] = parseInt(a[0]);
				a[1] = parseInt(a[1]);
				a[0] = a[1]-1<=0?(a[0]-1):a[0];
				a[1] = a[1]-1<=0?12:(a[1]-1);
				var ym = a[0]+'-'+a[1];
				this.setDate(jq, ym+'-01');
			},
			getOptions:function(jq){
				return $(jq).data('zxdatebox');
			},
			initSelect:function(jq){
				if(_def.clickSelect){
					this.getPanel(jq).find('.date_calendar tbody td').off('click');
					this.getPanel(jq).find('.date_calendar tbody td.date_day_thismonth').click(function(){
						_methods.selectDay(jq,$(this).find('span').text());
						_methods.setValue(jq,_methods.getDate(jq));
						_methods.getPanel(jq).hide();
					});
					this.getPanel(jq).find('.date_calendar tbody td.date_day_othermonth').click(function(){
						var day = $(this).find('span').text();
						if(day>15){
							_methods.prevMonth(jq);
						}else{
							_methods.nextMonth(jq);
						}
						_methods.selectDay(jq,day);
						_methods.setValue(jq,_methods.getDate(jq));
						_methods.getPanel(jq).hide();
					});
				}
			},
			setValue:function(jq,date){
				var dt = date.split(' ');
				var d = dt[0].split('-');
				$(jq).val(d[0]+'-'+(d[1].length==1?('0'+d[1]):d[1])+'-'+(d[2].length==1?('0'+d[2]):d[2]));
			}
	};
	var _id ;
	if(options == null || typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).zxSetOptions('dateoptions',_ops);
		$(this).data('zxdatebox',_ops);
		//处理input
		var _input_ops = {label:_ops.label,labelvisible:true,value:'',inline:true,
				after:'日期',colsize:{'col-xs-':1,'col-sm-':1,'col-md-':2,'col-lg-':3,'col-xl-':3}
		};
		$(this).zxinput(_input_ops);
		//处理panel
		var _w = $(this).parent('div.input-group').width();
		var _h = $(this).height();
		var _offset  = $(this).offset();
		_id = $(this).attr('id');
		var _def_width = _w-4;
//		var _def_height = 220;
		var _def_height = 'auto';
		var _panel_ops = {top:_offset.top+_h+5,left:_offset.left,width:_def_width,height:_def_height,
				onclickoutside:function(jq,e,s){if($(jq).attr('id') != $(s).attr('id') && $(jq).parent('.input-group').find('.'+$(s).attr('class')).length<=0){$(jq).zxpanel('hide');}}};
		var _panel = $(this).zxpanel(_panel_ops);
		//添加日历按钮事件
		$(this).next('.input-group-addon').off('click');
		$(this).next('.input-group-addon').on('click',function(){
			if($.zxUtil.zxIsShow(_panel)){
				$('#'+_id).zxpanel('hide');
			}else{
				var jq = $('#'+_id);
				_methods.showYMD(jq);
			}
		});
		
		return $(this);
	}else if(typeof options == 'string'){
		var m = _methods[options];
		return m(this,param);
	}
}

var _defPanelindex = 1;
$.fn.zxpanel = function(options,param){
	var _def = {
			top:0,
			left:0,
			width:100,
			height:100,
			anim:true,
			body:'',
            onclickoutside:function(jq,e,source){$(jq).zxpanel('hide');}
	};
	var _methods = {
			getPanel : function(jq){return $('#'+$(jq).data('zxpanelid'))||null;},
			show : function(jq){var o = _methods.getPanel(jq);if(o != null && o.length == 1) {_methods.registerEvent(jq);o.show();} else $(this).zxpanel();},
			hide : function(jq){var o = _methods.getPanel(jq);if(o != null && o.length == 1) o.hide();$('body').off('click');},
			clear : function(jq){var o = _methods.getPanel(jq);if(o != null && o.length == 1) o.html('');},
			close : function(jq){var o = _methods.getPanel(jq);if(o != null && o.length == 1) o.remove();},
			alert : function(jq){alert(1);},
			resize : function(jq,s){_methods.getPanel(jq).css(s);},
			getOptions:function(jq){
				return $(jq).data('zxpanel');
			},
			setBody : function(jq,b){_methods.getPanel(jq).html('<div class="panelcontent">'+b+'</div>')},
			change : function(jq,p){
				_methods.getPanel(jq).append('<div class="panelcontent">'+(p.body||p)+'</div>');
				if(p.inAnim != null){
					_methods.getPanel(jq).find('.panelcontent:last').anim(p.inAnim);
				}
				if(p.outAnim != null){
					_methods.getPanel(jq).find('.panelcontent:first').anim(p.outAnim,function(){_methods.getPanel(jq).find('.panelcontent:first').remove();});
				}else{
					_methods.getPanel(jq).find('.panelcontent:first').remove();
				}
			},
			registerEvent:function(jq){
				$('body').off('click');
				$('body').click(function(e){{
					if($.zxUtil.zxIsShow(_methods.getPanel(jq)) && ($(event.srcElement).attr('id') != $(jq).data('zxpanelid') && $(event.srcElement).parents('#'+$(jq).data('zxpanelid')).length<=0)){
						_methods.getOptions(jq).onclickoutside(jq,e,$(event.srcElement)); 
					};
				}});
			}
	};
	if(options == null){
		options = $(this).zxOptions('paneloptions');
	}
	var _id;
	if(typeof options == 'object'){
		var _ops = $.extend(_def,options);
		$(this).zxSetOptions('paneloptions',_ops);
		$(this).data('zxpanel',_ops);
		_id = $(this).data('zxpanelid');
		if($.zxUtil.isEmpty(_ops.before)){
			var _index = _defPanelindex++;
			_id = 'zxpanel_'+_index;
			$(this).data('zxpanelid',_id);
		}
		var _h = '<div id="'+_id+'" class="zxpanel"></div>';
		$('body').append(_h);
		$('#'+_id).width(_ops.width);
		$('#'+_id).height(_ops.height);
		$('#'+_id).offset({top:_ops.top,left:_ops.left});
//		_methods.registerEvent($(this));
		return $('#'+_id);
	}else if(typeof options == 'string'){
		var m = _methods[options];
		return m(this,param);
	}
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
