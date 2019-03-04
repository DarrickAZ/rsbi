<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>睿思BI|数据查询</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../resource/css/bireport.css" />
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/rsdetail.js?v2"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../ext-res/js/echarts.min.js"></script>
<script type="text/javascript" src="../ext-res/My97DatePicker/WdatePicker.js"></script>
</head>

<script language="javascript">
	<%
String pageInfo = (String)request.getAttribute("pageInfo");
if(pageInfo == null){
	%>
	var pageInfo = {};
	var isnewpage = true;
	<%
}else{
%>
	var pageInfo = <%=pageInfo%>;
	var isnewpage = false;
<%}%>
	var curTmpInfo = {"view":false, isupdate: false}; //临时对象
	$(function(){
		initpage();
	});

</script>
<style>
.hidebody {
	overflow:hidden;
}
</style>

<body class="gray-bg">
<bi:mobileTop/>
<nav class="navbar navbar-default animated fadeInDown" role="navigation" style="margin-bottom:0px;">
    <div>
        <!--向左对齐-->
        <ul class="nav navbar-nav navbar-left">
		<li class="dropdown">
        	<a href="#"  class="dropdown-toggle" data-toggle="dropdown">
            	<i class="fa fa-file"></i>文件
                <b class="caret"></b>
            </a>
        	<ul class="dropdown-menu">
                <li><a href="javascript:openreport(false);">打开</a></li>
                <li><a href="javascript:newpage(false);">新建</a></li>
                <li><a href="javascript:savepage(false);">保存</a></li>
				<li><a href="javascript:saveas(false);">另存</a></li>
            </ul>
        </li>
		<li class="dropdown">
        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
            	<i class="fa fa-file-excel-o"></i>导出
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <li><a href="javascript:exportPage('html');">HTML</a></li>
                <li><a href="javascript:exportPage('csv');">CSV</a></li>
                <li><a href="javascript:exportPage('excel');">EXCEL</a></li>
                <li><a href="javascript:exportPage('word');">WORD</a></li>
				<li><a href="javascript:exportPage('pdf');">PDF</a></li>
            </ul>
        </li>
        </ul>
    </div>
</nav>

<div class="wrapper wrapper-content">
<div class="row">
<div class="col-sm-3">
	<div class="ibox" style="margin-bottom:0px;">
		<div class="ibox-title">
			<h5>数据</h5>
		</div>
		<div class="ibox-content" style="padding:0px;">
			<div style="padding:15px 20px 0px 20px">
			<button class="btn btn-primary btn-block" onclick="showdropmenu(this)">选择表<span class="caret"></span></button>
			</div>
			<ul id="selectdatatree" class="easyui-tree" style="margin:5px 0px 0px 5px;overflow:auto;"></ul>
		</div>
	</div>
</div>
<div class="col-sm-9 animated fadeInRight">
	<div class="ibox">
		<div class="ibox-content" id="p_param" style="padding:5px;">
			<div class="ptabhelpr">拖拽维度到此处作为筛选条件</div>
		</div>
	</div>

	<div class="tabs-container">
		<ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" idx="1" href="#tab-1" aria-expanded="true"> 列表 </a>
			</li>
			<p class="navbar-form navbar-right" role="search">
				<button type="button" class="btn btn-default btn-xs btn-outline" onclick="cleanData()">清除数据</button>
			</p>

		</ul>

	</div>
	
	<div class="tab-content">
		<div id="tab-1" class="tab-pane active">
			<div class="panel-body">
				<div id="T2" class="comp_table" style="margin:10px; overflow:auto;"></div>
			</div>
		</div>
	</div>
</div>
</div>
</div>

	<!--
<div id="modelinfo" style="width:150px;">
    <div onclick="fastmodel()" >快速建模...</div>
    <div onclick="newdataset()" >创建数据集...</div>
</div>

<div id="insertdsinfo" style="width:150px;">
		<div onclick="newdatasource(false)" >创建数据源...</div>
		<div onclick="newdataset()" >创建数据集...</div>
</div>
-->
<div id="pdailog"></div>
<!-- 数据 操作菜单 -->
<div id="mydatasetmenu" class="easyui-menu">
	<div id="dataset_add" onclick="newdatactx()">新建...</div>
	<div id="dataset_mod" onclick="editmydata()">编辑...</div>
	<div id="dataset_del" onclick="deletemydata()">删除</div>
</div>
<div id="gridoptmenu" class="easyui-menu">
    <div>
    <span>排序</span>
    <div style="width:120px;">
    	<div id="col_ord1" onclick="gridColsort('asc')">升序</div>
        <div id="col_ord2"  onclick="gridColsort('desc')">降序</div>
        <div id="col_ord3" iconCls="icon-ok" onclick="gridColsort('')">默认</div>
    </div>
    </div>
    <div>
    <span>移动</span>
    <div style="width:120px;">
    	<div iconCls="icon-back" onclick="tableColmove('left')">左移</div>
        <div iconCls="icon-right" onclick="tableColmove('right')">右移</div>
    </div>
    </div>
    <div onclick="delTableCol()" iconCls="icon-remove">删除</div>
</div>
<div id="selecttablemenu" class="easyui-menu" style="width:180px;">
	<div onclick="fastmodel()">单个表</div>
    <div onclick="newdataset()">多个表</div>
	<div onclick="editdataset()">编辑当前表</div>
</div>
</body>
</html>