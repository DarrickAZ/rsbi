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
<title>创建多维数据模型</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../resource/js/cube.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</head>
<script language="javascript">
jQuery(function(){
	var dt = [{id:'zty', text:'主题域', iconCls:'icon-subject', children:${str}}];
	$("#typetree").tree({
		data:dt,
		onClick:function(node){
			var type = node.id;
			if(type == "zty"){
				type = "";
			}
			$('#cubelist').bootstrapTable('refresh',{query:{
				typeId: type,
				t:Math.random()
			}});
		}
	});
});
function viewsub(){
	var row = $("#cubelist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	editcube(row[0].tid);
}
function delsub(){
	var row = $("#cubelist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var data = row[0];
	if(confirm("是否确认删除？")){
		$.ajax({
			url:"delSubject.action",
			type:"GET",
			data:{tid:data.tid},
			dataType:"json",
			success:function(){
				$('#cubelist').bootstrapTable('refresh',{
					t:Math.random()
				});
			}
		});
	}
}
function viewcube(){
	var row = $("#cubelist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	if($("#dsColumn_div").size() == 0){
		$("<div id=\"dsColumn_div\"></div>").appendTo("body");
	}
	var ctx = "<iframe id=\"detailinfo\" width=\"100%\" height=\"100%\" src=\"../bireport/ReportDesign.action?selectDs="+row[0].tid+"\" frameborder=\"0\"></iframe>";
	$('#dsColumn_div').dialog({
		title: '多维分析',
		fit:true,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content:ctx,
		onClose:function(){
			$('#dsColumn_div').dialog('destroy');
		},
		buttons:[{
				text:'关闭',
				iconCls:"icon-ok",
				handler:function(){
					$('#dsColumn_div').dialog('close');
				}
			}]
	});
}
</script>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content">
 <div class="row">
				<div class="col-sm-3">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>模型分类</h5>
                    </div>
<div class="ibox-content">

 <ul id="typetree"></ul>



   </div>

</div>
 </div>
 <div class="col-sm-9 animated fadeInRight">
<div class="ibox">
	
		<div class="ibox-title">
		<h5>已建模型列表</h5>
		</div>
		<div class="ibox-content" style="padding:10px;">
		<div style="margin-bottom:10px;" class="btn-group" role="group">
			<button type="button" class="btn btn-outline btn-default" title="新增" onclick="location.href = 'newCubeStep1.action'">
				<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="viewsub()">
				<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="查询" onclick="viewcube()">
				<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delsub()">
				<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
			</button>
		</div>
		   <table id="cubelist" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="listSubject.action" data-side-pagination="server" >
			  <thead>
			  <tr>
				<th data-radio="true"></th>
			   <th data-field="tid">ID</th>
			   <th data-field="tDesc">名称</th>
			   <th data-field="tName" data-sortable="true">对应表</th>
			   <th data-field="tNote">说明</th>
			   </tr>
			   </thead>
			   </table>
			</div>

</div>
	 </div>
 </div>
</div>
   
<div id="pdailog"></div>
</body>
</html>