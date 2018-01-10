<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="#7CD88E" />
  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
  <meta http-equiv="Pragma" content="no-cache" />
  <meta http-equiv="Expires" content="0" />
<title>【还款方式】</title>
<script src="/static/js/mobile.js"></script>
<script
	src="/static/js/zepto.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/static/css/general.css" />
<style type="text/css">
body {
	font-size: 16px;
}

html, body, div, span, h1, h2, h3, p, em, img, dl, dt, dd, ol, ul, li,
	table, tr, th, td, form, input, select {
	margin: 0;
	padding: 0;
}

body {
	min-width: 320px;
	max-width: 480px;
	min-height: 100%;
	margin: 0 auto;
}

.bg {
	display: none;
	/*position: absolute;*/
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background-color: rgba(0, 0, 0, .7);
}

.bg img {
	display: block;
	position: absolute;
	width: 65%;
	top: 5px;
	height: auto;
	float: left;
	left: 25%;
}
</style>
</head>
<body>
	<div class="container">
		<style type="text/css">
.container {
	margin: 16px;
}
</style>
		<h2 style="margin: 10px auto 5px; font-size: 13px; color: rgb(102, 102, 102); white-space: normal; background-color: rgb(255, 255, 255);">主动还款</h2>
		<p class="_color">①如何进行主动还款？</br>进入“还款“页面点击借款列表进入详情页，点击“去还款”按钮，选择任意支付方式即可。</p>

		<h2 style="margin: 10px auto 5px; font-size: 13px; color: rgb(102, 102, 102); white-space: normal; background-color: rgb(255, 255, 255);">到期自动扣款</h2>
		<p class="_color">①平台如何进行自动扣款？</br>若在借款期限内未主动发起还款，则平台会在还款日当天从绑定银行卡中扣除所借款项，扣款成功后会向注册手机号发送短信提醒。</p>
		<p class="_color">②平台自动扣款的时间段有哪些？</br>在还款日当天进行实时扣款。</p>
		<p class="_color">③银行卡账户余额不足会影响自动扣款吗？</br>银行卡账户余额不足，会导致平台扣款失败，可能会造成逾期，请保证在扣款之前银行卡账户资金充足。</p>
		<p class="_color">④邮政储蓄银行卡不支持自动扣款，怎么办？</br>可通过支付宝还款或打款至对公账号两种方式进行还款。支付宝账号：hzmh@yongqianbei.com 对公银行账号为招商银行 571910707410506，还款后请与客服取得联系，将注册用户手机号、转账金额和转入银行卡信息截图提供给客服。客服电话：0571-88003589（工作日9:00-18:00）</p>

		<h2 style="margin: 10px auto 5px; font-size: 13px; color: rgb(102, 102, 102); white-space: normal; background-color: rgb(255, 255, 255);">支付宝转账</h2>
		<p class="_color">①如何进行支付宝转账？</br>进入支付宝首页，点击“转账”，选择“转到支付宝账户”，输入支付宝账号：hzmh@yongqianbei.com ,点击“下一步”，输入转账金额，并将姓名和注册手机号添加到备注中，点击“确认转账”，输入支付宝密码即可完成还款。</p>
		<p class="_color">②如何确认支付宝转账是否成功？</br>还款后请及时在“借款详情”页查看还款状态，当页面默认显示为申请页面时，则表示还款成功。如有疑问请联系客服。客服电话：0571-88003589（工作日9:00-18:00）。</p>
	</div>
	<script src="/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="/static/js/config.js" ></script>
	<script>
		$('.bank').text(getBank());
		$('.airPay').text(getAirpay());
	</script>
</body>
</html>


