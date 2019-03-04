<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script language="javascript" src="../resource/js/datawrite.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
</head>

<script language="javascript">
var tabinfo = {cols:[]};
$(function(){
	newtable("crttabdiv");
});
</script>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>数据填报</h5>
                    </div>
<div class="ibox-content">

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">

 <tr>
    <td class="header hctx" width="120" height="50">选择填报表：
    </td>
    <td colspan="1" class="hctx">
       <select id="tableId" name="tableId" class="inputform" >
        <option value=""></option>
        <c:forEach var="e" items="${tabs}">
        <option value="${e.tableId}">${e.tableName}(${e.tableNote})</option>
        </c:forEach>
        </select> 
      
    </td>
</tr>
<tr>
	<td class="header hctx" >
    </td>
    <td height="40" class="hctx">
    
    <button onclick="gotowrite()" class="btn btn-w-m btn-success"><i class="fa fa-arrow-right"></i> 下一步</button>
    
    </td>
</tr>
</table>
</div>

</div>
 </div>
 </div>
</div>
</body>
</html>