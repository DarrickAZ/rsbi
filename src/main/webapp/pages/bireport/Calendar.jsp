<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="ext" uri="/WEB-INF/ext-runtime.tld" %>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日历控件</title>
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css"/>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>  
</head>

<script language="javascript">
function cb(e, ts, val){
	e = e||window.event; 
	if (e && e.preventDefault) {//如果是FF下执行这个
		e.preventDefault();
	}else{ 
		window.event.returnValue = false;//如果是IE下执行这个
	}
	var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;       
	var scrollY = document.documentElement.scrollTop || document.body.scrollTop; 
	var x = e.pageX || e.clientX + scrollX;       
	var y = e.pageY || e.clientY + scrollY;
	window.curVal = val;
	$("#taskmenu").menu('show', {
		left: x,
		top: y
	});
}
function addjr(){
	var d = window.curVal;
	var year = d.substring(0, 4);
	var month = d.substring(4, 6);
	var day = d.substring(6, 8);
	var ctx= "<div style='margin:10px;'>日期：" + year +"年" + month  +"月"+ day+ "日" + "<br/><br/>" + "节日：<input type='text' id='jr' size='30'><br/><br/><input type='radio' name='type' value='more' checked>每年今天 <input type='radio' name='type' value='one'>只是今天</div>";
	$("#pdailog").dialog({
		title: '标注节日',
		closed: false,
		cache: false,
		modal: true,
		fit:false,
		closed: false,
		width:300,
		height:200,
		content:ctx,
		buttons:[{
			text:'确定',
			iconCls:"icon-ok",
			handler:function(){
				var jr = $("#pdailog #jr").val();
				var tp = $("#pdailog input[name=\"type\"]:checked").val();
				if(jr == ""){
					alert("请录入节日信息。");
					return;
				}
				$.ajax({
					   type: "POST",
					   url: "addjr.action",
					   dataType:"json",
					   data: {day:d,festival:jr, type:tp},
					   success: function(resp){
						   getCalendar('mycalendar', d);
					   }
				});
				$('#pdailog').dialog('close');
			}
		},{
			text:'取消',
			iconCls:"icon-cancel",
			handler:function(){
				$('#pdailog').dialog('close');
			}
		}]
	});
}
function removejr(){
	var d = window.curVal;
	if(confirm("是否确认删除？")){
		$.ajax({
			   type: "POST",
			   url: "removejr.action",
			   dataType:"json",
			   data: {day:d},
			   success: function(resp){
				   getCalendar('mycalendar', d);
			   }
		});
	}
}
</script>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>日历管理</h5>
                    </div>

					<div class="ibox-content">

<p class="text-warning">通过日历自定义节日。自定义的节日可以在多维分析中使用和展现。</p>
<div id="mycalendar">
 <ext:calendar divId="mycalendar" callback="cb"/>
</div>
<div align="left" class="text-warning">在日历上点击鼠标标注/删除节日</div>

</div>

</div>
 </div>
 </div>
</div>

<div id="taskmenu" class="easyui-menu">
    <div onclick="addjr()" iconCls="icon-add" >标注节日</div>
    <div onclick="removejr()" iconCls="icon-remove">删除节日</div>
</div>
<div id="pdailog"></div>
</body>
</html>