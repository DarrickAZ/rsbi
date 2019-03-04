<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" import="com.ruisitech.bi.util.RSBIUtils" pageEncoding="utf-8"%>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>睿思BI - 数据报表</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/css/portal.css" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../resource/js/json.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/js/portal-compress.js?v4"></script>
<script type="text/javascript" src="../ext-res/js/echarts.min.js"></script>
<script type="text/javascript" src="../ext-res/js/sortabletable.js"></script>
<script type="text/javascript" src="../ext-res/js/jquery.resizeend.min.js"></script>
<script type="text/javascript" src="../ext-res/js/jquery-resizable.js"></script>
<script type="text/javascript" src="../ext-res/js/echarts-wordcloud.min.js"></script>
</head>
<style>
.ibox .win-size-grip {
  opacity:0;	
  cursor:auto;
}
</style>
<script language="javascript">
var curTmpInfo = {is3g:'n',compEdit:true};  //组件编辑模式
$(function(){
	<c:if test="${id != null }">
	var dashboard = window.parent.dashboard;
	if(dashboard){
		var json = {"layout":1,"body":{"tr1":[{"colspan":1,"rowspan":1,"width":100,"height":100,"id":1,"children":[]}]}};
		var compId = "${id}";
		if(compId != ""){
			json.body.tr1[0].children.push(dashboard.pageInfo[compId]);
			curTmpInfo.isAddybp = false;
		}else{
			//新建仪表盘模式，
			var compType = '${param.compType}';
			var comp = null;
			if("box" == compType){
				 comp = {"id":newGuid(), "name":"数据块", "type":"box"};
			}else if("grid" == compType){
				comp = {"id":newGuid(), "name":"表格", "type":"grid"};
			}else if("table" == compType){
				comp = {"id":newGuid(), "name":"交叉表", "type":"table"};
			}else if("chart" == compType){
				comp = {"id":newGuid(), "name":"图表", "type":"chart",chartJson:{"type":"line"}, kpiJson:[null, null, null]};
			}
			json.body.tr1[0].children.push(comp);
			curTmpInfo.isAddybp = true;
		}
		window.pageInfo = json;
		//初始化布局
		initlayout();
	}
	</c:if>
	<c:if test="${pageInfo != null}">
		window.pageInfo = ${pageInfo};
		initlayout();
	</c:if>
});
</script>

<body class="easyui-layout" id="Jlayout">
<div data-options="region:'north',border:false" style="height:32px; overflow:hidden;">
  <div class="panel-header" style="padding:3px;">
        	<a href="javascript:backpage();" id="mb1" class="easyui-linkbutton" plain="true" iconCls="icon-back">返回</a>
        	<c:if test="${id == '' }">
        	<a href="javascript:shareComp();" id="mb2" class="easyui-linkbutton" plain="true" iconCls="icon-save">保存</a>
        	</c:if>
            <a href="javascript:;" menu="#selectinfo" id="mb4" class="easyui-menubutton" plain="true" iconCls="icon-dataset">数据</a>   
   </div>
   
</div>

<div data-options="region:'west',split:true,title:''" style="width:190px;">
<div id="comp_tab" data-options="fit:true,border:false" class="easyui-tabs" style="height:auto; width:auto;">
	<div title="立方体" style="">
        <ul id="datasettree" class="easyui-tree">
        </ul>
    </div>
    <div title="数据表" style="">
        <ul id="tabletree" class="easyui-tree">
        </ul>
    </div>
 </div>
</div>

<div data-options="region:'center',border:true" title="组件编辑">
  <div class="easyui-layout" data-options="fit:true">
  	
    <div data-options="region:'center'" id="optarea" align="center">
		
    </div>
  </div>
</div>

<div id="pdailog"></div>

<div id="dimoptmenu" class="easyui-menu">
	<div onclick="dimsort('asc')">升序</div>
    <div onclick="dimsort('desc')">降序</div>
    <div>
    <span>移动</span>
    <div style="width:120px;">
    	<div iconCls="icon-back" onclick="dimkpimove('left')">左移</div>
        <div iconCls="icon-right" onclick="dimkpimove('right')">右移</div>
        <div id="m_moveto" onclick="dimexchange()">移至</div>
    </div>
    </div>
    <div iconCls="icon-filter" onclick="filterDims()">筛选...</div>
    <div iconCls="icon-sum" onclick="aggreDim()" id="m_aggre">聚合...</div>
    <div onclick="delJsonKpiOrDim('dim')" iconCls="icon-remove">删除</div>
</div>
<div id="kpioptmenu" class="easyui-menu">
	<div onclick="kpiproperty()">属性...</div>
	<div onclick="kpiwarning()">预警...</div>
    <div>
    <span>排序</span>
    <div style="width:120px;">
    	<div id="k_kpi_ord1" onclick="kpisort('asc')">升序</div>
        <div id="k_kpi_ord2"  onclick="kpisort('desc')">降序</div>
        <div id="k_kpi_ord3" iconCls="icon-ok" onclick="kpisort('')">默认</div>
    </div>
    </div>
    <div>
    <span>移动</span>
    <div style="width:120px;">
    	<div iconCls="icon-back" onclick="dimkpimove('left')">左移</div>
        <div iconCls="icon-right" onclick="dimkpimove('right')">右移</div>
    </div>
    </div>
    <div iconCls="icon-remove" onclick="delJsonKpiOrDim('kpi')">删除</div>
</div>
<div id="chartoptmenu" class="easyui-menu">
	<div onclick="chartsort('asc')">升序</div>
    <div onclick="chartsort('desc')">降序</div>
    <div iconCls="icon-filter" onclick="chartfilterDims()" >筛选...</div>
    <div onclick="setChartKpi()" id="m_set">属性...</div>
    <div onclick="delChartKpiOrDim()" iconCls="icon-remove">清除</div>
</div>
<div id="table_menu" class="easyui-menu">
	<div iconCls="icon-dataset" onclick="editComp()">数据</div>
    <div iconCls="icon-filter" onclick="setcompfilter()">筛选...</div>
    <div onclick="compevent()">事件...</div>
    <div onclick="setComp()">属性...</div>
</div>
<div id="box_menu" class="easyui-menu">
	<div iconCls="icon-dataset" onclick="editComp()">数据</div>
    <div iconCls="icon-filter" onclick="setcompfilter()">筛选...</div>
    <div onclick="setComp()">属性...</div>
</div>
<div id="grid_menu" class="easyui-menu">
	<div iconCls="icon-dataset" onclick="editComp()">数据</div>
    <div iconCls="icon-filter" onclick="setcompfilter()">筛选...</div>
    <div onclick="setComp()">属性...</div>
</div>
<div id="text_menu" class="easyui-menu">
	<div onclick="editComp()">编辑</div>
    <div onclick="setComp()">属性...</div>
</div>
<div id="pic_menu" class="easyui-menu">
	<div onclick="editComp()">添加图片</div>
    <div onclick="setComp()">属性...</div>
</div>
<div id="chart_menu" class="easyui-menu">
	<div onclick="setcharttype(false)">图表类型...</div>
	<div iconCls="icon-dataset" onclick="editComp()">数据</div>
    <div iconCls="icon-filter" onclick="setcompfilter()">筛选...</div>
    <div onclick="compevent()">事件...</div>
    <div onclick="setComp()">属性...</div>
</div>
<div id="selectinfo" style="width:150px;">
    <div onclick="selectdataset()" >选择立方体...</div>
    <div onclick="selecttable(false)" >选择数据表...</div>
</div>
<div id="gridoptmenu" class="easyui-menu">
    <div>
    <span>排序</span>
    <div style="width:120px;">
    	<div id="col_ord1" onclick="gridColsort('asc')">升序</div>
        <div id="col_ord2"  onclick="gridColsort('desc')">降序</div>
        <div id="col_ord3" iconCls="icon-ok" onclick="gridColsort('')">默认</div>
    </div>
    </div>
    <div>
    <span>移动</span>
    <div style="width:120px;">
    	<div iconCls="icon-back" onclick="tableColmove('left')">左移</div>
        <div iconCls="icon-right" onclick="tableColmove('right')">右移</div>
    </div>
    </div>
    <div onclick="setGridColProp()" id="m_set">属性...</div>
    <div onclick="delGridCol()" iconCls="icon-remove">删除</div>
</div>

</body>
</html>