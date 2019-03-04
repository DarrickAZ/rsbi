<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%><%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%
List ds = (List)request.getAttribute("ls");
%>
<table class="grid3" style="width:auto;" cellpadding="0" cellspacing="0">
<%
for(int i=0; i<ds.size(); i++){
	Map m = (Map)ds.get(i);
	out.print("<tr>");
	for(int j=0; j<m.size(); j++){
%>
	<td class='grid3-td'><%=(m.get(String.valueOf(j)))%></td>	
<%
	}
	out.print("</tr>");
}
%>
</table>