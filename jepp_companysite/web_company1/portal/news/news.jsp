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
<link href="<%=basePath%>/metv5/favicon.ico" rel="shortcut icon">
<link rel="stylesheet" type="text/css" href="<%=basePath%>lib/css/metinfo_ui.css" id="metuimodule" data-module="2">
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

			<h3 style="border-top-left-radius: 5px; border-top-right-radius: 5px;" class="title myCorner" data-corner="top 5px">新闻资讯</h3>
			<div class="active" id="sidebar" data-csnow="9" data-class3="0" data-jsok="2">
				<dl class="list-none navnow">
					<dt class="on" id="part2_9">
						<a href="<%=basePath%>/metv5/lib/news_9_1.html" title="行业咨询" class="zm"><span>行业咨询</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_10">
						<a href="<%=basePath%>/metv5/lib/news_10_1.html" title="公司动态" class="zm"><span>公司动态</span></a>
					</dt>
				</dl>
				<div class="clear"></div>
			</div>

		
		</div>
		<div class="sb_box">
			<h3 class="title">
				<div class="position">
					当前位置：<a href="<%=basePath%>/metv5/" title="首页">首页</a> &gt; <a href="<%=basePath%>/metv5/lib/">新闻资讯</a> &gt; <a href="<%=basePath%>/metv5/lib/news_9_1.html">行业咨询</a>
				</div>
				<span>行业咨询</span>
			</h3>
			<div class="clear"></div>

			<div class="active" id="newslist">
				<ul class="list-none metlist">
					<li class="list top"><span>[2014-03-20]</span><a href="<%=basePath%>/metv5/lib/news20.html" title="某某物联网食品安全追溯新模式" target="_self">某某物联网食品安全追溯新模式</a>
					<p>近年来国内农产品安全（瘦肉精、健美猪、食物中毒、疯猪病、口蹄疫、禽流感等畜禽疾病以及严重农产品残药..</p></li>
					<li class="list "><span>[2014-03-20]</span><a href="<%=basePath%>/metv5/lib/news19.html" title="某某软件中标太原煤气化集团生产调度信息化工程" target="_self">某某软件中标太原煤气化集团生产调度信息化工程</a>
					<p>某某煤炭气化（集团）有限责任公司是国内首家煤炭综合利用大型联合企业,现已形成集煤炭开采、洗选、炼焦..</p></li>
					<li class="list "><span>[2014-03-20]</span><a href="<%=basePath%>/metv5/lib/news18.html" title="某某软件成功签约株洲市区域卫生信息系统建设项目" target="_self">某某软件成功签约株洲市区域卫生信息系统建设项目</a>
					<p>近日某某软件成功签约株洲市区域卫生信息系统建设项目。</p></li>
					<li class="list "><span>[2014-03-20]</span><a href="<%=basePath%>/metv5/lib/news17.html" title="山东某某集团全面预算管理系统顺利上线运行" target="_self">山东某某集团全面预算管理系统顺利上线运行</a>
					<p>2011年12月31日，山东某某集团全面预算管理系统顺利上线运行。</p></li>
				</ul>
				<div id="flip">
					<div class="digg4 metpager_8">
						<span class="disabled disabledfy"><b>«</b></span><span class="disabled disabledfy">‹</span><span class="current">1</span><span class="disabled disabledfy">›</span><span
							class="disabled disabledfy"><b>»</b></span>
					</div>
				</div>
			</div>

		</div>
		<div class="clear"></div>
		<jsp:include page="/footer.jsp"></jsp:include>
	</div>


</body>
</html>