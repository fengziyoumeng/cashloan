<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Java推送</title>
    <style type="text/css" media="screen">
        *{margin: 0; padding: 0;}
        img{border:0;}
        ol, ul ,li{list-style: none;}

        body{background:url("/static/images/bg1.jpg")}
        .chatBox{width:700px;height:500px;margin:100px auto;}
        h1{margin-left:225px;font-size:30px;color:#22af12;}
        .chatBox .c_info{text-align:left;line-height:40px;margin-bottom:10px;}
        .c_intext{font-size:17px;}
        .inputText{height:30px;border:0;outline:none;text-indent:1em;font-size:14px;
            font-family:"微软雅黑";color:#61B52D;margin-left:10px;}
        .btn{width:80px;text-indent:0;background:#76CA46;color:#fff;margin-left:10px;}
        .chatBox .c_message{width:97%;height:673px;overflow:auto;border:1px solid yellow;font-size:24px;color:#807f86;}
        .chatBox .c_send{margin-top:10px;}
    </style>
</head>
<body>
<div class="chatBox">
    <h1>一起聊天吧</h1>
    <!-- m.51jjhao.com -->
    <div class="c_info">
        &nbsp;<b class="c_intext">服务器地址</b>：<input type="text" id="addr" class="inputText" value="m.51jjhao.com:8884/chat" readonly="true">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <b>用户名</b>：<input type="text" id="userName" class="inputText" value="" placeholder="请输入用户名...">
        <input type="button" id="con" class="inputText btn" value="连接">
    </div>

    <div class="c_message"></div>

    <div class="c_send">
        <input type="text" id="sendText" class="inputText" style="width:536px;">
        <input type="button" id="sendBtn" class="inputText btn" value="发送">
    </div>
</div>

<script src="<%=basePath%>static/js/jquery.min.js"></script>
<script>
    var phoneWidth =  parseInt(window.screen.width);
    var phoneScale = phoneWidth/640;
    var ua = navigator.userAgent;
    if (/Android (\d+\.\d+)/.test(ua)){
        var version = parseFloat(RegExp.$1);
        if(version>2.3){
            document.write('<meta name="viewport" content="width=640, minimum-scale = '+phoneScale+', maximum-scale = '+phoneScale+', target-densitydpi=device-dpi">');
        }else{
            document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
        }
    } else {
        document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
    }

    var connected = false;

    $(function () {
        $(".c_send").hide();

        if(window.WebSocket || window.MozWebSocket){
            printMsg("该浏览器支持WebSocket","OK");
        }else{
            printMsg("该浏览器不支持WebSocket","ERROR");
            $("#con").attr("disabled","true");
        }
    });

    function printMsg(msg,msgType) {
        if(msgType == "OK"){
            msg = " <span style='color:green'> "+ msg + "</span>";
        }
        if(msgType == "ERROR"){
            msg = " <span style='color:red'> "+ msg + "</span>";
        }
        $(".c_message").append(msg+"<br/>");
    };

    $("#con").click(function () {
        if(connected){ //连接
            ws.send("["+ $("#userName").val() +"]离开了聊天室");
            printMsg("["+ $("#userName").val() +"]离开了聊天室");
            connected = false;
            ws.close();
        }else{ //没有连接
            printMsg("正在准备连接服务器，请稍等！");
            var url = "ws://" + $("#addr").val();
            if("WebSocket" in window){
                ws = new WebSocket(url);
            }else if("MozWebSocket" in window){
                ws = new WebSocket(url);
            }

            //连接成功后，设置连接状态
            connected = true;
            $("#con").val("断开");
            ws.onopen = openWs;
            ws.onmessage = msgWs;
            ws.onclose = closeWs;
            ws.onerror = errorWs;
        }
    });

    //打开socket
    function openWs() {
        printMsg("连接已建立....","OK");
        ws.send("["+ $("#userName").val() +"]进入了聊天室");
        $(".c_send").show();
    };

    //关闭socket
    function closeWs(event){
        $(".c_send").hide();
        $("#con").val("连接");
    };

    //服务器连接错误
    function errorWs(event){
        printMsg("与服务器连接发生错误...","ERROR");
    };

    //接受服务端推送来的消息消息
    function msgWs(event){
        printMsg(event.data);
    };

    //点击发送触发事件
    $("#sendBtn").click(function () {
        var text = $("#sendText").val();
        ws.send($("#userName").val()+"说："+text);
        $("#sendText").val(""); //清空发送框
    });
</script>
</body>
</html>