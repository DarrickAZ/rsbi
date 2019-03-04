<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>

<script language="javascript">
$(function(){
	var cols = <%
	List ls = (List)request.getAttribute("cols");
	out.print(net.sf.json.JSONArray.fromObject(ls));
	%>;
	$("#tablegrid").datagrid({
		url:'../imp/loaddata.action',
		singleSelect:true,
		pagination:true,
		pageSize:10,
		queryParams:{tableId:'${table.tableId}',tableName:'${table.tableName}'},
		onRowContextMenu:function(e,index,row){
			e.preventDefault();
			e.stopPropagation();
			//window.table_id = row.tableId;
			$("#tablegrid").datagrid("selectRow", index);
			var offset = {left:e.pageX, top:e.pageY};
			$("#menus").menu("show", {left:offset.left, top:offset.top});
		},
		toolbar:[{
			text:' 修改 &nbsp; ',
			iconCls:'icon-edit',
			handler:function(){
				editrecord('${table.tableId}','${table.tableName}', cols);
			}
		},{
			text:' 删除 &nbsp; ',
			iconCls:'icon-remove',
			handler:function(){
				delrecord('${table.tableId}','${table.tableName}');
			}
		}]
	});
});

</script>
<div style="margin:5px; font-size:14px;">
位置： <a href="javascript:;" onClick="go_home()">选择表</a> -> <a href="javascript:;" onClick="history.back();">数据填报</a> -> 浏览数据
</div>
       <div style="font-size:16px; font-weight:bold; margin:10px;">
      ${table.tableName}(${table.tableNote}) 
      </div>
      
      <table id="tablegrid" data-options="">
        <thead>
            <tr>
			<th data-options="field:'x',checkbox:true"></th>
				<c:forEach var="e" items="${ cols }">
                <th data-options="field:'${e.colName}',width:100,align:'center'">${e.colNote}</th>
                 </c:forEach>
            </tr>
        </thead>
     </table>
     
