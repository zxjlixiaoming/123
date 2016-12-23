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
    window.onload = window_onload;
    // フォーカスはオーダーIDテキストボックスに設定する
    function window_onload() {
        document.all.txtOrderCD.focus();
    }
    
    // 点击查询按钮进行查询
    function doSearch(){ 
            var form = document.forms[0];
            form.action = "${pageContext.request.contextPath}/gysk0220Order/list?mode=0";
            form.target="_self";
            form.method = "post";
            form.submit();
    }
    // 画面を閉める
    function toBack() {
        this.close();
    }
    // 画面を閉める
    function doSave() {
      var  values="";
      $('input[name="chkCheck"]:checked').each(function(x,y){
          if(x==0){
              values+=$(this).attr("id");
          }else{
              values+=";"+$(this).attr("id"); 
          }  
      });
      opener.$('#aspnetForm').form("load", {
          searchOrder: values
     }); 
      window.close(); 
    }
    
 // 取消全选
    function cancelAllCheckBtnClick(indexid) {
       var check = document.getElementsByName("chkCheck");
       var checkFlg = true;
       for (var index = 0; index < check.length; index ++) {
           if (check[index].checked == false) {
               checkFlg = false;
               break;
           }
       }
       if (checkFlg) {
           $("#chkSelectAll").prop("checked", true);
       } else {
           $("#chkSelectAll").prop("checked", false);
       }
    }
 
    //全选
    function allCheckBtnClick() {
        if ($("#chkSelectAll").prop("checked")) {
            $('input[name="chkCheck"]').prop("checked",true);
        }
        else {
           $('input[name="chkCheck"]').prop("checked",false);
        }
    }   
    //<![CDATA[
    var theForm = document.forms['form1'];
    if (!theForm) {
        theForm = document.form1;
    }
    function __doPostBack(eventTarget, eventArgument) {
        if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
            theForm.__EVENTTARGET.value = eventTarget;
            theForm.__EVENTARGUMENT.value = eventArgument;
            theForm.submit();
        }
    }
    //]]>
</script>
</head>
<body>
<div style="height:80%;width:80%">
    <form name="gyousekiOrderForm" method="post"  action="${pageContext.request.contextPath}/gysk0220Order/list" 
          enctype="multipart/form-data" id="form1">
        <div>
            <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="FDBD21B5" />
        </div>
        <input type="hidden" name="divhyouji" id="divhyouji" value="true" />
        <input type="hidden" name="tourokujikan" id="tourokujikan"
            value="2016/11/24 09:33:16" />
        <div align="left" class="easyui-panel" title="検索条件指定" style="height:220px;width:560px;" 
             id="ctl00_cphMain_edittable">
            <div style="padding:10px 30px 10px 10px">
            <table border="0" width="450px">
                <tr valign="bottom">
                    <td align="left" width="125px">
                        <font style="font-size: 14px">
                            <em>業績管理システム</em>
                        </font>
                    </td>
                    <td>--------<font color="#000099" style="font-size: 14px"><strong>オーダー取得</strong></font></td>
                </tr>
            </table>
            <div >
                 <hr width="100%" color="#4682B4" >
            </div>
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
                                    <td width="90px">オーダー番号：</td>
                                    <td><input name="txtOrderCD" type="text" maxlength="8"
                                        id="txtOrderCD" class="easyui-textbox" F_na="オーダー番号" F_ne="y"
                                        style="border-width: 1px; border-style: solid; width: 90px;"
                                        value='<c:out value="${entity.txtOrderCD}" escapeXml="true"/>' /></td>
                                    <td width="90px">オーダー名称：</td>
                                    <td><input name="txtOrderNM" type="text" maxlength="40"
                                        id="txtOrderNM" class="easyui-textbox" F_na="オーダー名称" F_cn="y"
                                        style="border-width: 1px; border-style: solid; width: 117px;"
                                        value='<c:out value="${entity.txtOrderNM}" escapeXml="true"/>' /></td>
                                </tr>
                                <tr height="5px">
                                </tr>
                                <tr>
                                    <td style="width: 90px">部門：</td>
                                    <td width="150px" colspan="3">
                                        <select name="txtDepartmentCD" id="txtDepartmentCD" style=" width: 160px" 
                                                class="easyui-combobox" data-options="editable:false">
                                                <option value="">　　</option>
                                            <c:forEach items="${departmentList}" var="item"> 
                                                <c:choose>
                                                    <c:when test="${entity.txtDepartmentCD == item.value}">
                                                        <option selected value="${item.value}"><c:out 
                                                            value="${item.name}" escapeXml="true"/></option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.value}"><c:out 
                                                            value="${item.name}" escapeXml="true"/></option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left"><input type="button" name="btnSearch"
                                        value="検索"  id="btnSearch" onclick="doSearch();"
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
        </div>
    <div>
    <!-- 一览部 -->
        <table id="t_1" class="easyui-datagrid" style="width:560px;height:340px" title="従業員一覧">
            <thead>
                <tr>
                     <th field="ck" ><input type="checkbox" name="chkSelectAll" id="chkSelectAll" value="" 
                         onclick="allCheckBtnClick();" /></th>
                     <th data-options="field:'userId',width:110,align:'center'">オーダー番号</th>
                     <th data-options="field:'userName',width:160,align:'center'">オーダー名称</th>
                     <th data-options="field:'department',width:130,align:'center'">所属部門</th>
                     <th data-options="field:'manager',width:110,align:'center'">管理者</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${list}" var="item">
                    <tr style="height: 18px;">
                        <td class="center">
                            <input type="checkbox" name="chkCheck" 
                                id="<c:out value="${item.orderCD}" escapeXml="true"/>"
                                 value="<c:out value="${item.orderCD}" escapeXml="true"/>" 
                                 onclick="cancelAllCheckBtnClick();">
                        </td>
                        <td><c:out value="${item.orderCD}" escapeXml="true"/></td>
                        <td><c:out value="${item.orderNM}" escapeXml="true"/></td>
                        <td><c:out value="${item.departmentNM}" escapeXml="true"/></td>
                        <td> <c:out value="${item.name}" escapeXml="true"/></td>
                    </tr>
                </c:forEach>                    
            </tbody>
        </table>
    </div>
    <table>
        <tr>
            <td>
                <input type="button" name="btnBack" value="戻る"
                    onclick="toBack();" id="btnBack" class="easyui-linkbutton" style="width:60px"/>
            </td>
            <td>
                <input type="button" name="btnBack" value="确定"
                    onclick="doSave();" id="btnBack" class="easyui-linkbutton" style="width:60px; margin-left:20px"/>
            </td>
        </tr>
    </table>    
    </form>
</div>
</body>
</html>

