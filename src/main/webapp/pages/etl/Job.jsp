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
<title>任务管理</title>
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

 <div class="row">
				<div class="col-sm-12">
				
				<div class="ibox">
				<div class="ibox-title">
					<h5>任务管理</h5>
				</div>

				<div class="ibox-content">
				<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
					<button type="button" class="btn btn-outline btn-default" title="新增" onclick="savejobs()">
						<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="editjob()">
						<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="立即执行" onclick="run_job()">
						<i class="glyphicon glyphicon-repeat" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="启动" onclick="startjob()">
						<i class="glyphicon glyphicon-play" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="停止" onclick="stopjob()">
						<i class="glyphicon glyphicon-stop" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="删除" onclick="deljob()">
						<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="任务日志" onclick="logs()">
						<i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>
					</button>
				</div>
				<table id="tablegrid" data-toggle="table" data-click-to-select="true" data-url="listJob.action">
					<thead>
						<tr>
							<th data-radio="true"></th>
							<th data-field="id">标识</th>
							<th data-field="name">名称</th>
							<th data-field="state" data-formatter="fmtstate" data-align="center">状态</th>
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

<script>
function fmtstate(v, row){
	if(v == 1){
		return "已停止";
	}else if(v == 2){
		return "<font color='red'>运行中</font>";
	}
}
function run_job(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	 __showLoading();
	 $.ajax({
		type:"post",
		url:"execJob.action",
		dataType:"json",
		data:{id: row.id},
		success:function(resp){
			__hideLoading();
			if(resp.result == 1){
				$.messager.alert("提示信息","执行成功。","info");
			}else{
				$.messager.alert("出错了。",resp.msg, "error");
			}
		},
		error: function(){
			__hideLoading();
			$.messager.alert("出错了。","系统出错，请查看后台日志。", "error");
		}
	 });
}
function editjob(){
	 var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	 if(row.state == 2){
		 viewjobs(row.id);
		 return;
	 }
	 savejobs(true, row.id);
}
function startjob(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	 if(row.state == 2){
		 $.messager.alert("出错了","任务已经启动。","error");
		 return;
	 }
	if(confirm("是否启动？")){
		$.ajax({
			type:"post",
			url:"runJob.action",
			dataType:"json",
			data:{id: row.id},
			success:function(){
				$("#tablegrid").bootstrapTable("refresh", {t:Math.random()});
			},
			error: function(){
				$.messager.alert("出错了。","系统出错，请联系管理员。", "error");
			}
		});
	}
}
function stopjob(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	 if(row.state == 1){
		 $.messager.alert("出错了","任务已经停止。","error");
		 return;
	 }
	if(confirm("是否停止？")){
		$.ajax({
			type:"post",
			url:"stopJob.action",
			dataType:"json",
			data:{id: row.id},
			success:function(){
				$("#tablegrid").bootstrapTable("refresh", {t:Math.random()});
			},
			error: function(){
				$.messager.alert("出错了。","系统出错，请联系管理员。", "error");
			}
		});
	}
}
function deljob(){
	 var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	  if(row.state == 2){
		 $.messager.alert("出错了","运行中的任务不能删除。","error");
		 return;
	 }
	 if(confirm('是否确认删除？')){
		 $.ajax({
			type:"post",
			url:"delJob.action",
			dataType:"json",
			data:{id: row.id},
			success:function(){
				$("#tablegrid").bootstrapTable("refresh", {t:Math.random()});
			},
			error: function(){
				$.messager.alert("出错了。","删除任务出错。", "error");
			}
		 });
	 }
}
function logs(){
	 var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	var ctx = "<iframe frameborder=\"0\" src='../control/extView?mvid=frame.Logs&job_id="+row.id+"' width='100%' height='100%'></iframe>";
	$('#pdailog').dialog({
		title: '任务日志',
		width:700,
		height: 360,
		closed: false,
		cache: false,
		modal: true,
		content: ctx,
		buttons:[{
				text:'关闭',
				iconCls:'icon-ok',
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
		});
}
</script>
<div id="pdailog"></div>

</body>
</html>