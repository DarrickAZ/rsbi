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
<title>注册已有表</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/etl.js?v2"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</head>
<script language="javascript">
function delCustom(){
	var row = $("#cfglist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	row = row[0];
	var data = row;
	var tid = row.tableId;
	var tname = row.tableName;
	if(confirm("是否确认删除？")){
		$.ajax({
			url:"delRegTable.action",
			type:"GET",
			data:{tableId:tid,tableName:tname},
			dataType:"json",
			success:function(){
				$('#cfglist').bootstrapTable('refresh',{
					t:Math.random()
				});
			}
		});
	}
}
function flash(){
	var row = $("#cfglist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	row = row[0];
	var tid = row.tableId;
	var tname = row.tableName;
	__showLoading();
	$.ajax({
		type:"post",
		url:"flashRegTable.action",
		dataType:"json",
		data:{tableId:tid,tableName:tname},
		success:function(resp){
			__hideLoading();
			$.messager.alert("提示框","刷新成功。", "info");
		},
		error:function(){
			__hideLoading();
		}
	});
}
</script>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">
	  <div class="row">
				<div class="col-sm-12">
	
		<div class="ibox">
				<div class="ibox-title">
					<h5>注册已有表</h5>
				</div>

				<div class="ibox-content">
				<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
					<button type="button" class="btn btn-outline btn-default" title="新增" onclick="newCustom(false)">
						<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="刷新" onclick="flash()">
						<i class="glyphicon glyphicon-refresh" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delCustom()">
						<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
					</button>
				</div>
			<table id="cfglist" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="loadRegTable.action" data-side-pagination="server">
				<thead>
					<tr>
						<th data-radio="true"></th>
						<th data-field="tableId" data-align="center">标识</th>
						<th data-field="tableName">名称</th>
						<th data-field="tableNote">说明</th>
						<th data-field="crtDate" data-align="center">创建时间</th>
						<th data-field="crtUserName" data-align="center">创建人</th>
					</tr>
				</thead>
			</table>
				
				</div>
		</div>

 </div>
 </div>
</div>
</div>
<div id="pdailog"></div>
</body>
</html>