/*
 * Copyright 2018 本系统版权归成都睿思商智科技有限公司所有 
 * 用户不能删除系统源码上的版权信息, 使用许可证地址:
 * https://www.ruisitech.com/licenses/index.html
 */
if($ == undefined){
	$ = jQuery;
}
function useropt(v){
	return "<a class=\"btn btn-primary btn-xs\" href='javascript:userRole("+v+");'> 授权角色 </a> <a class=\"btn btn-primary btn-xs\" href='javascript:userMenu("+v+");'> 授权菜单 </a>";
}
function roleopt(v){
	return "<a class=\"btn btn-primary btn-xs\" href='roleMenu.action?roleId="+v+"'>授权菜单</a> <a class=\"btn btn-primary btn-xs\" href='roleData.action?roleId="+v+"'>数据权限</a>";
}
function userstate(v){
	if(v == 1){
		return "启用";
	}else{
		return "停用";
	}
}
function delUser(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	if(row == null || row.length == 0){
		msginfo("请勾选数据。");
		return;
	}
	if(confirm("是否确认删除？")){
		$.ajax({
			type:"POST",
			url:"userList/delete.action",
			dataType:"JSON",
			data:{userId:row[0].userId},
			success:function(){
				$("#tablegrid").bootstrapTable("refresh");
			}
		});
	}
}
function addUser(isupdate){
	var execf = function(u){
		var deps = "<option value=''></option>";
		$.ajax({
			type:"GET",
			url:"../frame/loadAllDepartment.action",
			dataType:"JSON",
			data:{},
			async: false,
			success:function(resp){
				for(j=0; resp && j<resp.length; j++){
					deps = deps + "<option value='"+resp[j].id+"' "+(u&&u.deptId==resp[j].id?"selected":"")+">"+resp[j].deptName+"</option>";
				}
			}
		});
		var ctx = "<div class=\"form-group\"><label class=\"col-sm-3 control-label\" >用户工号：</label><div class=\"col-sm-8\"><input placeholder=\"登录系统使用\" name=\"staffId\" class=\"form-control\" type=\"text\" required=\"true\" value=\""+(u?u.staffId:"")+"\" "+(isupdate?"readOnly=\"true\"":"")+"></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >登录密码：</label><div class=\"col-sm-8\"><input id=\"password\" name=\"password\" class=\"form-control\" type=\"password\" "+(isupdate?"":"required=\"true\"")+" minlength=\"6\"></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >重复密码：</label><div class=\"col-sm-8\"><input id=\"password2\" name=\"password2\" class=\"form-control\" type=\"password\" "+(isupdate?"":"required=\"true\"")+"  minlength=\"6\"  equalTo=\"#password\"></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >用户名称：</label><div class=\"col-sm-8\"><input name=\"loginName\" class=\"form-control\" type=\"text\" required=\"true\" value=\""+(u?u.loginName:"")+"\"></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >用户性别：</label><div class=\"col-sm-8\"><select name=\"gender\" class=\"form-control\"><option value=''></option><option value='男' "+(u&&u.gender=='男'?"selected":"")+">男</option><option value='女' "+(u&&u.gender=='女'?"selected":"")+">女</option></select></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >账号状态：</label><div class=\"col-sm-8\"><select name=\"state\" class=\"form-control\" required=\"true\"><option value=\"1\" "+(u&&u.state==1?"selected":"")+">启用</option><option value=\"0\" "+(u&&u.state==0?"selected":"")+">停用</option></select></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >组织机构：</label><div class=\"col-sm-8\"><select name=\"deptId\" class=\"form-control\">"+deps+"</select></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >手机号码：</label><div class=\"col-sm-8\"><input name=\"mobilePhone\" class=\"form-control\" type=\"text\" value=\""+(u?u.mobilePhone:"")+"\" ></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >办公电话：</label><div class=\"col-sm-8\"><input name=\"officeTel\" class=\"form-control\" type=\"text\" value=\""+(u?u.officeTel:"")+"\" ></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >电子邮件：</label><div class=\"col-sm-8\"><input name=\"email\" class=\"form-control\" type=\"text\" value=\""+(u?u.email:"")+"\"></div></div>";
		openModelDailog(isupdate?"修改用户":"新增用户", ctx,  true);
		$("#dailogform").validate({
			submitHandler:function(form){
				var dt = $(form).serialize();
				if(isupdate){
					dt = dt + "&userId="+u.userId;
				}
				$.ajax({
					type:"POST",
					url:isupdate?"userList/update.action":"userList/save.action",
					dataType:"JSON",
					data:dt,
					success:function(resp){
						if(resp.result == 1){
							msginfo("操作成功！", "success");
							$("#modeldailog_div").modal("hide");
							$("#tablegrid").bootstrapTable("refresh");
						}else{
							msginfo(resp.msg);
						}
					}
				});
			}
		});
	}
	if(isupdate){
		var row = $("#tablegrid").bootstrapTable("getSelections");
		if(row == null || row.length == 0){
			msginfo("请勾选数据。");
			return;
		}
		$.getJSON("userList/get.action", {userId:row[0].userId}, function(r){
			execf(r.rows);
		});
	}else{
		execf();
	}
}
function addRole(isupdate){
	var execf = function(u){
		var ctx = "<div class=\"form-group\"><label class=\"col-sm-3 control-label\" >角色名称：</label><div class=\"col-sm-8\"><input name=\"roleName\" class=\"form-control\" type=\"text\" required=\"true\" value=\""+(u?u.roleName:"")+"\"></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >排序：</label><div class=\"col-sm-8\"><input name=\"ord\" class=\"form-control\" type=\"text\" required=\"true\" digits=\"true\" value=\""+(u?u.ord:"")+"\"></div></div><div class=\"form-group\"><label class=\"col-sm-3 control-label\" >备注信息：</label><div class=\"col-sm-8\"><input name=\"roleDesc\" class=\"form-control\" type=\"text\" value=\""+(u?u.roleDesc:"")+"\"></div></div>";
		openModelDailog(isupdate?"修改角色":"新增角色", ctx,  true);
		$("#dailogform").validate({
			submitHandler:function(form){
				var dt = $(form).serialize();
				if(isupdate){
					dt = dt + "&roleId="+u.roleId;
				}
				$.ajax({
					type:"POST",
					url:isupdate?"role/update.action":"role/save.action",
					dataType:"JSON",
					data:dt,
					success:function(resp){
						if(resp.result == 1){
							msginfo("操作成功！", "success");
							$("#modeldailog_div").modal("hide");
							$("#tablegrid").bootstrapTable("refresh");
						}else{
							msginfo(resp.msg);
						}
					}
				});
			}
		});
	}
	if(isupdate){
		var row = $("#tablegrid").bootstrapTable("getSelections");
		if(row == null || row.length == 0){
			msginfo("请勾选数据。");
			return;
		}
		$.getJSON("role/get.action", {roleId:row[0].roleId}, function(r){
			execf(r);
		});
	}else{
		execf();
	}
}
function delRole(){
	var row = $("#tablegrid").bootstrapTable("getSelections");
	if(row == null || row.length == 0){
		msginfo("请勾选数据。");
		return;
	}
	if(confirm("是否确认删除？")){
		$.ajax({
			type:"POST",
			url:"role/delete.action",
			dataType:"JSON",
			data:{roleId:row[0].roleId},
			success:function(){
				$("#tablegrid").bootstrapTable("refresh");
			}
		});
	}
}
function userRole(userId){
	var func = function(roles){
		var ctx = "";
		for(j=0; j<roles.length; j++){
			var r = roles[j];
			ctx =  ctx + "<label class=\"\"><div class=\"icheckbox_square-green\"><input name=\"roleId\" type=\"checkbox\" class=\"i-checks\" value=\""+r.roleId+"\" "+(r.userId?"checked":"")+"></div> "+r.roleName+"</label><br/>";
		}
		openModelDailog("授权用户角色", ctx,  true);
		$('#modeldailog_div .i-checks').iCheck({
			checkboxClass: 'icheckbox_square-green',
			radioClass: 'iradio_square-green',
		});
		$("#dailogform").validate({
			submitHandler:function(form){
				var dt = $(form).serialize();
				if(dt != ""){
					dt = dt + "&userId="+userId;
				}else{
					dt = dt+"userId="+userId;
				}
				$.ajax({
					type:"POST",
					url:"role/userRoleSave.action",
					dataType:"JSON",
					data:dt,
					success:function(resp){
						msginfo("操作成功！", "success");
						$("#modeldailog_div").modal("hide");
					}
				});
			}
		});
	}
	$.getJSON("role/userRolelist.action", {userId:userId}, function(dt){
		func(dt);
	});
}
function userMenu(userId){
	var u = "userMenu.action?userId="+userId;
	location.href = u;
}

function addMenu(update){
		var node = $("#ggcatatree").tree("getSelected");
		var obj;
		if(update){
			$.ajax({
				   type: "GET",
				   async: false,
				   url: "menu/get.action",
				   dataType:"JSON",
				   data: {"menuId":node.id},
				   success: function(resp){
					  obj = resp;
				   }
			});
		}
		if(update == false){
			//新增只能配置3级菜单
			var p1 = $("#ggcatatree").tree("getParent", node.target);
			if(p1 != null){
				var p2 = $("#ggcatatree").tree("getParent", p1.target);
				if(p2 != null){
					var p3 = $("#ggcatatree").tree("getParent", p2.target);
					if(p3 != null && p3.id == "0"){
						$.messager.alert("出错了。","菜单只能建3级", "error");
						return;
					}
				}
			}
			
		}
		var ctx = "<div style=\"padding:10px;\"><div class=\"lone\"><span class=\"inputtext\">名称：</span><input type=\"text\" id=\"name\" class=\"inputform\" value=\""+(obj?obj.menuName:"")+"\"></div><div class=\"lone\"><span class=\"inputtext\">URL：</span><input type=\"text\" id=\"url\" class=\"inputform\" value=\""+((obj?obj.menuUrl:""))+"\" placeholder=\"如果创建目录则不用填写URL\"></div><div class=\"lone\"><span class=\"inputtext\">排序：</span><input type=\"text\" id=\"order\" class=\"inputform\" value=\""+(obj?obj.menuOrder:"1")+"\"></div><div class=\"lone\"><span class=\"inputtext\">图标：</span><input type=\"text\" id=\"avatar\" class=\"inputform\" value=\""+(obj&&obj.avatar!=null?obj.avatar:"")+"\"></div><div class=\"lone\"><span class=\"inputtext\">备注：</span><input type=\"text\" id=\"desc\" class=\"inputform\" value=\""+(obj&&obj.menuDesc!=null?obj.menuDesc:"")+"\"></div></div>";
		$('#pdailog').dialog({
			title: update?'修改菜单':'新建菜单',
			width: 420,
			height: 260,
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
						var url = $("#pdailog #url").val();
						var note = $("#pdailog #desc").val();
						var order = $("#pdailog #order").val();
						if(name == ''){
							alert("名称必须填写。");
							return;
						}
						if(isNaN(order)){
							alert("排序字段必须是数字类型。");
							return;
						}
						var avatar = $("#pdailog #avatar").combobox("getValue");
						if(update==false){
							$.ajax({
							   type: "POST",
							   url: "menu/save.action",
							   dataType:"json",
							   data: {"menuName":name,"menuDesc":note,"menuOrder":order, "menuUrl":url, "menuPid":node.id, "avatar":avatar},
							   success: function(resp){
								   $("#ggcatatree").tree("append", {parent:node.target, data:[{id:resp.rows,text:name}]});
							   }
							});
						}else{
							$.ajax({
							   type: "POST",
							   url: "menu/update.action",
							   dataType:"json",
							   data: {"menuName":name,"menuDesc":note,"menuOrder":order, "menuUrl":url, "menuId":node.id,"avatar":avatar},
							   success: function(){
								   $("#ggcatatree").tree("update", {target:node.target, text:name});
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
		$("#pdailog #avatar").combobox({
			url:'../resource/fonts/menu-icons.json',
			valueField:'cls',
			textField:'text',
			height:25,
			formatter:function(row){
				return "<i class=\""+row.cls+"\"></i> "+row.text;
			}
		});
	}