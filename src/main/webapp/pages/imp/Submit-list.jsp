<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.*" %>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>数据列表</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/datawrite.js?v3"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>	
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</head>

<script language="javascript">
var cols = <%
List ls = (List)request.getAttribute("cols");
out.print(net.sf.json.JSONArray.fromObject(ls));
%>;
/**
$(function(){
	$("#tablegrid").datagrid({
		url:'loaddata.action',
		singleSelect:true,
		pagination:true,
		pageSize:10,
		queryParams:{tableId:'${table.tableId}',tableName:'${table.tableName}'},
		onRowContextMenu:function(e,index,row){
			e.preventDefault();
			e.stopPropagation();
			//window.table_id = row.table_id;
			$("#tablegrid").datagrid("selectRow", index);
			var offset = {left:e.pageX, top:e.pageY};
			$("#menus").menu("show", {left:offset.left, top:offset.top});
		},
		toolbar:[{
			text:'修改',
			iconCls:'icon-edit',
			handler:function(){
				editrecord();
			}
		},{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				delrecord();
			}
		}]
	});
});
**/
function editrecord(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	if(row.length == 0){
		 $.messager.alert("出错了","请勾选数据。","error");
		return;
	}
	row = row[0];
	$('#pdailog').dialog({
		title: '修改数据',
		width: 500,
		height: 360,
		closed: false,
		cache: false,
		modal: true,
		href:'premod.action?dataId=' + row.tmp_data_id+"&tableId=${table.tableId}",
		onLoad:function(){},
		buttons:[{
				text:'确定',
				iconCls:"icon-ok",
				handler:function(){
					 savedata(false, cols, '${table.tableName}', '${table.tableId}', true, row.tmp_data_id); 
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
function delrecord(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	if(row.length == 0){
		 $.messager.alert("出错了","请勾选数据。","error");
		return;
	}
	row = row[0];
	if(confirm("是否确认？删除后不能恢复。")){
		$.ajax({
			type:"post",
			url:"delData.action",
			dataType:"json",
			data:{dataId: row.tmp_data_id, tableName:'${table.tableName}'},
			success:function(dt){
				$("#tablegrid").bootstrapTable("refresh", {query:{t:Math.random()}});
			}
		});
	}
}
</script>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title" style="padding:10px;">
                        <h5>填报表管理 </h5>
                    </div>
<div class="ibox-content">


<div style="font-size:16px; font-weight:bold; padding:10px;">
${table.tableName}(${table.tableNote}) 
</div>
<div style="margin-bottom:10px;" class="btn-group" id="exampleTableEventsToolbar" role="group">
	<button type="button" class="btn btn-outline btn-default" title="返回" onclick="location.href='<c:if test="${income == 'dl'}">DataList.action</c:if><c:if test="${income != 'dl'}">write.action?tableId=${table.tableId}</c:if>'">
		<i class="glyphicon glyphicon-arrow-left" aria-hidden="true"></i>
	</button>
	<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="editrecord()">
		<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
	</button>
	<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delrecord()">
		<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
	</button>
</div>

<table id="tablegrid" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="loaddata.action?tableId=${table.tableId}&tableName=${table.tableName}" data-side-pagination="server">
	<thead>
    	<tr>
			<th data-radio="true"></th>
        	 <c:forEach var="e" items="${cols}">
        	<th data-field="${e.colName}" data-align="center">${e.colNote}</th>
             </c:forEach>
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