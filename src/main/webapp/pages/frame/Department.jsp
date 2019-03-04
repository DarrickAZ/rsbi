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
   <title>组织机构管理</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
</head>
<style>
.lone {
	padding:3px;
}
</style>
<script language="javascript">
$(function(){
	$('#departmentTree').tree({
		url:'loadDepartment.action',
		dnd:false,
		animate:true,
		data: [{id:'0', text:'组织机构树', state:'closed', iconCls:"icon-earth"}],
		onBeforeLoad: function(node){
			if(!node || node == null){
				return false;
			}
		},
		onContextMenu: function(e, node){
			e.preventDefault();
			$('#departmentTree').tree('select', node.target);
			if(node.id == '0'){
				$('#treeMenu').menu("disableItem", $("#treeMenu #mod"));
				$('#treeMenu').menu("disableItem", $("#treeMenu #del"));
			}else{
				$('#treeMenu').menu("enableItem", $("#treeMenu #mod"));
				$('#treeMenu').menu("enableItem", $("#treeMenu #del"));
			}
			$('#treeMenu').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});
	var node = $('#departmentTree').tree("getRoot");
	$('#departmentTree').tree("expand", node.target);

})
function addMenu(update){
	var node = $("#departmentTree").tree("getSelected");
	var obj;
	if(update){
		$.ajax({
			   type: "GET",
			   async: false,
			   url: "getDepartment.action",
			   dataType:"JSON",
			   data: {"id":node.id},
			   success: function(resp){
				  obj = resp;
			   }
		});
	}
	var ctx = "<div style=\"padding:10px;\"><div class=\"lone\"><span class=\"inputtext\">名称：</span><input type=\"text\" id=\"name\" class=\"inputform\" value=\""+(obj?obj.deptName:"")+"\"></div><div class=\"lone\"><span class=\"inputtext\">编码：</span><input type=\"text\" id=\"code\" class=\"inputform\" placeholder=\"在数据权限控制中使用\" value=\""+(obj?obj.deptCode:"")+"\"></div><div class=\"lone\"><span class=\"inputtext\">备注：</span><input type=\"text\" id=\"note\" class=\"inputform\" value=\""+(obj&&obj.deptNote!=null?obj.deptNote:"")+"\"></div></div>";
	$('#pdailog').dialog({
		title: update?'修改组织机构':'新建组织机构',
		width: 420,
		height: 200,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content: ctx,
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var name = $("#pdailog #name").val();
					var code = $("#pdailog #code").val();
					var note = $("#pdailog #note").val();
					if(name == ''){
						alert("名称必须填写。");
						return;
					}
					if(update==false){
						$.ajax({
						   type: "POST",
						   url: "saveDepartment.action",
						   dataType:"json",
						   data: {"deptName":name,"deptNote":note,"deptCode":code, "pid":node.id},
						   success: function(resp){
							   $("#departmentTree").tree("append", {parent:node.target, data:[{id:resp.rows,text:name}]});
						   }
						});
					}else{
						$.ajax({
						   type: "POST",
						   url: "updateDepartment.action",
						   dataType:"json",
						   data: {"deptName":name,"deptNote":note,"deptCode":code, id:node.id},
						   success: function(){
							   $("#departmentTree").tree("update", {target:node.target, text:name});
						   },
						   error: function(a, b, c){
							   $.messager.alert("出错了。","修改出错。", "error");
						   }
						});
					}
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
function delMenu(){
	if(confirm('是否确认删除？')){
		var node = $("#departmentTree").tree("getSelected");
		$.getJSON("delDepartment.action", {id:node.id}, function(){
			$("#departmentTree").tree("remove", node.target);
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
				<h5>组织机构管理</h5>
			</div>

			<div class="ibox-content">

			<div class="row">
				<div class="col-sm-5">
				<ul id="departmentTree" style="200px;"></ul>
				 </div>
				 <div class="col-sm-6">
				<p class="text-warning">在菜单上点击鼠标右键来新建或编辑组织机构。</p>
				</div>
			</div>


</div>
</div>
 </div>
 </div>
</div>
<div id="pdailog"></div>
<div id="treeMenu" class="easyui-menu">
	<div onclick="addMenu(false)" id="add">新增...</div>
    <div onclick="addMenu(true)" id="mod">修改...</div>
    <div onclick="delMenu()" id="del">删除</div>
</div>

</body>
</html>