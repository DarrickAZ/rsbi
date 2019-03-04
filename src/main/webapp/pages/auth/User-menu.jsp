<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%><%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ext" uri="/WEB-INF/ext-runtime.tld" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>用户管理</title>
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
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInDown">
  <div class="row">
     <div class="col-sm-12">
		<div class="ibox">
			<div class="ibox-title">
				<h5>用户管理 >> 所辖菜单</h5>
			</div>

			<div class="ibox-content">
			
				<div align="right" style="padding:5px;">
					<button type="button" onclick="getMenuIds()" class="btn btn-info btn-sm"> 保存 </button>
					<a href='userList.action' class="btn btn-default">返回</a>
				</div>
			
				<div align="left" style="margin:0px 10px 10px 10px;">
				<a href="javascript:t_expandAll()">全部展开</a> &nbsp;&nbsp; <a href="javascript:t_collapseAll()">全部关闭</a>
				 </div>
				<ul id="menutree"></ul>

</div>
</div>
 </div>
 </div>
</div>

</body>
</html>

<script language="javascript">
	var $ = jQuery;
	$(function(){
		$('#menutree').tree({
			data:[${datas}],
			dnd:false,
			lines:false,
			animate:true,
			checkbox:true,
			formatter:function(node){
				if(node.id == '0'){
					return node.text;
				}
				if(!node.attributes){
					return node.text
				}
				if(node.attributes.disabled == false){
					return node.text
				}
				return "<span style=\"color:#999;text-decoration:line-through\">" + node.text + "</span>"
			},
			onBeforeCheck:function(node, checked){
				if(!node.attributes){
					return false;
				}
				if(node.attributes && node.attributes.disabled){
					return false;
				}
			}
		});
	});
	function t_expandAll(){
		$('#menutree').tree("expandAll");
	}
	function t_collapseAll(){
		$('#menutree').tree("collapseAll");
	}
	function getMenuIds(){
		var ids = getMenuId();
		if(ids != ''){
			ids = ids.substring(0, ids.length - 1);
		}
		$.ajax({
			type:"POST",
			url:"userMenu/save.action",
			dataType:"JSON",
			data:{userId:${param.userId}, menuIds:ids},
			success:function(resp){
				msginfo("操作成功！", "success");
			}
		});
		
	}
	function getMenuId(){
		var ids = "";
		var nodes = $('#menutree').tree("getChecked", ['checked','indeterminate']);
		for(i=0; nodes&&i<nodes.length; i++){
			if(nodes[i].id == "root"){
				continue;
			}
			if(!nodes[i].attributes || nodes[i].attributes.disabled == true){
				continue;
			}
			ids = ids + nodes[i].id + ",";
		}
		return ids;
	}
</script>
