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
<title>CSV导入配置</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
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
<%
String method = request.getParameter("method");
if("resume".equals(method)){
	%>
	var json = ${ctx};
	$(function(){
		resumeConfig(json);
	});
	<%
}else{

}
%>
function goback(){
	location.href = 'ImportCsv.action?method=${param.method}&cfgid=${param.cfgid}';
}
</script>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title" style="padding:10px;height:38px;">
                        <h5>CSV导入配置 &nbsp; <button class="btn btn-outline btn-info btn-xs" onclick="goback()"><i class="fa fa-chevron-left"></i>上一步</button></h5>
                    </div>
<div class="ibox-content">

<input type="hidden" id="filepath" name="filepath" value="${file.filePath}">
<input type="hidden" id="fileId" name="fileId" value="${file.id}">
<input type="hidden" id="impType" name="impType" value="csv">
<input type="hidden" name="method" id="method" value="${param.method}">
<input type="hidden" name="cfgid" id="cfgid" value="${param.cfgid}">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">
 <tr>
    <td class="header hctx" width="120">文件名称：
    </td>
    <td colspan="1" height="50">${file.fileName} &nbsp; <button onclick="viewdata()" class="btn btn-default">数据预览</button></td>
</tr>
<tr>
    <td class="header hctx" width="120" height="50">文本分割符：
    </td>
    <td colspan="1" class="hctx">
    <select  id="splitWord" name="splitWord" class="easyui-combobox" data-options="width:200,panelHeight:80,height:28">
    	<option value=",">,</option>
        <option value="@">@</option>
        <option value="\t">\t</option>
    </select></td>
</tr>
 <tr>
    <td class="header hctx" width="120" height="50">编码：
    </td>
    <td colspan="1" class="hctx">
    <select  id="csvencode" name="csvencode" class="easyui-combobox" data-options="width:200,height:28">
    	<option value="GB2312">GB2312</option>
        <option value="GBK">GBK</option>
        <option value="UTF-8">UTF-8</option>
        <option value="ISO-8859-1">ISO-8859-1</option>
        <option value="US-ASCII">US-ASCII</option>
    </select></td>
</tr>
 <tr>
   <td class="header hctx" height="50"></td>
   <td colspan="1" class="hctx">
   <div class="checkbox checkbox-info">
   <input type="checkbox" id="nameinhead" name="nameinhead">
   <label for="nameinhead">
   第一行作为标题
   </label>
   </div>
   </td>
 </tr>
 <tr>
    <td class="header hctx" width="120" height="50">目标表：
    </td>
    <td colspan="1" class="hctx">
    <input type="hidden" id="targettableid" name="targettableid">
    <input type="text" id="targettable" name="targettable" class="inputform" readonly="readonly"> <button type="button" class="btn btn-outline btn-info" onclick="selectTable()">选择</button> <button type="button" class="btn btn-outline btn-info" onclick="newtable()">新建</button>
    </td>
</tr>
<tr>
   <td class="header hctx">字段映射：</td>
   <td colspan="1" class="hctx">
   <div id="tabletreepanel">
	<ul id="tabletree"></ul>
</div>
   </td>
 </tr>
 <tr>
   <td class="header hctx" height="50">清除数据：</td>
   <td colspan="1" class="hctx">
    <div class="checkbox checkbox-info">
   <input type="checkbox" id="truncate" name="truncate"><label for="truncate">导入前清除目标表数据。</label>
   </div>
   </td>
 </tr>
</table>
<div style="margin:10px;" align="center">
<a href="javascript:startimp();" class="easyui-linkbutton" data-options="iconCls:'icon-run'">开始导入</a>
<a href="javascript:;" onclick="query_data()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">分析数据</a>
<a href="javascript:;" onclick="savecfg()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存配置</a>
</div>
</div>

</div>
 </div>
 </div>
</div>


<div id="pdailog"></div>

</body>
</html>