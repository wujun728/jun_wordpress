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
			<h3 style="border-top-left-radius: 5px; border-top-right-radius: 5px;" class="title myCorner" data-corner="top 5px">服务和管理</h3>
			<div class="active" id="sidebar" data-csnow="28" data-class3="0" data-jsok="2">
				<dl class="list-none navnow">
					<dt id="part2_35">
						<a href="<%=basePath%>/service/service35.html" title="客户服务体系" class="zm"><span>客户服务体系</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_36">
						<a href="<%=basePath%>/service/service36.html" title="软件开发流程" class="zm"><span>软件开发流程</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_37">
						<a href="<%=basePath%>/service/service37.html" title="系统集成流程" class="zm"><span>系统集成流程</span></a>
					</dt>
				</dl>
				<dl class="list-none navnow">
					<dt id="part2_38">
						<a href="<%=basePath%>/service/service38.html" title="保修服务流程" class="zm"><span>保修服务流程</span></a>
					</dt>
				</dl>
				<div class="clear"></div>
			</div>

		
		</div>
		<div class="sb_box">
			<h3 class="title">
				<div class="position">
					当前位置：<a href="<%=basePath%>/" title="首页">首页</a> &gt; <a href="<%=basePath%>/service/">服务和管理</a>
				</div>
				<span>服务和管理</span>
			</h3>
			<div class="clear"></div>

			<div class="editor active" id="showtext">
				<div>
					<p>
						<span style="line-height: 2;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src="<%=basePath %>images/20140301_165357.jpg" style="width: 200px; height: 133px; float: left;padding-right:20px;" alt="图片关键词">作
							为致力于应用软件开发和计算机信息系统集成的领先企业，专业服务一直是某某软件的竞争优势所在。某某服务和某某产品（包括众多优秀的软件产品和行业整体解 决方案）一样，是某某软件战略发展的重点。服务先行是某某客户交流和市场推广的重要策略，服务制胜是某某和某某客户双赢的重要法宝。
						</span>
					</p>
					<p>&nbsp;</p>
					<div>
						<span style="font-size:14px;"><strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;某某软件的质量方针、经营原则、和服务宗旨</strong></span>
					</div>
					<div>&nbsp;</div>
					<div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;某某质量方针是“ 精心设计、优质服务、科学管理、追求最佳 ”，在这一方针的指导下，优质服务贯彻到公司每个项目、每个产品、每个部门、每个员工。多年来,某某本着“ 求实为本、至诚至信 ”的经营原则，本着“ 诚信、快捷、专业、理性
						”的服务宗旨，与广大客户建立了广泛、密切、共同发展的合作伙伴关系，客户的成功使某某服务品牌DHC在国内IT行业和主要应用领域树立了良好的形象。</div>
					<div>&nbsp;</div>
					<div>
						<span style="font-size:14px;"><strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;某某软件的服务对象 </strong></span>
					</div>
					<div>&nbsp;</div>
					<div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;某某发展之初就定位于为行业用户提供计算机系统和应用软件的整体解决方案，与某某软件产品和某某系统集成解决方案一致，某某服务的对象是国内 外企业、事业单位和政府部门，某某的一切产品和服务都是为了满足行业用户日益提高的信息技术相关需求。</div>
					<div>&nbsp; &nbsp; &nbsp;</div>
				</div>
				<div class="clear"></div>
			</div>

		</div>
		<div class="clear"></div>
		<jsp:include page="/footer.jsp"></jsp:include>
	</div>



</body>
</html>