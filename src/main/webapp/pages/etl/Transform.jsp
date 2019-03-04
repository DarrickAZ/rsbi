<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>数据转换</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/codemirror/codemirror.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/json.js"></script>
<script language="javascript" src="../ext-res/js/ext-base.js"></script>
<script language="javascript" src="../resource/js/etl.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="../resource/codemirror/codemirror.js"></script>
<script src="../resource/codemirror/mode/sql/sql.js"></script>
</head>
<style type="text/css">
  .CodeMirror {border: 1px solid silver; height: 240px; width:400px; font-size:13px;}
  .tfcolslist {
	  padding:3px;
	  margin-bottom:5px;
	  border-radius:5px;
	  color:#101010;
	  background-color: #f3f2ed;
	  font-size:14px;
  }
  .tfcolslist .tfcbtn {
	  float:right;
	  margin-top:5px;
  }
</style>
<script language="javascript">
function delcfg(idx){
	var rows = $('#cfglist'+idx).bootstrapTable('getSelections');
	if(rows.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var data = rows[0];
	if(confirm("是否确认删除？")){
		$.ajax({
			url:(idx==4?"deleteEsTable.action":"delTf.action"),
			type:"GET",
			data:{id:data.id},
			dataType:"json",
			success:function(){
				$('#cfglist'+idx).bootstrapTable("refresh", {t:Math.random()});
			}
		});
	}
}
function runtf(idx){
	var rows = $('#cfglist'+idx).bootstrapTable('getSelections');
	if(rows.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var data = rows[0];
	if(idx == 1){
		runtransform(data.id);
	}else if(idx == 2){
		runr2c(data.id);
	}else if(idx == 3){
		runsql(data.id);
	}else if(idx == 4){
		runt2es(data.id);
	}
}
function updatecfg(idx){
	var rows = $('#cfglist'+idx).bootstrapTable('getSelections');
	if(rows.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var data = rows[0];
	if(idx == 1){
		newtransform("update", data.id, data.tableMetaId);
	}else if(idx == 3){
		newsql(true, data.id, data.tableMetaId);
	}else if(idx == 2){
		newR2C(true, data.id, data.tableMetaId);
	}
}
function fmt(value){
	var str = "";
	if(value=="y"){
		str = "<font color='red'>是</font>";
	}else{
		str = "否";
	}
	return str;

}
</script>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content animated fadeInDown">
					<div class="tabs-container">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" idx="1" href="#tab-1" aria-expanded="true">表关联</a></li>
							<li><a data-toggle="tab" idx="2" href="#tab-2" aria-expanded="false">行转列</a></li>
							<li><a data-toggle="tab" idx="3" href="#tab-3" aria-expanded="false">SQL脚本</a></li>
							<li><a data-toggle="tab" idx="4" href="#tab-4" aria-expanded="false">同步表到ES</a></li>
						</ul>
					</div>
					<div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                            <div class="panel-body" style="padding:10px;">
								<div style="margin-bottom:10px;" class="btn-group" role="group">
									<button type="button" class="btn btn-outline btn-default" title="新增" onclick="newtransform()">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="执行" onclick="runtf(1)">
                                        <i class="glyphicon glyphicon-play" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="修改" onclick="updatecfg(1)">
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delcfg(1)">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                    </button>
								</div>
							
								<table data-toggle="table" data-click-to-select="true" id="cfglist1" data-url="loadTfTable.action" >
								  <thead>
								  <tr>
									<th data-radio="true"></th>
								   <th data-field="name" data-align="center">名称</th>
								   <th data-field="primaryTable" data-align="center">主表</th>
								   <th data-field="targetTable" data-align="center">目标表</th>
								   <th data-field="crtUserName" data-align="center">创建人</th>
								   <th data-field="crtdate" data-align="center">创建时间</th>
								   <th data-field="updatedate" data-align="center">修改时间</th>
								   </tr>
								   </thead>
								 </table>
                            </div>
                        </div>
                        <div id="tab-2" class="tab-pane">
                            <div class="panel-body" style="padding:10px;">
								<div style="margin-bottom:10px;" class="btn-group" role="group">
									<button type="button" class="btn btn-outline btn-default" title="新增" onclick="newR2C()">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="执行" onclick="runtf(2)">
                                        <i class="glyphicon glyphicon-play" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="修改" onclick="updatecfg(2)">
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delcfg(2)">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                    </button>
								</div>
								<table data-toggle="table" data-click-to-select="true" id="cfglist2" data-url="loadR2CTable.action" >
								  <thead>
								  <tr>
									<th data-radio="true"></th>
								   <th data-field="name" data-align="center">名称</th>
								   <th data-field="primaryTable" data-align="center">主表</th>
								   <th data-field="targetTable" data-align="center">目标表</th>
								   <th data-field="crtUserName" data-align="center">创建人</th>
								   <th data-field="crtdate" data-align="center">创建时间</th>
								   <th data-field="updatedate" data-align="center">修改时间</th>
								   </tr>
								   </thead>
								 </table>
                            </div>
                        </div>
						<div id="tab-3" class="tab-pane">
                            <div class="panel-body" style="padding:10px;">
								<div style="margin-bottom:10px;" class="btn-group" role="group">
									<button type="button" class="btn btn-outline btn-default" title="新增" onclick="newsql(false)">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="执行" onclick="runtf(3)">
                                        <i class="glyphicon glyphicon-play" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="修改" onclick="updatecfg(3)">
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delcfg(3)">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                    </button>
								</div>
								<table id="cfglist3" data-toggle="table" data-click-to-select="true" data-url="loadSqlTable.action">
								  <thead>
								  <tr>
									<th data-radio="true"></th>
								   <th data-field="name" data-align="center">名称</th>
								   <th data-field="targetTable" data-align="center">目标表</th>
								   <th data-field="crtUserName" data-align="center">创建人</th>
								   <th data-field="crtdate" data-align="center">创建时间</th>
								   <th data-field="updatedate" data-align="center">修改时间</th>
								   </tr>
								   </thead>
								  </table>
                            </div>
                        </div>
						<div id="tab-4" class="tab-pane">
                            <div class="panel-body" style="padding:10px;">
								<div style="margin-bottom:10px;" class="btn-group" role="group">
									<button type="button" class="btn btn-outline btn-default" title="新增" onclick="newT2ES(false)">
                                        <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="执行" onclick="runtf(4)">
                                        <i class="glyphicon glyphicon-play" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="修改" onclick="newT2ES(true)">
                                        <i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
                                    </button>
									<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delcfg(4)">
                                        <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                    </button>
								</div>
								<table id="cfglist4" data-toggle="table" data-click-to-select="true" data-url="loadEsTable.action">
								  <thead>
								  <tr>
									<th data-radio="true"></th>
								   <th data-field="name" data-align="center">名称</th>
								   <th data-field="targetTable" data-align="center">目标表</th>
								   <th data-field="esEnable" data-align="center" data-formatter="fmt">是否同步</th>
								   <th data-field="dataCount" data-align="center">数据量</th>
								   <th data-field="crtUserName" data-align="center">创建人</th>
								   <th data-field="crtdate" data-align="center">创建时间</th>
								   <th data-field="updatedate" data-align="center">修改时间</th>
								   </tr>
								   </thead>
								  </table>
                            </div>
                        </div>
                    </div>
 
</div>
<div id="pdailog"></div>
</body>
</html>