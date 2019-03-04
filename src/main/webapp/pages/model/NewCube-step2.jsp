<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="grid3" id="T_report54" cellSpacing="0" cellPadding="0">
<tr>
<th width="10%">操作</th>
<th width="19%">字段名</th>
<th width="10%">字段类型</th>
<th width="9%">长度</th>
<th width="33%">表达式</th>
<th width="16%">备注</th>
</tr>
<c:forEach var="e" items="${ ls }">
<tr>
<td class="kpiData1 grid3-td" align="center">
<c:if test="${e.expression != null}">
	<button onclick="createDyna(true, ${e.tableId}, ${e.colId})" class="btn btn-info btn-xs"><i class="fa fa-edit"></i></button>
    <button onclick="removeDyna(${e.colId}, ${e.tableId})" class="btn btn-danger btn-xs"><i class="fa fa-remove"></i></button>
</c:if>
</td>
<td class="kpiData1 grid3-td">${e.colName}</td>
<td class="kpiData1 grid3-td" align="center">${e.colType}</td>
<td class="kpiData1 grid3-td" align="center">${e.colLength}</td>
<td class="kpiData1 grid3-td" align="center">${e.expression}</td>
<td class="kpiData1 grid3-td">${e.colNote}</td>
</tr>
</c:forEach>
</table>
<script language="javascript">
$(".removebtn").linkbutton({iconCls:"icon-cancel", plain:true});
</script>