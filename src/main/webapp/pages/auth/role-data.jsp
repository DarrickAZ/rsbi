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
				<h5>角色管理 >> 数据权限</h5>
			</div>

			<div class="ibox-content">
				<form id="dform">
					<div align="right">
					<button type="button" onclick="getDataIds()" class="btn btn-info btn-sm"> 保存 </button>
					<a type="button" href="roleList.action" class="btn btn-default">返回</a>
					</div>
					<table style="width:560px;" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td width="368">
					    <div align="left" style="margin:0px 5px 5px 5px;">
					 &nbsp; <a href="javascript:t_expandAll()">全部展开</a> &nbsp;&nbsp; <a href="javascript:t_collapseAll()">全部关闭</a>
					 </div>
					    </td>
					    <td width="118" align="center"> <input name="selectview" type="checkbox"  onclick="selectall('view',this)" value="y">(全选)</td>
					  </tr>
					</table>
					
					<table id="reporttree" title="" class="easyui-treegrid" style="width:560px;height:500px;">
						<thead>
					    	<tr>
					        <th data-options="field:'name'" width="420">数据名称</th>
					        <th data-options="field:'view',formatter:printAuth" width="120" align="center">使用权限</th>
					        </tr>
					    </thead>
					</table>
				</form>
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
		$('#reporttree').treegrid({
			data: ${datas},
			idField: 'id',
			treeField: 'name',
			border:true
		});
		t_expandAll();
	});
	function t_expandAll(){
		$('#reporttree').treegrid("expandAll");
	}
	function t_collapseAll(){
		$('#reporttree').treegrid("collapseAll");
	}
	function printAuth(value,row,index){
		if(row.tp == 'data'){
			var chked = row.checked;
			return "<input type=\"checkbox\" "+(chked?"checked":"")+" value=\"y\" name=\""+this.field+"@"+(row.id - 100000)+"\">";
		}
	}
	function selectall(itp, ts){
		var ff = document.getElementById("dform");
		for(i=0; i<ff.elements.length; i++){
			if(ff.elements[i].type == "checkbox"){
				var name = ff.elements[i].name;
				var ns = name.split("@");
				if(ns.length >=2 && ns[0] == itp){
					if(ts.checked){
						ff.elements[i].checked = true;
					}else{
						ff.elements[i].checked = false;
					}
				}
			}
		}
		
	}
	function getDataIds(){
		var dt = $("#dform").serialize();
		if(dt != ""){
			dt = dt+ "&";
		}
		dt = dt + "roleId="+${param.roleId};
		$.ajax({
			type:"POST",
			url:"role/dataSave.action",
			dataType:"JSON",
			data:dt,
			success:function(resp){
				msginfo("操作成功！", "success");
			}
		});
	}
</script>
