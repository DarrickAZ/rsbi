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
   <title>Hadoop导入工具</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
</head>

<script language="javascript">
var isok = false;
function testHdfs(){
	var addres = $("#hdfsaddress").val();
	if(addres == "" || addres=="hdfs://" || addres.indexOf("hdfs://") != 0){
		msginfo("请录入正确的HDFS地址,以hdfs://开头");
		return;
	}
	__showLoading();
	$.post("testHdfs.action", {hdfsAddress:addres}, function(resp){
		__hideLoading();
		if(resp.result == 0){ //失败了
			msginfo("连接失败！" + resp.msg);
		}else{
			isok = true;
			msginfo("链接成功！", "success");
		}
	});
}
function nextpage(){
	if(isok){
		location.href = 'HadoopImport.action?hdfsAddress=' + $("#hdfsaddress").val();
	}else{
		msginfo("请先点击测试HDFS链接，确定连接正常后再点击下一步。");
	}
}
</script>

<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">

  <div class="row">
     <div class="col-sm-12">
		<div class="ibox">
			<div class="ibox-title">
				<h5>从Hadoop导入数据</h5>
			</div>

			<div class="ibox-content">

					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">
 <tr>
    <td class="header hctx" width="120" height="60">HDFS地址：
    </td>
    <td colspan="1" class="hctx">
    <input type="text" id="hdfsaddress" name="hdfsaddress" value="<c:if test="${hdfsAddress == null }">hdfs://</c:if><c:if test="${hdfsAddress != null }">${hdfsAddress}</c:if>" class="inputform">
    	
    </td>
</tr>


<tr>
<td class="hctx"></td>
<td class="hctx">
	<button onclick="testHdfs()" class="btn btn-success">测试 </button> <button onclick="nextpage()" class="btn btn-primary"><i class="fa fa-arrow-right"></i> 下一步 </button>
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