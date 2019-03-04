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
	$('.gridster ul').dashboard({cfg:${pageInfo}, editorEnable:"0", title:"${title}"});
});
</script>
<body class="gray-bg">
<bi:mobileTop/>
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