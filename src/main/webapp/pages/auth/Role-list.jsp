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
<title>用户管理</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<link href="../resource/iCheck/custom.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="../resource/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="../resource/validate/messages_zh.min.js"></script>
<script type="text/javascript" src="../resource/js/auth.js"></script>
<script type="text/javascript" src="../resource/iCheck/icheck.min.js"></script>
</head>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">
  <div class="row">
     <div class="col-sm-12">
		<div class="ibox">
			<div class="ibox-title">
				<h5>角色管理</h5>
			</div>

			<div class="ibox-content">
				<div class="btn-group" id="exampleTableEventsToolbar" role="group">
					<button type="button" class="btn btn-outline btn-default" title="新增" onclick="addRole(false)">
						<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="修改" onclick="addRole(true)">
						<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
					</button>
					<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delRole()">
						<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
					</button>
				</div>
				<table id="tablegrid" data-toggle="table" data-pagination="true" data-page-size="20" data-method="post" data-toolbar="#exampleTableEventsToolbar"
					data-click-to-select="true" data-url="role/list.action" data-side-pagination="server" data-search="true" data-search-on-enter-key="true">
					<thead>
						<tr>
							<th data-radio="true" data-align="center"></th>
							<th data-field="roleId" data-align="center">角色标识</th>
							<th data-field="roleName" data-align="center">角色名称</th>
							<th data-field="roleDesc" data-sortable="false">备注信息</th>
							<th data-field="createUser" data-align="center">创建人</th>
							<th data-field="createDate" data-align="center" data-sortable="false">创建时间</th>
							<th data-field="ord" data-align="center">排序</th>
							<th data-field="roleId" data-align="left" data-formatter="roleopt">操作</th>
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