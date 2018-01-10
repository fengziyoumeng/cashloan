<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <title>集借号注册页</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=basePath%>static/css/activity.css">
    <meta name="viewport" content="width=750,minium-scale=1.0,maximun-scale=1.0,user-scalable=no,target-densitydpi=device-dpi">
</head>
<body>
<div class="wrap">
    <div class="top_tip">
        <img src="/static/images/logo.png" alt="">
        <b>新一代来者不拒的借款神器</b>
        <a id="greenBtn"><img class="fr" src="/static/images/download.png" alt=""></a>
    </div>
    <form action="/api/user/wxRegister.htm" method="post" onsubmit="return myCheck()" id="register_form">
        <div>
            <input type="tel" name="loginName" placeholder="请输入手机号码">
        </div>
        <div>
            <input type="password" name="loginPwd" placeholder="6~16位字符，需包含字母和数字">
        </div>
        <input type="hidden" name="channelCode" value="undefined">
        <input type="hidden" name="mobileType" value="MTNL">
        <div>
            <input type="tel" name="vcode" placeholder="请输入短信验证码">
            <button type="button" id="sendCode">获取验证码</button>
        </div>
        <div>
            <button type="submit"></button>
        </div>
    </form>
    <div class="agreement">
        <a href="#">同意（注册协议）</a>
    </div>
    <p>杭州急借号网络科技有限公司</p>
    <p>浙ICP备17040297号-1</p>
</div>
<div class="mask">
    <div class="model">
        <p>验证码校验错误，请重新输入</p>
        <a href="javascript:maskHide()">确定</a>
    </div>
</div>
</body>
</html>
 
<script src="<%=basePath%>static/js/jquery.min.js"></script>
<script src="<%=basePath%>/static/js/jquery.base64.js"></script>
<script src="<%=basePath%>static/js/des.js"></script>
<script src="<%=basePath%>static/js/md5.js"></script>
<script>
    //提示错误遮罩层隐藏
    function maskHide(){
        $('.mask').hide();
    }

//    $('.greenBtn>a').attr('href',"http://localhost:8080/h5/register.jsp");

    $('#register_form').submit(function(){
//        var key = 'pasec23der1e12ecjer3AFRGYD03847@@*^DEJUsdu33'
        var url = "/api/user/wxRegister";
        var phone = $('input[name="loginName]').val();
        var pwd = $('input[name="loginPwd]').val();
        var pwd_md5 = $.md5(pwd);
//        var strencPwd = strEnc(pwd,key);
//        var encodePwd  = $.base64.encode(strencPwd);
        var vcode = $('input[name="vcode]').val();
        $.ajax(
            {
                type: 'POST',
                url: url,
                asyn:false,
                data:{
                    loginName:phone,
                    loginPwd:pwd_md5,
                    vcode:vcode,
                    mobileType:"MTNL"
                },
                success:function(){
                    alert("注册成功");
                    var u = window.navigator.userAgent;
                    var isAndroid = u.indexOf('Android')>-1||u.indexOf('Linux')>-1;
                    if(isAndroid){
                        //window.location.href="";//打开手机app上的协议
                        window.setTimeout(function(){
                            window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.yongqianbei.loan";//下载app的地址
                        },1000)
                    }
                    return false;

//                    var u = window.navigator.userAgent;
//                    var isAndroid = u.indexOf('Android')>-1||u.indexOf('Linux')>-1;
//                    if(isAndroid){
//                        //window.location.href="";//打开手机app上的协议
//                        window.setTimeout(function(){
//                            window.location.href="http://www.baidu.com";//下载app的地址
////                            window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.yongqianbei.loan";//下载app的地址
//                        },2000)
//                    }
                }
            }
        )
    })

    //获取验证码验证
    $('#sendCode').click(function(){
        console.log(11);
        var phone=$('input[name="loginName"]').val();
        if(phone==''||phone==null){
            $('.model').children('p').html('请输入正确的手机号');
            $('.mask').show();
            return;
        }else if(!/^1[345678]\d{9}/.test(phone)){
            $('.model').children('p').html('请输入正确的手机号');
            $('.mask').show();
            return;
        }else{
            var intervalobj;
            var count = 60;
            var curCount;
            function sendMessage(){
                curCount = count;
                $('#sendCode').attr({disabled: 'true'});
                $('#sendCode').html(curCount+"s");
                intervalobj = setInterval(SetRemainTime,1000);
                //后台发送验证码

                //点击获取验证码
                $.ajax({
                    url: '/user/sendSms.htm',
                    data: {
                        phone: phone,
                        type: 'register'
                    },
                    success: function(data) {
                        alert("验证码已发送");
                    },
                    dataType:"text"
                })
            }
            sendMessage();
            function SetRemainTime(){
                if(curCount==0){
                    clearInterval(intervalobj);
                    $('#sendCode').removeAttr('disabled');
                    $('#sendCode').html('重新发送');
                }else{
                    curCount--;
                    $('#sendCode').html(curCount+"s");
                }
            }
        }
    })

    //表单提交验证
    function myCheck(){
        var loginName = $('input[name="loginName"]').val();
//        var pwdvalue = $('input[name="loginPwd"]').val();
//        console.log("pwd"+pwdvalue);
//        var enpwd_md5 = $.md5(pwdvalue);
//        console.log("encpwd"+enpwd_md5);
        $('input[name="loginPwd"]').val(enpwd_md5);
        if($('input[name="loginName"]').val()==''||$('input[name="loginName"]').val()==null){
            $('.model p').html('手机号码不能为空');
            $('.mask').show();
            return false;
        }
        if($('input[name="vcode"]').val()==''||$('input[name="vcode"]').val()==null){
            $('.model p').html('验证码不能为空');
            $('.mask').show();
            return false;
        }
        if($('input[name="loginPwd"]').val()==''||$('input[name="loginPwd"]').val()==null){
            $('.model p').html('密码不能为空');
            $('.mask').show();
            return false;
        }
        if(!(/^1[3|4|5|7|8][0-9]{9}$/.test(loginName))){
            $('.model p').html('手机格式有误');
            $('.mask').show();
            return false;
        }
        if(!(/[0-9a-zA-Z]{6,20}/.test($('input[name="loginPwd"]').val()))){
            $('.model p').html('密码格式不正确');
            $('.mask').show();
            return false;
        }

        return true;
    }
    //点击下载app
     $('#greenBtn').click(function(){
     	var u = window.navigator.userAgent;
     	var isAndroid = u.indexOf('Android')>-1||u.indexOf('Linux')>-1;
     	if(isAndroid){
     		//window.location.href="";//打开手机app上的协议
     		window.setTimeout(function(){
     			window.location.href="http://116.62.174.111:8885/act/downloadApk.htm";//下载app的地址
     		},1000)
     	}
     })
</script>