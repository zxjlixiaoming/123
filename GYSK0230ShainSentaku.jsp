<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="page" uri="/WEB-INF/controls/pager.tld"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
<meta charset="UTF-8">
<title>FBSE-業績管理システム</title>
<meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-easyui-1.4.3/themes/bootstrap/easyui.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-easyui-1.4.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.3/jquery.easyui.min.js">
    </script>
<script type="text/javascript">
$(document).ready(function(){
    var ab=	$('#q7').val();
   // alert(ab);
    $('#department').combobox("setValue",ab);
   // $("#department").removeAttr("readonly");
   // $("#department").combobox({editable:false});
//     $('#department').combobox({  
//         textField:'text'  
//     });  
   
    });
	//检索
	function invalidation() {
            var form = document.forms[0];  
            form.action = "${pageContext.request.contextPath}/GYSK0230ShainSentaku/list"; 
            form.method = "post";
            form.submit();
          
	}
	//增加
	function insertData(){
		var form = document.forms[0];  
        form.action = "${pageContext.request.contextPath}/GYSK0230ShainSentaku/insertData"; 
        form.method = "post";
        form.submit();
	}
	//修改
	function updateData(){
		var form = document.forms[0];  
        form.action = "${pageContext.request.contextPath}/GYSK0230ShainSentaku/updateData"; 
        form.method = "post";
        form.submit();
	}
	//删除
	function deleteData(){
		var form = document.forms[0];  
        form.action = "${pageContext.request.contextPath}/GYSK0230ShainSentaku/deleteData"; 
        form.method = "post";
        form.submit();
	}
	
	
	
	 function delClick() {
         var msg = "削除しますか？";
         var r=confirm(msg);
         if(r==false){
             return;
         }
         var rows = $("#find1").datagrid("getSelections");
        // alert(rows.val);
         var arrList = []; 
         for(var i=0,l=rows.length;i<l;i++) {
             var cd = rows[i].userId;
            alert(cd);
            var a = arrList.push(cd);
            alert(a);
          }
       
         var $list=arrList.join(",");
         //var $list=arrList.toString;
         //var $list = arrList.pop();
         alert($list);
          // 削除
         $.ajax({
             type : "POST",
             url : "${pageContext.request.contextPath}/GYSK0230ShainSentaku/deleteData_1",
             data : {
            	 userId :$list
             },
             
             error : function() {
                     alert("失敗を削除する");
                   },
             success : function(data) {
                 alert("成功を削除する ");
                 roload(data);
             }
         });
     }
	
	
	function toBack() {
		this.close();
	}
	function doYes(){
		 var form = document.forms[0];
		 form.action = 
		 
		 form.method = "post";
         form.submit();
         this.close();
	}
	function doSelect(){
		// 1:拿到select对象： 
		 var  myselect=document.getElementById("department");

	  //2：拿到选中项的索引：
	  	var index=myselect.selectedIndex ;             // selectedIndex代表的是你所选中项的index

	  //3:拿到选中项options的value：  
	  	var a = myselect.options[index].value;
	  	alert(a);
	  //4:拿到选中项options的text： 
	  	var department = myselect.options[index].text;
	  	alert (department);
	}
</script>
</head>
<body>
<div style="height:80%;width:80%">
<div>
<a href="${pageContext.request.contextPath}/GYSK0230ShainSentaku/initSearch">查询</a>
<button onclick="insertData()" class="easyui-linkbutton">增加</button>
<button onclick="updateData()" class="easyui-linkbutton">修改</button>
<button onclick="deleteData()" class="easyui-linkbutton">删除</button>
<button onclick="delClick()" class="easyui-linkbutton">删除--</button>
</div>
	<form name="form1" method="post" action="" id="form1">

		<input type="hidden" name="SelectNo" id="SelectNo" value="1" /> 
		<input
			type="hidden" name="tourokujikan" id="tourokujikan" />
		<div align="left">
		<div align="left" class="easyui-panel" title="検索条件指定" style="height:250px;width:560px;padding:10px 30px 10px 10px" id="ctl00_cphMain_edittable">
			
			<table border="0" width="450px">
				<tr valign="bottom">
					<td align="left" width="125px"><font style="font-size: 14px"><em>業績管理システム</em></font></td>
					<td>--------<font color="#000099" style="font-size: 14px"><strong>従業員取得</strong></font></td>
				</tr>
			</table>
			<hr width="100%" color="#4682B4" style="margin-left:2px">
			</hr>
				<table border="0" width="450px">
					<tr>
                        <td><span id="counts" name="counts"
                            style="font-weight: bolder; font-size: 12px; color: red; font-family: 'ＭＳ Ｐゴシック'">
                            ${counts}
                            </span>
                            <span id="lblMsg"
                            style="font-weight: bolder; font-size: 12px; color: red; font-family: 'ＭＳ Ｐゴシック'">
                                                                件検索されました。
                            </span>
                        </td>
                    </tr>
					<tr>
						<td>
							<table style="font-size: 11px" width="450px">
								<tr>
									<td width="90px">従業員番号：</td>
									<td>
									<input name="userId" type="text" maxlength="6"
										id="userId" class="easyui-textbox" F_na="従業員番号" F_ne="y"
										style="border-width: 1px; border-style: solid; width: 90px;"
									value="${userId}"/>
										</td>
								</tr>
								<tr>
									<td width="130px">氏（日本語漢字）：</td>
									<td>
									<input name="firstUserName" type="text" maxlength="20"
										id="firstUserName" class="easyui-textbox" F_na="氏（日本語漢字）" F_cn="y"
										F_jp="y"
										style="border-width: 1px; border-style: solid; width: 90px;"
									value="${firstUserName}"/></td>
									<td width="130px">名（日本語漢字）：</td>
									<td>
									<input name="lastUserName" type="text" maxlength="20"
										id="lastUserName" class="easyui-textbox" F_na="名（日本語漢字）" F_cn="y" F_jp="y"
										style="border-width: 1px; border-style: solid; width: 117px;" 
										value="${lastUserName}"
									/>
									</td>
								</tr>
								<tr height="3px">
								</tr>
								<tr>
									<td style="width: 90px">部門：</td>
									<td width="150px" colspan="3">
									
									
									
<!-- 									<select name="department" 
										id="department" class="easyui-combobox"
										style="width: 160px" onchange="getDepartment"
									>

											<option value="">全部</option>
											<option value="1000">事業管理部</option>
											<option value="1010">系統開発事業部</option>
											<option value="1020">新人</option>
											<option value="1030">本社社員</option>
											<option value="1040">派遣社員</option>
											<option value="1050">その他</option>
											<option value="1060">新事業推進部</option>
									</select> --> 
									
									
									
									
									
 										<select class="easyui-combobox" name="department" id="department" style="width: 160px">
 										<option value="">全部 </option> 
										<c:forEach items="${departmentList}" var="item">
										<option value="${item.value}"><c:out value="${item.name}" escapeXml="true"></c:out></option>
										</c:forEach>
 										</select> 

									<input type="hidden" value="${department}" id="q7">
								</input>
									</td>
								</tr>
													
								
								<tr>
									<td><input type="button" name="btnSearch" value="検索"
										onclick="invalidation();" id="btnSearch"
										class="easyui-linkbutton" style="width:60px;padding:4px"/></td>
								</tr>
							</table>
							
							
						</td>
					</tr>
					
					<tr>
						<td>
							<hr width="500px" color="#4682B4">
							</hr>
						</td>
					</tr>
				</table>
				
				</div>
				
<div style="min-height: 280px; height: 220px;" class="DIV">
						

	<table id="find1" class="easyui-datagrid" style="width:560px;height:310px" title="従業員一覧">
		<thead >
			<tr >
				<th field="ck" checkbox="true" ></th>
				<th data-options="field:'userId',width:110,align:'center'">従業員番号</th>
				<th data-options="field:'userName',width:160,align:'center'">名前</th>
				<th data-options="field:'department',width:130,align:'center'">所属部門</th>
				<th data-options="field:'position',width:110,align:'center'">職務</th>
			</tr>
		</thead>
						
		<tbody>
               <c:forEach items="${list}" var="item">  
                   <tr style="height: 18px;">                          
						<td field="ck" checkbox="true" id="dgridDaily_ctl04_chkSelect" name="dgridDaily$ctl04$chkSelect"onclick="itemCheck();"></td>
                     	<td align="center" style="border-color: DarkBlue;"><c:out value="${item.userId}" escapeXml="true"/></td>
                     	<td align="left" style="border-color: DarkBlue;"><c:out value="${item.userName}" escapeXml="true"/></td><td align="left" style="border-color: DarkBlue;"><c:out value="${item.department}" escapeXml="true"/></td>
                     	<td align="left" style="border-color: DarkBlue;"><c:out value="${item.position}" escapeXml="true"/></td>                     
                   </tr>
               </c:forEach>
         </tbody>
						
	</table>
					<table>
						<tr>
							<td>
							<input type="button" name="btnBack" value="戻る"
								onclick="toBack();" id="btnBack" class="easyui-linkbutton" style="width:60px" />
							</td>
							<td>
							<input type="button" name="btnBack" value="确定"
								onclick="doYes()" id="btnBack" class="easyui-linkbutton" style="width:60px; margin-left:20px"/>
						</td>
						</tr>
					</table>
					</div>
		</div>
	</form>
	</div>
</body>
</html>