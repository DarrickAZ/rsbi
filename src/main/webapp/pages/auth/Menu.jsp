<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%><%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" buffer="none" %>
<%@ taglib prefix="ext" uri="/WEB-INF/ext-runtime.tld" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>菜单管理</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="../resource/js/auth.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
</head>
<style>
.lone {
	padding:3px;
}
</style>
<script language="javascript">
	var $ = jQuery;
	$(function(){
		$('#ggcatatree').tree({
			url:'menu/loadData.action',
			dnd:false,
			animate:true,
			data: [{id:'0', text:'系统菜单', state:'closed', iconCls:"icon-earth"}],
			onBeforeLoad: function(node){
				if(!node || node == null){
					return false;
				}
			},
			onContextMenu: function(e, node){
				e.preventDefault();
				$('#ggcatatree').tree('select', node.target);
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
		var node = $('#ggcatatree').tree("getRoot");
		$('#ggcatatree').tree("expand", node.target);
	});
	function delMenu(){
		if(confirm('是否确认删除？')){
			var node = $("#ggcatatree").tree("getSelected");
			$.ajax({
			   type: "POST",
			   url: "menu/delete.action",
			   dataType:"json",
			   data: {"menuId":node.id},
			   success: function(resp){
				   if(resp.result == 1){
				   	$("#ggcatatree").tree("remove", node.target);
				   }else{
					   $.messager.alert("出错了。",resp.msg, "error");
				   }
			   },
			   error: function(){
				   $.messager.alert("出错了。","系统错误", "error");
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
			<div class="ibox-title">
				<h5>菜单管理</h5>
			</div>

			<div class="ibox-content">

<div class="row">
    <div class="col-sm-5">
    <ul id="ggcatatree" style="200px;"></ul>
	 </div>
	 <div class="col-sm-6">
    <p class="text-warning">在菜单上点击鼠标右键来新建或编辑菜单。</p>
	</div>
</div>

<div id="pdailog"></div>
<div id="treeMenu" class="easyui-menu">
	<div onclick="addMenu(false)" id="add">新增...</div>
    <div onclick="addMenu(true)" id="mod">修改...</div>
    <div onclick="delMenu()" id="del">删除</div>
</div>

</div>
		</div>
 	</div>
 </div>
</div>

</body>
</html>
