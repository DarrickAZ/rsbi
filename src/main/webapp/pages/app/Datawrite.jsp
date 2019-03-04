<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>数据填报</title> 
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
 <link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
  <link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/mobile.css">  
  <link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
   <script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.min.js"></script>
   <script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script> 
   <script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.mobile.js"></script> 
   <script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
   <script language="javascript" src="../resource/js/datawrite.js"></script>
  </head>
 
 <style>
 <!--
 .mtxt {
	 margin-top:10px;
	 color:#666;
 }
 -->
 </style>
 <body>

 
 <div class="easyui-navpanel" id="home">
        
       <ul class="m-list">
       		<c:forEach var="e" items="${ls}" varStatus="status">
            <li><a href="javascript:;" onClick="go_write('${e.tableId}')">${e.tableName}(${e.tableNote})</a></li>
            </c:forEach>
        </ul>
    </div>
    
    <div class="easyui-navpanel" id="write">
			<div id="writectx">
        
                    
         </div>
    </div>
    
     <div class="easyui-navpanel" id="viewdata">
			<div id="datactx">
        
                    
         </div>
    </div>
    
    <div id="pdailog"></div>

      </body>
    </html>