/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
﻿if($ == undefined){
	$ = jQuery;
}
//创建填报表
function newtable(divId){
	var ctx = " <div id=\"datatab\" data-options=\"fit:true,tabPosition:'left'\"><div title=\"表信息\"><div class=\"textpanel\"><span class=\"inputtext\">填报表名称:</span><input placeholder=\"英文字符\" type=\"text\" id=\"tname\" name=\"tname\" class=\"inputform\"> <br/><span class=\"inputtext\">中文名称:</span><input type=\"text\" id=\"tnote\" name=\"tnote\" class=\"inputform\"><br/><span class=\"inputtext\">表备注信息:</span><input type=\"text\" id=\"tdesc\" name=\"tdesc\" class=\"inputform\"></div></div><div title=\"表字段\"> <div class=\"textpanel\" style=\"margin:3px;\"><table id=\"tablecol\"><thead><tr><th data-options=\"field:'x',checkbox:true\"></th><th data-options=\"field:'colName',width:120,align:'center'\">字段名</th><th data-options=\"field:'colNote',width:120,align:'center'\">中文名</th><th data-options=\"field:'colType',width:90,align:'center',formatter:fmttype\">类型</th><th data-options=\"field:'colLength',width:90,align:'center'\">长度</th><th data-options=\"field:'inputtype',width:100,align:'center',formatter:fmtinput\">输入方式</th><th data-options=\"field:'defvalue',width:100,align:'center'\">默认值</th></tr></thead></table></div></div></div>";
	$("#"+divId).html(ctx);
	$("#"+divId+" #datatab").tabs({});
	var toolbar = [{
		text:'添加字段',
		iconCls:'icon-add',
		handler:function(){
			addTableCol('create');
		}
	},{
		text:'编辑字段',
		iconCls:'icon-edit',
		handler:function(){
			var row = $("#tablecol").datagrid("getSelected");
			if(row == null){
				 $.messager.alert("出错了","请勾选数据。","error");
				return;
			}
			addTableCol('create',true, row.tmpid);
		}
	},{
		text:'删除字段',
		iconCls:'icon-remove',
		handler:function(){
			var row = $("#tablecol").datagrid("getSelected");
			if(row == null){
				 $.messager.alert("出错了","请勾选数据。","error");
				return;
			}
			if(confirm("是否确认？")){
				var idx = -1;
				for(i=0; i<tabinfo.metaCols.length; i++){
					var col = tabinfo.metaCols[i];
					if(col.tmpid == row.tmpid){
						idx = i;
						break;
					}
				}
				tabinfo.metaCols.splice(idx, 1);
				$("#tablecol").datagrid("loadData", tabinfo.metaCols);
			}
		}
	}];
	$("#tablecol").datagrid({
		toolbar:toolbar,
		data:tabinfo.metaCols,
		singleSelect:true
	});
}
function savetables(){
	var tname = $("#datatab #tname").val();
	var tnote = $("#datatab #tnote").val();
	var tdesc = $("#datatab #tdesc").val();
	if(tname == ""){
		 $.messager.alert("出错了","填报表名称是必填项。","error", function(){
			 $("#datatab").tabs("select", 0);
			 $("#datatab #tname").focus();
		 });
		 return;
	}
	if(ischinese(tname)){
		 $.messager.alert("出错了","填报表名称必须是英文字符。","error", function(){
			 $("#datatab").tabs("select", 0);
			 $("#datatab #tname").select();
		 });
		 return;
	}
	var reg = /^[a-z|A-Z]/;
	var rt = reg.test(tname);
	if(rt==false){
		$.messager.alert('出错了','表名必须以字母开始。', "error", function(){
			$("#datatab #tname").select();
		});
		return;
	}
	if(tabinfo.metaCols.length == 0){
		$.messager.alert("出错了","您还未定义填报表的表字段。","error", function(){
			 $("#datatab").tabs("select", 1);
		 });
		 return;
	}
	//判断表名是否重复
	var exist;
	$.ajax({
	   type: "POST",
	   async:false,
	   url: "../etl/tableExist.action",
	   dataType:"json",
	   data: {"tableName":tname},
	   success: function(resp){
		   exist = resp.rows;
	   },
	   error:function(er){
		  exist = -1;
	   }
	});
	if(exist == -1){
		$.messager.alert('出错了','网络错误。', "error");
		return;
	}
	if(Number(exist) >= 1){
		$("#ntable").tabs("select", 0);
		$.messager.alert('出错了','表名存在重复。', "error", function(){
			$("#datatab #tname").select();
		});
		return;
	}
					
	tabinfo.tableName = tname;
	tabinfo.tableNote = tnote;
	tabinfo.tableDesc = tdesc;
	$.ajax({
		type: "POST",
		url: "saveTable.action",
		dataType:"json",
		contentType : "application/json",
		data: JSON.stringify(tabinfo),
		success: function(resp){
			if(resp.result == 1){
				$.messager.alert('成功','表创建成功。', "info", function(){
					location.href = 'DataWrite.action';
				});
			}else{
				$.messager.alert('出错了',resp.msg, "error");
			}
		},
		error:function(){
			$.messager.alert('出错了','系统异常。', "error");
		}
	});
}
function fmttype(a){
	if(a == "String"){
		return "字符串";
	}else if(a == "Int"){
		return "整数";
	}else if(a == "Double"){
		return "小数";
	}else if(a == "Date"){
		return "日期";
	}else if(a == "Datetime"){
		return "时间";
	}
}
function addTableCol(state, isupdate, tmpid){
	var o;
	if(isupdate){
		if(state == "create"){
			for(i=0; i<tabinfo.metaCols.length; i++){
				var col = tabinfo.metaCols[i];
				if(col.tmpid == tmpid){
					o = col;
					break;
				}
			}
		}else{
			o =  $("#tablecol").datagrid("getSelected") ;
		}
	}
	var valOptions = isupdate? (o.options == null ? [] : o.options.split(",")) :[]; window.valOptions = valOptions;  //定义值列表，当录入类型是选择的时候，需要用到值列表
	var row = $("#tablegrid").size() == 0 ? null : $("#tablegrid").bootstrapTable("getSelections")[0] ;
	if($("#dsColumn_div").size() == 0){
		$("<div id=\"dsColumn_div\"></div>").appendTo("body");
	}
	var tps = [{value:'String',text:'字符串'},{value:'Int',text:'整数'},{value:'Double',text:'小数'},{value:'Date',text:'日期'}, {value:'Datetime',text:'时间'}];
	var str = "<option value=''></option>";
	for(i=0; i<tps.length; i++){
		str = str + "<option value='"+tps[i].value+"' "+(o&&o.colType==tps[i].value?"selected":"")+">" + tps[i].text + "</option>";
	}
	var values = reloadParamVals(valOptions, true);
	//获取字段
	var cols = $("#tablecol").datagrid("getRows");
	var upcols = "<option value=\"\"></option>";
	for(i=0; i<cols.length; i++){
		upcols = upcols + "<option value=\""+cols[i].colName+"\" "+(o&&o.updateCol==cols[i].colName?"selected":"")+">"+cols[i].colName+"("+cols[i].colNote+")"+"</option>";
	}
	var content = "<div style='float:left; width:370px; margin:10px; line-height:30px;'><span class=\"inputtext\">字段名：</span><input type='text' id='colname' name='colname' class='inputform' value='"+(o?o.colName:"")+"' "+(isupdate?"readOnly='true'":"")+" ><br/><span class=\"inputtext\">中文名：</span><input type='text' id='note' name='note' class='inputform' value='"+(o?o.colNote:"")+"'><br/><span class=\"inputtext\">字段类型：</span><select id='coltype' name='coltype' class='inputform' onchange='chgcolumntype()'>"+str+"</select><br/><span class=\"inputtext\">字段长度：</span><input type='text' id='collength' name='collength' style='width:80px;' class=\"inputform\" value='"+(o?(o.colLength==null?"":o.colLength):"")+"'><span class=\"inputtext\" style=\"text-align:right;\">精度：</span><input type='text' id='colScale' name='colScale' class=\"inputform\" style='width:80px;' value='"+(o?(o.colScale==null?"":o.colScale):"")+"'><br/><span class=\"inputtext\">默认值：</span><input type='text' id='defvalue' name='defvalue' class='inputform' value='"+(o&&o.defvalue!=null?o.defvalue:"")+"'><br/><span class=\"inputtext\">录入类型：</span><div style=\"line-height:20px;\" class=\"radio radio-info radio-inline\"><input type='radio' id='inputtype1' "+(!o||(o&&o.inputType=='input')?"checked":"")+" name='inputtype' value='input'><label for=\"inputtype1\">录入</label></div> <div style=\"line-height:20px;\" class=\"radio radio-info radio-inline\"><input type='radio' id='inputtype2' name='inputtype' value='select' "+(o&&o.inputType=='select'?"checked":"")+"><label for=\"inputtype2\">选择</label></div> <br/><span class=\"inputtext\">录入提示信息：</span><input type='text' id='cdesc' name='cdesc' class='inputform' value='"+(o?(o.colDesc==null?"":o.colDesc):"")+"'></div><div id=\"values\" style=\"float:right;width:250px;;display:"+(!o||o.inputType=='input'?"none":"block")+"\"><div class=\"radio radio-info radio-inline\"><input type='radio' id=\"valuestype1\" name='valuestype' value='static' "+(!o||!o.valuestype||o.valuestype == "static"?"checked":"")+"><label for=\"valuestype1\">静态值</label></div> <div class=\"radio radio-info radio-inline\"><input type='radio' id='valuestype2' name='valuestype' value='dtz' "+(o&&o.valuestype=="dtz"?"checked":"")+"><label for=\"valuestype2\">动态值</label></div><div id=\"staticdiv\"><div class=\"param_left\"><table cellspacing=\"0\" cellpadding=\"0\" class=\"grid3\"><tr><th width='50%'>值</th><th>操作</th></tr>"+values+"</table></div><div class=\"param_right\"><a href=\"javascript:;\" id=\"crtvalbtn\"><img src='../resource/img/edit_add.png' border='0' title='新增'></a><br/></div></div><div id=\"dtzdiv\" style=\"display:none;line-height:35px;\"><span class=\"inputtext\" style=\"width:96px;\">映射表：</span><input type=\"text\" id=\"matchTable\" style=\"width:80px;\" class=\"inputform\" value="+(o&&o.matchTable?o.matchTable:"")+"><button type=\"button\" class=\"btn btn-primary\" id=\"seltable\">选表</button><br/><span class=\"inputtext\" style=\"width:96px;\">表ID字段：</span><select id=\"matchCol\" style=\"width:100px;\" class=\"inputform\"></select><br/><span class=\"inputtext\" style=\"width:96px;\">表文本字段：</span><select id=\"matchColText\" style=\"width:100px;\" class=\"inputform\"></select><br/><span class=\"inputtext\" style=\"width:96px;\">通过：</span><select id=\"useCol\" style=\"width:96px;\" class=\"inputform\"></select>字段<br/><span class=\"inputtext\" style=\"width:96px;\">更新：</span><select id=\"updatecol\" class=\"inputform\" style=\"width:96px;\">"+upcols+"</select>字段<br/><span class=\"inputtext\" style=\"width:96px;\">过滤条件：</span><textarea id=\"condition\" class=\"inputform\" style=\"width:170px; height:28px;\">"+(o&&o.tCondition != null ?o.tCondition:"")+"</textarea></div></div>";
	$('#dsColumn_div').dialog({
		title: isupdate ? '编辑字段' :'添加字段',
		width: isupdate && o.inputType == "select" ?　660 : 400,
		height: 320,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content:content,
		onLoad:function(){},
		onClose:function(){
			delete window.valOptions;
			$('#dsColumn_div').dialog('destroy');
		},
		buttons:[{
				text:'确定',
				iconCls:"icon-ok",
				handler:function(){
					var colname = $("#dsColumn_div #colname").val();
					var coltype = $("#dsColumn_div #coltype").val();
					var collength = $("#dsColumn_div #collength").val();
					var colScale = $("#dsColumn_div #colScale").val();
					var note = $("#dsColumn_div #note").val();
					var defvalue = $("#dsColumn_div #defvalue").val(); 
					var inputtype = $("#dsColumn_div input[name=\"inputtype\"]:checked").val();
					var desc = $("#dsColumn_div #cdesc").val();
					var valuestype = $("#dsColumn_div input[name=\"valuestype\"]:checked").val();
					var matchTable = $("#dsColumn_div #matchTable").val();
					var matchCol = $("#dsColumn_div #matchCol").val();
					if(matchCol == null){
						matchCol = "";
					}
					var useCol = $("#dsColumn_div #useCol").val();
					if(useCol == null){
						useCol = "";
					}
					var matchColText = $("#dsColumn_div #matchColText").val();
					if(matchColText == null){
						matchColText = "";
					}
					var updatecol = $("#dsColumn_div #updatecol").val();
					var condition = $("#dsColumn_div #condition").val();
					var opts = "";
					for(k=0; k<valOptions.length; k++){
						opts = opts + valOptions[k];
						if(k != valOptions.length - 1){
							opts = opts + ",";
						}
					}
					if(colname == ""){
						 $.messager.alert("出错了","请录入字段名。","error", function(){
							 $("#dsColumn_div #colname").focus();
						 });
						 return;
					}
					if(ischinese(colname)){
						 $.messager.alert("出错了","字段名必须是英文字符。","error", function(){
							 $("#dsColumn_div #colname").focus();
						 });
						 return;
					}
					if(coltype == ""){
						 $.messager.alert("出错了","请选择字段类型。","error", function(){
							 $("#dsColumn_div #coltype").focus();
						 });
						 return;
					}
					if(collength == "" && coltype != "Date" && coltype != "Datetime"){
						 $.messager.alert("出错了","请录入字段长度。","error", function(){
							 $("#dsColumn_div #collength").focus();
						 });
						 return;
					}
					if(inputtype == "select"){
						if(valuestype == "static" && opts == ""){
							 $.messager.alert("出错了","请录入值列表。","error", function(){
								
							 });
							 return;
						}
						if(valuestype == "dtz"){
							if(matchTable == ""){
								$.messager.alert("出错了","请选择映射表。","error");
								return;
							}
							if(matchCol == ""){
								$.messager.alert("出错了","请选择对应字段。","error");
								return;
							}
						}
					}
					if(state == "create"){
						if(isupdate){
							o.colName = colname;
							o.colType = coltype;
							o.colLength = collength;
							o.colScale = colScale;
							o.colNote = note;
							o.defvalue = defvalue;
							o.inputType = inputtype;
							o.colDesc = desc;
							o.options = opts;
							o.valuestype = valuestype;
							o.matchTable = matchTable;
							o.matchCol = matchCol;
							o.useCol = useCol;
							o.updateCol = updatecol;
							o.tCondition = condition;
							o.matchColText = matchColText;
						}else{
							var col = {tmpid:newGuid(),colName:colname,colType:coltype,colLength:collength,colScale:colScale, colNote:note,defvalue:defvalue,inputType:inputtype,colDesc:desc,options:opts, valuestype:valuestype,matchTable:matchTable,matchCol:matchCol,useCol:useCol,updateCol:updatecol,tCondition:condition,matchColText:matchColText};
							tabinfo.metaCols.push(col);
						}
						//重新加载表格内容
						$("#tablecol").datagrid("loadData", tabinfo.metaCols);
					}else{
						if(isupdate){
							$.ajax({
								type:"post",
								url:"../etl/updateTableColumn.action",
								dataType:"json",
								data:{colName:colname, colType:coltype, colLength:collength, colScale:colScale, colNote:note,inputType:inputtype,defvalue:defvalue,tableId:row.tableId,tableName:row.tableName, colId:o.colId, colDesc:desc, options:opts,valuestype:valuestype,matchTable:matchTable,matchCol:matchCol,useCol:useCol,updateCol:updatecol,tCondition:condition,matchColText:matchColText},
							    success: function(resp){
								 	$("#tablecol").datagrid("reload", {t:Math.random()});
							    },
								error: function(resp){
									$.messager.alert("出错了","系统出错。","error");
								}
							});
						}else{
							$.ajax({
								type:"post",
								url:"../etl/addTableColumn.action",
								dataType:"json",
								data:{colName:colname, colType:coltype, colLength:collength, colScale:colScale, colNote:note,inputType:inputtype,defvalue:defvalue,tableId:row.tableId,tableName:row.tableName,colDesc:desc, options:opts,valuestype:valuestype,matchTable:matchTable,matchCol:matchCol,useCol:useCol,updateCol:updatecol,tCondition:condition,matchColText:matchColText},
							    success: function(resp){
								 	$("#tablecol").datagrid("reload", {t:Math.random()});
							    },
								error: function(resp){
									$.messager.alert("出错了","系统出错。","error");
								}
							});
						}
					}
					
					
					$('#dsColumn_div').dialog('close');
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#dsColumn_div').dialog('close');
				}
			}]
	});
	$("#dsColumn_div input[name=\"inputtype\"]").bind("click", function(){
		var val = $(this).val();
		if(val == 'input'){
			$("#values").css("display", "none");
			$('#dsColumn_div').dialog("resize", {width:400,height:320});
		}else{
			$("#values").css("display", "block");
			$('#dsColumn_div').dialog("resize", {width:660,height:320});
		}
	});
	$("#dsColumn_div #crtvalbtn").click(function(){
		newparamval(false);
	});
	var checkeddiv = function(val){
		if(val == "static"){
			$("#staticdiv").css("display", "inherit");
			$("#dtzdiv").css("display", "none");
		}else{
			$("#staticdiv").css("display", "none");
			$("#dtzdiv").css("display", "inherit");
		}
	}
	if(o&&o.valuestype){
		checkeddiv(o.valuestype);
	}
	$("#dsColumn_div input[name=\"valuestype\"]").click(function(){
		checkeddiv($(this).val());
	});
	if(o&&o.matchTable){
		getTableColumns(o.matchTable, o.matchCol, o.useCol, o.matchColText);
	}
	$("#dsColumn_div #seltable").click(function(){
		if($("#seltable_div").size() == 0){
				$("<div id=\"seltable_div\"></div>").appendTo("body");
		}
		var ctx = "";
		$.ajax({
		   type: "POST",
		   async:false,
		   url: "../etl/dwselectTables.action",
		   dataType:"json",
		   data: {t:Math.random()},
		   success: function(resp){
			   for(i=0; i<resp.length; i++){
				   ctx = ctx + "<input type=\"radio\" id=\"T"+i+"\" name=\"tableId\" value=\""+resp[i].tableId+","+resp[i].tableName+"\"><label for=\"T"+i+"\">" + resp[i].tableName + "("+resp[i].tableNote+")</label><br/>";
			   }
		   }
		});
		
		$('#seltable_div').dialog({
			title: '选择表',
			width: 420,
			height: 290,
			closed: false,
			cache: false,
			modal: true,
			toolbar:null,
			content:ctx,
			onLoad:function(){},
			onClose:function(){
				$('#seltable_div').dialog('destroy');
			},
			buttons:[{
					text:'确定',
					iconCls:"icon-ok",
					handler:function(){
						var r = $("#seltable_div input[name=\"tableId\"]:checked").val();
						if(!r || r == null){
							$.messager.alert("出错了","请至少选择一个数据表！", "error");
							return;
						}
						var strs = r.split(",");
						var tid = (strs[0]);
						var tname = (strs[1]);
						$("#dsColumn_div #matchTable").val(tname);
						getTableColumns(tname);
						$('#seltable_div').dialog('close');
					}
				},{
					text:'取消',
					iconCls:"icon-cancel",
					handler:function(){
						$('#seltable_div').dialog('close');
					}
				}]
		});
	});
}
function getTableColumns(tableName, selcol1, selcol2,selcol3){
	$.ajax({
		type: "POST",
		url: "../etl/listTableColumns.action",
		dataType:"json",
		data: {"tname":tableName,t:Math.random()},
		success: function(cols){
			var str = "<option value=\"\"></option>";
			var str3 = "<option value=\"\"></option>";
			for(i=0; i<cols.length; i++){
				str = str +　"<option value=\""+cols[i].name+"\" "+(selcol1&&selcol1==cols[i].name?"selected":"")+">"+cols[i].name+"</option>";
				str3 = str3 +　"<option value=\""+cols[i].name+"\" "+(selcol3&&selcol3==cols[i].name?"selected":"")+">"+cols[i].name+"</option>";
			}
			$("#dsColumn_div #matchCol").html(str);
			$("#dsColumn_div #matchColText").html(str3);
			var str2 = "<option value=\"\"></option>";
			for(i=0; i<cols.length; i++){
				str2 = str2 +　"<option value=\""+cols[i].name+"\" "+(selcol2&&selcol2==cols[i].name?"selected":"")+">"+cols[i].name+"</option>";
			}
			$("#dsColumn_div #useCol").html(str2);
		},
		error:function(){
			$.messager.alert('出错了','系统异常。', "error");
		}
	});
}
function chgcolumntype(){
	var val = $("#dsColumn_div #coltype").val();
	if(val == "Date"){
		$("#collength").val("");
		$("#colScale").val("");
		$("#collength").attr("readOnly", "readOnly");
	}else if(val == "String"){
		$("#collength").val("50");
		$("#colScale").val("");
	}else if(val == "Double"){
		$("#collength").val("12");
		$("#colScale").val("2");
	}else if(val == "Int"){
		$("#collength").val("10");
		$("#colScale").val("");
	}
}
function newparamval(isupdate, valId){
	if($("#paramAddDiv").size() == 0){
		$("<div id=\"paramAddDiv\" class=\"easyui-menu\"></div>").appendTo("body");
	}
	var t = null;
	if(isupdate){
		t = valId;
	}
	var ctx = "<div class=\"textpanel\"><span class=\"inputtext\">待选值：</span><input type=\"text\"  id=\"val\" name=\"val\" class=\"inputform\" value=\""+(t==null?"":t)+"\"></div>";
	$('#paramAddDiv').dialog({
		title: (isupdate == false ? '添加值':'编辑值'),
		width: 330,
		height: 160,
		closed: false,
		cache: false,
		modal: true,
		content:ctx,
		onClose:function(){
			$('#paramAddDiv').dialog('destroy');
		},
		buttons:[{
				text:'确定',
				iconCls:"icon-ok",
				handler:function(){
					var v = $("#paramAddDiv #val").val();
					if(v == ""){
						$.messager.alert("出错了","请填写值。","error");
						return;
					}
					if(isupdate){
						for(i=0; i<window.valOptions.length; i++){
							if(window.valOptions[i] == valId){
								window.valOptions[i] = v;
							}
						}
					}else{
						window.valOptions.push(v);
					}
					reloadParamVals(window.valOptions);
					$('#paramAddDiv').dialog('close');
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#paramAddDiv').dialog('close');
				}
			}]
	});
}
function reloadParamVals(valOptions, isreturn){
	$("#dsColumn_div #values table tr").each(function(index, b){
		if(index > 0){
			$(this).remove();
		}
	});
	var str = "";
	for(var i=0; i<valOptions.length; i++){
		var o = valOptions[i];
		str = str + "<tr><td  class=\"kpiData1 grid3-td\">"+o+"</td><td class=\"kpiData1 grid3-td\"><a href=\"javascript:newparamval(true,'"+o+"');\"><img title='编辑' src='../resource/img/pencil.png' border='0'></a> &nbsp; <a href=\"javascript:paramdelvals('"+o+"');\"><img title='删除' src='../resource/img/closeme.png' border='0'></a></td></tr>";
	}
	for(var i=valOptions.length; i<5; i++){
		str = str + "<tr>";
		for(j=0; j<2; j++){
			str = str + "<td class=\"kpiData1 grid3-td\"> &nbsp; </td>";
		}
		str = str + "</tr>";
	}
	if(isreturn == true){
		return str;
	}else{
		$(str).insertAfter("#dsColumn_div #values table tr");
	}
}
function paramdelvals(val){
	var idx = -1;
	for(var i=0; i<window.valOptions.length; i++){
		if(window.valOptions[i] == val){
			idx = i;
			break;
		}
	}
	window.valOptions.splice(idx, 1);
	reloadParamVals(window.valOptions, false);
}

function deltable(){
	 var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	if(confirm("是否确认删除？")){
		$.ajax({
			type:"post",
			url:"delTable.action",
			dataType:"json",
			data:{tableId:row.tableId},
		   success: function(resp){
				if(resp.result == 1){
					$("#tablegrid").bootstrapTable("refresh", {query:{t:Math.random()}});
				}else{
					$.messager.alert("出错了",resp.msg,"error");
				}
			 	
		   },
		   error: function(){
				$.messager.alert("出错了","系统出错。","error");
		   }
		});
	}
}
function viewtable(){
	 var row = $("#tablegrid").bootstrapTable("getSelections");
	 if(row.length == 0){
		 $.messager.alert("出错了","请先选择数据。","error");
		 return;
	 }
	 row = row[0];
	 var ctx = "<div id=\"datatab\" data-options=\"fit:true,tabPosition:'left'\"><div title=\"基本信息\"><div class=\"textpanel\"><span class=\"inputtext\">填报表名称:</span><input type=\"text\" id=\"tname\" name=\"tname\" class=\"inputform\" value=\""+row.tableName+"\" readonly> (不可更改) <br/><span class=\"inputtext\">中文名称:</span><input type=\"text\" id=\"tnote\" name=\"tnote\" class=\"inputform\" value=\""+row.tableNote+"\"><br/><span class=\"inputtext\">备注信息:</span><input type=\"text\" id=\"tdesc\" name=\"tdesc\" class=\"inputform\" value=\""+row.tableDesc+"\"></div></div><div title=\"表字段\"> <div class=\"textpanel\" style=\"margin:3px;\"><table id=\"tablecol\"><thead><tr><th data-options=\"field:'x',checkbox:true\"></th><th data-options=\"field:'colName',width:100,align:'center'\">字段名</th><th data-options=\"field:'colNote',width:120,align:'center'\">中文名</th><th data-options=\"field:'colType',width:70,align:'center',formatter:fmttype\">类型</th><th data-options=\"field:'colLength',width:70,align:'center'\">长度</th><th data-options=\"field:'colScale',width:70,align:'center'\">精度</th><th data-options=\"field:'inputType',width:70,align:'center',formatter:fmtinput\">输入方式</th><th data-options=\"field:'defvalue',width:80,align:'center'\">默认值</th></tr></thead></table></div></div><div title=\"数据预览\" href=\"../etl/queryTableData.action?tableId="+row.tableId+"&tableName="+row.tableName+"\"></div></div>";
	 $('#pdailog').dialog({
		title: '填报表信息',
		width: 800,
		height: 460,
		closed: false,
		cache: false,
		modal: true,
		toolbar:null,
		content:ctx,
		onLoad:function(){},
		buttons:[{
				text:'确定',
				iconCls:"icon-ok",
				handler:function(){
					var note = $("#pdailog #tnote").val();
					var desc = $("#pdailog #tdesc").val();
					$.ajax({
						type:"post",
						url:"updateTable.action",
						dataType:"json",
						data:{tableId: row.tableId, tableNote:note, tableDesc:desc},
						success:function(){
							$('#pdailog').dialog('close');
							$("#tablegrid").bootstrapTable("refresh", {query:{t:Math.random()}});
						}
					});
				}
			},{
				text:'取消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
	$("#datatab").tabs({
		
	});
	var toolbar = [{
		text:'添加字段',
		iconCls:'icon-add',
		handler:function(){
			addTableCol('update');
		}
	},{
		text:'编辑字段',
		iconCls:'icon-edit',
		handler:function(){
			var row = $("#tablecol").datagrid("getSelected");
			if(row == null){
				 $.messager.alert("出错了","请勾选数据。","error");
				return;
			}
			addTableCol('update',true, row.tmpid);
		}
	},{
		text:'删除字段',
		iconCls:'icon-remove',
		handler:function(){
			var r = $("#tablecol").datagrid("getSelected");
			if(r == null){
				 $.messager.alert("出错了","请勾选数据。","error");
				return;
			}
			if(confirm("是否确认？")){
				 $.ajax({
					type:"post",
					url:"../etl/delTableColumn.action",
					dataType:"HTML",
					data:{tableId: row.tableId,colId:r.colId},
					success:function(dt){
						$("#tablecol").datagrid("load", {t:Math.random()});
					}
				});
			}
		}
	}];
	$("#tablecol").datagrid({
		toolbar:toolbar,
		url:"listTableCols.action?tableId=" + row.tableId,
		singleSelect:true,
		onDblClickRow:function(){
			var row = $("#tablecol").datagrid("getSelected");
			addTableCol('update',true, row.tmpid);
		}
	});
}
function savedata(is3g, cols,  tname, tid, isupdate, dataId){
	var l = cols.length;
	var p = [];
	for(i=0; i<l; i++){
		var c = cols[i];
		var val = "";
		if(c.colType == "Date"){
			val = $('#'+(isupdate?'moddatactx':'writectx')+' #'+c.colName).datebox('getValue');
		}else if(c.colType == "Datetime"){
			val = $('#'+(isupdate?'moddatactx':'writectx')+' #'+c.colName).datetimebox('getValue');	
		}else if(c.colType == "Double" || c.colType == "Int"){
			val = $('#'+(isupdate?'moddatactx':'writectx') + ' #'+c.colName).numberbox('getValue');
		}else if(c.colType == "String" && c.inputType == "input"){
			val = $("#"+ (isupdate?'moddatactx':'writectx') + ' #' + c.colName).val();
		}else if(c.colType == "String" && c.inputType == "select"){
			val = $('#' +(isupdate?'moddatactx':'writectx') + " #"+c.colName).combobox('getValue');
		}
		//不判断必填
		/**
		if(val == ""){
			$.messager.alert("出错了", "["+c.dispName + "] 是必填项。","error");
			return;
		}
		**/
		//val 都以字符串处理
		var o = {colName:c.colName, colType:c.colType,value:val+""};
		p.push(o);
	}
	$("#datasavebtn").menubutton("disable");
	$.ajax({
		type:"post",
		url:isupdate?(is3g?"../imp/updateSubmit.action":"updateSubmit.action"):(is3g?"../imp/saveSubmit.action":"saveSubmit.action"),
		dataType:"json",
		contentType : "application/json",
		data:JSON.stringify({tableName:tname, cols:p, dataId:dataId}),
		error:function(err){
			$("#datasavebtn").menubutton("enable");
			$.messager.alert("提示信息","填报信息出错。", "error");
		},
		success:function(dt){
			$("#datasavebtn").menubutton("enable");
			if(dt.result == 0){
				$.messager.alert("出错了","填报信息:"+dt.msg, "error");
				return;
			}
			if(isupdate){
				//$.messager.alert("提示信息","数据更新成功。","info", function(){
					$("#tablegrid").bootstrapTable("refresh", {query:{t:Math.random()}});
				//});	
			}else{
				$('#pdailog').dialog({
					title: null,
					width: 300,
					height: 130,
					closed: false,
					cache: false,
					modal: true,
					content:"<div style=\"margin:16px; font-size:16px;\"><div class=\"messager-icon messager-info\"></div><div>数据保存成功！</div><div style=\"clear:both;\"></div></div>",
					buttons:[{
						text:'继续填报',
						handler:function(){
							if(is3g){
								go_write(tid);
								$('#pdailog').dialog('close');
							}else{
								location.href = "write.action?tableId=" + tid;
							}
						}
					},{
						text:'查看数据',
						handler:function(){
							if(is3g){
								go_viewdata(tid);
								$('#pdailog').dialog('close');
							}else{
								location.href = "listSubmit.action?tableId=" + tid;
							}
						}
					}]
				});
			}
		}
	});
	
}
function gotowrite(){
	var tableId = $("#tableId").val();
	if(tableId == ""){
		 $.messager.alert("出错了","请选择填报表。","error", function(){
			 $("#tableId").focus();
		 });
		return;
	}
	location.href = 'write.action?tableId='+tableId;
}
function fmtinput(v){
	if(v == "input"){
		return "录入";
	}else{
		return "选择";
	}
}
function go_write(tableId){
	$.mobile.go('#write');
	$("#writectx").load("write.action", {tableId:tableId});
}
function go_viewdata(tableId){
	$.mobile.go("#viewdata");
	$("#datactx").load("list.action", {tableId:tableId});
}
function go_home(){
	$.mobile.go("#home",'slide','right');
}
function impData(tableId, tableName){
	var func = function(cols){
		var tbs = "";
		for(j=0; j<cols.length; j++){
			tbs = tbs + "<option value='"+cols[j].tableId+","+cols[j].tableName+"'>"+cols[j].tableName+"-"+cols[j].tableNote+"</option>";
		}
		var ctx = "<div class=\"textpanel\"><span class=\"inputtext\">选择目标表：</span><select id=\"tabId\" name=\"tabId\" class=\"inputform\" >"+tbs+"</select><p class=\"text-warning\">请注意：需要目标表和填报表字段完全一样，才能把数据导入填报表中。</p></div>";
		$('#pdailog').dialog({
			title: '批量导入数据',
			width: 430,
			height: 220,
			closed: false,
			cache: false,
			modal: true,
			content:ctx,
			buttons:[{
					text:'确定',
					iconCls:"icon-ok",
					handler:function(){
						var tars = $("#pdailog #tabId").val().split(",");
						__showLoading();
						$.ajax({
							url:"impDatas.action",
							type:"post",
							dataType:"json",
							data:{tarTid:tars[0], tarTname:tars[1], tableId:tableId, tname:tableName},
							success:function(resp){
								__hideLoading();
								if(resp.result == 0){
									$.messager.alert("出错了",resp.msg,"error");
								}else{
									$('#pdailog').dialog('close');
									$.messager.alert("成功了","数据导入成功。","info");
								}
								
							},
							error:function(){
								__hideLoading();
							}
						});
					}
				},{
					text:'取消',
					iconCls:"icon-cancel",
					handler:function(){
						$('#pdailog').dialog('close');
					}
				}]
		});
	};
	$.ajax({
		type:"post",
		url:"../etl/loadByIncomes.action",
		dataType:"json",
		data:{income:"etl,tf,r2c,sql,custom"},
		success:function(resp){
			func(resp);
		},
	});
}
function editrecord(tableId, tableName, cols){
	var row = $("#tablegrid").datagrid("getSelected");
	if(row == null){
		 $.messager.alert("出错了","请勾选数据。","error");
		return;
	}
	$('#pdailog').dialog({
		title: '修改数据',
		fit:true,
		closed: false,
		cache: false,
		modal: true,
		content:"cc",
		onLoad:function(){},
		buttons:[{
				text:'确 定',
				iconCls:"icon-ok",
				handler:function(){
					 savedata(true, cols,　tableName, tableId, true, row.tmp_data_id); 
					$('#pdailog').dialog('close');
				}
			},{
				text:'取 消',
				iconCls:"icon-cancel",
				handler:function(){
					$('#pdailog').dialog('close');
				}
			}]
	});
	var url = 'premod.action?dataId=' + row.tmp_data_id+"&tableId="+tableId+"&tableName=" +tableName;
	$("#pdailog").dialog("refresh", url);
}
function delrecord(tableId, tableName){
	var row = $("#tablegrid").datagrid("getSelected");
	if(row == null){
		 $.messager.alert("出错了","请勾选数据。","error");
		return;
	}
	if(confirm("是否确认？删除后不能恢复。")){
		$.ajax({
			type:"post",
			url:"../imp/delData.action",
			dataType:"json",
			data:{dataId: row.tmp_data_id, tableName:tableName},
			success:function(dt){
				$("#tablegrid").datagrid("reload", {tableId:tableId,tableName:tableName,t:Math.random()});
			}
		});
	}
}

function ischinese(a){
	if (/[\u4E00-\u9FA5]/i.test(a)) {
		return true;  
	}else{    
		return false 
	}
}
//生成唯一标识
function newGuid()
{
    var guid = "";
    for (var i = 1; i <= 32; i++){
      var n = Math.floor(Math.random()*16.0).toString(16);
      guid +=   n;
      //if((i==8)||(i==12)||(i==16)||(i==20))
      //  guid += "-";
    }
    return guid;    
}