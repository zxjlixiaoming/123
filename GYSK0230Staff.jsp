<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="page" uri="/WEB-INF/controls/pager.tld"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />         
    <title>FBSE-業績管理システム </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-easyui-1.4.3/themes/bootstrap/easyui.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-easyui-1.4.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
</head>
<body>
<div style="height:80%;width:80%">
    <form name="form1" method="post"  id="form1">
        <script type="text/javascript">
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
            
            $(document).ready(function(){
                if (top.location != self.location) {
                    top.location = self.location;
                };
                // 页面信息设定
                if (null != '${msg}' && "" != '${msg}') {
                    var msg = eval('${msg}');
                    if(msg.length!=0){
                        $.alert(msg);
                    }
                }
                // 保持用戶名称
                $("#txtMemberID").textbox("setValue","${entity.jyugyoin_bangou}");
                $("#txtSurname").textbox("setValue","${entity.seij}");
                $("#txtName").textbox("setValue","${entity.meij}");
                $("#txtDepartmentCD").combobox("setValue","${entity.txtDepartmentCD}");
                
            });
        </script>
        
        <div>
            <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="F1790590" />
        </div>
        <input type="hidden" name="SelectNo" id="SelectNo" value="1" /> 
        <input type="hidden" name="tourokujikan" id="tourokujikan" />
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
                                    <td><input name="txtMemberID" type="text" maxlength="6"
                                        id="txtMemberID" class="easyui-textbox" F_na="従業員番号" F_ne="y"
                                        style="border-width: 1px; border-style: solid; width: 90px;" 
                                        value='<c:out value="${entity.jyugyoin_bangou}" escapeXml="true"/>' /></td>
                                </tr>
                                <tr>
                                    <td width="130px">氏（日本語漢字）：</td>
                                    <td>
                                    <input name="txtSurname" type="text" maxlength="20"
                                        id="txtSurname" class="easyui-textbox" F_na="氏（日本語漢字）" F_cn="y"
                                        F_jp="y"
                                        style="border-width: 1px; border-style: solid; width: 90px;" /></td>
                                    <td width="130px">名（日本語漢字）：</td>
                                    <td>
                                    <input name="txtName" type="text" maxlength="20"
                                        id="txtName" class="easyui-textbox" F_na="名（日本語漢字）" F_cn="y" F_jp="y"
                                        style="border-width: 1px; border-style: solid; width: 117px;" />
                                    </td>
                                </tr>
                                
                                <tr>
                                <td style="width: 90px">部門：</td>
                                    <td width="150px" colspan="3">
                                        <select name="txtDepartmentCD" id="txtDepartmentCD" style=" width: 160px" 
                                                class="easyui-combobox">
                                                <option value="">　　</option>
                                            <c:forEach items="${depList}" var="item"> 
                                                <c:choose>
                                                    <c:when test="${entity.departmentcd == item.value}">
                                                        <option selected value="${item.value}"><c:out value="${item.name}" escapeXml="true"/></option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${item.value}"><c:out value="${item.name}" escapeXml="true"/></option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr height="3px">
                                </tr>
                                <tr>
                                    <td>
                                         <input class="easyui-linkbutton" type="submit"
                                        name="ctl00$cphMain$btnOK" value="検索"
                                        onclick="doSave();" id="ctl00_cphMain_btnOK"
                                        style="text-align:center;padding:4px;width:60px"
                                    />
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
                
            <div id="divView" style="min-height: 385px;width:560px" class="DIV">
                <table id ="dg" width="450px" style="margin-left: -3px;">
                    <tr>
                        <td>
                            <table id="find1" class="easyui-datagrid" style="width:560px;height:375px" title="従業員一覧">
                                <thead>
                                    <tr>
                                        <th field="ck"></th>
                                        <th data-options="field:'shayinCd',width:110,align:'center'">従業員番号</th>
                                        <th data-options="field:'shayinNm',width:160,align:'center'">名前</th>
                                        <th data-options="field:'price',width:130,align:'center'">所属部門</th>
                                        <th data-options="field:'action',width:110,align:'center'">職務</th>
                                    </tr>
                                </thead>
                             <tbody>
                                   <c:forEach items="${list}" var="item">  
                                        <tr>
                                        <c:if test="1=1"> 
                                            <td class="center"><input type="radio" name="checkboxname" id=" <c:out value="${item.num}" escapeXml="true"/>" 
                                            value="<c:out value="${item.kanri_bangou}" escapeXml="true"/>" />
                                            </td>
                                        </c:if>
                                        <%-- <c:else test="1=2">  --%>
                                            <td class="center"><input type="checkbox" name="checkboxname" id=" <c:out value="${item.num}" escapeXml="true"/>" 
                                            value="<c:out value="${item.name}" escapeXml="true"/>" />
                                           
                                            <input type="hidden" name="staffBangou" id="staffBangou" value= "<c:out value="${item.zkey}" escapeXml="true"/>"/> 
                                            <input type="hidden" name="kanriBangoutxt" id="kanriBangoutxt" value= "<c:out value="${item.zkey}" escapeXml="true"/>"/> 
                                            </td>
                                            
                                        <%-- </c:else> --%>
                                            <td><c:out value="${item.jyugyoin_bangou}"  escapeXml="true"/></td>
                                            <td><c:out value="${item.name}"  escapeXml="true"/></td>
                                            <td><c:out value="${item.departmentnm}" escapeXml="true"/></td>
                                            <td><c:out value="${item.genyakusyoku}" escapeXml="true"/></td>
                                            <td><c:out value="${item.txtDepartmentCD}" escapeXml="true"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
                    <table>
                        <tr>
                            <td>
                            <input type="button" name="btnBack" value="戻る"
                                onclick="toBack();" id="btnBack" class="easyui-linkbutton" style="width:60px" />
                            </td>
                            <td>
                              
                            <td>
                                <input type="button" name="btnBack" value="确定"
                                onclick="doClose()" id="btnBack" class="easyui-linkbutton" style="width:60px; margin-left:20px"/>
                            </td>
                    </tr>
                </table>
            </div>
            
      <script type="text/javascript">
        window.onload=window_onload;
        function window_onload(){
            document.all.txtMemberID.focus();
        }
        
        document.onkeydown=onkeydown;
        function onkeydown(){
            var keycode = event.keyCode;
            if(keycode==13)
                document.all.btnSearch.focus();
        }
        
        function toBack(){
            this.close();
        }
        
        function invalidation(){
            var checkList = new Array("txtMemberID",'txtSurname','txtName');
            return checkAlls(checkList);
        }
        
        // 合法性のチェック
        function checkAlls(itemIdAry) {
            $(lblMsg).innerHTML = "";
            for (var i = 0; i < itemIdAry.length; i++) {
                setColor(itemIdAry[i], "#ffffcc");
            }
            for (var i = 0; i < itemIdAry.length; i++) {
                if (typeof($(itemIdAry[i])) == "object") {
                    var itemName = $(itemIdAry[i]).readAttribute("F_na");
                    if (itemName == null) {
                        $(lblMsg).innerHTML = itemName + "を入力してください。";
                        setColor(itemId, "red");
                        return false;
                    }
                    // 半角英数チェックEF_ne
                    if (checkAttribute(itemIdAry[i], "F_ne")) {
                        if(!isNull($F(itemIdAry[i]))){
                            if (getAttValue(itemIdAry[i], "F_ne") == "y" && !isNE($F(itemIdAry[i]))) {
                                    $(lblMsg).innerHTML = itemName + "はすべて半角英数字で入力してください。";
                                    setColor(itemIdAry[i], "red");
                                    return false;
                            }
                        }
                    }
                    // 中国語と日本語チェックF_jp
                    if (checkAttribute(itemIdAry[i], "F_jp")) {
                        if(!isNull($F(itemIdAry[i]))){
                            if (getAttValue(itemIdAry[i], "F_jp") == "y" && !isChineseAndJapanese($F(itemIdAry[i]))) {
                                    $(lblMsg).innerHTML = itemName + "は日本語で入力してください。";
                                    setColor(itemIdAry[i], "red");
                                    return false;
                            }
                        }
                    }
                    // 中国語F_cn
                    if (checkAttribute(itemIdAry[i], "F_cn")) {
                        if(!isNull($F(itemIdAry[i]))){
                            if (getAttValue(itemIdAry[i], "F_cn") == "y" && !CheckString($F(itemIdAry[i]))) {
                                    $(lblMsg).innerHTML = itemName + "は中国語で入力してください。";
                                    setColor(itemIdAry[i], "red");
                                    return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        
        function select(data1,data2){
            var res = new Array();
            res.push(data1);
            res.push(data2);
            returnValue = res;
            window.close();
        }
        window.onload = getSelectNo;
        function getSelectNo () {
            document.getElementById("SelectNo").value =  window.opener.aspnetForm.ctl00_cphMain_booSelectNo.value;
        }
        
        function doSave(){
          
            var $shayinCd=document.getElementById('02_cphMaster_Hizuketxt').value;
            var $shayinNm=document.getElementById('00_cphMaster_Hizuketxt').value;
            var $chengben=document.getElementById('01_cphMaster_Hizuketxt').value;
            if($shayinCd==null||$shayinCd==""||$shayinCd=="null"){
                alert("社員番号必须入力");
                return;
            }
            if($shayinNm==null||$shayinNm==""||$shayinNm=="null"){
                alert("社員名必须入力");
                return;
            }
            if(isNaN($chengben)){
                alert("控制成本为纯数字，请重新输入");
                return;
            }
            if(2==$flag){
                // ajax更新
                $.ajax({
                    type : "POST",
                    url : "${pageContext.request.contextPath}/gysk0230/search",
                    data : {
                           SHAINCD : $shayinCd,
                           SHAINNM: $shayinNm,
                           CONST: $chengben,
                           UPDATEUSER:'lr'
                    },
                    error:function(){
                            alert("error");
                          },
                    success : function(data) {
                        roload(data);
                        alert("success");
                    }
                });
            }else{
                // ajax插入数据
                $.ajax({
                    type : "POST",
                    url : "${pageContext.request.contextPath}/gysk0230/search",
                    data : {
                           SHAINCD : $("#02_cphMaster_Hizuketxt").val(),
                           SHAINNM: $("#00_cphMaster_Hizuketxt").val(),
                           CONST: $("#01_cphMaster_Hizuketxt").val(),
                           CREATEUSER:'lr',
                           UPDATEUSER:'lr'
                    },
                    error:function(){
                            alert("error");
                          },
                    success : function(data) {
                        alert("success");
                    }
                });
            }
        } 

        // 画面を閉める

                function doClose() {
                    var s = document.getElementsByName("checkboxname");
                    var a = document.getElementsByName("staffBangou");
                    var b = document.getElementsByName("kanriBangoutxt");
                    var s2 = "";
                    var a2 = "";
                    var b2 = "";
                    for (var i = 0; i < s.length; i++) {
                        if (s[i].checked) {
                            s2 += s[i].value + ';';
                            a2 += a[i].value + ';';
                            b2 += b[i].value + ';';
                        }
                    }
                    s2 = s2.substr(0, s2.length - 1);
                    a2 = a2.substr(0, a2.length - 1);
                    b2 = b2.substr(0, b2.length - 1);

                    opener.$('#aspnetForm').form("load", {
                        searchEmployer : s2,
                        staffBangou : a2
                    });
                    
                    opener.$('#mainForm').form("load", {
                        userName : s2,
                        kanriBangoutxt : b2
                    }); 
                    this.close();
                }
        </script>
        </form>
    </div>
</body>
</html>