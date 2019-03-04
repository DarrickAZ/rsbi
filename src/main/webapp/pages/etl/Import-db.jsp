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
<title>从数据库导入数据</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/etl.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>	
</head>
<script language="javascript">
function chgdbtype(){
	var linktype = $("#linktype").val();
	if(linktype == "mysql"){
		$('#port').numberbox('setValue', 3306);
	}else if(linktype == "oracle"){
		$('#port').numberbox('setValue', 1521);
	}else if(linktype == "sqlser"){
		$('#port').numberbox('setValue', 1433);
	}else if(linktype == "db2"){
		$('#port').numberbox('setValue', 50000);
	}else if(linktype == "hive"){
		$('#port').numberbox('setValue', 10000);
	}else if(linktype == "psql"){
		$('#port').numberbox('setValue', 5432);
	}else if(linktype == "kylin"){
		$('#port').numberbox('setValue', 7070);
	}
}
$(function(){
	<%
	String dsource = (String)request.getAttribute("dsource");
	if(dsource != null && dsource.length() > 0){
	%>
	var ds = ${dsource};
	$("#linktype").val(ds.linkType);
	$("#ipaddress").textbox('setValue', ds.ipAddress);
	$("#port").textbox('setValue', ds.ipPort);
	$("#dbname").textbox('setValue', ds.database);
	$("#uname").textbox('setValue', ds.uname);
	$("#psd").textbox('setValue', ds.psd);
	
	<%
	}
	%>
});
</script>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>从数据库导入数据</h5>
                    </div>

					<div class="ibox-content">
<form id="ff" name="ff" method="post" action="ImportDb2.action">
<input type="hidden" name="connstate" id="connstate" value="">
<input type="hidden" name="method" value="${param.method}">
<input type="hidden" name="cfgid" value="${param.cfgid}">

<button type="button" class="btn btn-outline btn-default" onclick="selectconn()"><i class='fa fa-database'></i> 选择已有连接</button>
<p/>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">
 <tr>
    <td class="header hctx" width="120" height="30">数据库类型：
    </td>
    <td colspan="1" class="hctx">
    <select id="linktype" name="linktype" class="inputform" onchange="chgdbtype()">
    	<option value="mysql">mysql</option>
        <option value="sqlser">sql server</option>
        <option value="oracle">oracle</option>
		<option value="db2">db2</option>
		<option value="hive">hive</option>
		<option value="psql">PostgreSQL</option>
		<option value="kylin">kylin</option>
    </select>
    </td>
</tr>
 <tr>
    <td class="header hctx" width="120" height="30">IP地址：
    </td>
    <td colspan="1" class="hctx">
    <input type="text" id="ipaddress" name="ipaddress" class="easyui-textbox inputform" data-options="required:true,height:25">
    	
    </td>
</tr>
<tr>
    <td class="header hctx" width="120" height="30">端口号：
    </td>
    <td colspan="1" class="hctx">
    <input type="text" id="port" name="port" class="easyui-numberbox inputform" data-options="required:true,height:25" value="3306">
    	
    </td>
</tr>
<tr>
    <td class="header hctx" width="120" height="30">数据库名称：
    </td>
    <td colspan="1" class="hctx">
    <input type="text" id="dbname" name="dbname" class="easyui-textbox inputform" data-options="required:true,height:25">
    	
    </td>
</tr>
<tr>
    <td class="header hctx" width="120" height="30">用户名：
    </td>
    <td colspan="1" class="hctx">
    <input type="text" id="uname" name="uname" class="easyui-textbox inputform" data-options="required:true,height:25">
    	
    </td>
</tr>
<tr>
    <td class="header hctx" width="120" height="30">密码：
    </td>
    <td colspan="1" class="hctx">
    <input type="password" id="psd" name="psd" class="easyui-textbox inputform" data-options="required:true,height:25">
    	
    </td>
</tr>

<tr>
<td class="hctx" height="50"></td>
<td class="hctx">
<button onclick="testconn();" class="btn btn-success" type="button">测试连接</button>
<button onclick="saveconn();" class="btn btn-primary" type="button"><i class="fa fa-save"></i> 保存</button>
<button onclick="nextpage();" class="btn btn-primary" type="button"><i class="fa fa-arrow-right"></i>下一步</button>
</td>
</tr>

</table>

</form>
</div>

</div>
 </div>
 </div>
</div>
<div id="pdailog"></div>
</body>
</html>