<%
/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
%>﻿<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bi" uri="/WEB-INF/common.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>睿思BI - 数据报表</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</head>
<script language="javascript">
$(function(){
	$(".folder-list").on("contextmenu",".reportTypes",function(e){
		e.preventDefault();
		e.stopPropagation();
		window.tid = $(this).attr("tid");
		var offset = {left:e.pageX, top:e.pageY};
		$("#menus").menu("show", {left:offset.left, top:offset.top});
		reloadPage(window.tid);
		return false;
	}).on("click", ".reportTypes", function(e){
		window.tid = $(this).attr("tid");
		reloadPage(window.tid);
	});
});
function checkdata(){
	var row = $("#reportlist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了","请勾选数据。", "error");
		return false;
	}else{
		return true;
	}
}
function reloadPage(cataId){
	$(".folder-list .reportTypes").css("background-color", "inherit");
	$(".folder-list li[tid="+cataId+"]").css("background-color", "#f8f8f8");
	$("#reportlist").bootstrapTable("refresh", {query:{"cataId":cataId,"t":Math.random()}});
}
function renamereport(){
	if(checkdata() == false){
		return;
	}
	var row = $("#reportlist").bootstrapTable("getSelections");
	row = row[0];
	var tps = "";
	$(".reportTypes").each(function(idx, obj){
		var typeId = $(this).attr("tid");
		var txt = $(this).text();
		tps = tps + "<option value=\""+typeId+"\" "+(row.typeId == typeId ?"selected":"")+">"+txt+"</option>";
	});
	var ctx = "<div class=\"textpanel\"><span class=\"inputtext\">报表名称：</span><input type=\"text\" class=\"inputform2\" id=\"name\" value=\""+row.pageName+"\"><br/><span class=\"inputtext\">所属分类：</span><select class=\"inputform2\" id=\"rtype\">"+tps+"</select></div>";
	$('#pdailog').dialog({
		title: '报表修改',
		width: 320,
		height: 180,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content: ctx,
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var name = $("#pdailog #name").val();
					var rtype = $("#pdailog #rtype").val();
					if(name == ''){
						$.messager.alert("出错了","名称必须填写。", "error", function(){
							$("#pdailog #name").focus();
						});
						return;
					}
					
					$.ajax({
					   type: "POST",
					   url: "rename.action",
					   dataType:"json",
					   data: {pageId:row.pageId, pageName:name, cataId:rtype},
					   success: function(resp){
						  $("#reportlist").bootstrapTable("refresh", {query:{"cataId":rtype,"t":Math.random()}});
					   },
					   error:function(){
						  
					   }
					});
					
					$('#pdailog').dialog('close');
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
}
function delreport(){
	if(checkdata() == false){
		return;
	}
	var row = $("#reportlist").bootstrapTable("getSelections");
	row = row[0];
	if(confirm('是否确认删除？')){
		$.ajax({
			  type: "POST",
			   url: "delete.action",
			   dataType:"json",
			   data: {pageId:row.pageId},
			   success: function(resp){
				  $("#reportlist").bootstrapTable("refresh", {"cataId":row.cataId,"t":Math.random()});
			   },
			   error:function(){
				  
			   }
		});
	}
}
function newreport(){
	location.href = 'customization.action';
}
function viewReport(){
	if(checkdata() == false){
		return;
	}
	var row = $("#reportlist").bootstrapTable("getSelections");
	row = row[0];
	location.href = 'show.action?pageId=' + row.pageId;
}
function editreport(){
	if(checkdata() == false){
		return;
	}
	var row = $("#reportlist").bootstrapTable("getSelections");
	row = row[0];
	location.href = 'customization.action?pageId=' + row.pageId;
}
function addType(update){
	var obj;
	if(update){
		var id = window.tid;
		$.ajax({
		   type: "GET",
		   url: "../report/getType.action",
		   dataType:"json",
		   data: {id:id},
		   async:false,
		   success: function(resp){
			   obj = resp;
		   }
		});
	}
	var ord = $(".reportTypes").size() + 1 ;
	var ctx = "<div class=\"textpanel\"><span class=\"inputtext\">名称：</span><input type=\"text\" id=\"name\" class=\"inputform2\" value=\""+(obj&&obj.name!=null?obj.name:"")+"\"><br/><span class=\"inputtext\">说明：</span><input type=\"text\" id=\"note\" class=\"inputform2\" value=\""+((obj&&obj.note!=null?obj.note:""))+"\"><br/><span class=\"inputtext\">排序：</span><input type=\"text\" id=\"order\" class=\"inputform2\" value=\""+(obj&&obj.ord!=null?obj.ord:ord)+"\"><br/></div>";
	$('#pdailog').dialog({
		title: update?'修改报表分类':'新建报表分类',
		width: 320,
		height: 200,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content: ctx,
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var name = $("#pdailog #name").val();
					var note = $("#pdailog #note").val();
					var order = $("#pdailog #order").val();
					if(name == ''){
						$.messager.alert("出错了","名称必须填写。", "error", function(){
							$("#pdailog #name").focus();
						});
						return;
					}
					if(isNaN(order)){
						$.messager.alert("出错了","排序字段必须是数字类型。", "error", function(){
							$("#pdailog #order").select();
						});
						return;
					}
					if(update==false){
						$.ajax({
						   type: "POST",
						   url: "../report/addType.action",
						   dataType:"json",
						   data: {"name":name,"note":note,"ord":order,income:"pc"},
						   success: function(resp){
							   $(".folder-list").append("<li tid=\""+resp.rows+"\" class=\"reportTypes\"><a href=\"javascript:;\"><i class=\"fa fa-folder\"></i>"+name+"</a></li>");
						   }
						});
					}else{
						$.ajax({
						   type: "POST",
						   url: "../report/updateType.action",
						   dataType:"json",
						   data: {"name":name,"note":note,"ord":order, "id":obj.id},
						   success: function(resp){
							   $(".reportTypes[tid="+obj.id+"] a").html("<i class=\"fa fa-folder\"></i>"+name);
						   },
						   error: function(a, b, c){
							   $.messager.alert("出错了。","修改出错。", "error");
						   }
						});
					}
					$('#pdailog').dialog('close');
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
}
function deleteType(){
	if(confirm('是否确认删除？')){
		$.ajax({
			  type: "POST",
			   url: "deleteType.action",
			   dataType:"json",
			   data: {id:window.tid},
			   success: function(resp){
				   if(resp.result  == 0){
					   $.messager.alert("出错了。","分类下含有报表，不能删除。", "error");
				   }else{
						$(".reportTypes[tid="+window.tid+"]").remove();
				   }
			   },
			   error:function(){
				  
			   }
		});
	}
}
function sharer(){
	if(checkdata() == false){
		return;
	}
	var row = $("#reportlist").bootstrapTable("getSelections");
	var ctx = "<div id=\"sharetab\"><div title=\"通过邮件分享\" style=\"padding:10px;\"><span class=\"inputtext\">邮件地址：</span><input type=\"text\" name=\"email\" id=\"email\" class=\"inputform\"><br/><br/><span class=\"inputtext\">主题： </span><input type=\"text\" name=\"subject\" id=\"subject\" class=\"inputform\"><br/><br/><span class=\"inputtext\">内容： </span><input type=\"text\" name=\"ctx\" id=\"ctx\" class=\"inputform\"><br/><br/><p class=\"text-warning\">说明：报表以附件形式发送给用户。</p></div><div title=\"直接生成URL\" style=\"padding:10px;\"><span class=\"inputtext\">是否需要登录： </span><div class=\"radio radio-info radio-inline\"><input id=\"isloginy\" name=\"islogin\" type=\"radio\" value=\"1\" checked><label for=\"isloginy\">是</div></label><div class=\"radio radio-info radio-inline\"><input id=\"isloginn\" name=\"islogin\" type=\"radio\" value=\"0\"><label for=\"isloginn\">否</label></div><br/><br/><span class=\"inputtext\">有效期： </span><div class=\"radio radio-info radio-inline\"><input type=\"radio\" id=\"yxq1\" name=\"yxq\" value=\"1\" checked><label for=\"yxq1\">一小时</label></div><div class=\"radio radio-info radio-inline\"><input type=\"radio\" id=\"yxq2\" name=\"yxq\" value=\"2\"><label for=\"yxq2\">一天</label></div><div class=\"radio radio-info radio-inline\"><input type=\"radio\" id=\"yxq3\" name=\"yxq\" value=\"-1\"><label for=\"yxq3\">永久有效</label></div> <br/> <br/><p class=\"text-warning\">说明：生成的URL可以直接通过微信/QQ发送给用户。</p></div><div title=\"推送到菜单\" style=\"padding:10px;\"><span class=\"inputtext\">名称：</span><input type=\"text\" id=\"name\" class=\"inputform2\"><br/><span class=\"inputtext\">排序：</span><input type=\"text\" id=\"ord\" class=\"inputform2\" value=\"1\"><br/><span class=\"inputtext\">图标：</span><input type=\"text\" id=\"avatar\" class=\"inputform2\"><table><tr><td valign=\"top\"><span class=\"inputtext\">上级菜单：</span></td><td><ul id=\"ggcatatree\" style=\"width:100%\"></ul></td></tr></table></div></div>";
	$('#pdailog').dialog({
		title: "报表分享",
		width: 420,
		height: 300,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content: ctx,
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				var tab = $('#sharetab').tabs('getSelected');
				var index = $('#sharetab').tabs('getTabIndex',tab);
				if(index == 0){
					//发送邮件
					var email = $("#pdailog #email").val();
					var sub = $("#pdailog #subject").val();
					var ctx = $("#pdailog #ctx").val();
					if(email == ""){
						msginfo("请填写邮件地址。");
						return;
					}
					if(sub == ""){
						msginfo("请填写主题");
						return;
					}
					__showLoading();
					$.ajax({
						url:"../report/sendMail.action",
						type:"POST",
						data:{rid:row[0].pageId, toAddress:email, subject:sub, content:ctx },
						dataType:"json",
						success:function(r){
							__hideLoading();
							if(r.result == 1){
								msginfo("邮件发送成功。", "success");
								
							}else{
								msginfo("错误信息：" + r.msg);
							}
						},
						error:function(){
							__hideLoading();
						}
					});
				}else if(index == 1){
					//复制url
					var islog = $("#pdailog input[name='islogin']:checked").val();
					var yxq = $("#pdailog input[name='yxq']:checked").val(); 
					if(yxq == "2"){
						yxq = "24";
					}
					__showLoading();
					$.ajax({
						url:"../report/copyUrl.action",
						type:"POST",
						data:{reportId:row[0].pageId, islogin:islog, yxq:yxq },
						dataType:"json",
						success:function(r){
							__hideLoading();
							if(r.result == 1){
								msginfo(r.rows, "success");
							}else{
								msginfo("错误信息：" + r.msg);
							}
						},
						error:function(){
							__hideLoading();
						}
					});
				}else if(index == 2){
					var name = $("#pdailog #name").val();
					var ord = $("#pdailog #ord").val();
					if(name == ''){
						msginfo("名称必须填写。");
						return;
					}
					if(ord == ''){
						msginfo("排序必须填写。");
						return;
					}
					if(isNaN(ord)){
						msginfo("排序必须是数字类型。");
						return;
					}
					var node = $("#pdailog #ggcatatree").tree("getSelected");
					if(node == null){
						msginfo("请选择上级菜单。");
						return;
					}
					var avatar = $("#pdailog #avatar").combobox("getValue");
					//新增只能配置3级菜单
					var p1 = $("#pdailog #ggcatatree").tree("getParent", node.target);
					if(p1 != null){
						var p2 = $("#pdailog #ggcatatree").tree("getParent", p1.target);
						if(p2 != null){
							var p3 = $("#pdailog #ggcatatree").tree("getParent", p2.target);
							if(p3 != null && p3.id == "0"){
								$.messager.alert("出错了。","菜单只能建3级", "error");
								return;
							}
						}
					}
					var url = "../portal/show.action?income=menu&pageId=" + row[0].pageId;
					$.ajax({
						type:"POST",
						url:"../auth/menu/save.action",
						data:{"menuName":name,"menuDesc":"","menuOrder":ord, "menuUrl":url, "menuPid":node.id,"avatar":avatar,urls:"portal/export.action,portal/print.action,portal/view.action"},
						dataType:"json",
						success:function(){
							msginfo("菜单推送成功。", "success");
						},
						error:function(){
							msginfo("系统出错。");
						}
					});
				}
				$('#pdailog').dialog('close');
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#pdailog').dialog('close');
			}
		}]
	});
	$("#sharetab").tabs({border:false,fit:true});
	//初始化菜单
	$('#ggcatatree').tree({
		url:'../auth/menu/loadData.action',
		dnd:false,
		animate:true,
		data: [{id:'0', text:'系统菜单', state:'closed', iconCls:"icon-earth"}],
		onBeforeLoad: function(node){
			if(!node || node == null){
				return false;
			}
		}
	});
	//初始化图标
	$("#pdailog #avatar").combobox({
		url:'../resource/fonts/menu-icons.json',
		valueField:'cls',
		textField:'text',
		height:25,
		formatter:function(row){
			return "<i class=\""+row.cls+"\"></i> "+row.text;
		}
	});
	var node = $('#ggcatatree').tree("getRoot");
	$('#ggcatatree').tree("expand", node.target);
	/**
	$("#savebtn").unbind("click").bind("click", function(){
		var tp = $(".tabs-container li.active").attr("tp");
		if(tp == "mail"){  //发送邮件
			var email = $("#msgdiv #email").val();
			var sub = $("#msgdiv #subject").val();
			var ctx = $("#msgdiv #ctx").val();
			if(email == ""){
				msginfo("请填写邮件地址。");
				return;
			}
			if(sub == ""){
				msginfo("请填写主题");
				return;
			}
			$("#msgdiv").modal("hide");
			__showLoading();
			$.ajax({
				url:"../report/sendMail.action",
				type:"POST",
				data:{rid:row[0].pageId, toAddress:email, subject:sub, content:ctx },
				dataType:"json",
				success:function(r){
					__hideLoading();
					if(r.result == 1){
						msginfo("邮件发送成功。", "success");
						
					}else{
						msginfo("错误信息：" + r.msg);
					}
				},
				error:function(){
					__hideLoading();
				}
			});
		}else{  //复制url
			var islog = $("#msgdiv input[name='islogin']:checked").val();
			var yxq = $("#msgdiv input[name='yxq']:checked").val(); 
			if(yxq == "2"){
				yxq = "24";
			}
			$("#msgdiv").modal("hide");
			__showLoading();
			$.ajax({
				url:"../report/copyUrl.action",
				type:"POST",
				data:{reportId:row[0].pageId, islogin:islog, yxq:yxq },
				dataType:"json",
				success:function(r){
					__hideLoading();
					if(r.result == 1){
						msginfo(r.rows, "success");
					}else{
						msginfo("错误信息：" + r.msg);
					}
				},
				error:function(){
					__hideLoading();
				}
			});
		}
	});
	**/
}
</script>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content">
 <div class="row">
 <div class="col-sm-3">
	<div class="ibox float-e-margins">
		<div class="ibox-content" style="border:0px;">
		<button onclick="location.href='customization.action'" class="btn btn-sm btn-block btn-primary">新建报表</button>
<p class="text-warning">定制个性化的数据可视化界面</p>
<h5 style="margin-top:20px;">分类
<a href='javascript:addType(false);'><span class="label label-primary pull-right"><i class="fa fa-plus"></i></span></a>
</h5>
<ul class="folder-list" style="padding: 0">
 <c:forEach var="e" items="${typels}" varStatus="statu">
	<li class="reportTypes" tid="${e.id}"><a href="javascript:;"><i class="fa fa-folder"></i>${e.text}</a></li> 
 </c:forEach>
</ul>
		</div>
	</div>
 </div>
<div class="col-sm-9  animated fadeInRight">
<div class="ibox">
	<div class="ibox-content" style="border:0px;">
		<div class="mail-box-header">
		<h2>报表列表</h2>
		</div>
		<div style="margin-bottom:10px;" class="btn-group" role="group">
			<button type="button" class="btn btn-outline btn-default" title="查看" onclick="viewReport()">
				<i class="glyphicon glyphicon-file" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="定制" onclick="editreport()">
				<i class="glyphicon glyphicon-cog" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="分享" onclick="sharer()">
				<i class="glyphicon glyphicon-share" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="改名" onclick="renamereport()">
				<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delreport()">
				<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
			</button>
		</div>
		  <table id="reportlist" data-toggle="table" data-pagination="true" data-page-size="20" data-click-to-select="true" data-url="listReports.action" data-side-pagination="server">
		  <thead>
		  <tr>
			<th data-radio="true"></th>
		   <th data-field="pageName" data-sortable="true">报表名称</th>
			<th data-field="cataName">分类</th>
		   <th data-field="userName">创建人</th>
		   <th data-field="crtDate" data-sortable="true">创建时间</th>
		   <th data-field="updateDate" data-sortable="true">修改时间</th>
		   </tr>
		   </thead>
		   </table>
	</div>
 </div> 
 </div>
 </div>
</div>
<div id="pdailog"></div>
<div id="menus" class="easyui-menu" style="width:140px;">
 	<div iconCls="icon-edit" onclick="addType(true)" >编辑</div>
    <div iconCls="icon-remove" onclick="deleteType()" >删除</div>
</div>

</body>
</html>