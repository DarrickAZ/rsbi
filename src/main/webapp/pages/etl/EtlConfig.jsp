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
<title>数据导入配置信息管理</title>
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
<script language="javascript">

function gocfg(){
	var row = $("#cfglist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var tp = row[0].impType;
	var id = row[0].cfgid;
	if(tp == "db"){
		location.href = 'ImportDb2.action?method=resume&cfgid='+id;
	}else if(tp == "csv"){
		location.href = 'ImportCsv2.action?method=resume&cfgid='+id;
	}else if(tp == "xls"){
		location.href = 'ImportXls2.action?method=resume&cfgid='+id;
	}else if(tp == "hadoop"){
		location.href = 'HadoopImport.action?method=resume&cfgid='+id;
	}
}
function delcfg(){
	var row = $("#cfglist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var data = row[0];
	if(confirm("是否确认删除？")){
		$.ajax({
			url:"delCfg.action",
			type:"GET",
			data:{cfgid:data.cfgid},
			dataType:"json",
			success:function(){
				$('#cfglist').bootstrapTable('refresh',{
					t:Math.random()
				});
			}
		});
	}
}
function fmtimptype(value){
	var str = "";
	if(value=="db"){
		str = "数据库";
	}else if(value == "xls"){
		str = "excel文件";
	}else if(value == "csv"){
		str = "csv文件";
	}else if(value == "hadoop"){
		str = "Hadoop/hdfs";
	}
	return str;
}
</script>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">

			
			<div class="ibox">
				<div class="ibox-title">
					<h5>导入配置信息列表</h5>
				</div>

				<div class="ibox-content">
			<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
				
				<button type="button" class="btn btn-outline btn-default" title="进入页面" onclick="gocfg()">
					<i class="glyphicon glyphicon-log-in" aria-hidden="true"></i>
				</button>
				<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delcfg()">
					<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
				</button>
			</div>
			<table id="cfglist" data-toggle="table" data-click-to-select="true" data-url="loadCfg.action">
				<thead>
					<tr>
						<th data-radio="true"></th>
					   <th data-field="cfgName">名称</th>
					   <th data-field="impType" data-formatter="fmtimptype">导入方式</th>
					   <th data-field="loginName">创建人</th>
					   <th data-field="crtDate" data-align="center">创建时间</th>
					   <th data-field="updateDate" data-align="center">修改时间</th>
					</tr>
				</thead>
			</table>
				
				</div>
			</div>
 </div>
 </div>
</div>
</body>
</html>