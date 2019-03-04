<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>ETL工具</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/etl.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</head>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">
  
<div class="tabs-container">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" idx="1" href="#tab-1" aria-expanded="true">导入表</a></li>
		<li><a data-toggle="tab" idx="2" href="#tab-2" aria-expanded="false">转换表</a></li>
		<li><a data-toggle="tab" idx="4" href="#tab-4" aria-expanded="false">填报表</a></li>
		<li><a data-toggle="tab" idx="5" href="#tab-5" aria-expanded="false">自定义</a></li>
	</ul>
</div>
<div class="tab-content">
	<div id="tab-1" class="tab-pane active">
		<div class="panel-body" style="padding:10px;">
			<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
				<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="viewtable(1)">
					<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
				</button>
				<button type="button" class="btn btn-outline btn-default" title="查询数据" onclick="mxsearch(1)">
					<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
				</button>
				<button type="button" class="btn btn-outline btn-default" title="删除" onclick="deltable()">
					<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
				</button>
			</div>
			<table id="tablegrid" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="loadTable.action?income=etl" data-side-pagination="server">
				<thead>
					<tr>
						<th data-radio="true" data-align="center"></th>
						<th data-field="tableId" data-align="center">标识</th>
						<th data-field="tableName" data-sortable="true">名称</th>
						<th data-field="tableNote">说明</th>
						<th data-field="crtDate" data-align="center" data-sortable="true">创建时间</th>
						<th data-field="crtUserName" data-align="center">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="tab-2" class="tab-pane">
		<div class="panel-body" style="padding:10px;">
			<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
				<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="viewtable(2)">
					<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
				</button>
				<button type="button" class="btn btn-outline btn-default" title="查询数据" onclick="mxsearch(2)">
					<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
				</button>
			</div>
			<table id="tablegrid2" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="loadByIncomes.action?income=tf,r2c,sql" data-side-pagination="server">
				<thead>
					<tr>
						<th data-radio="true" data-align="center"></th>
						<th data-field="tableId" data-align="center">标识</th>
						<th data-field="tableName" data-sortable="true">名称</th>
						<th data-field="tableNote">说明</th>
						<th data-field="crtDate" data-align="center" data-sortable="true">创建时间</th>
						<th data-field="crtUserName" data-align="center">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="tab-4" class="tab-pane">
		<div class="panel-body" style="padding:10px;">
			<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
				<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="viewtable(4)">
					<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
				</button>
				<button type="button" class="btn btn-outline btn-default" title="查询数据" onclick="mxsearch(4)">
					<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
				</button>
			</div>
			<table id="tablegrid4" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="loadTable.action?income=dw" data-side-pagination="server">
				<thead>
					<tr>
						<th data-radio="true" data-align="center"></th>
						<th data-field="tableId" data-align="center">标识</th>
						<th data-field="tableName" data-sortable="true">名称</th>
						<th data-field="tableNote">说明</th>
						<th data-field="crtDate" data-align="center" data-sortable="true">创建时间</th>
						<th data-field="crtUserName" data-align="center">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div id="tab-5" class="tab-pane">
		<div class="panel-body" style="padding:10px;">
			<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
				<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="viewtable(5)">
					<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
				</button>
				<button type="button" class="btn btn-outline btn-default" title="查询数据" onclick="mxsearch(5)">
					<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
				</button>
			</div>
			<table id="tablegrid5" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="loadTable.action?income=custom" data-side-pagination="server">
				<thead>
					<tr>
						<th data-radio="true" data-align="center"></th>
						<th data-field="tableId" data-align="center">标识</th>
						<th data-field="tableName" data-sortable="true">名称</th>
						<th data-field="tableNote">说明</th>
						<th data-field="crtDate" data-align="center" data-sortable="true">创建时间</th>
						<th data-field="crtUserName" data-align="center">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

		

					

</div>
<div id="pdailog"></div>

</body>
</html>