<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" pageEncoding="UTF-8" import="com.ruisi.ext.engine.ExtConstants"%>
<%@ page session="false" buffer="none" %>

<%
String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>禁止访问</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<link rel="shortcut icon" type="image/x-icon" href="<%=path%>/resource/img/rs_favicon.ico">
<link href="<%=path%>/ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/resource/css/animate.css" rel="stylesheet">
<link href="<%=path%>/resource/css/style.css" rel="stylesheet">
<link href="<%=path%>/resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="<%=path%>/ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="<%=path%>/ext-res/js/ext-base.js"></script>

 <style>
 <!--
.p_err {
	  width:470px;
	  margin:0 auto;
	   border: 1px solid #BBBBBB;
    border-radius: 8px 8px 8px 8px;
    box-shadow: 5px 5px 5px #DDDDDD;
	background-color:#FFF;
	line-height:20px;
	font-size:14px;
	height:60px;
  }
-->
 </style>
    
  </head>

 
  <body class="gray-bg">

  
  <div class="wrapper wrapper-content animated fadeInDown">
  
	  <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

				
					<div class="ibox-content" align="center">
					
					<img style="margin:10px;" src="<%=path%>/resource/img/icon-error.gif">
					<div style="font-size: 16px; font-weight:bold; margin-bottom:5px; margin-top:10px;">禁止访问!</div>
  

  

</div>
</div>
 </div>
 </div>
</div>

  </body>
</html>
