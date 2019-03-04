<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>

<script language="javascript">
$(function(){
	var cols = <%
List ls = (List)request.getAttribute("cols");
out.print(net.sf.json.JSONArray.fromObject(ls));
%>;
	$("#datasavebtn").menubutton({}).click(function(){
		savedata(true, cols, '${table.tableName}', ${table.tableId});
	});
	$("#databackbtn").menubutton({}).click(function(){
		history.back();
	});
	$("#viewdatabtn").linkbutton({}).click(function(){
		go_viewdata(${table.tableId});
	});
});

</script>

        <div style="margin:5px; font-size:14px;">
位置： <a href="javascript:;" onClick="go_home()">选择表</a> -> 数据填报
</div>
       <div style="font-size:16px; font-weight:bold; margin:10px; float:left;">
      ${table.tableName}(${table.tableNote}) 
      </div>
      
      <div style="margin:8px; float:right;">
        <a href="javascript:;" id="viewdatabtn" class="easyui-linkbutton" data-options="iconCls:'icon-cross2',plain:false">查看数据</a>
        </div>
      <bi:datawrite is3g="true" state="insert"/>
      
      <div align="center" style="padding:10px;">
       <a href="javascript:;" id="datasavebtn" class="easyui-menubutton" data-options="iconCls:'icon-save',menuAlign:'right',hasDownArrow:false" outline="true">保存</a>
        &nbsp;
      
        
        <a href="javascript:;" id="databackbtn" class="easyui-menubutton" data-options="iconCls:'icon-back',menuAlign:'right',hasDownArrow:false" outline="true">取消</a>
       </div>
     
    