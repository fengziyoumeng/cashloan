<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
	String invitationCode= request.getParameter("invitationCode");
	String channelCode= request.getParameter("channelCode");
	String inviteUserId= request.getParameter("userId");
	String loanSource= request.getParameter("loanSource");
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>轻松借款</title>
    <meta name="keywords" content="贷款,小额借钱,借贷,贷款app,急用钱,短期快速放贷,极速借款借钱,小额贷款">
    <meta name="description" content="专注于为个人提供正规小额贷款、无抵押贷款、个人贷款、闪电借钱等服务">
    <script src="<%=basePath%>static/js/flexable.js"></script>
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="#7CD88E">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <link rel="stylesheet" href="<%=basePath%>static/css/style.css"/>
</head>
<style>
    body{
        /*background: #FFDA1F;*/
        width:100%;
        height: 100%;
        background: url("/static/images/bg.png") no-repeat;
        background-size: 100% 100%;
    }
    html {
        width: 100%;
        height: 100%;
        background: #FFDA1F;
    }
</style>
<body>
    <div class="signup seven">
        <%--<div class="bar">--%>
            <%--<img src="/static/images/invite.png" alt="">--%>
        <%--</div>--%>
        <%--<img class="bar" alt="" src=""/>--%>
        <div class="content">
            <form action="/api/user/wxRegister.htm">
                <input name="phone" type="tel" value="" maxlength="11" placeholder="请输入本人常用手机号" style="margin-top: 0.35rem;"/>
                <input name="password" type="password" value="" placeholder="设置登录密码"/>
                <%if(channelCode != null&&!channelCode.equals("")&&!channelCode.equals("null")){ %>
                <input style="display: none" id='channelCode' name="channelCode" type="hidden" value="<%=channelCode%>"/>
                <%} %>
                <%if(invitationCode != null&&!invitationCode.equals("")&&!invitationCode.equals("null")){ %>
                <input style="display: none" id='invitation' name="invitationCode" type="text" value="<%=invitationCode%>" disabled="true" placeholder="推荐人"/>
                <%} %>
                <p class="picVerify clearfix">
                    <input type="text" id="code" name="code" placeholder="请输入图片验证码" />
                    <img id="imgObj" alt="验证码"  src="/api/h5/imgCode/generate.htm" onclick="changeImg()"/>
                </p>
                <p class="special clearfix">
                    <input name="vcode" type="text" value="" placeholder="请输入验证码" maxlength="4"/>
                    <button id="btn">获取验证码</button>
                </p>
                <a href="javascript:;" id="btn-reg" class="reg-btn"><img style="width:100%;" src="/static/images/button.png" alt=""></a>
                <p class="clearfix other">
                    <input id="checkbox" name="yes" type="checkbox" value=""/>
                    <label for="checkbox" onclick="click_a();">同意<a href="protocol_register.jsp">《使用协议》</a>
                          <i src="<%=basePath%>static/images/yes.png" id="click_a"></i>
                    </label> 
                     <a href="#">APP下载</a>                              
                </p>
            </form>
            <!-- <p>投资有风险 入市需谨慎<br/>杭州民华金融信息服务有限公司</p> -->
        </div>
    </div>

    <div class="popup tips" style="display:none">
        <div class="overlay"></div>
        <div class="dialog">
        <span class="close"></span>
        <h2 id="confirm">...</h2>
        <p>
            <a href="javascript:;" class="yes">确定</a>
        </p>
      </div>
    </div>

    <div class="popup pop" style="display:none">
        <div class="overlay"></div>
        <div class="dialog">
            <span class="close"></span>
            <h2>...</h2>
            <p>
                <a href="<%=basePath%>user/getAppUrl">立即下载APP，一键提现</a>
            </p>
        </div>
    </div>

</body>
</html>
 
<script src="<%=basePath%>static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/placeholders.js" ></script> 
<script type="text/javascript" src="<%=basePath%>static/js/signup.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/jquery.md5.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/config.js" ></script>
<script>
    //头部信息
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "<%=basePath%>static/js/hm.js";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();

    kdlcJsApiShareBack(); 
    function kdlcJsApiShareBack(){
        if (typeof(kdlcJsApi) != 'undefined') {
            kdlcJsApi.pageAddShare('{"isShare":1,"shareBtnTitle":"\u6309\u94ae\u6587\u6848","shareTitle":"\u5206\u4eabtitle","sharePageTitle":"\u5206\u4eab\u6709\u5956\u63cf\u8ff0","shareContent":"\u5206\u4eab\u63cf\u8ff0","shareUrl":"http:\/\/www.yongqianbei.com","shareImg":"http:\/\/res.koudailc.com\/article\/20160506\/3572c6e05464b6.png","sharePlatform":["wx","wechatf","qq","qqzone","sina","sms"],"shareSuccessAlert":"\u5206\u4eab\u6210\u529f\u5f39\u6846\u6587\u6848","shareIsUp":1,"shareUpId":11,"shareUpType":1,"shareUpUrl":"http:\/\/www.yongqianbei.com"}');
        };
        return 'kdlc_share_back';
    }

    //接口定义
    //var codeurl = '<%=basePath%>invite/getRegCode';
    //var signup ='<%=basePath%>invite/getRegister';
    //var reg ='<%=basePath%>user/addRegister';
    //var register ='<%=basePath%>appSystem/getBorrowByApp';

    //新加
    var codeurl = '/api/user/sendSms.htm';//获取验证码
    var signup = '/api/user/validateSmsCode.htm';//判断验证码手否正确
    var checkurl = '/api/user/h5SendSms.htm';
    //app注册接口
    //var reg ='/api/user/register.htm';
    //微信渠道注册接口
    var reg ='/api/user/wxRegister.htm';
    
    var invitationCode='<%=invitationCode%>';
    var inviteUserId='<%=inviteUserId%>';
    var channelCode='<%=channelCode%>';

    //协议选中切换
    var i = 0;
    function click_a(){
        if(i%2==0){
            $('#click_a').css('display', 'none');
            if(typeof bgColor !== 'undefined'){
               $('#click_a').css('background-color',bgColor);
            }
        }else{
            var src = $('#click_a').attr('src');
            $('#click_a').css('display', 'inline');
            $('#click_a').css('background','url('+ src + ') 0 0 no-repeat').css('background-size','0.3733333333rem 0.3733333333rem');
        }
        i++;
    } 
 // 刷新图片  
    function changeImg() {  
        var imgSrc = $("#imgObj");  
        var times = (new Date()).getTime(); 
        imgSrc.attr("src", '/api/h5/imgCode/generate.htm?timestamp='+times);  
    }
    //头部图片
//    $('img').eq(0).attr('src',getInvite_img());
    //app下载地址
    $('.other>a').attr('href',getInvite_a());


    $('#txtTopImg').attr('src',getIndex_img2()); //第一个横幅
    $('.txtBtmImg').attr('src',getIndex_img3()); //第二个横幅
</script>