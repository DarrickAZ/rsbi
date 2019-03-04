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
<title>从Excel文件导入数据</title>
<link rel="shortcut icon" type="image/x-icon" href="../resource/img/rs_favicon.ico">
<link href="../ext-res/css/bootstrap.min.css" rel="stylesheet">
<link href="../resource/css/animate.css" rel="stylesheet">
<link href="../resource/css/style.css" rel="stylesheet">
<link href="../resource/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../resource/webuploader/webuploader.css">
<script type="text/javascript" src="../ext-res/js/jquery.min.js"></script>
<script type="text/javascript" src="../ext-res/js/bootstrap.min.js?v=3.3.6"></script>
<script language="javascript" src="../resource/js/etl.js"></script>
<script type="text/javascript" src="../ext-res/js/ext-base.js"></script>
<script type="text/javascript" src="../resource/webuploader/webuploader.html5only.min.js"></script>
</head>

<script language="javascript">
$(function() {
	 var $ = jQuery,
        $list = $('#thelist'),
        state = 'pending',
        uploader;

    uploader = WebUploader.create({

        // 不压缩image
        resize: false,

        // swf文件路径
        swf:  + '../resource/webuploader/Uploader.swf',

        // 文件接收服务端。
        server: 'FileUpload.action?filetype=xls&cfgid=${param.cfgid}',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
		accept:{
			title: 'Excel文件',
			extensions: 'xls,xlsx',
			mimeTypes: ''
		},
		auto:true
    });

    uploader.on("uploadStart", function(){
		__showLoading();
	});

    uploader.on( 'uploadSuccess', function( file ) {
		__hideLoading();
        location.href = 'ImportXls2.action?method='+$("#method").val()+'&cfgid='+$("#cfgid").val();
    });

    uploader.on( 'uploadError', function( file ) {
		__hideLoading();
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'error', function( errTp ) {
        if(errTp == "Q_TYPE_DENIED"){
			alert("文件类型错误。");
		};
    });
});

</script>


<body class="gray-bg">
<bi:mobileTop/>
<input type="hidden" name="method" id="method" value="${param.method}">
<input type="hidden" name="cfgid" id="cfgid" value="${param.cfgid}">
<div class="wrapper wrapper-content animated fadeInDown">

 <div class="row">
				<div class="col-sm-12">
	<div class="ibox">

					<div class="ibox-title">
                        <h5>从Excel文件导入数据</h5>
                    </div>
<div class="ibox-content">
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="edittable">

 <tr>
    <td class="header hctx" width="120" height="100" valign="top">上传文件：
    </td>
    <td colspan="1" class="hctx">
    
	<div id="uploader" class="wu-example">
		<!--用来存放文件信息-->
		<div id="thelist" class="uploader-list"></div>
		<div class="btns">
			<div id="picker" style="display:inline-block;">选择文件</div>
		</div>
	</div>
	
     <br/>
    <p class="text-warning"> 1.请上传明细数据，第一行为数据表头，后面为数据。</p>
     <p class="text-warning"> 2.请不要包含标题。</p>
    <p class="text-warning">3.支持xls/xlsx格式。</p>
    <p class="text-warning">4.文件大小限制50M以内。</p>
    数据格式如下图：<br/>
    <img src='../resource/img/exceluppic.png'>
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