/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
function msginfo(info, type, func){
	if(!type){
		type = "error";
	}
	swal({
		title:"",
		text:info,
		type: type,
		confirmButtonColor: "#27c24c",
		confirmButtonText:"确定"
	},function(){
		if(func){
			func();
		}
	});
}
/**
jquery easyUI 回调函数
*/
function fmtdt(value,row,index){
	var myDate = new Date(value.time);
	return myDate.getFullYear() + "-" + (myDate.getMonth()+1) + "-" + myDate.getDate();
}
function formatDate(dt, fmt){
	var date = new Date(dt.time);
	 var o = { 
		"M+" : date.getMonth()+1,                 //月份 
		"d+" : date.getDate(),                    //日 
		"h+" : date.getHours(),                   //小时 
		"m+" : date.getMinutes(),                 //分 
		"s+" : date.getSeconds(),                 //秒 
		"q+" : Math.floor((date.getMonth()+3)/3), //季度 
		"S"  : date.getMilliseconds()             //毫秒 
	}; 
	if(/(y+)/.test(fmt)) {
			fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	}
	 for(var k in o) {
		if(new RegExp("("+ k +")").test(fmt)){
			 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
		 }
	 }
	return fmt; 
}
function __showLoading(){
	var sload = $('#loadingdiv');
	if(sload.size() == 0){
		sload = $('<div id="loadingdiv" class="sk-spinner sk-spinner-three-bounce" style="position:absolute;z-index:9999"><div class="sk-bounce1"></div><div class="sk-bounce2"></div><div class="sk-bounce3"></div></div>').appendTo('body');
		window.loadCompCnt = 1;	
	}else{
		window.loadCompCnt =  window.loadCompCnt + 1;
	}
	var doc = $(document);
	var win = $(window);
	var t = doc.scrollTop() + win.height()/2 - 50;
	var l = doc.scrollLeft() + win.width()/2 - 50;
	sload.css({'top':t, 'left':l});
	sload.show();
}
function __hideLoading(){
	window.loadCompCnt =  window.loadCompCnt - 1;
	if(window.loadCompCnt == 0){
		$("#loadingdiv").remove();
		delete window.loadCompCnt;
	}
}
/**
配置气泡大小
转换到 10 到 50
**/
function bubbleSize(maxval, minval, val, targetMax){
	if(maxval == minval){
		return 40;
	}
	if(!targetMax){
		targetMax = 50;
	}
	var r = (targetMax-10)/(maxval-minval)*(val-minval)+10;
	return r;
}
function formatNumber(num,pattern, shortname){
 if(!pattern || pattern.length == 0){
 	return num;
 }
 var shortdw;
  if(shortname && num > 100000000){
	 num = num / 100000000;
	 shortdw = "亿";
  }else if(shortname && num > 10000000){
	 num = num / 10000000;
	 shortdw = "千万";
  }else if(shortname && num > 1000000){
	 num = num / 1000000;
	 shortdw = "百万";
  }else if(shortname && num > 10000){
	  num = num / 10000;
	  shortdw = "万";
  }else if(shortname && num > 1000){
	  num = num / 1000;
	  shortdw = "千";
  }
  if(pattern.indexOf("%") <= 0 && shortname){
	  return (Math.round(num * 10) / 10) + (shortdw?shortdw:"");
  }
  if(pattern.indexOf("%") > 0){
	  num = num * 100;
  }
  var fmtarr = pattern?pattern.split('.'):[''];
  var retstr='';
  
  //先对数据做四舍五入
  var xsw = 0;
  if(fmtarr.length > 1){
	  xsw = fmtarr[1].length;
  }
  var bl = 1;
  for(i=0; i<xsw; i++){
	  bl = bl * 10;
  }
  num = num * bl;
  num = Math.round(num);
  num = num / bl;
  
  var strarr = num?num.toString().split('.'):['0'];
 
  // 整数部分
  var str = strarr[0];
  var fmt = fmtarr[0];
  var i = str.length-1;  
  var comma = false;
  for(var f=fmt.length-1;f>=0;f--){
    switch(fmt.substr(f,1)){
      case '#':
        if(i>=0 ) retstr = str.substr(i--,1) + retstr;
        break;
      case '0':
        if(i>=0) retstr = str.substr(i--,1) + retstr;
        else retstr = '0' + retstr;
        break;
      case ',':
        comma = true;
        retstr=','+retstr;
        break;
    }
  }
  if(i>=0){
    if(comma){
      var l = str.length;
      for(;i>=0;i--){
        retstr = str.substr(i,1) + retstr;
        if(i>0 && ((l-i)%3)==0) retstr = ',' + retstr; 
      }
    }
    else retstr = str.substr(0,i+1) + retstr;
  }

  retstr = retstr+'.';
  // 处理小数部分
  str=strarr.length>1?strarr[1]:'';
  fmt=fmtarr.length>1?fmtarr[1]:'';
  i=0;
  for(var f=0;f<fmt.length;f++){
    switch(fmt.substr(f,1)){
      case '#':
        if(i<str.length) retstr+=str.substr(i++,1);
        break;
      case '0':
        if(i<str.length) retstr+= str.substr(i++,1);
        else retstr+='0';
        break;
    }
  }

  var r = retstr.replace(/^,+/,'').replace(/\.$/,''); 
  if(pattern.indexOf("%") > 0){
	  r = r + "%";
  } 
  if(shortdw){
	  r = r + shortdw;
  }
  return r;
}
function getCalendar(divId, dt, minval, maxval){
	var url = 'CalendarView.action';
	$("#"+divId).load(url, {dt:dt, "max":maxval, "min":minval});
}
/**
日历提交
**/
function calendarPost(event, ts,value, cb){
	$("table.calen td").removeClass("curdt");
	$(ts).parent().addClass("curdt");
	if(cb){
		cb(event, ts, value);
	}
}
function selectyearmonth(){
	var isopen = $("#selyearmonth").attr("isopen");
	if(isopen && "y" == isopen){
		$("#selyearmonth").css("display","none").attr("isopen", "n");
	}else{
		$("#selyearmonth").css("display","block").attr("isopen", "y");
	}
}
function postpage(pageObj, servid, method, fromMV, subm, check, confirmState, exportDG, issubmit){
	var obj = document.forms[pageObj.formId];
	obj.elements[pageObj.sidKey].value = servid;
	obj.elements[pageObj.midKey].value = method;
	obj.elements[pageObj.fromId].value = fromMV;
	obj.elements[pageObj.exportKey].value = exportDG;
	obj.method = subm;
	if(!check){
		obj.onsubmit = null;
	}
	pageObj.needConfirm = confirmState;
	
	if(issubmit){
		//如果需要check 判断 checkRequire 是否返回 true
		if(check && checkRequire(obj)){
			obj.submit();
		}
		//如果不需要check, 直接提交
		if(!check){
			obj.submit();
		}
	}
}
/**
 * 可以点击的radio
 * @return
 */
function radiolink(url, target, divId){
	var pms = target+"="+jQuery("input[name='"+target+"']:checked").val();
	jQuery.post(url, pms, function(resp){
		if($("#p"+divId).size() > 0){
			$("#p"+divId).html(resp);
		}else{
			$("#"+divId).html(resp);
		}
		
	}, "html");
}
//分页提交
function gotopage(vf,str,cp,fromId){
	var ff = document.forms[vf.formId];
	ff.elements[cp].value=str;
	ff.elements[vf.sidKey].value = vf.sidValue;
	ff.elements[vf.midKey].value = vf.midValue;
	ff.elements[vf.fromId].value = fromId;
	ff.method = "post";
	ff.submit();
}
function keygoto(evt, pinfo, dgId, pageSize, currPage, params, fromMVId){
	evt = window.event || evt;
    if(evt.keyCode==13){//如果取到的键值是回车
         gotobyajax(pinfo, dgId, pageSize, currPage, params, fromMVId);       
     }

}

function keygoto2(evt, vf,str,cp,fromId){
	evt = window.event || evt;
    if(evt.keyCode==13){//如果取到的键值是回车
    	var ff = document.forms[vf.formId];
		ff.elements[cp].value=str;
		ff.elements[vf.sidKey].value = vf.sidValue;
		ff.elements[vf.midKey].value = vf.midValue;
		ff.elements[vf.fromId].value = fromId;
		ff.method = "post";
		//ff.submit();
     }
}
function gotobyajax(pinfo,dgId, pageSize, currPage, params, fromMVId){
	var url =  "";
	if(pinfo == null){
		url =  "../control/extControl?serviceid=ext.sys.fenye.ajax&currPage="+currPage+"&id="+dgId+"&pageSize="+pageSize+"&t_from_id="+fromMVId;
	}else{
		url = pinfo.resPath + "control/" + pinfo.extAction+"?"+pinfo.sidKey+"=ext.sys.fenye.ajax&currPage="+currPage+"&id="+dgId+"&pageSize="+pageSize+"&"+pinfo.fromId+"="+fromMVId;
	}
	__showLoading();
	jQuery.ajax({
	   type: "POST",
	   url: url,
	   dataType:"html",
	   data: params,
	   success: function(resp){
	   		__hideLoading();
		   jQuery("#" + dgId).html(resp);
	   },
	   error:function(resp){
	   		__hideLoading();
		   jQuery.messager.alert('出错了','系统出错，请联系管理员。','error');
	   }
	});
}
//检查checkBox是否勾选
function checkRadio(ff, targetId, tp){
	if (tp == 'radio') {
		var obj = ff.elements[targetId];
		if (obj == null || obj == undefined) {
			return false;
		}
		if (obj.length == undefined) {
			return obj.checked;
		}
		var isExist = false;
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked == true) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}else{
		var obj = ff.elements[targetId];
		if (obj == null || obj == undefined) {
			return false;
		}
		if(obj.value == ''){
			return false;
		}
		return true;
	}
}
/**
提交到多个组件
**/
function post2Comps(ids, urls, paramNames){
	for(k=0; k<ids.length; k++){
		var t = ids[k];
		var u = urls[k];
		post2Comp(t, u, null, paramNames);
	}
}

/**
 * 提交到一个组件
 * @param mvId
 * @param params
 * @return
 */
function post2Comp(targetId, url, ff, paramNames){
	var parms = '';
	for(var i=0; i<paramNames.length; i++){
		var p = paramNames[i];
		
		//采用jQuery方式取值
		if(jQuery('#'+p).attr('type') == 'radio'){
			var val = jQuery('input:radio[name='+p+']:checked').val();
			parms = parms +  p  + "=" + (val?val:"") + "&";
		}else if(jQuery('#'+p).attr('type') == 'checkbox'){
			jQuery("input[name='"+p+"']:checkbox:checked").each(function(){ 
				parms = parms +  p  + "=" + jQuery(this).val() + "&";
			});
		}else{
			var val = jQuery('#'+p).val();
			parms = parms +  p  + "=" + val + "&";
		}
		
		/**
		var rets = document.forms[ff.name].elements[p];
		if(rets.length == 1){
			parms = parms +  p  + "=" + rets.value+"&";
		}else{
			//是checkbox或radio
			for(var j=0; j<rets.length; j++){
				if(rets[j].checked){
					parms = parms +  p  + "=" + rets[j].value+"&";
				}
			}
		}
		**/
	}
	$("#"+targetId).html("<div align='center'><img src='../ext-res/image/large-loading.gif'></div>");
	jQuery.post(url, parms, function(resp){
		if($("#p" + targetId).size == 0){
			$("#"+targetId).html(resp);
		}else{
			$("#p"+targetId).html(resp);
		}
	}, "html");
}
/***
 * 提交到一个MV
 */
function post2MV(config){
	for(i=0; i<config.length; i++){
		__post2MV(config[i].target, config[i].url, config[i].paramNames, config[i].fname);
	}
}

function __post2MV(targetId, url, paramNames, fname){
	var parms = "";
	for(var i=0; i<paramNames.length; i++){
		var tp = paramNames[i].type;
		var p = paramNames[i].name;
		
		//采用jQuery方式取值
		if(tp == 'radio'){
			var val = jQuery('input:radio[name='+p+']:checked').val();
			if(!val){
				val = "";
			}
			parms = parms +  p  + "=" + val + "&";
		}else if(tp == 'checkBox'){
			$("input[name=\""+p+"\"]:checked").each(function(a, b){
				var v = $(b).val();
				parms = parms + p + "=" + v + "&";
			});
		}else{
			var val = null;
			if(paramNames[i].type == 'mselect'){ //多选需要特殊处理
				val = jQuery('#'+p).combobox("getValues");
				jQuery('#'+p).combobox("destroy");
			}else if(paramNames[i].type == 'tree'){  //tree类型参数特殊处理
				val = jQuery('#'+p).combobox("getValue");
				//选择完后需要destory组件, 防止以前的HTML在页面堆积
				jQuery('#'+p).combobox("destroy");
			}else{
				val = jQuery('#'+p).val();
			}
			parms = parms +  p  + "=" + val + "&";
		}
		
		/**
		var rets = document.forms[fname].elements[p];
		var tp = jQuery(rets).attr('type');
		if(tp != 'radio' && tp != 'checkbox'){
			parms = parms +  p  + "=" + rets.value+"&";
		}else{
			//是checkbox或radio
			for(var j=0; j<rets.length; j++){
				if(rets[j].checked){
					parms = parms +  p  + "=" + rets[j].value+"&";
				}
			}
		}
		**/
		
	}
	__showLoading();
	jQuery.ajax({
	   type: "POST",
	   url: url,
	   dataType:"html",
	   data: parms,
	   success: function(resp){
		   __hideLoading();
		   $(document.getElementById(targetId)).html(resp);
	   },
	   error:function(resp){
		   __hideLoading();
		   $.messager.alert('出错了','系统出错，请联系管理员。','error');
	   }
	});
}
//交叉表的body进行滚动时，header也进行滚动
function tableBodyscroll(id){
	$("#"+id+" .lock-dg-body").scroll(function(){
		var left = $(this).scrollLeft();
		$("#"+id+" .lock-dg-header").css("margin-left", "-"+left+"px");
	});
}

/**
 * 维度钻取
 * @return
 */
function fieldDirll(config){
	var fields = jQuery('#' + config.table + " td[drillid='"+config.pid+"'] .crossDirll").bind('click', function(e){
		var thiz = jQuery(this);
		if(thiz.attr('isopen') == 0){
			var tabTr = thiz.parent().css('font-weight', 'bold').parents("tr.tr-row1,tr.tr-row2");
			//获取内容
			__showLoading();
			var parms = thiz.attr('parms');
			if(config.text != undefined && config.text != ""){
				parms = parms + '&text=' + jQuery.fn.toJSON(config.text);
			}
			
			jQuery.ajax({url: config.url, data: parms, type:'POST', dataType:'html', success: function(resp){
				jQuery(resp).insertAfter(tabTr);
				__hideLoading();
			}});
			thiz.addClass('crossDirll-open');
			thiz.attr('isopen', '1');
		}else{
			__nodeRemove(thiz.parent().css('font-weight', 'normal'), config);
			thiz.removeClass('crossDirll-open');
			thiz.attr('isopen', '0');
		}
	});
}

function __nodeRemove(target, config){
	var pid = target.children('.crossDirll').attr('pid');
	jQuery('#' + config.table+' td[drillid="'+pid+'"]').each(function(a, b){
			__nodeRemove(jQuery(b), config);
			jQuery(b).parent().remove();
	});
}

/**
* 图形,表格连接
**/
function chartComp_Link(x, xval, url, pms, compId, tps, fromId){
	if(url == null || url == 'null'){
		alert("未定义接收组件。");
		return;
	}
	var tparam = pms;
	var box = $("#"+fromId).parents(".ibox-content");
	if(box.find(".eventback").size() == 0){  //创建事件返回按钮
		$("<div class=\"eventback\"><span class=\"label label-success\"><i class=\"fa fa-arrow-left\"></i>返回</span></div>").prependTo(box).click(function(){
			$(this).remove();
			var pp = {};
			var p = tparam.split("&");
			for(i=0; i<p.length; i++){
				var tmp = p[i].split("=");
				var k = tmp[0];
				var v = tmp[1];
				pp[k] = v;
			}
			for(i=0; i<url.length; i++){
				var u = url[i];
				var d = compId[i];
				__showLoading();
				
				jQuery("#"+(tps[i]=="chart"?"p":"")+d).load(u, pp, function(){
					__hideLoading();
				});
			}
		});
	}
	pms = pms + x+"="+xval;
	var pp = {};
	var p = pms.split("&");
	for(i=0; i<p.length; i++){
		var tmp = p[i].split("=");
		var k = tmp[0];
		var v = tmp[1];
		pp[k] = v;
	}
	for(i=0; i<url.length; i++){
		var u = url[i];
		var d = compId[i];
		__showLoading();
		jQuery("#"+(tps[i]=="chart"?"p":"")+d).load(u, pp, function(){
			__hideLoading();
		});
	}
}
function rowActionFireTR(config){
	var obj = $('#' + config.id);
	
	obj.on('click','.row-link', function(e){
		var o = $(this).find("a.lka");
		var pms = o.attr("parms");
		var name = o.attr("name");
		var nameDesc = o.attr("nameDesc");
		var value = o.attr("value");
		var id = obj.parents(".dashboard-box").attr("id");
		config.cb(pms, id, name, nameDesc, value);
	});
}
/**
 * 通过点击表格更新组件
 * @return
 */
function tableUpdateComp(config){
	var obj = $('#' + config.id);
	
	obj.on('click','.row-link', function(e){
		var tz = $(this);
	
		var box = $("#"+config.id).parents(".ibox-content");
		if(box.find(".eventback").size() == 0){  //创建事件返回按钮
			$("<div class=\"eventback\"><span class=\"label label-success\"><i class=\"fa fa-arrow-left\"></i>返回</span></div>").prependTo(box).click(function(){
				$(this).remove();
				for(i=0; i<config.url.length; i++){
					u = config.url[i].url;
					t = config.url[i].target;
					tp = config.url[i].type;
					var pp = {};
					var p = a.attr('parms' + i).split("&");
					for(j=0; j<p.length; j++){
						var tmp = p[j].split("=");
						var k = tmp[0];
						var v = tmp[1];
						if(k == config.linkParamName){
							continue;
						}
						pp[k] = v;
					}
					$("#"+(tp=="chart"?"p":"")+t).load(u, pp);
					
				}
			});
		}
		
		var a = tz.find('a.lka');
		if(a.size() > 0){
			$('#' + config.id + " .row-link").removeClass('link-selected');
			tz.addClass('link-selected');
			
			for(i=0; i<config.url.length; i++){
				u = config.url[i].url;
				t = config.url[i].target;
				tp = config.url[i].type;
				var pp = {};
				var p = a.attr('parms' + i).split("&");
				for(j=0; j<p.length; j++){
					var tmp = p[j].split("=");
					var k = tmp[0];
					var v = tmp[1];
					pp[k] = v;
				}
				$("#"+(tp=="chart"?"p":"")+t).load(u, pp);
				
			}
		}

	});
}
/**
 * 列上加链接
 * 连接面板在页面中只存在一个，id为compLinkPanel
 * @param url
 * @return
 */
function rowLinkFireTR(config){
	jQuery('#' + config.id + ' .row-link a.lka').bind('click', function(e){
		var tz = jQuery(this);
		if(config.type == 'open'){
			var panel = null;
			if(Ext.get('compLinkPanel') == null){
		    	//创建面板
				panel = new Ext.Window({
		    		id: 'compLinkPanel',
		    		title: '指标分析',
		    		renderTo: document.body,
		    		layout: {type: 'absolute'},
		    		width: 590,
		    		height: 410,
		    		draggable: true,
		    		resizable: false,
		    		closeAction : 'hide',
		    		shadow: false,
		    		autoScroll : true,
		    		html: "<div id='compLinkPanelctx'></div>"
		    	});
		    	panel.render();
		    }else{
		    	panel = Ext.getCmp('compLinkPanel');
		    }
		    panel.show();
		}else if(config.type == 'new'){
			location.href = config.url;
			return;
		}else{
			var tabTr = tz.parent().parent();
			jQuery('#compLinkPanelctxTr').remove();
			jQuery("<tr class='row-link' id='compLinkPanelctxTr'><td colspan='"+config.colspan+"'><div class='linkPanelClose' id='linkPanelClose'></div><div id='compLinkPanelctx'></div></td></tr>").insertAfter(tabTr);
			jQuery('#linkPanelClose').bind('click', function(){
				jQuery('#compLinkPanelctxTr').remove();
			});
		}
		jQuery("#compLinkPanelctx").load(config.url, tz.attr('parms'));
	});
}
function tableCell2MV(config){
	var obj = jQuery('#' + config.id);
	obj.on('click', ".cell-link", function(e){
			var tz = jQuery(this);
			location.href = config.url  + '&'+  tz.attr('parms');
	});
}
//全选
function selectAll(ts, cid){
	if(ts.checked == true){
		 $("input[name="+cid+"]").attr("checked",true).prop('checked', true);
	}else{
		$("input[name="+cid+"]").attr("checked",false);
	}
}
function exportXLS(url){
	location.href=  url;
}
function tableCellUpdateComp(config){
	var obj = $('#' + config.id);
	obj.on('click', '.cell-link', function(e){
		var tz = jQuery(this);
		for(i=0; i<config.url.length; i++){
			url = config.url[i].url;
			t = config.url[i].target;
			tp = config.url[i].type;
			
			$.post(url,  tz.attr('parms'), function(resp){
				$("#"+(tp=="chart"?"p":"")+t).html(resp);
			}, "html");
		}
	});
}
//table的head排序操作
function extColOrder(dgId, order, fromMVId, params){
	if(jQuery('#ext_order_state').val() == 'a'){
		jQuery('#ext_order_state').val('d');
	}else{
		jQuery('#ext_order_state').val('a');
	}
	var url = "extControl?serviceid=ext.sys.fenye.ajax&ext_col_order="+order+"&id="+dgId+"&ext_order_state="+jQuery('#ext_order_state').val()+"&t_from_id="+fromMVId;
	jQuery.post(url, params, function(resp){
		jQuery("#"+dgId).html(resp);
	}, "html");
}
/**
saveBtn 是否添加 保存按钮
**/
function openModelDailog(title, ctx, saveBtn){
	if($("#modeldailog_div").size() > 0){
		$("#modeldailog_div").remove();
	}
	if($("#modeldailog_div").size() == 0){
		var str = "<div class=\"modal inmodal fade\" id=\"modeldailog_div\" role=\"dialog\"  aria-hidden=\"true\"><form id=\"dailogform\" method=\"post\" class=\"form-horizontal\"><div class=\"modal-dialog modal-md\"><div class=\"modal-content\"><div class=\"modal-header\"><button type=\"button\" class=\"close\" data-dismiss=\"modal\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button><h4 class=\"modal-title\">"+title+"</h4></div><div class=\"modal-body\" style=\"min-height:200px;\">"+ctx+"</div><div class=\"modal-footer\">"+(saveBtn?"<button class=\"btn btn-info\" >确定</button>":"")+" <button type=\"button\" class=\"btn btn-white\" data-dismiss=\"modal\">关闭</button></div></div></div></form></div>";
		$(str).appendTo("body");
	}
	$("#modeldailog_div").modal("show");
	
}