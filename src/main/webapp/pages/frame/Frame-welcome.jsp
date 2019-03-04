<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
   <title>睿思BI - 系统介绍</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/iCheck/custom.css" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../resource/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
</head>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">
  
	  <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

		<div class="ibox-title">
			<h5>睿思BI - 系统介绍</h5>
		</div>

		<div class="ibox-content">	
		<p>	<b>“睿思BI”</b>商业智能系统V4.2是由<a href="http://www.ruisitech.com">成都睿思商智科技有限公司</a>自主研发的，具有自主知识产权的企业数据分析系统。系统包含数据导入、数据填报、数据建模、多维分析、数据查询、报表、移动BI等功能模块，方便企业快速建立一套易用，灵活、低成本的商业能平台，通过数据掌握企业经营情况，以数据支撑决策。
		</p>
		<p>&nbsp;</p>
		
		<dl class="dl-horizontal">
			<dt>系统名称：</dt><dd>${sinfo.rsbiName}</dd>
			<dt>系统版本：</dt><dd>${sinfo.rsbiVersion}</dd>
			<dt>版本号：</dt><dd>${sinfo.rsbiVersionNumber}</dd>
			<dt>最后更新时间：</dt><dd>${sinfo.rsbiLastupdate}</dd>
			<dt>官网地址：</dt><dd><a href="${sinfo.rsbiNet}">${sinfo.rsbiNet}</a></dd>
			<dt>jdk版本：</dt><dd>${sinfo.jdk}</dd>
			<dt>数据库：</dt><dd>${sinfo.dbName}</dd>
			<dt>邮件服务器：</dt><dd>${sinfo.mailHost}</dd>
			<dt>发送邮件账号：</dt><dd>${sinfo.mailUsername}</dd>
			<dt>单点登录地址：</dt><dd>${sinfo.ssoUrl}</dd>
			<dt>elasticsearch地址：</dt><dd>${sinfo.elasticsearch}</dd>
			<dt>elasticsearch版本号：</dt><dd>${sinfo.esVersion}</dd>
		</dl>
		<c:if test="${ syts == -10 }">
		<p class="text-warning">请安装license文件，下载地址：<a target="_blank" href="https://www.ruisitech.com/license.html">https://www.ruisitech.com/license.html</a>，并把下载的lic文件放入系统 WEB-INF 目录下。</p>
		</c:if>
		<c:if test="${ syts >= 0 }">
		<p class="text-warning">请注意：此版本为试用版，剩余有效期：<font size="16">${syts}</font>天。</p>
		</c:if>
		<p><button class="btn btn-primary btn-sm" onclick="window.open('https://www.ruisitech.com/')">购买标准版</button></p>
		</div>
</div>
 </div>
 </div>
</div>

</body>
</html>