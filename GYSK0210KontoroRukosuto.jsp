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
    <title>Master管理システム main</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-easyui-1.4.3/themes/bootstrap/easyui.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-easyui-1.4.3/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body style="margin-left: 10px; margin-top: 0">
    <form name="masterForm" id="aspnetForm" enctype="multipart/form-data">
            <div class="easyui-panel" title="社員情報" style="height:175px;width:400px;" id="ctl00_cphMain_edittable">
                <div style="padding:10px 30px 10px 30px">
                    <table border="0" cellspacing="1" cellpadding="4">
                        <tr style="width: 280px;">
                            <td>社員番号</td>
                            <td>
                                <input name="userid" type="text" id="02_cphMaster_Hizuketxt" class="easyui-textbox" 
                                    value="<c:out value="${entity.SHAINCD}" escapeXml="true"/>" style="text-align: left; width: 200px;"/>
                            </td>
                        </tr>
                        <tr style="width: 50px;">
                            <td>社員名</td>
                            <td>
                                <input name="username" type="text" id="00_cphMaster_Hizuketxt" class="easyui-textbox" 
                                    value="<c:out value="${entity.SHAINNM}" escapeXml="true"/>" style="text-align: left; width: 200px;"/>
                            </td>
                        </tr>                    
                        <tr style="width: 50px;">
                            <td>コントロールコスト</td>
                            <td>
                                <input name="kzcb" type="text" id="01_cphMaster_Hizuketxt"  class="easyui-textbox" 
                                    value="<c:out value="${entity.CONST}" escapeXml="true"/>" style="text-align: left; width: 200px;"/>
                            </td>
                        </tr>
                    </table>
                    <table>
                        <tr class="B_FIRSTLINE" align="center" >
                            <td width="656px" align="left">
                            <input class="easyui-linkbutton" type="button"
                                    name="ctl00$cphMain$btnOK" value="確定"
                                    onclick="doSave();" id="ctl00_cphMain_btnOK"
                                    style="text-align:center;padding:4px;width:60px"
                                    />
                            <input class="easyui-linkbutton" type="button"
                                    name="ctl00$cphMain$btnClear" value="クリア"
                                    onclick="doClear();"id="ctl00_cphMain_btnClear"
                                    style="text-align:center;padding:4px;width:60px" /> 
                            <!-- 更新区分 1 新规 ，2 修改 -->
                            <input name="suoriKuben" type="hidden" value="1" id="suoriKuben"/>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                </div>
            </div>
            <hr align="left" width="982">
            <table style="width:972px">
                <tr class="B_FIRSTLINE">
                    <td width="800px">
                        <input type="button"
                            name="ctl00$cphMain$btnAdd" value="新規"
                            onclick="add();" id="ctl00_cphMain_btnAdd"
                            class="easyui-linkbutton" style="text-align:center;padding:4px;width:60px"/>
                        
                        <input type="button"
                            name="ctl00$cphMain$btnDel" value="削除"
                            onclick="del();" id="ctl00_cphMain_btnDel"
                            class="easyui-linkbutton" style="text-align:center;padding:4px;width:60px" />
                            
                        <input class="easyui-filebox" id="fileImport" name="file" data-options="prompt:'选择文件...'">
                        <input type="button" onclick="upload();" class="easyui-linkbutton" value="導入" style="text-align:center;padding:4px;width:60px"/>
                    </td>
                        <td width="70px" align="right">全&nbsp;<span id="ctl00_cphMain_lblCount">6</span>&nbsp;位
                    </td>
                </tr>
            </table>
            <div id="divView" style="min-height: 385px;" class="DIV">
                <table id ="dg" width="982px" style="margin-left: -3px;">
                    <tr>
                        <td>
                            <table id ="ryxx" class="easyui-datagrid" style="width:980px;height:600px">
                                <thead>
                                    <tr>
                                        <th data-options="field:'ck'" checkbox="true"></th>
                                        <th data-options="field:'shayinCd',width:241,align:'center'">社員番号</th>
                                        <th data-options="field:'shayinNm',width:245,align:'center'">社員名</th>
                                        <th data-options="field:'price',width:245,align:'center'">コントロールコスト</th>
                                        <th data-options="field:'action',width:218,align:'center'">操作</th>
                                    </tr>
                                </thead>
                                <!--  以下是list内容 -->
                                <tbody>
                                   <c:forEach items="${list}" var="item">  
                                          <tr>
                                               <td class="center"></td>
                                            <td><c:out value="${item.SHAINCD}"  escapeXml="true"/></td>
                                               <td><c:out value="${item.SHAINNM}" escapeXml="true"/></td>
                                             <td><c:out value="${item.CONST}" escapeXml="true"/></td>
                                             <td>
                                                 <input class="easyui-linkbutton" type="button" name="edit4" value="編集" 
                                                     id="${item.SHAINCD},${item.SHAINNM},${item.CONST}"
                                                    onclick="update(id);" style="text-align:center;padding:4px"/>
                                            </td>
                                           </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        
        <script type="text/javascript">
        $(document).ready(function(){
             // 页面信息设定
           if (null != '${msg}' && "" != '${msg}') {
                var msg = eval('${msg}');
                if(msg.length!=0){
                    $.messager.alert({
                        title:'Massage',
                        msg:msg[0].msgContent,
                        style:{
                            top:0,
                            bottom:449
                        }
                    });
                }
            }
        });
        
        $('#02_cphMaster_Hizuketxt').textbox({
            inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
                keyup: function(event){
                if(event.keyCode == 13) {
                    $('#00_cphMaster_Hizuketxt').textbox().next('span').find('input').focus();
                }
                }
            })
        });
        $('#00_cphMaster_Hizuketxt').textbox({
            inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
                keyup: function(event){
                if(event.keyCode == 13) {
                    $('#01_cphMaster_Hizuketxt').textbox().next('span').find('input').focus();
                }
                }
            })
        });
        $('#01_cphMaster_Hizuketxt').textbox({
            inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
                keyup: function(event){
                if(event.keyCode == 13) {
                    doSave();
                }
                }
            })
        });
        
        function upload() {
            var form = document.forms[0];
            form.action ="${pageContext.request.contextPath}/gysk0210master/import";
            form.target="_self";
            form.method = "post";
            form.submit();
        }
                 
        function doClear(){
            $('#aspnetForm').form('load',{
                kzcb: "",
                suoriKuben: "2",
            });
        }
        
        function update(id){
            $('#02_cphMaster_Hizuketxt').combobox('disable');
            $('#00_cphMaster_Hizuketxt').combobox('disable');
            arr=id.split(","); 
            $('#aspnetForm').form('load',{
                userid: arr[0],
                username: arr[1],
                kzcb: arr[2],
                suoriKuben: "2",
            });
        }
        
        function doSave(){
            var $flag=document.getElementById('suoriKuben').value;
            var $shayinCd=document.getElementById('02_cphMaster_Hizuketxt').value;
            var $shayinNm=document.getElementById('00_cphMaster_Hizuketxt').value;
            var $chengben=document.getElementById('01_cphMaster_Hizuketxt').value;
            if($shayinCd==null||$shayinCd==""||$shayinCd=="null"){
                $.messager.alert({
                    title:'Massage',
                    msg:'社員番号は必須入力',
                    style:{
                        top:0,
                        bottom:449
                    }
                });
                return;
            }
            if($shayinCd.toString().length>6){
                $.messager.alert({
                    title:'Massage',
                    msg:'社員番号の長さ最大は6位だ',
                    style:{
                        top:0,
                        bottom:449
                    }
                });
                return;
            }
            if($shayinNm.toString().length>10){
                $.messager.alert({
                    title:'Massage',
                    msg:'社員名 の長さ最大は10位だ',
                    style:{
                        top:0,
                        bottom:449
                    }
                });
                return;
            }
            if(isNaN($chengben)){
                $.messager.alert({
                    title:'Massage',
                    msg:'コントロールコストは半角純数字として、再入力してください',
                    style:{
                        top:0,
                        bottom:449
                    }
                });
                return;
            }
            if($chengben.toString().length>10){
                $.messager.alert({
                    title:'Massage',
                    msg:'コントロールコストの長さ最大は10位だ',
                    style:{
                        top:0,
                        bottom:449
                    }
                });
                return;
            }
            if(2==$flag){
                // ajax更新
                $.ajax({
                    type : "POST",
                    url : "${pageContext.request.contextPath}/gysk0210master/update",
                    data : {
                           SHAINCD : $shayinCd,
                           SHAINNM: $shayinNm,
                           CONST: $chengben
                    },
                    success : function(data) {
                        roload(data);
                    }
                });
            }else{
                // ajax挿入データ 
                $.ajax({
                    type : "POST",
                    url : "${pageContext.request.contextPath}/gysk0210master/insert",
                    data : {
                           SHAINCD : $("#02_cphMaster_Hizuketxt").val(),
                           SHAINNM: $("#00_cphMaster_Hizuketxt").val(),
                           CONST: $("#01_cphMaster_Hizuketxt").val()
                    },
                    error:function(){
                        $.messager.alert({
                            title:'Massage',
                            msg:'該当データが既に存在して ，データを挿入して失敗する ',
                            style:{
                                top:0,
                                bottom:449
                            }
                        });
                      },
                    success : function(data) {
                        roload(data);
                    }
                });
            }
            
        }
        
        // 削除確認
        function del() {
            $.messager.confirm('確認', '削除しますか？', function(r){
                if (r){
                    var rows = $("#ryxx").datagrid("getSelections"); 
                    var arrList = []; 
                    for(var i=0,l=rows.length;i<l;i++){
                        var cd = rows[i].shayinCd;
                        arrList.push(cd); 
                     }
                    var $list=arrList.toString();
                     // 削除
                    $.ajax({
                        type : "POST",
                        url : "${pageContext.request.contextPath}/gysk0210master/delete",
                        data : {
                            shayinCd :$list
                        },
                        error:function(){
                                $.messager.alert({
                                    title:'Massage',
                                    msg:'失敗を削除する ',
                                    style:{
                                        top:0,
                                        bottom:449
                                    }
                                });
                             },
                        success : function(data) {
                            roload(data);
                        }
                    });
                };
            });
        }

        function add(){
            $('#02_cphMaster_Hizuketxt').combobox('enable');
            $('#00_cphMaster_Hizuketxt').combobox('enable');
            $('#aspnetForm').form('load',{
                userid: "",
                username: "",
                kzcb: "",
                suoriKuben: "1",
            });
        }

        function roload(data){
            var form = document.forms[0]; 
            form.submit();
        }
        </script>
    </form>
</body>
</html>
