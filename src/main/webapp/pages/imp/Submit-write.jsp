<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.*" %>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>数据填报</title>
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
<script language="javascript" src="../resource/js/datawrite.js?v2"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
</head>

<script language="javascript">
var cols = <%
List ls = (List)request.getAttribute("cols");
out.print(net.sf.json.JSONArray.fromObject(ls));
%>;
</script>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title" style="padding:10px;">
                        <h5>数据填报</h5>
                    </div>
<div class="ibox-content">

<div style="font-size:16px; font-weight:bold;">
${table.tableName}(${table.tableNote}) 
</div>
<div style="margin:3px;">
<a href="listSubmit.action?tableId=${table.tableId}" class="easyui-linkbutton" data-options="iconCls:'icon-cross2',plain:false">查看数据</a>
<a href="javascript:impData(${table.tableId}, '${table.tableName}');" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:false">批量导入</a>
</div>
<div id="writectx">
<bi:datawrite is3g="false" state="insert"/>
</div>
<div align="center" style="padding:10px;">
<button class="btn btn-success" onclick="savedata(false,cols, '${table.tableName}', ${table.tableId})" ><i class="fa fa-save"></i> 保存</button>
&nbsp;
<button onclick="location.href='Submit.action'" class="btn btn-white"><i class="fa fa-arrow-left"></i> 返回</button>
</div>

</div>

</div>
 </div>
 </div>
</div>



<div id="pdailog"></div>
</body>
</html>