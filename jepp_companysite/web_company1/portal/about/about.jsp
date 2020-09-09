<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="generator" content="MetInfo 5.2.7">
<link href="<%=basePath%>/portal/favicon.ico" rel="shortcut icon">
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
			<div class="active" id="sidebar" data-csnow="29" data-class3="0" data-jsok="2">
				<dl class="list-none navnow">
					<dt id="part2_30">
						<a href="<%=basePath%>/portal/about/about.jsp" title="公司简介" class="zm"><span>公司简介</span></a>
					</dt>
				</dl>
				<dl class="list-none ">
					<dt id="part2_31">
						<a href="<%=basePath%>/portal/about/about31.html" title="发展历程" class="zm"><span>发展历程</span></a>
					</dt>
				</dl>
				<dl class="list-none ">
					<dt id="part2_32">
						<a href="<%=basePath%>/portal/about/about32.html" title="分支机构" class="zm"><span>分支机构</span></a>
					</dt>
				</dl>
				<dl class="list-none ">
					<dt id="part2_33">
						<a href="<%=basePath%>/portal/about/about33.html" title="合作伙伴" class="zm"><span>合作伙伴</span></a>
					</dt>
				</dl>
				<dl class="list-none ">
					<dt id="part2_42">
						<a href="<%=basePath%>/portal/about/about42.html" title="联系我们" class="zm"><span>联系我们</span></a>
					</dt>
				</dl>
				<div class="clear"></div>
			</div>

		
		</div>
		<div class="sb_box">
			<h3 class="title">
				<div class="position">
					当前位置：<a href="<%=basePath%>/portal/" title="首页">首页</a> &gt; <a href="<%=basePath%>/portal/about/">关于我们</a>
				</div>
				<span>关于我们</span>
			</h3>
			<div class="clear"></div>

			<div class="editor active" id="showtext">
				<div>
					<p>&nbsp;</p>
					<div>
						<p>
							<span style="line-height: 2;">&nbsp; &nbsp; &nbsp;<img src="<%=basePath %>images/20140301_163529.png"
								style="width: 220px; height: 309px; float: right; padding-left: 20px; margin-left: 10px; margin-right: 10px;" alt="图片关键词">&nbsp; 某某软件股份公司成立于2001年1月，是深圳证券交易所上市公司。 股票简称“某某软件”，股票代码002065，
								注册资金6.9亿元。在北京及全国拥有30余家分支机构，员工规模超过4000人。以应用软件开发、计算机信息系统集成、信息技术服务及网络流控产品为主 要业务，拥有300多项自主知识产权的软件产品，是国家规划布局的重点软件企业， 国家火炬计划重点高新技术企业，国内最早通过软件能力成熟度集成（CMMI）5级认证的软件企业之一。连续多年被工业和信息化部评为中国自主软件产品前十 家企业。
							</span>
						</p>
						<p>&nbsp;</p>
						<div>&nbsp; &nbsp; &nbsp; 某某软件自成立以来，已为数百个用户提供了优秀的信息系统解决方案，涵盖多种应用与技术平台，用户遍布医疗、政府、金融、电信、电力、煤炭、石油、石化、 交通、国防、保险、科教及制造等行业，成功完成了国家电网公司、中国农业银行、中国移动通信公司、中石油股份公司、最高人民检察院、江苏中烟工业公司、中
							国国际航空公司、海关总署、北京友谊医院、中华联合财产保险公司、广州国土局、杭州、武汉房管局等客户的堪称业界典范的应用软件开发和计算机信息系统集成 项目。</div>
						<div>&nbsp;</div>
						<div>&nbsp; &nbsp; &nbsp; 作为专业信息技术服务供应商，某某十分注重同业界各类知名IT厂商建立和保持良好的合作关系，通过共享市场资源与技术资源了解最新的技术发张趋势与市场动 态，把国内外先进的技术和产品用一流的服务带给客户。多年良好的业绩及服务已经得到众多厂家的信任与认可。目前，某某已成为数十家国际知名IT企业的增值
							代理商、系统集成商或战略合作伙伴，为向用户提供全面解决方案及优质服务奠定了坚实的基础。</div>
						<div>&nbsp;</div>
					</div>
					<p>&nbsp;</p>
				</div>
				<div class="clear"></div>
			</div>

		</div>
		<div class="clear"></div>
		<jsp:include page="/footer.jsp"></jsp:include>
	</div>

	

</body>
</html>