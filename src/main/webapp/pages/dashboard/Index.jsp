<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>睿思BI - 数据仪表盘</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/dashboard.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/gridster/jquery.gridster.css" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../resource/js/dashboard.js"></script>
<script type="text/javascript" src="../resource/gridster/jquery.gridster.min.js"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="../ext-res/js/echarts.min.js"></script>
<script type="text/javascript" src="../ext-res/js/sortabletable.js"></script>
<script type="text/javascript" src="../ext-res/js/echarts-wordcloud.min.js"></script>
</head>
<script>
$(document).ready(function () {
	<c:if test="${pageInfo == null}">
	 $('.gridster ul').dashboard();
	</c:if>
	<c:if test="${pageInfo != null}">
	 $('.gridster ul').dashboard({cfg:${pageInfo}, editorEnable:"${editor}", title:"${title}"});
	</c:if>
});
</script>
<body class="gray-bg">
<bi:mobileTop/>
	<nav class="navbar navbar-default animated fadeInDown" role="navigation" style="margin-bottom:0px;">
	    <div>
	        <!--向左对齐-->
	        <ul class="nav navbar-nav navbar-left">
			<li class="dropdown">
	        	<a href="javascript:;"  class="dropdown-toggle" data-toggle="dropdown">
	            	<i class="fa fa-file"></i>仪表盘
	            	 <b class="caret"></b>
	            </a>
	            <ul class="dropdown-menu">
	                <li><a href="javascript:;" id="openDashboard" >打开</a></li>
	                <li><a href="javascript:;" id="addDashboard">新建</a></li>
	                <li><a href="javascript:;"  id="saveDashboard">保存</a></li>
	            </ul> 
	        </li>
			<li class="dropdown">
	        	<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
	            	<i class="fa fa-plus-circle"></i>分析图
	            	 <b class="caret"></b>
	            </a>
	            <ul class="dropdown-menu">
	                <li><a href="javascript:;" id="crtAnalysis">新建分析图</a></li>
	                <li><a href="javascript:;"  id="addAnalysis">添加已有分析图</a></li>
	            </ul> 
	        </li>
			<li><a href="javascript:;" id="ypbSet"><i class="fa fa-cog"></i>设置</a></li>
	        </ul>
	    </div>
	</nav>

		<!-- 
		<div class="dashboard-title">
			<div class="row">
				<div class="col-sm-2" id="dashboardTitle" style="padding:5px;overflow:hidden;">
					<c:if test="${title == null}">_新建仪表盘</c:if><c:if test="${title != null}">${title}</c:if>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<input type="text" class="form-control" id="searchInput" placeholder="搜索仪表盘"> <span class="input-group-btn"> <button id="searchbtn" type="button" class="btn btn-default"><i class="fa fa-search"></i>
						</button> </span>
					</div>
				</div>
				<div class="col-sm-4">
					<button id="addDashboard" type="button" title="新建" class="btn btn-link dashboard-btn"><i class="fa fa-file-o"></i></button>
					<button id="saveDashboard" type="button" title="保存" class="btn btn-link dashboard-btn"><i class="fa fa-save"></i></button>
					<button id="openDashboard" type="button" title="打开" class="btn btn-link dashboard-btn"><i class="fa fa-folder-open-o"></i></button>
					<button id="addAnalysis" type="button" title="添加分析图" class="btn btn-link dashboard-btn"><i class="fa fa-plus-circle"></i></button>
					<button id="ypbSet" type="button" title="设置" class="btn btn-link dashboard-btn"><i class="fa fa-cog"></i></button>
				</div>
			</div>
		</div>
		-->
		<div id="paramDiv" class="param"></div>
		<div class="gridster" style="padding:0px;">
			<div class="helptxt" align="center">点击右上角 <i class="fa fa-plus-circle"></i> 按钮添加分析图</div>
			<ul>
			</ul>
		</div>

<!-- 共用模态框 -->
<div class="modal inmodal fade" id="modelDiv" role="dialog"  aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
		<form class="form-horizontal" id="form1" name="form1">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">XXX</h4>
			</div>
			<div class="modal-body">
				
			</div>

			<div class="modal-footer">
				<button type="button" id="saveEventbtn" class="btn btn-success">确定</button>
				<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
			</div>
		</form>
		</div>
	</div>
</div>

</body>
</html>