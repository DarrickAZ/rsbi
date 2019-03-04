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
<title>从数据库导入数据到系统</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<link href="../resource/codemirror/codemirror.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/etl.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script src="../resource/codemirror/codemirror.js"></script>
<script src="../resource/codemirror/mode/sql/sql.js"></script>	
</head>
<style type="text/css">
  .CodeMirror {border: 1px solid silver; height: 140px; width:520px; font-size:14px;}
</style>
<script language="javascript">
//初始化代码编辑器
$(function(){
	var editor = CodeMirror.fromTextArea(document.getElementById("sql"), {
		mode: "text/x-sql",
		indentWithTabs: true,
		smartIndent: true,
		lineNumbers: true,
		matchBrackets : true,
		autofocus: false,
		lineWrapping:true
	});
	window.sqlEditor = editor;
});
<%
String method = request.getParameter("method");
if("resume".equals(method)){
	%>
	var json = ${ctx};
	var dsource = json.dsource;
	var job = json.job;
	$(function(){
		resumeConfig(json, '${param.linktype}', '${param.ipaddress}', '${param.port}', '${param.dbname}', '${param.uname}', '${param.psd}');
	});
	<%
}else{
	%>
	var dsource = {linkType:'${param.linktype}',ipAddress:'${param.ipaddress}', ipPort:'${param.port}', database:'${param.dbname}',uname:'${param.uname}', psd:'${param.psd}'};
	var job;
	<%
}
%>

$(function(){
	$.ajax({
		type:"post",
		url:"listdbTables.action",
		dataType:"json",
		data:dsource,
		success:function(resp){
			for(i=0; i<resp.rows.length; i++){
				resp.rows[i].state = "closed";
			}
			$("#alltables").tree({
				data:resp.rows,
				url:"listTableColumns.action",
				onBeforeLoad:function(node, param){
					if(node == null){
						return false;
					};
					param.dsource = JSON.stringify(dsource);
					param.tname = node.id;
				},
				onClick:function(node){
					var txt = node.id;
					if(node.iconCls == 'icon-dscol'){
						txt = txt + ",";
					}else {
						txt = txt + " ";
					}
					insertText2focus(document.getElementById("sql"),  txt);
				}
			});
			$("#sql").focus();
		}
	});
});
function goback(){
	location.href = 'ImportDb.action?method=${param.method}&cfgid=${param.cfgid}&dsource=' +escape(JSON.stringify(dsource));
}
</script>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title" style="padding:10px;height:38px;">
                        <h5>导入配置 &nbsp; <button class="btn btn-outline btn-info btn-xs" onclick="goback()"><i class="fa fa-chevron-left"></i>上一步</button></h5>
                    </div>

					<div class="ibox-content">
<input type="hidden" id="impType" name="impType" value="db">
<input type="hidden" id="cfgid" name="cfgid" value="${param.cfgid}">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">
 <tr>
 	<td  class="header hctx" width="120">
    导入SQL：
    </td>
    <td class="hctx">
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="540"><textarea style="width:530px; height:140px;" class="inputform" name="sql" id="sql"></textarea></td>
    <td width="353" valign="top">  <ul id="alltables" style="height:140px; overflow:auto;">
    </ul></td>
  </tr>
</table>
    <button onclick="viewdata()" class="btn btn-default">数据预览</button>
    </td>
 </tr>
 <tr>
    <td class="header hctx" width="120" height="50">目标表：
    </td>
    <td colspan="1" class="hctx">
    <input type="hidden" id="targettableid" name="targettableid">
    <input type="text" id="targettable" class="inputform" name="targettable" size="25" readonly="readonly"> <button type="button" class="btn btn-outline btn-info" onclick="selectTable()">选择</button> <button type="button" class="btn btn-outline btn-info" onclick="newtable()">新建</button>
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
   <td height="50">时间戳：</td>
   <td colspan="1" class="hctx"> [<span id="datelabel" class="text-primary" key="">未设置</span>] <a href="javascript:setsjc();" class="easyui-linkbutton">设置</a> <i class="text-info">设置时间戳，实现数据增量导入</i></td>
 </tr>
 <tr>
   <td height="30">清除数据：</td>
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
<!--
<a href="javascript:;" onclick="savejobs(false)" class="easyui-linkbutton" data-options="iconCls:'icon-jobs'">定时任务</a>
 -->
</div>

</div>

</div>
 </div>
 </div>
</div>

<div id="pdailog"></div>

</body>
</html>