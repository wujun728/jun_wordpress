<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePathT = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<header>
	<div class="inner">
		<div class="top-logo">
			<a href="index.jsp" title="软件公司" id="web_logo"> <img src="<%=basePathT %>images/1278987436.png" alt="软件公司" title="软件公司" style="margin:30px 0px 0px 0px;">
			</a>
			<ul class="top-nav list-none">
				<li class="t"><a href="#" onclick='SetHome(this,window.location,"非IE浏览器不支持此功能，请手动设置！");' style="cursor:pointer;" title="设为首页">设为首页</a><span>|</span> <a href="#"
					onclick='addFavorite("非IE浏览器不支持此功能，请手动设置！");' style="cursor:pointer;" title="收藏本站">收藏本站</a><span>|</span> <a class="fontswitch" id="StranLink" href="javascript:StranBody()">繁体中文</a></li>
				<li class="b"><span style="color:#ffffff;"><span style="font-size:16px;"><strong>4000 - 12345678</strong></span></span></li>
			</ul>
		</div>
		<nav>
			<ul class="list-none">
				<li data-corner="tl 5px" class="myCorner" id="nav_10001" style="width: 121px; border-top-left-radius: 5px;"><a href="<%=basePathT %>index.jsp" title="首页" class="nav"><span>首页</span></a></li>
				<li class="line"></li>
				
				<li id="nav_2" style="width:121px;"><a href="<%=basePathT %>portal/news/news.jsp" title="新闻资讯" class="hover-none nav"><span>新闻资讯</span></a></li>
				<li class="line"></li>
				<li id="nav_22" style="width:121px;"><a href="<%=basePathT %>portal/product/products.jsp" title="产品与解决方案" class="hover-none nav"><span>产品与解决方案</span></a></li>
				<li class="line"></li>
				<li id="nav_28" style="width:121px;"><a href="<%=basePathT %>portal/service/service.jsp" title="服务和管理" class="hover-none nav"><span>服务和管理</span></a></li>
				<li class="line"></li>
				<li id="nav_34" style="width:120px;"><a href="<%=basePathT %>portal/product/products.jsp" title="员工风采" class="hover-none nav"><span>成功案例</span></a></li>
				<li class="line"></li>
				<li id="nav_34" style="width:120px;"><a href="<%=basePathT %>portal/job/jobs.jsp" title="招贤纳士" class="hover-none nav"><span>招贤纳士</span></a></li>
				<li class="line"></li>
				<li id="nav_34" style="width:120px;"><a href="<%=basePathT %>portal/about/linkus.jsp" title="联系我们" class="hover-none nav"><span>联系我们</span></a></li>
				<li class="line"></li>
				<li id="nav_29" style="width:121px;"><a href="<%=basePathT %>portal/about/about.jsp" title="关于我们" class="hover-none nav"><span>关于我们</span></a></li>
			</ul>
		</nav>
	</div>
</header>
<div class="inner met_flash">
	<link href="lib/css/css.css" rel="stylesheet" type="text/css">
	<script src="lib/jquery.js"></script>
	<div class="flash flash6" style="width:980px; height:297px;">
		<div class="bx-wrapper" style="width:980px; position:relative;">
			<div class="bx-window" style="width:980px; height:297px; position:relative; overflow:hidden;">
				<ul style="height: 999999px; position: relative; top: -594px;" id="slider6" class="list-none">
					<li class="pager" style="list-style: none outside none; height: 297px;"><a href="" target="_blank" title=""> <img src="<%=basePathT %>images/biglogo/1393500857.jpg" alt="" height="297" width="980"></a></li>
					<li class="pager" style="list-style: none outside none; height: 297px;"><a href="" target="_blank" title=""> <img src="<%=basePathT %>images/biglogo/1393499756.jpg" alt="" height="297" width="980"></a></li>
					<li class="pager" style="list-style: none outside none; height: 297px;"><a href="" target="_blank" title=""> <img src="<%=basePathT %>images/biglogo/1393500564.jpg" alt="" height="297" width="980"></a></li>
		</ul>
			</div>
			<div class="bx-pager">
				<a href="" class="pager-link pager-1">1</a><a href="" class="pager-link pager-2 pager-active">2</a><a href="" class="pager-link pager-3">3</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#slider6').bxSlider({
				mode : 'vertical',
				autoHover : true,
				auto : true,
				pager : true,
				pause : 5000,
				controls : false
			});
		});
	</script>
</div>