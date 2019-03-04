<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%><%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="grid3" id="T_report54" cellpadding="0" cellspacing="0">
<thead>
<tr class="scrollColThead" style="background-color:#FFF">
	<th  class="null" colspan="1"  rowspan="1" width="40%">度量</th>
	<th  class="null" colspan="1"  rowspan="1" width="60%">解释</th>
</tr>
	<c:forEach var="e" items="${ls}" >
<tr>
 <td class='kpiData1 grid3-td' align="left">${e.kpiName}</td>	
 <td class='kpiData1 grid3-td' align="left" style="color:#666">${e.kpiDesc}</td>
</tr>
 </c:forEach>

</thead>
</table>