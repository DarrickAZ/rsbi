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
<title>手机报表管理</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/sweetalert/sweetalert.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="../resource/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../resource/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../resource/jquery-easyui-1.4.4/themes/icon.css">
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../resource/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../resource/bootstrap-table/bootstrap-table.min.css">
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="../resource/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
</head>
<script language="javascript">
jQuery(function(){
	var dt = [{id:'zty', text:'手机报表分类', iconCls:'icon-subject', children:${str}}];
	$("#typetree").tree({
		data:dt,
		onClick:function(node){
			var type = node.id;
			if(type == "zty"){
				type = "";
			}
			$('#cubelist').bootstrapTable('refresh',{query:{
				cataId: type,
				t:Math.random()
			}});
		}
	});
});
function sharer(){
	var row = $("#cubelist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	$("#msgdiv").modal("show");
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
				url:"sendMail.action",
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
				url:"copyUrl.action",
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
}
function editr(){
	var row = $("#cubelist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	editReport(row[0].pageId);
}
function delr(){
	var row = $("#cubelist").bootstrapTable("getSelections");
	if(row.length == 0){
		$.messager.alert("出错了。","您还未勾选数据。", "error");
		return;
	}
	var data = row[0];
	if(confirm("是否确认删除？")){
		$.ajax({
			url:"pushDel.action",
			type:"GET",
			data:{id:data.pageId},
			dataType:"json",
			success:function(){
				$('#cubelist').bootstrapTable('refresh',{
					query:{t:Math.random()}
				});
			}
		});
	}
}
function editReport(id){
	var url = '../portal/customization.action?is3g=y&pageId='+id+"&menus=%7bback%3a0%2cprint%3a0%2cexport%3a0%7d";
	var tb = [{
		iconCls:'icon-back',
		text:"返回",
		handler:function(){
			var win = document.getElementById("reportInfo").contentWindow;
			if(win.curTmpInfo && win.curTmpInfo.isupdate == true){
				$.messager.confirm("请确认","报表未保存，是否确认关闭？", function(r){
					if(r){
						$("#pdailog").dialog("close");
					}
				});
			}else{
				$("#pdailog").dialog("close");
			}
		}
	}];
	var obj = {
		fit:true,
		border:false,
		closed: false,
		cache: false,
		modal: false,
		noheader:true,
		content:"<iframe id=\"reportInfo\" name=\"reportInfo\" src=\""+url+"\" frameborder=\"0\" width=\"100%\" height=\"100%\"></iframe>",
		toolbar:tb
	};
	$('#pdailog').dialog(obj);
}
</script>
<body class="gray-bg">
<bi:mobileTop/>
<div class="wrapper wrapper-content">
 <div class="row">
				<div class="col-sm-3">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>报表分类</h5>
                    </div>
<div class="ibox-content">

 <ul id="typetree"></ul>

 </div>

</div>
 </div>
 
   <div class="col-sm-9 animated fadeInRight">
   
			<div class="ibox">
				
			<div class="ibox-content" style="border:0px;">
		<div class="mail-box-header">
		<h2>报表列表</h2>
		</div>
		<div style="margin-bottom:10px;" class="btn-group" role="group">
			<button type="button" class="btn btn-outline btn-default" title="新建" onclick="location.href='../portal/customization.action?is3g=y&menus=%7bprint%3a0%2cexport%3a0%7d'">
				<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="分享" onclick="sharer()">
				<i class="glyphicon glyphicon-share" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="编辑" onclick="editr()">
				<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
			</button>
			<button type="button" class="btn btn-outline btn-default" title="删除" onclick="delr()">
				<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
			</button>
		</div>

	  <table id="cubelist" data-toggle="table" data-click-to-select="true" data-url="pushList.action" >
      <thead>
      <tr>
      	<th data-radio="true"></th>
       <th data-field="pageName">名称</th>
        <th data-field="cataName">分类</th>
       <th data-field="userName">创建人</th>
       <th data-field="crtDate">创建时间</th>
       <th data-field="updateDate">修改时间</th>
       </tr>
       </thead>
       </table>
			</div>
			</div>

	 </div>
 </div>
</div>
   
<div class="modal inmodal fade" id="msgdiv" role="dialog"  aria-hidden="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">报表分享</h4>
				</div>
				<div class="modal-body" style="padding:3px;">
					<div class="tabs-container">
						<ul class="nav nav-tabs">
						   <li class="active" tp="mail"><a href="#mail" data-toggle="tab">通过邮件分享</a>
						   </li>
						   <li tp="url"><a href="#url" data-toggle="tab">直接生成URL</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="mail">
								<div class="panel-body" style="padding:20px;overflow:auto;min-height:250px;">
									<span class="inputtext">邮件地址：</span><input type="text" name="email" id="email" class="inputform"><br/><br/>
									<span class="inputtext">主题： </span><input type="text" name="subject" id="subject" class="inputform"><br/><br/>
									<span class="inputtext">内容： </span><input type="text" name="ctx" id="ctx" class="inputform"><br/>
									<p class="text-warning">说明：报表以附件形式发送给用户。</p>
								</div>
							</div>
							 <div class="tab-pane" id="url">
								<div class="panel-body" style="padding:20px;overflow:auto;min-height:250px;">
									<span class="inputtext">是否需要登录： </span>
									<div class="radio radio-info radio-inline"><input id="isloginy" name="islogin" type="radio" value="1" checked><label for="isloginy">是</label></div>
									<div class="radio radio-info radio-inline"><input id="isloginn" name="islogin" type="radio" value="0"><label for="isloginn">否</label></div>
									<br/><br/>
									<span class="inputtext">有效期： </span>
									<div class="radio radio-info radio-inline"><input type="radio" id="yxq1" name="yxq" value="1" checked><label for="yxq1">一小时</label></div>
									<div class="radio radio-info radio-inline"><input type="radio" id="yxq2" name="yxq" value="2"><label for="yxq2">一天</label></div>
									<div class="radio radio-info radio-inline"><input type="radio" id="yxq3" name="yxq" value="-1"><label for="yxq3">永久有效</label></div> <br/> <br/>
									<p class="text-warning">说明：生成的URL可以直接通过微信/QQ发送给用户。</p>
								</div>
							 </div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-success" id="savebtn"> 确定 </button>
					<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
<div id="pdailog"></div>
</body>
</html>