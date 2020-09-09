<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="generator" content="MetInfo 5.2.7">
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/css/metinfo_ui.css" id="metuimodule" data-module="1">
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/css/metinfo.css">
<script src="<%=basePath%>lib/jQuery1.js" type="text/javascript"></script>
<script src="<%=basePath%>lib/metinfo_ui.js" type="text/javascript"></script>
<script src="<%=basePath%>lib/ch.js" type="text/javascript"></script>
<!--[if IE]>
<script src="../public/js/html5.js" type="text/javascript"></script>
<![endif]-->
</head>
<body>
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="sidebar inner">
		<div class="sb_nav">

			<h3 style="border-top-left-radius: 5px; border-top-right-radius: 5px;" class="title myCorner" data-corner="top 5px">关于我们</h3>
			<div class="active" id="sidebar" data-csnow="42" data-class3="0" data-jsok="2">
				<dl class="list-none navnow">
					<dt id="part2_30">
						<a href="<%=basePath%>/metv5/about/about30.html" title="公司简介" class="zm"><span>公司简介</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_31">
						<a href="<%=basePath%>/metv5/about/about31.html" title="发展历程" class="zm"><span>发展历程</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_32">
						<a href="<%=basePath%>/metv5/about/about32.html" title="分支机构" class="zm"><span>分支机构</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_33">
						<a href="<%=basePath%>/metv5/about/about33.html" title="合作伙伴" class="zm"><span>合作伙伴</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt class="on" id="part2_42">
						<a href="<%=basePath%>/metv5/about/about42.html" title="联系我们" class="zm"><span>联系我们</span></a>
					</dt>
				</dl>
				<div class="clear"></div>
			</div>

			
		</div>
		<div class="sb_box">
			<h3 class="title">
				<div class="position">
					当前位置：<a href="<%=basePath%>/metv5/" title="首页">首页</a> &gt; <a href="<%=basePath%>/metv5/about/">关于我们</a> &gt; <a href="<%=basePath%>/metv5/about/about42.html">联系我们</a>
				</div>
				<span>联系我们</span>
			</h3>
			<div class="clear"></div>

			<div class="editor active" id="showtext">
				<div>
					<p>&nbsp;</p>
					<div>
						<span style="font-size:14px;"><strong><img src="<%=basePath%>images/20131228_153538.jpg" style="width: 245px; height: 230px; float: left;padding-left:30px;" alt="图片关键词">某某软件股份公司</strong></span>
					</div>
					<div>地 &nbsp;址：xx市xx区xx路xxxx园xxxx大厦</div>
					<div>邮 &nbsp;编：100190</div>
					<div>电 &nbsp;话：010-000000</div>
					<div>传 &nbsp;真：010-000000</div>
					<div>网 &nbsp;址：www.xxx.com.cn</div>
				</div>
				<div class="clear"></div>
			</div>

		</div>
		<div class="clear"></div>
		<jsp:include page="/footer.jsp"></jsp:include>
	</div>



</body>
</html>