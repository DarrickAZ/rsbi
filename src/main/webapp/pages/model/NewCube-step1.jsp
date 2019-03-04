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
<title>创建多维数据模型</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../resource/js/cube.js"></script>
</head>

<style>
<!--
.cubecfg {
	margin:0px;
}
-->
</style>
<script language="javascript">
var pageJson = {tName:"", tid:""};
</script>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">
 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>创建新的数据模型</h5>
                    </div>
<div class="ibox-content">


<div id="seltables">


 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">
  <tr>
    <td class="header hctx" width="120">模型名称：</td>
    <td class="hctx"><input type="text" class="inputform" id="cubename" name="cubename"></td>
  </tr>
  <tr>
    <td height="50">所属分类：</td>
    <td class="hctx">
    <select id="ftype" name="ftype" class="inputform">
    <option value=""></option>
   <c:forEach var="e" items="${ types }">
    <option value="${e.dsId}">${e.name}</option>
    </c:forEach>
    </select> 
    <button type="button" class="btn btn-sm btn-primary" onclick="location.href='SubjectType.action'">管理分类</button>
    </td>
  </tr>
  <tr>
    <td  height="50" width="120">备注：</td>
    <td class="hctx"><input type="text" class="inputform" id="cubenote" name="cubenote"></td>
  </tr>
 <tr>
    <td  height="50" width="120">对应数据表：
    </td>
    <td colspan="1" class="hctx">
    	<select id="tableid" name="tableid" class="inputform" onchange="loadData()">
        <option value=""></option>
        <c:forEach var="e" items="${ls}">
        <option value="${e.tableId},${e.tableName},<c:if test="${tableSql==null}">y</c:if><c:if test="${tableSql!=null}">n</c:if>">${e.tableName}(${e.tableNote})</option>
        </c:forEach>
        </select>
    </td>
</tr>

<tr>
<td  class="hctx"></td>
<td  class="hctx">
<a href="javascript:cubenext();" class="easyui-linkbutton" data-options="iconCls:'icon-right'">下一步</a>
</td>
</tr>
</table>
<div id="tabledata"></div>
</div>

<div id="showcolumns" style="display:none">

<div class="" style="margin:5px;">
<button onclick="createDyna(false)" class="btn btn-sm btn-info">创建动态字段</button>
&nbsp;
<a href="javascript:cubehome();" class="easyui-linkbutton" data-options="iconCls:'icon-left'">上一步</a> 
<a href="javascript:cubenext2();" class="easyui-linkbutton" data-options="iconCls:'icon-right'">下一步</a>
</div>
<div id="showcolumns_ctx">
</div>

<div style="margin:5px;">
<a href="javascript:cubehome();" class="easyui-linkbutton" data-options="iconCls:'icon-left'">上一步</a> 
<a href="javascript:cubenext2();" class="easyui-linkbutton" data-options="iconCls:'icon-right'">下一步</a>
</div>

</div>

<div id="cubeinfo" style="display:none;width:770px;">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" align="right">
    <div class="easyui-panel" data-options="width:330,height:400,cls:'cubecfg'" title="待选字段<span style='width:180px;display: inline-block;'></span><button type='button' style='margin-top:-3px;' class='btn btn-info btn-xs' id='autoCubeBtn'>自动生成模型</button>">
    <ul id="cubelefttree">
    </ul>
</div>
    </td>
    <td align="center" valign="top">
    <p style="height:150px;"></p>
    <button type="button" title="选择" onclick="ds2cube()" class="btn btn-success btn-circle">&gt;</button>
    <br/>
	 <br/>
    <button type="button" onclick="cube2ds()" title="移除" class="btn btn-success btn-circle">&lt;</button>
    </td>
    <td valign="top">
     <div class="easyui-panel" data-options="width:330,height:400,cls:'cubecfg'" title="维度和度量">
		 <ul id="cuberighttree">
		</ul>
	</div>
    </td>
    <td valign="top">
    <div style="width:80px;line-height:25px;text-align:right;"><button class="btn btn-default btn-xs" style="width:70px;" id="addDateDimBtn"><i class="fa fa-calendar-plus-o"></i>时间维度</button><br/><button class="btn btn-default btn-xs" style="width:70px;" onclick="addgroup()" ><i class="fa fa-plus"></i> 维度分组</button><button style="width:70px;" class="btn btn-default btn-xs" id="addDynaBtn"><i class="fa fa-plus-square"></i> 计算度量</button><button style="width:70px;" class="btn btn-default btn-xs" onclick="kpiCatalog();"><i class="fa fa-folder-open-o"></i>度量分类</button><button onclick="editcubecol();" class="btn btn-default btn-xs" style="width:70px;"><i class="fa fa-edit"></i> 编辑</button><button onclick="cube2ds();" class="btn btn-default btn-xs" style="width:70px;"><i class="fa fa-remove"></i> 删除</button></div>
    </td>
  </tr>
</table>

<div style="margin:5px;">
<a href="javascript:cubenext();" class="easyui-linkbutton" data-options="iconCls:'icon-left'">上一步</a>
<a href="javascript:savecubecfg(pageJson);" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
</div>

</div>


</div>

</div>
 </div>
 </div>
</div>

<div id="pdailog"></div>

</body>
</html>