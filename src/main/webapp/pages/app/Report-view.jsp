<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>睿思BI - 报表展现</title> 
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <script language="javascript" src="../ext-res/js/jquery.min.js"></script>
    <script type="text/javascript" src="../ext-res/js/echarts.min.js"></script>
    <script language="javascript" src="../ext-res/js/ext-base.js?v2"></script>
    <script language="javascript" src="../ext-res/js/sortabletable.js"></script>
    <script type="text/javascript" src="../ext-res/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../ext-res/js/jquery.resizeend.min.js"></script>
	<script type="text/javascript" src="../ext-res/js/echarts-wordcloud.min.js"></script>
  </head>
 <style>
table.r_layout {
	table-layout:fixed;
	width:100%;
}
table.r_layout td.layouttd {
	padding:10px;
}
.inputform2 {
	width:120px;
}
.inputtext {
	width:90px;
}
</style>
<script>
$(function(){
	//注册resize调整图形事件
	$(window).on("resizeend", function(e){
		$("div.chartUStyle").each(function(index, element) {
			var id = $(this).attr("id");
			id = id.substring(1, id.length);
			var chart = echarts.getInstanceByDom(document.getElementById(id));
			chart.resize($("#C"+id).width(), $("#C"+id).height());
		});
	});
});
</script>
<body class="gray-bg">
${str}
</body>
</html>